package edu.hm.weidacher.softarch.shareit.data;


import edu.hm.weidacher.softarch.shareit.data.dao.SimpleAccountDao;
import edu.hm.weidacher.softarch.shareit.data.dao.SimpleBookDao;
import edu.hm.weidacher.softarch.shareit.data.dao.SimpleCopyDao;
import edu.hm.weidacher.softarch.shareit.data.dao.Dao;
import edu.hm.weidacher.softarch.shareit.data.dao.SimpleDiscDao;
import edu.hm.weidacher.softarch.shareit.data.dao.UpdatableDao;
import edu.hm.weidacher.softarch.shareit.data.model.AbstractModel;
import edu.hm.weidacher.softarch.shareit.data.model.AbstractUpdatableModel;
import edu.hm.weidacher.softarch.shareit.data.model.Account;
import edu.hm.weidacher.softarch.shareit.data.model.Book;
import edu.hm.weidacher.softarch.shareit.data.model.Copy;
import edu.hm.weidacher.softarch.shareit.data.model.Disc;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * A very basic Datastore implementation.
 *
 * All the Daos must be registered by hand.
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class SimpleDatastore implements Datastore {

    /**
     * Returns the corresponding Dao for a classfile.
     *
     * @param forClass the model class file
     * @return a Dao for the classfile
     * @throws PersistenceException if the model-class has no registered Dao
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends AbstractModel> Dao<T> getDao(Class<T> forClass) throws PersistenceException {
	if (forClass == Book.class) {
	    return (Dao<T>) new SimpleBookDao();
	}
	if (forClass == Disc.class) {
	    return ((Dao<T>) new SimpleDiscDao());
	}
	if (forClass == Copy.class) {
	    return ((Dao<T>) new SimpleCopyDao());
	}
	if (forClass == Account.class) {
	    return ((Dao<T>) new SimpleAccountDao());
	}

	throw new PersistenceException("No registered Dao for model class: " + forClass.getSimpleName());
    }

    /**
     * Returns the corresponding UpdatableDao for a classfile.
     *
     * @param forClass the model class file
     * @return a Dao for the classfile
     * @throws PersistenceException if the model-class has no registered Dao or is not updatable
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends AbstractUpdatableModel> UpdatableDao<T> getUpdatableDao(Class<T> forClass) throws PersistenceException {
    	if (forClass == Book.class) {
    	    return (UpdatableDao<T>) new SimpleBookDao();
	}
	if (forClass == Disc.class) {
    	    return ((UpdatableDao<T>) new SimpleDiscDao());
	}
	if (forClass == Copy.class) {
    	    return ((UpdatableDao<T>) new SimpleCopyDao());
	}
	if (forClass == Account.class) {
	    return ((UpdatableDao<T>) new SimpleAccountDao());
	}

	throw new PersistenceException("No registered Dao for model class: " + forClass.getSimpleName());
    }
}
