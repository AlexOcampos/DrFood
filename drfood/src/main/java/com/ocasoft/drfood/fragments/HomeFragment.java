package com.ocasoft.drfood.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ocasoft.drfood.HistoryActivity;
import com.ocasoft.drfood.R;
import com.ocasoft.drfood.TrackFoodActivity;
import com.ocasoft.drfood.utils.SharedPreferencesUtils;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by Alex on 23/08/2015.
 */
public class HomeFragment extends Fragment {
	private static final String TAG = "DRFOOD_HomeFrag";
	private static final boolean DEBUG = true;
	//Progress bar
	ProgressBar mProgress;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home,container,false);

		initProgressBar(rootView);
		initTrackFoodButton(rootView);
		initEditTrackedFoodButton(rootView);
		initHistoryButton(rootView);

		return rootView;
	}

	/**
	 * Init he progress bar with the current calories
	 * @param rootView
	 */
	private void initProgressBar(View rootView) {
		//Progress bar
		if (DEBUG) Log.i("NavDrawFrag", "PlaceholderFragment - onCreateView - Home section - ProgressBar init");
		mProgress = (ProgressBar) rootView.findViewById(R.id.progressBarHome);
		mProgress.setMax(3000);
		mProgress.setProgress(75);
		//mProgress.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN); // +2500
		//mProgress.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN); // 2200 - 2500
		//mProgress.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN); // 1700 - 2200
		mProgress.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN); // -1700
	}

	/**
	 * Actions for TrackFood's button (Clickable Linear Layout)
	 * @param rootView
	 */
	private void initTrackFoodButton (View rootView) {
		LinearLayout buttonTrackFood = (LinearLayout) rootView.findViewById(R.id.HomeBody_LinLay_Track);
		buttonTrackFood.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Context context = v.getContext();

				Intent intent = new Intent(context, TrackFoodActivity.class);

				SharedPreferencesUtils.setSharedPrefValue(context, SharedPreferencesUtils.SP_TFA_CALLED_FROM_HOME, true);

				context.startActivity(intent);
			}
		});
	}

	/**
	 * Actions for Edit Tracked Food's button (Clickable Linear Layout)
	 * @param rootView
	 */
	private void initEditTrackedFoodButton (View rootView) {
		LinearLayout buttonEditTrackFood = (LinearLayout) rootView.findViewById(R.id.HomeBody_LinLay_Edit);
		buttonEditTrackFood.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Context context = v.getContext();

				Intent intent = new Intent(context, TrackFoodActivity.class);

				// We want to load the last used day & foodTime
				SharedPreferencesUtils.setSharedPrefValue(context, SharedPreferencesUtils.SP_TFA_CALLED_FROM_HOME, false);

				context.startActivity(intent);
			}
		});
	}

	/**
	 * Actions for ShowHistory's button  (Clickable Linear Layout)
	 * @param rootView
	 */
	private void initHistoryButton (View rootView) {
		LinearLayout buttonShowHistory = (LinearLayout) rootView.findViewById(R.id.HomeBody_LinLay_History);
		buttonShowHistory.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Context context = v.getContext();

				Intent intent = new Intent(context, HistoryActivity.class);
				context.startActivity(intent);
			}
		});
	}

}
