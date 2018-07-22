package smartBot.bussines.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bussines.service.CurrencyRatesService;
import smartBot.bussines.service.ZoneService;

@Component
@Transactional
public class SimpleProcess {

    private static final Log logger = LogFactory.getLog(SimpleProcess.class);

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private CurrencyRatesService currencyRatesService;

    public void calculate(Scope scope, CurrencyRates currencyRate) {

        scope.setZones(zoneService.calculate(scope, currencyRate));

        currencyRate = currencyRatesService.save(scope, currencyRate);

        logger.info("");
    }
}
