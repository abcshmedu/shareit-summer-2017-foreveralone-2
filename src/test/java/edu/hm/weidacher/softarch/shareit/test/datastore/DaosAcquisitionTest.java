package edu.hm.weidacher.softarch.shareit.test.datastore;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;

import edu.hm.weidacher.softarch.shareit.data.Datastore;
import edu.hm.weidacher.softarch.shareit.data.dao.AbstractUpdatableDao;
import edu.hm.weidacher.softarch.shareit.data.dao.Dao;
import edu.hm.weidacher.softarch.shareit.data.model.AbstractModel;
import edu.hm.weidacher.softarch.shareit.data.model.AbstractUpdatableModel;
import edu.hm.weidacher.softarch.shareit.data.model.Book;
import edu.hm.weidacher.softarch.shareit.data.model.Copy;
import edu.hm.weidacher.softarch.shareit.data.model.Disc;
import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class DabsAcquisitionTest {

    private Datastore datastore;
    private AbstractModel illegalModel;
    private AbstractUpdatableModel illegalUpdatableModel;

    @Before
    @SuppressWarnings("unchecked")
    public void setup() {
	datastore = Datastore.factory().getDatastore();
	illegalModel = new AbstractModel() {
	};
	illegalUpdatableModel = new AbstractUpdatableModel() {
	    @Override
	    public <T extends AbstractUpdatableModel> void mergeWith(T other) throws PersistenceException {
		// wayne
	    }
	};
    }

    @Test
    public void acquireBookDao() throws Exception {
	final Dao<Book> dao = datastore.getDao(Book.class);
	assertNotNull(dao);
	// trivial
	assertThat(dao, new TypeSafeMatcher<Dao<Book>>() {
	    @Override
	    protected boolean matchesSafely(Dao<Book> bookDao) {
		return bookDao instanceof AbstractUpdatableDao;
	    }

	    @Override
	    public void describeTo(Description description) {
	        description.appendText("AbstractUpdatableDao");
	    }
	});
    }

    @Test
    public void acquireUpdatableCopyDao() throws Exception {
	final Dao<Copy> dao = datastore.getUpdatableDao(Copy.class);
	assertNotNull(dao);
    }

    @Test
    public void acquireUpdatableDiscDao() throws Exception {
	final Dao<Disc> dao = datastore.getUpdatableDao(Disc.class);
	assertNotNull(dao);
    }

    @Test
    public void acquireUpdatableBookDao() throws Exception {
	final Dao<Book> dao = datastore.getUpdatableDao(Book.class);
	assertNotNull(dao);
	// trivial
	assertThat(dao, new TypeSafeMatcher<Dao<Book>>() {
	    @Override
	    protected boolean matchesSafely(Dao<Book> bookDao) {
		return bookDao instanceof AbstractUpdatableDao;
	    }

	    @Override
	    public void describeTo(Description description) {
		description.appendText("AbstractUpdatableDao");
	    }
	});
    }

    @Test
    public void acquireCopyDao() throws Exception {
	final Dao<Copy> dao = datastore.getDao(Copy.class);
	assertNotNull(dao);
    }

    @Test
    public void acquireDiscDao() throws Exception {
	final Dao<Disc> dao = datastore.getDao(Disc.class);
	assertNotNull(dao);
    }

    @Test(expected = PersistenceException.class)
    public void acquireIllegalDao() throws Exception {
	datastore.getDao(illegalModel.getClass());
    }

    @Test(expected = PersistenceException.class)
    public void acquireIllegalUpdatableDao() throws Exception{
        datastore.getUpdatableDao(illegalUpdatableModel.getClass());
    }

}
