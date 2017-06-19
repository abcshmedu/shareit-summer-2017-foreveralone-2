package edu.hm.weidacher.softarch.shareit.data.model;

import javax.persistence.Entity;

import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;
import edu.hm.weidacher.softarch.shareit.util.IsbnUtil;

/**
 * Model of a Book in the ShareIt application.
 * @author Simon Weidacher <weidache@hm.edu>
 */
@Entity
public class Book extends Medium {

    /**
     * The author of the book.
     */
    private String author;

    /**
     * The isbn of the book.
     */
    private String isbn;

    /**
     * Ctor.
     */
    public Book() {
	super();
	// beans
    }

    /**
     * Ctor.
     * @param title tile of the book
     * @param author author of the book
     * @param isbn isbn of the book
     */
    public Book(String title, String author, String isbn) {
	super(title);
	this.author = author;
	this.isbn = IsbnUtil.normalize(isbn);
    }

    /**
     * Copy Ctor.
     * @param other book, the copy will resemble
     */
    public Book(Book other) {
        super(other);
        this.author = other.getAuthor();
        this.isbn = other.getIsbn();
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
	if (other.getClass() != Book.class) {
	    throw new PersistenceException("Incompatible model given for merging!");
	}

	Book o = (Book) other;

	super.mergeWith(other);

	if (o.getAuthor() != null) {
	    this.setAuthor(o.getAuthor());
	}

	if (o.getIsbn() != null) {
	    this.setIsbn(o.getIsbn());
	}
    }

    /**
     * Checks whether this model has all required fields.
     *
     * @throws PersistenceException when is not valid
     */
    @Override
    public void validate() throws PersistenceException {
        super.validate();
	if (author == null || author.equals("")) {
	    throw new PersistenceException("Author missing");
	}

	if (!IsbnUtil.isValid(isbn)) {
	    throw new PersistenceException("Isbn missing or invalid");
	}
    }

    /**
     * Returns the author of this book.
     * @return author
     */
    public String getAuthor() {
	return author;
    }

    /**
     * Sets the author of this book.
     * @param author the new author
     */
    public void setAuthor(String author) {
	this.author = author;
	update();
    }

    /**
     * Returns the isbn number of this book.
     * @return isbn number
     */
    public String getIsbn() {
	return isbn;
    }

    /**
     * Sets the isbn number of this book.
     * @param isbn the new isbn
     */
    public void setIsbn(String isbn) {
	this.isbn = IsbnUtil.normalize(isbn);
	update();
    }

    /**
     * Returns a representative string of this entity.
     * @return string with info
     */
    @Override
    public String toString() {
	return "Book{ " + super.toString()
	    + " author='" + author + '\''
	    + ", isbn='" + isbn + '\''
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
	if (!(o instanceof Book)) {
	    return false;
	}
	if (!super.equals(o)) {
	    return false;
	}

	Book book = (Book) o;

	if (getAuthor() != null ? !getAuthor().equals(book.getAuthor()) : book.getAuthor() != null) {
	    return false;
	}
	return getIsbn() != null ? getIsbn().equals(book.getIsbn()) : book.getIsbn() == null;
    }

    /**
     * HashCode.
     *
     * @return number giving an easy comparable info about the entity
     */
    @Override
    public int hashCode() {
	int result = super.hashCode();
	result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
	result = 31 * result + (getIsbn() != null ? getIsbn().hashCode() : 0);
	return result;
    }
}
