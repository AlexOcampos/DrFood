package com.ocasoft.drfood;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ocasoft.drfood.fragments.AboutFragment;
import com.ocasoft.drfood.fragments.ConfigurationFragment;
import com.ocasoft.drfood.fragments.HomeFragment;
import com.ocasoft.drfood.utils.SharedPreferencesUtils;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

public class HomeActivity extends MaterialNavigationDrawer {
    private static final String TAG = "DRFOOD_HomeAct";
    private static final boolean DEBUG = true; //TODO : Disable DEBUG

	private MaterialAccount account;
	private MaterialSection target;

	private Context mContext;

	@Override
	public void init(Bundle bundle) {
		if (DEBUG) Log.i(TAG,"onCreate");

		// create and set the header
		View view = LayoutInflater.from(this).inflate(R.layout.custom_fragment_header, null);
		setDrawerHeaderCustom(view);

		// Save the context
		mContext = view.getContext();


		// create sections
		target = newSection(getResources().getString(R.string.title_section1),
				R.drawable.home_icon, new HomeFragment());
		this.addSection(target);
		this.addSection(newSection(getResources().getString(R.string.title_section2),
				R.drawable.settings_icon, new ConfigurationFragment()));
		this.addSection(newSection(getResources().getString(R.string.title_section3),
				R.drawable.info_icon, new AboutFragment()));

		enableToolbarElevation();

		thread.start();
	}

	@Override
	public void onResume() {
		super.onResume();  // Always call the superclass method first
		TextView emailTV = (TextView) findViewById(R.id.emailUserTextView);
		TextView nameTV = (TextView) findViewById(R.id.nameUserTextView);

		emailTV.setText(SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_USER_EMAIL));
		nameTV.setText(SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_USER_NAME));
	}

	private Thread thread = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					setSection(target);
				}
			});
		}
	});

}
