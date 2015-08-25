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
import com.ocasoft.drfood.database.TrackDiaryTable;
import com.ocasoft.drfood.database.TrackFoodTable;
import com.ocasoft.drfood.database.UserTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Alex on 16/05/2015.
 */
public class FoodContentProvider extends ContentProvider {
	private static final String TAG = "DRFOOD_FoodCProvider";
	private static final boolean DEBUG = true; //TODO : Disable DEBUG
	// database
	private FoodDatabaseHelper database;

	// used for the UriMacher
	private static final int FOODS = 10;
	private static final int FOOD_ID = 20;
	private static final int TRACKS = 30;
	private static final int TRACK_ID = 40;
	private static final int TRACK_FOOD = 50;
	private static final int USERS = 60;
	private static final int USER_ID = 70;
	private static final int DIARIES = 80;
	private static final int DIARY_ID = 90;

	private static final String AUTHORITY = "com.ocasoft.drfood.provider";

	// Tables of the database
	private static final String BASE_PATH_FOOD = "foods";
	private static final String BASE_PATH_TRACK = "tracks";
	private static final String BASE_PATH_TRACK_FOODS = "tracksfoods";
	private static final String BASE_PATH_USER = "user";
	private static final String BASE_PATH_DIARY = "diary";

	// Uris
	public static final Uri CONTENT_URI_FOOD = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_FOOD);
	public static final Uri CONTENT_URI_TRACK = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_TRACK);
	public static final Uri CONTENT_URI_TRACKEDFOOD = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_TRACK_FOODS);
	public static final Uri CONTENT_URI_USER = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_USER);
	public static final Uri CONTENT_URI_DIARY = Uri.parse("content://" + AUTHORITY
			+ "/" + BASE_PATH_DIARY);

	// ??
	public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/foods";
	public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/food";

	// UriMatcher
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static {
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_FOOD, FOODS);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_FOOD + "/#", FOOD_ID);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TRACK, TRACKS);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TRACK + "/#", TRACK_ID);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_TRACK_FOODS, TRACK_FOOD); //Returns info tracks+food
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_USER, USERS);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_USER + "/#", USER_ID);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_DIARY, DIARIES);
		sURIMatcher.addURI(AUTHORITY, BASE_PATH_DIARY + "/#", DIARY_ID);
	}

	@Override
	public boolean onCreate() {
		if (DEBUG) Log.i(TAG, "+++ onCreate() called! +++");
		database = new FoodDatabaseHelper(getContext());
		if (DEBUG) Log.i(TAG, "+++ onCreate() finished! +++");
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder) {

		// Uisng SQLiteQueryBuilder instead of query() method
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		int uriType = sURIMatcher.match(uri);
		switch (uriType) {
			case FOODS:
				if (DEBUG) Log.i(TAG, "+++ query() uriType FOODS! +++");

				// check if the caller has requested a column which does not exists
				checkColumns(projection,FoodTable.TABLE_NAME_FOOD);

				// Set the table
				queryBuilder.setTables(FoodTable.TABLE_NAME_FOOD);

				break;
			case FOOD_ID:
				if (DEBUG) Log.i(TAG, "+++ query() uriType FOOD_ID + " + uri.getLastPathSegment() + "! +++");

				// check if the caller has requested a column which does not exists
				checkColumns(projection,FoodTable.TABLE_NAME_FOOD);

				// Set the table
				queryBuilder.setTables(FoodTable.TABLE_NAME_FOOD);

				// adding the ID to the original query
				queryBuilder.appendWhere(FoodTable.COLUMN_NAME_FOOD_ID + "="
						+ uri.getLastPathSegment());

				break;
			case TRACKS:
				if (DEBUG) Log.i(TAG, "+++ query() uriType TRACKS! +++");

				// check if the caller has requested a column which does not exists
				checkColumns(projection,TrackFoodTable.TABLE_NAME_TRACKFOOD);

				// Set the table
				queryBuilder.setTables(TrackFoodTable.TABLE_NAME_TRACKFOOD);

				break;
			case TRACK_ID:
				if (DEBUG) Log.i(TAG, "+++ query() uriType TRACK_ID + " + uri.getLastPathSegment() + "! +++");

				// check if the caller has requested a column which does not exists
				checkColumns(projection,TrackFoodTable.TABLE_NAME_TRACKFOOD);

				// Set the table
				queryBuilder.setTables(TrackFoodTable.TABLE_NAME_TRACKFOOD);

				// adding the ID to the original query
				queryBuilder.appendWhere(TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID + "="
						+ uri.getLastPathSegment());
				break;
			case TRACK_FOOD:
				if (DEBUG) Log.i(TAG, "+++ query() uriType TRACK_FOOD + " + uri.getLastPathSegment() + "! +++");

				// Set the table (trackfood left join food)
				String table = TrackFoodTable.TABLE_NAME_TRACKFOOD;
				StringBuilder sb = new StringBuilder();
				sb.append(TrackFoodTable.TABLE_NAME_TRACKFOOD);
				sb.append(" LEFT OUTER JOIN ");
				sb.append(FoodTable.TABLE_NAME_FOOD);
				sb.append(" ON (");
				sb.append(TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_FOOD_ID));
				sb.append(" = ");
				sb.append(FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_ID));
				sb.append(")");
				table = sb.toString();

				queryBuilder.setTables(table);

				// check if the caller has requested a column which does not exists
				checkColumns(projection, Integer.toString(TRACK_FOOD));

				break;
			case USERS:
				if (DEBUG) Log.i(TAG, "+++ query() uriType USERS! +++");

				// check if the caller has requested a column which does not exists
				checkColumns(projection, UserTable.TABLE_NAME_USER);

				// Set the table
				queryBuilder.setTables(UserTable.TABLE_NAME_USER);

				break;
			case USER_ID:
				if (DEBUG) Log.i(TAG, "+++ query() uriType USER_ID + " + uri.getLastPathSegment() + "! +++");

				// check if the caller has requested a column which does not exists
				checkColumns(projection,UserTable.TABLE_NAME_USER);

				// Set the table
				queryBuilder.setTables(UserTable.TABLE_NAME_USER);

				// adding the ID to the original query
				queryBuilder.appendWhere(UserTable.COLUMN_NAME_USER_ID + "="
						+ uri.getLastPathSegment());
				break;
			case DIARIES:
				if (DEBUG) Log.i(TAG, "+++ query() uriType DIARIES! +++");

				// check if the caller has requested a column which does not exists
				checkColumns(projection,TrackDiaryTable.TABLE_NAME_DIARY);

				// Set the table
				queryBuilder.setTables(TrackDiaryTable.TABLE_NAME_DIARY);

				break;
			case DIARY_ID:
				if (DEBUG) Log.i(TAG, "+++ query() uriType DIARY_ID + " + uri.getLastPathSegment() + "! +++");

				// check if the caller has requested a column which does not exists
				checkColumns(projection,TrackDiaryTable.TABLE_NAME_DIARY);

				// Set the table
				queryBuilder.setTables(TrackDiaryTable.TABLE_NAME_DIARY);

				// adding the ID to the original query
				queryBuilder.appendWhere(TrackDiaryTable.TABLE_NAME_DIARY + "="
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
		String uriToParse = "";

		long id = 0;
		switch (uriType) {
			case FOODS:
				id = sqlDB.insert(FoodTable.TABLE_NAME_FOOD, null, values);
				uriToParse = BASE_PATH_FOOD + "/" + id;
				break;
			case TRACKS:
				id = sqlDB.insert(TrackFoodTable.TABLE_NAME_TRACKFOOD, null, values);
				uriToParse = BASE_PATH_TRACK + "/" + id;
				break;
			case USERS:
				id = sqlDB.insert(UserTable.TABLE_NAME_USER, null, values);
				uriToParse = BASE_PATH_USER + "/" + id;
				break;
			case DIARIES:
				id = sqlDB.insert(TrackDiaryTable.TABLE_NAME_DIARY, null, values);
				uriToParse = BASE_PATH_DIARY + "/" + id;
				break;
			default:
				throw new IllegalArgumentException("FoodContentprovider - insert() - Unknown URI: " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);

		return Uri.parse(uriToParse);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		if (DEBUG) Log.i(TAG, "+++ delete() called! +++");
		int uriType = sURIMatcher.match(uri);
		if (DEBUG) Log.i(TAG, "+++ delete() uriType = " + uriType + " +++");
		SQLiteDatabase sqlDB = database.getWritableDatabase();
		int rowsDeleted = 0;

		switch (uriType) {
			case FOODS:
				rowsDeleted = sqlDB.delete(FoodTable.TABLE_NAME_FOOD, selection,
						selectionArgs);
				break;
			case FOOD_ID:
				String idFood = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsDeleted = sqlDB.delete(FoodTable.TABLE_NAME_FOOD,
							FoodTable.COLUMN_NAME_FOOD_ID + "=" + idFood,
							null);
				} else {
					rowsDeleted = sqlDB.delete(FoodTable.TABLE_NAME_FOOD,
							FoodTable.COLUMN_NAME_FOOD_ID + "=" + idFood
									+ " and " + selection,
							selectionArgs);
				}
				break;
			case TRACKS:
				if (DEBUG) Log.i(TAG, "+++ delete() TRACKS called! +++");
				rowsDeleted = sqlDB.delete(TrackFoodTable.TABLE_NAME_TRACKFOOD, selection,
						selectionArgs);
				break;
			case TRACK_ID:
				if (DEBUG) Log.i(TAG, "+++ delete() TRACK_ID called! +++");
				String idTrack = uri.getLastPathSegment();
				if (DEBUG) Log.i(TAG, "+++ delete() TRACK_ID idTrack = " + idTrack + " +++");
				if (TextUtils.isEmpty(selection)) {
					rowsDeleted = sqlDB.delete(TrackFoodTable.TABLE_NAME_TRACKFOOD,
							TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID + "=" + idTrack,
							null);
					if (DEBUG) Log.i(TAG, "+++ delete() TRACK_ID-isEmpty rowsDeleted= " + rowsDeleted + " called! +++");
				} else {
					rowsDeleted = sqlDB.delete(TrackFoodTable.TABLE_NAME_TRACKFOOD,
							TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID + "=" + idTrack
									+ " and " + selection,
							selectionArgs);
					if (DEBUG) Log.i(TAG, "+++ delete() TRACK_ID-notEmpty = " + rowsDeleted + " called! +++");
					if (DEBUG) Log.i(TAG, "+++ delete() TRACK_ID-notEmpty > " + selection + " called! +++");
				}
				break;
			case USERS:
				rowsDeleted = sqlDB.delete(UserTable.TABLE_NAME_USER, selection,
						selectionArgs);
				break;
			case USER_ID:
				String idUser = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsDeleted = sqlDB.delete(UserTable.TABLE_NAME_USER,
							UserTable.COLUMN_NAME_USER_ID + "=" + idUser,
							null);
				} else {
					rowsDeleted = sqlDB.delete(UserTable.TABLE_NAME_USER,
							UserTable.COLUMN_NAME_USER_ID + "=" + idUser
									+ " and " + selection,
							selectionArgs);
				}
				break;
			case DIARIES:
				rowsDeleted = sqlDB.delete(TrackDiaryTable.TABLE_NAME_DIARY, selection,
						selectionArgs);
				break;
			case DIARY_ID:
				String idDiary = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsDeleted = sqlDB.delete(TrackDiaryTable.TABLE_NAME_DIARY,
							TrackDiaryTable.COLUMN_NAME_DIARY_DATE + "=" + idDiary,
							null);
				} else {
					rowsDeleted = sqlDB.delete(TrackDiaryTable.TABLE_NAME_DIARY,
							TrackDiaryTable.COLUMN_NAME_DIARY_DATE + "=" + idDiary
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
				String idFood = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsUpdated = sqlDB.update(FoodTable.TABLE_NAME_FOOD,
							values,
							FoodTable.COLUMN_NAME_FOOD_ID + "=" + idFood,
							null);
				} else {
					rowsUpdated = sqlDB.update(FoodTable.TABLE_NAME_FOOD,
							values,
							FoodTable.COLUMN_NAME_FOOD_ID + "=" + idFood
									+ " and "
									+ selection,
							selectionArgs);
				}
				break;
			case TRACKS:
				rowsUpdated = sqlDB.update(TrackFoodTable.TABLE_NAME_TRACKFOOD,
						values,
						selection,
						selectionArgs);
				break;
			case TRACK_ID:
				String idTrack = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsUpdated = sqlDB.update(TrackFoodTable.TABLE_NAME_TRACKFOOD,
							values,
							TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID + "=" + idTrack,
							null);
				} else {
					rowsUpdated = sqlDB.update(TrackFoodTable.TABLE_NAME_TRACKFOOD,
							values,
							TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID + "=" + idTrack
									+ " and "
									+ selection,
							selectionArgs);
				}
				break;
			case USERS:
				rowsUpdated = sqlDB.update(UserTable.TABLE_NAME_USER,
						values,
						selection,
						selectionArgs);
				break;
			case USER_ID:
				String idUser = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsUpdated = sqlDB.update(UserTable.TABLE_NAME_USER,
							values,
							UserTable.COLUMN_NAME_USER_ID + "=" + idUser,
							null);
				} else {
					rowsUpdated = sqlDB.update(UserTable.TABLE_NAME_USER,
							values,
							UserTable.COLUMN_NAME_USER_ID + "=" + idUser
									+ " and "
									+ selection,
							selectionArgs);
				}
				break;
			case DIARIES:
				rowsUpdated = sqlDB.update(TrackDiaryTable.TABLE_NAME_DIARY,
						values,
						selection,
						selectionArgs);
				break;
			case DIARY_ID:
				String idDiary = uri.getLastPathSegment();
				if (TextUtils.isEmpty(selection)) {
					rowsUpdated = sqlDB.update(TrackDiaryTable.TABLE_NAME_DIARY,
							values,
							TrackDiaryTable.COLUMN_NAME_DIARY_DATE + "=" + idDiary,
							null);
				} else {
					rowsUpdated = sqlDB.update(TrackDiaryTable.TABLE_NAME_DIARY,
							values,
							TrackDiaryTable.COLUMN_NAME_DIARY_DATE + "=" + idDiary
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

	private void checkColumns(String[] projection, String tableName) {
		if (DEBUG) Log.i(TAG, "+++ checkColumns() called! - "  + tableName + " +++");

		// Available columns of each table
		String[] availableFood = {
				FoodTable.COLUMN_NAME_FOOD_ID,
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
				FoodTable.COLUMN_NAME_FOOD_NAME,
				FoodTable.COLUMN_NAME_FOOD_CODE
		};

		String[] availableTrack = {
				TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID,
				TrackFoodTable.COLUMN_NAME_TRACKFOOD_QUANTITY,
				TrackFoodTable.COLUMN_NAME_TRACKFOOD_FOOD_ID,
				TrackFoodTable.COLUMN_NAME_TRACKFOOD_TIMEMOMENT_ID,
				TrackFoodTable.COLUMN_NAME_TRACKFOOD_USER_ID
		};

		String[] availableUser = {
				UserTable.COLUMN_NAME_USER_ID,
				UserTable.COLUMN_NAME_USER_LOGIN,
				UserTable.COLUMN_NAME_USER_PASSWORD,
				UserTable.COLUMN_NAME_USER_NAME,
				UserTable.COLUMN_NAME_USER_SURNAME,
				UserTable.COLUMN_NAME_USER_PICTURE_NAME,
				UserTable.COLUMN_NAME_USER_PICTURE,
				UserTable.COLUMN_NAME_USER_DNI,
				UserTable.COLUMN_NAME_USER_BIRTHDAY,
				UserTable.COLUMN_NAME_USER_BLOOD_GROUP,
				UserTable.COLUMN_NAME_USER_GENDER,
				UserTable.COLUMN_NAME_USER_PHONE,
				UserTable.COLUMN_NAME_USER_PROFESSION,
				UserTable.COLUMN_NAME_USER_STUDIES,
				UserTable.COLUMN_NAME_USER_CIVIL_STATUS,
				UserTable.COLUMN_NAME_USER_ADDRESS,
				UserTable.COLUMN_NAME_USER_POSTCODE,
				UserTable.COLUMN_NAME_USER_CITY,
				UserTable.COLUMN_NAME_USER_STATE,
				UserTable.COLUMN_NAME_USER_COUNTRY
		};

		String[] availableTrackFood = {
				TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID),
				TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_QUANTITY),
				TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_FOOD_ID),
				TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_TIMEMOMENT_ID),
				TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_USER_ID),
				FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_ID),
				FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_REGISTRYDATE),
				FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT),
				FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_QUANTITY),
				FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_ENERGY),
				FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_FATS),
				FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_PROTEINS),
				FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_CARBOHYDRATES),
				FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_CATEGORY),
				FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_COMMENTS),
				FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE),
				FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_COUNTER),
				FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_NAME),
				FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_CODE)
		};

		String[] availableTrackDiary = {
				TrackDiaryTable.COLUMN_NAME_DIARY_DATE,
				TrackDiaryTable.COLUMN_NAME_DIARY_TIMEFOOD,
				TrackDiaryTable.COLUMN_NAME_DIARY_CALORIES,
				TrackDiaryTable.COLUMN_NAME_DIARY_CARBOHYDRATES,
				TrackDiaryTable.COLUMN_NAME_DIARY_FATS,
				TrackDiaryTable.COLUMN_NAME_DIARY_PROTEINS,
				TrackDiaryTable.COLUMN_NAME_DIARY_USERID
		};

		// Use columns of the selected table
		String[] available;
		if (FoodTable.TABLE_NAME_FOOD.compareTo(tableName) == 0) {
			available = availableFood;
		} else if (TrackFoodTable.TABLE_NAME_TRACKFOOD.compareTo(tableName) == 0) {
			available = availableTrack;
		} else if (UserTable.TABLE_NAME_USER.compareTo(tableName) == 0) {
			available = availableUser;
		} else if (Integer.toString(TRACK_FOOD).compareTo(tableName) == 0) {
			available = availableTrackFood;
		} else if (TrackDiaryTable.TABLE_NAME_DIARY.compareTo(tableName) == 0) {
			available = availableTrackDiary;
		} else {
			throw new IllegalArgumentException("FoodContentprovider - checkColumns() - Unknown table");
		}


		if (projection != null) {
			HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
			HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));

			// check if all columns which are requested are available
			if (!availableColumns.containsAll(requestedColumns)) {
				throw new IllegalArgumentException("FoodContentprovider - checkColumns() - Unknown columns in projection");
			}
		}
	}
}
