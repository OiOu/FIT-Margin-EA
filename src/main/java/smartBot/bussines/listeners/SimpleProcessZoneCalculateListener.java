package smartBot.bussines.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.Zone;
import smartBot.bussines.service.ZoneService;

public class SimpleProcessZoneCalculateListener implements ZoneListener {

    private static final Log logger = LogFactory.getLog(SimpleProcessZoneCalculateListener.class);

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
    public void calculate(Scope scope, CurrencyRates currencyRate) {
        logger.info("Calculation process was started...");

        zoneService.calculate(scope, currencyRate);

        logger.info("Calculation process was finished");
        return;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public ZoneService getZoneService() {
        return zoneService;
    }

    public void setZoneService(ZoneService zoneService) {
        this.zoneService = zoneService;
    }
}
