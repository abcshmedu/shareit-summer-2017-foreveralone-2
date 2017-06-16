package edu.hm.weidacher.softarch.shareit.util;

import org.eclipse.jetty.util.StringUtil;

/**
 * Provides utility methods for ISBN numbers.
 * @author Simon Weidacher <weidache@hm.edu>
 */
public abstract class IsbnUtil {

    /**
     * Returns whether the given string resembles a valid isbn.
     *
     * @param isbn isbn representing string
     * @return whether the string was a valid isbn
     */
    public static boolean isValid(String isbn) {
        return StringUtil.isNotBlank(isbn);
    }

    /**
     * Normalizes a given isbn string.
     *
     * @param isbn string representing an isbn
     * @return normalized isbn (without hyphens)
     */
    public static String normalize(String isbn) {
        if (isbn == null) {
            throw new NullPointerException("ISBN may not be null...");
	}

	return isbn.replaceAll("[-_]", "");
    }

}
