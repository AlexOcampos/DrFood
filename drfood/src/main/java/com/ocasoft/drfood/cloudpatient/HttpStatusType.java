package com.ocasoft.drfood.cloudpatient;
import com.ocasoft.drfood.R;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerado con los posibles errores de comunicacion con el servicio web
 * @author alex
 *
 */
public enum HttpStatusType {

	/** No se ha sincronizado con la plataforma */
	NOT_SYNC(0, R.string.http_status_not_sync),
	/** Codigo lanzado debido a que el usuario no existe */
	USER_NOT_VALID(1, R.string.http_status_user_not_valid),
	/** Codigo lanzado debido a que la contraseña no existe */
	PASSWORD_NOT_VALID(2, R.string.http_status_password_not_valid),
	/** Codigo lanzado debido a un error en la conexion */
	CONNECTION_TIMEOUT(50, R.string.http_status_connection_timeout),
	/** Codigo lanzado cuando la autenticacion se realiza de forma correcta */
	AUTHENTICATION_OK(51, R.string.http_status_authentication_ok),
	/** Codigo lanzado cuando la autenticacion no es satisfactoria */
	AUTHENTICATION_KO(52, R.string.http_status_authentication_ko),
	/** Codigo lanzado cuando el registro de realiza de forma correcta */
	REGISTRATION_OK(53, R.string.http_status_registration_ok),
	/** Codigo lanzado cuando el registro no es satisfactorio */
	REGISTRATION_KO(54, R.string.http_status_registration_ko),
	/** Codigo lanzado cuando la edicion se realiza de forma correcta */
	EDIT_OK(55, R.string.http_status_edit_ok),
	/** Codigo lanzado cuando la edicion no es satisfactoria */
	EDIT_KO(56, R.string.http_status_edit_ko),
	/** Codigo lanzado cuando la sincronizacion de una comida es satisfactoria */
	SYNC_TRACKING_OK(57, R.string.http_sync_tracking_ok),
	/** Codigo lanzado cuando la sincronizacion de una comida no es satisfactoria */
	SYNC_TRACKING_KO(58, R.string.http_sync_tracking_ko),
	;
	
	/** Lista de estados OK*/
	public static List<HttpStatusType> STATUSES_OK = Arrays.asList(AUTHENTICATION_OK, REGISTRATION_OK, EDIT_OK,
			SYNC_TRACKING_OK);
	
	/** Lista de estados KO */
	public static List<HttpStatusType> STATUSES_KO = Arrays.asList(AUTHENTICATION_KO, REGISTRATION_KO, EDIT_KO,
			CONNECTION_TIMEOUT, PASSWORD_NOT_VALID, SYNC_TRACKING_KO);
	
	private int code;
	
	private int textId;
	
	/** Constructor */
	private HttpStatusType(int code, int textId) {
		this.code = code;
		this.textId = textId;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the textId
	 */
	public int getTextId() {
		return textId;
	}
	
	/**
	 * Metodo que obtiene el codigo de error
	 * @param id
	 * @return
	 */
	public static HttpStatusType valueOf(int id) {
		HttpStatusType[] codes = values();
		for (int i = 0; i < codes.length; i++) {
			if (codes[i].getCode() == id) {
				return codes[i]; 
			}
		}
		return null;
	}
}
