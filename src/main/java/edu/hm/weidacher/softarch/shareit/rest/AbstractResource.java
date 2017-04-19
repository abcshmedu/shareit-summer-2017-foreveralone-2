package edu.hm.weidacher.softarch.shareit.rest;

import java.net.URI;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import edu.hm.weidacher.softarch.shareit.Configuration;
import edu.hm.weidacher.softarch.shareit.data.Datastore;

/**
 * Base class for resources.
 * Supplies convenience methods.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public abstract class AbstractResource {

    /**
     * Path of this Resource at class level.
     */
    private final String path;

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
        path = finalClassResourcePath();
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

    /**
     * Creates a MediaType.Created Response.
     *
     * The URI will be the URI of the host + the path to this resource + the given suffix
     * A slash '/' will be prepended to the suffix if it has none yet
     *
     * @param suffix the last characters of the URI
     * @return jaxrs Response, ready to return
     */
    protected Response buildCreatedResponse(String suffix) {
        StringBuilder sb = new StringBuilder(Configuration.HOST.length() + path.length() + suffix.length() + 1);

        sb.append(Configuration.HOST);
        sb.append(path);

        if (!suffix.startsWith(Configuration.URL_SEPARATOR)) {
            sb.append(Configuration.URL_SEPARATOR);
	}
        sb.append(suffix);

	return Response.created(URI.create(sb.toString())).build();
    }

    /**
     * Returns the path from the @Path annotation at class level.
     * This is pretty slow, so store the value somewhere.
     * @return class resource path
     */
    private String finalClassResourcePath() {
	return getClass().getAnnotation(Path.class).value();
    }
}
