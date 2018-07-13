package smartBot.bussines.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import smartBot.bean.Currency;
import smartBot.bean.CurrencyRates;
import smartBot.bean.CurrencyRatesJson;
import smartBot.bean.jpa.CurrencyRatesEntity;
import smartBot.bussines.service.CurrencyService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CurrencyRatesServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    private Map<String, Currency> currencyMapCache;

    @Resource
    private CurrencyService currencyService;

    public CurrencyRatesServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from '{@link CurrencyRatesEntity}' to '{@link CurrencyRates}'
     * @param currencyRatesEntity
     */
    public CurrencyRates mapEntityToBean(CurrencyRatesEntity currencyRatesEntity) {
        if(currencyRatesEntity == null) {
            return null;
        }
        //--- Generic mapping
        CurrencyRates currencyRates = map(currencyRatesEntity, CurrencyRates.class);
        return currencyRates;
    }

    /**
     * Mapping from '{@link CurrencyRates}' to '{@link CurrencyRatesEntity}'
     * @param currencyRates
     * @param currencyRatesEntity
     */
    public void mapBeanToEntity(CurrencyRates currencyRates, CurrencyRatesEntity currencyRatesEntity) {
        if(currencyRates == null) {
            return;
        }

        //--- Generic mapping
        map(currencyRates, currencyRatesEntity);
    }

    /**
     * Mapping from '{@link List}<{@link CurrencyRates}'> to '{@link List}<{@link CurrencyRatesEntity}>'
     * @param currencyRatesEntities
     * @return List<CurrencyRates>
     */
    public List<CurrencyRates> mapEntitiesToBeans(List<CurrencyRatesEntity> currencyRatesEntities) {
        if(currencyRatesEntities == null || currencyRatesEntities.size() == 0) {
            return new ArrayList<>();
        }

        //--- Generic mapping
        ArrayList<CurrencyRates> currencyRatesArrayList = new ArrayList<>();
        for (CurrencyRatesEntity currencyRatesEntity: currencyRatesEntities)
            currencyRatesArrayList.add(map(currencyRatesEntity, CurrencyRates.class));

        return currencyRatesArrayList;
    }

    /**
     * Mapping from '{@link CurrencyRatesJson}' to '{@link CurrencyRates}'
     * @param currencyRatesJson
     */
    public CurrencyRates mapJsonToBean(CurrencyRatesJson currencyRatesJson) {
        if(currencyRatesJson == null) {
            return null;
        }

        CurrencyRates currencyRates = new CurrencyRates();
        Currency currency = null;
        if (currencyMapCache != null && currencyMapCache.containsKey(currencyRatesJson.getCurrency())) {
            currency = currencyMapCache.get(currencyRatesJson.getCurrency());
        } else {
            currency = currencyService.findByShortName(currencyRatesJson.getCurrency());
            if (currencyMapCache == null) {
                currencyMapCache = new HashMap<String, Currency>();
            }
            currencyMapCache.put(currencyRatesJson.getCurrency(), currency);
        }

        if (currency == null) return null;

        currencyRates.setCurrencyId(currency.getId());
        currencyRates.setHigh(currencyRatesJson.getHigh());
        currencyRates.setLow(currencyRatesJson.getLow());
        currencyRates.setOpen(currencyRatesJson.getOpen());
        currencyRates.setClose(currencyRatesJson.getClose());
        currencyRates.setPeriod(currencyRatesJson.getPeriod());
        currencyRates.setTimestamp(currencyRatesJson.getTimestamp());
        currencyRates.setVolume(currencyRatesJson.getVolume());
        currencyRates.setPips(currencyRatesJson.getPips());

        return currencyRates;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected ModelMapper getModelMapper() {
        return modelMapper;
    }

    protected void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


}
