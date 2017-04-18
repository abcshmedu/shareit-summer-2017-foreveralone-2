package edu.hm.weidacher.softarch.shareit.data.model;

import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class Disc extends Medium {

    private String barcode;

    private String director;

    private Integer fsk;

    public Disc() {
        super();
        //beans
    }

    public Disc(String title, String barcode, String director, Integer fsk) {
	super(title);
	this.barcode = barcode;
	this.director = director;
	this.fsk = fsk;
    }

    public String getBarcode() {
	return barcode;
    }

    public void setBarcode(String barcode) {
	this.barcode = barcode;
    }

    public String getDirector() {
	return director;
    }

    public void setDirector(String director) {
	this.director = director;
    }

    public Integer getFsk() {
	return fsk;
    }

    public void setFsk(Integer fsk) {
	this.fsk = fsk;
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
	if (!(other instanceof Disc)) {
	    throw new PersistenceException("Incompatible type given for merging...");
	}

        super.mergeWith(other);

	Disc o = (Disc)other;

	if (o.getBarcode() != null) {
	    this.setBarcode(o.getBarcode());
	}

	if (o.getDirector() != null) {
	    this.setDirector(o.getDirector());
	}

	if (o.getFsk() != null) {
	    this.setFsk(o.getFsk());
	}
    }

    @Override
    public String toString() {
	return "Disc{ " + super.toString() +
	    " barcode='" + barcode + '\'' +
	    ", director='" + director + '\'' +
	    ", fsk=" + fsk +
	    '}';
    }

    @Override
    public boolean equals(Object o) {
	if (this == o) return true;
	if (!(o instanceof Disc)) return false;
	if (!super.equals(o)) return false;

	Disc disc = (Disc) o;

	if (getFsk() != disc.getFsk()) return false;
	if (getBarcode() != null ? !getBarcode().equals(disc.getBarcode()) : disc.getBarcode() != null) return false;
	return getDirector() != null ? getDirector().equals(disc.getDirector()) : disc.getDirector() == null;
    }

    @Override
    public int hashCode() {
	int result = super.hashCode();
	result = 31 * result + (getBarcode() != null ? getBarcode().hashCode() : 0);
	result = 31 * result + (getDirector() != null ? getDirector().hashCode() : 0);
	result = 31 * result + getFsk();
	return result;
    }
}
