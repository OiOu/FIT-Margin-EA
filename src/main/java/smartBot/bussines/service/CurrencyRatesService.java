package smartBot.bussines.service;

import smartBot.bean.CurrencyRates;

import java.util.List;

public interface CurrencyRatesService extends Service<CurrencyRates> {

    List<CurrencyRates> findAllByShortName(String shortName);

    void delete(String shortName);
}
