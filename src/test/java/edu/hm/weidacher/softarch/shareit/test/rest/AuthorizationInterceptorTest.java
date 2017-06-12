package edu.hm.weidacher.softarch.shareit.test.rest;

import java.io.IOException;

import org.junit.Test;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class AuthorizationInterceptorTest {

    @Test(expected = NullPointerException.class)
    public void failUnmanaged () {
	try {
	    new edu.hm.weidacher.softarch.shareit.rest.authentication.AuthorizationInterceptor().filter(null);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
