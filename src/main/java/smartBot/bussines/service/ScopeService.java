package smartBot.bussines.service;

import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;

import java.util.List;

public interface ScopeService extends Service<Scope>{

    Scope findByCurrencyIdAndScopeType(Integer currencyId, Integer scopeType);

    Integer getLastId();

    Integer getLastId(Integer currencyId, Integer scopeType);

    Scope create(CurrencyRates currencyRate, Integer type);

    Scope save(Scope scope);

    void delete(Scope scope);

    List<Scope> saveAll(List<Scope> scopes);
}
