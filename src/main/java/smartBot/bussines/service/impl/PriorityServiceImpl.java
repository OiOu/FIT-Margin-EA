package smartBot.bussines.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.Currency;
import smartBot.bean.Priority;
import smartBot.bussines.service.CurrencyService;
import smartBot.bussines.service.PriorityService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.bussines.service.mapping.PriorityServiceMapper;
import smartBot.data.repository.jpa.PriorityJpaRepository;

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
    public Priority findByCurrencyId(Integer currencyId) {

        Priority priority = serverCache.getPriorityFromCache(currencyId);

        if (priority == null) {
            Currency currency = currencyService.findById(currencyId);

        }
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
    public Priority create(Priority bean) {
        return null;
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
