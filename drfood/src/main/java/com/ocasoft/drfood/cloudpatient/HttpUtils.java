package com.ocasoft.drfood.cloudpatient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


//import org.apache.http.HttpVersion;
//import org.apache.http.client.HttpClient;
//import org.apache.http.conn.ClientConnectionManager;
//import org.apache.http.conn.scheme.Scheme;
//import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.content.res.Resources;

import com.ocasoft.drfood.R;

/**
 * Clase de utilidad para las conexiones HTTPS
 * @author alex
 *
 */
public class HttpUtils {

//	public static final String SERVICE_URL = "https://oz49.udc.es/";
//	public static final String SERVICE_URL = "http://demo6569069.mockable.io/";
	private static final String AUTENTICATION_SERVICE_URL = "AutenticarUsuario/";
	private static final String REGISTER_URL = "RegistrarUsuario/";
	private static final String EDIT_URL = "EditarUsuario/";
	private static final String SAVE_TRACKING_URL = "GuardarRegistroAlimento";
	
	/** GUID que devuelve la peticion en caso de un error en la autenticacion */
	public static final String GUID_NOT_VALID = "00000000-0000-0000-0000-000000000000";
	
	/**
	 * Metodo que obtiene la url del servicio de autenticacion
	 * @param context
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getAutenticationServiceUrl(Context context) {
		try {
			Resources res = context.getResources();
			String syncUrl = res.getString(R.string.http_sync_url);
			return URLDecoder.decode(syncUrl, "UTF-8") + AUTENTICATION_SERVICE_URL;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Metodo que obtiene la url del servicio de registro
	 * @param context
	 * @return
	 */
	public static String getRegisterServiceUrl(Context context) {
		try {
			Resources res = context.getResources();
			String syncUrl = res.getString(R.string.http_sync_url);
			return URLDecoder.decode(syncUrl, "UTF-8") + REGISTER_URL;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Metodo que obtiene la url del servicio de edicion de usuario
	 * @param context
	 * @return
	 */
	public static String getEditServiceUrl(Context context) {
		try {
			Resources res = context.getResources();
			String syncUrl = res.getString(R.string.http_sync_url);
			return URLDecoder.decode(syncUrl, "UTF-8") + EDIT_URL;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Metodo que obtiene la url del servicio de sincronizacion de ejercicios
	 * @param context
	 * @return
	 */
	public static String getSaveExerciseServiceUrl(Context context) {
		try {
			Resources res = context.getResources();
			String syncUrl = res.getString(R.string.http_sync_url);
			return URLDecoder.decode(syncUrl, "UTF-8") + SAVE_TRACKING_URL;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Metodo que obtiene un cliente http
	 * @param context
	 * @return
	 */
	private static HttpClient getHttpClient(Context context) {
		try {
	    	HttpParams params = new BasicHttpParams();
	        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
			Resources res = context.getResources();
			int syncTimeout = Integer.parseInt(res.getString(R.string.http_sync_timeout));
	        HttpConnectionParams.setConnectionTimeout(params, syncTimeout * 1000);
	        HttpConnectionParams.setSoTimeout(params, syncTimeout * 1000);
	        
	        return new DefaultHttpClient(params);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Metodo que obtiene un cliente https
	 * @param context
	 * @return
	 */
	private static HttpClient getHttpsClient(Context context) {
	     try {
			   X509TrustManager x509TrustManager = new X509TrustManager() { 	           
					@Override
					public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
	 
					@Override
					public void checkServerTrusted(X509Certificate[] chain,String authType) throws CertificateException {}
	 
					@Override
					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
		        };
		        
		        HttpClient client = getHttpClient(context);
		        SSLContext sslContext = SSLContext.getInstance("TLS");
		        sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
		        SSLSocketFactory sslSocketFactory = new ExSSLSocketFactory(sslContext);
		        sslSocketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		        ClientConnectionManager clientConnectionManager = client.getConnectionManager();
		        SchemeRegistry schemeRegistry = clientConnectionManager.getSchemeRegistry();
		        schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
		        return new DefaultHttpClient(clientConnectionManager, client.getParams());
	     } catch (Exception ex) {
	    	 return null;
		 }
	}
	
	/**
	 * Metodo que obtiene el cliente http para la conexion con el servicio Web
	 * @param context
	 * @return
	 */
	public static HttpClient getClient(Context context) {
		if (true) {
			return getHttpsClient(context);
		} else {
			return getHttpClient(context);
		}
	}
}
