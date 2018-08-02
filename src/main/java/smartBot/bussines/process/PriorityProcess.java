package smartBot.bussines.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.*;
import smartBot.bussines.service.PriorityService;
import smartBot.bussines.service.ScopeService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.connection.netty.server.gateway.NettyBuildingMessageGateway;
import smartBot.defines.PriorityConstants;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class PriorityProcess {
    private static final Log logger = LogFactory.getLog(PriorityProcess.class);

    @Resource
    private ServerCache serverCache;

   @Resource
    private ScopeService scopeService;

    @Resource
    private PriorityService priorityService;

    private NettyBuildingMessageGateway gateway;


    //@Scheduled(cron = "0 2 20 * * MON-FRI", zone = "UTC")
    public Priority determinePriorityAndChange(List<Scope> scopes, CurrencyRates currencyRate) {

        // Get Priority settings (time when to check)
        // TODO LOOP by PrioritySubTypes can be added
        Currency currency = serverCache.getCurrencyFromCache(currencyRate.getCurrency().getId());


        Priority priorityNew = null;

        logger.debug("Determine priority process was started: " + Thread.currentThread().getName() + "...");

        boolean shouldBeSavedToCache = false;
        for (Scope scope : scopes) {
            for (Zone zone : scope.getZones()) {

                if (zone.getLevel().getPrioritySubType() == PriorityConstants.LOCAL || zone.getLevel().getPrioritySubType() == PriorityConstants.GLOBAL) {
                    if (scope.getType().intValue() == Scope.BUILD_FROM_HIGH
                            && (zone.getPriceCalc() - currencyRate.getPointPips() * currencyRate.getPointPrice() * zone.getLevel().getDistance() > currencyRate.getClose())) {

                        priorityNew = priorityService.build(currency, PriorityConstants.SELL, zone.getLevel().getPrioritySubType(), currencyRate.getTimestamp());

                        shouldBeSavedToCache = true;
                    }
                    if (scope.getType().intValue() == Scope.BUILD_FROM_LOW
                            && (zone.getPriceCalc() + currencyRate.getPointPips() * currencyRate.getPointPrice() * zone.getLevel().getDistance() < currencyRate.getClose())) {

                        priorityNew = priorityService.build(currency, PriorityConstants.BUY, zone.getLevel().getPrioritySubType(), currencyRate.getTimestamp());

                        shouldBeSavedToCache = true;
                    }
                }
            }

            if (shouldBeSavedToCache) {
                // Create and save new priority to DB and cache
                priorityNew = priorityService.create(priorityNew);
            }
        }

        logger.debug("Determine priority process was finished");

        return priorityNew;
    }
}
