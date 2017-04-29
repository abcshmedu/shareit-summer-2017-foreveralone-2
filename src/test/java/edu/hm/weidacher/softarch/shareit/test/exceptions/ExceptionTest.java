package edu.hm.weidacher.softarch.shareit.test.exceptions;

import org.junit.Test;

import edu.hm.weidacher.softarch.shareit.exceptions.PersistenceException;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class ExceptionTest {

    @Test(expected = PersistenceException.class)
    public void persistenceExceptionCreation () throws PersistenceException {
	throw new PersistenceException();
    }


    @Test(expected = PersistenceException.class)
    public void persistenceExceptionCreationString () throws PersistenceException {
	throw new PersistenceException("Message");
    }

    @Test(expected = PersistenceException.class)
    public void persistenceExceptionCreationThrowable () throws PersistenceException {
	throw new PersistenceException(new Throwable());
    }

    @Test(expected = PersistenceException.class)
    public void persistenceExceptionCreationThrowableString () throws PersistenceException {
	throw new PersistenceException("Mesasge", new Throwable());
    }

    @Test(expected = PersistenceException.class)
    public void persistenceExceptionCreationAll () throws PersistenceException {
	throw new PersistenceException("Message", new Throwable(), false, false);
    }
}
