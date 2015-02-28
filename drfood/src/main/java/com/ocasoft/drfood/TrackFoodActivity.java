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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ocasoft.drfood.infoobjects.Food;
import com.ocasoft.drfood.objects.TrackFoodListAdapter;

import java.util.ArrayList;

public class TrackFoodActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("TRACKFOOD","TrackFoodActivity - OnCreate");
		setContentView(R.layout.activity_trackfood);

		// Fill the List
//		LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View v = vi.inflate(R.layout.your_layout, null);
		// fill in any details dynamically here
//		TextView textView = (TextView) v.findViewById(R.id.a_text_view);
//		textView.setText("your text");
//
//		// insert into main view
//		ViewGroup insertPoint = (ViewGroup) findViewById(R.id.insert_point);
//		insertPoint.addView(v, 0, new ViewGroup.LayoutParams(
//				ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));



		//generate list
		// TODO: Mockup
		ArrayList<Food> list = new ArrayList<Food>();
		for (int i = 0; i < 50; i++) {
			Food object = new Food(i,"Spaghetti Carbonara "+i,"Breakfast",500);

			list.add(object);
		}

		//instantiate custom adapter
		TrackFoodListAdapter adapter = new TrackFoodListAdapter(list, this);

		//handle listview and assign adapter
		//ListView lView = (ListView)findViewById(R.id.listFoodConsummed);
		GridView lView = (GridView) findViewById(R.id.listFoodConsummed);
		lView.setAdapter(adapter);


//		Log.i("TRACKFOOD","TrackFoodActivity - OnCreate - Inflate a new item");
//		LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		ViewGroup parent = (ViewGroup)findViewById(R.id.listItemTrackFood);
//		View child = inflater.inflate(R.layout.item_trackfood, parent);
//
//
//		if (child != null) {
//			setItemText("test item programmatically...",R.id.textViewFoodNameItemX, child); // Modificación del nombre del producto
//			setItemText("125 gr",R.id.textViewQuantityItemX, child); // Modificación del nombre del producto
//			setItemText("1500 cal",R.id.textViewFoodCalItemX, child); // Modificación del nombre del producto
//
//			Log.i("TRACKFOOD","TrackFoodActivity - OnCreate - child=" +child.getId());


//			ImageView buttonShowHistory = (ImageView) child.findViewById(R.id.deleteFoodIcon);
//			if (buttonShowHistory != null) {
//				buttonShowHistory.setOnClickListener(new View.OnClickListener() {
//					public void onClick(View v) {
//						Context context = v.getContext();
//						Log.i("TRACKFOOD","TrackFoodActivity - OnCreate - someone press delete icon");
//						CharSequence text = "Hello delete button." + v.getParent().getParent();
//						Log.i("TRACKFOOD","TrackFoodActivity - OnCreate - someone press delete icon");
//						int duration = Toast.LENGTH_LONG;
//
//
//
//						Toast toast = Toast.makeText(context, text, duration);
//						toast.show();
//						Log.i("TRACKFOOD","TrackFoodActivity - OnCreate - deleteicon=" +v.getId());
//
//					}
//				});
//			}
//		}
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
}
