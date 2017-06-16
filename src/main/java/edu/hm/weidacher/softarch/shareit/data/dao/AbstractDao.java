package edu.hm.weidacher.softarch.shareit.data.dao;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.function.Function;


import javax.inject.Inject;

import edu.hm.weidacher.softarch.shareit.data.database.Database;
import edu.hm.weidacher.softarch.shareit.data.model.AbstractModel;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;
import edu.hm.weidacher.softarch.shareit.util.di.DIUtil;

/**
 * Abstract Dao class.
 *
 * All interface-spec methods have an implementation here.
 * @param <T> declares the model, the dao handles
 * @author Simon Weidacher <weidache@hm.edu>
 */
public abstract class AbstractDao<T extends AbstractModel> implements Dao<T> {

    /**
     * The .class object representing the model-type this AbstractDao handles.
     */
    private final Class<T> modelClass;

    /**
     * Database, containing all model-collections.
     */
    @Inject
    private Database databaseContainer;

    /**
     * Collection containing all persisted objects of the model.
     */
    private Collection<T> database;

    /**
     * Ctor.
     *
     * @param clazz .class object representing the modeltype
     */
    protected AbstractDao(Class<T> clazz) {
	modelClass = clazz;
	DIUtil.getInjectorStatic().injectMembers(this);
    }

    /**
     * Return all entitys under T.
     *
     * @return collection containing all available entitys
     */
    @Override
    public Collection<T> getAll() {
	return getDatabase();
    }

    /**
     * Return the model corresponding to an id.
     *
     * @param id the id of the model
     * @return the model or null, if the id dies not match any model
     */
    @Override
    public T getById(UUID id) {
	return getDatabaseWritable().stream()
	    .filter(book -> id.equals(book.getId()))
	    .findFirst()
	    .orElse(null);
    }

    /**
     * Return the identity corresponding to the identifier.
     *
     * @param keyExtractor function, extracting the key from the handled model
     * @param identifier   identifies the desired entity
     * @return entity or null, if no entity could be matched to the identifier
     * @throws NullPointerException if one of the parameters was null
     */
    @Override
    public <KEY> T getByExtractor(Function<T, KEY> keyExtractor, KEY identifier) {
        if (keyExtractor == null) {
            throw new NullPointerException("Key Extractor may not be null");
	}
	if (identifier == null) {
	    throw new NullPointerException("Identifier may not be null");
	}

	return getAll().stream()
	    .filter(entity -> keyExtractor.apply(entity).equals(identifier))
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

	entity.validate();

	final UUID id = validateIdPresent(copiedEntity);

	if (getById(id) != null) {
	    throw new PersistenceException("Model already present under the id!");
	}

	preStore(entity);

	getDatabaseWritable().add(copiedEntity);
	return copiedEntity.getId();
    }

    /**
     * Methods that is called before the model is actually persisted.
     *
     * @param model the model that can be modified prior to persisting
     */
    protected void preStore(T model) {
	// ignore
    }

    /**
     * Deletes an entity from the datastore.
     *
     * @param id UUID of the entity to delete
     * @return the entity that has been removed
     * or null if there was no entity associated with the id
     * @throws PersistenceException if any errors occur during the operation
     */
    @Override
    public T delete(UUID id) throws PersistenceException {
	final T entity = getById(id);

	if (entity != null) {
	    getDatabaseWritable().remove(entity);
	}

	return entity;
    }

    /**
     * Returns the database storing all handled entities.
     *
     * @return a collection with write operations
     */
    protected Collection<T> getDatabaseWritable() {
        if (database == null) {
            database = databaseContainer.getCollectionForType(modelClass); // init on first call
	}
        return database;
    }

    /**
     * Returns the database stroring all handled entities.
     *
     * The database is wrapped in Collections.unmodifiableCollection()
     *  to prevent changing the contents of the colleciton.
     *
     * @return unmodifiable collection
     */
    protected Collection<T> getDatabase() {
        return Collections.unmodifiableCollection(getDatabaseWritable());
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
