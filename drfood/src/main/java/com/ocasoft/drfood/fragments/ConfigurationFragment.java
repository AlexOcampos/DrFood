package com.ocasoft.drfood.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ocasoft.drfood.R;
import com.ocasoft.drfood.utils.SharedPreferencesUtils;

/**
 * Created by Alex on 24/08/2015.
 */
public class ConfigurationFragment extends Fragment {
	private static final String TAG = "DRFOOD_ConfFrag";
	private static final boolean DEBUG = true;
	private Context mContext;
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_configurations,container,false);
		mContext = rootView.getContext();

		fillUserPreferences(rootView);

		return rootView;
	}

	private void fillUserPreferences(View rootView) {
		// Set user email and name
		String userName = SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_USER_NAME);
		String userEmail = SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_USER_EMAIL);
		EditText userNameET = (EditText) rootView.findViewById(R.id.editTextUserName);
		EditText userEmailET = (EditText) rootView.findViewById(R.id.editTextUserEmail);

		if (userEmail != null && userName != null && userEmailET != null && userNameET != null) {
			userNameET.setText(userName);
			userEmailET.setText(userEmail);
		}

		// If the user modify his name or email we have to update it
		userNameET.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				SharedPreferencesUtils.setSharedPrefValue(mContext,SharedPreferencesUtils.SP_USER_NAME,s.toString());
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});



		userEmailET.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				SharedPreferencesUtils.setSharedPrefValue(mContext,SharedPreferencesUtils.SP_USER_EMAIL,s.toString());
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});

	}
}
