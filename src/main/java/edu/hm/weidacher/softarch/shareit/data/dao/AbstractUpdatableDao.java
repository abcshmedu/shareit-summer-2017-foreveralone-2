package edu.hm.weidacher.softarch.shareit.data.dao;

import java.util.UUID;

import edu.hm.weidacher.softarch.shareit.data.model.AbstractUpdatableModel;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * Abstract Base class, providing utility methods.
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public abstract class AbstractUpdatableDao<T extends AbstractUpdatableModel> implements UpdatableDao<T> {

    /**
     * Update an existing model.
     *
     * @param model the model that shall be updated
     * @throws PersistenceException if no model is present under the id of the model
     */
    @Override
    public void update(T model) throws PersistenceException {
	UUID id = validateIdPresent(model);

	// is a model present under the id?
	final T persisted = validateIsPersisted(id);

	// book is present -> merge it!
	persisted.mergeWith(model);
    }

    /**
     * Checks for an id in a model.
     * @param model model to check for
     * @return the id of the model
     * @throws PersistenceException if the models id is null
     */
    protected UUID validateIdPresent (T model) throws PersistenceException{
	UUID id = model.getId();
	if (id == null) {
	    throw new PersistenceException("Model has no id, can thus not be updated: " + model.toString());
	}

	return id;
    }

    /**
     * Checks whether a model is persisted under the given id.
     * @param id id to check for
     * @return the model if persisted
     * @throws PersistenceException if no model is persisted under the id
     */
    protected T validateIsPersisted (UUID id) throws PersistenceException {
	// is a model present under the id?
	final T persisted = getById(id);

	if (persisted == null) {
	    throw new PersistenceException("Not entity found for id, can thus not be updated: " + id.toString());
	}

	return persisted;
    }

}
