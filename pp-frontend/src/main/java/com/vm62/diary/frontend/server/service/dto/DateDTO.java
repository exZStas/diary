package com.vm62.diary.frontend.server.service.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

/**
 * Represents date as combination of year, month, day, hours (in 24-hour clock), minutes and seconds.
 * Original purpose - to replace java.util.Date in DTOs because of formatting issues in GWT client.
 */
public class DateDTO implements Comparable<DateDTO>, Serializable, IsSerializable {

	private static final long serialVersionUID = 1L;
	
	int year;
	int month;
	int day;
	int hours24;
	int minutes;
	int seconds;
    int timeZone;
	
	/**
	 * @deprecated use {@link #DateDTO(int, int, int, int, int, int)} instead.
	 */
	public DateDTO() {
	}
	
	/**
	 * Constructor.
	 * <br>
	 * <u>Important:</u> Do not use this constructor directly, use corresponding assembler instead.
	 * @param year
	 * @param month
	 * @param day
	 * @param hours24
	 * @param minutes
	 * @param seconds
	 */
    public DateDTO(int year, int month, int day, int hours24, int minutes, int seconds) {
        super();
        this.year = year;
        this.month = month;
        this.day = day;
        this.hours24 = hours24;
        this.minutes = minutes;
        this.seconds = seconds;
        this.timeZone = 1; // default GMT+1
    }

    /**
     * Constructor.
     * <br>
     * <u>Important:</u> Do not use this constructor directly, use corresponding assembler instead.
     * @param year
     * @param month
     * @param day
     * @param hours24
     * @param minutes
     * @param seconds
     * @param timeZone
     */
    public DateDTO(int year, int month, int day, int hours24, int minutes, int seconds, int timeZone) {
        super();
        this.year = year;
        this.month = month;
        this.day = day;
        this.hours24 = hours24;
        this.minutes = minutes;
        this.seconds = seconds;
        this.timeZone = timeZone;
    }

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHours24() {
		return hours24;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getSeconds() {
		return seconds;
	}

    public int getTimeZone() {
        return timeZone;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + hours24;
		result = prime * result + minutes;
		result = prime * result + month;
		result = prime * result + seconds;
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DateDTO other = (DateDTO) obj;
		if (day != other.day)
			return false;
		if (hours24 != other.hours24)
			return false;
		if (minutes != other.minutes)
			return false;
		if (month != other.month)
			return false;
		if (seconds != other.seconds)
			return false;
		if (year != other.year)
			return false;
		return true;
	}
	
	@Override
	public int compareTo(DateDTO other) {
		if (this == other)
			return 0;
		if (other == null)
			return 1;
		int result = year - other.year;
		if (result != 0) {
			return result;
		}
		result = month - other.month;
		if (result != 0) {
			return result;
		}
		result = day - other.day;
		if (result != 0) {
			return result;
		}
		result = hours24 - other.hours24;
		if (result != 0) {
			return result;
		}
		result = minutes - other.minutes;
		if (result != 0) {
			return result;
		}
		return seconds - other.seconds;
	}


	@Override
	public String toString() {
		return getDateString() + " " + getTimeString();
	}

	public String getDateString(){
		return this.day + "-" + this.month + "-" + this.year;
	}

	public String getTimeString() {
		return this.hours24 + ":" + this.minutes + ":" + this.seconds;
	}

}
