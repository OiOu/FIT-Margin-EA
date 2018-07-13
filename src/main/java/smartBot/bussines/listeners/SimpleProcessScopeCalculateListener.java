package smartBot.bussines.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bussines.process.SimpleZoneProcess;

public class SimpleProcessScopeCalculateListener implements ScopeListener {
    private static final Log logger = LogFactory.getLog(SimpleProcessScopeCalculateListener.class);

    // Create the simple process instance to store scope
    private SimpleZoneProcess simpleZoneProcess = new SimpleZoneProcess();

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
        simpleZoneProcess.process();

        logger.info("Calculation process was finished");
        return;
    }
}
