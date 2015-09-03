package com.ocasoft.drfood.syncrest.utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;

/**
 * Dr Food
 * Created by Alex on 31/08/2015.
 */
public class HttpUtils {
	/** GUID que devuelve la peticion en caso de un error en la autenticacion */
	public static final String GUID_NOT_VALID = "00000000-0000-0000-0000-000000000000";

	/**
	 * Metodo que parsea la respuesta del servicio Web y lo transforma en JSON
	 * @param response
	 * @return
	 */
	public static String parseResponse(String response) throws ParseException, IOException {
		String respStr = response.replace("\\", "");

		if (!respStr.startsWith("{") && !respStr.endsWith("}")) {
			respStr = respStr.substring(1,respStr.length()-1);
		}
		return respStr;
	}
}
