package com.ocasoft.drfood.syncrest.requests;

import com.google.gson.JsonObject;

/**
 * Dr Food
 * Created by Alex on 03/09/2015.
 */
public class RegistrationRequest {
	/** Nombre del usuario */
	private String nombre;
	/** Email del usuario */
	private String email;
	/** Password del usuario */
	private String password;

	public RegistrationRequest(String nombre, String email, String password) {
		this.nombre = nombre;
		this.email = email;
		this.password = password;
	}

	public JsonObject getRequest() {
		JsonObject json = new JsonObject();
		json.addProperty("nombre", nombre);
		json.addProperty("email", email);
		json.addProperty("password", password);

		return json;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
