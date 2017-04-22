package edu.hm.weidacher.softarch.shareit.data;

import javax.validation.constraints.NotNull;

import edu.hm.weidacher.softarch.shareit.data.dao.Dao;
import edu.hm.weidacher.softarch.shareit.data.dao.UpdatableDao;
import edu.hm.weidacher.softarch.shareit.data.model.AbstractModel;
import edu.hm.weidacher.softarch.shareit.data.model.AbstractUpdatableModel;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * The Datastore for the ShareIt application.
 *
 * This is the main interface to the data-layer.
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public interface Datastore {

    /**
     * Returns the corresponding Dao for a classfile.
     *
     * @param <T> declares the model, the desired dao handles
     * @param forClass the model class file
     * @return a Dao for the classfile
     * @throws PersistenceException if the model-class has no registered Dao
     */
    <T extends AbstractModel> Dao<T> getDao(@NotNull Class<T> forClass) throws PersistenceException;

    /**
     * Returns the corresponding UpdatableDao for a classfile.
     *
     * @param <T> declares the model, the desired dao handles
     * @param forClass the model class file
     * @return a Dao for the classfile
     * @throws PersistenceException if the model-class has no registered Dao or is not updatable
     */
    <T extends AbstractUpdatableModel> UpdatableDao<T> getUpdatableDao(@NotNull Class<T> forClass) throws PersistenceException;

    /**
     * Returns the factory for instances of this class.
     *
     * @return an instance of the DatastoreFactory
     */
    static DatastoreFactory factory() {
	return DatastoreFactory.factory();
    }
}
