package com.ocasoft.drfood.infoobjects;

import android.content.Context;

import com.ocasoft.drfood.R;

/**
 * Created by Alex on 21/08/2015.
 */
public class FoodCategory {

	public static final int FOODCATEGORY_GREEN 	= 1;
	public static final int FOODCATEGORY_ORANGE = 2;
	public static final int FOODCATEGORY_RED 	= 3;

	private FoodCategory() {}

	/**
	 * Provides the category's name
	 * @param context app context
	 * @param type FOODCATEGORY_GREEN, FOODCATEGORY_ORANGE or FOODCATEGORY_RED
	 * @return the category's name
	 */
	public static String getCategoryName(Context context, int type) {
		String result = "";

		switch (type) {
			case FOODCATEGORY_GREEN:
				result = context.getString(R.string.food_category_green);
				break;
			case FOODCATEGORY_ORANGE:
				result = context.getString(R.string.food_category_orange);
				break;
			case FOODCATEGORY_RED:
				result = context.getString(R.string.food_category_red);
				break;
		}

		return result;
	}
}
