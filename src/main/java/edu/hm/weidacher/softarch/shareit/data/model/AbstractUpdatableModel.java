package edu.hm.weidacher.softarch.shareit.data.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * Base class  for all Models in the ShareIt application that can be updated.
 * @author Simon Weidacher <weidache@hm.edu>
 */
@MappedSuperclass
public abstract class AbstractUpdatableModel extends AbstractModel {

    /**
     * The date/time of the last update process.
     */
    private Date lastUpdate;

    /**
     * Ctor.
     */
    public AbstractUpdatableModel() {
        super();
        update();
    }

    /**
     * Merges the data of the other Model with this.
     *
     * All fields of the other model will be written to this one, except
     *  for null fields, the original data remains
     *
     * @param <T> the type of the model to merge with
     * @param other the other model
     * @throws PersistenceException when the type of other does is not compatible to the type of this model
     */
    public abstract <T extends AbstractUpdatableModel> void mergeWith(T other) throws PersistenceException;

    /**
     * Returns the date/time of the last update.
     * @return lud
     */
    public Date getLastUpdate() {
	return lastUpdate;
    }

    /**
     * Updates the time of the last update.
     */
    public void update() {
        setLastUpdate(new Date());
    }

    /**
     * Sets the time of the last update.
     * @param lastUpdate lud time
     */
    public void setLastUpdate(Date lastUpdate) {
	this.lastUpdate = lastUpdate;
    }

    /**
     * Returns a representative string of this entity.
     * @return string with info
     */
    @Override
    public String toString() {
	return super.toString() + " lastUpdate=" + lastUpdate;
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
	if (!(o instanceof AbstractUpdatableModel)) {
	    return false;
	}
	if (!super.equals(o)) {
	    return false;
	}

	return true;
/*
	AbstractUpdatableModel that = (AbstractUpdatableModel) o;

	return getLastUpdate() != null ? getLastUpdate().equals(that.getLastUpdate()) : that.getLastUpdate() == null;
*/
    }

    /**
     * HashCode.
     *
     * @return number giving an easy comparable info about the entity
     */
    @Override
    public int hashCode() {
	int result = super.hashCode();
	result = 31 * result + (getLastUpdate() != null ? getLastUpdate().hashCode() : 0);
	return result;
    }
}
