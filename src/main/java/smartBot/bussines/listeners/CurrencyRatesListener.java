package smartBot.bussines.listeners;

import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;

public interface CurrencyRatesListener {

    void onCloseBar(Scope scope, CurrencyRates currencyRate);

    void onOpenBar(Scope scope, CurrencyRates currencyRate);

}
