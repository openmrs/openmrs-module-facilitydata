package org.openmrs.module.facilitydata.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A utility class for common date operations
 */
public class FacilityDataUtil {
		
	protected static Log log = LogFactory.getLog(FacilityDataUtil.class);
	
	public static boolean isDateInRange(Date toCheck, Date minRange, Date maxRange) {
		String toCheckStr = formatYmd(toCheck);
		return ((minRange == null || toCheckStr.compareTo(formatYmd(minRange)) >= 0) &&
				(maxRange == null || toCheckStr.compareTo(formatYmd(maxRange)) <= 0));
	}
	
	/**
	 * Utility method to format a date in the given format
	 * @param d the date to format
	 * @return a String representing the date in the passed format
	 */
	public static String formatDate(Date d, String format) {
		return formatDate(d, format, "");
	}
	
	/**
	 * Utility method to format a date in the given format
	 * @param d the date to format
	 * @param format the DateFormat to use
	 * @param defaultIfNUll the value to return if the passed date is null
	 * @return a String representing the date in the passed format
	 */
	public static String formatDate(Date d, String format, String defaultIfNull) {
		if (d != null) {
			DateFormat df = new SimpleDateFormat(format);
			return df.format(d);
		}
		return defaultIfNull;
	}
	
	/**
	 * Utility method to format a date in yyyy-MM-dd format
	 * @param d the date to format
	 * @return a String representing the date in the passed format
	 */
	public static String formatYmd(Date d) {
		return formatDate(d, "yyyy-MM-dd", "");
	}
	
	/**
	 * Utility method to parse a date in the given format
	 * @param s the string to parse
	 * @param format the date format
	 * @return a Date representing the date in the passed format
	 */
	public static Date parseYmd(String s) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return df.parse(s);
		}
		catch (Exception e) {
			throw new RuntimeException("Cannot parse " + s + " into a date using ymd format");
		}
	}
	
	/**
	 * Utility method to parse a date in the given format
	 * @param s the string to parse
	 * @param format the date format
	 * @return a Date representing the date in the passed format
	 */
	public static Date parseDate(String s, String format) {
		DateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(s);
		}
		catch (Exception e) {
			throw new RuntimeException("Cannot parse " + s + " into a date using format " + format);
		}
	}
	
	/**
	 * @return the date that is the end of the month that the passed date is in
	 */
	public static Date getEndOfMonth(Date currentDate) { 
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		return cal.getTime();
	}	
}
