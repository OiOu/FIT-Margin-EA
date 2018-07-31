package smartBot.bussines.service;

import org.joda.time.DateTime;
import smartBot.bean.MarginRates;

import java.util.List;

public interface MarginRatesService extends Service<MarginRates> {

    List<MarginRates> findAllByShortName(String shortName);

    MarginRates findByShortNameAndDate(String shortName, DateTime onDate);
    MarginRates findByCurrencyIdAndDate(Integer currencyId, DateTime onDate);
    void createAll(List<MarginRates> marginRateJsonList);
    void delete(String shortName);
}
