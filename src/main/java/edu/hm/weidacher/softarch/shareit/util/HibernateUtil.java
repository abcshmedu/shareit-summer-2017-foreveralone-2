package edu.hm.weidacher.softarch.shareit.util;

import java.util.logging.Logger;

import javax.inject.Provider;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Utility Class dealing with Hibernate specific stuff.
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class HibernateUtil implements ServletContextListener {

    /**
     * Logger.
     */
    private static final Logger LOG = Logger.getAnonymousLogger();

    /**
     * Name of the persistence unit of the application.
     */
    public static final String PERSISTENCE_UNIT_NAME = "share-it-unit";

    /**
     * Hibernate SessionFactory.
     * Users can claim an EntityManager (Session) from it.
     */
    private static final SessionFactory SESSION_FACTORY = new Configuration().configure().buildSessionFactory();

    /**gi
     * Returns the SessionFactory.
     * @return sessionFactory
     */
    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    /**
     * Closes the SessionFactory.
     */
    private static void shutdown() {
        SESSION_FACTORY.close();
    }

    /**
     * Returns a Session provider.
     * @return providing the session like a champ
     */
    public static Provider<Session> getSessionProvider() {
	return () -> getSessionFactory().getCurrentSession();
    }

    /**
     * Called when the application-server boots.
     * @param servletContextEvent context
     */
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
	LOG.info("Hibernate Context initialized");
    }

    /**
     * Called when the application-server is destroyed.
     * @param servletContextEvent context
     */
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
	shutdown();
	LOG.info("Hibernate Context destroyed");
    }

}
