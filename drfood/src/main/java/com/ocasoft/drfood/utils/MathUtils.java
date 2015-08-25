package com.ocasoft.drfood.utils;

/**
 * Dr Food
 * Created by Alex on 25/08/2015.
 */
public class MathUtils {
	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
}
