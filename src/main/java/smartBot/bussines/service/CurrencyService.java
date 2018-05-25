package smartBot.bussines.service;

import smartBot.bean.Currency;

public interface CurrencyService extends Service<Currency>{

    Currency findByShortName(String shortName);

    void delete(String shortName);
}
