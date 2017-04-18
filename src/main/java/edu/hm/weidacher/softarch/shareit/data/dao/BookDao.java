package edu.hm.weidacher.softarch.shareit.data.dao;

import edu.hm.weidacher.softarch.shareit.data.model.Book;

/**
 * A Dao for the book model.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class BookDao extends AbstractUpdatableDao<Book> {

    /**
     * Ctor for a new BookDao.
     */
    public BookDao() {
	super(Book.class);
    }

}
