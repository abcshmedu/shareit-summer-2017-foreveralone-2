package edu.hm.weidacher.softarch.shareit.data.dao;

import edu.hm.weidacher.softarch.shareit.data.model.Book;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public interface BookDao extends UpdatableDao<Book> {

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
    Book getByIsbn(String isbn);

}
