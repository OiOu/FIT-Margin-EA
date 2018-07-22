package smartBot.bussines.service;

import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;

import java.util.List;

public interface CurrencyRatesService extends Service<CurrencyRates> {

    List<CurrencyRates> findAllByShortName(String shortName);

    CurrencyRates save(Scope scope, CurrencyRates currencyRate);

    void delete(String shortName);
}
