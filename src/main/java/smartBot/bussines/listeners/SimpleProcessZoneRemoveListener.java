package smartBot.bussines.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.Zone;

public class SimpleProcessZoneRemoveListener implements ZoneListener {
    private static final Log logger = LogFactory.getLog(SimpleProcessZoneRemoveListener.class);

    @Override
    public void onZoneAdd(Scope scope, Zone zone) {
        return;
    }

    @Override
    public void onZoneRemove(Scope scope, Zone zone) {
        // Print the name of the newly added animal
        logger.info("Remove Zone with name '" + zone.getName() + "'");
    }

    @Override
    public void calculate(Scope scope, CurrencyRates currencyRate) {
        return;
    }
}
