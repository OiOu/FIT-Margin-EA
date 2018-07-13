package smartBot.bussines.process;

import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bussines.listeners.ScopeListener;
import smartBot.bussines.listeners.SimpleProcessScopeAddedListener;
import smartBot.bussines.listeners.SimpleProcessScopeCalculateListener;
import smartBot.bussines.listeners.SimpleProcessScopeRemoveListener;

import java.util.ArrayList;
import java.util.List;

public class SimpleScopeProcess {

    private List<Scope> scopes = new ArrayList<>();
    private List<ScopeListener> listeners = new ArrayList<>();

    public SimpleScopeProcess() {
        // Register a listener to be notified when an scope is added
        this.registerScopeListener(new SimpleProcessScopeAddedListener());
        this.registerScopeListener(new SimpleProcessScopeRemoveListener());
        this.registerScopeListener(new SimpleProcessScopeCalculateListener());
    }

    public void addScope(Scope scope) {
        // Add the scope to the list of scopes
        if (!this.scopes.contains(scope)) {
            this.scopes.add(scope);

            // Notify the list of registered listeners
            this.notifyScopeAddListeners(scope);
        }
    }

    public void removeScope(Scope scope) {
        // Add the scope to the list of scopes
        this.scopes.remove(scope);

        // Notify the list of registered listeners
        this.notifyScopeRemoveListeners(scope);
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

    protected void notifyScopeUpdateZonesListeners(Scope scope, CurrencyRates currencyRate) {
        // Notify each of the listeners in the list of registered listeners
        this.listeners.forEach(listener -> listener.onScopeCalculateZones(scope, currencyRate));
    }

    public void process(Scope scope, CurrencyRates currencyRate) {
        // Add the scope to the list of scopes
        if (scope != null) {
            // Notify the list of registered listeners
            this.notifyScopeUpdateZonesListeners(scope, currencyRate);
        }

        return;
    }

}
