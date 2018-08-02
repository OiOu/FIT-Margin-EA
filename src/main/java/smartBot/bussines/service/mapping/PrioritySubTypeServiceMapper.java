package smartBot.bussines.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import smartBot.bean.PrioritySubType;
import smartBot.bean.jpa.PrioritySubTypeEntity;

@Component
public class PrioritySubTypeServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public PrioritySubTypeServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from '{@link PrioritySubTypeEntity}' to '{@link PrioritySubType}'
     * @param prioritySubTypeEntity
     */
    public PrioritySubType mapEntityToBean(PrioritySubTypeEntity prioritySubTypeEntity) {
        if(prioritySubTypeEntity == null) {
            return null;
        }
        //--- Generic mapping
        PrioritySubType prioritySubType = map(prioritySubTypeEntity, PrioritySubType.class);
        return prioritySubType;
    }

    /**
     * Mapping from '{@link PrioritySubType}' to '{@link PrioritySubTypeEntity}'
     * @param prioritySubType
     * @param prioritySubTypeEntity
     */
    public void mapBeanToEntity(PrioritySubType prioritySubType, PrioritySubTypeEntity prioritySubTypeEntity) {
        if(prioritySubType == null) {
            return;
        }

        //--- Generic mapping
        map(prioritySubType, prioritySubTypeEntity);
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

