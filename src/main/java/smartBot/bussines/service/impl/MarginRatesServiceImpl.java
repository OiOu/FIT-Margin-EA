package smartBot.bussines.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class MarginRatesServiceImpl implements MarginRatesService {

    private static final Log logger = LogFactory.getLog(MarginRatesServiceImpl.class);

    @Autowired
    private MarginRatesJpaRepository marginRatesJpaRepository;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyServiceMapper currencyServiceMapper;

    @Autowired
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
    public MarginRates findByShortNameAndDate(String shortName, Date onDate) {
        // TODO Try to get marginRate from cache to avoid additional DB query

        List<MarginRatesEntity> marginRateEntity = marginRatesJpaRepository.getByShortNameAndDate(shortName, onDate);
        MarginRates marginRates = null;
        if (marginRateEntity != null && marginRateEntity.size() > 0) {
            marginRates = marginRatesServiceMapper.mapEntityToBean(marginRateEntity.get(0));
        }

        return marginRates;
    }

    @Override
    public MarginRates findByCurrencyIdAndDate(Integer currencyId, Date onDate) {
        // Try to get marginRate from cache to avoid additional DB query
        MarginRates marginRate = serverCache.getMarginRateFromCache(currencyId, onDate);
        if (marginRate == null) {

            List<MarginRatesEntity> marginRateEntityList = marginRatesJpaRepository.getByCurrencyIdAndDate(currencyId, onDate);
            if (marginRateEntityList != null && !marginRateEntityList.isEmpty()) {
                List<MarginRates> marginRateList = marginRatesServiceMapper.mapEntitiesToBeans(marginRateEntityList);
                Collections.sort(marginRateList);
                marginRate = marginRateList.get(0);
                serverCache.setMarginRateToCache(currencyId, marginRate);
            }
        }

        return marginRate;
    }

    @Transactional
    @Override
    public void delete(String shortName) {
        marginRatesJpaRepository.deleteAllByShortName(shortName);
    }

    public void createAll(List<MarginRates> marginRateJsonList) {
        if (marginRateJsonList == null) {
            throw new IllegalStateException("ERROR: Create: MarginRates json list is NULL!");
        }

        List<MarginRatesEntity> marginRateEntityList = new ArrayList<MarginRatesEntity>();
        List<MarginRatesEntity> marginRateEntityListForSave = new ArrayList<MarginRatesEntity>();

        marginRateEntityList =  marginRatesServiceMapper.mapBeansToEntities(marginRateJsonList);

        marginRateEntityList.stream().forEach( value -> {
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
                        MarginRates lastMarginRate = marginRateAllList.get(0);
                        Collections.sort(marginRateAllList);

                        if (lastMarginRate.getId() != value.getId() && // check if not the same entity
                                lastMarginRate.getMaintenanceRate().doubleValue() != value.getMaintenanceRate().doubleValue()) {
                            // get values from last row
                            // if will be needed to update -> do it in DB OR in input parameter from main client
                            value.setFuturePoint(lastMarginRate.getFuturePoint());
                            value.setPricePerContract(lastMarginRate.getPricePerContract());

                            marginRateEntityListForSave.add(value);
                        }
                    } else {
                        logger.fatal("Cant get Margin Rate from JSON!");
                    }
                }
            }
        });

        marginRatesJpaRepository.saveAll(marginRateEntityListForSave);

        return;
    }

    @Override
    public MarginRates create(MarginRates marginRates) {
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
