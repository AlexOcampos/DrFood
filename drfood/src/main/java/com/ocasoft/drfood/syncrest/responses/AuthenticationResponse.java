package com.ocasoft.drfood.syncrest.responses;

import java.io.Serializable;

/**
 * Dr Food
 * Created by Alex on 01/09/2015.
 */
public class AuthenticationResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Guid del usuario*/
	private String idUsuario;
	/** Nombre del usuario */
	private String nombre;
	/** Email del usuario */
	private String email;

	public AuthenticationResponse() {}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the idUsuario
	 */
	public String getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AutenticationResponse [idUsuario=" + idUsuario + ", nombre="
				+ nombre + ", email=" + email + "]";
	}
}

