package smartBot.bussines.listeners;

import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;

public interface CurrencyRatesListener {

    void onCloseBar(CurrencyRates currencyRate);

    void onOpenBar(CurrencyRates currencyRate);

    void onZoneTouch(CurrencyRates currencyRate, Scope scope);
}
