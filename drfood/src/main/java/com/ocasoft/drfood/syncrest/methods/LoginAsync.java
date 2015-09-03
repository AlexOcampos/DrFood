package com.ocasoft.drfood.syncrest.methods;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.ocasoft.drfood.syncrest.clients.CloudPatientRestClient;
import com.ocasoft.drfood.syncrest.responses.AuthenticationResponse;
import com.ocasoft.drfood.syncrest.utils.HttpUtils;
import com.ocasoft.drfood.utils.SharedPreferencesUtils;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;

/**
 * Dr Food
 * Created by Alex on 30/08/2015.
 */
public class LoginAsync {
	private static final String TAG = "DRFOOD_LoginAsync";
	private static final boolean DEBUG = true; //TODO : Disable DEBUG


	public static boolean doLogin(final Context context, String mEmail, String mPassword) throws JSONException {
		boolean doLogin = false;

		String result = CloudPatientRestClient.get(CloudPatientRestClient.BASE_URL + CloudPatientRestClient.LOGIN_METHOD
				+ mEmail + "/" + mPassword, context, null);
		String response = null;
		try {
			response = HttpUtils.parseResponse(result);

			Gson gsonResponse = new Gson();
			AuthenticationResponse authResp = gsonResponse.fromJson(response, AuthenticationResponse.class);

			if (authResp != null) {
				if (HttpUtils.GUID_NOT_VALID.compareTo(authResp.getIdUsuario()) == 0) {
					// User not valid -> show an error
					if (DEBUG) Log.i(TAG, "+++ onCompleted Login KO +++");
					SharedPreferencesUtils.setSharedPrefValue(context, SharedPreferencesUtils.SP_USER_COD,
							authResp.getIdUsuario());
					doLogin = false;
				} else {
					// User valid -> save data and redirect home
					if (DEBUG) Log.i(TAG, "+++ onCompleted Login OK +++");
					SharedPreferencesUtils.setSharedPrefValue(context, SharedPreferencesUtils.SP_USER_EMAIL,
							authResp.getEmail());
					SharedPreferencesUtils.setSharedPrefValue(context, SharedPreferencesUtils.SP_USER_NAME,
							authResp.getNombre());
					SharedPreferencesUtils.setSharedPrefValue(context, SharedPreferencesUtils.SP_USER_COD,
							authResp.getIdUsuario());
					doLogin = true;
				}
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}


		return doLogin;
	}
}
