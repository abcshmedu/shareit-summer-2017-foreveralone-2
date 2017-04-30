package edu.hm.weidacher.softarch.shareit.test.rest;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

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
        Disc disc = new Disc("Eine Disc", "8888888", "von einem Regisseur", 16);
        // store
	final Response creationResponse = sut.createDisc(gson.toJson(disc));
	assertNotNull(creationResponse);
	assertEquals(Response.Status.CREATED.getStatusCode(), creationResponse.getStatus());

	// retrieve
	final String location = creationResponse.getHeaderString("Location");
	assertTrue("Created Response Header Location Does not end with barcode", location.endsWith(disc.getBarcode()));

	Response storedDiscResponse = sut.getById(disc.getBarcode());
	assertEquals(Response.Status.OK.getStatusCode(), storedDiscResponse.getStatus());

	Disc storedDisc = gson.fromJson(((String) storedDiscResponse.getEntity()), Disc.class);
	assertEquals(disc, storedDisc);
    }


    @Test
    public void testUpdate() {
	Disc disc = new Disc("Eine Disc", "7777", "von einem Regisseur", 16);

	// create
	final Response creationResponse = sut.createDisc(gson.toJson(disc));
	assertNotNull(creationResponse);
	assertEquals(Response.Status.CREATED.getStatusCode(), creationResponse.getStatus());

	// update
	disc.setDirector("New Director nobody ever heard of");
	final Response updatedResponse = sut.updateDisc(disc.getBarcode(), gson.toJson(disc));
	assertNotNull(updatedResponse);
	assertEquals(Response.Status.OK.getStatusCode(), updatedResponse.getStatus());

	Map responseMap = gson.fromJson((String) updatedResponse.getEntity(), Map.class);
	assertTrue("Update response does not contain location key", responseMap.containsKey("location"));
	assertTrue("Bad uri after updated Disc", ((String) responseMap.get("location")).endsWith(disc.getBarcode()));

	// retrieve
	final Response afterUpdateResponse = sut.getById(disc.getBarcode());
	assertNotNull(afterUpdateResponse);
	assertEquals(Response.Status.OK.getStatusCode(), afterUpdateResponse.getStatus());

	final Disc updated = gson.fromJson((String) afterUpdateResponse.getEntity(), Disc.class);
	assertEquals(disc, updated);
    }


    @Test
    public void testBadCreate() {
	assertBadRequest(sut.createDisc(" "));
	assertBadRequest(sut.createDisc("{}"));
	assertBadRequest(sut.createDisc("{'notadisc':'mothafucka'}"));
	assertBadRequest(sut.createDisc(""));
	assertBadRequest(sut.createDisc("{{{{{{}"));
    }


    @Test
    public void testBadUpdate() {
	assertBadRequest(sut.updateDisc("", "{}"));
	assertNotFound(sut.updateDisc("4444", "{}"));
	assertNotFound(sut.updateDisc("123123123", "//2{}"));

	assertBadRequest(sut.updateDisc("7777", "{#####}")); // 7777 stored previously, if fails, start again :)
	assertBadRequest(sut.updateDisc("", "{}"));
    }


    @Test
    public void testBadGetByBarcode() {
	assertBadRequest(sut.getById(""));
	assertNotFound(sut.getById("peij32900jvnmekmvd"));
    }

    private void assertBadRequest(Response response) {
        assertRequestStatus(Response.Status.BAD_REQUEST, response);
    }

    private void assertNotFound(Response response) {
        assertRequestStatus(Response.Status.NOT_FOUND, response);
    }

    private void assertRequestStatus(Response.Status status, Response response) {
	assertEquals(status.getStatusCode(), response.getStatus());

    }
}
