package edu.hm.weidacher.softarch.shareit.data.database;

import java.util.Collection;

import edu.hm.weidacher.softarch.shareit.data.model.AbstractModel;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * Interface for a pseudo database.
 * <p>
 * Classes that implement this interface must guarantee to hold a reference to the object at any time.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public interface Database {

    /**
     * Returns the collection that stores objects of the given type.
     *
     * If no collection for the type is registered yet, it will be created.
     *
     * @param <T> declares the models type of the collection
     * @param type the corresponding type
     * @return the corresponding collection
     */
    <T extends AbstractModel> Collection<T> getCollectionForType(Class<T> type);

    /**
     * Persist the data contained in the database to a non-volatile medium.
     * @throws PersistenceException if any error occurs during persistence
     */
    void persist() throws PersistenceException;

    /**
     * Load the state and data of the database from a previous persist() call.
     *
     * @throws PersistenceException if any error occurs during loading (ENOENT, corrupt ...)
     */
    void load() throws PersistenceException;

}
