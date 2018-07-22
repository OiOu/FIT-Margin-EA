package smartBot.bussines.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.Currency;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.jpa.CurrencyEntity;
import smartBot.bean.jpa.CurrencyRatesEntity;
import smartBot.bean.jpa.ScopeEntity;
import smartBot.bussines.service.CurrencyRatesService;
import smartBot.bussines.service.CurrencyService;
import smartBot.bussines.service.mapping.CurrencyRatesServiceMapper;
import smartBot.bussines.service.mapping.CurrencyServiceMapper;
import smartBot.bussines.service.mapping.ScopeServiceMapper;
import smartBot.data.repository.jpa.CurrencyRatesJpaRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class CurrencyRatesServiceImpl implements CurrencyRatesService {

    private static final Log logger = LogFactory.getLog(CurrencyRatesServiceImpl.class);

    @Autowired
    private CurrencyRatesJpaRepository currencyRatesJpaRepository;

    @Resource
    private CurrencyRatesServiceMapper currencyRatesServiceMapper;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CurrencyServiceMapper currencyServiceMapper;

    @Autowired
    private ScopeServiceMapper scopeServiceMapper;


    @Override
    public CurrencyRates findById(Integer id) {
        CurrencyRatesEntity currencyRatesEntity = currencyRatesJpaRepository.getById(id);
        CurrencyRates currencyRates = null;
        if (currencyRatesEntity != null) {
            currencyRates = currencyRatesServiceMapper.mapEntityToBean(currencyRatesEntity);
        }
        return currencyRates;
    }

    @Override
    public List<CurrencyRates> findAll() {
        Iterable<CurrencyRatesEntity> currencyRateEntities = currencyRatesJpaRepository.findAll();
        List<CurrencyRates> beans = new ArrayList<CurrencyRates>();
        for (CurrencyRatesEntity currencyRatesEntity : currencyRateEntities) {
            beans.add(currencyRatesServiceMapper.mapEntityToBean(currencyRatesEntity));
        }

        return beans;
    }

    @Override
    public CurrencyRates create(CurrencyRates bean) {
        return null;
    }

    @Override
    public List<CurrencyRates> findAllByShortName(String shortName) {
        List<CurrencyRatesEntity> currencyRatesEntity = currencyRatesJpaRepository.findAllByCurrencyShortName(shortName);
        List<CurrencyRates> currencyRates = null;
        if (currencyRatesEntity != null) {
            currencyRates = currencyRatesServiceMapper.mapEntitiesToBeans(currencyRatesEntity);
        }
        return currencyRates;
    }

    @Override
    public void delete(String shortName) {
        currencyRatesJpaRepository.deleteAllByShortName(shortName);
    }

    @Override
    public CurrencyRates save(Scope scope, CurrencyRates currencyRates) {
        if (currencyRates == null) {
            throw new IllegalStateException("ERROR: Create: CurrencyRates is NULL!");
        }

        if (scope == null) {
            throw new IllegalStateException("ERROR: Create: Scope is NULL!");
        }

        CurrencyRatesEntity currencyRatesEntity = currencyRatesJpaRepository.findAllByScopeId(scope.getId());
        Integer currencyRateId = null;

        if (currencyRatesEntity == null) {
            ScopeEntity scopeEntity = new ScopeEntity();
            CurrencyEntity currencyEntity = new CurrencyEntity();

            scopeServiceMapper.mapBeanToEntity(scope, scopeEntity);

            Currency currency = currencyService.findById(currencyRates.getCurrency().getId());
            if (currency == null) {
                logger.error("ERROR: Create: CurrencyRates: Currency is NULL!");
                return null;
            }
            currencyServiceMapper.mapBeanToEntity(currency, currencyEntity);

            currencyRatesEntity = new CurrencyRatesEntity();
            currencyRatesEntity.setCurrency(currencyEntity);
            currencyRatesEntity.setScope(scopeEntity);
        } else {
            currencyRateId = currencyRatesEntity.getId();
        }

        currencyRatesServiceMapper.mapBeanToEntity(currencyRates, currencyRatesEntity);
        if (currencyRateId != null) {
            currencyRatesEntity.setId(currencyRateId);
        }
        currencyRatesEntity = currencyRatesJpaRepository.save(currencyRatesEntity);

        return currencyRatesServiceMapper.mapEntityToBean(currencyRatesEntity);
    }

    @Override
    public void delete(CurrencyRates currencyRates) {
        CurrencyRatesEntity currencyEntity = currencyRatesJpaRepository.getById(currencyRates.getId());
        if (currencyEntity == null) throw new IllegalArgumentException("ERROR: Delete: CurrencyRates with ID: " + currencyRates.getId() + " was not found!");

        currencyRatesJpaRepository.delete(currencyEntity);
    }

    @Override
    public void delete(Integer id) {
        currencyRatesJpaRepository.deleteById(id);
    }

}
