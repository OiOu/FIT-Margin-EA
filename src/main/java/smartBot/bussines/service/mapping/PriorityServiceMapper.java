package smartBot.bussines.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import smartBot.bean.Priority;
import smartBot.bean.jpa.PriorityEntity;

@Component
public class PriorityServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public PriorityServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from '{@link PriorityEntity}' to '{@link Priority}'
     * @param priorityEntity
     */
    public Priority mapEntityToBean(PriorityEntity priorityEntity) {
        if(priorityEntity == null) {
            return null;
        }
        //--- Generic mapping
        Priority priority = map(priorityEntity, Priority.class);
        return priority;
    }

    /**
     * Mapping from '{@link Priority}' to '{@link PriorityEntity}'
     * @param priority
     * @param priorityEntity
     */
    public void mapBeanToEntity(Priority priority, PriorityEntity priorityEntity) {
        if(priority == null) {
            return;
        }

        //--- Generic mapping
        map(priority, priorityEntity);
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

