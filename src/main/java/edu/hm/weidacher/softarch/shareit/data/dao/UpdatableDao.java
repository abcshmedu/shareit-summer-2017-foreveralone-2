package edu.hm.weidacher.softarch.shareit.data.dao;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import edu.hm.weidacher.softarch.shareit.data.model.AbstractUpdatableModel;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * A Dao for updatable Models.
 *
 * @param <T> declares the model, the dao handles
 * @author Simon Weidacher <weidache@hm.edu>
 */
public interface UpdatableDao <T extends AbstractUpdatableModel> extends Dao <T> {

    /**
     * Update an existing entity.
     *
     * The id contained in the model determines the entity to update.
     *
     * @param model the entity that shall be updated
     * @throws PersistenceException if no model is present under the id of the model
     */
    void update(@NotNull T model) throws PersistenceException;

    /**
     * Update an existing model with the id.
     *
     * @param model the model carrying the updated information
     * @param id the id of the entity that shall be updated
     * @throws PersistenceException if no model is present under the id of the model
     */
    void update(@NotNull T model, @NotNull UUID id) throws PersistenceException;

}
