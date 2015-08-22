package com.ocasoft.drfood.uiobjects;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import com.ocasoft.drfood.contentprovider.FoodContentProvider;
import com.ocasoft.drfood.database.FoodTable;
import com.ocasoft.drfood.database.TrackFoodTable;
import com.ocasoft.drfood.infoobjects.Food;
import com.ocasoft.drfood.utils.SharedPreferencesUtils;

import org.apache.commons.lang3.StringEscapeUtils;

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
		listItemFoodNameText.setText(StringEscapeUtils.unescapeJava(list.get(position).getName()));

		// Modify Food Quantity
		TextView listItemQuantityText = (TextView)convertView.findViewById(R.id.textViewQuantityItemX);
		listItemQuantityText.setText(list.get(position).getQuantity() + " "
				+ StringEscapeUtils.unescapeJava(list.get(position).getUnity_measure()));

		// Modify Food Calories
		TextView listItemCaloriesText = (TextView)convertView.findViewById(R.id.textViewFoodCalItemX);
		listItemCaloriesText.setText(list.get(position).getEnergy() + " kcal");

		//Handle delete button onClickListener
		LinearLayout buttonDeleteFood = (LinearLayout) convertView.findViewById(R.id.deleteFoodLayout);
		if (buttonDeleteFood != null) {
			buttonDeleteFood.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (DEBUG) Log.i(TAG, "+++ deleteFood => " + list.get(position).getId() + "! +++");
					Context context = v.getContext();

					// Generate the URI (append the Id)
					Uri contentUriTrackId = Uri.withAppendedPath(
							FoodContentProvider.CONTENT_URI_TRACK,
							Integer.toString(list.get(position).getTrackId()));

					// Use the contentProvider to delete the record with the Id
					ContentResolver cr = context.getContentResolver();
					cr.delete(contentUriTrackId,
							null,
							null
					);

					// Delete the element of the front-end list
					list.remove(position);
					notifyDataSetChanged();
				}
			});
		}

		// Handle click on product (update it)
		LinearLayout productSummaryLL = (LinearLayout) convertView.findViewById(R.id.productSummaryLL);
		if (productSummaryLL != null) {
			productSummaryLL.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (DEBUG) Log.i(TAG, "+++ editFood => " + list.get(position).getId() + "! +++");

					Context context = v.getContext();


					Intent intent = new Intent(context, FoodDetailActivity.class);
					intent.putExtra(FoodDetailActivity.EDIT_OPERATION, true);
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_ID, list.get(position).getId());
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_NAME, list.get(position).getName());
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_QUANTITY, list.get(position).getQuantity());
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_ENERGY, list.get(position).getEnergy());
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT, list.get(position).getTimeMoment());
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE, list.get(position).getUnity_measure());
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_CATEGORY, list.get(position).getCategory());
					intent.putExtra(TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID, list.get(position).getTrackId());
					intent.putExtra(FoodSelectorActivity.selFoodTimeExtraName,
							SharedPreferencesUtils.getSharedPrefIntValue(context,
									SharedPreferencesUtils.SP_CURRENTFOODTIME));
					intent.putExtra(FoodSelectorActivity.selDayExtraName,
							SharedPreferencesUtils.getSharedPrefIntValue(context,
									SharedPreferencesUtils.SP_CURRENTDAY));
					intent.putExtra(FoodSelectorActivity.selYearExtraName,
							SharedPreferencesUtils.getSharedPrefIntValue(context,
									SharedPreferencesUtils.SP_CURRENTYEAR));
					intent.putExtra(FoodSelectorActivity.selMonthExtraName,
							SharedPreferencesUtils.getSharedPrefIntValue(context,
									SharedPreferencesUtils.SP_CURRENTMONTH));

					context.startActivity(intent);
				}
			});
		}


		return convertView;
	}

	public void setData(Cursor data) {
		if (DEBUG) Log.i(TAG, "+++ setData() called! +++");
		int i = 0;

		// Empty current list
		clearData();

		// Load new values
		if (data != null && data.moveToFirst()) { // move cursor to first row

			do {
				Food object = new Food();

				// Get data from Cursor
				object.setId(data.getInt(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_ID)));
				object.setName(data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_NAME)));
				object.setTimeMoment(data.getInt(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT)));
				object.setFats(data.getInt(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_FATS)));
				object.setTrackId(data.getInt(data.getColumnIndex(TrackFoodTable.COLUMN_NAME_TRACKFOOD_ID)));
				object.setQuantity(data.getInt(data.getColumnIndex(TrackFoodTable.COLUMN_NAME_TRACKFOOD_QUANTITY)));
				object.setUnity_measure(data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE)));

//				if (DEBUG) Log.i(TAG, "+++ setData() id+name+timemoment+fats => [" + object.getTrackId() + "] "
//						+ object.getId() + " - " + object.getName() + " - " + object.getTimeMoment()
//						+ " - " + object.getFats() + " +++");

				list.add(object);
			} while (data.moveToNext()); // move to next row
		} else {
			if (DEBUG) Log.i(TAG, "+++ setData() no-results! +++");
		}
		this.notifyDataSetChanged();
	}

	public void clearData() {
		list.clear();
		this.notifyDataSetChanged();
	}
}
