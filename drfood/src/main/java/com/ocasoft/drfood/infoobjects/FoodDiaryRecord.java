package com.ocasoft.drfood.infoobjects;

/**
 * Dr Food
 * Created by Alex on 28/08/2015.
 */
public class FoodDiaryRecord {
	private int id;
	private String date;
	private int foodTimeId;
	private double calories;
	private double fats;
	private double carbohydrates;
	private double proteins;

	public FoodDiaryRecord(String date, int foodTimeId, double calories, double fats, double carbohydrates, double proteins) {
		this.id = -1;
		this.date = date;
		this.foodTimeId = foodTimeId;
		this.calories = calories;
		this.fats = fats;
		this.carbohydrates = carbohydrates;
		this.proteins = proteins;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getFoodTimeId() {
		return foodTimeId;
	}

	public void setFoodTimeId(int foodTimeId) {
		this.foodTimeId = foodTimeId;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public double getFats() {
		return fats;
	}

	public void setFats(double fats) {
		this.fats = fats;
	}

	public double getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public double getProteins() {
		return proteins;
	}

	public void setProteins(double proteins) {
		this.proteins = proteins;
	}
}
