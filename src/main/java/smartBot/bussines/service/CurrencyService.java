package smartBot.bussines.service;

import smartBot.bean.Currency;

public interface CurrencyService extends Service<Currency>{

    Currency findByShortName(String shortName);
    Currency findByClearingCode(String clearingCode);

    void delete(String shortName);
}
