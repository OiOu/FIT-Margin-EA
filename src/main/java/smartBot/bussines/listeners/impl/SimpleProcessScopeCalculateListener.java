package smartBot.bussines.listeners.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Priority;
import smartBot.bean.Scope;
import smartBot.bean.Zone;
import smartBot.bussines.listeners.ScopeListener;
import smartBot.bussines.process.OrderProcess;
import smartBot.bussines.process.SimpleZoneProcess;
import smartBot.bussines.service.PriorityService;
import smartBot.bussines.service.ZoneService;
import smartBot.connection.netty.server.common.HostPort;
import smartBot.defines.PriorityConstants;

import javax.annotation.Resource;

@Component
public class SimpleProcessScopeCalculateListener implements ScopeListener {
    private static final Log logger = LogFactory.getLog(SimpleProcessScopeCalculateListener.class);

    @Resource
    private SimpleZoneProcess simpleZoneProcess;

    @Resource
    private ZoneService zoneService;

    @Resource
    private PriorityService priorityService;

    @Resource
    private OrderProcess orderProcess;

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

    @Autowired
    private SimpleProcessZoneTouchListener simpleProcessZoneTouchListener;

    @Override
    public void onScopeCalculateZones(Scope scope, CurrencyRates currencyRate) {
        logger.info("Calculation calculate was started...");

        simpleZoneProcess.setScope(scope);
        simpleZoneProcess.setCurrencyRates(currencyRate);

        if (simpleZoneProcess.getListeners().isEmpty()) {
            simpleZoneProcess.registerZoneListener(simpleProcessZoneAddedListener);
            simpleZoneProcess.registerZoneListener(simpleProcessZoneRemoveListener);
            simpleZoneProcess.registerZoneListener(simpleProcessZoneCalculateListener);
            simpleZoneProcess.registerZoneListener(simpleProcessZoneTouchListener);
        }

        simpleZoneProcess.calculate();

        logger.info("Calculation calculate was finished");
        return;
    }

    @Override
    public void onScopeCheckAndProcess(Scope scope, CurrencyRates currencyRate, HostPort hostPort) {
        if (scope != null && scope.getZones() != null && scope.getZones().size() > 0) {

            Priority priority = priorityService.findByCurrencyIdAndPrioritySubType(scope.getCurrency().getId(), PriorityConstants.LOCAL);

            for (Zone zone : scope.getZones()) {
                // Price has enter pre-zone range
                if (priority != null && !zone.getActivated() && scope.getType() == priority.getType().getType()) {
                    if ((scope.getType().intValue() == Scope.BUILD_FROM_HIGH && currencyRate.getLow() <= zone.getPriceCalcOrderDetectionZone())
                            || (scope.getType().intValue() == Scope.BUILD_FROM_LOW && currencyRate.getHigh() >= zone.getPriceCalcOrderDetectionZone())) {

                        // Set flag that zone was touched
                        zone.setActivated(true);

                        if (zone.getLevel().getTradeAllowed() && zone.getPriceTakeProfit() != null && zone.getPriceStopLoss() != null) {
                            // Create and save into DB new order (only for actual CurrencyRate). For history we will only save in DB
                            orderProcess.openOrder(currencyRate, priority, zone, hostPort);
                        }
                        // Update zone
                        zone = zoneService.save(zone);
                    }
                }

                // Price has enter zone range
                if ((scope.getType().intValue() == Scope.BUILD_FROM_HIGH && !zone.getTouched() && currencyRate.getLow() <= zone.getPriceCalcShift())
                        || (scope.getType().intValue() == Scope.BUILD_FROM_LOW && !zone.getTouched() && currencyRate.getHigh() >= zone.getPriceCalcShift())) {

                    // Update Touched flag
                    simpleZoneProcess.touchZone(scope, zone);
                }
            }
        }
    }
}
