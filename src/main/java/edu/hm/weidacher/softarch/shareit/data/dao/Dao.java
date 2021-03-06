package edu.hm.weidacher.softarch.shareit.data.dao;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Function;

import javax.validation.constraints.NotNull;

import edu.hm.weidacher.softarch.shareit.data.model.AbstractModel;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * A Dao for all entity.
 *
 * As standard entity aren't updatable, we can only store new objects or receive them.
 *
 * @param <T> declares the model, the dao handles
 * @author Simon Weidacher <weidache@hm.edu>
 */
public interface Dao< T extends AbstractModel > {

    /**
     * Return the entity corresponding to an id.
     *
     * @param id the id of the entity
     * @return the entity or null, if the id dies not match any entity
     */
    T getById(@NotNull UUID id);

    /**
     * Return the identity corresponding to the identifier.
     *
     * @param keyExtractor function, extracting the key from the handled model
     * @param identifier identifies the desired entity
     * @param <KEY> type of the key
     * @return entity or null, if no entity could be matched to the identifier
     * @throws NullPointerException if one of the parameters was null
     */
    <KEY> T getByExtractor(@NotNull Function<T, KEY> keyExtractor, @NotNull KEY identifier);

    /**
     * Stores an entity in the datastore.
     *
     * @param model the entity to persist
     * @return id of the created entity
     * @throws PersistenceException when a model has already been persisted under the id of the model
     */
    UUID store(@NotNull T model) throws PersistenceException;

    /**
     * Deletes an entity from the datastore.
     * @param id UUID of the entity to delete
     * @return the entity that has been removed
     * 		or null if there was no entity associated with the id
     * @throws PersistenceException if any errors occur during the operation
     */
    T delete(@NotNull UUID id) throws PersistenceException;

    /**
     * Return all entitys under T.
     * @return collection containing all available entitys
     */
    Collection<T> getAll();

    /**
     * Returns the class, this Dao handles.
     * @return model class
     */
    Class<T> getModelClass();
}
