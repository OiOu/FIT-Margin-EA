package smartBot.bussines.listeners.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bussines.listeners.ScopeListener;
import smartBot.bussines.process.SimpleZoneProcess;

import javax.annotation.Resource;

@Component
public class SimpleProcessScopeCalculateListener implements ScopeListener {
    private static final Log logger = LogFactory.getLog(SimpleProcessScopeCalculateListener.class);

    @Resource
    private SimpleZoneProcess simpleZoneProcess;

    @Override
    public void onScopeAdd(Scope scope) {
        return;
    }

    @Override
    public void onScopeRemove(Scope scope) {
        return;
    }

    @Override
    public void onScopeCalculateZones(Scope scope, CurrencyRates currencyRate) {
        logger.info("Calculation calculate was started...");

        simpleZoneProcess.setScope(scope);
        simpleZoneProcess.setCurrencyRates(currencyRate);

        simpleZoneProcess.calculate();

        logger.info("Calculation calculate was finished");
        return;
    }

    @Override
    public void onScopeDeterminePriorityZones(Scope scope, CurrencyRates currencyRate) {


    }
}
