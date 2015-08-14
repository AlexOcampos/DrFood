package com.ocasoft.drfood.infoobjects;

/**
 * Created by Alex on 31/01/2015.
 */
public class FoodTime {
	private String name;
	private int id;

	public FoodTime(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
