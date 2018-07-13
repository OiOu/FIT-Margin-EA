package smartBot.bussines.process;

import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.Zone;
import smartBot.bussines.listeners.SimpleProcessZoneAddedListener;
import smartBot.bussines.listeners.SimpleProcessZoneCalculateListener;
import smartBot.bussines.listeners.SimpleProcessZoneRemoveListener;
import smartBot.bussines.listeners.ZoneListener;

import java.util.ArrayList;
import java.util.List;

public class SimpleZoneProcess {

    private Scope scope;
    private CurrencyRates currencyRates;

    //private List<Zone> zones = new ArrayList<>();
    private List<ZoneListener> listeners = new ArrayList<>();

    public SimpleZoneProcess() {
        // Register a listener to be notified when an zone is added
        this.registerZoneListener(new SimpleProcessZoneAddedListener());
        this.registerZoneListener(new SimpleProcessZoneRemoveListener());
        this.registerZoneListener(new SimpleProcessZoneCalculateListener());
    }

    public void addZones(List<Zone> zones) {
        // Add the zone to the list of zones
        if (zones != null && zones.size() > 0) {
            for (Zone zone: zones) {
                this.addZone(zone);
            }
        }
    }

    public void addZone(Zone zone) {
        // Add the zone to the list of zones
        if (!this.scope.getZones().contains(zone)) {
            this.scope.getZones().add(zone);
        } else {

            // Notify the list of registered listeners
            this.notifyZoneAddListeners(zone);
        }
    }

    public void removeZone(Zone zone) {
        // Add the zone to the list of zones
        this.scope.getZones().remove(zone);

        // Notify the list of registered listeners
        this.notifyZoneRemoveListeners(zone);
    }

    public void registerZoneListener(ZoneListener listener) {
        // Add the listener to the list of registered listeners
        this.listeners.add(listener);
    }

    public void unRegisterZoneListener(ZoneListener listener) {
        // Remove the listener from the list of the registered listeners
        this.listeners.remove(listener);
    }

    protected void notifyZoneAddListeners(Zone zone) {
        // Notify each of the listeners in the list of registered listeners
        this.listeners.forEach(listener -> listener.onZoneAdd(this.scope, zone));
    }

    protected void notifyZoneRemoveListeners(Zone zone) {
        // Notify each of the listeners in the list of registered listeners
        this.listeners.forEach(listener -> listener.onZoneRemove(this.scope, zone));
    }

    protected void notifyZoneUpdateZonesListeners(Scope scope, CurrencyRates currencyRate) {
        // Notify each of the listeners in the list of registered listeners
        this.listeners.forEach(listener -> listener.calculate(scope, currencyRate));
    }

    public void process() {
        if (this.scope != null) {
            // Notify the list of registered listeners
            this.notifyZoneUpdateZonesListeners(this.scope, this.currencyRates);
        }
        return;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public CurrencyRates getCurrencyRates() {
        return currencyRates;
    }

    public void setCurrencyRates(CurrencyRates currencyRates) {
        this.currencyRates = currencyRates;
    }
}
