package smartBot.bussines.listeners;

import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.connection.netty.server.common.HostPort;

public interface ScopeListener {

    void onScopeAdd(Scope scope);

    void onScopeRemove(Scope scope);

    void onScopeCalculateZones(Scope scope, CurrencyRates currencyRate);

    void onScopeCheckAndProcess(Scope scope, CurrencyRates currencyRate, HostPort hostPort);
}
