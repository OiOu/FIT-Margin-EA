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
import smartBot.bean.jpa.ScopeEntity;
import smartBot.bussines.service.ScopeService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.CurrencyServiceMapper;
import smartBot.bussines.service.mapping.ScopeServiceMapper;
import smartBot.data.repository.jpa.CurrencyJpaRepository;
import smartBot.data.repository.jpa.ScopeJpaRepository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class ScopeServiceImpl implements ScopeService {

    private static final Log logger = LogFactory.getLog(ScopeServiceImpl.class);

    @Autowired
    private ScopeJpaRepository scopeJpaRepository;

    @Resource
    private ScopeServiceMapper scopeServiceMapper;

    @Autowired
    private CurrencyJpaRepository currencyJpaRepository;

    @Autowired
    private CurrencyServiceImpl currencyService;

    @Resource
    private CurrencyServiceMapper currencyServiceMapper;

    @Autowired
    private ServerCache serverCache;

    @Override
    public ScopeEntity findById(Integer id) {
        return null;
    }

    @Override
    public List<ScopeEntity> findAll() {
        return null;
    }

    @Override
    public ScopeEntity create(ScopeEntity bean) {
        return null;
    }

    @Override
    public Scope create(CurrencyRates currencyRate, Integer type) {

        Scope scope = new Scope();
        scope.setCurrencyId(currencyRate.getCurrencyId());
        scope.setType(type);
        scope.setTimestampFrom(new Date());
        scope.setName(scope.toString());

        ScopeEntity scopeEntity = new ScopeEntity();
        CurrencyEntity currencyEntity = new CurrencyEntity();

        Currency currency = currencyService.findById(scope.getCurrencyId());
        if (currency == null) {
            currencyEntity = currencyJpaRepository.getById(scope.getCurrencyId());
            if (currencyEntity == null) {
                logger.error("ERROR: Create: Scope: currencyEntity is NULL!");
                return null;
            }
        } else {
             currencyServiceMapper.mapBeanToEntity(currency, currencyEntity);
        }

        scopeEntity.setCurrency(currencyEntity);

        scopeServiceMapper.mapBeanToEntity(scope, scopeEntity);
        scopeEntity = scopeJpaRepository.save(scopeEntity);
        scope = scopeServiceMapper.mapEntityToBean(scopeEntity);

        return scope;
    }

    @Override
    public void save(Scope scope) {

        ScopeEntity scopeEntity = new ScopeEntity();
        CurrencyEntity currencyEntity = new CurrencyEntity();

        Currency currency = currencyService.findById(scope.getCurrencyId());
        if (currency == null) {
            currencyEntity = currencyJpaRepository.getById(scope.getCurrencyId());
            if (currencyEntity == null) {
                logger.error("ERROR: Create: Scope: currencyEntity is NULL!");
                return;
            }
        } else {
            currencyServiceMapper.mapBeanToEntity(currency, currencyEntity);
        }

        scopeEntity.setCurrency(currencyEntity);

        scopeServiceMapper.mapBeanToEntity(scope, scopeEntity);
        scopeJpaRepository.save(scopeEntity);

        return;
    }


    @Override
    public void delete(ScopeEntity bean) {
        return ;
    }

    @Override
    public void delete(Integer id) {
        scopeJpaRepository.deleteById(id);
    }

    @Override
    public Scope findByCurrencyIdAndScopeTypeAndOnDate(Integer currencyId, Integer scopeType, Date onDate) {
        // Try to get scope from cache
        Scope scope = serverCache.getScopeFromCache(currencyId, scopeType, onDate);

        if (scope == null) {
            // Search in DB
            List<ScopeEntity> scopeEntityList = scopeJpaRepository.findByCurrencyIdAndScopeTypeAndTimestamp(currencyId, scopeType, onDate);
            if (scopeEntityList != null) {

                List<Scope> scopes = scopeServiceMapper.mapEntitiesToBeans(scopeEntityList);
                if (scopes != null && !scopes.isEmpty()) {
                    Collections.sort(scopes);
                    scope = scopes.get(0);
                    serverCache.setScopeCache(scope);
                }
            }
        }
        return scope;
    }

    @Override
    public Integer getLastId() {
        return scopeJpaRepository.getLastId();
    }

    @Override
    public Integer getLastId(Integer currencyId, Integer scopeType) {
        return scopeJpaRepository.getLastId(currencyId, scopeType);
    }



}
