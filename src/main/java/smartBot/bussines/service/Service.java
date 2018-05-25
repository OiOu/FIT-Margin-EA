package smartBot.bussines.service;

import java.util.List;

public interface Service<T> {
    /**
     * Loads an entity from the database using its Primary Key
     * @param id
     * @return T
     */
    T findById(Integer id);

    /**
     * Loads all entities.
     * @return all entities
     */
    List<T> findAll();

    /**
     * Creates the given entity in the database
     * @param bean
     * @return T
     */
    T create(T bean);

    /**
     * Delete the given entity from the database
     * @param bean
     * @return void
     */
    void delete(T bean);

    /**
     * Delete the given entity from the database
     * @param id
     * @return void
     */
    void delete(Integer id);
}
