package edu.hm.weidacher.softarch.shareit.data;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import edu.hm.weidacher.softarch.shareit.data.model.AbstractModel;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * A Dao for all models.
 *
 * As standard models aren't updatable, we can only store new objects or receive them.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public interface Dao< T extends AbstractModel > {

    /**
     * Return the model corresponding to an id.
     *
     * @param id the id of the model
     * @return the model or null, if the id dies not match any model
     */
    T getById (@NotNull final UUID id);

    /**
     * Stores a model in the datastore.
     *
     * @param model the model to persist
     * @throws PersistenceException when a model has already been persisted under the id of the model
     */
    void store (@NotNull final T model) throws PersistenceException;

}
