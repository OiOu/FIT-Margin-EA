/*
package smartBot.bussines.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Zone;
import smartBot.bussines.listeners.ZoneListener;
import smartBot.bussines.listeners.impl.SimpleProcessZoneTouchListener;

import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class PriorityProcess {
    private static final Log logger = LogFactory.getLog(PriorityProcess.class);

    private CurrencyRates currencyRates;

    //private List<Zone> zones = new ArrayList<>();
    private List<ZoneListener> listeners = new ArrayList<>();

    public PriorityProcess() {
        // Register a listener to be notified when an zone is added
        this.registerZoneListener(new SimpleProcessZoneTouchListener());
    }

    public void touchZone(Zone zone) {
        // Notify the list of registered listeners
        this.notifyZoneTouchListeners(zone);
    }

    public void registerZoneListener(ZoneListener listener) {
        // Add the listener to the list of registered listeners
        this.listeners.add(listener);
    }

    public void unRegisterZoneListener(ZoneListener listener) {
        // Remove the listener from the list of the registered listeners
        this.listeners.remove(listener);
    }

    protected void notifyZoneTouchListeners(Zone zone) {
        // Notify each of the listeners in the list of registered listeners
        this.listeners.forEach(listener -> listener.onZoneAdd(this.scope, zone));
    }

    public void process() {
        if (this.scope != null) {
            // Notify the list of registered listeners
            this.notifyZoneListeners(this.scope, this.currencyRates);
        }
        return;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    public CurrencyRates getCurrencyRates() {
        return currencyRates;
    }

    public void setCurrencyRates(CurrencyRates currencyRates) {
        this.currencyRates = currencyRates;
    }

}
*/
