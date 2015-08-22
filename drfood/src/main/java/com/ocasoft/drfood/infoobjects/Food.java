package com.ocasoft.drfood.infoobjects;

import com.ocasoft.drfood.utils.DateUtils;

import java.util.Date;

/**
 * TODO: Delete this class (only for test)
 */
public class Food {
	private int id;
	private Date registryDate;
	private int timeMoment;
	private int quantity; // Default quantity
	private double energy;
	private double fats;
	private double proteins;
	private	double carbohydrates;
	private int category;
	private String comments;
	private String unity_measure;
	private int counter;
	private String name;
	private int trackId;
	private String code;

	public Food() {}

	public Food(int id, Date registryDate, int timeMoment, int quantity,
				double energy, double fats, double proteins, double carbohydrates,
				int category, String comments, String unity_measure, int counter, String name) {
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
		this.code = "1";
	}

	/**
	 * Create a new food
	 * @param id
	 * @param registryDate
	 * @param timeMoment
	 * @param quantity
	 * @param energy
	 * @param fats
	 * @param proteins
	 * @param carbohydrates
	 * @param category
	 * @param comments
	 * @param unity_measure
	 * @param counter
	 * @param name
	 */
	public Food(int id, String registryDate, int timeMoment, int quantity,
				double energy, double fats, double proteins, double carbohydrates,
				int category, String comments, String unity_measure, int counter, String name, String cod) {
		this.id = id;
		this.registryDate = DateUtils.string2Date(registryDate, DateUtils.DATE_FORMAT_DAYMONTHYEAR);
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
		this.code = cod;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getRegistryDate() {
		return registryDate;
	}

	public void setRegistryDate(Date registryDate) {
		this.registryDate = registryDate;
	}

	public int getTimeMoment() {
		return timeMoment;
	}

	public void setTimeMoment(int timeMoment) {
		this.timeMoment = timeMoment;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}

	public double getFats() {
		return fats;
	}

	public void setFats(double fats) {
		this.fats = fats;
	}

	public double getProteins() {
		return proteins;
	}

	public void setProteins(double proteins) {
		this.proteins = proteins;
	}

	public double getCarbohydrates() {
		return carbohydrates;
	}

	public void setCarbohydrates(double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getUnity_measure() {
		return unity_measure;
	}

	public void setUnity_measure(String unity_measure) {
		this.unity_measure = unity_measure;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTrackId() {
		return trackId;
	}

	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}

	public String getCode() {
		return code;
	}

	public void setCod(String cod) {
		this.code = cod;
	}
}
