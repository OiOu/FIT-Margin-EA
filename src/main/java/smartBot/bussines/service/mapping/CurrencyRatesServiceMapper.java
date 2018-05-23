package smartBot.bussines.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import smartBot.bean.CurrencyRates;
import smartBot.bean.jpa.CurrencyRatesEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class CurrencyRatesServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

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
