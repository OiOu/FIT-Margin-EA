package smartBot.bussines.listeners.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.Zone;
import smartBot.bussines.listeners.ZoneListener;
import smartBot.bussines.process.SimpleCalculateProcess;

import javax.annotation.Resource;

@Component
public class SimpleProcessZoneCalculateListener implements ZoneListener {

    private static final Log logger = LogFactory.getLog(SimpleProcessZoneCalculateListener.class);

    @Resource
    private SimpleCalculateProcess simpleCalculateProcess;

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

        simpleCalculateProcess.calculate(scope, currencyRate);

        return;
    }
}
