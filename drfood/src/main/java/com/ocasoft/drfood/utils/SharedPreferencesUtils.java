package com.ocasoft.drfood.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Alex on 15/08/2015.
 */
public class SharedPreferencesUtils {
	private static final String SHARED_PREFS_FILE = "drfood_prefs_file";

	// ======================================== Saved Values ========================================
	// HomeFragment => TrackFoodActivity
	/**
	 * Boolean - True if TrackFoodActivity was called from HomeFragment. In this case, it will display current Day and
	 * current FoodTime.
	 */
	public static final String SP_TFA_CALLED_FROM_HOME = "tfaCalledFromHome";

	// TrackFoodActivity => TrackFoodListAdapter
	public static final String SP_CURRENTFOODTIME = "currentFoodTime";
	public static final String SP_CURRENTDAY = "currentDay";
	public static final String SP_CURRENTMONTH = "currentMonth";
	public static final String SP_CURRENTYEAR = "currentYear";

	// ConfigurationFragment
	/**
	 * String - User name
	 */
	public static final String SP_USER_NAME = "userName";
	/**
	 * String - User email
	 */
	public static final String SP_USER_EMAIL = "userEmail";
	/**
	 * String - Login
	 */
	public static final String SP_USER_COD = "userCod";


	// FoodDatabaseHelper
	public static final String SP_DBINITIALIZED = "dbInitialized";

	/**
	 * Boolean - true if there aren't any trackings
	 */
	public static final String SP_DBNOTEMPTY = "dbEmpty";

	public static final String SP_TM_MORNING_INIT = "tmMorningInit";
	public static final String SP_TM_MORNING_END = "tmMorningEnd";
	public static final String SP_TM_LUNCH_INIT = "tmLuchInit";
	public static final String SP_TM_LUNCH_END = "tmLuchEnd";
	public static final String SP_TM_SNACK_INIT = "tmSnackInit";
	public static final String SP_TM_SNACK_END = "tmSnackEnd";
	public static final String SP_TM_DINNER_INIT = "tmDinnerInit";
	public static final String SP_TM_DINNER_END = "tmDinnerEnd";

	private static SharedPreferences getSettings(Context mContext){
		return mContext.getSharedPreferences(SHARED_PREFS_FILE, 0);
	}

	// ======================================== Getters ========================================
	public static String getSharedPrefStringValue(Context mContext, String parameter) {
		return getSettings(mContext).getString(parameter, null);
	}

	public static int getSharedPrefIntValue(Context mContext, String parameter) {
		return getSettings(mContext).getInt(parameter, -1);
	}

	public  static boolean getSharedPrefBooleanValue(Context mContext, String parameter) {
		return getSettings(mContext).getBoolean(parameter, false);
	}

	// ======================================== Setters ========================================
	public static void setSharedPrefValue(Context mContext, String parameter, String value){
		SharedPreferences.Editor editor = getSettings(mContext).edit();
		editor.putString(parameter, value);
		editor.commit();
	}

	public static void setSharedPrefValue(Context mContext, String parameter, int value){
		SharedPreferences.Editor editor = getSettings(mContext).edit();
		editor.putInt(parameter, value);
		editor.commit();
	}

	public static void setSharedPrefValue(Context mContext, String parameter, boolean value) {
		SharedPreferences.Editor editor = getSettings(mContext).edit();
		editor.putBoolean(parameter, value);
		editor.commit();
	}
}
