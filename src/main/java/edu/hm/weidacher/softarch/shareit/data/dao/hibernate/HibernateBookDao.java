package edu.hm.weidacher.softarch.shareit.data.dao.hibernate;

import edu.hm.weidacher.softarch.shareit.data.dao.BookDao;
import edu.hm.weidacher.softarch.shareit.data.model.Book;

/**
 * BookDao + Hibernate.
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class HibernateBookDao extends AbstractHibernateUpdatableDao<Book> implements BookDao {

    /**
     * Ctor.
     */
    public HibernateBookDao() {
	super(Book.class);
    }

    /**
     * Returns a book that is registered with the given isbn.
     * <p>
     * If multiple books exist with the isbn given, only one is returned.
     * Which one is undefined!
     *
     * @param isbn isbn query
     * @return a book or null if none could be found
     * @throws NullPointerException if isbn is null
     */
    @Override
    public Book getByIsbn(String isbn) {
	return getByExtractor(Book::getIsbn, isbn);
    }
}
