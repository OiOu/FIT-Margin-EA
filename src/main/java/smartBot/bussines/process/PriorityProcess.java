package smartBot.bussines.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.*;
import smartBot.bussines.service.CurrencyService;
import smartBot.bussines.service.PriorityService;
import smartBot.defines.PriorityConstants;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class PriorityProcess {
    private static final Log logger = LogFactory.getLog(PriorityProcess.class);

    @Resource
    private CurrencyService currencyService;

    @Resource
    private PriorityService priorityService;

    //@Scheduled(cron = "0 2 20 * * MON-FRI", zone = "UTC")
    public Priority determinePriority(List<Scope> scopes, CurrencyRates currencyRate) {

        // TODO LOOP by PrioritySubTypes can be added
        Priority priorityNew = null;
        Priority priorityLast = null;

        Currency currency = currencyService.findById(currencyRate.getCurrency().getId());

        if (currencyRate.getTimestamp().isBefore(new DateTime())) {
            DateTime dateTime1 = new DateTime(currencyRate.getTimestamp()).withTime(22, 59, 0, 0);
            DateTime dateTime2 = new DateTime(currencyRate.getTimestamp()).withTime(23, 1, 0, 0);

            priorityLast = priorityService.findByCurrencyIdAndPrioritySubType(currency.getId(), PriorityConstants.LOCAL);

            if (currencyRate.getTimestamp().isAfter(dateTime1) && currencyRate.getTimestamp().isBefore(dateTime2)
                    && (priorityLast == null || priorityLast.getStartDate().toDate().before(dateTime1.toDate()))) {

                logger.debug("Determine priority process was started: " + Thread.currentThread().getName() + "...");

                for (Scope scope : scopes) {
                    for (Zone zone : scope.getZones()) {
                        if (!zone.getLevel().getEnable()) { continue; }

                        if (zone.getLevel().getPrioritySubType() == PriorityConstants.LOCAL) {
                            if (scope.getType().intValue() == Scope.BUILD_FROM_HIGH
                                    && (priorityLast == null || priorityLast.getType().getType() == PriorityConstants.BUY)
                                    && (zone.getPriceCalc() - currencyRate.getPointPips() * currencyRate.getPointPrice() * zone.getLevel().getPriorityDistance() > currencyRate.getClose())) {

                                priorityNew = priorityService.build(currency, PriorityConstants.SELL, zone.getLevel().getPrioritySubType(), currencyRate.getTimestamp());
                            }
                            if (scope.getType().intValue() == Scope.BUILD_FROM_LOW
                                    && (priorityLast == null || priorityLast.getType().getType() == PriorityConstants.SELL)
                                    && (zone.getPriceCalc() + currencyRate.getPointPips() * currencyRate.getPointPrice() * zone.getLevel().getPriorityDistance() < currencyRate.getClose())) {

                                priorityNew = priorityService.build(currency, PriorityConstants.BUY, zone.getLevel().getPrioritySubType(), currencyRate.getTimestamp());
                            }
                        }
                    }
                }

                logger.debug("Determine priority process was finished");
            }
        }

        if (priorityNew != null && (priorityLast == null || priorityLast.getType().getType() != priorityNew.getType().getType())) {
            return priorityNew;
        } else {
            return null;
        }
    }
}
