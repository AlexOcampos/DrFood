package com.ocasoft.drfood.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Alex on 16/05/2015.
 */
public class FoodDatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = "DRFOOD_FoodDBHelper";
	private static final boolean DEBUG = true; //TODO : Disable DEBUG

	// If you change the database schema, you must increment the database version.
	// Database Version
	public static final int DATABASE_VERSION = 1;

	// Database Name
	public static final String DATABASE_NAME = "drfood.db";

	public FoodDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		if (DEBUG) Log.i(TAG, "+++ onCreate() called! +++");
		FoodTable.onCreate(database);
		TrackFoodTable.onCreate(database);
		UserTable.onCreate(database);
		if (DEBUG) Log.i(TAG, "+++ onCreate() finished! +++");
	}

	// Method is called during an upgrade of the database,
	// e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		if (DEBUG) Log.i(TAG, "+++ onUpgrade() called! +++");
		FoodTable.onUpgrade(database, oldVersion, newVersion);
		TrackFoodTable.onUpgrade(database, oldVersion, newVersion);
		UserTable.onUpgrade(database, oldVersion, newVersion);
		if (DEBUG) Log.i(TAG, "+++ onUpgrade() finished! +++");
	}
}
