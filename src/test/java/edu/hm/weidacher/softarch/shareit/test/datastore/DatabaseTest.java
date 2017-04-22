package edu.hm.weidacher.softarch.shareit.test.datastore;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.hm.weidacher.softarch.shareit.data.database.Database;
import edu.hm.weidacher.softarch.shareit.data.database.SimpleDatabase;
import edu.hm.weidacher.softarch.shareit.data.model.Book;
import edu.hm.weidacher.softarch.shareit.data.model.Copy;
import edu.hm.weidacher.softarch.shareit.data.model.Disc;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class DatabaseTest {
    
    private Database database;
    
    private List<Book> cBooks = Arrays.asList(new Book(), new Book(), new Book());
    private List<Disc> cDiscs = Arrays.asList(new Disc(), new Disc(), new Disc());
    private List<Copy> cCopies = Arrays.asList(new Copy(), new Copy(), new Copy());
    
    @Before
    public void setup() throws PersistenceException {
        database = new SimpleDatabase();
	
        final Collection<Book> books = database.getCollectionForType(Book.class);
	books.addAll(cBooks);

	final Collection<Disc> discs = database.getCollectionForType(Disc.class);
	discs.addAll(cDiscs);

	final Collection<Copy> copies = database.getCollectionForType(Copy.class);
	copies.addAll(cCopies);
	
	database.persist();
	
	database = null;
	database = new SimpleDatabase();
	database.load();
    }

    @Test
    public void stillContainsBooks() {
	final Collection<Book> books = database.getCollectionForType(Book.class);
	assertTrue(cBooks.containsAll(books));
    }

    @Test
    public void stillContainsDiscs() {
	final Collection<Disc> Discs = database.getCollectionForType(Disc.class);
	assertTrue(cDiscs.containsAll(Discs));
    }

    @Test
    public void stillContainsCopys() {
	final Collection<Copy> copies = database.getCollectionForType(Copy.class);
	assertTrue(cCopies.containsAll(copies));
    }
    
    
    
}
