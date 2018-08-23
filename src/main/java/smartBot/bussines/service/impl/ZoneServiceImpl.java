package smartBot.bussines.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.*;
import smartBot.bean.jpa.ZoneEntity;
import smartBot.bussines.service.*;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.ZoneServiceMapper;
import smartBot.data.repository.jpa.ZoneJpaRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Transactional
public class ZoneServiceImpl implements ZoneService {
    private static final Log logger = LogFactory.getLog(ZoneServiceImpl.class);

    @Resource
    private CurrencyRatesService currencyRateService;

    @Resource
    private CurrencyService currencyService;

    @Resource
    private ScopeService scopeService;

    @Resource
    private MarginRatesService marginRateService;

    @Resource
    private ZoneJpaRepository zoneJpaRepository;

    @Resource
    private ZoneServiceMapper zoneServiceMapper;

    @Resource
    private ZoneLevelService zoneLevelService;

    @Resource
    private OrderSettingsService orderSettingsService;

    @Resource
    private ServerCache serverCache;

    /*
    * Main procedure for calculate scope of zone info
    */
    // TODO remove synchronized and test it
    public synchronized List<Zone> calculate(Scope scope, CurrencyRates currencyRate) {

        MarginRates marginRate = serverCache.getMarginRateFromCache(scope.getCurrency().getId());

        // do not process if margin was not found
        if (marginRate == null) return null;

        Currency currency = currencyService.findById(scope.getCurrency().getId());

        List<ZoneLevel> zoneLevels = zoneLevelService.findAll();
        Collections.sort(zoneLevels);

        List<Zone> zones = scope.getZones();

        if (zones == null || zones.isEmpty()) {
            for (ZoneLevel level : zoneLevels){
                if (!level.isEnable()) { continue; }

                Zone zone = new Zone();
                zone.setLevel(level);
                if (scope.getType() == Scope.BUILD_FROM_HIGH) {
                    zone.setPrice(currencyRate.getHigh());
                } else {
                    zone.setPrice(currencyRate.getLow());
                }
                zone.setActivated(false);
                zone.setTouched(false);
                zone.setScope(scope);
                zone.setTimestamp(currencyRate.getTimestamp());
                zone.setName(zone.toString());
                zones.add(zone);

            }
        }

        // if scope has zones then recalculate it. for new list calculate too
        List<Zone> newZones = new ArrayList<>();
        for (int i = 0; i < zones.size(); i++) {
            Zone zone = zones.get(i);
            if (!zone.getLevel().isEnable()) { continue; }

            if (scope.getType() == Scope.BUILD_FROM_HIGH) {
                zone.setPrice(currencyRate.getHigh());
            } else {
                zone.setPrice(currencyRate.getLow());
            }
            zone.setTimestamp(currencyRate.getTimestamp());
            zone.setActivated(false);
            zone.setTouched(false);
            newZones.add(calculate(scope, zone, currency, currencyRate, marginRate));
        }

        Iterable<ZoneEntity> zoneListEntities = zoneServiceMapper.mapBeansToEntities(newZones);
        zoneListEntities = zoneJpaRepository.saveAll(zoneListEntities);
        zones = zoneServiceMapper.mapEntitiesToBeans((List<ZoneEntity>) zoneListEntities);

        scope.setZones(zones);

        return zones;
    }

    private Zone calculate(Scope scope, Zone zone, Currency currency, CurrencyRates currencyRate, MarginRates marginRate) {

        Double futurePoint = marginRate.getFuturePoint();
        if (futurePoint == null) futurePoint = currencyRate.getPointPips() *currencyRate.getPointPrice();

        Double marginRateForCalculation = marginRate.getMaintenanceRate() * 1.1 / marginRate.getPricePerContract() * futurePoint;
        Integer heightK = (currency.getK() != null? currency.getK() : 1);

        if (currency.getInverted()) {
            zone.setPriceCalc(1 / (1 / zone.getPrice() + marginRateForCalculation * currencyRate.getPointPips() * zone.getLevel().getK() * scope.getType()));
        } else {
            zone.setPriceCalc(zone.getPrice() - marginRateForCalculation * zone.getLevel().getK() * scope.getType());
        }
        zone.setPriceCalcShift(zone.getPriceCalc() + zone.getLevel().getHeight() * heightK * currencyRate.getPointPips() * currencyRate.getPointPrice() * scope.getType());
        zone.setPriceCalcOrderDetectionZone(zone.getPriceCalc() + zone.getLevel().getHeight() * 2 * heightK * currencyRate.getPointPips() * currencyRate.getPointPrice() * scope.getType());

        // Get inverse scope for determining take profit price
        Integer scopeTypeForTakeProfit = scope.getType() == Scope.BUILD_FROM_HIGH? Scope.BUILD_FROM_LOW : Scope.BUILD_FROM_HIGH;
        Scope scopeForTakeProfit = scopeService.findByCurrencyIdAndScopeType(currency.getId(), scopeTypeForTakeProfit);

        // Getting Order settings
        OrderSettings orderSettings = orderSettingsService.getByCurrencyId(currency.getId());
        if (orderSettings != null) {
            // If smth will happen later we will have StopLoss anyway
            zone.setPriceStopLoss(zone.getPriceCalc() - orderSettings.getSlSize() * heightK * currencyRate.getPointPips() * currencyRate.getPointPrice() * scope.getType());

            if (scopeForTakeProfit != null && scopeForTakeProfit.getZones() != null) {
                List<Zone> zones = scopeForTakeProfit.getZones();
                for (Zone z : zones) {
                    // filter zones which has priceCalcShift with risk/profit >= 1:N
                    if (zone.getLevel().isEnable()) {
                        // Calculate distance to next (profit) zone and determine stop loss size like (distance / riskProfitRounded)
                        Double distanceInPoints = (z.getPriceCalcShift() - zone.getPriceCalcShift()) * scope.getType() / currencyRate.getPointPrice() / currencyRate.getPointPips();
                        Integer stopLossInPoint = distanceInPoints.intValue() / (orderSettings.getRiskProfitMin() != null ? orderSettings.getRiskProfitMin() : 2);

                        // to avoid useless orders (open price is very close to TP)
                        if (!z.getTouched() && distanceInPoints > 50 && z.getLevel().getTakeProfitPercent() > 0.0 ) {
                            // SL Should not be > fixed size but it can be less
                            if (stopLossInPoint > orderSettings.getSlSize()) {
                                stopLossInPoint = orderSettings.getSlSize();
                            }

                            zone.setPriceStopLoss(zone.getPriceCalcShift() - stopLossInPoint * heightK * currencyRate.getPointPips() * currencyRate.getPointPrice() * scope.getType());
                            zone.setPriceTakeProfit(z.getPriceCalcShift());

                            if (orderSettings.getBreakEven() > 0) {
                                zone.setPriceBreakEvenProfit(zone.getPriceCalcShift() + orderSettings.getBreakEven() * heightK * currencyRate.getPointPips() * currencyRate.getPointPrice() * scope.getType());
                                zone.setPriceTrailProfit(zone.getPriceCalcShift() + orderSettings.getTrail() * heightK * currencyRate.getPointPips() * currencyRate.getPointPrice() * scope.getType());
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            logger.info("OrderSettings for currency: " + currency.getShortName() + " was not found");
        }

        return zone;
    }

    @Override
    public boolean isReCalculationNeeded(CurrencyRates currentCurrencyRate, CurrencyRates lastCurrencyRate) {
        return false;
    }

    @Override
    public Zone findById(Integer id) {
        return null;
    }

    @Override
    public List<Zone> findAll() {
        return null;
    }

    @Override
    public Zone save(Zone zone) {
        ZoneEntity zoneEntity = new ZoneEntity();
        zoneServiceMapper.mapBeanToEntity(zone, zoneEntity);

        zoneEntity = zoneJpaRepository.save(zoneEntity);
        return zoneServiceMapper.mapEntityToBean(zoneEntity);
    }

    @Override
    public void delete(Zone bean) { }

    @Override
    public void delete(Integer id) { }

    @Override
    public Integer getLastId() {
        return zoneJpaRepository.getLastId();
    }

}
