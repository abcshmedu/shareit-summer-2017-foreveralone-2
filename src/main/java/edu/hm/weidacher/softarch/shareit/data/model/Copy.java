package edu.hm.weidacher.softarch.shareit.data.model;

import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class Copy extends AbstractUpdatableModel{

    private Medium medium;

    private String owner;

    public Copy() {
	super();
	// beans
    }

    public Copy(Medium medium, String owner) {
        super();
	this.medium = medium;
	this.owner = owner;
    }

    public Medium getMedium() {
	return medium;
    }

    public void setMedium(Medium medium) {
	this.medium = medium;
    }

    public String getOwner() {
	return owner;
    }

    public void setOwner(String owner) {
	this.owner = owner;
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

    @Override
    public boolean equals(Object o) {
	if (this == o) return true;
	if (!(o instanceof Copy)) return false;
	if (!super.equals(o)) return false;

	Copy copy = (Copy) o;

	if (getMedium() != null ? !getMedium().equals(copy.getMedium()) : copy.getMedium() != null) return false;
	return getOwner() != null ? getOwner().equals(copy.getOwner()) : copy.getOwner() == null;
    }

    @Override
    public int hashCode() {
	int result = super.hashCode();
	result = 31 * result + (getMedium() != null ? getMedium().hashCode() : 0);
	result = 31 * result + (getOwner() != null ? getOwner().hashCode() : 0);
	return result;
    }
}
