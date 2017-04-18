package edu.hm.weidacher.softarch.shareit.data;

/**
 * Factory creating Datastore access objects.
 *
 * Singleton!
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public final class DatastoreFactory {

    /**
     * The Datastore supplied by this factory.
     */
    private final Datastore datastore;

    /**
     * Ctor.
     */
    private DatastoreFactory() {
	this.datastore = new SimpleDatastore();
    }

    /**
     * Factory function for Datastores.
     * @return a datastore
     */
    public Datastore getDatastore() {
        return this.datastore;
    }

    /**
     * The one and only instance of this factory.
     */
    private static DatastoreFactory instance = null;

    /**
     * Factory function for this Factory.
     * @return an instance of the DatastoreFactory
     */
    public static DatastoreFactory factory() {
	if (instance == null) {
	    instance = new DatastoreFactory();
	}

	return instance;
    }

}
