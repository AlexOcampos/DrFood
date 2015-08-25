package com.ocasoft.drfood.dbhandlers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.ocasoft.drfood.infoobjects.Food;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class DatabaseHandler extends SQLiteOpenHelper {

	// If you change the database schema, you must increment the database version.
	// Database Version
	public static final int DATABASE_VERSION = 1;

	// Database Name
	public static final String DATABASE_NAME = "drfood.db";

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

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_FOODS);
	}


	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL(SQL_DELETE_FOODS); //Foods

		// Create tables again
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}


	/**
	 * FOOD CRUD OPERATIONS
	 */

	// Adding new food
	public void addFood(Food food) {
		SQLiteDatabase db = this.getWritableDatabase();

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


		// Inserting Row
		db.insert(TABLE_NAME_FOOD, null, values);
		db.close(); // Closing database connection
	}

	// Getting single food
	public Food getFood(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_NAME_FOOD, new String[] {
						COLUMN_NAME_FOOD_ID,
						COLUMN_NAME_FOOD_CARBOHYDRATES,
						COLUMN_NAME_FOOD_CATEGORY,
						COLUMN_NAME_FOOD_COMMENTS,
						COLUMN_NAME_FOOD_COUNTER,
						COLUMN_NAME_FOOD_ENERGY,
						COLUMN_NAME_FOOD_FATS,
						COLUMN_NAME_FOOD_NAME,
						COLUMN_NAME_FOOD_PROTEINS,
						COLUMN_NAME_FOOD_QUANTITY,
						COLUMN_NAME_FOOD_REGISTRYDATE,
						COLUMN_NAME_FOOD_TIMEMOMENT,
						COLUMN_NAME_FOOD_UNITY_MEASURE},
				COLUMN_NAME_FOOD_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Food food = new Food(
				Integer.parseInt(cursor.getString(0)),
				cursor.getString(1),
				Integer.parseInt(cursor.getString(2)),
				Integer.parseInt(cursor.getString(3)),
				-1,
				Integer.parseInt(cursor.getString(4)),
				Double.parseDouble(cursor.getString(5)),
				Double.parseDouble(cursor.getString(6)),
				Double.parseDouble(cursor.getString(7)),
				Integer.parseInt(cursor.getString(8)),
				cursor.getString(9),
				cursor.getString(10),
				Integer.parseInt(cursor.getString(11)),
				cursor.getString(12),
				cursor.getString(13));

		// return contact
		return food;
	}

	// Getting All food
	public List<Food> getAllFoods() {
		List<Food> foodList = new ArrayList<Food>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_NAME_FOOD;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Log.i("BD_TEST", "[ " + cursor.getString(0) + " || "
						+ cursor.getString(1) + " || "
						+ cursor.getString(2) + " || "
						+ cursor.getString(3) + " || "
						+ cursor.getString(4) + " || "
						+ cursor.getString(5) + " || "
						+ cursor.getString(6) + " || "
						+ cursor.getString(7) + " || "
						+ cursor.getString(8) + " || "
						+ cursor.getString(9) + " || "
						+ cursor.getString(10) + " || "
						+ cursor.getString(11) + " || "
						+ cursor.getString(12) + " ]");
//				Food food = new Food(
//						Integer.parseInt(cursor.getString(0)),
//						Date.valueOf(cursor.getString(1)),
//						cursor.getString(2),
//						Integer.parseInt(cursor.getString(3)),
//						Integer.parseInt(cursor.getString(4)),
//						Integer.parseInt(cursor.getString(5)),
//						Integer.parseInt(cursor.getString(6)),
//						Integer.parseInt(cursor.getString(7)),
//						cursor.getString(8),
//						cursor.getString(9),
//						cursor.getString(10),
//						Integer.parseInt(cursor.getString(11)),
//						cursor.getString(12));
				// Adding contact to list
//				foodList.add(food);
			} while (cursor.moveToNext());
		}

		// return food list
		return foodList;
	}

	// Getting Food Count
	public int getFoodsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_NAME_FOOD;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

	// Updating single Food
	public int updateFood(Food food) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

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

		// updating row
		return db.update(TABLE_NAME_FOOD, values, COLUMN_NAME_FOOD_ID + " = ?",
				new String[] { String.valueOf(food.getId()) });
	}

	// Deleting single food
	public void deleteFood(Food food) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME_FOOD, COLUMN_NAME_FOOD_ID + " = ?",
				new String[] { String.valueOf(food.getId()) });
		db.close();
	}

}
