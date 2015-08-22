package com.ocasoft.drfood.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Alex on 15/08/2015.
 */
public class SharedPreferencesUtils {
	private static final String SHARED_PREFS_FILE = "drfood_prefs_file";

	// ======================================== Saved Values ========================================
	// TrackFoodActivity & TrackFoodListAdapter
	public static final String SP_CURRENTFOODTIME = "currentFoodTime";
	public static final String SP_CURRENTDAY = "currentDay";
	public static final String SP_CURRENTMONTH = "currentMonth";
	public static final String SP_CURRENTYEAR = "currentYear";

	// FoodDatabaseHelper
	public static final String SP_DBINITIALIZED = "dbInitialized";

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

	public static void setSharedPrefsFile(Context mContext, String parameter, boolean value) {
		SharedPreferences.Editor editor = getSettings(mContext).edit();
		editor.putBoolean(parameter, value);
		editor.commit();
	}
}