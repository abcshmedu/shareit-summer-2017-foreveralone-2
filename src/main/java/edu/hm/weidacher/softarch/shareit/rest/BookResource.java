package edu.hm.weidacher.softarch.shareit.rest;

import java.util.Collection;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
            return Response.status(Response.Status.BAD_REQUEST).build();
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
	    return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
	}

    }

}
