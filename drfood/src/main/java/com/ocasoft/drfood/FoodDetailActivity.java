package com.ocasoft.drfood;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ocasoft.drfood.contentprovider.FoodContentProvider;
import com.ocasoft.drfood.database.FoodTable;
import com.ocasoft.drfood.database.TrackFoodTable;
import com.ocasoft.drfood.uiobjects.TrackFoodListAdapter;
import com.ocasoft.drfood.utils.DateUtils;

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
    private int foodQuantity = -1;
    private int foodEnergy = -1;
	private int selectedFoodTimeId = -1;
    private String foodTimeMoment = "";
    private String foodUnityMeasure = "";
    private String foodCategory = "";
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
					trackId = -1;
					foodName = null;
					foodId = -1;
					foodQuantity = -1;
					foodEnergy = -1;
					foodTimeMoment = "";
					selectedFoodTimeId = -1;
					foodUnityMeasure = "";
					foodCategory = "";
					selectedDay 	= -1;
					selectedYear 	= -1;
					selectedMonth 	= -1;
					loadingOk = false;
				} else {
					trackId = extras.getInt(TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID);
					editOperation = extras.getBoolean(EDIT_OPERATION);
					foodName = extras.getString(FoodTable.COLUMN_NAME_FOOD_NAME);
					foodId = extras.getInt(FoodTable.COLUMN_NAME_FOOD_ID);
					foodQuantity = extras.getInt(FoodTable.COLUMN_NAME_FOOD_QUANTITY);
					foodEnergy = extras.getInt(FoodTable.COLUMN_NAME_FOOD_ENERGY);
					selectedFoodTimeId = extras.getInt(FoodSelectorActivity.selFoodTimeExtraName);
					foodTimeMoment = extras.getString(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT);
					foodUnityMeasure = extras.getString(FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE);
					foodCategory = extras.getString(FoodTable.COLUMN_NAME_FOOD_CATEGORY);
					selectedDay 	= extras.getInt(FoodSelectorActivity.selDayExtraName);
					selectedYear 	= extras.getInt(FoodSelectorActivity.selYearExtraName);
					selectedMonth 	= extras.getInt(FoodSelectorActivity.selMonthExtraName);

					if (DEBUG) Log.i(TAG, "+++ onCreate() foodName=" + foodName + " foodId=" + foodId + " +++");
					loadingOk = true;
				}
			} else {
				trackId = (Integer) savedInstanceState.getSerializable(TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID);
				editOperation = (Boolean) savedInstanceState.getSerializable(EDIT_OPERATION);
				foodName = (String) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_NAME);
				foodId = (Integer) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_ID);
				foodQuantity = (Integer) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_QUANTITY);
				foodEnergy = (Integer) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_ENERGY);
				selectedFoodTimeId = (Integer) savedInstanceState.getSerializable(FoodSelectorActivity.selFoodTimeExtraName);
				foodTimeMoment = (String) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT);
				foodUnityMeasure = (String) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE);
				foodCategory =(String) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_CATEGORY);
				selectedDay 	= (Integer) savedInstanceState.getSerializable(FoodSelectorActivity.selDayExtraName);
				selectedYear 	= (Integer) savedInstanceState.getSerializable(FoodSelectorActivity.selYearExtraName);
				selectedMonth 	= (Integer) savedInstanceState.getSerializable(FoodSelectorActivity.selMonthExtraName);

				if (DEBUG) Log.i(TAG, "+++ onCreate() foodName=" + foodName + " foodId=" + foodId + " +++");
				loadingOk = true;
			}
		} catch (NumberFormatException e) {
			loadingOk = false;
			handleErrorFood(); //not valid foodId value
		}

		if (foodId == -1 || foodQuantity == -1 || foodEnergy == -1 || trackId == -1 || selectedDay == -1
				|| selectedYear == -1 || selectedMonth == -1 || selectedFoodTimeId == -1) {//not valid values
			loadingOk = false;
			handleErrorFood();
		}

		// If loadingOk then set the food data in the layout
		if (loadingOk) {
			// Set title (food name)
			TextView foodNameTV = (TextView) findViewById(R.id.foodNameDetailTV);

			if (DEBUG)
				foodNameTV.setText(foodName + " (id=" + foodId + ")");
			else
				foodNameTV.setText(foodName);

			// Set quantity
            EditText quantityET = (EditText) findViewById(R.id.quantityDetailET);

			//quantityTV.setText(foodQuantity); // +"" because we want a string
            quantityET.setText(foodQuantity + "");

			// Set unityMeasure
			TextView unityMeasureTV = (TextView) findViewById(R.id.unityMeasureDetailTV);
			unityMeasureTV.setText(foodUnityMeasure);

			// Set foodEnergy
			TextView energyTV = (TextView) findViewById(R.id.energyDetailTV);
			energyTV.setText(foodEnergy+"");

			// Set foodCategory
			TextView categoryTV = (TextView) findViewById(R.id.categoryDetailTV);
			categoryTV.setText(foodCategory);
		}

		if (DEBUG) Log.i(TAG, "+++ Extras: "
				+ "selectedFoodTimeId: " + selectedFoodTimeId
				+ "| selectedDay: " + selectedDay
				+ "| selectedYear: " + selectedYear
				+ "| selectedMonth: " + selectedMonth
				+ "| trackId: " + trackId
				+ " +++");

		// Save selected values (TrackFood)
		setDoneButtonListener();

	}

	/**
	 * Actions for Add Button
	 */
	private void setDoneButtonListener() {
		if (DEBUG) Log.i(TAG, "+++ setDoneButtonListener() +++");
		findViewById(R.id.buttonDoneFoodDetail).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Context context = v.getContext();

                // Defines a new Uri object that receives the result of the insertion
                Uri mNewUri;

                // Defines an object to contain the new values to insert
                ContentValues mNewValues = new ContentValues();

                // Read quantity (foodQuantity)
                EditText mEdit   = (EditText) findViewById(R.id.quantityDetailET);

				if (mEdit != null) {
					if (DEBUG) Log.i(TAG, "+++ setDoneButtonListener() mEdit.getText():" + mEdit.getText() + " +++");
				} else {
					if (DEBUG) Log.i(TAG, "+++ setDoneButtonListener() mEdit.getText(): null +++");
				}



				if (editOperation) {
					// ================== UPDATE AND EXISTING FOOD ==================
					mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_QUANTITY,
							mEdit.getText().toString());

					// Generate the URI (append the Id)
					Uri contentUriTrackId = Uri.withAppendedPath(
							FoodContentProvider.CONTENT_URI_TRACK,
							Integer.toString(trackId));

					// Use the contentProvider to update the record with the trackId
					context.getContentResolver().update(contentUriTrackId, mNewValues, null, null);


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
					// ====================== INSERT NEW FOOD =======================
					/*
					 * Sets the values of each column and inserts the word. The arguments to the "put"
					 * method are "column name" and "value"
					 */
					mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_QUANTITY,
							mEdit.getText().toString());
					mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_FOOD_ID,
							foodId);
					mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_TIMEMOMENT_ID,
							Integer.toString(selectedFoodTimeId));
					mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_USER_ID,
							"1");
					mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_DATE,
							DateUtils.formatDate(selectedYear, selectedMonth, selectedDay, DateUtils.DATE_FORMAT_DAYMONTHYEAR));

					mNewUri = context.getContentResolver().insert(
							FoodContentProvider.CONTENT_URI_TRACK,   // the user dictionary content URI
							mNewValues                          // the values to insert
					);

					CharSequence text = "Inserting " + foodName + " (Quantity: " + mEdit.getText().toString() + ")"
							+ ((DEBUG) ? ("\n==================DEBUG==================="
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
