package com.ocasoft.drfood.objects;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ocasoft.drfood.*;
import com.ocasoft.drfood.infoobjects.Food;

import java.util.ArrayList;

/**
 * Created by Alex on 31/01/2015.
 */
public class TrackFoodListAdapter extends BaseAdapter implements ListAdapter {

	private ArrayList<Food> list = new ArrayList<Food>();
	private Context mContext;


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
//			if (view == null) {
//				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//				view = inflater.inflate(R.id.listFoodConsummed, null);
//			}

		if(convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item_trackfood, null);
		}

		Log.i("TRACKFOOD", "TrackFoodListActivity - position=" + position);

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
		ImageView buttonShowHistory = (ImageView) convertView.findViewById(R.id.deleteFoodIcon);
		if (buttonShowHistory != null) {
			buttonShowHistory.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					// TODO: Eliminar este codigo de ejemplo
//					Context context = v.getContext();
//					CharSequence text = "Hello delete button." + v.getParent().getParent();
//					int duration = Toast.LENGTH_LONG;
//					Toast toast = Toast.makeText(context, text, duration);
//					toast.show();
					// fin codigo ejemplo

						list.remove(position); //or some other task
						notifyDataSetChanged();
				}
			});
		}

		return convertView;
	}
}
