package com.ocasoft.drfood;

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

/**
 * Created by Alex on 02/03/2015.
 */
public class FoodDetailActivity extends ActionBarActivity {
	private static final String TAG = "DRFOOD_FoodDetail";
	private static final boolean DEBUG = true; //TODO : Disable DEBUG

    private String foodName = "";
    private int foodId = -1;
    private int foodQuantity = -1;
    private int foodEnergy = -1;
    private String foodTimeMoment = "";
    private String foodUnityMeasure = "";
    private String foodCategory = "";


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
					foodName = null;
					foodId = -1;
					foodQuantity = -1;
					foodEnergy = -1;
					foodTimeMoment = "";
					foodUnityMeasure = "";
					foodCategory = "";

					loadingOk = false;
				} else {
					foodName = extras.getString(FoodTable.COLUMN_NAME_FOOD_NAME);
					foodId = extras.getInt(FoodTable.COLUMN_NAME_FOOD_ID);
					foodQuantity = extras.getInt(FoodTable.COLUMN_NAME_FOOD_QUANTITY);
					foodEnergy = extras.getInt(FoodTable.COLUMN_NAME_FOOD_ENERGY);
					foodTimeMoment = extras.getString(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT);
					foodUnityMeasure = extras.getString(FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE);
					foodCategory = extras.getString(FoodTable.COLUMN_NAME_FOOD_CATEGORY);


					if (DEBUG) Log.i(TAG, "+++ onCreate() foodName=" + foodName + " foodId=" + foodId + " +++");
					loadingOk = true;
				}
			} else {
				foodName = (String) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_NAME);
				foodId = (Integer) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_ID);
				foodQuantity = (Integer) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_QUANTITY);
				foodEnergy = (Integer) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_ENERGY);
				foodTimeMoment = (String) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT);
				foodUnityMeasure = (String) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE);
				foodCategory =(String) savedInstanceState.getSerializable(FoodTable.COLUMN_NAME_FOOD_CATEGORY);

				if (DEBUG) Log.i(TAG, "+++ onCreate() foodName=" + foodName + " foodId=" + foodId + " +++");
				loadingOk = true;
			}
		} catch (NumberFormatException e) {
			loadingOk = false;
			handleErrorFood(); //not valid foodId value
		}

		if (foodId == -1 || foodQuantity == -1 || foodEnergy == -1) {//not valid values
			loadingOk = false;
			handleErrorFood();
		}

		// If loadingOk then set the food data in the layout
		if (loadingOk) {
			if (DEBUG) Log.i(TAG, "+++ onCreate() loadingOk! +++");

			// Set title (food name)
			TextView foodNameTV = (TextView) findViewById(R.id.foodNameDetailTV);

			if (DEBUG)
				foodNameTV.setText(foodName + " (id=" + foodId + ")");
			else
				foodNameTV.setText(foodName);

			if (DEBUG) Log.i(TAG, "+++ onCreate() loadingOk-name! +++");

			// Set quantity
            EditText quantityET = (EditText) findViewById(R.id.quantityDetailET);

			//quantityTV.setText(foodQuantity); // +"" because we want a string
            quantityET.setText(foodQuantity + "");
			if (DEBUG) Log.i(TAG, "+++ onCreate() loadingOk-quantity! +++");

			// Set unityMeasure
			TextView unityMeasureTV = (TextView) findViewById(R.id.unityMeasureDetailTV);
			unityMeasureTV.setText(foodUnityMeasure);
			if (DEBUG) Log.i(TAG, "+++ onCreate() loadingOk-unityMeasure! +++");

			// Set foodEnergy
			TextView energyTV = (TextView) findViewById(R.id.energyDetailTV);
			energyTV.setText(foodEnergy+"");
			if (DEBUG) Log.i(TAG, "+++ onCreate() loadingOk-energy! +++");

			// Set foodCategory
			TextView categoryTV = (TextView) findViewById(R.id.categoryDetailTV);
			categoryTV.setText(foodCategory);
			if (DEBUG) Log.i(TAG, "+++ onCreate() loadingOk-category! +++");
		}

		// Save selected values (TrackFood)
		setDoneButtonListener();

	}

	/**
	 * Actions for Add Button
	 */
	private void setDoneButtonListener() {
		findViewById(R.id.buttonDoneFoodDetail).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Context context = v.getContext();
                CharSequence text = "Hello Done Button.";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);


                // Defines a new Uri object that receives the result of the insertion
                Uri mNewUri;

				if (DEBUG) Log.i(TAG, "+++ setDoneButtonListener() 1 +++");
                // Defines an object to contain the new values to insert
                ContentValues mNewValues = new ContentValues();
				if (DEBUG) Log.i(TAG, "+++ setDoneButtonListener() 2 +++");
                //TODO: Read quantity (foodQuantity)
                EditText mEdit   = (EditText) findViewById(R.id.quantityDetailET);
				if (DEBUG) Log.i(TAG, "+++ setDoneButtonListener() 3 +++");
				if (mEdit != null) {
					if (DEBUG) Log.i(TAG, "+++ setDoneButtonListener() mEdit.getText():" + mEdit.getText() + " +++");
				} else {
					if (DEBUG) Log.i(TAG, "+++ setDoneButtonListener() mEdit.getText(): null +++");
				}

                /*
                 * Sets the values of each column and inserts the word. The arguments to the "put"
                 * method are "column name" and "value"
                 */
                //mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID, "1");
                mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_QUANTITY, mEdit.getText().toString());
                mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_FOOD_ID, foodId);
                mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_TIMEMOMENT_ID, "4");
                mNewValues.put(TrackFoodTable.COLUMN_NAME_TRACKFOOD_USER_ID, "1");

				if (DEBUG) Log.i(TAG, "+++ setDoneButtonListener() newVAlues +++");

				mNewUri = context.getContentResolver().insert(
                        FoodContentProvider.CONTENT_URI_TRACK,   // the user dictionary content URI
                        mNewValues                          // the values to insert
                );

				if (DEBUG) Log.i(TAG, "+++ setDoneButtonListener() nNewUri +++");

				toast.show();

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
