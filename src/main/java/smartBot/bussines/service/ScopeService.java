package smartBot.bussines.service;

import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.jpa.ScopeEntity;

import java.util.Date;

public interface ScopeService extends Service<ScopeEntity>{

    Scope findByCurrencyIdAndScopeTypeAndOnDate(Integer currencyId, Integer scopeType, Date onDate);

    Integer getLastId();

    Integer getLastId(Integer currencyId, Integer scopeType);

    Scope create(CurrencyRates currencyRate, Integer type);

    void save(Scope scope);
}
