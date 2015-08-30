package com.ocasoft.drfood.cloudpatient.factory;

import android.content.Context;
import com.ocasoft.drfood.cloudpatient.WebCommunicationService;

/**
 * Factoria abstracta para obtener el Servicio de comunicacion con la plataforma Web
 * @author alex
 *
 */
public abstract class WebserviceAbstractFactory {

	/**
	 * Metodo que obtiene el servicio para instanciar la comunicacion con la plataforma Web
	 * @param context
	 * @return
	 */
	public abstract WebCommunicationService getWebCommunicationService(Context context);
}
