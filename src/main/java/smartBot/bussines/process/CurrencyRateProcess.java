package smartBot.bussines.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bussines.service.PriorityService;
import smartBot.bussines.service.cache.ServerCache;
import smartBot.defines.Strings;

@Transactional
@Component
public class CurrencyRateProcess {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyRateProcess.class);

    @Autowired
    private ServerCache serverCache;

    @Autowired
    private SimpleScopeProcess simpleScopeProcess;

    @Autowired
    private PriorityService priorityService;


    public void calculateZones(Scope scope) {
        // Calculate zones for each scope
        simpleScopeProcess.calculate(scope, scope.getCurrencyRate());
    }

    public void determinePriority(Scope scope, CurrencyRates currentCurrencyRate) {

        simpleScopeProcess.determinePriority(scope, currentCurrencyRate);
    }

    private String prepareZonesMessageResponse(Scope scope) {
        StringBuilder sb = new StringBuilder();

        scope.getZones().stream().forEach(zone -> sb.append(scope.getType()).append(Strings.COMMA)
                .append(zone.getTimestamp()).append(Strings.COMMA)
                .append(zone.getPrice()).append(Strings.COMMA)
                .append(zone.getPriceCalc()).append(Strings.COMMA)
                .append(zone.getPriceCalcShift()));
        logger.info("> message size: " + sb.toString().length());
        return sb.toString();
    }
}
