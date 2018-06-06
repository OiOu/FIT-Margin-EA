package smartBot.bussines.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import smartBot.bean.MarginRates;
import smartBot.bean.jpa.MarginRatesEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class MarginRatesServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    public MarginRatesServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from '{@link MarginRatesEntity}' to '{@link MarginRates}'
     * @param marginRatesEntity
     */
    public MarginRates mapEntityToBean(MarginRatesEntity marginRatesEntity) {
        if(marginRatesEntity == null) {
            return null;
        }
        //--- Generic mapping
        MarginRates marginRates = map(marginRatesEntity, MarginRates.class);
        return marginRates;
    }

    /**
     * Mapping from '{@link MarginRates}' to '{@link MarginRatesEntity}'
     * @param marginRates
     * @param marginRatesEntity
     */
    public void mapBeanToEntity(MarginRates marginRates, MarginRatesEntity marginRatesEntity) {
        if(marginRates == null) {
            return;
        }

        //--- Generic mapping
        map(marginRates, marginRatesEntity);
    }

    public  List<MarginRatesEntity> mapBeansToEntities(List<MarginRates> marginRatesList) {
        if(marginRatesList == null) {
            return null;
        }

        //--- Generic mapping
        return map(marginRatesList, MarginRatesEntity.class);
    }
    /**
     * Mapping from '{@link List}<{@link MarginRates}'> to '{@link List}<{@link MarginRatesEntity}>'
     * @param marginRatesEntities
     * @return List<MarginRates>
     */
    public List<MarginRates> mapEntitiesToBeans(List<MarginRatesEntity> marginRatesEntities) {
        if(marginRatesEntities == null || marginRatesEntities.size() == 0) {
            return new ArrayList<>();
        }

        //--- Generic mapping
        ArrayList<MarginRates> marginRatesArrayList = new ArrayList<>();
        for (MarginRatesEntity marginRatesEntity: marginRatesEntities)
            marginRatesArrayList.add(map(marginRatesEntity, MarginRates.class));

        return marginRatesArrayList;
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
