package edu.hm.weidacher.softarch.shareit.data.model;

import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public abstract class Medium extends AbstractUpdatableModel{

    private String title;

    public Medium() {
        // beans
    }

    public Medium(String title) {
	this.title = title;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
        update();
	this.title = title;
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

    @Override
    public String toString() {
	return "Medium{" + super.toString() +
	    " title='" + title + "'}";
    }

    @Override
    public boolean equals(Object o) {
	if (this == o) return true;
	if (!(o instanceof Medium)) return false;
	if (!super.equals(o)) return false;

	Medium medium = (Medium) o;

	return getTitle() != null ? getTitle().equals(medium.getTitle()) : medium.getTitle() == null;
    }

    @Override
    public int hashCode() {
	int result = super.hashCode();
	result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
	return result;
    }
}
