package com.ocasoft.drfood.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.ocasoft.drfood.database.FoodDatabaseHelper;
import com.ocasoft.drfood.database.FoodTable;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Alex on 16/05/2015.
 */
public class FoodContentProvider extends ContentProvider {
	private static final String TAG = "DRFOOD_FoodContProv";
	private static final boolean DEBUG = true; //TODO : Disable DEBUG
	// database
	private FoodDatabaseHelper database;

	// used for the UriMacher
	private static final int FOODS = 10;
	private static final int FOOD_ID = 20;

	private static final String AUTHORITY = "com.ocasoft.drfood.contentprovider";
	private static final String BASE_PATH = "foods";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH);

	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/foods";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/food";

	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH, FOODS);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", FOOD_ID);
	}

	@Override
	public boolean onCreate() {
		database = new FoodDatabaseHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder) {
		if (DEBUG) Log.i(TAG, "+++ query() called! +++");

		// Uisng SQLiteQueryBuilder instead of query() method
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		// check if the caller has requested a column which does not exists
		checkColumns(projection);

		// Set the table
		queryBuilder.setTables(FoodTable.TABLE_NAME_FOOD);

		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
			case FOODS:
				if (DEBUG) Log.i(TAG, "+++ query() uriType FOODS! +++");
				break;
			case FOOD_ID:
				if (DEBUG) Log.i(TAG, "+++ query() uriType FOOD_ID + " + uri.getLastPathSegment() + "! +++");
				// adding the ID to the original query
				queryBuilder.appendWhere(FoodTable.COLUMN_NAME_FOOD_ID + "="
						+ uri.getLastPathSegment());
				break;
			default:
				throw new IllegalArgumentException("FoodContentprovider - query() - Unknown URI: " + uri);
		}

		SQLiteDatabase db = database.getWritableDatabase();
		Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

		// make sure that potential listeners are getting notified
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (DEBUG) Log.i(TAG, "+++ insert() called! +++");
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		long id = 0;
		switch (uriType) {
			case FOODS:
				id = sqlDB.insert(FoodTable.TABLE_NAME_FOOD, null, values);
				break;
			default:
				throw new IllegalArgumentException("FoodContentprovider - insert() - Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASE_PATH + "/" + id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		if (DEBUG) Log.i(TAG, "+++ delete() called! +++");
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;
		switch (uriType) {
			case FOODS:
				rowsDeleted = sqlDB.delete(FoodTable.TABLE_NAME_FOOD, selection,
						selectionArgs);
				break;
			case FOOD_ID:
				String id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsDeleted = sqlDB.delete(FoodTable.TABLE_NAME_FOOD,
							FoodTable.COLUMN_NAME_FOOD_ID + "=" + id,
							null);
				} else {
					rowsDeleted = sqlDB.delete(FoodTable.TABLE_NAME_FOOD,
							FoodTable.COLUMN_NAME_FOOD_ID + "=" + id
									+ " and " + selection,
							selectionArgs);
				}
				break;
			default:
				throw new IllegalArgumentException("FoodContentprovider - delete() - Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		if (DEBUG) Log.i(TAG, "+++ update() called! +++");
		int uriType = sURIMatcher.match(uri);
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsUpdated = 0;
		switch (uriType) {
			case FOODS:
				rowsUpdated = sqlDB.update(FoodTable.TABLE_NAME_FOOD,
						values,
						selection,
						selectionArgs);
				break;
			case FOOD_ID:
				String id = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsUpdated = sqlDB.update(FoodTable.TABLE_NAME_FOOD,
							values,
							FoodTable.COLUMN_NAME_FOOD_ID + "=" + id,
							null);
				} else {
					rowsUpdated = sqlDB.update(FoodTable.TABLE_NAME_FOOD,
							values,
							FoodTable.COLUMN_NAME_FOOD_ID + "=" + id
									+ " and "
									+ selection,
							selectionArgs);
				}
				break;
			default:
				throw new IllegalArgumentException("FoodContentprovider - update() - Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}

	private void checkColumns(String[] projection) {
		if (DEBUG) Log.i(TAG, "+++ checkColumns() called! +++");
		String[] available = { FoodTable.COLUMN_NAME_FOOD_ID,
				FoodTable.COLUMN_NAME_FOOD_REGISTRYDATE,
				FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT,
				FoodTable.COLUMN_NAME_FOOD_QUANTITY,
				FoodTable.COLUMN_NAME_FOOD_ENERGY,
				FoodTable.COLUMN_NAME_FOOD_FATS,
				FoodTable.COLUMN_NAME_FOOD_PROTEINS,
				FoodTable.COLUMN_NAME_FOOD_CARBOHYDRATES,
				FoodTable.COLUMN_NAME_FOOD_CATEGORY,
				FoodTable.COLUMN_NAME_FOOD_COMMENTS,
				FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE,
				FoodTable.COLUMN_NAME_FOOD_COUNTER,
				FoodTable.COLUMN_NAME_FOOD_NAME};
		if (projection != null) {

			// ++++++++++++++++++++++++++++++++++++++++++ DEGUG +++++++++++++++++++++++++++++++++++++++++++++++++++
//			if (DEBUG) {
//				String projectionsValues = "";
//				String availableValues = "";
//				for (String p : projection) {
//					projectionsValues += (p + ",");
//				}
//				for (String a : available) {
//					availableValues += (a + ",");
//				}
//				Log.i(TAG, "+++ checkColumns() projections: " + projectionsValues + "+++");
//				Log.i(TAG, "+++ checkColumns() availables: " + availableValues + "+++");
//			}
			// ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
			// check if all columns which are requested are available
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException("FoodContentprovider - checkColumns() - Unknown columns in projection");
			}
		}
	}
}
