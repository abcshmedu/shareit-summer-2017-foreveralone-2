package edu.hm.weidacher.softarch.shareit.data.model;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class Disc extends Medium {

    private String barcode;

    private String director;

    private int fsk;

    public Disc() {
        super();
        //beans
    }

    public Disc(String title, String barcode, String director, int fsk) {
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

    public int getFsk() {
	return fsk;
    }

    public void setFsk(int fsk) {
	this.fsk = fsk;
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
