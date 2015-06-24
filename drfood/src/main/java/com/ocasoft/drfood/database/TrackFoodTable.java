package com.ocasoft.drfood.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Alex on 06/06/2015.
 */
public class TrackFoodTable {
	// Foods table name
	public static final String TABLE_NAME_TRACKFOOD = "trackfood";

	// Foods Table Columns names
	public static final String COLUMN_NAME_TRACKFOOD_ID = "id"; // Id
	public static final String COLUMN_NAME_TRACKFOOD_FOOD_ID = "foodId"; // Food id
	public static final String COLUMN_NAME_TRACKFOOD_TIMEMOMENT_ID = "timemomentId"; // Time moment id
	public static final String COLUMN_NAME_TRACKFOOD_USER_ID = "userId"; // User id
	public static final String COLUMN_NAME_TRACKFOOD_QUANTITY = "quantity"; // Quantity of food

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";

	// Food create table sql
	private static final String SQL_CREATE_TRACKFOOD =
			"CREATE TABLE " + TABLE_NAME_TRACKFOOD + " (" +
					COLUMN_NAME_TRACKFOOD_ID + " INTEGER PRIMARY KEY," +
					COLUMN_NAME_TRACKFOOD_FOOD_ID + TEXT_TYPE + COMMA_SEP +
					COLUMN_NAME_TRACKFOOD_TIMEMOMENT_ID + TEXT_TYPE + COMMA_SEP +
					COLUMN_NAME_TRACKFOOD_USER_ID + TEXT_TYPE + COMMA_SEP +
					COLUMN_NAME_TRACKFOOD_QUANTITY + TEXT_TYPE +
					" )";

	private static final String SQL_DELETE_TRACKFOOD =
			"DROP TABLE IF EXISTS " + TABLE_NAME_TRACKFOOD;

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(SQL_CREATE_TRACKFOOD);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
								 int newVersion) {
		Log.w(TrackFoodTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL(SQL_DELETE_TRACKFOOD);
		onCreate(database);
	}
}