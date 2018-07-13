package smartBot.bussines.service;

import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.Zone;

import java.util.List;

public interface ZoneService extends Service<Zone> {

    List<Zone> calculate(Scope scope, CurrencyRates currencyRate);

    boolean isReCalculationNeeded(CurrencyRates currentCurrencyRate, CurrencyRates lastCurrencyRate);

    Integer getLastId();
}
