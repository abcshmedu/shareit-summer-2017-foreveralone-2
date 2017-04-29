package edu.hm.weidacher.softarch.shareit.test.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import edu.hm.weidacher.softarch.shareit.data.model.Book;
import edu.hm.weidacher.softarch.shareit.data.model.Disc;
import edu.hm.weidacher.softarch.shareit.rest.DiscResource;


/**
 * This TestCase probes the REST-API available via HTTP.
 * Thus, the performed test do not affect the codecoverage...
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class DiscResourceTest {

    private DiscResource sut;
    private Gson gson;

    @Before
    public void setup() {
	sut = new DiscResource();
	gson = new Gson();
    }

    @Test
    public void gsonAvailable() {
	assertNotNull(sut.getGson());
    }

    @Test
    public void testGetAll() {
	final Response allDiscsResponse = sut.getAllDiscs();

	assertNotNull(allDiscsResponse);

	final ArrayList allBooksList = gson.fromJson((String) allDiscsResponse.getEntity(), ArrayList.class);

	assertNotNull(allBooksList);
    }

    @Test
    public void testPost() throws MalformedURLException {
        // TODO
	final Disc book = new Disc("Eine Disc", "mit barcode", "von einem Regisseur", 16);

	final Response creationResponse = sut.createDisc(gson.toJson(book));
	assertNotNull("Response after book creation was null!", creationResponse);
	assertEquals(Response.Status.CREATED.getStatusCode(), creationResponse.getStatus());

	final String location = creationResponse.getHeaderString("Location");

	assertNotNull(location);
	assertNotNull("Is not an URI", new URL(location));
    }

    @Test
    public void testGetByBarcode() {
	// TODO
    }


    @Test
    public void testUpdate() {
	// TODO
    }


    @Test
    public void testBadCreate() {
	// TODO
    }


    @Test
    public void testBadUpdate() {
	// TODO
    }


    @Test
    public void testBadGetByBarcode() {
	// TODO
    }
}
