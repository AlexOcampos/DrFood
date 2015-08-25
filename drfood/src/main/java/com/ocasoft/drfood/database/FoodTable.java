package com.ocasoft.drfood.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ocasoft.drfood.R;
import com.ocasoft.drfood.infoobjects.Food;
import com.ocasoft.drfood.utils.FileManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 16/05/2015.
 */
public class FoodTable {
	private static final String TAG = "DRFOOD_FoodTable";
	private static final boolean DEBUG = true; //TODO : Disable DEBUG

	// Foods table name
	public static final String TABLE_NAME_FOOD = "food";

	// Foods Table Columns names
	public static final String COLUMN_NAME_FOOD_ID = "foodId"; // Id
	public static final String COLUMN_NAME_FOOD_REGISTRYDATE = "registrydate";
	public static final String COLUMN_NAME_FOOD_TIMEMOMENT = "timemoment"; // Breakfast, Lunch, Snack, Dinner
	public static final String COLUMN_NAME_FOOD_QUANTITY = "quantityDef"; // suggested quantity
	public static final String COLUMN_NAME_FOOD_ENERGY = "energy"; // X approximate calories
	public static final String COLUMN_NAME_FOOD_FATS = "fats";
	public static final String COLUMN_NAME_FOOD_PROTEINS = "proteins";
	public static final String COLUMN_NAME_FOOD_CARBOHYDRATES = "carbohydrates";
	public static final String COLUMN_NAME_FOOD_CATEGORY = "category"; // Green Food, Orange Food, Red Food
	public static final String COLUMN_NAME_FOOD_COMMENTS = "comments";
	public static final String COLUMN_NAME_FOOD_UNITY_MEASURE = "unitymeasure"; // Unity measure (gr, cup, litres...)
	public static final String COLUMN_NAME_FOOD_COUNTER = "counter";
	public static final String COLUMN_NAME_FOOD_NAME = "name"; // Food name
	public static final String COLUMN_NAME_FOOD_CODE = "code";

	// Aux tokens
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
					COLUMN_NAME_FOOD_NAME + TEXT_TYPE + COMMA_SEP +
					COLUMN_NAME_FOOD_CODE + TEXT_TYPE +
					" )";

	private static final String SQL_DELETE_FOODS =
			"DROP TABLE IF EXISTS " + TABLE_NAME_FOOD;

	public static void onCreate(SQLiteDatabase database, Context context) {
		if (DEBUG) Log.i(TAG, "+++ onCreate() FoodTable called! +++");
		database.execSQL(SQL_CREATE_FOODS);

//		addMockFoods(database); // Mockup foods
		readInitialFoods(database, context);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
								 int newVersion, Context context) {
		Log.w(FoodTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL(SQL_DELETE_FOODS);
		onCreate(database, context);
	}

	/**
	 * Read initial foods from initialFoods_*.txt
	 * @param context the app context
	 */
	private static void readInitialFoods(SQLiteDatabase database, Context context) {
		List<Food> db = FileManager.LoadText(context, R.raw.initialfoods0es);

		for (Food food : db) {
			ContentValues values = new ContentValues();
			//Get values from food object
			//values.put(COLUMN_NAME_FOOD_ID, food.getId());
			values.put(COLUMN_NAME_FOOD_CARBOHYDRATES, food.getCarbohydrates());
			values.put(COLUMN_NAME_FOOD_CATEGORY, food.getCategory());
			values.put(COLUMN_NAME_FOOD_COMMENTS, food.getComments());
			values.put(COLUMN_NAME_FOOD_COUNTER, food.getCounter());
			values.put(COLUMN_NAME_FOOD_ENERGY, food.getEnergy());
			values.put(COLUMN_NAME_FOOD_FATS, food.getFats());
			values.put(COLUMN_NAME_FOOD_NAME, food.getName());
			values.put(COLUMN_NAME_FOOD_PROTEINS, food.getProteins());
			values.put(COLUMN_NAME_FOOD_QUANTITY, food.getQuantityDefault());
			values.put(COLUMN_NAME_FOOD_REGISTRYDATE, food.getRegistryDate().toString());
			values.put(COLUMN_NAME_FOOD_TIMEMOMENT, food.getTimeMoment());
			values.put(COLUMN_NAME_FOOD_UNITY_MEASURE, food.getUnity_measure());
			values.put(COLUMN_NAME_FOOD_CODE, food.getCode());

			// Inserting Row
			database.insert(TABLE_NAME_FOOD, null, values);
		}
	}

	public static String addPrefix(String column) {
		return TABLE_NAME_FOOD + "." + column;
	}

	public static String removePrefix(String colum) {
		return colum.replace(TABLE_NAME_FOOD,"");
	}
}