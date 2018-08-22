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
import smartBot.bussines.service.CurrencyService;
import smartBot.bussines.service.ScopeService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.CurrencyServiceMapper;
import smartBot.bussines.service.mapping.ScopeServiceMapper;
import smartBot.data.repository.jpa.CurrencyJpaRepository;
import smartBot.data.repository.jpa.ScopeJpaRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
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
    private CurrencyService currencyService;

    @Resource
    private CurrencyServiceMapper currencyServiceMapper;

    @Autowired
    private ServerCache serverCache;

    @Override
    public Scope findById(Integer id) {
        return null;
    }

    @Override
    public List<Scope> findAll() {
        return null;
    }

    @Override
    public Scope create(CurrencyRates currencyRate, Integer type) {

        Currency currency = currencyService.findById(currencyRate.getCurrency().getId());

        ScopeEntity scopeEntity = new ScopeEntity();
        Scope scope = new Scope();
        CurrencyEntity currencyEntity = new CurrencyEntity();

        if (currency != null) {
            scope.setCurrency(currency);
            //scope.setCurrencyRate(currencyRate);
            scope.setType(type);
            scope.setTimestampFrom(currencyRate.getTimestamp());
            scope.setName(scope.toString());

            currencyServiceMapper.mapBeanToEntity(currency, currencyEntity);

            scopeEntity.setCurrency(currencyEntity);

            scopeServiceMapper.mapBeanToEntity(scope, scopeEntity);
            scopeEntity = scopeJpaRepository.save(scopeEntity);
            scope = scopeServiceMapper.mapEntityToBean(scopeEntity);
        }

        return scope;
    }

    @Override
    public Scope save(Scope scope) {
        if (scope != null) {
            ScopeEntity scopeEntity = new ScopeEntity();
            scopeServiceMapper.mapBeanToEntity(scope, scopeEntity);
            scopeEntity = scopeJpaRepository.save(scopeEntity);
            scope = scopeServiceMapper.mapEntityToBean(scopeEntity);
        }
        return scope;
    }

    @Override
    public void delete(Scope scope) {
        if (scope != null) {
            ScopeEntity scopeEntity = new ScopeEntity();
            scopeServiceMapper.mapBeanToEntity(scope, scopeEntity);
            scopeJpaRepository.delete(scopeEntity);

            serverCache.removeScopeFromCache(scope);
        }
        return;
    }

    @Override
    public List<Scope> saveAll(List<Scope> scopes) {
        if (scopes != null) {

            List<ScopeEntity> scopeEntities = new ArrayList<>();
            scopeServiceMapper.mapBeansToEntities(scopes, scopeEntities);
            scopeEntities = (List<ScopeEntity>)scopeJpaRepository.saveAll(scopeEntities);
            scopeServiceMapper.mapBeansToEntities(scopes, scopeEntities);

        }
        return scopes;
    }

    @Override
    public void delete(Integer id) {
        scopeJpaRepository.deleteById(id);
    }

    @Override
    public Scope findByCurrencyIdAndScopeType(Integer currencyId, Integer scopeType) {
        // Try to get scope from cache
        Scope scope = serverCache.getScopeFromCache(currencyId, scopeType);

        if (scope == null) {
            // Search in DB
            List<ScopeEntity> scopeEntityList = scopeJpaRepository.findByCurrencyIdAndScopeType(currencyId, scopeType);
            if (scopeEntityList != null) {

                List<Scope> scopes = scopeServiceMapper.mapEntitiesToBeans(scopeEntityList);
                if (scopes != null && !scopes.isEmpty()) {
                    Collections.sort(scopes);
                    scope = scopes.get(0);

                    serverCache.setScopeToCache(scope);
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
