package smartBot.bussines.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import smartBot.bean.OrderSettings;
import smartBot.bean.jpa.OrderSettingsEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderSettingsServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    public OrderSettingsServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from '{@link OrderSettingsEntity}' to '{@link OrderSettings}'
     * @param orderSettingsEntity
     */
    public OrderSettings mapEntityToBean(OrderSettingsEntity orderSettingsEntity) {
        if(orderSettingsEntity == null) {
            return null;
        }
        //--- Generic mapping
        OrderSettings orderSettings = map(orderSettingsEntity, OrderSettings.class);
        return orderSettings;
    }

    /**
     * Mapping from '{@link OrderSettings}' to '{@link OrderSettingsEntity}'
     * @param orderSettings
     * @param orderSettingsEntity
     */
    public void mapBeanToEntity(OrderSettings orderSettings, OrderSettingsEntity orderSettingsEntity) {
        if(orderSettings == null) {
            return;
        }

        //--- Generic mapping
        map(orderSettings, orderSettingsEntity);
    }

    public  List<OrderSettingsEntity> mapBeansToEntities(List<OrderSettings> marginRatesList) {
        if(marginRatesList == null) {
            return null;
        }

        //--- Generic mapping
        return map(marginRatesList, OrderSettingsEntity.class);
    }
    /**
     * Mapping from '{@link List}<{@link OrderSettings}'> to '{@link List}<{@link OrderSettingsEntity}>'
     * @param marginRatesEntities
     * @return List<OrderSettings>
     */
    public List<OrderSettings> mapEntitiesToBeans(List<OrderSettingsEntity> marginRatesEntities) {
        if(marginRatesEntities == null || marginRatesEntities.size() == 0) {
            return new ArrayList<>();
        }

        //--- Generic mapping
        ArrayList<OrderSettings> marginRatesArrayList = new ArrayList<>();
        for (OrderSettingsEntity orderSettingsEntity: marginRatesEntities)
            marginRatesArrayList.add(map(orderSettingsEntity, OrderSettings.class));

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
