package smartBot.bussines.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import smartBot.bean.ZoneLevel;
import smartBot.bean.jpa.ZoneLevelEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class ZoneLevelServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    public ZoneLevelServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from '{@link ZoneLevelEntity}' to '{@link ZoneLevel}'
     * @param zoneLevelEntity
     */
    public ZoneLevel mapEntityToBean(ZoneLevelEntity zoneLevelEntity) {
        if(zoneLevelEntity == null) {
            return null;
        }
        //--- Generic mapping
        ZoneLevel zoneLevel = map(zoneLevelEntity, ZoneLevel.class);
        return zoneLevel;
    }

    /**
     * Mapping from '{@link ZoneLevel}' to '{@link ZoneLevelEntity}'
     * @param zoneLevel
     * @param zoneLevelEntity
     */
    public void mapBeanToEntity(ZoneLevel zoneLevel, ZoneLevelEntity zoneLevelEntity) {
        if(zoneLevel == null) {
            return;
        }

        //--- Generic mapping
        map(zoneLevel, zoneLevelEntity);
    }

    public  List<ZoneLevelEntity> mapBeansToEntities(List<ZoneLevel> marginRatesList) {
        if(marginRatesList == null) {
            return null;
        }

        //--- Generic mapping
        return map(marginRatesList, ZoneLevelEntity.class);
    }
    /**
     * Mapping from '{@link List}<{@link ZoneLevel}'> to '{@link List}<{@link ZoneLevelEntity}>'
     * @param marginRatesEntities
     * @return List<ZoneLevel>
     */
    public List<ZoneLevel> mapEntitiesToBeans(List<ZoneLevelEntity> marginRatesEntities) {
        if(marginRatesEntities == null || marginRatesEntities.size() == 0) {
            return new ArrayList<>();
        }

        //--- Generic mapping
        ArrayList<ZoneLevel> marginRatesArrayList = new ArrayList<>();
        for (ZoneLevelEntity zoneLevelEntity: marginRatesEntities)
            marginRatesArrayList.add(map(zoneLevelEntity, ZoneLevel.class));

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
