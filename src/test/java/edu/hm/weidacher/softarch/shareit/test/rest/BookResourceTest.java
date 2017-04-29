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
import edu.hm.weidacher.softarch.shareit.rest.BookResource;


/**
 * This TestCase probes the REST-API available via HTTP.
 * Thus, the performed test do not affect the codecoverage...
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class BookResourceTest {

    private BookResource sut;
    private Gson gson;

    @Before
    public void setup() {
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

}
