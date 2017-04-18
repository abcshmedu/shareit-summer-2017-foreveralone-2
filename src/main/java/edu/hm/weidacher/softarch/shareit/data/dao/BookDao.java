package edu.hm.weidacher.softarch.shareit.data.dao;

import java.util.UUID;

import edu.hm.weidacher.softarch.shareit.data.model.Book;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * A Dao for the book model.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class BookDao implements UpdatableDao<Book> {

    /**
     * Update an existing model.
     *
     * @param model the model that shall be updated
     * @throws PersistenceException if no model is present under the id of the model
     */
    @Override
    public void update(Book model) throws PersistenceException {

    }

    /**
     * Return the model corresponding to an id.
     *
     * @param id the id of the model
     * @return the model or null, if the id dies not match any model
     */
    @Override
    public Book getById(UUID id) {
	return null;
    }

    /**
     * Stores a model in the datastore.
     *
     * @param model the model to persist
     * @throws PersistenceException when a model has already been persisted under the id of the model
     */
    @Override
    public void store(Book model) throws PersistenceException {

    }

}
