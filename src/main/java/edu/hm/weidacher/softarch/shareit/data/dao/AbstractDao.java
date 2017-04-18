package edu.hm.weidacher.softarch.shareit.data.dao;

import java.util.Collection;
import java.util.UUID;

import edu.hm.weidacher.softarch.shareit.data.database.DatabaseFactory;
import edu.hm.weidacher.softarch.shareit.data.model.AbstractModel;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * Abstract Dao class.
 *
 * All interface-spec methods have an implementation here.
 * @param <T> declares the model, the dao handles
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public abstract class AbstractDao<T extends AbstractModel> implements Dao<T> {

    /**
     * The .class object representing the model-type this AbstractDao handles.
     */
    private final Class<T> modelClass;

    /**
     * Collection containing all persisted objects of the model.
     */
    private final Collection<T> database;

    /**
     * Ctor.
     *
     * @param clazz .class object representing the modeltype
     */
    protected AbstractDao(Class<T> clazz) {
	modelClass = clazz;
	database = DatabaseFactory.getDatabase().getCollectionForType(modelClass);
    }

    /**
     * Return the model corresponding to an id.
     *
     * @param id the id of the model
     * @return the model or null, if the id dies not match any model
     */
    @Override
    public T getById(UUID id) {
	return database.stream()
	    .filter(book -> id.equals(book.getId()))
	    .findFirst()
	    .orElse(null);
    }

    /**
     * Stores a model in the datastore.
     *
     * @param model the model to persist
     * @throws PersistenceException when a model has already been persisted under the id of the model
     */
    @Override
    public void store(T model) throws PersistenceException {
	if (getById(model.getId()) != null) {
	    throw new PersistenceException("Model already present under the id!");
	}

	database.add(model);
    }

    /**
     * Returns the collection containing all stored objects of the model handled by this Dao.
     *
     * @return all the models!!
     */
    protected Collection<T> getDatabase() {
	return database;
    }
}
