package smartBot.bussines.service;

import org.joda.time.DateTime;
import smartBot.bean.MarginRates;

import java.util.List;

public interface MarginRatesService extends Service<MarginRates> {

    List<MarginRates> findAllByShortName(String shortName);

    MarginRates findByShortNameOnDate(String shortName, DateTime onDate);
    MarginRates findByCurrencyIdOnDate(Integer currencyId, DateTime onDate);
    List<MarginRates> createAll(List<MarginRates> marginRateJsonList);
    void delete(String shortName);
}
