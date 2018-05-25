package smartBot.bussines.service;

import smartBot.bean.MarginRates;

import java.util.List;

public interface MarginRatesService extends Service<MarginRates> {

    List<MarginRates> findAllByShortName(String shortName);

    void delete(String shortName);
}
