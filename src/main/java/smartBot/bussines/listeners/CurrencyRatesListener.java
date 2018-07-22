package smartBot.bussines.listeners;

import smartBot.bean.CurrencyRates;

public interface CurrencyRatesListener {

    void onCloseBar(CurrencyRates currencyRate);

    void onOpenBar(CurrencyRates currencyRate);
}
