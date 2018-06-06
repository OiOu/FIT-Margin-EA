package smartBot.bussines.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.MarginRates;
import smartBot.bean.jpa.CurrencyEntity;
import smartBot.bean.jpa.MarginRatesEntity;
import smartBot.bussines.service.MarginRatesService;
import smartBot.bussines.service.mapping.MarginRatesServiceMapper;
import smartBot.data.repository.jpa.CurrencyJpaRepository;
import smartBot.data.repository.jpa.MarginRatesJpaRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class MarginRatesServiceImpl implements MarginRatesService {

    private static final Log logger = LogFactory.getLog(MarginRatesServiceImpl.class);

    @Autowired
    private MarginRatesJpaRepository marginRatesJpaRepository;

    @Autowired
    private CurrencyJpaRepository currencyJpaRepository;

    @Resource
    private MarginRatesServiceMapper marginRatesServiceMapper;

    @Override
    public MarginRates findById(Integer id) {
        MarginRatesEntity marginRatesRatesEntity = marginRatesJpaRepository.getById(id);
        MarginRates marginRates = null;
        if (marginRatesRatesEntity != null) {
            marginRates = marginRatesServiceMapper.mapEntityToBean(marginRatesRatesEntity);
        }
        return marginRates;
    }

    @Override
    public List<MarginRates> findAll() {
        Iterable<MarginRatesEntity> marginRateEntities = marginRatesJpaRepository.findAll();
        List<MarginRates> beans = new ArrayList<MarginRates>();
        for (MarginRatesEntity marginRatesEntity : marginRateEntities) {
            beans.add(marginRatesServiceMapper.mapEntityToBean(marginRatesEntity));
        }
        return beans;
    }

    @Override
    public List<MarginRates> findAllByShortName(String shortName) {
        List<MarginRatesEntity> currencyRatesEntity = marginRatesJpaRepository.findAllByCurrencyShortName(shortName);
        List<MarginRates> currencyRates = null;
        if (currencyRatesEntity != null) {
            currencyRates = marginRatesServiceMapper.mapEntitiesToBeans(currencyRatesEntity);
        }
        return currencyRates;
    }

    @Override
    public MarginRates getByShortNameAndDate(String shortName, DateTime onDate) {
        MarginRatesEntity currencyRatesEntity = marginRatesJpaRepository.getByShortNameAndDate(shortName, onDate) ;
        MarginRates currencyRates = null;
        if (currencyRatesEntity != null) {
            currencyRates = marginRatesServiceMapper.mapEntityToBean(currencyRatesEntity);
        }
        return currencyRates;
    }

    @Transactional
    @Override
    public void delete(String shortName) {
        marginRatesJpaRepository.deleteAllByShortName(shortName);
    }

    public void createAll(List<MarginRates> marginRatesList) {
        if (marginRatesList == null) {
            throw new IllegalStateException("ERROR: Create: MarginRates list is NULL!");
        }

        List<MarginRatesEntity> marginRatesEntityList = new ArrayList<MarginRatesEntity>();
        List<MarginRatesEntity> marginRatesEntityListForSave = new ArrayList<MarginRatesEntity>();

        marginRatesEntityList =  marginRatesServiceMapper.mapBeansToEntities(marginRatesList);

        marginRatesEntityList.stream().forEach(value -> {
            CurrencyEntity currencyEntity = currencyJpaRepository.findByClearingCode(value.getClearingCode());
            if (currencyEntity == null) {
                logger.error("ERROR: Create: CurrencyRates: currencyEntity for clearing code "+value.getClearingCode()+" is NULL!");
            } else {
                MarginRatesEntity lastMarginRatesEntity = marginRatesJpaRepository.getLastByClearingCode(value.getClearingCode(), value.getStartPeriod(), value.getEndPeriod());
                value.setCurrency(currencyEntity);

                if (lastMarginRatesEntity == null) {
                    marginRatesEntityListForSave.add(value);
                } else if (lastMarginRatesEntity.getId() != value.getId() && // check if not the same entity
                            lastMarginRatesEntity.getMaintenanceRate().doubleValue() != value.getMaintenanceRate().doubleValue()) {
                    lastMarginRatesEntity.setEndDate(new Date());
                    marginRatesEntityListForSave.add(lastMarginRatesEntity);
                    marginRatesEntityListForSave.add(value);
                }
            }
        });

        marginRatesJpaRepository.saveAll(marginRatesEntityListForSave);

        return;
    }

    @Override
    public MarginRates create(MarginRates marginRates) {
        if (marginRates == null) {
            throw new IllegalStateException("ERROR: Create: MarginRates is NULL!");
        }

        CurrencyEntity currencyEntity = currencyJpaRepository.findByClearingCode(marginRates.getClearingCode());
        if (currencyEntity == null) {
            logger.error("ERROR: Create: CurrencyRates: currencyEntity is NULL!");
            return null;
        }
        MarginRatesEntity marginRatesEntity = new MarginRatesEntity();
        marginRatesEntity.setCurrency(currencyEntity);

        marginRatesServiceMapper.mapBeanToEntity(marginRates, marginRatesEntity);
        MarginRatesEntity marginEntitySaved = marginRatesJpaRepository.save(marginRatesEntity);
        return marginRatesServiceMapper.mapEntityToBean(marginEntitySaved);
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
