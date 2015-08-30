package com.ocasoft.drfood.cloudpatient.async;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;
//import es.udc.master.tfm.runtracker.bbdd.profiles.Profile;
import com.ocasoft.drfood.cloudpatient.HttpStatusType;
import com.ocasoft.drfood.cloudpatient.HttpUtils;
import com.ocasoft.drfood.cloudpatient.WebCommunicationService;
import com.ocasoft.drfood.cloudpatient.factory.WebServiceFactory;
import com.ocasoft.drfood.cloudpatient.object.AutenticationResponseWS;
import com.ocasoft.drfood.infoobjects.User;

//import es.udc.master.tfm.runtracker.fragments.profile.add.UserDetailsFragment;

/**
 * Clase asincrona encargada de autenticarse contra el servicio web
 * @author alex
 *
 */
public class AutenticationWSAsync extends AsyncTask<String, Void, HttpStatusType> {
	/** Contexto de la aplicacion */
	private Context context;
	/** Dialogo de carga al guardar el ejercicio */
	private ProgressDialog progress;
	/** Usuario que se autentica */
	private User user;
	
	/**
	 * Constructor del comando
	 * @param context
	 * @param user
	 */
	public AutenticationWSAsync(Context context, User user) {
		this.context = context;
		this.user = user;
	}
	
    @Override
    protected void onPreExecute() {
		progress = new ProgressDialog(context);
//		progress.setTitle(context.getString(R.string.progress_dialog_autentication_title));
//		progress.setMessage(context.getString(R.string.progress_dialog_autentication_content));
		progress.setCancelable(false);
		progress.show();
    }
	
	@Override
	protected HttpStatusType doInBackground(String... args) {
        HttpStatusType status = HttpStatusType.AUTHENTICATION_OK;
		try {
			//Se realiza la peticion contra el servicio Web
			WebCommunicationService service = WebServiceFactory.getInstance().getWebCommunicationService(context);
			AutenticationResponseWS response = service.autenticate(user.getEmail(), user.getEnPass());

        	//Se obtiene el codigo de error recibido
        	if (response == null || HttpUtils.GUID_NOT_VALID.equals(response.getIdUsuario()) || response.getCodigoError() != null) {
        		HttpStatusType statusRev = null;
        		if (response.getCodigoError() != null) {
        			statusRev = HttpStatusType.valueOf(response.getCodigoError());
        		}
        		if (statusRev != null) {
        			status = statusRev;
        		} else {
        			status = HttpStatusType.AUTHENTICATION_KO;
        		}
        		
        	} else {
        		user.setAutenticationResponse(response);
        	}
		} catch (ConnectTimeoutException cte) {
			status = HttpStatusType.CONNECTION_TIMEOUT;
        } catch(Exception ex) {
        	Log.e("WebService", "Error en la comunicacion", ex);
        	status = HttpStatusType.AUTHENTICATION_KO;
        }

        return status;
	}

    @Override
    protected void onPostExecute(final HttpStatusType status) {
    	if (progress.isShowing()) {
        	progress.dismiss();
        }
        
    	//Si la autenticacion fue satisfactoria, se recarga la pagina de detalles de perfil con los datos del usuario
    	user.setSyncStatus(status);
    	if (HttpStatusType.AUTHENTICATION_OK.equals(status)) {
//			((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
//			.replace(R.id.container, new UserDetailsFragment()).commit();
    	}
    	
		Toast toast = Toast.makeText(context, status.getTextId(), Toast.LENGTH_SHORT);
		toast.show();
    }
}
