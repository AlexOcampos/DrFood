package com.ocasoft.drfood;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_selector);

        if (DEBUG) Log.i(TAG, "+++ onCreate() called! +++");

        if (DEBUG) {
            Log.i(TAG, "+++ Calling initLoader()! +++");
            if (getLoaderManager().getLoader(LOADER_ID) == null) {
                Log.i(TAG, "+++ Initializing the new Loader... +++");
            } else {
                Log.i(TAG, "+++ Reconnecting with existing Loader (id '1')... +++");
            }
        }
        // Initialize a Loader with id '1'. If the Loader with this id already
        // exists, then the LoaderManager will reuse the existing Loader.
        getLoaderManager().initLoader(LOADER_ID, null, this);

        fillData();

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

        getLoaderManager().initLoader(0, null, this);
        adapter = new GridAdapter(getBaseContext());
        gridview.setAdapter(adapter);
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
