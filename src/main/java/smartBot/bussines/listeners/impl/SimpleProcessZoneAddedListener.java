package smartBot.bussines.listeners.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.Zone;
import smartBot.bussines.listeners.ZoneListener;

@Component
public class SimpleProcessZoneAddedListener implements ZoneListener {
    private static final Log logger = LogFactory.getLog(SimpleProcessZoneAddedListener.class);

    @Override
    public void onZoneAdd(Scope scope, Zone zone) {
        // Print the name of the newly added animal
        logger.info("Added a new Zone with name '" + zone.getName() + "'");
    }

    @Override
    public void onZoneRemove(Scope scope, Zone zone) {
        return;
    }

    @Override
    public void onZoneTouch(CurrencyRates currencyRate) {
        return;
    }

    @Override
    public void calculate(Scope scope, CurrencyRates currencyRate) {
        return;
    }
}
