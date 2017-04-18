package edu.hm.weidacher.softarch.shareit.data.dao;

import java.util.Collection;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import edu.hm.weidacher.softarch.shareit.data.model.AbstractModel;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * A Dao for all entity.
 *
 * As standard entity aren't updatable, we can only store new objects or receive them.
 *
 * @param <T> declares the model, the dao handles
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public interface Dao< T extends AbstractModel > {

    /**
     * Return the entity corresponding to an id.
     *
     * @param id the id of the entity
     * @return the entity or null, if the id dies not match any entity
     */
    T getById(@NotNull UUID id);

    /**
     * Stores an entity in the datastore.
     *
     * @param model the entity to persist
     * @throws PersistenceException when a model has already been persisted under the id of the model
     */
    void store(@NotNull T model) throws PersistenceException;

    /**
     * Return all entitys under T
     * @return collection containing all available entitys
     */
    Collection<T> getAll();

}
