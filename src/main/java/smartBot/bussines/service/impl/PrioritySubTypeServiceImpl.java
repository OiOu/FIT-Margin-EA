package smartBot.bussines.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.PrioritySubType;
import smartBot.bean.jpa.PrioritySubTypeEntity;
import smartBot.bussines.service.PrioritySubTypeService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.PrioritySubTypeServiceMapper;
import smartBot.data.repository.jpa.PrioritySubTypeJpaRepository;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class PrioritySubTypeServiceImpl implements PrioritySubTypeService {
    private static final Log logger = LogFactory.getLog(PrioritySubTypeServiceImpl.class);

    @Resource
    private PrioritySubTypeJpaRepository prioritySubTypeJpaRepository;

    @Resource
    private PrioritySubTypeServiceMapper prioritySubTypeServiceMapper;

    @Resource
    private ServerCache serverCache;

    @Override
    public PrioritySubType getSubtype(Integer subtype) {
        PrioritySubType prioritySubType = serverCache.getPrioritySubTypeFromCache(subtype);
        if (prioritySubType == null) {
            PrioritySubTypeEntity prioritySubTypeEntity = prioritySubTypeJpaRepository.getBySubtype(subtype);
            if (prioritySubTypeEntity != null) {
                prioritySubType = prioritySubTypeServiceMapper.mapEntityToBean(prioritySubTypeEntity);
                serverCache.setPrioritySubType(prioritySubType);
            }
        }

        return prioritySubType;
    }

    @Override
    public PrioritySubType findById(Integer id) {
        return null;
    }

    @Override
    public List<PrioritySubType> findAll() {
        return null;
    }

    @Override
    public PrioritySubType save(PrioritySubType bean) {
        return null;
    }

    @Override
    public void delete(PrioritySubType bean) {

    }

    @Override
    public void delete(Integer id) {

    }
}
