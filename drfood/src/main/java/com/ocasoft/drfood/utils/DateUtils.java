package com.ocasoft.drfood.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Alex on 14/08/2015.
 */
public class DateUtils {
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
}
