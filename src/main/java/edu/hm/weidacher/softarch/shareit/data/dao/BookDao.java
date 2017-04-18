package edu.hm.weidacher.softarch.shareit.data.dao;

import java.util.Collection;
import java.util.UUID;

import edu.hm.weidacher.softarch.shareit.data.database.DatabaseFactory;
import edu.hm.weidacher.softarch.shareit.data.model.Book;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * A Dao for the book model.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class BookDao implements UpdatableDao<Book> {

    private final Collection<Book> database;

    public BookDao() {
        this.database = DatabaseFactory.getDatabase().getCollectionForType(Book.class);
    }

    /**
     * Update an existing model.
     *
     * @param model the model that shall be updated
     * @throws PersistenceException if no model is present under the id of the model
     */
    @Override
    public void update(Book model) throws PersistenceException {
        UUID id = model.getId();
        if (id == null) {
            throw new PersistenceException("Model has no id, can thus not be updated: " + model.toString());
	}

	// is a model present under the id?
	final Book persisted = getById(id);

	if (persisted == null) {
            throw new PersistenceException("Not entity found for id, can thus not be updated: " + model.toString());
	}

	// book is present -> merge it!
        persisted.mergeWith(model);
    }

    /**
     * Return the model corresponding to an id.
     *
     * @param id the id of the model
     * @return the model or null, if the id dies not match any model
     */
    @Override
    public Book getById(UUID id) {
	return database.stream()
	    .filter(book -> id.equals(book.getId()))
	    .findFirst()
	    .orElse(null);
    }

    /**
     * Stores a model in the datastore.
     *
     * @param model the model to persist
     * @throws PersistenceException when a model has already been persisted under the id of the model
     */
    @Override
    public void store(Book model) throws PersistenceException {
	if (getById(model.getId()) != null) {
	    throw new PersistenceException("Book already present under the id!");
	}

	database.add(model);
    }

}
