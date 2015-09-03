package com.ocasoft.drfood.syncrest.responses;

import java.io.Serializable;

/**
 * Dr Food
 * Created by Alex on 02/09/2015.
 */
public class RegistrationResponse implements Serializable {
	private static final long serialVersionUID = 2L;

	/** Guid del usuario*/
	private String idUsuario;

	public RegistrationResponse() {}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
}
