package edu.hm.weidacher.softarch.shareit.test.datastore;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import edu.hm.weidacher.softarch.shareit.data.Datastore;
import edu.hm.weidacher.softarch.shareit.data.dao.Dao;
import edu.hm.weidacher.softarch.shareit.data.dao.UpdatableDao;
import edu.hm.weidacher.softarch.shareit.data.model.Book;
import edu.hm.weidacher.softarch.shareit.data.model.Copy;
import edu.hm.weidacher.softarch.shareit.data.model.Disc;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class DaosStorageTest {

    private Datastore datastore;

    private Book cBook;
    private UUID idBook;
    private Disc cDisc;
    private UUID idDisc;
    private Copy cCopy;
    private UUID idCopy;

    @Before
    public void setup() throws Exception{
        datastore = Datastore.factory().getDatastore();

        // store some stuff in the ds
	storeBook();
	storeCopy();
	storeDisc();
    }

    @Test
    public void storeBook() throws Exception {
	final Dao<Book> dao = datastore.getDao(Book.class);
	final Book book = new Book("cool title", "cool author", "12093847-222-212678");
	UUID id = dao.store(book);
	cBook = book;
	assertNotNull(id);
	idBook = id;
    }

    @Test
    public void storeDisc() throws Exception {
	final Dao<Disc> dao = datastore.getDao(Disc.class);
	final Disc disc = new Disc("discy titlely", "--|--||||", "stanley kubrick", 555);
	UUID id = dao.store(disc);
	cDisc = disc;
	assertNotNull(id);
	idDisc = id;
    }

    @Test
    public void storeCopy() throws Exception {
	final Dao<Copy> dao = datastore.getDao(Copy.class);
	final Disc disc = new Disc("asdogub", "0wdgb", "aosubgodg", 11);
	final Copy me = new Copy(disc, "me");
	UUID id = dao.store(me);
	cCopy = me;
	idCopy = id;
    }

    @Test
    public void getBook() throws PersistenceException {
	final Dao<Book> dao = datastore.getDao(Book.class);
	final Collection<Book> all = dao.getAll();

	assertTrue(all.size() >= 1);
	assertTrue(all.contains(cBook));
    }

    @Test
    public void getCopy() throws PersistenceException {
	final Dao<Copy> dao = datastore.getDao(Copy.class);
	final Collection<Copy> all = dao.getAll();

	assertTrue(all.size() >= 1);
	assertTrue(all.contains(cCopy));
    }

    @Test
    public void getDisc() throws PersistenceException {
	final Dao<Disc> dao = datastore.getDao(Disc.class);
	final Collection<Disc> all = dao.getAll();

	assertTrue(all.size() >= 1);
	assertTrue(all.contains(cDisc));
    }
    
    @Test
    public void getBookById() throws Exception {
	final Dao<Book> dao = datastore.getDao(Book.class);
	assertEquals(dao.getById(idBook), cBook);
    }

    @Test
    public void getDiscById() throws Exception {
	final Dao<Disc> dao = datastore.getDao(Disc.class);
	assertEquals(dao.getById(idDisc), cDisc);
    }

    @Test
    public void getCopyById() throws Exception {
	final Dao<Copy> dao = datastore.getDao(Copy.class);
	assertEquals(dao.getById(idCopy), cCopy);
    }

    @Test
    public void storeBookTwice() throws Exception {
	final Dao<Book> dao = datastore.getDao(Book.class);
	UUID id = dao.store(cBook);
	assertNotEquals(id, cBook.getId());
    }

    @Test
    public void storeCopyTwice() throws Exception {
	final Dao<Copy> dao = datastore.getDao(Copy.class);
	UUID id = dao.store(cCopy);
	assertNotEquals(id, cCopy.getId());
    }

    @Test
    public void storeDiscTwice() throws Exception {
	final Dao<Disc> dao = datastore.getDao(Disc.class);
	UUID id = dao.store(cDisc);
	assertNotEquals(id, cDisc.getId());
    }

    @Test
    public void updateBook() throws PersistenceException {
	final UpdatableDao<Book> updatableDao = datastore.getUpdatableDao(Book.class);

	cBook.setTitle("something different");

	updatableDao.update(cBook, idBook);
    }
    
    @Test
    public void updateDisc() throws PersistenceException {
	final UpdatableDao<Disc> updatableDao = datastore.getUpdatableDao(Disc.class);

	cDisc.setTitle("something different");

	updatableDao.update(cDisc, idDisc);
    }
    
    @Test
    public void updateCopy() throws PersistenceException {
	final UpdatableDao<Copy> updatableDao = datastore.getUpdatableDao(Copy.class);

	cCopy.setOwner("something different");

	updatableDao.update(cCopy, idCopy);
    }

    @Test(expected = PersistenceException.class)
    public void updateBookMissingId() throws PersistenceException {
	Book failyBook = new Book("fail", "fail", "fail") {
	    @Override
	    public UUID getId() {
		return null;
	    };
	};

	datastore.getUpdatableDao(Book.class).update(failyBook);
    }

    @Test(expected = PersistenceException.class)
    public void updateNewBook() throws PersistenceException {
	Book failyBook = new Book("fail", "fail", "fail");

	datastore.getUpdatableDao(Book.class).update(failyBook);
    }

    @Test(expected = PersistenceException.class)
    public void updateCopyMissingId() throws PersistenceException {
	Copy failyCopy = new Copy() {
	    @Override
	    public UUID getId() {
		return null;
	    };
	};

	datastore.getUpdatableDao(Copy.class).update(failyCopy);
    }

    @Test(expected = PersistenceException.class)
    public void updateNewCopy() throws PersistenceException {
	Copy failyCopy = new Copy();

	datastore.getUpdatableDao(Copy.class).update(failyCopy);
    }

    @Test(expected = PersistenceException.class)
    public void updateDiscMissingId() throws PersistenceException {
	Disc failyDisc = new Disc() {
	    @Override
	    public UUID getId() {
		return null;
	    };
	};

	datastore.getUpdatableDao(Disc.class).update(failyDisc);
    }

    @Test(expected = PersistenceException.class)
    public void updateNewDisc() throws PersistenceException {
	Disc failyDisc = new Disc();

	datastore.getUpdatableDao(Disc.class).update(failyDisc);
    }

}
