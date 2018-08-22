package smartBot.bussines.process;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.Zone;
import smartBot.bussines.listeners.ZoneListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Transactional
public class SimpleZoneProcess {

    private Scope scope;
    private CurrencyRates currencyRates;

    private List<ZoneListener> listeners = Collections.synchronizedList(new ArrayList<>());

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

    public List<ZoneListener> getListeners() {
        return this.listeners;
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

    protected void notifyZoneCalculateListeners(Scope scope, CurrencyRates currencyRate) {
        // Notify each of the listeners in the list of registered listeners
        for (ZoneListener listener : this.listeners) {
            listener.calculate(scope, currencyRate);
        }
    }

    protected void notifyZoneTouchListeners(Scope scope, Zone zone) {
        // Notify each of the listeners in the list of registered listeners
        for (ZoneListener listener : this.listeners) {
            listener.onZoneTouch(scope, zone);
        }
    }

    public void calculate() {
        if (this.scope != null && this.currencyRates != null) {
            // Notify the list of registered listeners
            this.notifyZoneCalculateListeners(this.scope, this.currencyRates);
        }
        return;
    }

    public void touchZone(Scope scope, Zone zone) {
        // Notify the list of registered listeners
        this.notifyZoneTouchListeners(scope, zone);
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
