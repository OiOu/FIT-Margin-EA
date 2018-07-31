package smartBot.bussines.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bussines.service.PriorityService;
import smartBot.bussines.service.cache.ServerCache;

import javax.annotation.Resource;

@Transactional
@Component
public class CurrencyRateProcess {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyRateProcess.class);

    @Resource
    private ServerCache serverCache;

    @Resource
    private SimpleScopeProcess simpleScopeProcess;

    @Resource
    private PriorityService priorityService;


    public void calculateZones(Scope scope) {
        // Calculate zones for each scope
        simpleScopeProcess.calculate(scope, scope.getCurrencyRate());
    }

    public void touchZone(Scope scope, CurrencyRates currentCurrencyRate) {
        // Determine zones that was touched by price
        simpleScopeProcess.touchZone(scope, currentCurrencyRate);
    }


}
