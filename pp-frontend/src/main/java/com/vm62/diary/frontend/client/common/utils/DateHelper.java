package com.vm62.diary.frontend.client.common.utils;

import com.vm62.diary.frontend.server.service.dto.DateDTO;
import com.google.gwt.i18n.client.DateTimeFormat;

import java.util.Date;

/**
 * Utility class for manipulations with {@link DateDTO} objects.
 */
public abstract class DateHelper {

    public static final String DATE_DELIMITER = "-";
    public static final String TIME_DELIMITER = ":";

    private static final DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(SimpleDateFormat.DATE_TIME.getValue());
    private static final DateTimeFormat dateFormat = DateTimeFormat.getFormat(SimpleDateFormat.DATE.getValue());
    private static long serverTimeDelta;

    /**
     * hide utility class constructor
     */
    private DateHelper(){

    }


    public static String formatTime(DateDTO date) {
        String result = "";
        if (date.getHours24()<10) {
            result += "0";
        }
        result += date.getHours24() + ":";
        if (date.getMinutes()<10) {
            result += "0";
        }
        result += date.getMinutes();

        return result;
    }


    public static Date trimMilliseconds(Date start) {
        return new Date((long)(start.getTime()/1000)*1000);
    }

    /**
     * Encapsulates possible date formats to be used with {@link DateDTO} objects.
     */
    public enum SimpleDateFormat {
        /**
         * "dd-MM-yyyy"
         */
        DATE("dd-MM-yyyy"),
        /**
         * "HH:mm:ss"
         */
        TIME("HH:mm:ss"),
        /**
         * "dd-MM-yyyy, HH:mm:ss"
         */
        DATE_TIME("dd-MM-yyyy HH:mm:ss"),
        /**
         * "HH:mm"
         */
        HOURS_MINUTES("HH:mm"),
        /**
         * "yyyy-MM-dd HH:mm"
         */
        DATE_TIME_TRIM_SECONDS("yyyy-MM-dd HH:mm");

        private String value;

        SimpleDateFormat(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Converts date/time components into {@link Date} object.
     * @param year
     * @param month
     * @param day
     * @param hours24
     * @param minutes
     * @param seconds
     * @param timeZone
     * @return
     */
    public static Date convert(int year, int month,	int day, int hours24, int minutes, int seconds, int timeZone) {
        return convert(new DateDTO(year, month, day, hours24, minutes, seconds, timeZone));
    }

    /**
     * Converts {@link DateDTO} to a {@link Date}.
     * @param simpleDate original {@link DateDTO} object.
     * @return result {@link Date} object. 
     */
    public static Date convert(DateDTO simpleDate) {
    	if (simpleDate == null) {
    		return null;
    	}
    	String dateString = constructDateTimeString(simpleDate);
    	return dateTimeFormat.parse(dateString);
    }

    /**
     * Converts {@link Date} to {@link DateDTO}.
     * @param date original {@link Date} object.
     * @return {@link DateDTO} object.
     */
    public static DateDTO convert(Date date) {
        if (date == null) {
            return null;
        }
        int year = date.getYear() + 1900;
        int month = date.getMonth() + 1;
        int day = date.getDate();
        int hours24 = date.getHours();
        int minutes = date.getMinutes();
        int seconds = date.getSeconds();
        int timezone = date.getTimezoneOffset();
        return new DateDTO(year, month, day, hours24, minutes, seconds, timezone);
    }

    /**
     * Converts {@link String} to {@link DateDTO}.
     * Expects a string formatted as: dd-mm-yyyy
     *
     * @return {@link DateDTO} object.
     */
    public static DateDTO convert(String dateTimeValue) {

        if (dateTimeValue == null) {
            return null;
        }
        Date dateFromString;
        try{
            dateFromString = dateTimeFormat.parse(dateTimeValue);
            return new DateDTO(dateFromString.getYear()+ 1900, dateFromString.getMonth() + 1, dateFromString.getDate(), dateFromString.getHours(), dateFromString.getMinutes(), 0);
        }
        catch(Exception e){
            return null;
        }
    }

    /**
     * Converts {@link DateDTO} into string formatted according to format parameter.
     * @param simpleDate original {@link DateDTO} object.
     * @param format {@link SimpleDateFormat}
     * @return constructed string.
     */
    public static String constructString(DateDTO simpleDate, SimpleDateFormat format) {
        if (simpleDate == null) {
            return "";
        }
        if (SimpleDateFormat.DATE.equals(format)) {
            return constructDateString(simpleDate);
        } else if (SimpleDateFormat.DATE_TIME.equals(format)) {
            return constructDateTimeString(simpleDate);
        } else if (SimpleDateFormat.HOURS_MINUTES.equals(format)) {
            return constructHoursMinutesString(simpleDate);
        } else if (SimpleDateFormat.TIME.equals(format)) {
            return constructTimeString(simpleDate);
        } else if (SimpleDateFormat.DATE_TIME_TRIM_SECONDS.equals(format)) {
            return constructDateTimeWithoutSecString(simpleDate);
        } else {
            // for unknown format use date format.
            return constructDateString(simpleDate);
        }
    }


    /**
     * Converts DateDTO into string formatted as "dd-MM-yyyy HH:mm:ss".
     * @param simpleDate original {@link DateDTO} object.
     * @return constructed string.
     */
    public static String constructDateTimeString(DateDTO simpleDate) {
        if (simpleDate == null) {
            return "";
        }
        StringBuilder dateString = new StringBuilder();
        dateString.append(constructDateString(simpleDate))
                .append(" ")
                .append(constructTimeString(simpleDate));
        return dateString.toString();
    }

    /**
     * Converts DateDTO into string formatted as "dd-MM-yyyy HH:mm".
     * @param simpleDate original {@link DateDTO} object.
     * @return constructed string.
     */
    public static String constructDateTimeWithoutSecString(DateDTO simpleDate) {
        if (simpleDate == null) {
            return "";
        }
        StringBuilder dateString = new StringBuilder();
        dateString.append(constructDateString(simpleDate))
                .append(" ")
                .append(constructHoursMinutesString(simpleDate));
        return dateString.toString();
    }

    /**
     * Converts DateDTO into string formatted as "HH:mm:ss".
     * @param simpleDate original {@link DateDTO} object.
     * @return constructed string.
     */
    public static String constructTimeString(DateDTO simpleDate) {
        if (simpleDate == null) {
            return "";
        }
        String secondsString = StringHelper.complementLeft(String.valueOf(simpleDate.getSeconds()), 2, '0');
        StringBuilder dateString = new StringBuilder();
        dateString.append(constructHoursMinutesString(simpleDate))
                .append(TIME_DELIMITER)
                .append(secondsString);
        return dateString.toString();
    }

    /**
     * Converts DateDTO into string formatted as "HH:mm".
     * @param simpleDate original {@link DateDTO} object.
     * @return constructed string.
     */
    public static String constructHoursMinutesString(DateDTO simpleDate) {
        if (simpleDate == null) {
            return "";
        }
        String hoursString = StringHelper.complementLeft(String.valueOf(simpleDate.getHours24()), 2, '0');
        String minutesString = StringHelper.complementLeft(String.valueOf(simpleDate.getMinutes()), 2, '0');
        StringBuilder dateString = new StringBuilder();
        dateString.append(hoursString)
                .append(TIME_DELIMITER)
                .append(minutesString);
        return dateString.toString();
    }

    /**
     * Converts DateDTO into string formatted as "dd-MM-yyyy".
     * @param simpleDate original {@link DateDTO} object.
     * @return constructed string.
     */
    public static String constructDateString(DateDTO simpleDate) {
        if (simpleDate == null) {
            return "";
        }

        StringBuilder dateString = new StringBuilder();
        String dayString = StringHelper.complementLeft(String.valueOf(simpleDate.getDay()), 2, '0');
        String monthString = StringHelper.complementLeft(String.valueOf(simpleDate.getMonth()), 2, '0');
        dateString.append(dayString)
                .append(DATE_DELIMITER)
                .append(monthString)
                .append(DATE_DELIMITER)
                .append(simpleDate.getYear());
        return dateString.toString();
    }

    /**
     * Checks if given simple date is after current moment of time.
     * @param simpleDate {@link DateDTO} object to test.
     * @return <code>true</code> if given date is after current moment of time, <code>false</code> otherwise.
     */
    public static boolean afterNow(DateDTO simpleDate) {
        if (simpleDate != null) {
            Date convertedDate = convert(simpleDate);
            return convertedDate.after(getServerTime());
        }
        return false;
    }

    /**
     * Checks if given simple date is before current moment of time.
     * @param simpleDate {@link DateDTO} object to test.
     * @return <code>true</code> if given date is before current moment of time, <code>false</code> otherwise.
     */
    public static boolean beforeNow(DateDTO simpleDate) {
        if (simpleDate != null) {
            Date convertedDate = convert(simpleDate);
            return convertedDate.before(getServerTime());
        }
        return false;
    }

    public static void setServerTimeDelta(long _serverTimeDelta){
        serverTimeDelta = _serverTimeDelta;
    }


    public static long getServerTimeDelta() {
        return serverTimeDelta;
    }

    public static String createTimeString(int totalSeconds){
        int hours = totalSeconds / 3600;
        int tmpSeconds = totalSeconds % 3600;
        int minutes = tmpSeconds / 60;
        int seconds = tmpSeconds % 60;

        String timeString = "";
        if(hours>0){
            timeString += hours + " " + "hour";
        }

        if(minutes>0){
            if(timeString.length()>0){
                timeString += ", ";
            }
            timeString += minutes + " " + "minutes";
        }

        if(seconds>0){
            if(timeString.length()>0){
                timeString += ", ";
            }
            timeString += seconds + " " + "seconds";
        }
        return timeString;
    }

    public static Date toServerTime(Date date) {
        return new Date(date.getTime() + getServerTimeDelta());
    }

    public static Date getServerTime() {
        Date date = new Date();
        return toServerTime(date);
    }

    public static Date cutOffTime(Date date) {
        if (date != null) {
            return dateFormat.parse(dateFormat.format(date));
        }
        return null;
    }
}
