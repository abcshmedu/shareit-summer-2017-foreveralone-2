package edu.hm.weidacher.softarch.shareit.data.dao;

import java.util.UUID;

import edu.hm.weidacher.softarch.shareit.data.model.AbstractUpdatableModel;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * Abstract Base class, providing utility methods.
 *
 * @param <T> declares the model, the dao handles
 * @author Simon Weidacher <weidache@hm.edu>
 */
public abstract class AbstractUpdatableDao<T extends AbstractUpdatableModel> extends AbstractDao<T> implements UpdatableDao<T> {

    /**
     * Ctor.
     * @param clazz declares the model, the dao handles
     */
    protected AbstractUpdatableDao(Class<T> clazz) {
        super(clazz);
    }

    /**
     * Update an existing model.
     *
     * @param model the model that shall be updated
     * @throws PersistenceException if no model is present under the id of the model
     */
    @Override
    public void update(T model) throws PersistenceException {
	UUID id = validateIdPresent(model);

	update(model, id);
    }

    /**
     * Update an existing model with the id.
     *
     * @param model the model carrying the updated information
     * @param id    the id of the entity that shall be updated
     * @throws PersistenceException if no model is present under the id of the model
     */
    @Override
    public void update(T model, UUID id) throws PersistenceException {
	// is a model present under the id?
	final T persisted = validateIsPersisted(id);

	// book is present -> merge it!
	persisted.mergeWith(model);

	preStore(model);
    }

    /**
     * Methods that is called before the model is actually persisted.
     *
     * @param model the model that can be modified prior to persisting
     */
    @Override
    protected void preStore(T model) {
        model.update(); // updates lud
    }

    /**
     * Checks whether a model is persisted under the given id.
     * @param id id to check for
     * @return the model if persisted
     * @throws PersistenceException if no model is persisted under the id
     */
    protected T validateIsPersisted(UUID id) throws PersistenceException {
	// is a model present under the id?
	final T persisted = getById(id);

	if (persisted == null) {
	    throw new PersistenceException("Not entity found for id, can thus not be updated: " + id.toString());
	}

	return persisted;
    }

}
