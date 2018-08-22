package smartBot.bussines.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.Currency;
import smartBot.bean.jpa.CurrencyEntity;
import smartBot.bussines.service.CurrencyService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.CurrencyServiceMapper;
import smartBot.data.repository.jpa.CurrencyJpaRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class CurrencyServiceImpl implements CurrencyService{
    private static final Log logger = LogFactory.getLog(CurrencyServiceImpl.class);

    @Autowired
    private CurrencyJpaRepository currencyJpaRepository;

    @Resource
    private CurrencyServiceMapper currencyServiceMapper;

    @Autowired
    private ServerCache serverCache;

    @Override
    public Currency findById(Integer id) {

        // Try to get currency from cache to avoid additional DB query
        Currency currency = serverCache.getCurrencyFromCache( id );

        if (currency == null) {
            CurrencyEntity currencyEntity = currencyJpaRepository.getById(id);
            if (currencyEntity != null) {
                currency = currencyServiceMapper.mapEntityToBean(currencyEntity);
                serverCache.setCurrencyToCache(currency);
            } else {
                logger.warn("Currency:" + id + " was not found");
            }

        }
        return currency;
    }

    @Override
    public List<Currency> findAll() {
        Iterable<CurrencyEntity> currencyEntities = currencyJpaRepository.findAll();
        List<Currency> beans = new ArrayList<Currency>();
        for (CurrencyEntity currencyEntity : currencyEntities) {
            beans.add(currencyServiceMapper.mapEntityToBean(currencyEntity));
        }

        return beans;
    }

    @Override
    public Currency findByShortName(String shortName) {
        Currency currency = serverCache.getCurrencyFromCache( shortName );

        if (currency == null) {
            CurrencyEntity currencyEntity = currencyJpaRepository.findByShortName(shortName);
            if (currencyEntity != null) {
                currency = currencyServiceMapper.mapEntityToBean(currencyEntity);
                serverCache.setCurrencyToCache(currency);
            } else {
                logger.warn("Currency:" + shortName + " was not found");
            }
        }
        return currency;
    }

    @Override
    public Currency findByClearingCode(String clearingCode) {
        Currency currency = serverCache.getCurrencyFromCache( clearingCode);

        if (currency == null) {
            CurrencyEntity currencyEntity = currencyJpaRepository.findByClearingCode(clearingCode);
            if (currencyEntity != null) {
                currency = currencyServiceMapper.mapEntityToBean(currencyEntity);
                serverCache.setCurrencyToCache(currency);
            } else {
                logger.warn("Currency with clearing code:" + clearingCode + " was not found");
            }
        }
        return currency;
    }

    @Transactional
    @Override
    public void delete(String shortName) {
        currencyJpaRepository.deleteAllByShortName(shortName);
    }

    @Transactional
    @Override
    public Currency save(Currency currency) {
        if (currency == null) {
            throw new IllegalStateException("ERROR: Create: Currency is NULL!");
        }

        Integer id = currency.getId();
        if (id == null) {
            id = new Integer(0);
        }
        CurrencyEntity currencyEntity = currencyJpaRepository.getById(id);

        if (currencyEntity != null) {
            throw new IllegalStateException("ERROR: Create: Currency with ID: " + currency.getId() + " already exists!");
        }
        currencyEntity = new CurrencyEntity();
        currencyServiceMapper.mapBeanToEntity(currency, currencyEntity);
        CurrencyEntity currencyEntitySaved = currencyJpaRepository.save(currencyEntity);
        return currencyServiceMapper.mapEntityToBean(currencyEntitySaved);
    }

    @Transactional
    @Override
    public void delete(Currency currency) {
        if (currency == null) {
            throw new IllegalStateException("ERROR: Delete: Currency is NULL!");
        }

        CurrencyEntity currencyEntity = currencyJpaRepository.getById(currency.getId());
        if (currencyEntity == null) throw new IllegalArgumentException("ERROR: Delete: Currency with ID: " + currency.getId() + " was not found!");

        currencyJpaRepository.delete(currencyEntity);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        currencyJpaRepository.deleteById(id);
    }
}
