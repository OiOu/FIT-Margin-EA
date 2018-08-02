package smartBot.bussines.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.PriorityType;
import smartBot.bean.jpa.PriorityTypeEntity;
import smartBot.bussines.service.PriorityTypeService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.PriorityTypeServiceMapper;
import smartBot.data.repository.jpa.PriorityTypeJpaRepository;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class PriorityTypeServiceImpl implements PriorityTypeService {
    private static final Log logger = LogFactory.getLog(PriorityTypeServiceImpl.class);

    @Resource
    private PriorityTypeJpaRepository priorityTypeJpaRepository;

    @Resource
    private PriorityTypeServiceMapper priorityTypeServiceMapper;

    @Resource
    private ServerCache serverCache;

    @Override
    public PriorityType getType(Integer type) {
        PriorityType priorityType = serverCache.getPriorityTypeFromCache(type);
        if (priorityType == null) {
            PriorityTypeEntity priorityTypeEntity = priorityTypeJpaRepository.getByType(type);
            if (priorityTypeEntity != null) {
                priorityType = priorityTypeServiceMapper.mapEntityToBean(priorityTypeEntity);
                serverCache.setPriorityTypeToCache(priorityType);
            }
        }

        return priorityType;
    }

    @Override
    public PriorityType findById(Integer id) {
        return null;
    }

    @Override
    public List<PriorityType> findAll() {
        return null;
    }

    @Override
    public PriorityType create(PriorityType bean) {
        return null;
    }

    @Override
    public void delete(PriorityType bean) {

    }

    @Override
    public void delete(Integer id) {

    }
}
