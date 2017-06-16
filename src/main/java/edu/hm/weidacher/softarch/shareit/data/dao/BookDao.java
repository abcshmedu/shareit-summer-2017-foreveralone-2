package edu.hm.weidacher.softarch.shareit.data.dao;

import edu.hm.weidacher.softarch.shareit.data.model.Book;
import edu.hm.weidacher.softarch.shareit.util.IsbnUtil;

/**
 * A Dao for the book model.
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class BookDao extends AbstractUpdatableDao<Book> {

    /**
     * Ctor for a new BookDao.
     */
    public BookDao() {
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

	model.setIsbn(IsbnUtil.normalize(model.getIsbn()));
    }
}
