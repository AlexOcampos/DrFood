package com.ocasoft.drfood.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ocasoft.drfood.infoobjects.Food;

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
	public static final String COLUMN_NAME_FOOD_QUANTITY = "quantity"; // suggested quantity
	public static final String COLUMN_NAME_FOOD_ENERGY = "energy"; // X approximate calories
	public static final String COLUMN_NAME_FOOD_FATS = "fats";
	public static final String COLUMN_NAME_FOOD_PROTEINS = "proteins";

	public static final String COLUMN_NAME_FOOD_CARBOHYDRATES = "carbohydrates";
	public static final String COLUMN_NAME_FOOD_CATEGORY = "category"; // Green Food, Orange Food, Red Food
	public static final String COLUMN_NAME_FOOD_COMMENTS = "comments";
	public static final String COLUMN_NAME_FOOD_UNITY_MEASURE = "unitymeasure"; // Unity measure (gr, cup, litres...)
	public static final String COLUMN_NAME_FOOD_COUNTER = "counter";
	public static final String COLUMN_NAME_FOOD_NAME = "name"; // Food name

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
		if (DEBUG) Log.i(TAG, "+++ onCreate() FoodTable called! +++");
		database.execSQL(SQL_CREATE_FOODS);

		addMockFoods(database);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
								 int newVersion) {
		Log.w(FoodTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL(SQL_DELETE_FOODS);
		onCreate(database);
	}

	// Add a mockupdatabase
	private static void addMockFoods(SQLiteDatabase database) {
		if (DEBUG) Log.i(TAG, "Inserting mockup database...");
		// Mockup data base
		List<Food> db = new ArrayList<Food>();
		db.add(new Food(1, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "green food", "comment1", "gr", 1, "tortilla de patatas"));
		db.add(new Food(2, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "green food", "comment1", "gr", 1, "Gazpacho"));
		db.add(new Food(3, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "green food", "comment1", "gr", 1, "Paella"));
		db.add(new Food(4, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "green food", "comment1", "gr", 1, "Calamares en su tinta"));
		db.add(new Food(5, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "green food", "comment1", "gr", 1, "chicharro al chacolí"));
		db.add(new Food(6, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "green food", "comment1", "gr", 1, "Migas de Teruel"));
		db.add(new Food(7, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "green food", "comment1", "gr", 1, "Escalibada"));
		db.add(new Food(8, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "green food", "comment1", "gr", 1, "Cocido madrileño"));
		db.add(new Food(9, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "green food", "comment1", "gr", 1, "Tarta de Santiago"));
		db.add(new Food(10, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "orange food", "comment1", "gr", 1, "Hojuelas"));
		db.add(new Food(11, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "orange food", "comment1", "gr", 1, "Pulpo a la gallega"));
		db.add(new Food(12, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "orange food", "comment1", "gr", 1, "Fabada"));
		db.add(new Food(13, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "orange food", "comment1", "gr", 1, "Lentejas"));
		db.add(new Food(14, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "orange food", "comment1", "gr", 1, "Patatas a la riojana"));
		db.add(new Food(15, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "orange food", "comment1", "gr", 1, "Bacalao a la vizcaina"));
		db.add(new Food(16, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "red food", "comment1", "gr", 1, "Caldereta de langosta"));
		db.add(new Food(17, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "red food", "comment1", "gr", 1, "Sopa de ajo"));
		db.add(new Food(18, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "red food", "comment1", "gr", 1, "Ajo blanco"));
		db.add(new Food(19, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "red food", "comment1", "gr", 1, "Fetuccini a la puttanesca"));
		db.add(new Food(20, new java.util.Date(), "Afternoon", 100, 1, 1, 1, 1, "red food", "comment1", "gr", 1, "Spaguett alla Bolognese"));

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
			values.put(COLUMN_NAME_FOOD_QUANTITY, food.getQuantity());
			values.put(COLUMN_NAME_FOOD_REGISTRYDATE, food.getRegistryDate().toString());
			values.put(COLUMN_NAME_FOOD_TIMEMOMENT, food.getTimeMoment());
			values.put(COLUMN_NAME_FOOD_UNITY_MEASURE, food.getUnity_measure());

			// Inserting Row
			database.insert(TABLE_NAME_FOOD, null, values);
		}
//		database.close(); // Closing database connection
	}

	public static String addPrefix(String column) {
		return TABLE_NAME_FOOD + "." + column;
	}

	public static String removePrefix(String colum) {
		return colum.replace(TABLE_NAME_FOOD,"");
	}
}