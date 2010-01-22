package org.openmrs.module.facilitydata.util;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class FacilityDataDateUtils {


    public static Date getLastOfMonthDate(Date inputDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(inputDate);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        return cal.getTime();
    }

    public static Calendar getCalendar(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal;
    }


    public static Date incrementMonths(Date d1, int numMonths) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        cal.add(Calendar.MONTH, numMonths);
        return cal.getTime();
    }

    public static String incrementMonth(Calendar cal,int numMonths) {
        cal.add(Calendar.MONTH, numMonths);
        int month = cal.get(Calendar.MONTH);
        return month < 10 ? "0" + (month + 1) : String.format("%d", month + 1);
    }

    public static int incrementYear(Calendar calendar, int numYears) {        
        calendar.add(Calendar.YEAR,numYears);
        return calendar.get(Calendar.YEAR);
    }


    public static Date getIncrementedDate(Date d1, int increment) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        cal.add(Calendar.DATE, increment);
        return cal.getTime();
    }

    public static String dateToYMD(Date date) {
        if (date == null) return "";
        return getDateFormat().format(date);
    }

    public static String dateToYMD(Calendar cal) {
        if (cal == null) return "";
        return getDateFormat().format(cal.getTime());
    }

    public static String today() {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        return df.format(new Date());
    }


    public static DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy/MM/dd");
    }

    public static Date getDateFor(int month, int day, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }
}
