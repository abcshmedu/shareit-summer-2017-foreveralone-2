package edu.hm.weidacher.softarch.shareit.data.dao;

import javax.validation.constraints.NotNull;

import edu.hm.weidacher.softarch.shareit.data.model.AbstractUpdatableModel;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * A Dao for updatable Models.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public interface UpdatableDao <T extends AbstractUpdatableModel> extends Dao <T>{

    /**
     * Update an existing model.
     *
     * @param model the model that shall be updated
     * @throws PersistenceException if no model is present under the id of the model
     */
    void update (@NotNull final T model) throws PersistenceException;

}
