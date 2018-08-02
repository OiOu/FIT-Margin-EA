package smartBot.bussines.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.Currency;
import smartBot.bean.Priority;
import smartBot.bean.PrioritySubType;
import smartBot.bean.PriorityType;
import smartBot.bean.jpa.PriorityEntity;
import smartBot.bean.jpa.PrioritySubTypeEntity;
import smartBot.bean.jpa.PriorityTypeEntity;
import smartBot.bussines.service.CurrencyService;
import smartBot.bussines.service.PriorityService;
import smartBot.bussines.service.PrioritySubTypeService;
import smartBot.bussines.service.PriorityTypeService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.PriorityServiceMapper;
import smartBot.bussines.service.mapping.PrioritySubTypeServiceMapper;
import smartBot.bussines.service.mapping.PriorityTypeServiceMapper;
import smartBot.data.repository.jpa.PriorityJpaRepository;
import smartBot.defines.PriorityConstants;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class PriorityServiceImpl implements PriorityService{
    private static final Log logger = LogFactory.getLog(PriorityServiceImpl.class);

    @Resource
    private PriorityJpaRepository priorityJpaRepository;

    @Resource
    private PriorityServiceMapper priorityServiceMapper;

    @Resource
    private CurrencyService currencyService;

    @Resource
    private PriorityTypeService priorityTypeService;

    @Resource
    private PrioritySubTypeService prioritySubTypeService;

    @Resource
    private PriorityTypeServiceMapper priorityTypeServiceMapper;

    @Resource
    private PrioritySubTypeServiceMapper prioritySubTypeServiceMapper;

    @Resource
    private ServerCache serverCache;

    @Override
    public Priority findByCurrencyIdAndPrioritySubType(Integer currencyId, Integer prioritySubType) {

        PriorityEntity priorityEntity = priorityJpaRepository.findByCurrencyIdAndSubType(currencyId, prioritySubType);
        Priority priority = priorityServiceMapper.mapEntityToBean(priorityEntity);

        return priority;
    }

    @Override
    public Priority build(Currency currency, Integer priorityType, Integer prioritySubType, DateTime onDate) {
        Priority priority = new Priority();
        priority.setCurrency(currency);

        PriorityType pt = priorityTypeService.getType(priorityType);
        PrioritySubType pst = prioritySubTypeService.getSubtype(prioritySubType);

        priority.setType(pt);
        priority.setSubtype(pst);
        priority.setStartDate(onDate);

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
                PriorityTypeEntity pte = new PriorityTypeEntity();
                priorityTypeServiceMapper.mapBeanToEntity(priority.getType(), pte);

                PrioritySubTypeEntity pste = new PrioritySubTypeEntity();
                prioritySubTypeServiceMapper.mapBeanToEntity(priority.getSubtype(), pste);

                priorityEntity.setType(pte);
                priorityEntity.setSubtype(pste);
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
