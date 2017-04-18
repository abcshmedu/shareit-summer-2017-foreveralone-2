package edu.hm.weidacher.softarch.shareit.data;

/**
 * Factory creating Datastore access objects.
 *
 * Singleton!
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class DatastoreFactory {

    private static DatastoreFactory instance = null;

    public static DatastoreFactory factory() {
	if (instance == null) {
	    instance = new DatastoreFactory();
	}

	return instance;
    }

    private Datastore datastore;

    private DatastoreFactory() {

    }

    public Datastore getDatastore() {
	return datastore;
    }

}
