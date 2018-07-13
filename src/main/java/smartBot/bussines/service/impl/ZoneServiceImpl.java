package smartBot.bussines.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.*;
import smartBot.bussines.service.*;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.data.repository.jpa.ZoneJpaRepository;

import javax.annotation.Resource;
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

    @Autowired
    private MarginRatesService marginRateService;

    @Resource
    private ZoneJpaRepository zoneJpaRepository;

    @Resource
    private ZoneLevelService zoneLevelService;

    @Autowired
    private ServerCache serverCache;

    /*
    * Main procedure for calculate scope of zone info
    */
    public List<Zone> calculate(Scope scope, CurrencyRates currencyRate) {

        Currency currency = currencyService.findById(currencyRate.getCurrencyId());
        MarginRates marginRate = marginRateService.findByCurrencyIdAndDate( currencyRate.getCurrencyId(),  currencyRate.getTimestamp());

        List<Zone> zones = scope.getZones();

        if (zones == null || zones.isEmpty()) {
            for (int level = 1; level < 10; level++) {
                Zone zone = new Zone();
                zone.setLevelId(level);
                if (scope.getType() == Scope.BUILD_FROM_HIGH) {
                    zone.setPrice(currencyRate.getHigh());
                } else {
                    zone.setPrice(currencyRate.getLow());
                }
                zone.setActivated(false);
                zone.setScopeId(scope.getId());
                zone.setTimestamp(currencyRate.getTimestamp());
                zone.setName(zone.toString());
                zones.add(zone);
            }
        }

        for (int level = 0; level < zones.size(); level++){
            Zone zone = calculate(scope, zones.get(level), currency, currencyRate, marginRate, level);
            if (zone != null) {
                zones.add(zone);
            }
        }
        return zones;
    }

    private Zone calculate(Scope scope, Zone zone, Currency currency, CurrencyRates currencyRate, MarginRates marginRate, Integer level) {

        ZoneLevel zoneLevel = zoneLevelService.findById(level);

        //Double marginRateForCalculation = marginRate.getMaintenanceRate() * 1.1 / marginRate.getPricePerContract() * 1;

        if (currency.getInverted()) {
            zone.setPriceCalc(1 / (1 / zone.getPrice() + marginRate.getMaintenanceRate() * zoneLevel.getK() * scope.getType()));
            zone.setPriceCalcShift(zone.getPriceCalc() + zoneLevel.getHeight() * currencyRate.getPips() * scope.getType());
        } else {
            zone.setPriceCalc(zone.getPrice().doubleValue() - marginRate.getMaintenanceRate().doubleValue() * zoneLevel.getK().doubleValue() * scope.getType().intValue());
            zone.setPriceCalcShift(zone.getPriceCalc() + zoneLevel.getHeight() * currencyRate.getPips() * scope.getType());
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
