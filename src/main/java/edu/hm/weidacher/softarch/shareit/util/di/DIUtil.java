package edu.hm.weidacher.softarch.shareit.util.di;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import edu.hm.weidacher.softarch.shareit.data.Datastore;
import edu.hm.weidacher.softarch.shareit.data.SimpleDatastore;
import edu.hm.weidacher.softarch.shareit.data.dao.AccountDao;
import edu.hm.weidacher.softarch.shareit.data.dao.BookDao;
import edu.hm.weidacher.softarch.shareit.data.dao.CopyDao;
import edu.hm.weidacher.softarch.shareit.data.dao.DiscDao;
import edu.hm.weidacher.softarch.shareit.data.dao.hibernate.HibernateAccountDao;
import edu.hm.weidacher.softarch.shareit.data.dao.hibernate.HibernateBookDao;
import edu.hm.weidacher.softarch.shareit.data.dao.hibernate.HibernateCopyDao;
import edu.hm.weidacher.softarch.shareit.data.dao.hibernate.HibernateDiscDao;
import edu.hm.weidacher.softarch.shareit.data.database.Database;
import edu.hm.weidacher.softarch.shareit.data.database.SimpleDatabase;
import edu.hm.weidacher.softarch.shareit.util.HibernateUtil;

/**
 * Initializes the injector and makes it available for the project.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class DIUtil extends GuiceServletContextListener {

    /**
     * The injector.
     */
    private static Injector injector;

    /**
     * Returns the injector.
     *
     * @return injector
     */
    @Override
    protected Injector getInjector() {
	return getInjectorStatic();
    }

    /**
     * Returns the injector.
     * <p>
     * Required for Guice <=> HL2 bridge.
     *
     * Performing a lazy initialization to prevent Hibernate from booting when it's not required.
     *
     * @return injector
     */
    public static Injector getInjectorStatic() {
	if (injector == null) {
	    initializeProductionInjector();
	}

	return injector;
    }

    /**
     * Returns, whether the injector has been initialized yet.
     * @return is injector initialized?
     */
    public static boolean isInjectorInitialized() {
	return injector != null;
    }

    /**
     * Initialize the production injector
     */
    private static void initializeProductionInjector() {
	System.out.println("Initializing production injector");

	// set the new injector
	setInjectorStatic(Guice.createInjector(new ServletModule() {

	    /**
	     * Configure all bindings for the injector.
	     */
	    @Override
	    protected void configureServlets() {
		bind(Database.class).to(SimpleDatabase.class);
		bind(Datastore.class).to(SimpleDatastore.class);
		bind(Session.class).toProvider(HibernateUtil.getSessionProvider());
		bind(SessionFactory.class).toInstance(HibernateUtil.getSessionFactory());

		bind(BookDao.class).to(HibernateBookDao.class);
		bind(CopyDao.class).to(HibernateCopyDao.class);
		bind(AccountDao.class).to(HibernateAccountDao.class);
		bind(DiscDao.class).to(HibernateDiscDao.class);
	    }
	}));
    }

    /**
     * Sets the injector once.
     *
     * When testing invoke this before any other access to this Util to prevent booting Hibernate.
     *
     * @param fresh injector
     */
    public static void setInjectorStatic(Injector fresh) {
	injector = fresh;
    }
}
