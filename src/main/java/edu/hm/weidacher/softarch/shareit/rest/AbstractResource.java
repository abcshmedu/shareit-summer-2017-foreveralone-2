package edu.hm.weidacher.softarch.shareit.rest;

import com.google.gson.Gson;

import edu.hm.weidacher.softarch.shareit.data.Datastore;

/**
 * Base class for resources.
 * Supplies convenience methods.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public abstract class AbstractResource {

    /**
     * Gson instance.
     */
    private Gson gson;

    /**
     * Datastore instance.
     */
    private Datastore datastore;

    /**
     * Ctor.
     */
    protected AbstractResource() {
        gson = new Gson();
        datastore = Datastore.factory().getDatastore();
    }

    /**
     * Returns an instance of Gson.
     * @return gson
     */
    public Gson getGson() {
        return gson;
    }

    /**
     * Returns the datastore.
     * @return datastore
     */
    public Datastore getDatastore() {
        return datastore;
    }

}
