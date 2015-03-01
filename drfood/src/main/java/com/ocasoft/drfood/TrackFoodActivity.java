package com.ocasoft.drfood;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ocasoft.drfood.infoobjects.Food;
import com.ocasoft.drfood.objects.TrackFoodListAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class TrackFoodActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trackfood);

		//======================== Set Spinner values ========================
		Spinner spinner = (Spinner) findViewById(R.id.foodtime_spinner);

		// Spinner click listener
		spinner.setOnItemSelectedListener(this);

		// Get foodtime's values
		ArrayList<String> foodTimes = new ArrayList<String>();
		// TODO: Mockup
		foodTimes.add("Breakfast");
		foodTimes.add("Lunch");
		foodTimes.add("Snack");
		foodTimes.add("Dinner");

		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<String> foodTimesAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, foodTimes);

		// Specify the layout to use when the list of choices appears
		foodTimesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// Apply the adapter to the spinner
		spinner.setAdapter(foodTimesAdapter);

		//========================      set date      ========================
		TextView tvDisplayDate = (TextView) findViewById(R.id.textViewDate);
		Calendar c = Calendar.getInstance();
		String year = Integer.toString(c.get(Calendar.YEAR));
		int monthInt = c.get(Calendar.MONTH) + 1;
		String month = (monthInt > 9) ? Integer.toString(monthInt) : "0"+Integer.toString(monthInt);
		int dayInt = c.get(Calendar.DAY_OF_MONTH);
		String day = (dayInt > 9) ? Integer.toString(dayInt) : "0"+Integer.toString(dayInt);

		// set current date into textview (dd-mm-yyyy)
		tvDisplayDate.setText(new StringBuilder()
				.append(day).append("/")
				.append(month).append("/") // Month is 0 based, just add 1
				.append(year));

		//======================== generate food list ========================
		// TODO: Mockup
		ArrayList<Food> list = new ArrayList<Food>();
		for (int i = 1; i < 5; i++) {
			Food object = new Food(i,"Spaghetti Carbonara "+i,"Breakfast",500);
			list.add(object);
		}

		//instantiate custom adapter
		TrackFoodListAdapter adapter = new TrackFoodListAdapter(list, this);

		//handle listview and assign adapter
		GridView lView = (GridView) findViewById(R.id.listFoodConsummed);
		lView.setAdapter(adapter);

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

		// Showing selected spinner item
		Log.i("TRACKFOOD", "Selected: " + item);
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}
}
