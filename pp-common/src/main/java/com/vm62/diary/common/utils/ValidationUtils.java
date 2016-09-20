package com.vm62.diary.common.utils;



import com.vm62.diary.common.ErrorType;
import com.vm62.diary.common.ExceptionFactory;
import com.vm62.diary.common.ServiceException;
import com.vm62.diary.common.constants.Regexps;
import com.vm62.diary.common.utils.DateTimeUtils;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Class with static methods to validate input parameters. 
 * <br>Is-methods perform validation and return boolean result.
 * <br>If-methods perform validation and throw {@link ServiceException} exception.
 * <br>
 * Adopted from legacy nl.promedico.algemeen.core.util.ArgumentValidator.
 */
public abstract class ValidationUtils {

    public static final int MINIMAL_LOGIN_LENGTH = 5;
    public static final int MIN_PUBLIC_PAGE_ALIAS_LENGTH = 3;
    public static final int MAX_PUBLIC_PAGE_ALIAS_LENGTH = 50;

    /**
     * Hide constructor of Util class
     */
    private ValidationUtils(){

    }

    /**
	 * If given object is null then throws {@link ServiceException} 
	 * with specified error-code {@link ErrorType} and in-place arguments 
	 * to construct exception message text.
	 * 
	 * @param object object to be checked.
	 * @param errorType type of error to throw 
	 * @param args in-place arguments to construct message text
	 * @throws ServiceException
	 */
	public static void ifNull(Object object, ErrorType errorType, Object ... args) throws ServiceException {
		if (object == null) {
			ExceptionFactory.throwServiceException(errorType, args);
		}
	}

	/**
	 * If given object is NOT null then throws {@link ServiceException}
	 * with specified error-code {@link ErrorType} and in-place arguments
	 * to construct exception message text.
	 *
	 * @param object object to be checked.
	 * @param errorType type of error to throw
	 * @param args in-place arguments to construct message text
	 * @throws ServiceException
	 */
	public static void ifNotNull(Object object, ErrorType errorType, Object ... args) throws ServiceException {
		if (object != null) {
			ExceptionFactory.throwServiceException(errorType, args);
		}
	}
	
	/**
	 * If all given objects are null then throws {@link ServiceException}
	 * with specified error-code {@link ErrorType} and in-place arguments 
	 * to construct exception message text.
	 *
	 * @param objectsToCheck
	 * @param errorType
	 * @param args
	 * @throws ServiceException
	 */
	public static void ifAllNull(Object[] objectsToCheck, ErrorType errorType, Object ... args) throws ServiceException {
		if (isAllNull(objectsToCheck)) {
			ExceptionFactory.throwServiceException(errorType, args);
		}
	}

	/**
	 * If all given objects are null or (in case of Strings, Collections, Maps and object arrays) are empty 
	 * then throws {@link ServiceException} with specified error-code {@link ErrorType} and in-place arguments 
	 * to construct exception message text.
	 * 
	 * @param objectsToCheck
	 * @param errorType
	 * @param args
	 * @throws ServiceException
	 */
	public static void ifAllNullOrEmpty(Object[] objectsToCheck, ErrorType errorType, Object ... args) throws ServiceException {
		if (isAllNullOrEmpty(objectsToCheck)) {
			ExceptionFactory.throwServiceException(errorType, args);
		}
	}
	
	/**
	 * If both given objects not null then throws {}@link {@link ServiceException}
	 * with specified error-code {@link ErrorType} and in-place arguments 
	 * to construct exception message text.
	 *
	 * @param obj1
	 * @param obj2
	 * @param errorType
	 * @param args
	 * @throws ServiceException
	 */
	public static void ifBothNotNull(Object obj1, Object obj2, ErrorType errorType, Object ... args) throws ServiceException {
		if (obj1 != null && obj2 != null) {
			ExceptionFactory.throwServiceException(errorType, args);
		}
	}
	
	/**
	 * No in-place arguments version of {@link ValidationUtils ifNull(Object object, ErrorType errorType, Object ... args)} method
	 * <br>
	 * If given object is null then throws {@link ServiceException} with specified error-code {@link ErrorType}
	 * 
	 * @param object object to be checked.
	 * @param errorType type of error to throw 
	 * @throws ServiceException
	 */
	public static void ifNull(Object object, ErrorType errorType) throws ServiceException {
		if (object == null) {
			ExceptionFactory.throwServiceException(errorType);
		}
	}		
	
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
	 * If given string is null or empty (contains no characters) 
	 * then throws {@link ServiceException} with specified 
	 * error-code {@link ErrorType} and in-place arguments to construct 
	 * exception message text.
	 *
	 * @param string string to be checked.
	 * @param errorType type of error to throw 
	 * @param args in-place arguments to construct message text
	 * @throws ServiceException
	 */
	public static void ifNullOrEmpty(String string, ErrorType errorType, Object ... args) throws ServiceException {
		if (isNullOrEmpty(string)) {
			ExceptionFactory.throwServiceException(errorType, args);
		}
	}
	
	/**
	 * 'No in-place arguments' version of {@link ValidationUtils ifNullOrEmpty(String string, ErrorType errorType, Object ... args)} method
	 * <br> 
	 * If given string is null or empty (contains no characters) 
	 * then throws {@link ServiceException} with specified 
	 * error-code {@link ErrorType}.
	 * 
	 * @param string string to be checked.
	 * @param errorType type of error to throw 
	 * @throws ServiceException
	 */
	public static void ifNullOrEmpty(String string, ErrorType errorType) throws ServiceException {
		if (isNullOrEmpty(string)) {
			ExceptionFactory.throwServiceException(errorType);
		}
	}	
	
	/**
	 * If given string is null or empty after trim (contains no characters) 
	 * then throws {@link ServiceException} with specified 
	 * error-code {@link ErrorType} and in-place arguments to construct 
	 * exception message text.
	 *
	 * @param string string to be checked.
	 * @param errorType type of error to throw 
	 * @param args in-place arguments to construct message text
	 * @throws ServiceException
	 */
	public static void ifNullOrTrimEmpty(String string, ErrorType errorType, Object ... args) throws ServiceException {
		if (isNullOrTrimEmpty(string)) {
			ExceptionFactory.throwServiceException(errorType, args);
		}
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

	/**
	 * If given collection contains no unique elements 
	 * then throws {@link ServiceException} with specified 
	 * error-code {@link ErrorType} and in-place arguments to construct 
	 * exception message text.
	 * 
	 * @param collection
	 * @param errorType
	 * @param args
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void ifHasDuplicates(Collection<?> collection, ErrorType errorType, Object ... args) throws ServiceException {
		if (collection != null && new HashSet(collection).size() != collection.size()) {
			ExceptionFactory.throwServiceException(errorType, args);
		}
	}	
	
	
	/**
	 * Checks if given array is null or empty;
	 * returns <code>true</code> if it is and <code>false</code> if not.
	 * @param array array to check.
	 * @return boolean result of the check.
	 */
	public static boolean isNullOrEmpty(Object[] array) {
		return array == null || array.length == 0;
	}	

	/**
	 * If given collection is null or empty (contains no elements) 
	 * then throws {@link ServiceException} with specified 
	 * error-code {@link ErrorType} and in-place arguments to construct 
	 * exception message text.
	 * 
	 * @param collection collection to be checked.
	 * @param errorType type of error to throw 
	 * @param args in-place arguments to construct message text
	 * @throws ServiceException
	 */
	public static void ifNullOrEmpty(Collection<?> collection, ErrorType errorType, Object ... args) throws ServiceException {
		if (isNullOrEmpty(collection)) {
			ExceptionFactory.throwServiceException(errorType, args);
		}
	}

	/**
	 * If given array is null or empty (contains no elements) or 
	 * ALL array elements are null or empty (contain no characters)
	 * then throws {@link ServiceException} with specified 
	 * error-code {@link ErrorType} and in-place arguments to construct 
	 * exception message text.
	 * 
	 * @param values array of the values to be checked.
	 * @param errorType type of error to throw 
	 * @param args in-place arguments to construct message text
	 * @throws ServiceException
	 */
	public static void ifNullOrEmptyAllValues(String[] values, ErrorType errorType, Object ... args) throws ServiceException {
		if (isNullOrEmpty(values)) {
			ExceptionFactory.throwServiceException(errorType, args);
		}
		for (String value : values) {
			if (!isNullOrEmpty(value)) {
				return;
			}
		}
		ExceptionFactory.throwServiceException(errorType, args);
	}
	
	/**
	 * 'No in-place arguments' version of {@link ValidationUtils#ifNullOrEmpty(Collection, ErrorType, Object...)} method
	 * <br> 
	 * If given collection is null or empty (contains no elements) 
	 * then throws {@link ServiceException} with specified 
	 * error-code {@link ErrorType}.
	 * 
	 * @param collection collection to be checked.
	 * @param errorType type of error to throw 
	 * @throws ServiceException
	 */
	public static void ifNullOrEmpty(Collection<?> collection, ErrorType errorType) throws ServiceException {
		if (isNullOrEmpty(collection)) {
			ExceptionFactory.throwServiceException(errorType);
		}
	}	
	
	/**
	 * Checks if given map is null or empty;
	 * returns <code>true</code> if it is and <code>false</code> if not.
	 * 
	 * @param map to check.
	 * @return boolean result of the check.
	 */
	public static boolean isNullOrEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}	
	
	/**
	 * If given map is null or empty (contains no elements) 
	 * then throws {@link ServiceException} with specified 
	 * error-code {@link ErrorType} and in-place arguments to construct 
	 * exception message text.
	 * 
	 * @param map map to be checked.
	 * @param errorType type of error to throw 
	 * @param args in-place arguments to construct message text
	 * @throws ServiceException
	 */
	public static void ifNullOrEmpty(Map<?, ?> map, ErrorType errorType, Object ... args) throws ServiceException {
		if (isNullOrEmpty(map)) {
			ExceptionFactory.throwServiceException(errorType, args);
		}
	}
	
	/**
	 * 'No in-place arguments' version of {@link ValidationUtils#ifNullOrEmpty(Map, ErrorType, Object...)} method
	 * <br> 
	 * If given map is null or empty (contains no elements) 
	 * then throws {@link ServiceException} with specified 
	 * error-code {@link ErrorType}.
	 * 
	 * @param map map to be checked.
	 * @param errorType type of error to throw 
	 * @throws ServiceException
	 */
	public static void ifNullOrEmpty(Map<?, ?> map, ErrorType errorType) throws ServiceException {
		if (isNullOrEmpty(map)) {
			ExceptionFactory.throwServiceException(errorType);
		}
	}		
	
	/**
	 * If length of given string exceeds provided maximum then throws provided {@link ServiceException}
	 * @param string
	 * @param maxLength
	 * @param errorType
	 * @throws ServiceException
	 */
	public static void ifTooLong(String string, int maxLength, ErrorType errorType, Object ... args) throws ServiceException {
		if (isTooLong(string, maxLength)) {
			ExceptionFactory.throwServiceException(errorType, args);
		}
	}

    /**
     * If length of given string is less than provided minimum then throws provided {@link ServiceException}
     * @param string
     * @param minLength
     * @param errorType
     * @throws ServiceException
     */
    public static void ifTooShort(String string, int minLength, ErrorType errorType, Object ... args) throws ServiceException {
        if (isTooShort(string, minLength)) {
            ExceptionFactory.throwServiceException(errorType, args);
        }
    }

    /**
     * If login format is incorrect then throws provided {@link ServiceException}
     * @param loginValue
     * @param errorType
     * @param args
     * @throws ServiceException
     */
    public static void ifLoginIncorrectFormat(String loginValue, ErrorType errorType, Object ... args) throws ServiceException {
        if (!isLoginFormatValid(loginValue)) {
            ExceptionFactory.throwServiceException(errorType, args);
        }
    }


    /**
     * If password format is incorrect
     * then throws {@link ServiceException} with {@link ErrorType}
     * @param passwordValue
     * @param login
     * @param errorType
     * @param args
     * @throws ServiceException
     */
    public static void ifPasswordIncorrectFormat(String passwordValue, String login, ErrorType errorType, Object ... args) throws ServiceException {
        if (!isPasswordFormatValid(passwordValue, login)) {
            ExceptionFactory.throwServiceException(errorType, args);
        }
    }


    /**
     * If email format is incorrect then throws provided {@link ServiceException}
     * @param emailValue
     * @param errorType
     * @param args
     * @throws ServiceException
     */
    public static void ifEmailIncorrectFormat(String emailValue, ErrorType errorType, Object ... args) throws ServiceException {
        if (!isEmailFormatValid(emailValue)) {
            ExceptionFactory.throwServiceException(errorType, args);
        }
    }

	/**
	 * Checks if given string can be interpreted as a date (without time); if it can  - returns <code>true</code>, otherwise - <code>false</code>.
	 * If string is null or empty or consists of spaces only - returns false.
	 * @param dateValue
	 * @return
	 */
	public static boolean isDateFormatValid(String dateValue) {
		if (dateValue != null && !dateValue.trim().isEmpty()) {
			dateValue = dateValue.trim();
		} else {
			return false;
		}
		if (dateValue.contains("-")
				|| dateValue.contains("/")
                || dateValue.contains(".")
				|| (dateValue.matches("^[0-9]+$") && (dateValue.length() == 8 || dateValue.length() == 6))) {
			// try to parse date
			try {
				dateValue = dateValue.replace("/", "").replace("-", "").replace(".", "");
				if (dateValue.matches("^[0-9]{6}$")) {
					DateTimeUtils.getDateFormat_ddMMyy().parse(dateValue);
				} else if (dateValue.matches("^[0-9]{8}$")) {
					DateTimeUtils.getDateFormat_ddMMyyyy().parse(dateValue);
				} else {
					return false;
				}
			} catch (ParseException e) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * Checks if given string can be interpreted as a date/time; if it can  - returns <code>true</code>, otherwise - <code>false</code>.
	 * If string is null or empty or consists of spaces only - returns false.
	 * @param dateTimeValue
	 * @return
	 */
	public static boolean isDateTimeFormatValid(String dateTimeValue) {
		if (dateTimeValue != null && !dateTimeValue.trim().isEmpty()) {
			dateTimeValue = dateTimeValue.trim();
		} else {
			return false;
		}
		if (dateTimeValue.contains("-")
				|| dateTimeValue.contains("/")
				|| dateTimeValue.contains(" ")
				|| dateTimeValue.matches("^[0-9]{14}$")) {
			// try to parse date
			try {
				dateTimeValue = dateTimeValue.replace("/", "").replace("-", "").replace(" ", "").replace(":", "");
				if (dateTimeValue.matches("^[0-9]{14}$")) {
					DateTimeUtils.getDateFormat_ddMMyyyyHHmmss().parse(dateTimeValue);
				} else {
					return false;
				}
			} catch (ParseException e) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if given string is a valid dutch zip code; if it is  - returns <code>true</code>, otherwise - <code>false</code>.
	 * If string is null or empty or consists of spaces only - returns false.
	 * @param postcodeValue
	 * @return
	 */
	public static boolean isDutchPostcodeFormatValid(String postcodeValue) {
		if (postcodeValue != null && !postcodeValue.trim().isEmpty()) {
			postcodeValue = postcodeValue.trim();
		} else {
			return false;
		}
		
		// Postcode ==> NNNNAA
        return postcodeValue.matches("^[0-9]{4}[a-zA-Z]{2}$");
    }

	/**
	 * Checks if given string can be interpreted as a house number; if it can  - returns <code>true</code>, otherwise - <code>false</code>.
	 * If string is null or empty or consists of spaces only - returns false.
	 * @param huisnummerValue
	 * @return
	 */
	public static boolean isHuisnummerFormatValid(String huisnummerValue) {
		if (huisnummerValue != null && !huisnummerValue.trim().isEmpty()) {
			huisnummerValue = huisnummerValue.trim();
		} else {
			return false;
		}
        return huisnummerValue.matches("^[0-9]+$") && huisnummerValue.length() < 6;
    }
	
	/**
	 * Checks if given value is a valid house number; if it is  - returns <code>true</code>, otherwise - <code>false</code>.
	 * If value is null - returns false.
	 * @param huisnummerValue
	 * @return
	 */
	public static boolean isHuisnummerFormatValid(Integer huisnummerValue) {
        return huisnummerValue != null && huisnummerValue <= 999999 && huisnummerValue > 0;
    }

    /**
     * Checks if given string is a valid login; if it is returns <code>true</code>, otherwise - <code>false</code>.
     * If string is null or empty or consists of spaces only - returns false.
     *
     * @param loginValue value to check
     * @return
     */
    public static boolean isLoginFormatValid(final String loginValue) {
        if (loginValue == null || loginValue.trim().isEmpty()) {
            return false;
        }
        String trimmedLoginValue = loginValue.trim();
        //Login must contain only letters and numbers, length is not less than 5 symbols
        return trimmedLoginValue.matches("^[a-zA-Z_\\d]{" + MINIMAL_LOGIN_LENGTH + ",}$");
    }

    /**
     * Checks if given string is a valid alias; if it is returns <code>true</code>, otherwise - <code>false</code>.
     * If string is null or empty or consists of spaces only - returns false.
     * @param alias value to check
     * @return
     */
    public static boolean isPublicPageAliasFormatValid(final String alias) {
        if (alias == null) {
            return false;
        }

        String trimmedAlias = alias.trim();
        //alias cannot contain double underscore
        if (trimmedAlias.contains("__")) {
            return false;
        }
        //alias must contain only letters and numbers, length is not less than 3 and more than 50 symbols
        return trimmedAlias.matches("^\\w{" + MIN_PUBLIC_PAGE_ALIAS_LENGTH + "," + MAX_PUBLIC_PAGE_ALIAS_LENGTH + "}$");
    }

    /**
     * Checks if given string is a valid email; if it is returns <code>true</code>, otherwise - <code>false</code>.
     * If string is null or empty or consists of spaces only - returns false.
     *
     * @param emailValue value to check
     * @return
     */
    public static boolean isEmailFormatValid(final String emailValue) {
        if (emailValue == null || emailValue.trim().isEmpty()) {
            return false;
        }
        String trimmedEmailValue = emailValue.trim();

		String validationRegexp = Regexps.EMAIL_VALIDATION_REGEXP;
		return trimmedEmailValue.matches(validationRegexp);
    }

    /**
     * Checks if given string is a valid password; if it is returns <code>true</code>, otherwise - <code>false</code>.
     * If string is null or empty or consists of spaces only - returns false.
     *
     * @param passwordValue value to check
     * @return
     */
    public static boolean isPasswordFormatValid(final String passwordValue, String login) {
        if (passwordValue == null || passwordValue.trim().isEmpty()) {
            return false;
        }

        if(login != null) {
            //password must be different from the login
            if(passwordValue.equals(login)){
                return false;
            }
        }
        //password must contain at least one letter
        if(!Pattern.compile("[a-zA-Z]+").matcher(passwordValue).find()) {
            return false;
        }
        //password must contain at least one numeric
        if(!Pattern.compile("\\d+").matcher(passwordValue).find()){
            return false;
        }
        //password must not contain more than 3 consecutive identical characters
        if(Pattern.compile("(.)\\1{3,}").matcher(passwordValue).find()){
            return false;
        }
        return true;
    }


    /**
	 * Checks that arguments are equal (using {@code equals()} method) or both have {@code null} value.
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public static boolean areEqualOrBothNull(Object arg1, Object arg2) {
		if (arg1 == null && arg2 == null) {
			return true;
		}
		return arg1 != null ? arg1.equals(arg2) : arg2.equals(arg1);
	}

	/**
	 * Checks that trim-ed values of String arguments are equal ignoring case (using {@code equalsIgnoreCase()} method)
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public static boolean areEqualTrimIgnoreCase(String arg1, String arg2) {
		if (arg1 == null || arg2 == null) {
			return false;
		}
		return arg1.trim().equalsIgnoreCase(arg2.trim());
	}

	/**
	 * Checks that String arguments are equal ignoring case (using {@code equalsIgnoreCase()} method) or both have {@code null} value.
	 * @param arg1
	 * @param arg2
	 * @return
	 */
	public static boolean areEqualTrimIgnoreCaseOrBothNull(String arg1, String arg2) {
		if (arg1 == null && arg2 == null) {
			return true;
		} else if (arg1 == null || arg2 == null) {
			return false;
		}
		return arg1.trim().equalsIgnoreCase(arg2.trim());
	}
	
	/**
	 * Checks that collections are equal or both null either empty ignoring elements order
	 * @param collection1
	 * @param collection2
	 * @return
	 */
	public static boolean areEqualOrBothNullOrEmptyIgnoreOrder(Collection<?> collection1, Collection<?> collection2) {
		if (ValidationUtils.isNullOrEmpty(collection1)) {
			if (!ValidationUtils.isNullOrEmpty(collection2)) {
				return false;
			}
		} else if (ValidationUtils.isNullOrEmpty(collection2) ||
				   !collection1.containsAll(collection2) ||
				   !collection2.containsAll(collection1)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns {@code true} if all given objects are null otherwise returns {@code false}.
	 * <br>
	 * 
	 * @param objectsToCheck
	 */
	public static boolean isAllNull(Object... objectsToCheck) {
		for (Object obj : objectsToCheck) {
			if (obj != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns {@code true} if all given objects are null or empty otherwise returns {@code false}.
	 * 'Emptiness' is checked for {@link Collection}, {@link Map}, {@link String} objects and {@link Object} arrays.
	 * 
	 * @param objectsToCheck
	 */
	public static boolean isAllNullOrEmpty(Object... objectsToCheck) {
		for (Object obj : objectsToCheck) {
			if (obj != null) {
				if (obj instanceof Collection ||
					obj instanceof Map ||
					obj instanceof Object[] ||
					obj instanceof String) {
					if (obj instanceof Collection && !((Collection) obj).isEmpty() ||
						obj instanceof Map && !((Map) obj).isEmpty() ||
						obj instanceof Object[] && ((Object[]) obj).length > 0 ||
						obj instanceof String && !((String) obj).isEmpty()) {
						
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Returns {@code true} if provided string is not null/empty and its length more then specified maximum value.
	 * Otherwise return {@code false}.
	 * @param string
	 * @param maximalLength
	 * @return
	 */
	public static boolean isTooLong(String string, int maximalLength) {
		return !isNullOrEmpty(string) && string.length() > maximalLength;
	}

    /**
     * Returns {@code true} if provided string is not null/empty and its length is less than specified minimum value.
     * Otherwise return {@code false}.
     * @param string
     * @param minimalLength
     * @return
     */
    public static boolean isTooShort(String string, int minimalLength) {
        return !isNullOrEmpty(string) && string.length() < minimalLength;
    }
}
