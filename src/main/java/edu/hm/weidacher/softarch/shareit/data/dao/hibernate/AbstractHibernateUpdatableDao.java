package edu.hm.weidacher.softarch.shareit.data.dao.hibernate;

import java.util.UUID;

import edu.hm.weidacher.softarch.shareit.data.dao.UpdatableDao;
import edu.hm.weidacher.softarch.shareit.data.model.AbstractUpdatableModel;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * An abstract Dao that handles data through Hibernate.
 * All models accessed through subclasses of this dao can be modified in some way.
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public abstract class AbstractHibernateUpdatableDao<T extends AbstractUpdatableModel> extends AbstractHibernateDao<T> implements UpdatableDao<T> {

    /**
     * Ctor.
     * @param modelClass the class handled through the dao
     */
    public AbstractHibernateUpdatableDao(Class<T> modelClass) {
        super(modelClass);
    }

    /**
     * Update an existing entity.
     * <p>
     * The id contained in the model determines the entity to update.
     *
     * @param model the entity that shall be updated
     * @throws PersistenceException if no model is present under the id of the model
     */
    @Override
    public void update(T model) throws PersistenceException {
	if (model.getId() == null) {
	    throw new NullPointerException("Entity's ID may not be null.");
	}

	getSession().beginTransaction();

	try {

	    final T persistedEntity = getSession().get(getModelClass(), model.getId());

	    if (persistedEntity == null) {
		throw new PersistenceException(String.format(
		    "No entity was present under the ID %s during update.", model.getId().toString()));
	    }

	    persistedEntity.mergeWith(model); // merge them

	    getSession().update(persistedEntity);

	} finally {
	    getSession().close();
	}

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
	if (id == null) {
	    throw new NullPointerException("ID may not be null.");
	}

	try {

	    final T persistedEntity = getSession().get(getModelClass(), model.getId());

	    if (persistedEntity == null) {
		throw new PersistenceException(String.format(
		    "No entity was present under the ID %s during update.", model.getId().toString()));
	    }

	    persistedEntity.mergeWith(model); // merge them

	    getSession().update(persistedEntity);

	} finally {
	    getSession().close();
	}
    }
}
