package com.ocasoft.drfood;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ocasoft.drfood.contentprovider.FoodContentProvider;
import com.ocasoft.drfood.database.FoodTable;
import com.ocasoft.drfood.database.TrackFoodTable;
import com.ocasoft.drfood.infoobjects.Food;
import com.ocasoft.drfood.infoobjects.FoodTimeList;
import com.ocasoft.drfood.uiobjects.TrackFoodListAdapter;
import com.ocasoft.drfood.utils.CursorObserver;
import com.ocasoft.drfood.utils.DateUtils;
import com.ocasoft.drfood.utils.SharedPreferencesUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class TrackFoodActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener,
		LoaderManager.LoaderCallbacks<Cursor>{
	private static final String TAG = "DRFOOD_TrFoodActi";
	private static final boolean DEBUG = true;
	private int mYear, mMonth, mDay;
	TextView tvDisplayDate;
	private DatePickerDialog dpd;
	private FoodTimeList foodTimes;
	private int selectedFoodTimeId = -1;
	private Context mContext;

	private TrackFoodListAdapter adapter;
	private static final String[] PROJECTION = new String[] {
			TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID),
			TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_QUANTITY),
			TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_FOOD_ID),
			TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_TIMEMOMENT_ID),
			TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_USER_ID),
			FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_NAME),
			FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT),
			FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_FATS),
			FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE)
	};
	// The Loader's id (this id is specific to the ListFragment's LoaderManager)
	private static final int LOADER_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trackfood);

		mContext = getBaseContext();

		if (DEBUG) {
			Log.i(TAG, "+++ Calling initLoader()! +++");
			if (getLoaderManager().getLoader(LOADER_ID) == null) {
				Log.i(TAG, "+++ Initializing the new Loader... +++");
			} else {
				Log.i(TAG, "+++ Reconnecting with existing Loader (id '1')... +++");
			}
		}

		//======================== Set Spinner values ========================
		setSpinnerFoodTimes();

		//========================      set date      ========================
		setDateTrackFood();

		// Prev Date Button
		setPrevButtonListener();

		// Next Date Button
		setNextButtonListener();

		//======================== Generate food list ========================
		generateFoodList();

		//====================== Set Add button Listener =====================
		setAddButtonListener();

		//======================== Set data FoodList =========================
		// Initialize a Loader with id '1'. If the Loader with this id already
		// exists, then the LoaderManager will reuse the existing Loader.
		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	/**
	 * Initialize spinner foodtimes and all the actions related with this
	 */
	private void setSpinnerFoodTimes() {
		Spinner spinner = (Spinner) findViewById(R.id.foodtime_spinner);

		// Spinner click listener
		spinner.setOnItemSelectedListener(this);

		// Get foodtime's values
		foodTimes = new FoodTimeList();

		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<String> foodTimesAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, foodTimes.toArrayList());

		// Specify the layout to use when the list of choices appears
		foodTimesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Apply the adapter to the spinner
		spinner.setAdapter(foodTimesAdapter);

		// Get current value
		String item = spinner.getSelectedItem().toString();

		// Save the current foodTimeId
		selectedFoodTimeId = foodTimes.getByName(item).getId();

		/*
		 * Save values to use it in the TrackFoodListAdapter, because I need this values.
		 * I don't know if there is a better way to do this.
		 */
		SharedPreferencesUtils.setSharedPrefValue(mContext, SharedPreferencesUtils.SP_CURRENTFOODTIME, selectedFoodTimeId);

		// Showing selected spinner item
		Log.i(TAG, "Default: " + item + " - " + selectedFoodTimeId);
	}

	/**
	 * Actions for Add Button
	 */
	private void setAddButtonListener() {
		findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Context context = v.getContext();

				// Start FoodSelectorActivity
				Intent intent = new Intent(context, FoodSelectorActivity.class);
				intent.putExtra(FoodSelectorActivity.selFoodTimeExtraName, selectedFoodTimeId);
				intent.putExtra(FoodSelectorActivity.selDayExtraName, mDay);
				intent.putExtra(FoodSelectorActivity.selMonthExtraName, mMonth);
				intent.putExtra(FoodSelectorActivity.selYearExtraName, mYear);
				context.startActivity(intent);

				CharSequence text = "Hello ADD Button.";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		});
	}

	/**
	 * Go to the previous day
	 */
	private void setPrevButtonListener() {
		findViewById(R.id.linLayoutDatePrev).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Context context = v.getContext();

				// Previous day
				mDay--;

				// Display Selected date in textbox
				tvDisplayDate.setText(DateUtils.formatDate(mYear, mMonth, mDay, DateUtils.DATE_FORMAT_DAYMONTHYEAR));

				/*
				 * Save values to use it in the TrackFoodListAdapter, because I need this values.
				 * I don't know if there is a better way to do this.
				 */
				SharedPreferencesUtils.setSharedPrefValue(mContext, SharedPreferencesUtils.SP_CURRENTDAY, mDay);

				// Update foodlist (Restart loader)
				getLoaderManager().restartLoader(LOADER_ID, null, TrackFoodActivity.this);
			}
		});
	}

	/**
	 * Go to the next day
	 */
	private void setNextButtonListener() {
		findViewById(R.id.linLayoutDateNext).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Previous day
				mDay++;

				// Display Selected date in textbox
				tvDisplayDate.setText(DateUtils.formatDate(mYear, mMonth, mDay, DateUtils.DATE_FORMAT_DAYMONTHYEAR));

				/*
				 * Save values to use it in the TrackFoodListAdapter, because I need this values.
				 * I don't know if there is a better way to do this.
				 */
				SharedPreferencesUtils.setSharedPrefValue(mContext, SharedPreferencesUtils.SP_CURRENTDAY, mDay);

				// Update foodlist (Restart loader)
				getLoaderManager().restartLoader(LOADER_ID, null, TrackFoodActivity.this);
			}
		});
	}

	private void generateFoodList() {
		// ======================== Load tracked food ========================
        /*
            1) Buscar tracked food del contentprovider por los siguientes criterios:
                - usuario
                - momento horario
                - fecha

            2) Inicializar la lista de alimentos "list" con dichos valores
         */

		//instantiate custom adapter
		adapter = new TrackFoodListAdapter(this);

		//handle listview and assign adapter
		GridView lView = (GridView) findViewById(R.id.listFoodConsummed);
		lView.setAdapter(adapter);
	}

	/**
	 * Set the date to the DateTextView and create DatePickerDialog.
	 */
	private void setDateTrackFood() {
		tvDisplayDate = (TextView) findViewById(R.id.textViewDate);
		// Set current date in the TextView
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		tvDisplayDate.setText(DateUtils.formatDate(mYear, mMonth, mDay, DateUtils.DATE_FORMAT_DAYMONTHYEAR));

		/*
		 * Save values to use it in the TrackFoodListAdapter, because I need this values.
		 * I don't know if there is a better way to do this.
		 */
		SharedPreferencesUtils.setSharedPrefValue(mContext, SharedPreferencesUtils.SP_CURRENTMONTH, mMonth);
		SharedPreferencesUtils.setSharedPrefValue(mContext, SharedPreferencesUtils.SP_CURRENTYEAR, mYear);
		SharedPreferencesUtils.setSharedPrefValue(mContext, SharedPreferencesUtils.SP_CURRENTDAY, mDay);

		// Open DatePickerDialog and set user selected date
		dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// Display Selected date in textbox
				tvDisplayDate.setText(DateUtils.formatDate(year,monthOfYear,dayOfMonth,DateUtils.DATE_FORMAT_DAYMONTHYEAR));
				mYear = year;
				mMonth = monthOfYear;
				mDay = dayOfMonth;

				/*
				 * Save values to use it in the TrackFoodListAdapter, because I need this values.
				 * I don't know if there is a better way to do this.
				 */
				SharedPreferencesUtils.setSharedPrefValue(mContext, SharedPreferencesUtils.SP_CURRENTMONTH, mMonth);
				SharedPreferencesUtils.setSharedPrefValue(mContext, SharedPreferencesUtils.SP_CURRENTYEAR, mYear);
				SharedPreferencesUtils.setSharedPrefValue(mContext, SharedPreferencesUtils.SP_CURRENTDAY, mDay);

				// Update foodlist (Restart loader)
				getLoaderManager().restartLoader(LOADER_ID, null, TrackFoodActivity.this);
			}
		}, mYear, mMonth, mDay);

		// Set OnDateSetListener to TextView
		tvDisplayDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Launch Date Picker Dialog
				dpd.show();
			}
		});
	}

	/**
	 * Set a text for a TextView
	 * @param text The text
	 * @param textViewId id of the TextView
	 * @param parentView parent of the TextView
	 */
	private void setItemText(String text, int textViewId, View parentView) {
		TextView childNameTV = (TextView) parentView.findViewById(textViewId);
		if (childNameTV != null) {
			childNameTV.setText(text);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food_selector, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		// On selecting a spinner item
		String item = adapterView.getItemAtPosition(i).toString();

		// Save the current foodTimeId
		selectedFoodTimeId = foodTimes.getByName(item).getId();

		/*
		 * Save values to use it in the TrackFoodListAdapter, because I need this values.
		 * I don't know if there is a better way to do this.
		 */
		SharedPreferencesUtils.setSharedPrefValue(mContext, SharedPreferencesUtils.SP_CURRENTFOODTIME, selectedFoodTimeId);

		Log.i(TAG, "Selected: " + item + " - " + selectedFoodTimeId);

		// Restart loader
		getLoaderManager().restartLoader(LOADER_ID, null, this);
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
		Log.i(TAG, "Nothing selected. Current: " + selectedFoodTimeId);
	}

	/**********************/
	/** LOADER CALLBACKS **/
	/**********************/
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if (DEBUG) Log.i(TAG, "+++ onCreateLoader() called! +++");

		// Prepare where clause
		String mSelectionClause = TrackFoodTable.COLUMN_NAME_TRACKFOOD_USER_ID + " = ?"
				+ " AND " + TrackFoodTable.COLUMN_NAME_TRACKFOOD_TIMEMOMENT_ID + " = ?"
				+ " AND " + TrackFoodTable.COLUMN_NAME_TRACKFOOD_DATE + " = ?";
		String[] mSelectionArgs = {
				"1", 	// UserId
				Integer.toString(selectedFoodTimeId),	// TimemomentId
				DateUtils.formatDate(mYear, mMonth, mDay, DateUtils.DATE_FORMAT_DAYMONTHYEAR) // Date
		};

		// Create a new CursorLoader with the following query parameters.
		CursorLoader cursor = new CursorLoader(TrackFoodActivity.this, FoodContentProvider.CONTENT_URI_TRACKEDFOOD,
				PROJECTION, mSelectionClause, mSelectionArgs, null);

		return cursor;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");

		adapter.setData(data);

		/**
		 * Registering content observer for this cursor, When this cursor value will be change
		 * This will notify our loader to reload its data*/
		CursorObserver cursorObserver = new CursorObserver(new Handler(), loader);
		data.registerContentObserver(cursorObserver);
		data.setNotificationUri(getContentResolver(),FoodContentProvider.CONTENT_URI_TRACK);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if (DEBUG) Log.i(TAG, "+++ onLoadReset() called! +++");

		adapter.clearData();
	}
}
