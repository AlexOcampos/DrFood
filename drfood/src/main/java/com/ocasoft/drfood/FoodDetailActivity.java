package com.ocasoft.drfood;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
public class FoodDetailActivity extends ActionBarActivity {
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
    private String foodTimeMoment = "";
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
		foodTimeMoment = extras.getString(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT);
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
		foodTimeMoment = (String) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT);
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

                // Read quantity (foodQuantity)
                EditText mEdit   = (EditText) findViewById(R.id.quantityDetailET);

				if (mEdit != null) {
					if (DEBUG) Log.i(TAG, "+++ setDoneButtonListener() mEdit.getText():" + mEdit.getText() + " +++");
				} else {
					if (DEBUG) Log.i(TAG, "+++ setDoneButtonListener() mEdit.getText(): null +++");
				}

				if (editOperation) {
					// *************************************************************************************************
					// 										UPDATE FOOD OPERATION
					// *************************************************************************************************
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


					// ==================================== (2) UPDATE DIARY RECORD ====================================



					// TODO: DEBUG MESSAGES
					CharSequence text = "Updating " + foodName + " (Quantity: " + mEdit.getText().toString() + ")"
							+ ((DEBUG) ? ("\n==================DEBUG==================="
							+ "\nfoodTime:" + selectedFoodTimeId
							+ "\nnewUri: " + contentUriTrackId.toString()
							+ "\nDate: " + DateUtils.formatDate(selectedYear,selectedMonth,selectedDay,
							DateUtils.DATE_FORMAT_DAYMONTHYEAR))
							+ "\nTrackId: " + trackId
							: "");

					if (DEBUG) Log.i(TAG, "+++ setDoneButtonListener() done! " + text + " +++");

					int duration = Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(context, text, duration);

					toast.show();
				} else {
					// *************************************************************************************************
					// 										INSERT FOOD OPERATION
					// *************************************************************************************************
					// ==================================== (1) UPDATE FOOD COUNTER ====================================
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



					// ====================================== (2) INSERT NEW FOOD ======================================
					/*
					 * Sets the values of each column and inserts the word. The arguments to the "put"
					 * method are "column name" and "value"
					 */
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

					// ==================================== (3) INSERT DIARY RECORD ====================================






					// TODO: DEBUG MESSAGES
					CharSequence text = "Saving " + StringEscapeUtils.unescapeJava(foodName)
							+ ((DEBUG) ? ("\n==================DEBUG==================="
							+ "\nfoodCounter: " + foodCounter
							+ "\nQuantity: " + mEdit.getText().toString()
							+ "\nfoodTime:" + selectedFoodTimeId
							+ "\nnewUri: " + mNewUri.toString()
							+ "\nDate: " + DateUtils.formatDate(selectedYear,selectedMonth,selectedDay,
												DateUtils.DATE_FORMAT_DAYMONTHYEAR))
							: "");

					if (DEBUG) Log.i(TAG, "+++ setDoneButtonListener() done! " + text + " +++");

					int duration = Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(context, text, duration);

					toast.show();
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
}
