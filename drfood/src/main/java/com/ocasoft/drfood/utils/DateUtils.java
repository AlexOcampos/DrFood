package com.ocasoft.drfood.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alex on 14/08/2015.
 */
public class DateUtils {
	private static final String TAG = "DRFOOD_DateUtils";
	private static final boolean DEBUG = true;
	public final static String DATE_FORMAT_DAYMONTHYEAR = "dd/MM/yyyy";

	/**
	 * Format a date in the selected format
	 * @param year year
	 * @param monthOfYear month of the year
	 * @param dayOfMonth day of the month
	 * @param dateFormat the date format (use DATE_FORMAT_* constants
	 * @return the formated date
	 */
	public static String formatDate(int year, int monthOfYear, int dayOfMonth, String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, monthOfYear);
		cal.set(Calendar.DATE, dayOfMonth);

		return format.format(cal.getTime());
	}

	public static Date string2Date(String date, String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date result = null;
		try {
			result = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Format the current date in the selected format
	 * @param dateFormat the date format (use DATE_FORMAT_* constants
	 * @return the formated current date
	 */
	public static String getCurrentDateFormated(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		int mYear = cal.get(Calendar.YEAR);
		int mMonth = cal.get(Calendar.MONTH);
		int mDay = cal.get(Calendar.DAY_OF_MONTH);

		return formatDate(mYear,mMonth,mDay,DATE_FORMAT_DAYMONTHYEAR);
	}

	/**
	 * Compare two dates in String format
	 * @param o1 date 1
	 * @param o2 date 2
	 * @param format format
	 * @return
	 */
	public static int compareDateStrings(String o1, String o2, String format) {
		Date d1 = (DateUtils.string2Date(o1,format));
		Date d2 = (DateUtils.string2Date(o2,format));
		int result = d1.compareTo(d2);

		if (DEBUG) Log.i(TAG, "+++ compareDateStrings: o1=" + o1 + " o2=" + o2 + " ===> " + result + " +++ ");

		return result;
	}

	/**
	 * Converts a Date to a String in the selected format
	 * @param date the date
	 * @param dateFormat the date format (use DATE_FORMAT_* constants
	 * @return the formated selected date
	 */
	public static String Date2String(Date date, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int mYear = cal.get(Calendar.YEAR);
		int mMonth = cal.get(Calendar.MONTH);
		int mDay = cal.get(Calendar.DAY_OF_MONTH);
		return formatDate(mYear,mMonth,mDay,DATE_FORMAT_DAYMONTHYEAR);
	}

	/**
	 * Adds days to a date
	 * @param date the date
	 * @param days number of days to add
	 * @return the modified date
	 */
	public static Date addDays(Date date, int days)	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); //minus number would decrement the days
		return cal.getTime();
	}

	public static int getCurrentHour() {
		Calendar rightNow = Calendar.getInstance();
		int hour = rightNow.get(Calendar.HOUR_OF_DAY);
		return hour;
	}
}
