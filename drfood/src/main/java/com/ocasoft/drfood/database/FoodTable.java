package com.ocasoft.drfood.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Alex on 16/05/2015.
 */
public class FoodTable {
	// Foods table name
	public static final String TABLE_NAME_FOOD = "food";

	// Foods Table Columns names
	public static final String COLUMN_NAME_FOOD_ID = "id";
	public static final String COLUMN_NAME_FOOD_REGISTRYDATE = "registrydate";
	public static final String COLUMN_NAME_FOOD_TIMEMOMENT = "timemoment";
	public static final String COLUMN_NAME_FOOD_QUANTITY = "quantity";
	public static final String COLUMN_NAME_FOOD_ENERGY = "energy";
	public static final String COLUMN_NAME_FOOD_FATS = "fats";
	public static final String COLUMN_NAME_FOOD_PROTEINS = "proteins";

	public static final String COLUMN_NAME_FOOD_CARBOHYDRATES = "carbohydrates";
	public static final String COLUMN_NAME_FOOD_CATEGORY = "category";
	public static final String COLUMN_NAME_FOOD_COMMENTS = "comments";
	public static final String COLUMN_NAME_FOOD_UNITY_MEASURE = "unitymeasure";
	public static final String COLUMN_NAME_FOOD_COUNTER = "counter";
	public static final String COLUMN_NAME_FOOD_NAME = "name";

	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";

	// Food create table sql
	private static final String SQL_CREATE_FOODS =
		"CREATE TABLE " + TABLE_NAME_FOOD + " (" +
			COLUMN_NAME_FOOD_ID + " INTEGER PRIMARY KEY," +
			COLUMN_NAME_FOOD_REGISTRYDATE + TEXT_TYPE + COMMA_SEP +
			COLUMN_NAME_FOOD_TIMEMOMENT + TEXT_TYPE + COMMA_SEP +
			COLUMN_NAME_FOOD_QUANTITY + TEXT_TYPE + COMMA_SEP +
			COLUMN_NAME_FOOD_ENERGY + TEXT_TYPE + COMMA_SEP +
			COLUMN_NAME_FOOD_FATS + TEXT_TYPE + COMMA_SEP +
			COLUMN_NAME_FOOD_PROTEINS + TEXT_TYPE + COMMA_SEP +
			COLUMN_NAME_FOOD_CARBOHYDRATES + TEXT_TYPE + COMMA_SEP +
			COLUMN_NAME_FOOD_CATEGORY + TEXT_TYPE + COMMA_SEP +
			COLUMN_NAME_FOOD_COMMENTS + TEXT_TYPE + COMMA_SEP +
			COLUMN_NAME_FOOD_UNITY_MEASURE + TEXT_TYPE + COMMA_SEP +
			COLUMN_NAME_FOOD_COUNTER + TEXT_TYPE + COMMA_SEP +
			COLUMN_NAME_FOOD_NAME + TEXT_TYPE +
			" )";

	private static final String SQL_DELETE_FOODS =
			"DROP TABLE IF EXISTS " + TABLE_NAME_FOOD;

	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(SQL_CREATE_FOODS);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
								 int newVersion) {
		Log.w(FoodTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL(SQL_DELETE_FOODS);
		onCreate(database);
	}
}
