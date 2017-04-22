package edu.hm.weidacher.softarch.shareit.test.datastore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import edu.hm.weidacher.softarch.shareit.data.Datastore;
import edu.hm.weidacher.softarch.shareit.data.DatastoreFactory;

/**
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class DatastoreTest {

    @Test
    public void acquireDatastoreFactory() {
	final DatastoreFactory factory = Datastore.factory();
	assertNotNull(factory);
	assertEquals(factory, Datastore.factory());
    }

    @Test
    public void acquireDatastore() {
	final Datastore datastore = Datastore.factory().getDatastore();
	assertNotNull(datastore);
	assertEquals(datastore, Datastore.factory().getDatastore());
    }

}
