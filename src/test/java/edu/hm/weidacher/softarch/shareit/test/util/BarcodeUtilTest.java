package edu.hm.weidacher.softarch.shareit.test.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.hm.weidacher.softarch.shareit.util.BarcodeUtil;

/**
 * @author Simon Weidacher <weidache@hm.edu>
 */
public class BarcodeUtilTest {

    @Test
    public void barcodeValidTest() {
        assertTrue(BarcodeUtil.isValid("124pihpincpkasndadv"));
        assertTrue(BarcodeUtil.isValid("psdibndosvnf"));
    }

    @Test
    public void barcodeInvalid() {
        assertFalse(BarcodeUtil.isValid(null));
        assertFalse(BarcodeUtil.isValid(""));
    }
}
