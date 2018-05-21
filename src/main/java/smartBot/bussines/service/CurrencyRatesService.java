package smartBot.bussines.service;

import smartBot.bean.CurrencyRates;

import java.util.List;

public interface CurrencyRatesService {
    
    /**
     * Loads all entities.
     * @return all entities
     */
    List<CurrencyRates> findAll();

    /**
     * Loads entity by short name.
     * @param shortName
     * @return CurrencyRates
     */
    List<CurrencyRates> findAllByShortName(String shortName);

    /**
     * Creates the given entity in the database
     * @param currencyRates
     * @return CurrencyRates
     */
    CurrencyRates create(CurrencyRates currencyRates);

    /**
     * Delete the given entity from the database
     * @param currencyRates
     * @return void
     */
    void delete(CurrencyRates currencyRates);

    /**
     * Delete the given entity from the database
     * @param id
     * @return void
     */
    void deleteById(Integer id);
    /**
     * Delete entities from the database by shortname
     * @param shortname
     * @return void
     */
    void deleteByShortName(String shortname);
}
