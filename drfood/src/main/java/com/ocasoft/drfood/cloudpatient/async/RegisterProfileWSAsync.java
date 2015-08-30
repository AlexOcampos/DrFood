package com.ocasoft.drfood.cloudpatient.async;

import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;
import com.ocasoft.drfood.R;
//import es.udc.master.tfm.runtracker.bbdd.factory.DatabaseFactory;
//import es.udc.master.tfm.runtracker.bbdd.profiles.Profile;
//import es.udc.master.tfm.runtracker.bbdd.profiles.ProfileDAO;
import com.ocasoft.drfood.cloudpatient.HttpStatusType;
import com.ocasoft.drfood.cloudpatient.HttpUtils;
import com.ocasoft.drfood.cloudpatient.WebCommunicationService;
import com.ocasoft.drfood.cloudpatient.factory.WebServiceFactory;
import com.ocasoft.drfood.cloudpatient.object.RegistrationResponseWS;
import com.ocasoft.drfood.infoobjects.User;

//import es.udc.master.tfm.runtracker.fragments.profile.ProfileFragment;

/**
 * Clase asincrona encargada de registrarse contra el servicio web
 * @author alex
 *
 */
public class RegisterProfileWSAsync extends AsyncTask<String, Void, HttpStatusType> {
	/** Contexto de la aplicacion */
	private Context context;
	/** Dialogo de carga al guardar el ejercicio */
	private ProgressDialog progress;
	/** Usuario que se autentica */
	private User user;
	/** Indica si es necesario cerrar la actividad actual */
	private boolean syncronization;
	
	/**
	 * Constructor del comando
	 * @param context
	 * @param user
	 */
	public RegisterProfileWSAsync(Context context, User user, boolean syncronization) {
		this.context = context;
		this.user = user;
		this.syncronization = syncronization;
	}
	
    @Override
    protected void onPreExecute() {
		progress = new ProgressDialog(context);
		if (syncronization) {
//			progress.setTitle(context.getString(R.string.progress_dialog_sync_profile_title));
//			progress.setMessage(context.getString(R.string.progress_dialog_sync_profile_content));
		} else {
//			progress.setTitle(context.getString(R.string.progress_dialog_registration_title));
//			progress.setMessage(context.getString(R.string.progress_dialog_registration_content));
		}

		progress.setCancelable(false);
		progress.show();
    }
	
	@Override
	protected HttpStatusType doInBackground(String... params) {
        HttpStatusType status = HttpStatusType.REGISTRATION_OK;
		try {	
			//Se realiza la peticion contra el servicio Web
			WebCommunicationService service = WebServiceFactory.getInstance().getWebCommunicationService(context);
			RegistrationResponseWS response = service.register(user.getEmail(), user.getEnPass(),
					user.getRegistrationRequest());

			//Se obtiene el codigo de error recibido
        	if (HttpUtils.GUID_NOT_VALID.equals(response.getIdUsuario()) || response.getCodigoError() != null) {
        		HttpStatusType statusRev = null;
        		if (response.getCodigoError() != null) {
        			statusRev = HttpStatusType.valueOf(response.getCodigoError());
        		}
        		if (statusRev != null) {
        			status = statusRev;
        		} else {
        			status = HttpStatusType.REGISTRATION_KO;
        		}
        		
        	} else {
        		user.setGuid(response.getIdUsuario());
        	}
		} catch (ConnectTimeoutException cte) {
			status = HttpStatusType.CONNECTION_TIMEOUT;
        } catch(Exception ex) {
        	Log.e("WebService", "Error en la comunicacion", ex);
        	status = HttpStatusType.REGISTRATION_KO;
        }

        return status;
	}
	
    @Override
    protected void onPostExecute(final HttpStatusType status) {
    	if (progress.isShowing()) {
        	progress.dismiss();
        }
    	
    	///Si el registro es de una sincronizacion se modifica el codigo
    	HttpStatusType finishStatus = status;
		if (syncronization) {
			if (HttpStatusType.STATUSES_OK.contains(status)) {
				finishStatus = HttpStatusType.SYNC_TRACKING_OK;
			} else {
				finishStatus = HttpStatusType.SYNC_TRACKING_KO;
			}
		}
    	
    	//Se actualiza el Guid del usuario
    	user.setSyncStatus(finishStatus);
//    	ProfileDAO profileHelper = DatabaseFactory.getInstance().getProfileDAO(context);
//		profileHelper.update(profile);
		
		//Se muestra un mensaje informativo
		Toast toast = Toast.makeText(context, finishStatus.getTextId(), Toast.LENGTH_SHORT);
		toast.show();
		
		//Se refrescan la lista de perfiles
		if (syncronization) {
//	    	List<Fragment> fragments = ((FragmentActivity)context).getSupportFragmentManager().getFragments();
//	    	for (Fragment fragment : fragments) {
//				if (fragment instanceof ProfileFragment)  {
//					ProfileFragment profileFragment = (ProfileFragment) fragment;
//					profileFragment.refreshProfiles();
//					return;
//				}
//			}
		} else {
			//Se finaliza la actividad actual
			((FragmentActivity)context).finish();
		}
    }
}
