package com.ocasoft.drfood.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Alex on 16/05/2015.
 */
public class FoodDatabaseHelper extends SQLiteOpenHelper {
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
		FoodTable.onCreate(database);
	}

	// Method is called during an upgrade of the database,
	// e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
						  int newVersion) {
		FoodTable.onUpgrade(database, oldVersion, newVersion);
	}

}
