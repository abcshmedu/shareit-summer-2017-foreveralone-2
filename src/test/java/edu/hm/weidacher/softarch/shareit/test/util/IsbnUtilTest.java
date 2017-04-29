package edu.hm.weidacher.softarch.shareit.test.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.hm.weidacher.softarch.shareit.util.IsbnUtil;

/**
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public class IsbnUtilTest {

    @Test
    public void isbnValid() {
        assertTrue(IsbnUtil.isValid("apginsdapig"));
        assertTrue(IsbnUtil.isValid("09j3321031940---123tg"));
    }

    @Test
    public void isbnInvalid() {
        assertFalse(IsbnUtil.isValid(null));
        assertFalse(IsbnUtil.isValid(""));
    }
}
