package smartBot.bussines.service;

import org.joda.time.DateTime;
import smartBot.bean.MarginRates;

import java.util.List;

public interface MarginRatesService extends Service<MarginRates> {

    List<MarginRates> findAllByShortName(String shortName);

    MarginRates getByShortNameAndDate(String shortName, DateTime onDate);

    void delete(String shortName);
}
