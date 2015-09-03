package com.ocasoft.drfood;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ocasoft.drfood.contentprovider.FoodContentProvider;
import com.ocasoft.drfood.database.FoodTable;
import com.ocasoft.drfood.database.TrackDiaryTable;
import com.ocasoft.drfood.infoobjects.FoodDiaryRecord;
import com.ocasoft.drfood.infoobjects.FoodDiaryRecordList;
import com.ocasoft.drfood.infoobjects.FoodTimeList;
import com.ocasoft.drfood.utils.DateUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryActivity extends AppCompatActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {
	private static final String TAG = "DRFOOD_History";
	private static final boolean DEBUG = true;

	private FoodDiaryRecordList diaryRecordList;

	// The Loader's id (this id is specific to the ListFragment's LoaderManager)
	private static final int LOADER_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		diaryRecordList = new FoodDiaryRecordList();

		// Initialize a Loader with id '1'. If the Loader with this id already
		// exists, then the LoaderManager will reuse the existing Loader.
		getLoaderManager().initLoader(LOADER_ID, null, this);

//		mockupDataSets(barChart);
	}

	private BarData prepareDataSets() {
		List<BarEntry> valsFoodTime1 = diaryRecordList.getBarEntry(
				FoodTimeList.TIMEMOMENT_BREAKFAST,FoodDiaryRecordList.CALORIES);
		List<BarEntry> valsFoodTime2 = diaryRecordList.getBarEntry(
				FoodTimeList.TIMEMOMENT_LUNCH,FoodDiaryRecordList.CALORIES);
		List<BarEntry> valsFoodTime3 = diaryRecordList.getBarEntry(
				FoodTimeList.TIMEMOMENT_SNACK,FoodDiaryRecordList.CALORIES);
		List<BarEntry> valsFoodTime4 = diaryRecordList.getBarEntry(
				FoodTimeList.TIMEMOMENT_DINNER,FoodDiaryRecordList.CALORIES);

		// TODO DEBUG ===========================================================================
		for (BarEntry v : valsFoodTime1) {
			if (DEBUG) Log.i(TAG, "+++ valsFoodTime1! +++ " + v.getVal() + " - " + v.getXIndex());
		}

		BarDataSet setFoodTime1 = new BarDataSet(valsFoodTime1,
				FoodTimeList.getNameById(FoodTimeList.TIMEMOMENT_BREAKFAST));
		BarDataSet setFoodTime2 = new BarDataSet(valsFoodTime2,
				FoodTimeList.getNameById(FoodTimeList.TIMEMOMENT_LUNCH));
		BarDataSet setFoodTime3 = new BarDataSet(valsFoodTime3,
				FoodTimeList.getNameById(FoodTimeList.TIMEMOMENT_SNACK));
		BarDataSet setFoodTime4 = new BarDataSet(valsFoodTime4,
				FoodTimeList.getNameById(FoodTimeList.TIMEMOMENT_DINNER));

		setFoodTime1.setColors(new int[]{R.color.bar_breakfast_color}, getBaseContext());
		setFoodTime2.setColors(new int[]{R.color.bar_lunch_color}, getBaseContext());
		setFoodTime3.setColors(new int[]{R.color.bar_snack_color}, getBaseContext());
		setFoodTime4.setColors(new int[]{R.color.bar_dinner_color}, getBaseContext());

		List<BarDataSet> dataSets = new ArrayList<BarDataSet>();
		dataSets.add(setFoodTime1);
		dataSets.add(setFoodTime2);
		dataSets.add(setFoodTime3);
		dataSets.add(setFoodTime4);

		ArrayList<String> xVals = diaryRecordList.getDays();

		BarData data = new BarData(xVals, dataSets);

		return data;
	}

	private void createBarChart() {
		BarChart barChart = (BarChart) findViewById(R.id.historyBarChart);
		barChart.setDescription(null);
		barChart.setNoDataTextDescription("No hay datos");
		barChart.setData(prepareDataSets());
		barChart.setMaxVisibleValueCount(0);
		//barChart.setVisibleXRangeMaximum(16);
		//barChart.animateY(3000, Easing.EasingOption.EaseOutBack);
		barChart.invalidate(); // refresh
	}

	@Deprecated
	private void mockupDataSets(BarChart barChart) {
		ArrayList<BarEntry> valsFoodTime1 = new ArrayList<BarEntry>();
		ArrayList<BarEntry> valsFoodTime2 = new ArrayList<BarEntry>();
		ArrayList<BarEntry> valsFoodTime3 = new ArrayList<BarEntry>();
		ArrayList<BarEntry> valsFoodTime4 = new ArrayList<BarEntry>();

		// Fill valsFoodTime1
		valsFoodTime1.add(new BarEntry(112, 0));
		valsFoodTime1.add(new BarEntry(114, 1));
		valsFoodTime1.add(new BarEntry(135, 2));
		valsFoodTime1.add(new BarEntry(100, 3));
		valsFoodTime1.add(new BarEntry(99, 4));
		valsFoodTime1.add(new BarEntry(80, 5));
		valsFoodTime1.add(new BarEntry(110, 6));
		valsFoodTime1.add(new BarEntry(112, 7));
		valsFoodTime1.add(new BarEntry(114, 8));
		valsFoodTime1.add(new BarEntry(135, 9));
		valsFoodTime1.add(new BarEntry(100, 10));
		valsFoodTime1.add(new BarEntry(99, 11));
		valsFoodTime1.add(new BarEntry(80, 12));
		valsFoodTime1.add(new BarEntry(110, 13));

		// Fill valsFoodTime2
		valsFoodTime2.add(new BarEntry(212, 0));
		valsFoodTime2.add(new BarEntry(154, 1));
		valsFoodTime2.add(new BarEntry(175, 2));
		valsFoodTime2.add(new BarEntry(120, 3));
		valsFoodTime2.add(new BarEntry(111, 4));
		valsFoodTime2.add(new BarEntry(85, 5));
		valsFoodTime2.add(new BarEntry(110, 6));
		valsFoodTime2.add(new BarEntry(145, 7));
		valsFoodTime2.add(new BarEntry(132, 8));
		valsFoodTime2.add(new BarEntry(131, 9));
		valsFoodTime2.add(new BarEntry(101, 10));
		valsFoodTime2.add(new BarEntry(113, 11));
		valsFoodTime2.add(new BarEntry(87, 12));
		valsFoodTime2.add(new BarEntry(110, 13));

		// Fill valsFoodTime3
		valsFoodTime3.add(new BarEntry(212, 0));
		valsFoodTime3.add(new BarEntry(154, 1));
		valsFoodTime3.add(new BarEntry(175, 2));
		valsFoodTime3.add(new BarEntry(120, 3));
		valsFoodTime3.add(new BarEntry(111, 4));
		valsFoodTime3.add(new BarEntry(85, 5));
		valsFoodTime3.add(new BarEntry(110, 6));
		valsFoodTime3.add(new BarEntry(145, 7));
		valsFoodTime3.add(new BarEntry(132, 8));
		valsFoodTime3.add(new BarEntry(131, 9));
		valsFoodTime3.add(new BarEntry(101, 10));
		valsFoodTime3.add(new BarEntry(113, 11));
		valsFoodTime3.add(new BarEntry(87, 12));
		valsFoodTime3.add(new BarEntry(110, 13));

		// Fill valsFoodTime2
		valsFoodTime4.add(new BarEntry(212, 0));
		valsFoodTime4.add(new BarEntry(154, 1));
		valsFoodTime4.add(new BarEntry(175, 2));
		valsFoodTime4.add(new BarEntry(120, 3));
		valsFoodTime4.add(new BarEntry(111, 4));
		valsFoodTime4.add(new BarEntry(85, 5));
		valsFoodTime4.add(new BarEntry(110, 6));
		valsFoodTime4.add(new BarEntry(145, 7));
		valsFoodTime4.add(new BarEntry(132, 8));
		valsFoodTime4.add(new BarEntry(131, 9));
		valsFoodTime4.add(new BarEntry(101, 10));
		valsFoodTime4.add(new BarEntry(113, 11));
		valsFoodTime4.add(new BarEntry(87, 12));
		valsFoodTime4.add(new BarEntry(110, 13));


		BarDataSet setFoodTime1 = new BarDataSet(valsFoodTime1, "Desayuno");
		BarDataSet setFoodTime2 = new BarDataSet(valsFoodTime2, "Comida");
		BarDataSet setFoodTime3 = new BarDataSet(valsFoodTime3, "Merienda");
		BarDataSet setFoodTime4 = new BarDataSet(valsFoodTime4, "Cena");

		setFoodTime1.setColors(new int[]{R.color.bar_breakfast_color}, getBaseContext());
		setFoodTime2.setColors(new int[]{R.color.bar_lunch_color}, getBaseContext());
		setFoodTime3.setColors(new int[]{R.color.bar_snack_color}, getBaseContext());
		setFoodTime4.setColors(new int[]{R.color.bar_dinner_color}, getBaseContext());

		List<BarDataSet> dataSets = new ArrayList<BarDataSet>();
		dataSets.add(setFoodTime1);
		dataSets.add(setFoodTime2);
		dataSets.add(setFoodTime3);
		dataSets.add(setFoodTime4);

		ArrayList<String> xVals = new ArrayList<String>();
		xVals.add("04/08/2015");
		xVals.add("05/08/2015");
		xVals.add("06/08/2015");
		xVals.add("07/08/2015");
		xVals.add("08/08/2015");
		xVals.add("09/08/2015");
		xVals.add("10/08/2015");
		xVals.add("11/08/2015");
		xVals.add("12/08/2015");
		xVals.add("13/08/2015");
		xVals.add("14/08/2015");
		xVals.add("15/08/2015");
		xVals.add("16/08/2015");
		xVals.add("17/08/2015");

		BarData data = new BarData(xVals, dataSets);
		barChart.setData(data);


		barChart.setDescription(null);
		barChart.setNoDataTextDescription("No hay datos");
		barChart.setMaxVisibleValueCount(0);
		barChart.setVisibleXRangeMaximum(16);
		barChart.animateY(3000, Easing.EasingOption.EaseOutBack);

		barChart.invalidate(); // refresh
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.menu_history, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//
//		//noinspection SimplifiableIfStatement
//		if (id == R.id.action_settings) {
//			return true;
//		}
//
//		return super.onOptionsItemSelected(item);
//	}

	/**********************/
	/** LOADER CALLBACKS **/
	/**********************/
	/**
	 * Instantiate and return a new Loader for the given ID.
	 *
	 * @param id   The ID whose loader is to be created.
	 * @param args Any arguments supplied by the caller.
	 * @return Return a new Loader instance that is ready to start loading.
	 */
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projectionTrackDiary = {
				TrackDiaryTable.COLUMN_NAME_DIARY_DATE,
				TrackDiaryTable.COLUMN_NAME_DIARY_TIMEFOOD,
				TrackDiaryTable.COLUMN_NAME_DIARY_CALORIES,
				TrackDiaryTable.COLUMN_NAME_DIARY_CARBOHYDRATES,
				TrackDiaryTable.COLUMN_NAME_DIARY_FATS,
				TrackDiaryTable.COLUMN_NAME_DIARY_PROTEINS,
				TrackDiaryTable.COLUMN_NAME_DIARY_USERID
		};
		String mSelectionClause = TrackDiaryTable.COLUMN_NAME_DIARY_USERID + " = ?";
		String[] mSelectionArgs = {
				"1" // UserId
		};
		return new CursorLoader(HistoryActivity.this, FoodContentProvider.CONTENT_URI_DIARY,
				projectionTrackDiary, mSelectionClause, mSelectionArgs, TrackDiaryTable.COLUMN_NAME_DIARY_DATE + " DESC, "
				+ TrackDiaryTable.COLUMN_NAME_DIARY_TIMEFOOD + " ASC");
	}


	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		String result = "";
		if (DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");

		if (data.getCount() > 0) {
			if (!data.isFirst())
				data.moveToNext();

			do {
//				result += "\n" + data.getString(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_DATE))
//						+ " # " + data.getString(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_TIMEFOOD))
//						+ " # " + data.getString(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_CALORIES));

				diaryRecordList.add(new FoodDiaryRecord(
						data.getString(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_DATE)),
						data.getInt(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_TIMEFOOD)),
						data.getDouble(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_CALORIES)),
						data.getDouble(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_FATS)),
						data.getDouble(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_CARBOHYDRATES)),
						data.getDouble(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_PROTEINS))
				));
			} while (data.moveToNext());

			// This is necessary to set correct xaxis ids
			diaryRecordList.refreshList();

			diaryRecordList.sort();

			result += "(id) # date # time # cals # carbs # fats # proteins";
			for (Object f : diaryRecordList) {
				FoodDiaryRecord ff = (FoodDiaryRecord) f;
				result += "\n (" + ff.getId()
						+ ") # " + ff.getDate()
						+ " # " + ff.getFoodTimeId()
						+ " # " + ff.getCalories()
						+ " # " + ff.getCarbohydrates()
						+ " # " + ff.getFats()
						+ " # " + ff.getProteins();
			}

			TextView historyTextView = (TextView) findViewById(R.id.historyTextView);
			historyTextView.setText(result);
		}

		createBarChart();
	}

	/**
	 * Called when a previously created loader is being reset, and thus
	 * making its data unavailable.  The application should at this point
	 * remove any references it has to the Loader's data.
	 *
	 * @param loader The Loader that is being reset.
	 */
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}
}
