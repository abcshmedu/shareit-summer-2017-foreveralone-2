package edu.hm.weidacher.softarch.shareit.util.di;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import edu.hm.weidacher.softarch.shareit.data.Datastore;
import edu.hm.weidacher.softarch.shareit.data.SimpleDatastore;
import edu.hm.weidacher.softarch.shareit.data.dao.BookDao;
import edu.hm.weidacher.softarch.shareit.data.dao.Dao;
import edu.hm.weidacher.softarch.shareit.data.database.Database;
import edu.hm.weidacher.softarch.shareit.data.database.SimpleDatabase;

/**
 * Initializes the injector and makes it available for the project.
 *
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class DIUtil extends GuiceServletContextListener {

    /**
     * The injector.
     */
    private static final Injector injector;

    /*
      Initialize the injector.
      Using a static initializer here to enable more complex initialization in the future without further refactorings.
     */
    static {
        injector = Guice.createInjector(new ServletModule() {

	    /**
	     * Configure all bindings for the injector.
	     */
	    @Override
	    protected void configureServlets() {
		bind(Database.class).to(SimpleDatabase.class);
                bind(Datastore.class).to(SimpleDatastore.class);
                bind(Dao.class).annotatedWith(Names.named("BookDao")).to(BookDao.class);
	    }

	});
    }

    /**
     * Returns the injector.
     * @return injector
     */
    @Override
    protected Injector getInjector() {
	return injector;
    }

    /**
     * Returns the injector.
     *
     * Required for guice <=> HL2 bridge.
     *
     * @return injector
     */
    public static Injector getInjectorStatic() {
        return injector;
    }
}
