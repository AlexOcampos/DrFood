package com.ocasoft.drfood.syncrest.methods;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.ocasoft.drfood.syncrest.clients.CloudPatientRestClient;
import com.ocasoft.drfood.syncrest.requests.RegistrationRequest;
import com.ocasoft.drfood.syncrest.responses.RegistrationResponse;
import com.ocasoft.drfood.syncrest.utils.HttpUtils;
import com.ocasoft.drfood.utils.SharedPreferencesUtils;

import java.io.IOException;
import java.text.ParseException;


/**
 * Dr Food
 * Created by Alex on 30/08/2015.
 */
public class RegistryAsync {
	private static final String TAG = "DRFOOD_RegAsync";
	private static final boolean DEBUG = true; //TODO : Disable DEBUG

	public static boolean doRegistry(final Context context, String mEmail, String mPassword, String mName) {
		boolean doRegistry = false;
		RegistrationRequest request = new RegistrationRequest(mName,mEmail,mPassword);

		String result = CloudPatientRestClient.post(CloudPatientRestClient.BASE_URL
				+ CloudPatientRestClient.SIGNUP_METHOD, context, request);
		String response = null;
		if (DEBUG) Log.i(TAG, "+++ onCompleted Registry : " + result + " +++");
		try {
			response = HttpUtils.parseResponse(result);
			Gson gsonResponse = new Gson();
			RegistrationResponse regResp = gsonResponse.fromJson(response, RegistrationResponse.class);

			if (DEBUG) Log.i(TAG, "+++ onCompleted Registry : " + response + " +++");
			if (regResp == null) {
				if (DEBUG) Log.i(TAG, "+++ onCompleted Registry NULL +++");
			} else {
				if (DEBUG) Log.i(TAG, "+++ onCompleted Registry NOT NULL +++");
			}

			if (regResp != null) {
				if (HttpUtils.GUID_NOT_VALID.compareTo(regResp.getIdUsuario()) == 0) {
					// User not valid -> show an error
					if (DEBUG) Log.i(TAG, "+++ onCompleted Registry KO +++");
					SharedPreferencesUtils.setSharedPrefValue(context, SharedPreferencesUtils.SP_USER_COD,
							regResp.getIdUsuario());
					doRegistry = false;
				} else {
					// User valid -> save data and redirect home
					if (DEBUG) Log.i(TAG, "+++ onCompleted Registry OK +++");
					SharedPreferencesUtils.setSharedPrefValue(context, SharedPreferencesUtils.SP_USER_EMAIL,
							mEmail);
					SharedPreferencesUtils.setSharedPrefValue(context, SharedPreferencesUtils.SP_USER_NAME,
							mName);
					SharedPreferencesUtils.setSharedPrefValue(context, SharedPreferencesUtils.SP_USER_COD,
							regResp.getIdUsuario());
					doRegistry = true;
				}

			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return doRegistry;
	}
}
