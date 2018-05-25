package smartBot.bussines.service.impl;

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
import java.util.List;

@Component
@Transactional
public class MarginRatesServiceImpl implements MarginRatesService {

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

    @Transactional
    @Override
    public void delete(String shortName) {
        marginRatesJpaRepository.deleteAllByShortName(shortName);
    }

    @Transactional
    @Override
    public MarginRates create(MarginRates marginRates) {
        if (marginRates == null) {
            throw new IllegalStateException("ERROR: Create: MarginRates is NULL!");
        }

        CurrencyEntity currencyEntity = currencyJpaRepository.getById(marginRates.getCurrencyId());
        if (currencyEntity == null) {
            throw new IllegalStateException("ERROR: Create: CurrencyRates: currencyEntity is NULL!");
        }
        MarginRatesEntity marginRatesEntity = new MarginRatesEntity();
        marginRatesEntity.setCurrency(currencyEntity);

        marginRatesServiceMapper.mapBeanToEntity(marginRates, marginRatesEntity);
        MarginRatesEntity marginEntitySaved = marginRatesJpaRepository.save(marginRatesEntity);
        return marginRatesServiceMapper.mapEntityToBean(marginEntitySaved);
    }

    @Transactional
    @Override
    public void delete(MarginRates marginRates) {
        MarginRatesEntity marginEntity = marginRatesJpaRepository.getById(marginRates.getId());
        if (marginEntity == null) throw new IllegalArgumentException("ERROR: Delete: MarginRates with ID: " + marginRates.getId() + " was not found!");

        marginRatesJpaRepository.delete(marginEntity);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        marginRatesJpaRepository.deleteById(id);
    }

}
