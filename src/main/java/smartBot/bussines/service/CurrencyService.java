package smartBot.bussines.service;

import smartBot.bean.Currency;

import java.util.List;

public interface CurrencyService {
    /**
     * Loads an entity from the database using its Primary Key
     * @param id
     * @return Currency
     */
    Currency findById(Integer id);

    /**
     * Loads all entities.
     * @return all entities
     */
    List<Currency> findAll();

    /**
     * Loads entity by short name.
     * @param shortName
     * @return Currency
     */
    Currency findByShortName(String shortName);

    /**
     * Saves the given entity in the database (create or update)
     * @param currency
     * @return Currency
     */
    Currency save(Currency currency);

    /**
     * Creates the given entity in the database
     * @param currency
     * @return Currency
     */
    Currency create(Currency currency);

    /**
     * Updates the given entity in the database
     * @param currency
     * @return Currency
     */
    Currency update(Currency currency);

    /**
     * Delete the given entity from the database
     * @param currency
     * @return void
     */
    void delete(Currency currency);

    /**
     * Delete the given entity from the database
     * @param id
     * @return void
     */
    void delete(Integer id);
}
