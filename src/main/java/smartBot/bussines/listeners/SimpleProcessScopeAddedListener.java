package smartBot.bussines.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;

public class SimpleProcessScopeAddedListener implements ScopeListener {
    private static final Log logger = LogFactory.getLog(SimpleProcessScopeAddedListener.class);

    @Override
    public void onScopeAdd(Scope scope) {
        // Print the name of the newly added animal
        logger.info("Added a new Scope with name '" + scope.getName() + "'");
    }

    @Override
    public void onScopeRemove(Scope scope) {
        return;
    }

    @Override
    public void onScopeCalculateZones(Scope scope, CurrencyRates currencyRate) {
        return;
    }
}
