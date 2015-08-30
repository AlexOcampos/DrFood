package com.ocasoft.drfood.cloudpatient.factory;

import android.content.Context;
import com.ocasoft.drfood.cloudpatient.WebCommunicationService;
import com.ocasoft.drfood.cloudpatient.WebCommunicationServiceImpl;

/**
 * Factoria concreta que obtiene la interfaz de comunicacion con la plataforma Web
 * @author alex
 *
 */
public class WebServiceFactory extends WebserviceAbstractFactory {

	/** Unica instancia de la factoria */
	private static WebServiceFactory instance = null;
	
	/** Metodo que crea la factoria */
	private synchronized static void createInstance() {
		if (instance == null) {
			instance = new WebServiceFactory();
		}
	}
	
	/**
	 * Metodo que obtiene la factoria
	 * @return
	 */
	public static WebServiceFactory getInstance() {
		if (instance == null) {
			createInstance();
		}
		return instance;
	}

	@Override
	public WebCommunicationService getWebCommunicationService(Context context) {
		return new WebCommunicationServiceImpl(context);
	}

}
