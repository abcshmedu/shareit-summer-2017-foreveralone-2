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
     * @param suffix the last characters of the URI
     * @return jaxrs Response, ready to return
     */
    protected Response buildCreatedResponse(String suffix) {
	return Response.created(URI.create(getAbsolutePath(suffix))).build();
    }

    /**
     * Builds a HTTP Response.
     * @param status HTTP status code
     * @return jaxrs Response, ready to return
     */
    protected Response error(Response.Status status) {
        return error("No error message", status);
    }

    /**
     * Builds a HTTP Response.
     *
     * @param message error description, human readable
     * @param status HTTP status code
     * @return jaxrs Response, ready to return
     */
    protected Response error(String message, Response.Status status) {
	return Response.status(status).entity(message).build();
    }

    /**
     * Builds a HTTP Response from an exception.
     *
     * @param e the exception describing the error
     * @return jaxrs Response, ready to return
     */
    protected Response error(Exception e) {
        String entity;

        if (e == null) {
            entity = "";
	} else {
            entity = e.getMessage();
	}

	return error(entity, Response.Status.BAD_REQUEST);
    }

    /**
     * Returns the URI as a String to this resource + the given suffix.
     *
     * The URI will be the URI of the host + the path to this resource + the given suffix
     * A slash '/' will be prepended to the suffix if it has none yet

     *
     * @param suffix some string, like an id or else
     * @return URI to something
     */
    protected String getAbsolutePath(String suffix) {
	StringBuilder sb = new StringBuilder(Configuration.HOST.length() + path.length() + suffix.length() + 1);

	sb.append(Configuration.HOST);
	sb.append(getPath());

	if (!suffix.startsWith(Configuration.URL_SEPARATOR)) {
	    sb.append(Configuration.URL_SEPARATOR);
	}
	sb.append(suffix);

	return sb.toString();
    }

    /**
     * Returns the path from the @Path annotation at class level.
     * This is pretty slow, so use the getter.
     * @return class resource path
     */
    private String finalClassResourcePath() {
	return getClass().getAnnotation(Path.class).value();
    }

    /**
     * Returns the path from the @Path annotation at class level.
     * @return class resource path
     */
    public String getPath() {
        return path;
    }
}
