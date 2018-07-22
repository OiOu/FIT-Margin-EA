package smartBot.bussines.listeners.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import smartBot.bean.CurrencyRates;
import smartBot.bussines.listeners.CurrencyRatesListener;

public class CurrencyRateOnCloseListener implements CurrencyRatesListener {

    private static final Log logger = LogFactory.getLog(CurrencyRateOnCloseListener.class);

//    @Resource
//    private PriorityProcess priorityProcess;

    @Override
    public void onCloseBar(CurrencyRates currencyRate) {
   //     priorityProcess.execute(currencyRate);
    }

    @Override
    public void onOpenBar(CurrencyRates currencyRate) {
        return;
    }
}
