package edu.hm.weidacher.softarch.shareit.util;

import org.eclipse.jetty.util.StringUtil;

/**
 * Provides utility methods for ISBN numbers.
 * @author Simon Weidacher <simon.weidacher@timebay.eu>
 */
public final class IsbnUtil {

    /**
     * Returns whether the given string resembles a valid isbn.
     *
     * @param isbn isbn representing string
     * @return whether the string was a valid isbn
     */
    public static boolean isValid(String isbn) {
        return StringUtil.isNotBlank(isbn);
    }

}
