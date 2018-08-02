package smartBot.bussines.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import smartBot.bean.PriorityType;
import smartBot.bean.jpa.PriorityTypeEntity;

@Component
public class PriorityTypeServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public PriorityTypeServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from '{@link PriorityTypeEntity}' to '{@link PriorityType}'
     * @param priorityTypeEntity
     */
    public PriorityType mapEntityToBean(PriorityTypeEntity priorityTypeEntity) {
        if(priorityTypeEntity == null) {
            return null;
        }
        //--- Generic mapping
        PriorityType priorityType = map(priorityTypeEntity, PriorityType.class);
        return priorityType;
    }

    /**
     * Mapping from '{@link PriorityType}' to '{@link PriorityTypeEntity}'
     * @param priorityType
     * @param priorityTypeEntity
     */
    public void mapBeanToEntity(PriorityType priorityType, PriorityTypeEntity priorityTypeEntity) {
        if(priorityType == null) {
            return;
        }

        //--- Generic mapping
        map(priorityType, priorityTypeEntity);
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

