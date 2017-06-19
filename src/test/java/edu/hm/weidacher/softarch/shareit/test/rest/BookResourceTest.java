package edu.hm.weidacher.softarch.shareit.test.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import edu.hm.weidacher.softarch.shareit.data.model.Book;
import edu.hm.weidacher.softarch.shareit.rest.BookResource;
import edu.hm.weidacher.softarch.shareit.test.DependencyInjectionTestPreparation;


/**
 * This TestCase probes the REST-API available via HTTP.
 * Thus, the performed test do not affect the codecoverage...
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class BookResourceTest extends DependencyInjectionTestPreparation {

    private BookResource sut;
    private Gson gson;

    @Before
    public void setup() {
        setupDependencyInjection();
        sut = new BookResource();
        gson = new Gson();
    }

    @Test
    public void gsonAvailable() {
	assertNotNull(sut.getGson());
    }

    @Test
    public void testGetAll() {
	final Response allBooksResponse = sut.getAllBooks();

	assertNotNull(allBooksResponse);

	final ArrayList allBooksList = gson.fromJson((String) allBooksResponse.getEntity(), ArrayList.class);

	assertNotNull(allBooksList);
    }

    @Test
    public void testPost() throws MalformedURLException {
	final Book book = new Book("Ein Buch", "Von einem Autor", "mit ISBN");

	final Response creationResponse = sut.createBook(gson.toJson(book));
	assertNotNull("Response after book creation was null!", creationResponse);
    	assertEquals(Response.Status.CREATED.getStatusCode(), creationResponse.getStatus());

	final String location = creationResponse.getHeaderString("Location");

	assertNotNull(location);
	assertNotNull("Is not an URI", new URL(location));
    }

    @Test
    public void testGetByBarcode() {
	final Book book = new Book("Ein Buch", "Von einem Autor", "asdasdag3f234v42");

	// store
	final Response creationResponse = sut.createBook(gson.toJson(book));
	assertNotNull(creationResponse);
	assertEquals(Response.Status.CREATED.getStatusCode(), creationResponse.getStatus());

	// retrieve
	final String location = creationResponse.getHeaderString("Location");
	assertTrue("Created Response Header Location Does not end with barcode", location.endsWith(book.getIsbn()));

	Response storedDiscResponse = sut.getBookByIsbn(book.getIsbn());
	assertEquals(Response.Status.OK.getStatusCode(), storedDiscResponse.getStatus());

	Book storedDisc = (Book) storedDiscResponse.getEntity();

	assertEquals(book, storedDisc);
    }


    @Test
    public void testUpdate() {
	final Book book = new Book("Ein Buch", "Von einem Autor", "135t4ghergdfh56g");

	// create
	final Response creationResponse = sut.createBook(gson.toJson(book));
	assertNotNull(creationResponse);
	assertEquals(Response.Status.CREATED.getStatusCode(), creationResponse.getStatus());

	// update
	book.setTitle("New asfv nobody ever heard of");
	final Response updatedResponse = sut.updateBook(book.getIsbn(), gson.toJson(book));
	assertNotNull(updatedResponse);
	assertEquals(Response.Status.OK.getStatusCode(), updatedResponse.getStatus());

	Map responseMap = gson.fromJson((String) updatedResponse.getEntity(), Map.class);
	assertTrue("Update response does not contain location key", responseMap.containsKey("location"));
	assertTrue("Bad uri after updated Disc", ((String) responseMap.get("location")).endsWith(book.getIsbn()));

	// retrieve
	final Response afterUpdateResponse = sut.getBookByIsbn(book.getIsbn());
	assertNotNull(afterUpdateResponse);
	assertEquals(Response.Status.OK.getStatusCode(), afterUpdateResponse.getStatus());

	final Book updated = ((Book) afterUpdateResponse.getEntity());
	assertEquals(book, updated);
    }


    @Test
    public void testBadCreate() {
	assertBadRequest(sut.createBook(" "));
	assertBadRequest(sut.createBook("{}"));
	assertBadRequest(sut.createBook("{'notadisc':'mothafucka'}"));
	assertBadRequest(sut.createBook(""));
	assertBadRequest(sut.createBook("{{{{{{}"));
    }


    @Test
    public void testBadUpdate() {
	assertBadRequest(sut.updateBook("", "{}"));
	assertNotFound(  sut.updateBook("4444", "{}"));
	assertNotFound(  sut.updateBook("123123123", "//2{}"));

	assertBadRequest(sut.updateBook("7777", "{#####}")); // 7777 stored previously, if fails, start again :)
	assertBadRequest(sut.updateBook("", "{}"));
    }


    @Test
    public void testBadGetByBarcode() {
	assertBadRequest(sut.getBookByIsbn(""));
	assertNotFound(sut.getBookByIsbn("peij32900jvnmekmvd"));
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
