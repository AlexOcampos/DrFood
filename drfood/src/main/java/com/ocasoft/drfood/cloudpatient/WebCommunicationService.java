package com.ocasoft.drfood.cloudpatient;

import com.ocasoft.drfood.cloudpatient.object.AutenticationResponseWS;
import com.ocasoft.drfood.cloudpatient.object.EditRequestWS;
import com.ocasoft.drfood.cloudpatient.object.EditResponseWS;
import com.ocasoft.drfood.cloudpatient.object.ExerciseResponseWS;
import com.ocasoft.drfood.cloudpatient.object.ProfileExerciseWS;
import com.ocasoft.drfood.cloudpatient.object.RegistrationRequestWS;
import com.ocasoft.drfood.cloudpatient.object.RegistrationResponseWS;

import java.io.IOException;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;


/**
 * Interfaz de acceso para la sincronizacion con el WebService
 * @author hugo
 *
 */
public interface WebCommunicationService {

	/**
	 * Metodo para autenticar un perfil con la plataforma Web
	 * @param login
	 * @param enPassword
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @return
	 */
	public AutenticationResponseWS autenticate (String login, String enPassword) throws ClientProtocolException,
			IOException;
	
	/**
	 * Metodo para registrar un perfil contra la plataforma Web
	 * @param login
	 * @param enPassword
	 * @param requestContent
	 * @throws ParseException
	 * @throws IOException
	 * @return
	 */
	public RegistrationResponseWS register (String login, String enPassword, RegistrationRequestWS requestContent)
			throws ParseException, IOException;
	
	/**
	 * Metodo para editar un perfil en la plataforma Web
	 * @param guid
	 * @param requestContent
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @return
	 */
	public EditResponseWS edit (String guid, EditRequestWS requestContent) throws ClientProtocolException, IOException;
	
	/**
	 * Metodo para registrar un ejercicio en la plataforma Web de un perfil en concreto
	 * @param guid
	 * @param requestContent
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @return
	 */
	public ExerciseResponseWS saveExercise(String guid, ProfileExerciseWS requestContent)
			throws ClientProtocolException, IOException;
}
