package com.ocasoft.drfood.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Dr Food
 * Created by Alex on 25/08/2015.
 */
public class TrackDiaryTable {
	private static final String TAG = "DRFOOD_TrackDailyTable";
	private static final boolean DEBUG = true; //TODO : Disable DEBUG

	// Foods table name
	public static final String TABLE_NAME_DIARY = "trackDiary";

	// Foods Table Columns names
	public static final String COLUMN_NAME_DIARY_USERID = "diaryUserId";
	public static final String COLUMN_NAME_DIARY_DATE = "diaryDate";
	public static final String COLUMN_NAME_DIARY_TIMEFOOD = "diaryTimeFood";
	public static final String COLUMN_NAME_DIARY_CALORIES = "diaryCalories";
	public static final String COLUMN_NAME_DIARY_PROTEINS = "diaryProteins";
	public static final String COLUMN_NAME_DIARY_FATS = "diaryFats";
	public static final String COLUMN_NAME_DIARY_CARBOHYDRATES = "diaryCarbohydrates";


	// Aux tokens
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";

	// Food create table sql
	private static final String SQL_CREATE_DIARY =
			"CREATE TABLE " + TABLE_NAME_DIARY + " (" +
					COLUMN_NAME_DIARY_DATE + TEXT_TYPE  + COMMA_SEP +
					COLUMN_NAME_DIARY_USERID + TEXT_TYPE + COMMA_SEP +
					COLUMN_NAME_DIARY_TIMEFOOD + TEXT_TYPE + COMMA_SEP +
					COLUMN_NAME_DIARY_CALORIES + TEXT_TYPE + COMMA_SEP +
					COLUMN_NAME_DIARY_PROTEINS + TEXT_TYPE + COMMA_SEP +
					COLUMN_NAME_DIARY_FATS + TEXT_TYPE + COMMA_SEP +
					COLUMN_NAME_DIARY_CARBOHYDRATES + TEXT_TYPE + " )";

	private static final String SQL_DELETE_DIARY =
			"DROP TABLE IF EXISTS " + TABLE_NAME_DIARY;

	public static void onCreate(SQLiteDatabase database) {
		if (DEBUG) Log.i(TAG, "+++ onCreate() FoodTable called! +++");
		database.execSQL(SQL_CREATE_DIARY);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
								 int newVersion) {
		Log.w(TrackDiaryTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL(SQL_DELETE_DIARY);
		onCreate(database);
	}

	public static String addPrefix(String column) {
		return TABLE_NAME_DIARY + "." + column;
	}

	public static String removePrefix(String colum) {
		return colum.replace(TABLE_NAME_DIARY,"");
	}
}
