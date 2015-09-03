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

		// =================== FOOD TIMES
		String morningInit 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_MORNING_INIT);
		String morningEnd 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_MORNING_END);
		String lunchInit 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_LUNCH_INIT);
		String lunchEnd 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_LUNCH_END);
		String snackInit 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_SNACK_INIT);
		String snackEnd 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_SNACK_END);
		String dinnerInit 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_DINNER_INIT);
		String dinnerEnd 	= SharedPreferencesUtils.getSharedPrefStringValue(mContext, SharedPreferencesUtils.SP_TM_DINNER_END);

		EditText morningInitET 	= (EditText) rootView.findViewById(R.id.editTextMorningInit);
		EditText morningEndET 	= (EditText) rootView.findViewById(R.id.editTextMorningEnd);
		EditText lunchInitET 	= (EditText) rootView.findViewById(R.id.editTextLunchInit);
		EditText lunchEndET 	= (EditText) rootView.findViewById(R.id.editTextLunchEnd);
		EditText snackInitET 	= (EditText) rootView.findViewById(R.id.editTextSnackInit);
		EditText snackEndET 	= (EditText) rootView.findViewById(R.id.editTextSnackEnd);
		EditText dinnerInitET 	= (EditText) rootView.findViewById(R.id.editTextDinnerInit);
		EditText dinnerEndET 	= (EditText) rootView.findViewById(R.id.editTextDinnerEnd);

		morningInitET.setText(morningInit);
		morningEndET.setText(morningEnd);
		lunchInitET.setText(lunchInit);
		lunchEndET.setText(lunchEnd);
		snackInitET.setText(snackInit);
		snackEndET.setText(snackEnd);
		dinnerInitET.setText(dinnerInit);
		dinnerEndET.setText(dinnerEnd);

		morningInitET.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				SharedPreferencesUtils.setSharedPrefValue(mContext, SharedPreferencesUtils.SP_TM_MORNING_INIT, s.toString());
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		morningEndET.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				SharedPreferencesUtils.setSharedPrefValue(mContext,SharedPreferencesUtils.SP_TM_MORNING_END,s.toString());
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		lunchInitET.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				SharedPreferencesUtils.setSharedPrefValue(mContext,SharedPreferencesUtils.SP_TM_LUNCH_INIT,s.toString());
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		lunchEndET.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				SharedPreferencesUtils.setSharedPrefValue(mContext,SharedPreferencesUtils.SP_TM_LUNCH_END,s.toString());
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		snackInitET.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				SharedPreferencesUtils.setSharedPrefValue(mContext,SharedPreferencesUtils.SP_TM_SNACK_INIT,s.toString());
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		snackEndET.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				SharedPreferencesUtils.setSharedPrefValue(mContext,SharedPreferencesUtils.SP_TM_SNACK_END,s.toString());
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		dinnerInitET.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				SharedPreferencesUtils.setSharedPrefValue(mContext,SharedPreferencesUtils.SP_TM_DINNER_INIT,s.toString());
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
		dinnerEndET.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				SharedPreferencesUtils.setSharedPrefValue(mContext,SharedPreferencesUtils.SP_TM_DINNER_END,s.toString());
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});
	}
}
