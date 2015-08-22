package com.ocasoft.drfood.uiobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ocasoft.drfood.FoodDetailActivity;
import com.ocasoft.drfood.FoodSelectorActivity;
import com.ocasoft.drfood.R;
import com.ocasoft.drfood.database.FoodTable;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Based on https://github.com/goodev/SquareGridView/blob/master/SquareGrid/src/org/goodev/squaregrid/GridAdapter.java
 */
public class GridAdapter extends BaseAdapter implements Filterable {
	public static class FoodUI {
		public int id;
		public String text;
		public int quantity;
		public int energy;
		public String timeMoment;
		public String unityMeasure;
		public String category;
		public int resId;
		public String code;
		public int counter;

		/**
		 * Checks if the filter value exists in the Food object
		 * @param filter filter value
		 * @return true if the Food contains the filter value. If not, false is returned
		 */
		public boolean filterResult(CharSequence filter) {
			return text.toLowerCase().contains(filter.toString().toLowerCase());
		}
	}

	private static final String TAG = "DRFOOD_GridAdapter";
	private static final boolean DEBUG = true; //TODO : Disable DEBUG

	private List<FoodUI> mItems = new ArrayList<GridAdapter.FoodUI>();
    private List<FoodUI> mItemsFiltered = new ArrayList<GridAdapter.FoodUI>();
	private Context mContext;

	// Extras values
	private int selectedFoodTimeId = -1;
	private int selectedDay = -1;
	private int selectedYear = -1;
	private int selectedMonth = -1;


	public GridAdapter(Context context, Bundle extras) {
		mContext = context;

		if(extras != null) {
			selectedFoodTimeId = extras.getInt(FoodSelectorActivity.selFoodTimeExtraName);
			selectedDay = extras.getInt(FoodSelectorActivity.selDayExtraName);
			selectedYear = extras.getInt(FoodSelectorActivity.selYearExtraName);
			selectedMonth = extras.getInt(FoodSelectorActivity.selMonthExtraName);
		} else {
			Log.e(TAG, "GridAdapter() Extras is null");
		}

	}

	@Override
	public int getCount() {
		return mItemsFiltered.size();
	}

	@Override
	public Object getItem(int position) {
		return mItemsFiltered.get(position);
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

		FoodUI item = (FoodUI) getItem(position);
		image.setImageResource(item.resId);
		text.setText(StringEscapeUtils.unescapeJava(item.text));


		convertView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Context context = v.getContext();
				FoodUI item = (FoodUI) getItem(position);

				if (item == null) {
					Log.e(TAG, "getView() setOnClickListener() item == null");
				} else {
					Intent intent = new Intent(context, FoodDetailActivity.class);
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_ID, item.id);
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_NAME, item.text);
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_QUANTITY, item.quantity);
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_ENERGY, item.energy);
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT, item.timeMoment);
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE, item.unityMeasure);
					intent.putExtra(FoodTable.COLUMN_NAME_FOOD_CATEGORY, item.category);
					intent.putExtra(FoodSelectorActivity.selFoodTimeExtraName, selectedFoodTimeId);
					intent.putExtra(FoodSelectorActivity.selDayExtraName, selectedDay);
					intent.putExtra(FoodSelectorActivity.selYearExtraName, selectedYear);
					intent.putExtra(FoodSelectorActivity.selMonthExtraName, selectedMonth);
					context.startActivity(intent);
				}
			}
		});

		if (DEBUG) Log.i(TAG, "+++ getView() text: "+ item.text + " || (" + position + ") " +
				"count: " + item.code + " +++");
		return convertView;
	}

	public void setData(Cursor data) {
		if (DEBUG) Log.i(TAG, "+++ setData() called! +++");
		int i = 0;
		if (data.moveToFirst()) { // move cursor to first row

			do {
				FoodUI object = new FoodUI();

				// Get data from Cursor
				object.id = data.getInt(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_ID));
				object.text = data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_NAME));
				object.quantity = data.getInt(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_QUANTITY));
				object.energy = data.getInt(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_ENERGY));
				object.timeMoment = data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_TIMEMOMENT));
				object.unityMeasure = data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_UNITY_MEASURE));
				object.category = data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_CATEGORY));
				object.code = data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_CODE));
				object.counter = data.getInt(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_COUNTER));

				// Get the image. The name of them is "food_" + foodCode
				String nameOfDrawable = "food_" + data.getString(data.getColumnIndex(FoodTable.COLUMN_NAME_FOOD_CODE));
				int drawableResourceId = mContext.getResources().getIdentifier(nameOfDrawable, "drawable",
						mContext.getPackageName());
				object.resId = drawableResourceId;

				mItems.add(object);
			} while (data.moveToNext()); // move to next row
		}

        //To start, set both data sources to the incoming data
        mItemsFiltered = mItems;

	}

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
				FilterResults results = new FilterResults();    // Holds the results of a filtering operation in values

                //If there's nothing to filter on, return the original data for your list
                if(charSequence == null || charSequence.length() == 0)	{
                    results.values = mItems;
                    results.count = mItems.size();
                } else	{
                    List<FoodUI> filterResultsData = new ArrayList<FoodUI>();

                    for(FoodUI data : mItems)	{
                        //In this loop, you'll filter through originalData and compare each item to charSequence.
                        //If you find a match, add it to your new ArrayList
						if(data.filterResult(charSequence.toString())) {
                            filterResultsData.add(data);
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
				mItemsFiltered = (List<FoodUI>) filterResults.values;  // has the filtered values
				notifyDataSetChanged();  // notifies the data with new filtered values
            }
        };
    }

}
