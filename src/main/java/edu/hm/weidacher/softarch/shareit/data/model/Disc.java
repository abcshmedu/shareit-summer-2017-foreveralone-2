package edu.hm.weidacher.softarch.shareit.data.model;

import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;
import edu.hm.weidacher.softarch.shareit.util.BarcodeUtil;

/**
 * Model of a Disc in the ShareIt application.
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class Disc extends Medium {

    /**
     * The barcode (EAN) of the disc.
     */
    private String barcode;

    /**
     * Director of the disc.
     */
    private String director;

    /**
     * The lower age-limit of this book.
     * People below this age (in years) are not allowed to consume this media.
     */
    private Integer fsk;

    /**
     * Ctor.
     */
    public Disc() {
        super();
        //beans
    }

    /**
     * Ctor.
     * @param title title of the disc
     * @param barcode barcode of the disc
     * @param director director of the disc
     * @param fsk fsk of the disc
     */
    public Disc(String title, String barcode, String director, Integer fsk) {
	super(title);
	this.barcode = barcode;
	this.director = director;
	this.fsk = fsk;
    }

    /**
     * Copy-Ctor.
     *
     * @param other the disc to copy
     */
    public Disc(Disc other) {
        super(other);
        this.barcode = other.getBarcode();
        this.director = other.getDirector();
        this.fsk = other.getFsk();
    }

    /**
     * Returns the barcode of the disc.
     * @return barcode
     */
    public String getBarcode() {
	return barcode;
    }

    /**
     * Sets the barcode of this disc.
     * @param barcode new barcode
     */
    public void setBarcode(String barcode) {
	this.barcode = barcode;
	update();
    }

    /**
     * Returns the director of this disc.
     * @return director
     */
    public String getDirector() {
	return director;
    }

    /**
     * Sets the director of this disc.
     * @param director new director
     */
    public void setDirector(String director) {
	this.director = director;
	update();
    }

    /**
     * Returns the fsk limit of this disk.
     * @return fsk
     */
    public Integer getFsk() {
	return fsk;
    }

    /**
     * Sets the fsk limit of this disk.
     * @param fsk new fsk
     */
    public void setFsk(Integer fsk) {
	this.fsk = fsk;
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

    /**
     * Checks whether this model has all required fields.
     *
     * @throws PersistenceException when invalid
     */
    @Override
    public void validate() throws PersistenceException {
	super.validate();
	if (!BarcodeUtil.isValid(barcode)) {
	    throw new PersistenceException("Barcode missing or invalid");
	}

	if (director == null || director.equals("")) {
	    throw new PersistenceException("Director missing or invalid");
	}

	if (fsk == null || fsk < 0) {
	    throw new PersistenceException("FSK missing or invalid");
	}
    }

    /**
     * Returns a representative string of this entity.
     * @return string with info
     */
    @Override
    public String toString() {
	return "Disc{ " + super.toString()
	    + " barcode='" + barcode + '\''
	    + ", director='" + director + '\''
	    + ", fsk=" + fsk
	    + '}';
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
	if (!(o instanceof Disc)) {
	    return false;
	}
	if (!super.equals(o)) {
	    return false;
	}

	Disc disc = (Disc) o;

	if (getFsk() != disc.getFsk()) {
	    return false;
	}
	if (getBarcode() != null ? !getBarcode().equals(disc.getBarcode()) : disc.getBarcode() != null) {
	    return false;
	}
	return getDirector() != null ? getDirector().equals(disc.getDirector()) : disc.getDirector() == null;
    }

    /**
     * HashCode.
     *
     * @return number giving an easy comparable info about the entity
     */
    @Override
    public int hashCode() {
	int result = super.hashCode();
	result = 31 * result + (getBarcode() != null ? getBarcode().hashCode() : 0);
	result = 31 * result + (getDirector() != null ? getDirector().hashCode() : 0);
	result = 31 * result + getFsk();
	return result;
    }
}
