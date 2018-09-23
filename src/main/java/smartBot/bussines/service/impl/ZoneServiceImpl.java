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
import smartBot.defines.PriorityConstants;

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
    private PriorityService priorityService;

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
            for (int k = 1; k <= 10; k++) {
                for (ZoneLevel level : zoneLevels) {
                    if (!level.getEnable()) {
                        continue;
                    }

                    Zone zone = new Zone();
                    zone.setLevel(level);
                    zone.setFloor(k);
                    zone.setActivated(false);
                    zone.setTouched(false);
                    zone.setScope(scope);
                    zone.setTimestamp(currencyRate.getTimestamp());
                    zone.setName(zone.toString());
                    zones.add(zone);
                }
            }
        }

        // if scope has zones then recalculate it
        List<Zone> newZones = new ArrayList<>();
        Double distance = new Double(0);
        Integer heightK = (currencyRate.getCurrency().getK() != null? currencyRate.getCurrency().getK() : 1);
        Zone zoneLastOnFloor = null;

        Priority priority = priorityService.findByCurrencyIdAndPrioritySubType(currency.getId(), PriorityConstants.LOCAL);
        for (int i = 0; i < zones.size(); i++) {
            Zone zone = zones.get(i);

            // Check if zone price is the same - means that margin rate was changed not high or low extremum
            // Skip calculation if zone was touched. Its needed for case when margin rate was changed - we should build new zones from prev touched
            //todo check distance value because we have a shift for floors 3+
            if (!zone.getTouched() || (zone.getTouched() && priority != null && priority.getType().getType() == scope.getType())) {
                if (zone.getFloor() == 1) {
                    if (scope.getType() == Scope.BUILD_FROM_HIGH) {
                        zone.setPrice(currencyRate.getHigh());
                    } else {
                        zone.setPrice(currencyRate.getLow());
                    }
                } else {
                    zone.setPrice(zoneLastOnFloor.getPriceCalc() - distance * currencyRate.getPointPrice() * currencyRate.getPointPips() * heightK * scope.getType());
                }

                zone.setTimestamp(currencyRate.getTimestamp());
                zone.setActivated(false);
                if (!serverCache.isForceUpdateZoneNeeded()) {
                    zone.setTouched(false);
                }

                Zone newZonAfterCalculation = calculate(scope, zone, currency, currencyRate, marginRate);
                newZones.add(newZonAfterCalculation);
                if (i > 0 && (i + 1) % zoneLevels.size() == 0) {
                    zoneLastOnFloor = newZonAfterCalculation;
                }
            } else {
                newZones.add(zone);

                if (i > 0 && (i + 1) % zoneLevels.size() == 0) {
                    zoneLastOnFloor = zone;
                }
            }
        }

        Iterable<ZoneEntity> zoneListEntities = zoneServiceMapper.mapBeansToEntities(newZones);
        zoneListEntities = zoneJpaRepository.saveAll(zoneListEntities);
        zones = zoneServiceMapper.mapEntitiesToBeans((List<ZoneEntity>) zoneListEntities);

        return zones;
    }

    private Zone calculate(Scope scope, Zone zone, Currency currency, CurrencyRates currencyRate, MarginRates marginRate) {

        if (!zone.getLevel().getEnable()) return null;
        if (zone.getTouched()) return zone;

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
        zone.setPriceOrder(zone.getPriceCalc() + zone.getLevel().getOrderAssignmentShift() * heightK * currencyRate.getPointPips() * currencyRate.getPointPrice() * scope.getType());

        // Get inverse scope for determining take profit price
        Integer scopeTypeForTakeProfit = scope.getType() == Scope.BUILD_FROM_HIGH? Scope.BUILD_FROM_LOW : Scope.BUILD_FROM_HIGH;
        Scope scopeForTakeProfit = scopeService.findByCurrencyIdAndScopeType(currency.getId(), scopeTypeForTakeProfit);

        // If something will happen later - we will have SL and TP anyway
        zone.setPriceStopLoss(zone.getPriceOrder() - zone.getLevel().getStopLossSize() * heightK * currencyRate.getPointPips() * currencyRate.getPointPrice() * scope.getType());
        zone.setPriceTakeProfit(zone.getPriceOrder() - zone.getLevel().getStopLossSize() * zone.getLevel().getRiskProfitMin() * heightK * currencyRate.getPointPips() * currencyRate.getPointPrice() * scope.getType());

        // if getBreakEven is not null and > 0 we will move SL to getBreakEven shift price
        if (zone.getLevel().getBreakEven() != null) {
            zone.setPriceBreakEvenProfit(zone.getPriceOrder() + zone.getLevel().getBreakEven() * heightK * currencyRate.getPointPips() * currencyRate.getPointPrice() * scope.getType());
        }

        if (zone.getLevel().getTrail() != null) {
            zone.setPriceTrailProfit(zone.getPriceOrder() + zone.getLevel().getTrail() * heightK * currencyRate.getPointPips() * currencyRate.getPointPrice() * scope.getType());
        }

        if (scope.getType() == Scope.BUILD_FROM_LOW && currencyRate.getAtrPriceFromMonthHigh() != null) {
            zone.setPriceATR(currencyRate.getAtrPriceFromMonthHigh());
        }
        if (scope.getType() == Scope.BUILD_FROM_HIGH && currencyRate.getAtrPriceFromMonthLow() != null) {
            zone.setPriceATR(currencyRate.getAtrPriceFromMonthLow());
        }

        if (scopeForTakeProfit != null && scopeForTakeProfit.getZones() != null) {
            List<Zone> zones = scopeForTakeProfit.getZones();
            for (Zone zoneTakeProfit : zones) {
                // filter zones which has priceCalcShift with risk/profit >= 1:N
                if (zoneTakeProfit.getLevel().getEnable()) {
                    // Calculate distance to next (profit) zone and determine stop loss size like (distance / riskProfitRounded)
                    Double distanceInPoints = (zoneTakeProfit.getPriceCalcShift() - zone.getPriceOrder()) * scope.getType() / currencyRate.getPointPrice() / currencyRate.getPointPips()
                            - zone.getLevel().getOrderAssignmentShift() * scope.getType();

                    // Use fixed or dynamic SL
                    Integer stopLossInPoint = zoneTakeProfit.getLevel().getStopLossSize();
                    if (stopLossInPoint > zoneTakeProfit.getLevel().getStopLossSize()) {
                        stopLossInPoint = zoneTakeProfit.getLevel().getStopLossSize();

                        if (zoneTakeProfit.getLevel().getDynamicStopLoss()) {
                            stopLossInPoint = distanceInPoints.intValue() / (zoneTakeProfit.getLevel().getRiskProfitMin() != null ? zoneTakeProfit.getLevel().getRiskProfitMin() : 2);
                        }
                    }

                    zone.setPriceStopLoss(zone.getPriceOrder() - stopLossInPoint * heightK * currencyRate.getPointPips() * currencyRate.getPointPrice() * scope.getType());

                    // to avoid useless orders (open price is very close to TP)
                    if (!zoneTakeProfit.getTouched() && distanceInPoints > 50) { // TODO distanceInPoints from DB
                        zone.setPriceTakeProfit(zoneTakeProfit.getPriceCalcShift());
                        break;
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
