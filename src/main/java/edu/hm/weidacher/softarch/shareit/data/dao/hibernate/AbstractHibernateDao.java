package edu.hm.weidacher.softarch.shareit.data.dao.hibernate;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import edu.hm.weidacher.softarch.shareit.data.dao.Dao;
import edu.hm.weidacher.softarch.shareit.data.model.AbstractModel;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;
import edu.hm.weidacher.softarch.shareit.util.di.DIUtil;

/**
 * An abstract Dao that accesses Data through Hibernate.
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public abstract class AbstractHibernateDao<T extends AbstractModel> implements Dao<T> {

    /**
     * The hibernate session.
     * All persistence calls are processed through the session.
     */
    private Session session;

    @Inject
    private SessionFactory sessionFactory;

    /**
     * Class, this Dao handles.
     */
    private final Class<T> modelClass;

    /**
     * Ctor.
     */
    public AbstractHibernateDao (Class<T> modelClass) {
	this.modelClass = modelClass;
	DIUtil.getInjectorStatic().injectMembers(this);
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
        if (model == null) {
            throw new PersistenceException("Model may not be null");
	}

	model.validate();

        try {
            beginTransaction();

            getSession().persist(model);

            // now, the id should be set
	    return model.getId();
	} catch (Exception e) {
            throw new PersistenceException("Exception caught persisting entity " + model.toString(), e);
	} finally {
	    commitTransaction();
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
	        beginTransaction();
		getSession().delete(entity);
	    }

	    return entity;
	} catch (Exception e) {
            throw new PersistenceException("Error deleting entity by ID: " + id.toString(), e);
	} finally {
            commitTransaction();
	}
    }

    /**
     * Return all entitys under T.
     *
     * @return collection containing all available entitys
     */
    @Override
    public Collection<T> getAll() {
	beginTransaction();

	final CriteriaQuery<T> query = getSession().getCriteriaBuilder()
	    .createQuery(modelClass);

	final Root<T> from = query.from(modelClass);

	final List<T> resultList = getSession().createQuery(query.select(from)).getResultList();

	commitTransaction();

	return resultList;
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
        if (session == null || !session.isOpen()) {
	    // lazily create a new session
            session = sessionFactory.getCurrentSession();
	}

	return session;
    }

    /**
     * Start a transaction.
     */
    protected void beginTransaction() {
	getSession().beginTransaction();
    }

    /**
     * Commit a transaction.
     *
     * This will first flush and then close the session.
     */
    protected void commitTransaction() {
	getSession().getTransaction().commit();

	// session closed. set it to null to enable acquiring a new session when it's required
	session = null;
    }
}
