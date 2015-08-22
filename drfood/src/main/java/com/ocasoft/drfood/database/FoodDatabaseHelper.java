package com.ocasoft.drfood.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ocasoft.drfood.utils.SharedPreferencesUtils;

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

	private Context mContext;

	public FoodDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = context;

		if (!checkDataBase()) {
			// Create DataBase and initilize FoodTable
			Log.i(TAG, "+++ Initializing the DB... +++");
			FoodTable.onCreate(this.getWritableDatabase(), mContext);
			SharedPreferencesUtils.setSharedPrefsFile(mContext,SharedPreferencesUtils.SP_DBINITIALIZED,true);
			Log.i(TAG, "+++ DB initialized! +++");
		}

	}

	/**
	 * Check if the database exist and can be read.
	 *
	 * @return true if it exists and can be read, false if it doesn't
	 */
	private boolean checkDataBase() {
		boolean check = SharedPreferencesUtils.getSharedPrefBooleanValue(mContext, SharedPreferencesUtils.SP_DBINITIALIZED);

		if (check) { // if true database was initialized...
			return true;
		}

		// ...in other case we must open the database to check it
		SQLiteDatabase checkDB = null;
		try {
			checkDB = SQLiteDatabase.openDatabase(DATABASE_NAME, null,
					SQLiteDatabase.OPEN_READONLY);
			checkDB.close();
		} catch (SQLiteException e) {
			// database doesn't exist yet.
		}
		return checkDB != null;
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		if (DEBUG) Log.i(TAG, "+++ onCreate() called! +++");
		//FoodTable.onCreate(database,mContext);
		TrackFoodTable.onCreate(database);
		UserTable.onCreate(database);
		if (DEBUG) Log.i(TAG, "+++ onCreate() finished! +++");
	}

	// Method is called during an upgrade of the database,
	// e.g. if you increase the database version
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		if (DEBUG) Log.i(TAG, "+++ onUpgrade() called! +++");
		FoodTable.onUpgrade(database, oldVersion, newVersion, mContext);
		TrackFoodTable.onUpgrade(database, oldVersion, newVersion);
		UserTable.onUpgrade(database, oldVersion, newVersion);
		if (DEBUG) Log.i(TAG, "+++ onUpgrade() finished! +++");
	}
}
