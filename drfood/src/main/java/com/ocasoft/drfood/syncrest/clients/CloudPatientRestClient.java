package com.ocasoft.drfood.syncrest.clients;


import android.content.Context;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.ocasoft.drfood.syncrest.requests.RegistrationRequest;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import java.security.cert.X509Certificate;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutionException;

/**
 * Dr Food
 * Created by Alex on 30/08/2015.
 */
public class CloudPatientRestClient {
//	public static final String BASE_URL = "https://oz49.udc.es/";
	public static final String BASE_URL = "http://demo5933610.mockable.io/";

	public static final String LOGIN_METHOD = "AutenticarUsuario/";
	public static final String SIGNUP_METHOD = "RegistrarUsuario/";


	public static String get(String url, Context context, FutureCallback<String> futureCallback) {
		Ion ion = Ion.getDefault(context);

		try {
			// Prepare trustManager
			SSLContext sslContext = SSLContext.getInstance("TLS");
			TrustManager x509TrustManager = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			sslContext.init(null, new TrustManager[] { x509TrustManager }, null);


			ion.configure().createSSLContext("TLS");
			ion.getHttpClient().getSSLSocketMiddleware().setSSLContext(sslContext);
			ion.getHttpClient().getSSLSocketMiddleware().setTrustManagers(new TrustManager[] { x509TrustManager });

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		try {
			return ion.with(context)
					.load(url)
					.asString()
					.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
//				.setCallback(futureCallback)
//				.setCallback(futureCallback) // FutureCallback<JsonObject>

		return null;
	}

	public static String post(String url, Context context, RegistrationRequest request) {
		String result = "";

		Ion ion = Ion.getDefault(context);

		try {
			// Prepare trustManager
			SSLContext sslContext = SSLContext.getInstance("TLS");
			TrustManager x509TrustManager = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			sslContext.init(null, new TrustManager[] { x509TrustManager }, null);


			ion.configure().createSSLContext("TLS");
			ion.getHttpClient().getSSLSocketMiddleware().setSSLContext(sslContext);
			ion.getHttpClient().getSSLSocketMiddleware().setTrustManagers(new TrustManager[] { x509TrustManager });

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		}

		try {
			return ion.with(context)
					.load(url)
					.setJsonObjectBody(request.getRequest())
					.asString()
					.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return result;
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return BASE_URL + relativeUrl;
	}
}
