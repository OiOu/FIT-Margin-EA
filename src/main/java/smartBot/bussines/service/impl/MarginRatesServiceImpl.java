package smartBot.bussines.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.Currency;
import smartBot.bean.MarginRates;
import smartBot.bean.jpa.CurrencyEntity;
import smartBot.bean.jpa.MarginRatesEntity;
import smartBot.bussines.service.CurrencyService;
import smartBot.bussines.service.MarginRatesService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.CurrencyServiceMapper;
import smartBot.bussines.service.mapping.MarginRatesServiceMapper;
import smartBot.data.repository.jpa.MarginRatesJpaRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Transactional
public class MarginRatesServiceImpl implements MarginRatesService {

    private static final Log logger = LogFactory.getLog(MarginRatesServiceImpl.class);

    @Resource
    private MarginRatesJpaRepository marginRatesJpaRepository;

    @Resource
    private CurrencyService currencyService;

    @Resource
    private CurrencyServiceMapper currencyServiceMapper;

    @Resource
    private ServerCache serverCache;

    @Resource
    private MarginRatesServiceMapper marginRatesServiceMapper;

    @Override
    public MarginRates findById(Integer id) {
        // TODO Try to get marginRate from cache to avoid additional DB query
        MarginRatesEntity marginRatesEntity = marginRatesJpaRepository.getById(id);
        MarginRates marginRates = null;
        if (marginRatesEntity != null) {
            marginRates = marginRatesServiceMapper.mapEntityToBean(marginRatesEntity);
        }
        return marginRates;
    }

    @Override
    public List<MarginRates> findAll() {
        // TODO Try to get marginRate from cache to avoid additional DB query
        Iterable<MarginRatesEntity> marginRateEntities = marginRatesJpaRepository.findAll();
        List<MarginRates> beans = new ArrayList<MarginRates>();
        for (MarginRatesEntity marginRatesEntity : marginRateEntities) {
            beans.add(marginRatesServiceMapper.mapEntityToBean(marginRatesEntity));
        }
        return beans;
    }

    @Override
    public List<MarginRates> findAllByShortName(String shortName) {
        // TODO Try to get marginRate from cache to avoid additional DB query
        List<MarginRatesEntity> marginRateEntity = marginRatesJpaRepository.findAllByCurrencyShortName(shortName);
        List<MarginRates> marginRates = null;
        if (marginRateEntity != null) {
            marginRates = marginRatesServiceMapper.mapEntitiesToBeans(marginRateEntity);
        }
        return marginRates;
    }

    @Override
    public MarginRates findByShortNameOnDate(String shortName, DateTime onDate) {
        // TODO Try to get marginRate from cache to avoid additional DB query

        List<MarginRatesEntity> marginRateEntity = marginRatesJpaRepository.getByShortNameOnDate(shortName, onDate);
        MarginRates marginRates = null;
        if (marginRateEntity != null && marginRateEntity.size() > 0) {
            marginRates = marginRatesServiceMapper.mapEntityToBean(marginRateEntity.get(0));
        }

        return marginRates;
    }

    @Override
    public MarginRates findByCurrencyIdOnDate(Integer currencyId, DateTime onDate) {
        // Try to get marginRate from cache to avoid additional DB query
        MarginRates marginRate = serverCache.getMarginRateFromCache(currencyId);
        if (marginRate == null
                || marginRate.getEndDate() != null && (marginRate.getEndDate().isBefore(onDate) || marginRate.getEndDate().isEqual(onDate))) {

            List<MarginRatesEntity> marginRateEntityList = marginRatesJpaRepository.getByCurrencyIdOnDate(currencyId, onDate);
            if (marginRateEntityList != null && !marginRateEntityList.isEmpty()) {

                if (marginRate != null) {
                    // Set force update flag for recalculation (only when it was not the first margin from DB
                    serverCache.setIsForceUpdateZoneNeeded(true);
                }

                List<MarginRates> marginRateList = marginRatesServiceMapper.mapEntitiesToBeans(marginRateEntityList);
                Collections.sort(marginRateList);

                marginRate = marginRateList.get(0);
                serverCache.setMarginRateToCache(currencyId, marginRate);

            } else {
                logger.error("Margin Rate for currencyId: " + currencyId + " was not found on date: " +onDate);
            }
        }
        return marginRate;
    }

    @Override
    public void delete(String shortName) {
        marginRatesJpaRepository.deleteAllByShortName(shortName);
    }

    @Override
    public synchronized List<MarginRates> createAll(List<MarginRates> marginRateJsonList) {
        if (marginRateJsonList == null) {
            throw new IllegalStateException("ERROR: Create: MarginRates json list is NULL!");
        }

        List<MarginRatesEntity> marginRateEntityList = new ArrayList<>();
        List<MarginRatesEntity> marginRateEntityListForSave = new ArrayList<>();

        marginRateEntityList =  marginRatesServiceMapper.mapBeansToEntities(marginRateJsonList);

        for (MarginRatesEntity value : marginRateEntityList) {
            Currency currency = currencyService.findByClearingCode(value.getClearingCode());

            if (currency != null) {
                CurrencyEntity currencyEntity = new CurrencyEntity();
                currencyServiceMapper.mapBeanToEntity(currency, currencyEntity);

                value.setCurrency(currencyEntity);

                List<MarginRatesEntity> marginRateEntityAllList = marginRatesJpaRepository.findAllByClearingCode(value.getClearingCode());

                if (marginRateEntityAllList == null || marginRateEntityAllList.size() == 0) {
                    marginRateEntityListForSave.add(value);
                } else {

                    List<MarginRates> marginRateAllList = marginRatesServiceMapper.mapEntitiesToBeans(marginRateEntityAllList);
                    if (marginRateAllList != null && !marginRateAllList.isEmpty()) {
                        Collections.sort(marginRateAllList);
                        MarginRates lastMarginRate = marginRateAllList.get(0);
                        lastMarginRate.setEndDate(new DateTime().withTime(23, 00, 00, 00 ));

                        // TODO check period to avoid duplicates for RU

                        if (lastMarginRate.getMaintenanceRate().doubleValue() != value.getMaintenanceRate().doubleValue()
                                && comparePeriods(value.getStartPeriod(), value.getEndPeriod())) {
                            // get values from currency because we should save history of changing next values
                            value.setFuturePoint(currency.getFuturePoint());
                            value.setPricePerContract(currency.getPricePerContract());

                            MarginRatesEntity lastMarginRatesEntity = new MarginRatesEntity();
                            marginRatesServiceMapper.mapBeanToEntity(lastMarginRate, lastMarginRatesEntity);
                            lastMarginRatesEntity.setCurrency(currencyEntity);

                            marginRateEntityListForSave.add(value);
                            marginRateEntityListForSave.add(lastMarginRatesEntity);
                        }
                    } else {
                        logger.fatal("Cant get Margin Rate from JSON!");
                    }
                }
            }
        }

        List<MarginRates> marginRateAllList = new ArrayList<>();
        if (marginRateEntityListForSave != null && marginRateEntityListForSave.size() > 0) {
            List<MarginRatesEntity> marginRatesEntityList = (List<MarginRatesEntity>) marginRatesJpaRepository.saveAll(marginRateEntityListForSave);
            marginRateAllList = marginRatesServiceMapper.mapEntitiesToBeans(marginRatesEntityList);
        }

        return marginRateAllList;
    }

    private boolean comparePeriods(String date1, String date2) {
        if (date1.equals("-") || date2.equals("-")) {
            return true;
        }
        DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/yyyy");
        DateTime dt1 = dtf.parseDateTime(date1);
        DateTime dt2 = dtf.parseDateTime(date2);
        DateTime dtNow = new DateTime();

        // set the first day of month
        dt1.withDayOfMonth(1);
        dt2.withDayOfMonth(1);
        dtNow.withDayOfMonth(1);

        if (dtNow.isAfter(dt1) && dtNow.isBefore(dt2)) {
            return true;
        }

        return false;
    }

    @Override
    public MarginRates save(MarginRates marginRates) {
        if (marginRates == null) {
            throw new IllegalStateException("ERROR: Create: MarginRates is NULL!");
        }

        MarginRatesEntity marginRateEntitySaved = null;
        Currency currency = currencyService.findByClearingCode(marginRates.getClearingCode());

        if (currency == null) {
            logger.error("Create MarginRate: currency for clearing code "+marginRates.getClearingCode()+" is NULL!");
        } else {
            CurrencyEntity currencyEntity = new CurrencyEntity();
            currencyServiceMapper.mapBeanToEntity(currency, currencyEntity);

            if (currencyEntity == null) {
                logger.error("ERROR: Create: CurrencyRates: currencyEntity is NULL!");
                return null;
            }

            MarginRatesEntity marginRateEntity = new MarginRatesEntity();
            marginRateEntity.setCurrency(currencyEntity);

            marginRatesServiceMapper.mapBeanToEntity(marginRates, marginRateEntity);
            marginRateEntitySaved = marginRatesJpaRepository.save(marginRateEntity);
            return marginRatesServiceMapper.mapEntityToBean(marginRateEntitySaved);
        }
        return null;
    }

    @Override
    public void delete(MarginRates marginRates) {
        // delete should not be possible at all
    }

    @Override
    public void delete(Integer id) {
        marginRatesJpaRepository.deleteById(id);
    }

}
