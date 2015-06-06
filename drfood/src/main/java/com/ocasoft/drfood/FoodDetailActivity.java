package com.ocasoft.drfood;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.ocasoft.drfood.database.FoodTable;

/**
 * Created by Alex on 02/03/2015.
 */
public class FoodDetailActivity extends ActionBarActivity {
	private static final String TAG = "DRFOOD_FoodDetail";
	private static final boolean DEBUG = true; //TODO : Disable DEBUG

	private static final String[] PROJECTION = new String[] { "id", "name" };


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (DEBUG) Log.i(TAG, "+++ onCreate() called! +++");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_detail);

		String foodName = "";
		int foodId = -1;
		int foodQuantity = -1;
		int foodEnergy = -1;
		String foodTimeMoment = "";
		String foodUnityMeasure = "";
		String foodCategory = "";

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
			TextView quantityTV = (TextView) findViewById(R.id.quantityDetailTV);

			quantityTV.setText(foodQuantity+""); // +"" because we want a string
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

	}

	private void handleErrorFood() {
		if (DEBUG) Log.i(TAG, "+++ handleErrorFood() called! +++");
		//TODO: the food is incorrect. Do something
	}
}
