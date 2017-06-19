package edu.hm.weidacher.softarch.shareit.data.dao.simple;

import edu.hm.weidacher.softarch.shareit.data.dao.BookDao;
import edu.hm.weidacher.softarch.shareit.data.model.Book;

/**
 * A Dao for the book model.
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class SimpleBookDao extends SimpleAbstractUpdatableDao<Book> implements BookDao {

    /**
     * Ctor for a new SimpleBookDao.
     */
    public SimpleBookDao() {
	super(Book.class);
    }

    /**
     * Returns a book that is registered with the given isbn.
     *
     * If multiple books exist with the isbn given, only one is returned.
     *  Which one is undefined!
     *
     * @param isbn isbn query
     * @return a book or null if none could be found
     * @throws NullPointerException if isbn is null
     */
    @Override
    public Book getByIsbn(String isbn) {
        if (isbn == null) {
            throw new NullPointerException("ISBN may not be null when querying by it!");
	}

	return getDatabase().stream()
	    .filter(book -> isbn.equals(book.getIsbn()))
	    .findFirst()
	    .orElse(null);
    }

    /**
     * Methods that is called before the model is actually persisted.
     *
     * @param model the model that can be modified prior to persisting
     */
    @Override
    protected void preStore(Book model) {
	super.preStore(model);
    }
}
