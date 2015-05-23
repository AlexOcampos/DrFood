package com.ocasoft.drfood.infoobjects;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * TODO: Delete this class (only for test)
 */
public class Food {
	private int id;
	private Date registryDate;
	private String timeMoment;
	private int quantity;
	private int energy;
	private int fats;
	private int proteins;
	private	int carbohydrates;
	private String category;
	private String comments;
	private String unity_measure;
	private int counter;
	private String name;
	private final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

	public Food(int id, String name, String timeMoment, int fats) {
		this.id = id;
		this.name = name;
		this.timeMoment = timeMoment;
		this.fats = fats;
	}

	public Food(int id, Date registryDate, String timeMoment, int quantity,
				int energy, int fats, int proteins, int carbohydrates,
				String category, String comments, String unity_measure, int counter, String name) {
		this.id = id;
		this.registryDate = registryDate;
		this.timeMoment = timeMoment;
		this.quantity = quantity;
		this.energy = energy;
		this.fats = fats;
		this.proteins = proteins;
		this.carbohydrates = carbohydrates;
		this.category = category;
		this.comments = comments;
		this.unity_measure = unity_measure;
		this.counter = counter;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public Date getRegistryDate() {
		return registryDate;
	}

	public String getRegistryDateFormat() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				DATEFORMAT, Locale.getDefault());
		return dateFormat.format(registryDate);
	}

	public String getTimeMoment() {
		return timeMoment;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getEnergy() {
		return energy;
	}

	public int getFats() {
		return fats;
	}

	public int getProteins() {
		return proteins;
	}

	public int getCarbohydrates() {
		return carbohydrates;
	}

	public String getCategory() {
		return category;
	}

	public String getComments() {
		return comments;
	}

	public String getUnity_measure() {
		return unity_measure;
	}

	public int getCounter() {
		return counter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRegistryDate(Date registryDate) {
		this.registryDate = registryDate;
	}

	/**
	 * It sets the registry date.
	 * @param registryDate registryDate in format "yyyy-MM-dd HH:mm:ss"
	 */
	public void setRegistryDate(String registryDate) {
		DateFormat format = new SimpleDateFormat(DATEFORMAT, Locale.getDefault());
		try {
			this.registryDate = format.parse(registryDate);
		} catch (ParseException e) {
			Log.i("FOOD.JAVA", "setRegistryDate : Error parsing Registry Date (Wrong: "
					+ registryDate + ") [" + DATEFORMAT + "]");
		}
	}

	public void setTimeMoment(String timeMoment) {
		this.timeMoment = timeMoment;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public void setFats(int fats) {
		this.fats = fats;
	}

	public void setProteins(int proteins) {
		this.proteins = proteins;
	}

	public void setCarbohydrates(int carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setUnity_measure(String unity_measure) {
		this.unity_measure = unity_measure;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
}
