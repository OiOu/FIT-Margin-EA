package smartBot.bussines.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import smartBot.bean.Currency;
import smartBot.bean.jpa.CurrencyEntity;

@Component
public class CurrencyServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public CurrencyServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from 'CurrencyEntity' to 'Currency'
     * @param currencyEntity
     */
    public Currency mapCurrencyEntityToCurrency(CurrencyEntity currencyEntity) {
        if(currencyEntity == null) {
            return null;
        }
        //--- Generic mapping
        Currency currency = map(currencyEntity, Currency.class);
        return currency;
    }

    /**
     * Mapping from 'Currency' to 'CurrencyEntity'
     * @param currency
     * @param currencyEntity
     */
    public void mapCurrencyToCurrencyEntity(Currency currency, CurrencyEntity currencyEntity) {
        if(currency == null) {
            return;
        }

        //--- Generic mapping
        map(currency, currencyEntity);
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

