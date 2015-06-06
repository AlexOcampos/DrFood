package com.ocasoft.drfood.uiobjects;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ocasoft.drfood.FoodDetailActivity;
import com.ocasoft.drfood.R;
import com.ocasoft.drfood.database.FoodTable;

/**
 * Based on https://github.com/goodev/SquareGridView/blob/master/SquareGrid/src/org/goodev/squaregrid/GridAdapter.java
 */
public class GridAdapter extends BaseAdapter{
	public static class Food {
		public int id;
		public String text;
		public int quantity;
		public int energy;
		public String timeMoment;
		public String unityMeasure;
		public String category;
		public int resId;
	}

	private static final String TAG = "DRFOOD_GridAdapter";
	private static final boolean DEBUG = true; //TODO : Disable DEBUG

	private List<Food> mItems = new ArrayList<GridAdapter.Food>();
	private Context mContext;
	public GridAdapter(Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (DEBUG) Log.i(TAG, "+++ getView() called! +++");
		if(convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
		}

		ImageView image = (ImageView) convertView.findViewById(R.id.icon);
		TextView text = (TextView) convertView.findViewById(R.id.text);

		Food item = (Food) getItem(position);
		image.setImageResource(item.resId);
		text.setText(item.text);


		convertView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Context context = v.getContext();
				Food item = (Food) getItem(position);

				if (item == null) {
					// TODO: Manage error (show a toast?)
				} else {
					Intent intent = new Intent(context, FoodDetailActivity.class);
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_ID, item.id);
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_NAME, item.text);
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_QUANTITY, item.quantity);
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_ENERGY, item.energy);
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT, item.timeMoment);
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE, item.unityMeasure);
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_CATEGORY, item.category);
					context.startActivity(intent);
				}
			}
		});

		if (DEBUG) Log.i(TAG, "+++ getView() text: "+ item.text + " || (" + position + ") +++");
		return convertView;
	}

	public void setData(Cursor data) {
		if (DEBUG) Log.i(TAG, "+++ setData() called! +++");
		int i = 0;
		if (data.moveToFirst()) { // move cursor to first row

			do {
				Food object = new Food();

				// Get data from Cursor
				object.id = data.getInt(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_ID));
				object.text = data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_NAME));
				object.quantity = data.getInt(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_QUANTITY));
				object.energy = data.getInt(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_ENERGY));
				object.timeMoment = data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT));
				object.unityMeasure = data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE));
				object.category = data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_CATEGORY));
				object.resId = R.drawable.spaghetti;

				mItems.add(object);
			} while (data.moveToNext()); // move to next row
		}



	}
}
