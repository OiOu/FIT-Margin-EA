package smartBot.bussines.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.jpa.CurrencyRatesEntity;
import smartBot.bussines.service.CurrencyRatesService;
import smartBot.bussines.service.CurrencyService;
import smartBot.bussines.service.ScopeService;
import smartBot.bussines.service.cache.ServerCache;
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
    private ScopeService scopeService;

    @Autowired
    private CurrencyServiceMapper currencyServiceMapper;

    @Autowired
    private ScopeServiceMapper scopeServiceMapper;

    @Resource
    private ServerCache serverCache;

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
    public CurrencyRates findLastByScope(Scope scope) {
        CurrencyRatesEntity currencyRatesEntity = currencyRatesJpaRepository.findAllByScopeIdAndScopeType(scope.getId(), scope.getType());
        CurrencyRates currencyRates = null;
        if (currencyRatesEntity != null) {
            currencyRates = currencyRatesServiceMapper.mapEntityToBean(currencyRatesEntity);
        }
        return currencyRates;
    }

    @Override
    public void delete(String shortName) {
        currencyRatesJpaRepository.deleteAllByShortName(shortName);
    }

    @Override
    public void merge(CurrencyRates currentCurrencyRate, CurrencyRates lastCurrencyRate) {
        lastCurrencyRate.setTimestamp(currentCurrencyRate.getTimestamp());
        lastCurrencyRate.setVolume(currentCurrencyRate.getVolume());
        lastCurrencyRate.setOpen(currentCurrencyRate.getOpen());
        lastCurrencyRate.setClose(currentCurrencyRate.getClose());
        lastCurrencyRate.setHigh(currentCurrencyRate.getHigh());
        lastCurrencyRate.setLow(currentCurrencyRate.getLow());
        lastCurrencyRate.setPointPrice(currentCurrencyRate.getPointPrice());
        lastCurrencyRate.setPointPips(currentCurrencyRate.getPointPips());
        lastCurrencyRate.setScope(currentCurrencyRate.getScope());
        lastCurrencyRate.setCurrency(currentCurrencyRate.getCurrency());
    }

    @Override
    public CurrencyRates findLastByCurrencyIdAndScopeType(Integer currencyId, Integer scopeType) {
        CurrencyRates currencyRate = serverCache.getCurrencyRatesFromCache(currencyId, scopeType);

        if (currencyRate == null) {
            CurrencyRatesEntity currencyRatesEntity = currencyRatesJpaRepository.findAllByCurrencyIdAndScopeType(currencyId, scopeType);
            if (currencyRatesEntity != null) {
                currencyRate = currencyRatesServiceMapper.mapEntityToBean(currencyRatesEntity);
                serverCache.setCurrencyRatesToCache(currencyRate);
            }
        }
        return currencyRate;
    }

    @Override
    public CurrencyRates save(CurrencyRates currencyRates) {
        if (currencyRates == null) {
            throw new IllegalStateException("ERROR: Create: CurrencyRates is NULL!");
        }

        // Search in DB
        CurrencyRatesEntity currencyRatesEntity = currencyRatesJpaRepository.findAllByScopeIdAndScopeType(currencyRates.getScope().getId(), currencyRates.getScope().getType());

        if (currencyRatesEntity == null) {

            currencyRatesEntity = new CurrencyRatesEntity();
        }

        currencyRatesServiceMapper.mapBeanToEntity(currencyRates, currencyRatesEntity);

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
