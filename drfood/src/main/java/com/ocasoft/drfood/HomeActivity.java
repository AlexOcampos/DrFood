package com.ocasoft.drfood;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.ocasoft.drfood.fragments.AboutFragment;
import com.ocasoft.drfood.fragments.HomeFragment;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
import it.neokree.materialnavigationdrawer.elements.MaterialAccount;
import it.neokree.materialnavigationdrawer.elements.MaterialSection;

public class HomeActivity extends MaterialNavigationDrawer {
    private static final String TAG = "DRFOOD_HomeAct";
    private static final boolean DEBUG = true; //TODO : Disable DEBUG

	private MaterialAccount account;
	private MaterialSection target;


	@Override
	public void init(Bundle bundle) {
		if (DEBUG) Log.i(TAG,"onCreate");
//		setContentView(R.layout.activity_home);

		// create and set the header
		View view = LayoutInflater.from(this).inflate(R.layout.custom_fragment_header,null);
		setDrawerHeaderCustom(view);

		// create sections
		target = newSection("Home", R.drawable.ic_action_tick, new HomeFragment());
		this.addSection(target);
		this.addSection(newSection("About", R.drawable.ic_action_tick, new AboutFragment()));

		enableToolbarElevation();

		thread.start();
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
//                    removeAccount(account);
//                    notifyAccountDataChanged();
//                    removeSection(target);
					setSection(target);
				}
			});
		}
	});

}
