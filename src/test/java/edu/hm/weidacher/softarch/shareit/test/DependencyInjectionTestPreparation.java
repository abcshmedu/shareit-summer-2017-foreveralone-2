package edu.hm.weidacher.softarch.shareit.test;

import com.google.inject.Guice;
import com.google.inject.servlet.ServletModule;

import edu.hm.weidacher.softarch.shareit.data.Datastore;
import edu.hm.weidacher.softarch.shareit.data.SimpleDatastore;
import edu.hm.weidacher.softarch.shareit.data.dao.AccountDao;
import edu.hm.weidacher.softarch.shareit.data.dao.BookDao;
import edu.hm.weidacher.softarch.shareit.data.dao.CopyDao;
import edu.hm.weidacher.softarch.shareit.data.dao.DiscDao;
import edu.hm.weidacher.softarch.shareit.data.dao.simple.SimpleAccountDao;
import edu.hm.weidacher.softarch.shareit.data.dao.simple.SimpleBookDao;
import edu.hm.weidacher.softarch.shareit.data.dao.simple.SimpleCopyDao;
import edu.hm.weidacher.softarch.shareit.data.dao.simple.SimpleDiscDao;
import edu.hm.weidacher.softarch.shareit.data.database.Database;
import edu.hm.weidacher.softarch.shareit.data.database.SimpleDatabase;
import edu.hm.weidacher.softarch.shareit.util.di.DIUtil;

/**
 * Basic JUnit test case.
 *
 * I will simulate global setup here (but it will be run for each indivual case that extends this case.)
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class DependencyInjectionTestPreparation {

    /**
     * Sets the correct injector.
     *
     * Be sure that this is the first call in your @Before method.
     *
     * Synchronized, because i want to make sure that only one injector is created.
     *  (Guice throws errors when there are multiple).
     */
    protected synchronized void setupDependencyInjection() {
        if (!DIUtil.isInjectorInitialized()) {
	    DIUtil.setInjectorStatic(Guice.createInjector(new ServletModule() {
		@Override
		protected void configureServlets() {
		    bind(Database.class).to(SimpleDatabase.class);
		    bind(Datastore.class).to(SimpleDatastore.class);

		    bind(BookDao.class).to(SimpleBookDao.class);
		    bind(CopyDao.class).to(SimpleCopyDao.class);
		    bind(AccountDao.class).to(SimpleAccountDao.class);
		    bind(DiscDao.class).to(SimpleDiscDao.class);
		}
		    }));

	}
    }



}
