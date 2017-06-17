package edu.hm.weidacher.softarch.shareit.data.dao.hibernate;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Function;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import edu.hm.weidacher.softarch.shareit.data.dao.Dao;
import edu.hm.weidacher.softarch.shareit.data.model.AbstractModel;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * An abstract Dao that accesses Data through Hibernate.
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public abstract class AbstractHibernateDao<T extends AbstractModel> implements Dao<T> {

    /**
     * The hibernate session.
     * All persistence calls are processed through the session.
     */
    @Inject
    private Session session;

    /**
     * Class, this Dao handles.
     */
    private final Class<T> modelClass;

    /**
     * Ctor.
     */
    public AbstractHibernateDao (Class<T> modelClass) {
	this.modelClass = modelClass;
    }

    /**
     * Return the entity corresponding to an id.
     *
     * @param id the id of the entity
     * @return the entity or null, if the id dies not match any entity
     */
    @Override
    public T getById(UUID id) {
	return getSession().get(modelClass, id);
    }

    /**
     * Return the identity corresponding to the identifier.
     *
     * This is a convenience method. Do only use it for testing purposes, as it is a lot slower compared to a native
     *  CriteriaQuery.
     *
     * @param keyExtractor function, extracting the key from the handled model
     * @param identifier   identifies the desired entity
     * @return entity or null, if no entity could be matched to the identifier
     * @throws NullPointerException if one of the parameters was null
     */
    @Override
    public <KEY> T getByExtractor(Function<T, KEY> keyExtractor, KEY identifier) {
	return getAll()
	    .stream()
	    .filter((entity) -> keyExtractor.apply(entity).equals(identifier))
	    .findFirst()
	    .orElse(null);
    }

    /**
     * Stores an entity in the datastore.
     *
     * @param model the entity to persist
     * @return id of the created entity
     * @throws PersistenceException when a model has already been persisted under the id of the model
     */
    @Override
    public UUID store(T model) throws PersistenceException {
        try {
            getSession().persist(model);

            // now, the id should be set
	    return model.getId();
	} catch (Exception e) {
            throw new PersistenceException("Exception caught persisting entity " + model.toString(), e);
	}
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
        try {
	    final T entity = getById(id);

	    if (entity != null) {
		getSession().delete(entity);
	    }

	    return entity;
	} catch (Exception e) {
            throw new PersistenceException("Error deleting entity by ID: " + id.toString(), e);
	}
    }

    /**
     * Return all entitys under T.
     *
     * @return collection containing all available entitys
     */
    @Override
    public Collection<T> getAll() {
	final CriteriaQuery<T> query = getSession().getCriteriaBuilder()
	    .createQuery(modelClass);

	final Root<T> from = query.from(modelClass);

	return getSession().createQuery(query.select(from)).getResultList();
    }

    /**
     * Returns the class, this Dao handles.
     *
     * @return model class
     */
    @Override
    public Class<T> getModelClass() {
	return modelClass;
    }

    /**
     * Returns the session.
     * @return the session
     */
    public Session getSession() {
	return session;
    }
}
