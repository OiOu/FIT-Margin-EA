package smartBot.bussines.service;

import org.joda.time.DateTime;
import smartBot.bean.Currency;
import smartBot.bean.Priority;

public interface PriorityService extends Service<Priority>{

    Priority findByCurrencyIdAndPrioritySubType(Integer currencyId, Integer prioritySubType);

    Priority build (Currency currency, Integer priorityType, Integer prioritySubType, DateTime onDate);
}
