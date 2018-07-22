package smartBot.bussines.listeners.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SimpleProcessZoneAddedListener simpleProcessZoneAddedListener;

    @Autowired
    private SimpleProcessZoneRemoveListener simpleProcessZoneRemoveListener;

    @Autowired
    private SimpleProcessZoneCalculateListener simpleProcessZoneCalculateListener;

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
        logger.info("Calculation process was started...");

        simpleZoneProcess.setScope(scope);
        simpleZoneProcess.setCurrencyRates(currencyRate);

        if (simpleZoneProcess.getListeners().isEmpty()) {
            simpleZoneProcess.registerZoneListener(simpleProcessZoneAddedListener);
            simpleZoneProcess.registerZoneListener(simpleProcessZoneRemoveListener);
            simpleZoneProcess.registerZoneListener(simpleProcessZoneCalculateListener);
        }

        simpleZoneProcess.process();

        logger.info("Calculation process was finished");
        return;
    }
}
