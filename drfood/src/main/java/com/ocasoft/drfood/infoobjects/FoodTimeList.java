package com.ocasoft.drfood.infoobjects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 28/07/2015.
 */
public class FoodTimeList {
	private List<FoodTime> list;

	public FoodTimeList() {
		list = new ArrayList<FoodTime>();
		addFoodTime(new FoodTime("Breakfast", 1));
		addFoodTime(new FoodTime("Lunch", 2));
		addFoodTime(new FoodTime("Snack", 3));
		addFoodTime(new FoodTime("Dinner", 4));
	}

	/**
	 * Return the FoodTime with the request Id. Null if doesn't exist
	 * @param id the id of the wanted FoodTime
	 * @return the FoodTime. Null if doesn't exist
	 */
	public FoodTime getById(int id) {
		FoodTime result = null;

		for(FoodTime f : list) {
			if (f.getId() == id) {
				result = f;
				break;
			}
		}
		return result;
	}

	/**
	 * Return the FoodTime with the request name. Null if doesn't exist
	 * @param name the name of the wanted FoodTime
	 * @return the FoodTime. Null if doesn't exist
	 */
	public FoodTime getByName(String name) {
		FoodTime result = null;

		for(FoodTime f : list) {
			if (f.getName().compareTo(name) == 0) {
				result = f;
				break;
			}
		}
		return result;
	}

	/**
	 * Add a new foodTime to the list
	 * @param foodTime the new foodTime
	 */
	private void addFoodTime(FoodTime foodTime) {
		list.add(foodTime);
	}

	/**
	 * Convert the list of FoodTimes in a ArrayList<String> with the names of the FoodTimes
	 * @return the ArrayList
	 */
	public ArrayList<String> toArrayList() {
		ArrayList<String> foodTimes = new ArrayList<String>();

		for (FoodTime f : list) {
			foodTimes.add(f.getName());
		}

		return foodTimes;
	}
}
