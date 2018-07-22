package smartBot.bussines.listeners.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.Zone;
import smartBot.bussines.listeners.ZoneListener;
import smartBot.bussines.process.SimpleProcess;

import javax.annotation.Resource;

@Component
public class SimpleProcessZoneCalculateListener implements ZoneListener {

    private static final Log logger = LogFactory.getLog(SimpleProcessZoneCalculateListener.class);

    @Resource
    private SimpleProcess simpleProcess;

    @Override
    public void onZoneAdd(Scope scope, Zone zone) {
        return;
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
        logger.info("Calculation process was started...");

        simpleProcess.calculate(scope, currencyRate);

        logger.info("Calculation process was finished");
        return;
    }
}