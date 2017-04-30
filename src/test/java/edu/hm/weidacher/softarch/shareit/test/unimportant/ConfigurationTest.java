package edu.hm.weidacher.softarch.shareit.test.unimportant;

import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import edu.hm.weidacher.softarch.shareit.Configuration;

/**
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class ConfigurationTest {

    @Test
    public void configurationExists() {
        assertNotEquals("", Configuration.HOST);
	assertNotEquals(1, Configuration.PORT);
	assertNotEquals("", Configuration.URL_SEPARATOR);
    }

}
