package com.vm62.diary.frontend.client.common.utils;

import java.util.Collection;

public abstract class ValidationUtils {
	/**
	 * Checks if given string is null or empty string (equals to "");
	 * returns <code>true</code> if it is and <code>false</code> if not.
	 * 
	 * @param string string to be checked.
	 * @return boolean result of the check.
	 */
	public static boolean isNullOrEmpty(String string) {
		return string == null || "".equals(string);
	}

    /**
     * Determines whether string is null or contains no characters after been trimmed.
     * @param s input string to test
     * @return true if string is not null or empty after trim().
     */
    public static boolean isNullOrTrimEmpty(String s) {
        return (s == null || s.trim().isEmpty());
    }

    /**
     * Checks if given collection is null or empty;
     * returns <code>true</code> if it is and <code>false</code> if not.
     * @param collection collection to check.
     * @return boolean result of the check.
     */
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

}
