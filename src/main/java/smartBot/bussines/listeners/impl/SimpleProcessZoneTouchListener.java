package smartBot.bussines.listeners.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.Zone;
import smartBot.bussines.listeners.ZoneListener;
import smartBot.bussines.service.ZoneService;

@Component
public class SimpleProcessZoneTouchListener implements ZoneListener {

    private static final Log logger = LogFactory.getLog(SimpleProcessZoneTouchListener.class);

    @Autowired
    private ZoneService zoneService;

    @Override
    public void onZoneAdd(Scope scope, Zone zone) {
        return;
    }

    @Override
    public void onZoneRemove(Scope scope, Zone zone) {
        return;
    }

    @Override
    public void onZoneTouch(Scope scope, Zone zone) {

        zone.setActivated(true);
        zone.setTradeCount(zone.getTradeCount().intValue() + 1);
        zoneService.create(zone);

        // TODO here can be algorithm for entering pattern
        logger.debug("Zone: " + zone + " was touched");
    }

    @Override
    public void calculate(Scope scope, CurrencyRates currencyRate) {
        return;
    }
}
