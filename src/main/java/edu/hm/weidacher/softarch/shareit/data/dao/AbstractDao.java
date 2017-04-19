package edu.hm.weidacher.softarch.shareit.data.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import edu.hm.weidacher.softarch.shareit.data.database.DatabaseFactory;
import edu.hm.weidacher.softarch.shareit.data.model.AbstractModel;
import edu.hm.weidacher.softarch.shareit.data.model.Book;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;
import edu.hm.weidacher.softarch.shareit.exceptions.ShareItException;

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
     * Return all entitys under T
     *
     * @return collection containing all available entitys
     */
    @Override
    public Collection<T> getAll() {
	return Collections.unmodifiableCollection(database);
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
     * Stores a entity in the datastore.
     *
     * @param entity the entity to persist
     * @return id of the created entity
     * @throws PersistenceException when a entity has already been persisted under the id of the entity
     * 					or the entity has no id attached
     */
    @Override
    public UUID store(T entity) throws PersistenceException {
        // instantiate a new entity using copy constructor
	final T copiedEntity = copyEntity(entity);

	final UUID id = validateIdPresent(copiedEntity);

	if (getById(id) != null) {
	    throw new PersistenceException("Model already present under the id!");
	}

	database.add(copiedEntity);
	return copiedEntity.getId();
    }

    /**
     * Checks for an id in a entity.
     * @param model model to check for
     * @return the id of the entity
     * @throws PersistenceException if the entity id is null
     */
    protected UUID validateIdPresent(T model) throws PersistenceException {
	UUID id = model.getId();

	if (id == null) {
	    throw new PersistenceException("Entity has no id: " + model.toString());
	}

	return id;
    }

    /**
     * Copy an entity of a model.
     *
     * This way, it can be ensured that no one can set an id to an entity.
     *
     * @param entity the entity carrying only raw data fields
     * @return a fresh entity with ID and stuff
     * @throws PersistenceException if there have been any problems during instantiation
     */
    private T copyEntity(T entity) throws PersistenceException {
	try {
	    return modelClass.getConstructor(modelClass).newInstance(entity);
	} catch (ReflectiveOperationException e) {
	    throw new PersistenceException("Error copying an entity", e);
	}
    }

}
