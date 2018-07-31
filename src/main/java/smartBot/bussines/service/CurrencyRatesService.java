package smartBot.bussines.service;

import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;

import java.util.List;

public interface CurrencyRatesService extends Service<CurrencyRates> {

    List<CurrencyRates> findAllByShortName(String shortName);

    CurrencyRates findLastByScope(Scope scope);

    CurrencyRates save(CurrencyRates currencyRate);

    void delete(String shortName);

    void merge(CurrencyRates currentCurrencyRate, CurrencyRates lastCurrencyRate);

    CurrencyRates findLastByCurrencyIdAndScopeType(Integer currencyId, Integer scopeType);
}
