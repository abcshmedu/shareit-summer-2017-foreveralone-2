package edu.hm.weidacher.softarch.shareit.data.model;

import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * Model for media in the ShareIt application.
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public abstract class Medium extends AbstractUpdatableModel {

    /**
     * Title of the media.
     */
    private String title;

    /**
     * Ctor.
     */
    public Medium() {
        // beans
    }

    /**
     * Ctor.
     * @param title title of the media
     */
    public Medium(String title) {
	this.title = title;
    }

    /**
     * Copy ctor.
     * @param other the Medium the copy resembles
     */
    public Medium(Medium other) {
        this(other.getTitle());
    }

    /**
     * Returns the title of the media.
     * @return title
     */
    public String getTitle() {
	return title;
    }

    /**
     * Sets the title of the media.
     * @param title new title
     */
    public void setTitle(String title) {
	this.title = title;
        update();
    }

    /**
     * Merges the data of the other Model with this.
     * <p>
     * All fields of the other model will be written to this one, except
     * for null fields, the original data remains
     *
     * @param other the other model
     * @throws PersistenceException when the type of other does is not compatible to the type of this model
     */
    @Override
    public <T extends AbstractUpdatableModel> void mergeWith(T other) throws PersistenceException {
	if (!(other instanceof Medium)) {
	    throw new PersistenceException("Bad model type given for merging.");
	}

	Medium o = (Medium) other;

	if (o.getTitle() != null) {
	    this.setTitle(o.getTitle());
	}
    }

    /**
     * Returns a representative string of this entity.
     * @return string with info
     */
    @Override
    public String toString() {
	return "Medium{" + super.toString()
	    + " title='" + title + "'}";
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
	if (!(o instanceof Medium)) {
	    return false;
	}
	if (!super.equals(o)) {
	    return false;
	}

	Medium medium = (Medium) o;

	return getTitle() != null ? getTitle().equals(medium.getTitle()) : medium.getTitle() == null;
    }

    /**
     * HashCode.
     *
     * @return number giving an easy comparable info about the entity
     */
    @Override
    public int hashCode() {
	int result = super.hashCode();
	result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
	return result;
    }
}
