package edu.hm.weidacher.softarch.shareit.test.rest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;

import edu.hm.weidacher.softarch.shareit.Configuration;


/**
 * This TestCase probes the REST-API available via HTTP.
 * Thus, the performed test do not affect the codecoverage...
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class BookResourceAPITest {

    static String BOOK_RESOURCE_PATH = Configuration.HOST + "/media/books";

    public void setup() throws Exception {
        try {

	    // check if server is running
	    final Response.StatusType response = Client.create().resource(BOOK_RESOURCE_PATH)
		.head().getStatusInfo();

	    if (response.getStatusCode() < 200 || response.getStatusCode() > 305) {
		throw new Exception("Application server not available for testing, status code: "
		    + response.getReasonPhrase());
	    }
	} catch (ClientHandlerException e) {
            throw new Exception("Application server not started!!");
	}

	// API available, testing can proceed
    }


    public void testEmpty() {
	final String response = Client.create().resource(BOOK_RESOURCE_PATH)
	    .accept(MediaType.APPLICATION_JSON_TYPE)
	    .get(String.class);

	assertEquals("[]", response); // should deliver empty JSON array
    }

}
