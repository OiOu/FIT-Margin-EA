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
    private ServerCache serverCache;

    /*
    * Main procedure for calculate scope of zone info
    */
    public synchronized List<Zone> calculate(Scope scope, CurrencyRates currencyRate) {

        Currency currency = currencyService.findById(currencyRate.getCurrency().getId());
        MarginRates marginRate = marginRateService.findByCurrencyIdAndDate( currencyRate.getCurrency().getId(),  currencyRate.getTimestamp());
        List<ZoneLevel> zoneLevels = zoneLevelService.findAll();
        Collections.sort(zoneLevels);

        List<Zone> zones = scope.getZones();

        if (zones == null || zones.isEmpty()) {
            for (ZoneLevel level : zoneLevels){
                Zone zone = new Zone();
                zone.setLevel(level);
                if (scope.getType() == Scope.BUILD_FROM_HIGH) {
                    zone.setPrice(currencyRate.getHigh());
                } else {
                    zone.setPrice(currencyRate.getLow());
                }
                zone.setActivated(false);
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
            if (scope.getType() == Scope.BUILD_FROM_HIGH) {
                zone.setPrice(currencyRate.getHigh());
            } else {
                zone.setPrice(currencyRate.getLow());
            }
            zone.setTimestamp(currencyRate.getTimestamp());
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
    public Zone create(Zone bean) {
        return null;
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
