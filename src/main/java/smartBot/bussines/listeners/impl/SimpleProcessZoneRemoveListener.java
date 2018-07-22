package smartBot.bussines.listeners.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.Zone;
import smartBot.bussines.listeners.ZoneListener;

@Component
public class SimpleProcessZoneRemoveListener implements ZoneListener {
    private static final Log logger = LogFactory.getLog(SimpleProcessZoneRemoveListener.class);

    @Override
    public void onZoneAdd(Scope scope, Zone zone) {
        return;
    }

    @Override
    public void onZoneRemove(Scope scope, Zone zone) {
        logger.info("Remove Zone with name '" + zone.getName() + "'");
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
