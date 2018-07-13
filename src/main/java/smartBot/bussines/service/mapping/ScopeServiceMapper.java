package smartBot.bussines.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import smartBot.bean.Scope;
import smartBot.bean.jpa.ScopeEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScopeServiceMapper extends AbstractServiceMapper {

    /**
     * ModelMapper : bean to bean mapping library.
     */
    private ModelMapper modelMapper;

    /**
     * Constructor.
     */
    public ScopeServiceMapper() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Mapping from '{@link ScopeEntity}' to '{@link Scope}'
     * @param scopeEntity
     */
    public Scope mapEntityToBean(ScopeEntity scopeEntity) {
        if(scopeEntity == null) {
            return null;
        }
        //--- Generic mapping
        Scope scope = map(scopeEntity, Scope.class);
        return scope;
    }

    /**
     * Mapping from '{@link List<ScopeEntity>}' to '{@link List<Scope>}'
     * @param scopeEntities
     */
    public List<Scope> mapEntitiesToBeans(List<ScopeEntity> scopeEntities) {
        if(scopeEntities == null) {
            return null;
        }
        //--- Generic mapping
        List<Scope> scopes = new ArrayList<>();
        for (ScopeEntity scopeEntity : scopeEntities) {
            scopes.add(mapEntityToBean(scopeEntity));
        }
        return scopes;
    }


    /**
     * Mapping from '{@link Scope}' to '{@link ScopeEntity}'
     * @param scope
     * @param scopeEntity
     */
    public void mapBeanToEntity(Scope scope, ScopeEntity scopeEntity) {
        if(scope == null) {
            return;
        }

        //--- Generic mapping
        map(scope, scopeEntity);
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

