package edu.hm.weidacher.softarch.shareit.rest;

import java.util.Collection;

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
import edu.hm.weidacher.softarch.shareit.util.IsbnUtil;

/**
 * Rest resource for interaction with books.
 *
 * The following methods are defined:
 *  - GET 	/media/books		list all books
 *  - POST	/media/books		add a new book
 *  - PUT	/media/books		update a special book, identified via model content
 *  - GET	/media/books/{isbn}	get a special book
 *  - PUT	/media/books/{isbn}	update a special book, identified via path
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
@Path("/media/books")
public class BookResource extends AbstractResource{

    /**
     * Connection point to the database.
     */
    private BookDao bookDao;

    /**
     * Ctor.
     */
    public BookResource () {
        super();
	try {
	    bookDao = (BookDao) getDatastore().getDao(Book.class);
	} catch (PersistenceException e) {
	    // can't happen
	    e.printStackTrace();
	}
    }

    /**
     * Retrieve all books.
     *
     * @return HTTP Response
     * 		200 : returns a JSON array of all books listed
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks() {
	final Collection<Book> allBooks = bookDao.getAll();
	String allBooksJson = getGson().toJson(allBooks);

	return Response.ok(allBooksJson).build();
    }

    /**
     * Retrieve a book.
     *
     * @param isbn isbn number identifying the book
     * @return HTTP Response
     * 		200 : returns the book as json string
     * 		400 : invalid isbn given
     * 		404 : no book could be found under the isbn
     */
    @GET
    @Path("/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookByIsbn(@PathParam("isbn") String isbn) {
        if (!IsbnUtil.isValid(isbn)) {
            return error(Response.Status.BAD_REQUEST);
	}

	final Book bookByIsbn = bookDao.getByIsbn(isbn);

        if (bookByIsbn == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
	}

	return Response.ok(bookByIsbn).build();
    }

    /**
     * Creates a book from a given model.
     *
     * @param json the book as JSON string
     * @return HTTP Response
     * 		201 : If the book was created successfully. return URI to the entity
     * 		400 : The model contained invalid values
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBook(String json) {
	try {
	    final Book book = getGson().fromJson(json, Book.class);
	    bookDao.store(book);

	    return buildCreatedResponse(book.getIsbn());
	} catch (PersistenceException e) {
	    return error(e);
	} catch (JsonSyntaxException e) {
	    return error("Bad book model given", Response.Status.BAD_REQUEST);
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
        if (!IsbnUtil.isValid(isbn)) {
            return error("Isbn invalid", Response.Status.BAD_REQUEST);
	}

	try {
	    final Book givenBook = getGson().fromJson(json, Book.class);
	    final Book persistedByIsbn = bookDao.getByIsbn(isbn);

	    if (persistedByIsbn == null) {
		return error(Response.Status.NOT_FOUND);
	    }

	    bookDao.update(givenBook, persistedByIsbn.getId());

	    // return link to updated book
	    return okWithUri(getAbsolutePath(givenBook.getIsbn()));

	} catch (JsonSyntaxException jsex) {
	    return error("Bad book model", Response.Status.BAD_REQUEST);
	} catch (PersistenceException e) {
	    return error(e);
	}
    }

}
