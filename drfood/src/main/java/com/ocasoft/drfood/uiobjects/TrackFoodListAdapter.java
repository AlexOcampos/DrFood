package com.ocasoft.drfood.uiobjects;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.ocasoft.drfood.*;
import com.ocasoft.drfood.database.FoodTable;
import com.ocasoft.drfood.infoobjects.Food;

import java.util.ArrayList;

/**
 * Created by Alex on 31/01/2015.
 */
public class TrackFoodListAdapter extends BaseAdapter implements ListAdapter {
	private static final String TAG = "DRFOOD_TFoodListA";
	private static final boolean DEBUG = true; //TODO : Disable DEBUG
	private ArrayList<Food> list = new ArrayList<Food>();
	private Context mContext;

	public TrackFoodListAdapter(Context context) {
		this.mContext = context;
	}

	public TrackFoodListAdapter(ArrayList<Food> list, Context context) {
		this.list = list;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int pos) {
		return list.get(pos);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_trackfood, null);
		}

		// Modify Food Name
		TextView listItemFoodNameText = (TextView)convertView.findViewById(R.id.textViewFoodNameItemX);
		listItemFoodNameText.setText(list.get(position).getName());

		// Modify Food Quantity
		TextView listItemQuantityText = (TextView)convertView.findViewById(R.id.textViewQuantityItemX);
		listItemQuantityText.setText("1 piece");

		// Modify Food Calories
		TextView listItemCaloriesText = (TextView)convertView.findViewById(R.id.textViewFoodCalItemX);
		listItemCaloriesText.setText("" + list.get(position).getFats());

		//Handle delete button onClickListener
		LinearLayout buttonDeleteFood = (LinearLayout) convertView.findViewById(R.id.deleteFoodLayout);
		if (buttonDeleteFood != null) {
			buttonDeleteFood.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
						list.remove(position); //or some other task
						notifyDataSetChanged();
				}
			});
		}

		return convertView;
	}

	public void setData(Cursor data) {
		if (DEBUG) Log.i(TAG, "+++ setData() called! +++");
		int i = 0;
		if (data.moveToFirst()) { // move cursor to first row

			do {
				Food object = new Food();

				// Get data from Cursor
				object.setId(data.getInt(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_ID)));
				object.setName(data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_NAME)));
				object.setTimeMoment(data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT)));
				object.setFats(data.getInt(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_FATS)));

				if (DEBUG) Log.i(TAG, "+++ setData() id+name+timemoment+fats => "
						+ object.getId() + " - " + object.getName() + " - " + object.getTimeMoment()
						+ " - " + object.getFats() + " +++");

				list.add(object);
			} while (data.moveToNext()); // move to next row
		} else {
			if (DEBUG) Log.i(TAG, "+++ setData() no-results! +++");
		}
	}
}
