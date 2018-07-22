package smartBot.bussines.service;

import smartBot.bean.MarginRates;

import java.util.Date;
import java.util.List;

public interface MarginRatesService extends Service<MarginRates> {

    List<MarginRates> findAllByShortName(String shortName);

    MarginRates findByShortNameAndDate(String shortName, Date onDate);
    MarginRates findByCurrencyIdAndDate(Integer currencyId, Date onDate);
    void createAll(List<MarginRates> marginRateJsonList);
    void delete(String shortName);
}
