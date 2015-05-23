package com.ocasoft.drfood.uiobjects;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ocasoft.drfood.R;
import com.ocasoft.drfood.database.FoodTable;

/**
 * Based on https://github.com/goodev/SquareGridView/blob/master/SquareGrid/src/org/goodev/squaregrid/GridAdapter.java
 */
public class GridAdapter extends BaseAdapter{
	public static class Item{
		public int id;
		public String text;
		public int resId;
	}

	private static final String TAG = "DRFOOD_GridAdapter";
	private static final boolean DEBUG = true;

	private List<Item> mItems = new ArrayList<GridAdapter.Item>();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if (DEBUG) Log.i(TAG, "+++ getView() called! +++");
		if(convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
		}
		ImageView image = (ImageView) convertView.findViewById(R.id.icon);
		TextView text = (TextView) convertView.findViewById(R.id.text);
		Item item = (Item) getItem(position);
		image.setImageResource(item.resId);
		text.setText(item.text);

		if (DEBUG) Log.i(TAG, "+++ getView() text: "+ item.text + " || (" + position + ") +++");
		return convertView;
	}

	public void setData(Cursor data) {
		if (DEBUG) Log.i(TAG, "+++ setData() called! +++");
		int i = 0;
		if (data.moveToFirst()) { // move cursor to first row

			do {
				Item object = new Item();

				// Get version from Cursor
				object.text = data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_NAME));
				object.resId = R.drawable.spaghetti;

				mItems.add(object);
			} while (data.moveToNext()); // move to next row
		}



	}
}
