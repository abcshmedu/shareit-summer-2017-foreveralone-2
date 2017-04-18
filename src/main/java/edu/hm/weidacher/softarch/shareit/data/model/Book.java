package edu.hm.weidacher.softarch.shareit.data.model;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class Book extends Medium {

    private String author;
    
    private String isbn;

    public Book() {
	super();
	// beans
    }

    public Book(String title, String author, String isbn) {
	super(title);
	this.author = author;
	this.isbn = isbn;
    }

    public String getAuthor() {
	return author;
    }

    public void setAuthor(String author) {
	this.author = author;
    }

    public String getIsbn() {
	return isbn;
    }

    public void setIsbn(String isbn) {
	this.isbn = isbn;
    }

    @Override
    public String toString() {
	return "Book{ " + super.toString() +
	    " author='" + author + '\'' +
	    ", isbn='" + isbn + '\'' +
	    '}';
    }

    @Override
    public boolean equals(Object o) {
	if (this == o) return true;
	if (!(o instanceof Book)) return false;
	if (!super.equals(o)) return false;

	Book book = (Book) o;

	if (getAuthor() != null ? !getAuthor().equals(book.getAuthor()) : book.getAuthor() != null) return false;
	return getIsbn() != null ? getIsbn().equals(book.getIsbn()) : book.getIsbn() == null;
    }

    @Override
    public int hashCode() {
	int result = super.hashCode();
	result = 31 * result + (getAuthor() != null ? getAuthor().hashCode() : 0);
	result = 31 * result + (getIsbn() != null ? getIsbn().hashCode() : 0);
	return result;
    }
}
