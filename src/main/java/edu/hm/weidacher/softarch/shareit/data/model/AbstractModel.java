package edu.hm.weidacher.softarch.shareit.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * Base class for all Models in the ShareIt application.
 * @author Simon Weidacher <weidache@hm.edu>
 */
public abstract class AbstractModel implements Serializable {

    /**
     * The id of this entity.
     */
    private final UUID id;

    /**
     * The date of creation of this entity.
     */
    private final Date creationDate;

    /**
     * Ctor.
     */
    public AbstractModel() {
        id = UUID.randomUUID();
        creationDate = new Date();
    }

    /**
     * Returns the id of this entity.
     *
     * @return unique id
     */
    public UUID getId() {
	return id;
    }

    /**
     * Returns the date of creation.
     *
     * @return date
     */
    public Date getCreationDate() {
	return creationDate;
    }

    /**
     * Checks whether this model has all required fields.
     * @throws PersistenceException when invalid
     */
    public abstract void validate() throws PersistenceException;

    /**
     * Returns a representative string of this entity.
     * @return string with info
     */
    @Override
    public String toString() {
	return "id="
	    + id
	    + ", creationDate=" + creationDate;
    }

    /**
     * Equals.
     *
     * @param o object to compare to
     * @return whether the two objects have the same value
     */
    @Override
    public boolean equals(Object o) {
	if (this == o) {
	    return true;
	}
	if (!(o instanceof AbstractModel)) {
	    return false;
	}

	return true;
/*
	AbstractModel that = (AbstractModel) o;

	if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) {
	    return false;
	}
	return getCreationDate() != null ? getCreationDate().equals(that.getCreationDate()) : that.getCreationDate() == null;
*/
    }

    /**
     * HashCode.
     *
     * @return number giving an easy comparable info about the entity
     */
    @Override
    public int hashCode() {
	int result = getId() != null ? getId().hashCode() : 0;
	result = 31 * result + (getCreationDate() != null ? getCreationDate().hashCode() : 0);
	return result;
    }
}
