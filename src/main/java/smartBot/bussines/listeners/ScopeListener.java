package smartBot.bussines.listeners;

import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;

public interface ScopeListener {

    void onScopeAdd(Scope scope);

    void onScopeRemove(Scope scope);

    void onScopeCalculateZones(Scope scope, CurrencyRates currencyRate);
    
    void onScopeDeterminePriorityZones(Scope scope, CurrencyRates currencyRate);
}
