package smartBot.bussines.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bussines.listeners.ScopeListener;
import smartBot.bussines.listeners.impl.SimpleProcessScopeAddedListener;
import smartBot.bussines.listeners.impl.SimpleProcessScopeCalculateListener;
import smartBot.bussines.listeners.impl.SimpleProcessScopeRemoveListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Transactional
public class SimpleScopeProcess {

    private List<ScopeListener> listeners = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    private SimpleProcessScopeAddedListener simpleProcessScopeAddedListener;

    @Autowired
    private SimpleProcessScopeRemoveListener simpleProcessScopeRemoveListener;

    @Autowired
    private SimpleProcessScopeCalculateListener simpleProcessScopeCalculateListener;

    public void addScope(Scope scope) {
        // Notify the list of registered listeners
        this.notifyScopeAddListeners(scope);
    }

    public void removeScope(Scope scope) {
        // Notify the list of registered listeners
        this.notifyScopeRemoveListeners(scope);
    }

    public List<ScopeListener> getListeners() {
        return this.listeners;
    }

    public void registerScopeListener(ScopeListener listener) {
        // Add the listener to the list of registered listeners
        this.listeners.add(listener);
    }

    public void unRegisterScopeListener(ScopeListener listener) {
        // Remove the listener from the list of the registered listeners
        this.listeners.remove(listener);
    }

    protected void notifyScopeAddListeners(Scope scope) {
        // Notify each of the listeners in the list of registered listeners
        this.listeners.forEach(listener -> listener.onScopeAdd(scope));
    }

    protected void notifyScopeRemoveListeners(Scope scope) {
        // Notify each of the listeners in the list of registered listeners
        this.listeners.forEach(listener -> listener.onScopeRemove(scope));
    }

    protected void notifyScopeCalculateListeners(Scope scope, CurrencyRates currencyRate) {
        // Notify each of the listeners in the list of registered listeners
        this.listeners.forEach(listener -> listener.onScopeCalculateZones(scope, currencyRate));
    }

    protected void notifyScopeDeterminePriorityListeners(Scope scope, CurrencyRates currencyRate) {
        // Notify each of the listeners in the list of registered listeners
        this.listeners.forEach(listener -> listener.onScopeDeterminePriorityZones(scope, currencyRate));
    }

    public SimpleScopeProcess() {

        if (getListeners().isEmpty()) {
            registerScopeListener(simpleProcessScopeAddedListener);
            registerScopeListener(simpleProcessScopeCalculateListener);
            registerScopeListener(simpleProcessScopeRemoveListener);
        }
    }

    public void calculate(Scope scope, CurrencyRates currencyRate) {
        // Add the scope to the list of scopes
        if (scope != null) {
            // Notify the list of registered listeners
            this.notifyScopeCalculateListeners(scope, currencyRate);
        }
        return;
    }

    public void determinePriority(Scope scope, CurrencyRates currencyRate) {
        // Add the scope to the list of scopes
        if (scope != null) {
            // Notify the list of registered listeners
            this.notifyScopeDeterminePriorityListeners(scope, currencyRate);
        }
        return;
    }

}
