package com.ocasoft.drfood;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ocasoft.drfood.contentprovider.FoodContentProvider;
import com.ocasoft.drfood.database.FoodTable;
import com.ocasoft.drfood.database.TrackDiaryTable;
import com.ocasoft.drfood.database.TrackFoodTable;
import com.ocasoft.drfood.uiobjects.TrackFoodListAdapter;
import com.ocasoft.drfood.utils.DateUtils;
import com.ocasoft.drfood.utils.MathUtils;
import com.ocasoft.drfood.utils.SharedPreferencesUtils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Text;

/**
 * Created by Alex on 02/03/2015.
 */
public class FoodDetailActivity extends AppCompatActivity {
	private static final String TAG = "DRFOOD_FoodDetail";
	private static final boolean DEBUG = true; //TODO : Disable DEBUG

	public static final String EDIT_OPERATION = "edit";

	private boolean editOperation = false;
	private int trackId = -1;
    private String foodName = "";
    private int foodId = -1;
    private int foodQuantityDefault = -1;
    private int foodQuantitySelected = -1;
    private int foodEnergy = -1;
	private int selectedFoodTimeId = -1;
    private int foodTimeMoment = -1;
    private String foodUnityMeasure = "";
    private String foodCategory = "";
	private String foodComments = "";
	private double foodFats = -1;
	private double foodProteins = -1;
	private double foodCarbohydrates = -1;
	private String foodCode = "";
	private int foodCounter = -1;
	private int selectedDay = -1;
	private int selectedYear = -1;
	private int selectedMonth = -1;

	private static final String[] PROJECTION = new String[] { "id", "name" };


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (DEBUG) Log.i(TAG, "+++ onCreate() called! +++");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_detail);

		boolean loadingOk = false;

		//Get values for FoodId and FoodName
		try {
			if (savedInstanceState == null) {
				Bundle extras = getIntent().getExtras();
				if(extras == null) {
					loadingOk = false;
				} else {
					getExtras(extras);
					loadingOk = true;
				}
			} else {
				getSavedState(savedInstanceState);
				loadingOk = true;
			}
		} catch (NumberFormatException e) {
			loadingOk = false;
			handleErrorFood(); //not valid foodId value
		}

		if (foodId == -1 || foodQuantityDefault == -1 || foodEnergy == -1 || trackId == -1 || selectedDay == -1
				|| selectedYear == -1 || selectedMonth == -1 || selectedFoodTimeId == -1) {//not valid values
			loadingOk = false;
			handleErrorFood();
		}

		// If loadingOk then set the food data in the layout
		if (loadingOk) {
			loadFoodInfo();
		}

		if (DEBUG) Log.i(TAG, "+++ Extras: "
				+ "selectedFoodTimeId: " + selectedFoodTimeId
				+ "| selectedDay: " + selectedDay
				+ "| selectedYear: " + selectedYear
				+ "| selectedMonth: " + selectedMonth
				+ "| trackId: " + trackId
				+ "| foodCounter: " + foodCounter
				+ " +++");

		// Save selected values (TrackFood)
		setDoneButtonListener();
	}

	private void getExtras(Bundle extras) {
		trackId = extras.getInt(TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID);
		editOperation = extras.getBoolean(EDIT_OPERATION);
		foodName = extras.getString(FoodTable.COLUMN_NAME_FOOD_NAME);
		foodId = extras.getInt(FoodTable.COLUMN_NAME_FOOD_ID);
		foodQuantityDefault = extras.getInt(FoodTable.COLUMN_NAME_FOOD_QUANTITY);
		foodQuantitySelected = extras.getInt(TrackFoodTable.COLUMN_NAME_TRACKFOOD_QUANTITY);
		foodEnergy = extras.getInt(FoodTable.COLUMN_NAME_FOOD_ENERGY);
		foodFats = extras.getDouble(FoodTable.COLUMN_NAME_FOOD_FATS);
		foodProteins = extras.getDouble(FoodTable.COLUMN_NAME_FOOD_PROTEINS);
		foodCarbohydrates = extras.getDouble(FoodTable.COLUMN_NAME_FOOD_CARBOHYDRATES);
		foodComments = extras.getString(FoodTable.COLUMN_NAME_FOOD_COMMENTS);
		foodTimeMoment = extras.getInt(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT);
		foodUnityMeasure = extras.getString(FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE);
		foodCategory = extras.getString(FoodTable.COLUMN_NAME_FOOD_CATEGORY);
		foodCounter = extras.getInt(FoodTable.COLUMN_NAME_FOOD_COUNTER);
		foodCode = extras.getString(FoodTable.COLUMN_NAME_FOOD_CODE);
		selectedFoodTimeId = extras.getInt(FoodSelectorActivity.selFoodTimeExtraName);
		selectedDay 	= extras.getInt(FoodSelectorActivity.selDayExtraName);
		selectedYear 	= extras.getInt(FoodSelectorActivity.selYearExtraName);
		selectedMonth 	= extras.getInt(FoodSelectorActivity.selMonthExtraName);
	}

	private void getSavedState(Bundle savedInstanceState) {
		trackId = (Integer) savedInstanceState.getSerializable(TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID);
		editOperation = (Boolean) savedInstanceState.getSerializable(EDIT_OPERATION);
		foodName = (String) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_NAME);
		foodId = (Integer) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_ID);
		foodQuantityDefault = (Integer) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_QUANTITY);
		foodQuantitySelected = (Integer) savedInstanceState.getSerializable(TrackFoodTable.COLUMN_NAME_TRACKFOOD_QUANTITY);
		foodEnergy = (Integer) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_ENERGY);
		selectedFoodTimeId = (Integer) savedInstanceState.getSerializable(FoodSelectorActivity.selFoodTimeExtraName);
		foodTimeMoment = (Integer) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT);
		foodUnityMeasure = (String) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE);
		foodCategory = (String) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_CATEGORY);
		foodCounter = (Integer) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_COUNTER);
		foodFats = (Double) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_FATS);
		foodProteins = (Double) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_PROTEINS);
		foodCarbohydrates = (Double) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_CARBOHYDRATES);
		foodComments = (String) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_COMMENTS);
		foodCode = (String) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_CODE);
		selectedDay 	= (Integer) savedInstanceState.getSerializable(FoodSelectorActivity.selDayExtraName);
		selectedYear 	= (Integer) savedInstanceState.getSerializable(FoodSelectorActivity.selYearExtraName);
		selectedMonth 	= (Integer) savedInstanceState.getSerializable(FoodSelectorActivity.selMonthExtraName);
	}

	private void loadFoodInfo() {
		// Set title (food name) ===================================================================================
		TextView foodNameTV = (TextView) findViewById(R.id.nameFoodTextView);

		foodNameTV.setText(StringEscapeUtils.unescapeJava(foodName));

		// Set quantity ============================================================================================
		EditText quantityET = (EditText) findViewById(R.id.quantityDetailET);
		//quantityTV.setText(foodQuantity); // +"" because we want a string
		if (editOperation) {
			quantityET.setText(foodQuantitySelected + "");
		} else {
			quantityET.setText(foodQuantityDefault + "");
			foodQuantitySelected = foodQuantityDefault;
		}

		// Set unityMeasure ========================================================================================
		TextView unityMeasureTV = (TextView) findViewById(R.id.unityMeasureDetailTV);
		unityMeasureTV.setText(StringEscapeUtils.unescapeJava(foodUnityMeasure));

		// Set foodEnergy ==========================================================================================
		final TextView energyTV = (TextView) findViewById(R.id.caloriesValue);
		energyTV.setText((foodEnergy*foodQuantitySelected/foodQuantityDefault)+"");

		// Set Carbs ===============================================================================================
		final TextView carbsTV = (TextView) findViewById(R.id.carbsValue);
		double carbsTVValue = MathUtils.round(
				(foodCarbohydrates*foodQuantitySelected/foodQuantityDefault), 2);
		carbsTV.setText(carbsTVValue + " " + getString(R.string.carbohydrates_text));

		// Set Proteins ============================================================================================
		final TextView proteinsTV = (TextView) findViewById(R.id.proteinValue);
		double proteinsTVValue = MathUtils.round(
				(foodProteins*foodQuantitySelected/foodQuantityDefault), 2);
		proteinsTV.setText(proteinsTVValue + " " + getString(R.string.proteins_text));

		// Set Fats ================================================================================================
		final TextView fatsTV = (TextView) findViewById(R.id.fatValue);
		double fatsTVValue = MathUtils.round(
				(foodFats*foodQuantitySelected/foodQuantityDefault), 2);
		fatsTV.setText(fatsTVValue + " " + getString(R.string.fats_text));


		// Set on text change listener to update energy, fats, proteins and carbohydrates
		quantityET.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				if (s.toString().length() > 0) {
					foodQuantitySelected = Integer.parseInt(s.toString());

					double carbsTVValue = MathUtils.round(
							(foodCarbohydrates*foodQuantitySelected/foodQuantityDefault), 2);
					double proteinsTVValue = MathUtils.round(
							(foodProteins*foodQuantitySelected/foodQuantityDefault), 2);
					double fatsTVValue = MathUtils.round(
							(foodFats*foodQuantitySelected/foodQuantityDefault), 2);

					energyTV.setText((foodEnergy*foodQuantitySelected/foodQuantityDefault) + "");
					carbsTV.setText(carbsTVValue + " " + getString(R.string.carbohydrates_text));
					proteinsTV.setText(proteinsTVValue + " " + getString(R.string.proteins_text));
					fatsTV.setText(fatsTVValue + " " + getString(R.string.fats_text));
				}
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});

		// Set Image ===============================================================================================
		String nameOfDrawable = "food_" + foodCode;
		int drawableResourceId = getResources().getIdentifier(nameOfDrawable, "drawable", getPackageName());
		ImageView foodImageView = (ImageView) findViewById(R.id.circularFoodImageView);
		if (drawableResourceId != 0) {
			foodImageView.setImageResource(drawableResourceId);
		}

		// Set Comments ============================================================================================
		TextView summaryFoodTV = (TextView) findViewById(R.id.summaryFoodTextView);
		summaryFoodTV.setText(StringEscapeUtils.unescapeJava(foodComments));


		// Set foodCategory
//		TextView categoryTV = (TextView) findViewById(R.id.categoryDetailTV);
//		categoryTV.setText(foodCategory);
	}

	/**
	 * Actions for Add Button
	 */
	private void setDoneButtonListener() {
		if (DEBUG) Log.i(TAG, "+++ setDoneButtonListener() +++");



		findViewById(R.id.buttonDoneFoodDetail).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Context context = v.getContext();

				SharedPreferencesUtils.setSharedPrefValue(context, SharedPreferencesUtils.SP_DBNOTEMPTY,true);

				// Read quantity (foodQuantity)
                EditText mEdit   = (EditText) findViewById(R.id.quantityDetailET);

				if (editOperation) {
					// *************************************************************************************************
					// 										UPDATE FOOD OPERATION
					// *************************************************************************************************
					if (DEBUG) Log.i(TAG, "+++ [updatefood] (UPDATE) There a record! +++");
					// (1) UPDATE AND EXISTING FOOD =================================
					updateFoodRecord(context, mEdit);

					// (2) UPDATE DIARY RECORD ====================================
					DiaryRecord data = getCurrentDiaryRecord(context);
					// Calculate new values (saved - original + current)
					double energyNew = data.getSavedCalories() - (foodEnergy*foodQuantitySelected / foodQuantityDefault)
							+ (foodEnergy*Double.parseDouble(mEdit.getText().toString()) / foodQuantityDefault);
					double proteinsNew = data.getSavedCalories() - (foodProteins*foodQuantitySelected / foodQuantityDefault)
							+ (foodProteins*Double.parseDouble(mEdit.getText().toString()) / foodQuantityDefault);;
					double fatsNew = data.getSavedCalories() - (foodFats*foodQuantitySelected / foodQuantityDefault)
							+ (foodFats*Double.parseDouble(mEdit.getText().toString()) / foodQuantityDefault);;
					double carbohydratesNew = data.getSavedCalories() - (foodCarbohydrates*foodQuantitySelected / foodQuantityDefault)
							+ (foodCarbohydrates*Double.parseDouble(mEdit.getText().toString()) / foodQuantityDefault);
					data.setSavedCalories(energyNew);
					data.setSavedProteins(proteinsNew);
					data.setSavedFats(fatsNew);
					data.setSavedCarbohydrates(carbohydratesNew);

					updateDiaryRecord(data, context);

				} else {
					// *************************************************************************************************
					// 										INSERT FOOD OPERATION
					// *************************************************************************************************
					// (1) UPDATE FOOD COUNTER ====================================
					updateFoodCounter(context);

					// (2) INSERT NEW FOOD ======================================
					insertNewFood(context,mEdit);

					// (3) INSERT DIARY RECORD ====================================
					DiaryRecord data = getCurrentDiaryRecord(context);

					if (data == null) {
						/*
							If we don't have values in the database for this date, we have to insert a new record in
							the diary.
						 */
						if (DEBUG) Log.i(TAG, "+++ [insertfood] (INSERT) There aren't diary for this day! +++");
						insertDiaryRecord(context);
					} else {
						/*
							If we have values in the database for this date+user+foodTime, we have to update the record
							to add the calories of this food to the diary.
						 */
						if (DEBUG) Log.i(TAG, "+++ [insertfood] (UPDATE) There a record! +++");
						updateDiaryRecord(data,context);
					}
				}

				// Close FoodDetailActivity
				finish();
			}
		});
	}

	private void handleErrorFood() {
		if (DEBUG) Log.i(TAG, "+++ handleErrorFood() called! +++");
		//TODO: the food is incorrect. Do something
	}

	// =================================================================================================================
	// 												FOOD METHODS
	// =================================================================================================================

	/**
	 * Increment (+1) the food using counter
	 * @param context the context
	 */
	private void updateFoodCounter(Context context) {
		ContentValues mUpdateValues = new ContentValues();
		foodCounter++;
		mUpdateValues.put(FoodTable.COLUMN_NAME_FOOD_COUNTER,
				Integer.toString(foodCounter));

		// Generate the URI (append the Id)
		Uri contentUriFoodId = Uri.withAppendedPath(
				FoodContentProvider.CONTENT_URI_FOOD,
				Integer.toString(foodId));

		// Use the contentProvider to update the record with the trackId
		context.getContentResolver().update(contentUriFoodId, mUpdateValues, null, null);
	}


	// =================================================================================================================
	// 												TRACK FOOD METHODS
	// =================================================================================================================

	/**
	 * Insert a new food in TrackFood table
	 * @param context the context
	 * @param mEdit the editText with the quantity
	 */
	private void insertNewFood(Context context, EditText mEdit) {
		// Defines a new Uri object that receives the result of the insertion
		Uri mNewUri;

		// Defines an object to contain the new values to insert
		ContentValues mNewValues = new ContentValues();

		mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_QUANTITY,
				mEdit.getText().toString());
		mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_FOOD_ID,
				foodId);
		mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_TIMEMOMENT_ID,
				Integer.toString(selectedFoodTimeId));
		mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_USER_ID,
				"1");
		mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_DATE,
				DateUtils.formatDate(selectedYear, selectedMonth, selectedDay,
						DateUtils.DATE_FORMAT_DAYMONTHYEAR));

		mNewUri = context.getContentResolver().insert(
				FoodContentProvider.CONTENT_URI_TRACK,   // the user dictionary content URI
				mNewValues                          // the values to insert
		);
	}

	/**
	 * Update the selected quantity of the product
	 * @param context the context
	 * @param mEdit the quantity EditText
	 */
	private void updateFoodRecord(Context context, EditText mEdit) {
		// Defines a new Uri object that receives the result of the insertion
		Uri mNewUri;

		// Defines an object to contain the new values to insert
		ContentValues mNewValues = new ContentValues();

		// ================================== (1) UPDATE AND EXISTING FOOD =================================
		mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_QUANTITY,
				mEdit.getText().toString());

		// Generate the URI (append the Id)
		Uri contentUriTrackId = Uri.withAppendedPath(
				FoodContentProvider.CONTENT_URI_TRACK,
				Integer.toString(trackId));

		// Use the contentProvider to update the record with the trackId
		context.getContentResolver().update(contentUriTrackId, mNewValues, null, null);
	}

	// =================================================================================================================
	// 												DIARY RECORD METHODS
	// =================================================================================================================

	/**
	 * Get current diary record
	 * @param context
	 * @return DiaryRecord with the values. Null if don't exist
	 */
	private DiaryRecord getCurrentDiaryRecord(Context context) {
		String[] selectionTrackDiary = {
				TrackDiaryTable.COLUMN_NAME_DIARY_DATE,
				TrackDiaryTable.COLUMN_NAME_DIARY_TIMEFOOD,
				TrackDiaryTable.COLUMN_NAME_DIARY_CALORIES,
				TrackDiaryTable.COLUMN_NAME_DIARY_CARBOHYDRATES,
				TrackDiaryTable.COLUMN_NAME_DIARY_FATS,
				TrackDiaryTable.COLUMN_NAME_DIARY_PROTEINS,
				TrackDiaryTable.COLUMN_NAME_DIARY_USERID
		};
		String mSelectionClause = TrackDiaryTable.COLUMN_NAME_DIARY_DATE + " = ? AND "
				+ TrackDiaryTable.COLUMN_NAME_DIARY_TIMEFOOD + " = ? AND "
				+ TrackDiaryTable.COLUMN_NAME_DIARY_USERID + " = ?";
		String[] mSelectionArgs = {
				DateUtils.formatDate(selectedYear,selectedMonth,selectedDay,
						DateUtils.DATE_FORMAT_DAYMONTHYEAR), // Date
				Integer.toString(selectedFoodTimeId),	// TimemomentId
				"1" // UserId
		};
		if (DEBUG) Log.i(TAG, "+++ (TrackDiaryTable) "
				+ " ## " + TrackDiaryTable.COLUMN_NAME_DIARY_DATE + " " + DateUtils.formatDate(selectedYear,selectedMonth,selectedDay,DateUtils.DATE_FORMAT_DAYMONTHYEAR)
				+ " ## " + TrackDiaryTable.COLUMN_NAME_DIARY_TIMEFOOD + " " + Integer.toString(selectedFoodTimeId)
				+ " ## " + TrackDiaryTable.COLUMN_NAME_DIARY_USERID + " " + "1"
				+ " +++");

		Cursor data = context.getContentResolver().query(FoodContentProvider.CONTENT_URI_DIARY,
				selectionTrackDiary, mSelectionClause, mSelectionArgs, null);
		double savedCarbohydrates = -1;
		double savedFats = -1;
		double savedCalories = -1;
		double savedProteins = -1;

		if (data.getCount() > 0) {
			if (!data.isFirst())
				data.moveToNext();

			do {
				savedCarbohydrates = data.getDouble(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_CARBOHYDRATES));
				savedFats = data.getDouble(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_FATS));
				savedCalories = data.getDouble(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_CALORIES));
				savedProteins = data.getDouble(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_PROTEINS));
			} while (data.moveToNext());
		} else {
			return null;
		}

		return new DiaryRecord(savedCarbohydrates,savedFats,savedCalories,savedProteins);
	}

	/**
	 * Update a record with this food + the savedData in DiaryRecord
	 * @param data the saved data
	 * @param context the context
	 */
	private void updateDiaryRecord(DiaryRecord data, Context context) {
		// Key (date+foodTime+userId)
		ContentValues mNewValuesDiary = new ContentValues();
		mNewValuesDiary.put(TrackDiaryTable.COLUMN_NAME_DIARY_DATE,
				DateUtils.formatDate(selectedYear, selectedMonth, selectedDay,
						DateUtils.DATE_FORMAT_DAYMONTHYEAR));
		mNewValuesDiary.put(TrackDiaryTable.COLUMN_NAME_DIARY_TIMEFOOD,
				Integer.toString(selectedFoodTimeId));
		mNewValuesDiary.put(TrackDiaryTable.COLUMN_NAME_DIARY_USERID,
				"1");

		// Values (newValue + savedValue)
		mNewValuesDiary.put(TrackDiaryTable.COLUMN_NAME_DIARY_CALORIES,
				(foodEnergy*foodQuantitySelected / foodQuantityDefault)+data.getSavedCalories());
		mNewValuesDiary.put(TrackDiaryTable.COLUMN_NAME_DIARY_CARBOHYDRATES,
				(foodCarbohydrates*foodQuantitySelected / foodQuantityDefault)+data.getSavedCarbohydrates());
		mNewValuesDiary.put(TrackDiaryTable.COLUMN_NAME_DIARY_FATS,
				(foodFats*foodQuantitySelected / foodQuantityDefault)+data.getSavedFats());
		mNewValuesDiary.put(TrackDiaryTable.COLUMN_NAME_DIARY_PROTEINS,
				(foodProteins*foodQuantitySelected / foodQuantityDefault)+data.getSavedProteins());

		// Selection
		String mSelectionClause = TrackDiaryTable.COLUMN_NAME_DIARY_DATE + " = ? AND "
				+ TrackDiaryTable.COLUMN_NAME_DIARY_TIMEFOOD + " = ? AND "
				+ TrackDiaryTable.COLUMN_NAME_DIARY_USERID + " = ?";
		String[] mSelectionArgs = {
				DateUtils.formatDate(selectedYear,selectedMonth,selectedDay,
						DateUtils.DATE_FORMAT_DAYMONTHYEAR), // Date
				Integer.toString(selectedFoodTimeId),	// TimemomentId
				"1" // UserId
		};

		// Update diary
		context.getContentResolver().update(
				FoodContentProvider.CONTENT_URI_DIARY,
				mNewValuesDiary,        // Values to update
				mSelectionClause,		// Same than the select query
				mSelectionArgs			// Same than the select query
		);
	}

	/**
	 * Insert a new record with the selected food
	 * @param context the context
	 */
	private void insertDiaryRecord(Context context) {
		// Defines an object to contain the new values to insert
		ContentValues mNewValuesDiary = new ContentValues();
		mNewValuesDiary.put(TrackDiaryTable.COLUMN_NAME_DIARY_DATE,
				DateUtils.formatDate(selectedYear, selectedMonth, selectedDay, DateUtils.DATE_FORMAT_DAYMONTHYEAR));
		mNewValuesDiary.put(TrackDiaryTable.COLUMN_NAME_DIARY_TIMEFOOD,
				Integer.toString(selectedFoodTimeId));
		mNewValuesDiary.put(TrackDiaryTable.COLUMN_NAME_DIARY_USERID,
				"1");
		mNewValuesDiary.put(TrackDiaryTable.COLUMN_NAME_DIARY_CALORIES,
				(foodEnergy*foodQuantitySelected / foodQuantityDefault));
		mNewValuesDiary.put(TrackDiaryTable.COLUMN_NAME_DIARY_CARBOHYDRATES,
				(foodCarbohydrates*foodQuantitySelected / foodQuantityDefault));
		mNewValuesDiary.put(TrackDiaryTable.COLUMN_NAME_DIARY_FATS,
				(foodFats*foodQuantitySelected / foodQuantityDefault));
		mNewValuesDiary.put(TrackDiaryTable.COLUMN_NAME_DIARY_PROTEINS,
				(foodProteins*foodQuantitySelected / foodQuantityDefault));
		context.getContentResolver().insert(
				FoodContentProvider.CONTENT_URI_DIARY,
				mNewValuesDiary                          // the values to insert
		);
	}


	// =================================================================================================================
	// 												AUX CLASSES
	// =================================================================================================================
	/**
	 * Save the data of a diary food record
	 */
	private class DiaryRecord {
		double savedCarbohydrates = -1;
		double savedFats = -1;
		double savedCalories = -1;
		double savedProteins = -1;

		public DiaryRecord(double savedCarbohydrates, double savedFats, double savedCalories, double savedProteins) {
			this.savedCarbohydrates = savedCarbohydrates;
			this.savedFats = savedFats;
			this.savedCalories = savedCalories;
			this.savedProteins = savedProteins;
		}

		public double getSavedCarbohydrates() {
			return savedCarbohydrates;
		}

		public void setSavedCarbohydrates(double savedCarbohydrates) {
			this.savedCarbohydrates = savedCarbohydrates;
		}

		public double getSavedFats() {
			return savedFats;
		}

		public void setSavedFats(double savedFats) {
			this.savedFats = savedFats;
		}

		public double getSavedCalories() {
			return savedCalories;
		}

		public void setSavedCalories(double savedCalories) {
			this.savedCalories = savedCalories;
		}

		public double getSavedProteins() {
			return savedProteins;
		}

		public void setSavedProteins(double savedProteins) {
			this.savedProteins = savedProteins;
		}
	}
}
