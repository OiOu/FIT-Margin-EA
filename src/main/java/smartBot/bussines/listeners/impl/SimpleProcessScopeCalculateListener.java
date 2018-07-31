package smartBot.bussines.listeners.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.Zone;
import smartBot.bussines.listeners.ScopeListener;
import smartBot.bussines.process.SimpleZoneProcess;

import javax.annotation.Resource;

@Component
public class SimpleProcessScopeCalculateListener implements ScopeListener {
    private static final Log logger = LogFactory.getLog(SimpleProcessScopeCalculateListener.class);

    @Resource
    private SimpleZoneProcess simpleZoneProcess;

    @Override
    public void onScopeAdd(Scope scope) {
        return;
    }

    @Override
    public void onScopeRemove(Scope scope) {
        return;
    }

    @Autowired
    private SimpleProcessZoneAddedListener simpleProcessZoneAddedListener;

    @Autowired
    private SimpleProcessZoneRemoveListener simpleProcessZoneRemoveListener;

    @Autowired
    private SimpleProcessZoneCalculateListener simpleProcessZoneCalculateListener;

    @Override
    public void onScopeCalculateZones(Scope scope, CurrencyRates currencyRate) {
        logger.info("Calculation calculate was started...");

        simpleZoneProcess.setScope(scope);
        simpleZoneProcess.setCurrencyRates(currencyRate);

        if (simpleZoneProcess.getListeners().isEmpty()) {
            simpleZoneProcess.registerZoneListener(simpleProcessZoneAddedListener);
            simpleZoneProcess.registerZoneListener(simpleProcessZoneRemoveListener);
            simpleZoneProcess.registerZoneListener(simpleProcessZoneCalculateListener);
        }

        simpleZoneProcess.calculate();

        logger.info("Calculation calculate was finished");
        return;
    }

    @Override
    public void onScopeTouchZones(Scope scope, CurrencyRates currencyRate) {
        if (scope != null && scope.getZones() != null && scope.getZones().size() > 0) {

            for (Zone zone: scope.getZones()) {
                if (!zone.getActivated()
                        && (scope.getType().intValue() == Scope.BUILD_FROM_HIGH && currencyRate.getLow() <= zone.getPriceCalcShift()
                        || scope.getType().intValue() == Scope.BUILD_FROM_LOW && currencyRate.getHigh() >= zone.getPriceCalcShift())) {

                    // Update Activated and Trade count columns for zone
                    // Activated will be reset after re-calculation
                    simpleZoneProcess.touchZone(scope, zone);
                }
            }
        }
    }
}
