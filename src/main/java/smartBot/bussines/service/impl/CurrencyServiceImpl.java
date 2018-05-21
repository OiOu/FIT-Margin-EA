package smartBot.bussines.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.Currency;
import smartBot.bean.jpa.CurrencyEntity;
import smartBot.bussines.service.CurrencyService;
import smartBot.bussines.service.mapping.CurrencyServiceMapper;
import smartBot.data.repository.jpa.CurrencyJpaRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class CurrencyServiceImpl implements CurrencyService{

    @Autowired
    private CurrencyJpaRepository currencyJpaRepository;

    @Resource
    private CurrencyServiceMapper currencyServiceMapper;

    @Override
    public Currency findById(Integer id) {

        CurrencyEntity currencyEntity = currencyJpaRepository.getById(id);
        Currency currency = null;
        if (currencyEntity != null) {
            currency = currencyServiceMapper.mapEntityToBean(currencyEntity);
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
        CurrencyEntity currencyEntity = currencyJpaRepository.findByShortName(shortName);
        Currency currency = null;
        if (currencyEntity != null) {
            currency = currencyServiceMapper.mapEntityToBean(currencyEntity);
        }
        return currency;
    }

    @Override
    public Currency save(Currency currency) {
        if (currency == null) {
            throw new IllegalStateException("ERROR: Save: Currency is NULL!");
        }

        CurrencyEntity currencyEntity = currencyJpaRepository.getById(currency.getId());
        if (currencyEntity == null) throw new IllegalArgumentException("ERROR: Save: Currency with ID: " + currency.getId() + " was not found!");

        currencyServiceMapper.mapBeanToEntity(currency, currencyEntity);
        CurrencyEntity currencyEntitySaved = currencyJpaRepository.save(currencyEntity);
        return currencyServiceMapper.mapEntityToBean(currencyEntitySaved);
    }

    @Override
    public Currency create(Currency currency) {
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

    @Override
    public Currency update(Currency currency) {
        if (currency == null) {
            throw new IllegalStateException("ERROR: Update: Currency is NULL!");
        }

        CurrencyEntity currencyEntity = currencyJpaRepository.getById(currency.getId());
        if (currencyEntity == null) throw new IllegalArgumentException("ERROR: Update: Currency with ID: " + currency.getId() + " was not found!");

        currencyServiceMapper.mapBeanToEntity(currency, currencyEntity);
        CurrencyEntity currencyEntitySaved = currencyJpaRepository.save(currencyEntity);

        return currencyServiceMapper.mapEntityToBean(currencyEntitySaved);
    }

    @Override
    public void delete(Currency currency) {
        if (currency == null) {
            throw new IllegalStateException("ERROR: Delete: Currency is NULL!");
        }

        CurrencyEntity currencyEntity = currencyJpaRepository.getById(currency.getId());
        if (currencyEntity == null) throw new IllegalArgumentException("ERROR: Delete: Currency with ID: " + currency.getId() + " was not found!");

        currencyJpaRepository.delete(currencyEntity);
    }

    @Override
    public void delete(Integer id) {
        currencyJpaRepository.deleteById(id);
    }
}
