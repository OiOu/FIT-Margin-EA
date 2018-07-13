package smartBot.bussines.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import smartBot.bean.Zone;
import smartBot.bean.jpa.ZoneEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class ZoneServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    public ZoneServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from '{@link ZoneEntity}' to '{@link Zone}'
     * @param zoneEntity
     */
    public Zone mapEntityToBean(ZoneEntity zoneEntity) {
        if(zoneEntity == null) {
            return null;
        }
        //--- Generic mapping
        Zone zone = map(zoneEntity, Zone.class);
        return zone;
    }

    /**
     * Mapping from '{@link Zone}' to '{@link ZoneEntity}'
     * @param zone
     * @param zoneEntity
     */
    public void mapBeanToEntity(Zone zone, ZoneEntity zoneEntity) {
        if(zone == null) {
            return;
        }

        //--- Generic mapping
        map(zone, zoneEntity);
    }

    public  List<ZoneEntity> mapBeansToEntities(List<Zone> marginRatesList) {
        if(marginRatesList == null) {
            return null;
        }

        //--- Generic mapping
        return map(marginRatesList, ZoneEntity.class);
    }
    /**
     * Mapping from '{@link List}<{@link Zone}'> to '{@link List}<{@link ZoneEntity}>'
     * @param marginRatesEntities
     * @return List<Zone>
     */
    public List<Zone> mapEntitiesToBeans(List<ZoneEntity> marginRatesEntities) {
        if(marginRatesEntities == null || marginRatesEntities.size() == 0) {
            return new ArrayList<>();
        }

        //--- Generic mapping
        ArrayList<Zone> marginRatesArrayList = new ArrayList<>();
        for (ZoneEntity zoneEntity: marginRatesEntities)
            marginRatesArrayList.add(map(zoneEntity, Zone.class));

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
