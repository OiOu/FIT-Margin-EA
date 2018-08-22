package smartBot.bussines.listeners.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bussines.listeners.ScopeListener;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.connection.netty.server.common.HostPort;

@Component
public class SimpleProcessScopeAddedListener implements ScopeListener {
    private static final Log logger = LogFactory.getLog(SimpleProcessScopeAddedListener.class);

    @Autowired
    private ServerCache serverCache;

    @Override
    public void onScopeAdd(Scope scope) {
        serverCache.setScopeToCache(scope);
        logger.info("Added a new Scope with name '" + scope.getName() + "'");
    }

    @Override
    public void onScopeRemove(Scope scope) {
        return;
    }

    @Override
    public void onScopeCalculateZones(Scope scope, CurrencyRates currencyRate) {
        return;
    }

    @Override
    public void onScopeCheckAndProcess(Scope scope, CurrencyRates currencyRate, HostPort hostPort) {
        return;
    }
}
