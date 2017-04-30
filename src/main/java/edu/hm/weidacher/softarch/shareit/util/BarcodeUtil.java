package edu.hm.weidacher.softarch.shareit.util;

import org.eclipse.jetty.util.StringUtil;

/**
 * Provides utility methods for barcodes.
 * @author Simon Weidacher <weidache@hm.edu>
 */
public abstract class BarcodeUtil {

    /**
     * Returns whether the given string resembles a valid barcode.
     *
     * @param barcode barcode representing string
     * @return whether the string was a valid barcode
     */
    public static boolean isValid(String barcode) {
        return StringUtil.isNotBlank(barcode);
    }

}
