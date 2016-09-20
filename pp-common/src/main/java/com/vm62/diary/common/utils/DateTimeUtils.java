package com.vm62.diary.common.utils;



import com.vm62.diary.common.ServiceException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date and time utility methods
 */
public abstract class DateTimeUtils {
	
	/** date/time format using 'ddMMyy' pattern */
	public static SimpleDateFormat getDateFormat_ddMMyy() {
		SimpleDateFormat df = new SimpleDateFormat("ddMMyy");
		df.setLenient(false);
		return df;
	}

	/** date/time format using 'ddMMyyyy' pattern */
	public static SimpleDateFormat getDateFormat_ddMMyyyy() {
		SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
		df.setLenient(false);
		return df;
	}

	/** date/time format using 'ddMMyyyyHHmmss' pattern */
	public static SimpleDateFormat getDateFormat_ddMMyyyyHHmmss() {
		SimpleDateFormat df = new SimpleDateFormat("ddMMyyyyHHmmss");
		df.setLenient(false);
		return df;
	}

    /**
     * hide util class constructor
     */
    private DateTimeUtils(){
    }

	/**
	 * Tries to convert given string into {@link Date} value.
	 * <ul>
	 *  <li>ddMMyy</li>
	 *  <li>ddMMyyyy</li>
	 * </ul>
	 * If string can not be interpreted as a date throws a {@link ServiceException}.
	 * @param dateValue
	 * @return
	 * @throws ServiceException
	 */
	public static Date convertToDate(String dateValue) throws ServiceException {
		Date result = null;
		if (ValidationUtils.isDateFormatValid(dateValue)) {
			dateValue = dateValue.replace("/", "").replace("-", "").replace(".", "");
			try {
				if (dateValue.matches("^[0-9]{6}$")) {
					result = getDateFormat_ddMMyy().parse(dateValue);
				} else if (dateValue.matches("^[0-9]{8}$")) {
					result = getDateFormat_ddMMyyyy().parse(dateValue);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * Tries to convert given string into {@link Date} value (including time).
	 * If time is not specified, converts to date with '00:00:00' time part.
	 * If string can not be interpreted as a date throws a {@link ServiceException}.
	 * @param dateTimeValue
	 * @return
	 * @throws ServiceException
	 */
	public static Date convertToDateTime(String dateTimeValue) throws ServiceException {
		Date result = null;
		final boolean dateTimeFormatValid = ValidationUtils.isDateTimeFormatValid(dateTimeValue);
		final boolean dateFormatValid = ValidationUtils.isDateFormatValid(dateTimeValue);
		if (dateTimeFormatValid
				|| dateFormatValid) {
			dateTimeValue = dateTimeValue.replace("/", "").replace("-", "").replace(" ", "").replace(":", "");
			try {
				if (dateTimeValue.matches("^[0-9]{14}$")) {
					result = getDateFormat_ddMMyyyyHHmmss().parse(dateTimeValue);
				} else {
					result = convertToDate(dateTimeValue);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
