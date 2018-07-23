package smartBot.bussines.listeners.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bussines.listeners.CurrencyRatesListener;
import smartBot.bussines.process.PriorityProcess;

import javax.annotation.Resource;

@Component
public class CurrencyRateListener implements CurrencyRatesListener {

    private static final Log logger = LogFactory.getLog(CurrencyRateListener.class);

    @Resource
    private PriorityProcess priorityProcess;

    @Override
    public void onCloseBar(CurrencyRates currencyRate) {
        return;
    }

    @Override
    public void onOpenBar(CurrencyRates currencyRate) {
        return;
    }

    @Override
    public void onZoneTouch(CurrencyRates currencyRate, Scope scope) {
        //priorityProcess.execute(currencyRate);
    }
}
