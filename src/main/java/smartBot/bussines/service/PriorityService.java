package smartBot.bussines.service;

import smartBot.bean.Priority;

public interface PriorityService extends Service<Priority>{

    Priority findByCurrencyIdAndPrioritySubType(Integer currencyId, Integer prioritySubType);
}
