package com.ocasoft.drfood;

import android.app.DatePickerDialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
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
import com.ocasoft.drfood.utils.DateUtils;

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

	private TrackFoodListAdapter adapter;
	private static final String[] PROJECTION = new String[] {
			TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID),
			TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_QUANTITY),
			TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_FOOD_ID),
			TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_TIMEMOMENT_ID),
			TrackFoodTable.addPrefix(TrackFoodTable.COLUMN_NAME_TRACKFOOD_USER_ID),
			FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_NAME),
			FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT),
			FoodTable.addPrefix(FoodTable.COLUMN_NAME_FOOD_FATS)
	};
	// The Loader's id (this id is specific to the ListFragment's LoaderManager)
	private static final int LOADER_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trackfood);

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

		//======================== generate food list ========================
		generateFoodList();

		//======================= Set button Listeners =======================
		// Add Button
		setAddButtonListener();

		// Done Button
		setDoneButtonListener();

		// Favourite Button
		setFavouriteListener();

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

		// Showing selected spinner item
		Log.i(TAG, "Default: " + item + " - " + selectedFoodTimeId);

		// Restart loader (se inicializa en el OnCreate()
		//getLoaderManager().restartLoader(LOADER_ID, null, this);
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
				context.startActivity(intent);

				CharSequence text = "Hello ADD Button.";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		});
	}

	/**
	 * Actions for Done Button
	 */
	private void setDoneButtonListener() {
		findViewById(R.id.buttonDone).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Context context = v.getContext();

				// Start FoodSelectorActivity
				Intent intent = new Intent(context, FoodSelectorActivity.class);
				intent.putExtra(FoodSelectorActivity.selFoodTimeExtraName, selectedFoodTimeId);
				context.startActivity(intent);

				CharSequence text = "DONE Button.";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		});
	}

	/**
	 * Actions for Favourite Button
	 */
	private void setFavouriteListener() {
		findViewById(R.id.buttonFavourite).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//TODO: Launch FoodSelectorActivity (favourite mode=on)
				Context context = v.getContext();
				CharSequence text = "Hello Favourite Button.";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
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

		// Open DatePickerDialog and set user selected date
		dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// Display Selected date in textbox
				tvDisplayDate.setText(DateUtils.formatDate(year,monthOfYear,dayOfMonth,DateUtils.DATE_FORMAT_DAYMONTHYEAR));
				mYear = year;
				mMonth = monthOfYear;
				mDay = dayOfMonth;
			}
		}, mYear, mMonth, mDay);

		// Set OnDateSetListener to TextView
		tvDisplayDate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Launch Date Picker Dialog
				dpd.show();
			}
		});

		// Set OnDateSetListener to Calendar Icon
		findViewById(R.id.imageViewDate).setOnClickListener(new View.OnClickListener() {
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
		return new CursorLoader(TrackFoodActivity.this, FoodContentProvider.CONTENT_URI_TRACKEDFOOD,
				PROJECTION, mSelectionClause, mSelectionArgs, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");

		adapter.setData(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if (DEBUG) Log.i(TAG, "+++ onLoadReset() called! +++");

		adapter.clearData();
	}
}
