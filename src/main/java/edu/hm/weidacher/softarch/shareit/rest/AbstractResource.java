package edu.hm.weidacher.softarch.shareit.rest;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import javax.inject.Inject;

import edu.hm.weidacher.softarch.shareit.Configuration;
import edu.hm.weidacher.softarch.shareit.data.Datastore;
import edu.hm.weidacher.softarch.shareit.util.di.DIUtil;

/**
 * Base class for resources.
 * Supplies convenience methods.
 *
 * @author Simon Weidacher <weidache@hm.edu>
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
    @Inject
    private Datastore datastore;

    /**
     * Ctor.
     */
    protected AbstractResource() {
        gson = new Gson();
        path = finalClassResourcePath();
	DIUtil.getInjectorStatic().injectMembers(this);
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
	return Response.created(
	    URI.create(getAbsolutePath(urlEncoded(suffix)))
	).build();
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
        Map<String, Object> errorMap = new HashMap<>(2);

        errorMap.put("code", status.getStatusCode());
        errorMap.put("detail", message);

	return Response.status(status).entity(getGson().toJson(errorMap)).build();
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
     * Builds a 200 HTTP Response with an uri.
     *
     * @param uri the uri in the response
     * @return jaxrs Response, ready to return
     */
    protected Response okWithUri(String uri) {
	return Response.ok(String.format("{\"location\":\"%s\"}", urlEncoded(uri))).build();
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
     * Returns the given string URL encoded.
     * The encoding will be UTF-8 as stated in @see <a href="https://docs.oracle.com/javase/7/docs/api/java/net/URLEncoder.html"/>
     *
     * @param string encode this
     * @return url encoded string
     */
    public String urlEncoded(String string) {
	try {
	    return URLEncoder.encode(string, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    // can't happen during production
	    throw new AssertionError("Bad hardcoded encoding");
	}
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
