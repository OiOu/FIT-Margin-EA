package smartBot.bussines.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.CurrencyRates;
import smartBot.bean.jpa.CurrencyEntity;
import smartBot.bean.jpa.CurrencyRatesEntity;
import smartBot.bussines.service.CurrencyRatesService;
import smartBot.bussines.service.mapping.CurrencyRatesServiceMapper;
import smartBot.data.repository.jpa.CurrencyJpaRepository;
import smartBot.data.repository.jpa.CurrencyRatesJpaRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class CurrencyRatesServiceImpl implements CurrencyRatesService {

    @Autowired
    private CurrencyRatesJpaRepository currencyRatesJpaRepository;

    @Autowired
    private CurrencyJpaRepository currencyJpaRepository;

    @Resource
    private CurrencyRatesServiceMapper currencyRatesServiceMapper;

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
    public List<CurrencyRates> findAllByShortName(String shortName) {
        List<CurrencyRatesEntity> currencyRatesEntity = currencyRatesJpaRepository.findAllByCurrencyShortName(shortName);
        List<CurrencyRates> currencyRates = null;
        if (currencyRatesEntity != null) {
            currencyRates = currencyRatesServiceMapper.mapEntitiesToBeans(currencyRatesEntity);
        }
        return currencyRates;
    }

    @Override
    public CurrencyRates create(CurrencyRates currencyRates) {
        if (currencyRates == null) {
            throw new IllegalStateException("ERROR: Create: CurrencyRates is NULL!");
        }

        CurrencyEntity currencyEntity = currencyJpaRepository.getById(currencyRates.getCurrencyId());
        if (currencyEntity == null) {
            throw new IllegalStateException("ERROR: Create: CurrencyRates: currencyEntity is NULL!");
        }
        CurrencyRatesEntity currencyRatesEntity = new CurrencyRatesEntity();
        currencyRatesEntity.setCurrency(currencyEntity);

        currencyRatesServiceMapper.mapBeanToEntity(currencyRates, currencyRatesEntity);
        CurrencyRatesEntity currencyEntitySaved = currencyRatesJpaRepository.save(currencyRatesEntity);
        return currencyRatesServiceMapper.mapEntityToBean(currencyEntitySaved);
    }

    @Override
    public void delete(CurrencyRates currencyRates) {
        CurrencyRatesEntity currencyEntity = currencyRatesJpaRepository.getById(currencyRates.getId());
        if (currencyEntity == null) throw new IllegalArgumentException("ERROR: Delete: CurrencyRates with ID: " + currencyRates.getId() + " was not found!");

        currencyRatesJpaRepository.delete(currencyEntity);
    }

    @Override
    public void deleteById(Integer id) {
        currencyRatesJpaRepository.deleteById(id);
    }

    @Override
    public void deleteByShortName(String shortName) {
        currencyRatesJpaRepository.deleteAllByShortName(shortName);
    }
}
