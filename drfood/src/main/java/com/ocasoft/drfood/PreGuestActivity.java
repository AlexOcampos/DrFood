package com.ocasoft.drfood;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ocasoft.drfood.utils.SharedPreferencesUtils;

public class PreGuestActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre_guest);

		Button doneButton = (Button) findViewById(R.id.skip_button);
		doneButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Context context = v.getContext();
				EditText guestName = (EditText) findViewById(R.id.nameGuestET);
				String guestNameStr = guestName.getText().toString();

				EditText guestEmail = (EditText) findViewById(R.id.emailGuestET);
				String guestEmailStr = guestEmail.getText().toString();

				SharedPreferencesUtils.setSharedPrefValue(context,SharedPreferencesUtils.SP_USER_NAME,guestNameStr);
				SharedPreferencesUtils.setSharedPrefValue(context,SharedPreferencesUtils.SP_USER_EMAIL,guestEmailStr);

				Intent intent = new Intent(context, HomeActivity.class);
				context.startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_pre_guest, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
