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
            currency = currencyServiceMapper.mapCurrencyEntityToCurrency(currencyEntity);
        }
        return currency;
    }

    @Override
    public List<Currency> findAll() {
        Iterable<CurrencyEntity> currencyEntities = currencyJpaRepository.findAll();
        List<Currency> beans = new ArrayList<Currency>();
        for (CurrencyEntity currencyEntity : currencyEntities) {
            beans.add(currencyServiceMapper.mapCurrencyEntityToCurrency(currencyEntity));
        }

        return beans;
    }

    @Override
    public Currency findByShortName(String shortName) {
        CurrencyEntity currencyEntity = currencyJpaRepository.findByShortName(shortName);
        Currency currency = null;
        if (currencyEntity != null) {
            currency = currencyServiceMapper.mapCurrencyEntityToCurrency(currencyEntity);
        }
        return currency;
    }

    @Override
    public Currency save(Currency currency) {
        CurrencyEntity currencyEntity = currencyJpaRepository.getById(currency.getId());
        currencyServiceMapper.mapCurrencyToCurrencyEntity(currency, currencyEntity);
        CurrencyEntity currencyEntitySaved = currencyJpaRepository.save(currencyEntity);
        return currencyServiceMapper.mapCurrencyEntityToCurrency(currencyEntitySaved);
    }

    @Override
    public Currency create(Currency currency) {
        Integer id = currency.getId();
        if (id == null) {
            id = new Integer(0);
        }
        CurrencyEntity currencyEntity = currencyJpaRepository.getById(id);

        if (currencyEntity != null) {
            throw new IllegalStateException("already.exists");
        }
        currencyEntity = new CurrencyEntity();
        currencyServiceMapper.mapCurrencyToCurrencyEntity(currency, currencyEntity);
        CurrencyEntity currencyEntitySaved = currencyJpaRepository.save(currencyEntity);
        return currencyServiceMapper.mapCurrencyEntityToCurrency(currencyEntitySaved);
    }

    @Override
    public Currency update(Currency currency) {
        CurrencyEntity assetEntity = currencyJpaRepository.getById(currency.getId());
        currencyServiceMapper.mapCurrencyToCurrencyEntity(currency, assetEntity);
        CurrencyEntity currencyEntitySaved = currencyJpaRepository.save(assetEntity);

        return currencyServiceMapper.mapCurrencyEntityToCurrency(currencyEntitySaved);
    }

    @Override
    public void delete(Currency currency) {
        CurrencyEntity currencyEntity = currencyJpaRepository.getById(currency.getId());
        currencyJpaRepository.delete(currencyEntity);
    }

    @Override
    public void delete(Integer id) {
        currencyJpaRepository.deleteById(id);
    }
}
