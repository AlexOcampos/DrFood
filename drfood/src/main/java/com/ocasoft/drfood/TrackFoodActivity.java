package com.ocasoft.drfood;

import android.app.DatePickerDialog;
import android.content.Context;
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

import com.ocasoft.drfood.infoobjects.Food;
import com.ocasoft.drfood.uiobjects.TrackFoodListAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class TrackFoodActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener {
	private int mYear, mMonth, mDay;
	TextView tvDisplayDate;
	private DatePickerDialog dpd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trackfood);

		//======================== Set Spinner values ========================
		setSpinnerFoodTimes();

		//========================      set date      ========================
		setDateTrackFood();

		//======================== generate food list ========================
		generateFoodList();

		//======================== Set button Listeners ========================
		// Add Button
		setAddButtonListener();

		// Done Button
		setDoneButtonListener();

		// Favourite Button
		setFavouriteListener();
	}

	/**
	 * Initialize spinner foodtimes and all the actions related with this
	 */
	private void setSpinnerFoodTimes() {
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
	}

	/**
	 * Actions for Add Button
	 */
	private void setAddButtonListener() {
		findViewById(R.id.buttonAdd).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//TODO: Launch FoodSelectorActivity
				Context context = v.getContext();
				CharSequence text = "Hello Add Button.";
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
				//TODO: Launch Save data and exit TrackFoodActivity
				Context context = v.getContext();
				CharSequence text = "Hello Done Button.";
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
		// TODO: Mockup
		ArrayList<Food> list = new ArrayList<Food>();
		for (int i = 1; i < 15; i++) {
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
	 * Set the date to the DateTextView and create DatePickerDialog.
	 */
	private void setDateTrackFood() {
		tvDisplayDate = (TextView) findViewById(R.id.textViewDate);
		// Set current date in the TextView
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		tvDisplayDate.setText(format.format(c.getTime()));

		// Open DatePickerDialog and set user selected date
		dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// Display Selected date in textbox
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.MONTH, monthOfYear);
				cal.set(Calendar.DATE, dayOfMonth);
				tvDisplayDate.setText(format.format(cal.getTime()));
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

		// Showing selected spinner item
		Log.i("TRACKFOOD", "Selected: " + item);
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}
}
