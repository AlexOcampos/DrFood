package com.ocasoft.drfood;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ocasoft.drfood.syncrest.methods.RegistryAsync;

import org.json.JSONException;

public class SignUpActivity extends AppCompatActivity {
	private static final String TAG = "DRFOOD_SignUpActivity";
	private static final boolean DEBUG = true; //TODO : Disable DEBUG

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserRegistryTask mRegTask = null;

	private EditText mEmailView;
	private EditText mPassword1View;
	private EditText mPassword2View;
	private EditText mNameView;
	private View mProgressView;
	private View mSignUpFormView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		mNameView = (EditText) findViewById(R.id.nameSignUpET);
		mEmailView = (EditText) findViewById(R.id.emailSignUpET);
		mPassword1View = (EditText) findViewById(R.id.passwordSignUpET1);
		mPassword2View = (EditText) findViewById(R.id.passwordSignUpET2);

		Button mEmailSignInButton = (Button) findViewById(R.id.signup_signup_button);
		mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptRegister();
			}
		});

		mSignUpFormView = findViewById(R.id.signup_form);
		mProgressView = findViewById(R.id.registry_progress);

	}

	/**
	 * Attempts to register the account specified by the signup form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptRegister() {
		// Reset errors.
		mNameView.setError(null);
		mEmailView.setError(null);
		mPassword1View.setError(null);
		mPassword2View.setError(null);

		// Store values at the time of the login attempt.
		String name = mNameView.getText().toString();
		String email = mEmailView.getText().toString();
		String password1 = mPassword1View.getText().toString();
		String password2 = mPassword2View.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password, if the user entered one.
		if (!TextUtils.isEmpty(password1) && !isPasswordValid(password1)) {
			mPassword1View.setError(getString(R.string.signup_error_invalid_password1));
			focusView = mPassword1View;
			cancel = true;
		}

		// Check for equal passwords
		if (!TextUtils.isEmpty(password2) && password2.compareTo(password1)!=0) {
			mPassword2View.setError(getString(R.string.signup_error_invalid_password2));
			focusView = mPassword2View;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(email)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!isEmailValid(email)) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		// Check for a valid name
		if (TextUtils.isEmpty(name)) {
			mNameView.setError(getString(R.string.error_field_required));
			focusView = mNameView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			showProgress(true);
			mRegTask = new UserRegistryTask(this, email, password1, name);
			mRegTask.execute((Void) null);
		}
	}

	private boolean isEmailValid(String email) {
		return email.contains("@");
	}

	private boolean isPasswordValid(String password) {
		return password.length() > 0;
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
			mSignUpFormView.animate().setDuration(shortAnimTime).alpha(
					show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
				}
			});

			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mProgressView.animate().setDuration(shortAnimTime).alpha(
					show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
				}
			});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
			mSignUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserRegistryTask extends AsyncTask<Void, Void, Boolean> {

		private final String mEmail;
		private final String mPassword;
		private final String mName;
		private Activity activity;

		UserRegistryTask(Activity activity, String email, String password, String name) {
			this.activity = activity;
			mEmail = email;
			mName = name;
			mPassword = password;
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			boolean result = false;

			result = RegistryAsync.doRegistry(activity.getBaseContext(), mEmail, mPassword, mName);

			return result;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mRegTask = null;
			showProgress(false);

			if (success) {
				Intent intent = new Intent(activity, HomeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				activity.getApplicationContext().startActivity(intent);
			} else {
				mEmailView.setError(getString(R.string.signup_error_invalid_email));
				mEmailView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mRegTask = null;
			showProgress(false);
		}
	}

}
