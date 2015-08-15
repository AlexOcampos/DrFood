package com.ocasoft.drfood;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.GridView;

import com.ocasoft.drfood.contentprovider.FoodContentProvider;
import com.ocasoft.drfood.database.FoodTable;
import com.ocasoft.drfood.uiobjects.GridAdapter;

public class FoodSelectorActivity extends ActionBarActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "DRFOOD_FoodSel";
    private static final boolean DEBUG = true;

    private static final String[] PROJECTION = new String[] {
    	FoodTable.COLUMN_NAME_FOOD_ID,
    	FoodTable.COLUMN_NAME_FOOD_NAME,
		FoodTable.COLUMN_NAME_FOOD_QUANTITY,
		FoodTable.COLUMN_NAME_FOOD_ENERGY,
		FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT,
		FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE,
		FoodTable.COLUMN_NAME_FOOD_CATEGORY
	};

    // The Loader's id (this id is specific to the ListFragment's LoaderManager)
    private static final int LOADER_ID = 1;

    private GridAdapter adapter;

	// Extras values
	private int selectedFoodTimeId;
	private int selectedDay;
	private int selectedYear;
	private int selectedMonth;

	// Extras in the beginning of this activity
	public final static String selFoodTimeExtraName = "selectedFoodTimeId";
	public final static String selDayExtraName = "selectedDay";
	public final static String selYearExtraName = "selectedYear";
	public final static String selMonthExtraName = "selectedMonth";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
		if (DEBUG) Log.i(TAG, "+++ onCreate() called! +++");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_selector);

		boolean loadingOk = false;

		// Get selectedFoodTimeId
		try {
			if (savedInstanceState == null) {
				Bundle extras = getIntent().getExtras();
				if(extras == null) {
					selectedFoodTimeId = -1;
					selectedDay = -1;
					selectedYear = -1;
					selectedMonth = -1;
					loadingOk = false;
				} else {
					selectedFoodTimeId = extras.getInt(selFoodTimeExtraName);
					selectedDay = extras.getInt(selDayExtraName);
					selectedYear = extras.getInt(selYearExtraName);
					selectedMonth = extras.getInt(selMonthExtraName);

					if (DEBUG) Log.i(TAG, "+++ onCreate() selectedFoodTimeId=" + selectedFoodTimeId + " +++");
					loadingOk = true;
				}
			} else {
				selectedFoodTimeId = (Integer) savedInstanceState.getSerializable(selFoodTimeExtraName);
				selectedDay = (Integer) savedInstanceState.getSerializable(selDayExtraName);
				selectedYear = (Integer) savedInstanceState.getSerializable(selYearExtraName);
				selectedMonth = (Integer) savedInstanceState.getSerializable(selMonthExtraName);

				if (DEBUG) Log.i(TAG, "+++ onCreate() selectedFoodTimeId=" + selectedFoodTimeId + " +++");
				loadingOk = true;
			}
		} catch (NumberFormatException e) {
			loadingOk = false;
			handleErrorSelector(); //not valid foodId value
		}


        if (DEBUG) {
            Log.i(TAG, "+++ Calling initLoader()! +++");
            if (getLoaderManager().getLoader(LOADER_ID) == null) {
                Log.i(TAG, "+++ Initializing the new Loader... +++");
            } else {
                Log.i(TAG, "+++ Reconnecting with existing Loader (id '1')... +++");
            }

			Log.i(TAG, "+++ Extras: "
					+ "selectedFoodTimeId: " + selectedFoodTimeId
					+ "| selectedDay: " + selectedDay
					+ "| selectedYear: " + selectedYear
					+ "| selectedMonth: " + selectedMonth
					+ " +++");
        }
        // Initialize a Loader with id '1'. If the Loader with this id already
        // exists, then the LoaderManager will reuse the existing Loader.
        getLoaderManager().initLoader(LOADER_ID, null, this);

        // Fill the List
        fillData();
    }

	private void handleErrorSelector() {
		if (DEBUG) Log.i(TAG, "+++ handleErrorSelector() called! +++");
		//TODO: the selector is incorrect. Do something
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


    /*
     *
     *
     *
     */
	private void fillData() {
		if (DEBUG) Log.i(TAG, "+++ fillData() called! +++");
		GridView gridview = (GridView) findViewById(R.id.gridview);

		getLoaderManager().initLoader(LOADER_ID, null, this);

		Bundle extras = new Bundle();
		extras.putInt(selFoodTimeExtraName, selectedFoodTimeId);
		extras.putInt(selDayExtraName, selectedDay);
		extras.putInt(selYearExtraName, selectedYear);
		extras.putInt(selMonthExtraName, selectedMonth);

		adapter = new GridAdapter(getBaseContext(), extras);
		gridview.setAdapter(adapter);

		// Add Text Change Listener to EditText
		EditText etSearch = (EditText) findViewById(R.id.etSearch);
		etSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// Call back the Adapter with current character to Filter
				adapter.getFilter().filter(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

    /**********************/
    /** LOADER CALLBACKS **/
    /**********************/
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (DEBUG) Log.i(TAG, "+++ onCreateLoader() called! +++");
        // Create a new CursorLoader with the following query parameters.
        return new CursorLoader(FoodSelectorActivity.this, FoodContentProvider.CONTENT_URI_FOOD,
                PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");


        // ++++++++++++++++++++++++++++++++++++++++++ DEGUG +++++++++++++++++++++++++++++++++++++++++++++++++++
//        if (DEBUG) {
//            int i = 0;
//            if (data.moveToFirst()) { // move cursor to first row
//                do {
//                    // Get version from Cursor
//                    String foodName = data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_NAME));
//
//                    Log.i(TAG, "+++ onLoadFinished() (" + i + ") foodName : "+ foodName + " +++");
//                    i++;
//
//                } while (data.moveToNext()); // move to next row
//            }
//        }
        // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

//        adapter.swapCursor(data);

        adapter.setData(data);

//        if (isResumed()) {
//            setListShown(true);
//        } else {
//            setListShownNoAnimation(true);
//        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (DEBUG) Log.i(TAG, "+++ onLoadReset() called! +++");

        //adapter.setData(null);
    }
}
