package com.ocasoft.drfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ocasoft.drfood.utils.SharedPreferencesUtils;

public class SplashActivity extends AppCompatActivity {
	private static final String TAG = "DRFOOD_SplashAct";
	private static final boolean DEBUG = true; //TODO : Disable DEBUG

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (DEBUG) Log.i(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		String timeFoodEmpties = SharedPreferencesUtils.getSharedPrefStringValue(getBaseContext(),
				SharedPreferencesUtils.SP_TM_MORNING_INIT);

		if (timeFoodEmpties == null) {
			// Init values
			SharedPreferencesUtils.setSharedPrefValue(getBaseContext(), SharedPreferencesUtils.SP_TM_MORNING_INIT, "5:00");
			SharedPreferencesUtils.setSharedPrefValue(getBaseContext(), SharedPreferencesUtils.SP_TM_MORNING_END, "12:59");
			SharedPreferencesUtils.setSharedPrefValue(getBaseContext(), SharedPreferencesUtils.SP_TM_LUNCH_INIT, "13:00");
			SharedPreferencesUtils.setSharedPrefValue(getBaseContext(), SharedPreferencesUtils.SP_TM_LUNCH_END, "16:59");
			SharedPreferencesUtils.setSharedPrefValue(getBaseContext(), SharedPreferencesUtils.SP_TM_SNACK_INIT, "17:00");
			SharedPreferencesUtils.setSharedPrefValue(getBaseContext(), SharedPreferencesUtils.SP_TM_SNACK_END, "19:59");
			SharedPreferencesUtils.setSharedPrefValue(getBaseContext(), SharedPreferencesUtils.SP_TM_DINNER_INIT, "20:00");
			SharedPreferencesUtils.setSharedPrefValue(getBaseContext(), SharedPreferencesUtils.SP_TM_DINNER_END, "4:59");
		}

		// Delay 3000ms
		thread.start();
	}


	private Thread thread = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (DEBUG) Log.i(TAG,"Delay");
					String userCod = SharedPreferencesUtils.getSharedPrefStringValue(getBaseContext(),
							SharedPreferencesUtils.SP_USER_COD);

					if (userCod !=  null) {
						if (DEBUG) Log.i(TAG,"onCreate - Logged");
						Intent intent = new Intent(getBaseContext(), HomeActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						getBaseContext().startActivity(intent);
					} else {
						if (DEBUG) Log.i(TAG,"onCreate - New install");
						Intent intent = new Intent(getBaseContext(), LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						getBaseContext().startActivity(intent);
					}
				}
			});
		}
	});
}
