package com.ocasoft.drfood.cloudpatient;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
 
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
 
 
import org.apache.http.conn.ssl.SSLSocketFactory;
 
/**
 * Clase de utilidad para la conexion https
 * @author alex
 *
 */
public class ExSSLSocketFactory extends SSLSocketFactory {
	private SSLContext sslContext = SSLContext.getInstance("TLS");
	
	/**
	 * Constructor de la clase
	 * @param truststore Key
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 */
	public ExSSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException,
            KeyStoreException, UnrecoverableKeyException {
		super(truststore);
		TrustManager x509TrustManager = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
 
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
 
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
       
        sslContext.init(null, new TrustManager[] { x509TrustManager }, null);
    }
	
	/**
	 * Constructor de la clase
	 * @param context the sslcontext
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 */
    public ExSSLSocketFactory(SSLContext context) throws KeyManagementException, NoSuchAlgorithmException,
			KeyStoreException, UnrecoverableKeyException {
       super(null);
       sslContext = context;
    }

    @Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
			UnknownHostException {
        return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
    }
 
    @Override
    public Socket createSocket() throws IOException {
        return sslContext.getSocketFactory().createSocket();
    }
}