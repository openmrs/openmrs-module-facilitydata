package org.openmrs.module.facilitydata.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.module.facilitydata.model.FacilityDataReport;
import org.openmrs.module.facilitydata.model.FacilityDataValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
	
	/**
	 * Returns the passed date, at the specified time
	 */
	public static Date getDateTime(int year, int mon, int day, int hr, int min, int sec, int ms) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, mon-1);
		c.set(Calendar.DATE, day);
		c.set(Calendar.HOUR_OF_DAY, hr);
		c.set(Calendar.MINUTE, min);
		c.set(Calendar.SECOND, sec);
		c.set(Calendar.MILLISECOND, ms);
		return c.getTime();
	}
	
	/**
	 * Returns the passed date, at midnight
	 */
	public static Date getDateTime(int year, int mon, int day) {
		return getDateTime(year, mon, day, 0, 0, 0, 0);
	}

	/**
	 * Returns the passed date, at the specified time
	 */
	public static Date getDateTime(Date d, int hour, int minute, int second, int millisecond) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		c.set(Calendar.MILLISECOND, millisecond);
		return c.getTime();
	}
	
	/**
	 * Returns a date that represents the very beginning of the passed date
	 */
	public static Date getStartOfDay(Date d) {
		return getDateTime(d, 0, 0, 0, 0);
	}
	
	/**
	 * Returns the last second of the day
	 */
	public static Date getEndOfDay(Date d) {
		return getDateTime(d, 23, 59, 59, 999);
	}
	
	/**
	 * Returns the last second of the day if the hour/minute/second/ms of the passed date are all zero,
	 * otherwise just returns the passed date
	 */
	public static Date getEndOfDayIfTimeExcluded(Date d) {
		if (d != null) {
			Date startOfDay = getStartOfDay(d);
			if (d.compareTo(startOfDay) == 0) {
				return getEndOfDay(d);
			}
		}
		return d;
	}
	
	/**
	 * @return a List of {@link FacilityDataReport}s from the passed values
	 */
	public static List<FacilityDataReport> getFacilityDataReports(List<FacilityDataValue> values) {
		Map<String, FacilityDataReport> l = new HashMap<String, FacilityDataReport>();
		for (FacilityDataValue v : values) {
			String key = v.getFacility() + "." + v.getFromDate().getTime() + "." + v.getToDate().getTime();
			FacilityDataReport r = l.get(key);
			if (r == null) {
				r = new FacilityDataReport();
				r.setSchema(v.getQuestion().getSection().getSchema());
				r.setStartDate(v.getFromDate());
				r.setEndDate(v.getToDate());
				r.setLocation(v.getFacility());
				l.put(key, r);
			}
			r.addValue(v);
		}
		return new ArrayList<FacilityDataReport>(l.values());
	}

	/**
	 * @return all Locations that are either the passed Location, or any children or grandchildren of it
	 */
	public static Set<Location> getAllLocationsInHierarchy(Location l) {
		Set<Location> ret = new HashSet<Location>();
		ret.add(l);
		if (l.getChildLocations() != null) {
			for (Location childLocation : l.getChildLocations()) {
				ret.addAll(getAllLocationsInHierarchy(childLocation));
			}
		}
		return ret;
	}
}
