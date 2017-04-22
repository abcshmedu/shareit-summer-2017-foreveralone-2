package edu.hm.weidacher.softarch.shareit.rest;

import java.util.Collection;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonSyntaxException;

import edu.hm.weidacher.softarch.shareit.data.dao.BookDao;
import edu.hm.weidacher.softarch.shareit.data.model.Book;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * Rest resource for interaction with books.
 *
 * The following methods are defined:
 *  - GET 	/media/books		list all books
 *  - POST	/media/books		add a new book
 *  - GET	/media/books/{isbn}	get a special book
 *  - PUT	/media/books/{isbn}	update a special book
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
@Path("/media/books")
public class BookResource extends AbstractResource{

    private BookDao bookDao;

    public BookResource () {
        super();
	try {
	    bookDao = (BookDao) getDatastore().getDao(Book.class);
	} catch (PersistenceException e) {
	    // can't happen
	    e.printStackTrace();
	}
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
	final Collection<Book> allBooks = bookDao.getAll();
	String allBooksJson = getGson().toJson(allBooks);

	return Response.ok(allBooksJson).build();
    }

    @GET
    @Path("/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookByIsbn(@PathParam("isbn") String isbn) {
        if (isbn == null || isbn.equals("")) {
            return error(Response.Status.NOT_FOUND);
	}

	final Book bookByIsbn = bookDao.getByIsbn(isbn);

        if (bookByIsbn == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
	}

	return Response.ok(bookByIsbn).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(String json) {
	final Book book = getGson().fromJson(json, Book.class);

	try {
	    UUID id = bookDao.store(book);
	    return buildCreatedResponse(id.toString());

	} catch (PersistenceException e) {
	    return error(e);
	}
    }

    /**
     * Updates an existing book.
     *
     * Only the values title, author & isbn can be updated. All other values are ignored.
     * If the model contains a valid id, the book is updated by the id,
     *  else the model must be identified by an isbn.
     *
     * @param json the model as JSON serialized string
     * @return HTTP Response
     * 		200 - OK: If the update was successful
     * 			  Response entity contains the URI to the updated model
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(String json) {
	try {
	    final Book book = getGson().fromJson(json, Book.class);
	    UUID storeBy = book.getId();

	    // if id is set, update by id
	    if (storeBy != null) {
		bookDao.update(book);
	    }
	    // else update by isbn
	    else if (book.getIsbn() != null) {
		final Book persisted = bookDao.getByIsbn(book.getIsbn());
		storeBy = persisted.getId();
		bookDao.update(book, storeBy);
	    }
	    else {
	        // no identifying information given
		return error("No id or isbn given. You must provide at least one.", Response.Status.BAD_REQUEST);
	    }

	    // return link to updated book
	    return okWithUri(getAbsolutePath(storeBy.toString()));

	} catch (PersistenceException e) {
	    return error(e);
	}
    }

    /**
     * Updates the book, stored with the isbn from the path.
     *
     * @param isbn the isbn identifying the book
     * @param json model of the book containing the updated parameters
     * @return HTTP Response:
     * 		200 : The update was successful + URI to the updated book
     * 		400 : The given model could not be parsed to a Book entity
     * 		404 : No book could be found under the given isbn
     */
    @PUT
    @Path("/{isbn}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBook(@PathParam("isbn") String isbn, String json) {
	try {
	    final Book givenBook = getGson().fromJson(json, Book.class);
	    final Book persistedByIsbn = bookDao.getByIsbn(isbn);

	    if (persistedByIsbn == null) {
		return error(Response.Status.NOT_FOUND);
	    }

	    bookDao.update(givenBook, persistedByIsbn.getId());

	    // return link to updated book
	    return okWithUri(getAbsolutePath(persistedByIsbn.getId().toString()));

	} catch (JsonSyntaxException jsex) {
	    return error("Bad book model", Response.Status.BAD_REQUEST);
	} catch (PersistenceException e) {
	    return error(e);
	}
    }

}
