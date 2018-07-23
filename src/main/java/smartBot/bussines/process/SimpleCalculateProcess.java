package smartBot.bussines.process;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smartBot.bean.CurrencyRates;
import smartBot.bean.Scope;
import smartBot.bean.Zone;
import smartBot.bussines.service.CurrencyRatesService;
import smartBot.bussines.service.ZoneService;

import java.util.List;

@Component
@Transactional
public class SimpleCalculateProcess {

    private static final Log logger = LogFactory.getLog(SimpleCalculateProcess.class);

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private CurrencyRatesService currencyRatesService;

    public void calculate(Scope scope, CurrencyRates currencyRate) {

        List<Zone> zoneList = zoneService.calculate(scope, currencyRate);

        scope.setZones(zoneList);

        logger.info("");
    }
}
