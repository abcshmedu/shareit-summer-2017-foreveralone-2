package edu.hm.weidacher.softarch.shareit.data.model;

import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * Model for a copy in the ShareIt application.
 *
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class Copy extends AbstractUpdatableModel {

    /**
     * The medium, this copy resembles.
     */
    private Medium medium;

    /**
     * The owner of the copy.
     */
    private String owner;

    /**
     * Ctor.
     */
    public Copy() {
	super();
	// beans
    }

    /**
     * Ctor.
     * @param medium medium
     * @param owner owner
     */
    public Copy(Medium medium, String owner) {
        super();
	this.medium = medium;
	this.owner = owner;
    }

    /**
     * Copy ctor.
     * @param other copy the copy will resemble
     */
    public Copy(Copy other) {
        this(other.getMedium(), other.getOwner());
    }

    /**
     * Returns the medium, this copy resembles.
     * @return the medium
     */
    public Medium getMedium() {
	return medium;
    }

    /**
     * Sets the medium.
     * @param medium new medium
     */
    public void setMedium(Medium medium) {
	this.medium = medium;
	update();
    }

    /**
     * Returns the owner of the copy.
     * @return owner
     */
    public String getOwner() {
	return owner;
    }

    /**
     * Sets the owner of the copy.
     * @param owner owner
     */
    public void setOwner(String owner) {
	this.owner = owner;
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
	if (!(other instanceof Copy)) {
	    throw new PersistenceException("Bad model given for merging.");
	}

	Copy o = (Copy)other;

	if (o.getMedium() != null) {
	    this.setMedium(o.getMedium());
	}

	if (o.getOwner() != null) {
	    this.setOwner(o.getOwner());
	}
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
	if (!(o instanceof Copy)) {
	    return false;
	}
	if (!super.equals(o)) {
	    return false;
	}

	Copy copy = (Copy) o;

	if (getMedium() != null ? !getMedium().equals(copy.getMedium()) : copy.getMedium() != null) {
	    return false;
	}
	return getOwner() != null ? getOwner().equals(copy.getOwner()) : copy.getOwner() == null;
    }

    /**
     * HashCode.
     *
     * @return number giving an easy comparable info about the entity
     */
    @Override
    public int hashCode() {
	int result = super.hashCode();
	result = 31 * result + (getMedium() != null ? getMedium().hashCode() : 0);
	result = 31 * result + (getOwner() != null ? getOwner().hashCode() : 0);
	return result;
    }
}
