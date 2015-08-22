package com.ocasoft.drfood;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ocasoft.drfood.dbhandlers.DatabaseHandler;
import com.ocasoft.drfood.infoobjects.Food;

import java.util.List;

public class HomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private static final String TAG = "DRFOOD_HomeAct";
	public static final String PREFS_NAME = "DrFoodPrefsFile";
    private static final boolean DEBUG = true; //TODO : Disable DEBUG

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		if (DEBUG) Log.i(TAG,"onCreate");

        setContentView(R.layout.activity_home);

        mNavigationDrawerFragment =
                (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
		if (DEBUG) Log.i(TAG,"onNavigationDrawerItemSelected");
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
				.replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1: // Home
                mTitle = getString(R.string.title_section1);
                break;
            case 2: // Section 2
                mTitle = getString(R.string.title_section2);
                break;
            case 3: // Help
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
			if (DEBUG) Log.i("NavDrawFrag","PlaceholderFragment newInstance");
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

			View rootView = null;
			TextView textView;

			//Progress bar
			ProgressBar mProgress;
			int mProgressStatus = 0;

			//Cards
			LinearLayout trackFoodCard;

			int sectionFragment = getArguments().getInt(ARG_SECTION_NUMBER);
			switch (sectionFragment) {
				case 1: // Home Fragment
					if (DEBUG) Log.i("NavDrawFrag","PlaceholderFragment - onCreateView - Home section");
					rootView = inflateHomeFragment(inflater, container);

					//Progress bar
					if (DEBUG) Log.i("NavDrawFrag","PlaceholderFragment - onCreateView - Home section - ProgressBar init");
					mProgress = (ProgressBar) rootView.findViewById(R.id.progressBarHome);
					mProgress.setMax(3000);
					mProgress.setProgress(75);
					//mProgress.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN); // +2500
					//mProgress.getProgressDrawable().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_IN); // 2200 - 2500
					//mProgress.getProgressDrawable().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_IN); // 1700 - 2200
					mProgress.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN); // -1700


					//Manage Cards
					//rootView.findViewById(R.id.HomeBody_LinLay1).setVisibility(View.GONE); // Remove card

					// Test LL clickable
					LinearLayout homeCard_LL3 = (LinearLayout) rootView.findViewById(R.id.HomeBody_LinLay3);
					homeCard_LL3.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// Do something in response to button click
							Context context = v.getContext();

							CharSequence text = "Hello LinearLayout Lorem Ipsum dolor.";
							int duration = Toast.LENGTH_LONG;

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
						}
					});

					break;
				case 2: // Section2 Fragment
					if (DEBUG) Log.i("NavDrawFrag","PlaceholderFragment - onCreateView - 2 section");
					rootView = inflater.inflate(R.layout.fragment_section2, container, false);
					textView = (TextView) rootView.findViewById(R.id.section_label);
					textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));

					Button initDBButton = (Button) rootView.findViewById(R.id.buttonInitDB);
					initDBButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// Do something in response to button click
							Context context = v.getContext();

							// Restore preferences
							SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
							boolean dbInitialized = settings.getBoolean("dbInitialized", false);

							SharedPreferences.Editor editor = settings.edit();
							editor.putBoolean("dbInitialized", false);
							editor.commit();

							CharSequence text = "Next time, application will load the DB";
							int duration = Toast.LENGTH_LONG;

							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
						}
					});

					break;
				case 3: // Help Fragment
					if (DEBUG) Log.i("NavDrawFrag","PlaceholderFragment - onCreateView - Help section");
					rootView = inflater.inflate(R.layout.fragment_help, container, false);
					textView = (TextView) rootView.findViewById(R.id.section_label);
					textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
					break;
			}

            return rootView;
        }

		/**
		 * Returns the Home Fragment for the NavigationDrawerFragment.
		 * @param inflater the inflater of the onCreateView
		 * @param container the container of the onCreateView
		 * @return the home view
		 */
		private View inflateHomeFragment (LayoutInflater inflater, ViewGroup container) {
			View rootView;
			TextView textView;

			// Inflate the home view
			rootView = inflater.inflate(R.layout.fragment_home, container, false);

			// Load section number
//			textView = (TextView) rootView.findViewById(R.id.section_label);
//			textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));

			/* ********************************************************
			 * Actions for TrackFood's button (Clickable Linear Layout)
			 * ********************************************************/
			LinearLayout buttonTrackFood = (LinearLayout) rootView.findViewById(R.id.HomeBody_LinLay_Track);
			buttonTrackFood.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO: Start Track food activity
					Context context = v.getContext();

//					CharSequence text = "Hello Button1. You will track food.";
//					int duration = Toast.LENGTH_LONG;
//
//					Toast toast = Toast.makeText(context, text, duration);
//					toast.show();

					Intent intent = new Intent(context, TrackFoodActivity.class);

					context.startActivity(intent);
				}
			});

			/* ********************************************************
			 * Actions for Edit Tracked Food's button (Clickable Linear Layout)
			 * ********************************************************/
			LinearLayout buttonEditTrackFood = (LinearLayout) rootView.findViewById(R.id.HomeBody_LinLay_Edit);
			buttonEditTrackFood.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO: Start Track food activity (edit option)
					Context context = v.getContext();

					Intent intent = new Intent(context, FoodSelectorActivity.class);

					context.startActivity(intent);
				}
			});

			/* ********************************************************
			 * Actions for ShowHistory's button  (Clickable Linear Layout)
			 * ********************************************************/
			LinearLayout buttonShowHistory = (LinearLayout) rootView.findViewById(R.id.HomeBody_LinLay_History);
			buttonShowHistory.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// TODO: Start Show history activity
					Context context = v.getContext();

					Intent intent = new Intent(context, FoodDetailActivity.class);
					context.startActivity(intent);

//					CharSequence text = "Hello Button2. You will show the food history.";
//					int duration = Toast.LENGTH_LONG;
//
//					Toast toast = Toast.makeText(context, text, duration);
//					toast.show();
				}
			});

			return rootView;
		}

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((HomeActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
