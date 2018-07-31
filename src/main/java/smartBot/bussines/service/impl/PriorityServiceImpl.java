package smartBot.bussines.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.Priority;
import smartBot.bean.jpa.PriorityEntity;
import smartBot.bussines.service.CurrencyService;
import smartBot.bussines.service.PriorityService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.PriorityServiceMapper;
import smartBot.data.repository.jpa.PriorityJpaRepository;
import smartBot.defines.PriorityConstants;

import java.util.List;

@Component
@Transactional
public class PriorityServiceImpl implements PriorityService{
    private static final Log logger = LogFactory.getLog(PriorityServiceImpl.class);

    @Autowired
    private PriorityJpaRepository priorityJpaRepository;

    @Autowired
    private PriorityServiceMapper priorityServiceMapper;

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private ServerCache serverCache;

    @Override
    public Priority findByCurrencyIdAndPrioritySubType(Integer currencyId, Integer prioritySubType) {

        PriorityEntity priorityEntity = priorityJpaRepository.findByCurrencyIdAndSubType(currencyId, prioritySubType);
        Priority priority = priorityServiceMapper.mapEntityToBean(priorityEntity);

        return priority;
    }

    @Override
    public Priority findById(Integer id) {
        return null;
    }

    @Override
    public List<Priority> findAll() {
        return null;
    }

    @Override
    public Priority create(Priority priority) {
        PriorityEntity priorityEntity = null;
        if (priority != null) {

            priorityEntity = priorityJpaRepository.findByCurrencyIdAndSubType(priority.getCurrency().getId(), PriorityConstants.LOCAL);
            if (priorityEntity == null) {
                priorityEntity = new PriorityEntity();
                priorityServiceMapper.mapBeanToEntity(priority, priorityEntity);
            } else {
                priorityEntity.setType(priority.getType());
                priorityEntity.setStartDate(priority.getStartDate());
            }

            priorityEntity = priorityJpaRepository.save(priorityEntity);
            priority = priorityServiceMapper.mapEntityToBean(priorityEntity);
        }
        return priority;
    }

    @Override
    public void delete(Priority bean) {

    }

    @Transactional
    @Override
    public void delete(Integer id) {
        return;
    }
}
