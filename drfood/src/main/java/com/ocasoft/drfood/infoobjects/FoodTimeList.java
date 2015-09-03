package com.ocasoft.drfood.infoobjects;

import android.content.Context;

import com.ocasoft.drfood.utils.DateUtils;
import com.ocasoft.drfood.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 28/07/2015.
 */
public class FoodTimeList {
	private List<FoodTime> list;

	// TimeMoment values
	public static final int TIMEMOMENT_BREAKFAST 	= 1;
	public static final int TIMEMOMENT_LUNCH 		= 2;
	public static final int TIMEMOMENT_SNACK 		= 3;
	public static final int TIMEMOMENT_DINNER 		= 4;

	// TimeMoment Strings
	private static final String TIMEMOMENT_NAME_BREAKFAST = "Desayuno";
	private static final String TIMEMOMENT_NAME_LUNCH = "Comida";
	private static final String TIMEMOMENT_NAME_SNACK = "Merienda";
	private static final String TIMEMOMENT_NAME_DINNER = "Cena";

	public FoodTimeList() {
		list = new ArrayList<FoodTime>();
		addFoodTime(new FoodTime(TIMEMOMENT_NAME_BREAKFAST, TIMEMOMENT_BREAKFAST));
		addFoodTime(new FoodTime(TIMEMOMENT_NAME_LUNCH, TIMEMOMENT_LUNCH));
		addFoodTime(new FoodTime(TIMEMOMENT_NAME_SNACK, TIMEMOMENT_SNACK));
		addFoodTime(new FoodTime(TIMEMOMENT_NAME_DINNER, TIMEMOMENT_DINNER));
	}

	/**
	 * Return the FoodTime with the request Id. Null if doesn't exist
	 * @param id the id of the wanted FoodTime
	 * @return the FoodTime. Null if doesn't exist
	 */
	public FoodTime getById(int id) {
		FoodTime result = null;

		for(FoodTime f : list) {
			if (f.getId() == id) {
				result = f;
				break;
			}
		}
		return result;
	}

	/**
	 * Return the FoodTime with the request name. Null if doesn't exist
	 * @param name the name of the wanted FoodTime
	 * @return the FoodTime. Null if doesn't exist
	 */
	public FoodTime getByName(String name) {
		FoodTime result = null;

		for(FoodTime f : list) {
			if (f.getName().compareTo(name) == 0) {
				result = f;
				break;
			}
		}
		return result;
	}

	/**
	 * Add a new foodTime to the list
	 * @param foodTime the new foodTime
	 */
	private void addFoodTime(FoodTime foodTime) {
		list.add(foodTime);
	}

	/**
	 * Convert the list of FoodTimes in a ArrayList<String> with the names of the FoodTimes
	 * @return the ArrayList
	 */
	public ArrayList<String> toArrayList() {
		ArrayList<String> foodTimes = new ArrayList<String>();

		for (FoodTime f : list) {
			foodTimes.add(f.getName());
		}

		return foodTimes;
	}

	/**
	 * Return the id of the current foodTime in function of current hour
	 * @return the current timeMomentId (FoodTimeList.TIMEMOMENT_*)
	 */
	public static int getCurrentTimeMoment(Context mContext) {
		int currentHour = DateUtils.getCurrentHour();
		int currentMinute = DateUtils.getCurrentMinute();
		String currentTime = DateUtils.formatDate(currentHour, currentMinute, DateUtils.DATE_FORMAT_HOURMINUTE);

		// Breakfast
		String morningInit 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_MORNING_INIT);
		String morningEnd 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_MORNING_END);
		if (DateUtils.compareTimeBetweenStrings(morningInit,morningEnd,currentTime,DateUtils.DATE_FORMAT_HOURMINUTE)) {
			return TIMEMOMENT_BREAKFAST;
		}

		// Lunch
		String lunchInit 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_LUNCH_INIT);
		String lunchEnd 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_LUNCH_END);
		if (DateUtils.compareTimeBetweenStrings(lunchInit,lunchEnd,currentTime,DateUtils.DATE_FORMAT_HOURMINUTE)) {
			return TIMEMOMENT_LUNCH;
		}

		// Snack
		String snackInit 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_SNACK_INIT);
		String snackEnd 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_SNACK_END);
		if (DateUtils.compareTimeBetweenStrings(snackInit,snackEnd,currentTime,DateUtils.DATE_FORMAT_HOURMINUTE)) {
			return TIMEMOMENT_SNACK;
		}

		// Dinner
		String dinnerInit 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_DINNER_INIT);
		String dinnerEnd 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_DINNER_END);
		if (DateUtils.compareTimeBetweenStrings(dinnerInit,dinnerEnd,currentTime,DateUtils.DATE_FORMAT_HOURMINUTE)) {
			return TIMEMOMENT_DINNER;
		}

		// In other case is a...
		return TIMEMOMENT_BREAKFAST;
	}

	/**
	 * Get the name of the timeMoment
	 * @param id the id of the timeMoment
	 * @return the name of the timeMoment
	 */
	public static String getNameById(int id) {
		String result = "";
		switch (id) {
			case TIMEMOMENT_BREAKFAST:
				result = TIMEMOMENT_NAME_BREAKFAST;
				break;
			case TIMEMOMENT_LUNCH:
				result = TIMEMOMENT_NAME_LUNCH;
				break;
			case TIMEMOMENT_SNACK:
				result = TIMEMOMENT_NAME_SNACK;
				break;
			case TIMEMOMENT_DINNER:
				result = TIMEMOMENT_NAME_DINNER;
				break;
		}
		return result;
	}
}
