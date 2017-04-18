package edu.hm.weidacher.softarch.shareit.data.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.hm.weidacher.softarch.shareit.data.model.AbstractModel;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * Pretty simple implementation of a database.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class SimpleDatabase implements Database {

    private final static String PERSIST_PATH = "data.db";

    /**
     * Map containing collections for each model type.
     */
    private final Map<Class< ? extends AbstractModel>, Collection< ? extends AbstractModel>> data;

    /**
     * Ctor.
     * Creates a new empty database
     */
    public SimpleDatabase() {
        this.data = new HashMap<>();
    }

    /**
     * Returns the collection that stores objects of the given type.
     * <p>
     * If no collection for the type is registered yet, it will be created.
     *
     * @param type the corresponding type
     * @return the corresponding collection
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends AbstractModel> Collection<T> getCollectionForType(Class<T> type) {
        // add the collection if the type is not yet present
        if (!data.containsKey(type)) {
            data.put(type, new ArrayList<>());
	}

	return (Collection<T>) data.get(type);
    }

    /**
     * Persist the data contained in the database to a non-volatile medium.
     *
     * @throws PersistenceException if any error occurs during persistence
     */
    @Override
    public void persist() throws PersistenceException {
        try (
            final FileOutputStream fos = new FileOutputStream(PERSIST_PATH);
            final ObjectOutputStream oos = new ObjectOutputStream(fos)
	) {
	    oos.writeObject(data);
        } catch (IOException e) {
	    throw new PersistenceException(e);
	}
    }

    /**
     * Load the state and data of the database from a previous persist() call.
     *
     * @throws PersistenceException if any error occurs during loading (ENOENT, corrupt ...)
     */
    @Override
    @SuppressWarnings("unchecked")
    public void load() throws PersistenceException {
	try (
	    final FileInputStream fis = new FileInputStream(PERSIST_PATH);
	    final ObjectInputStream ois = new ObjectInputStream(fis)
	) {
	    final Object o = ois.readObject();
	    if (!(o instanceof Map)) {
	        throw new PersistenceException("Persisted file corrupted!");
	    }

	    final Map<Class<? extends AbstractModel>, Collection<? extends AbstractModel>> loaded =
		(Map<Class<? extends AbstractModel>, Collection<? extends AbstractModel>>) o;

	    data.putAll(loaded);

	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    throw new PersistenceException("No persisted file found", e);
	} catch (IOException e) {
	    e.printStackTrace();
	    throw new PersistenceException(e);
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	    throw new PersistenceException("Persisted file corrupted!", e);
	}
    }
}
