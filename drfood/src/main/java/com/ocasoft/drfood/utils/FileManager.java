package com.ocasoft.drfood.utils;


import android.content.Context;
import android.util.Log;

import com.ocasoft.drfood.infoobjects.Food;

import org.apache.commons.lang3.StringEscapeUtils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 21/08/2015.
 */
public class FileManager {
	private static final String TAG = "DRFOOD_FileManager";
	private static final boolean DEBUG = false; //TODO : Disable DEBUG

	private static final String COLUMN_SEPARATOR = ";";

	public FileManager() {

	}

	public static List<Food> LoadText(Context context, int resourceId) {
		// The InputStream opens the resourceId and sends it to the buffer
		InputStream is = context.getResources().openRawResource(resourceId);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String readLine = null;
		List<Food> db = new ArrayList<Food>();

		try {
			if (DEBUG) Log.i(TAG, ":::: LoadText");
			// While the BufferedReader readLine is not null
			while ((readLine = br.readLine()) != null) {
				// Lines that starts with # are comments. Ignore them
				if ("#".compareTo(readLine.substring(0, 1)) == 0) {
					continue;
				}

				if (DEBUG) Log.i(TAG, StringEscapeUtils.unescapeJava(readLine));

				// Split the columns of the line
				String[] array = readLine.split(COLUMN_SEPARATOR);

				// Check number of columns
				if (array.length != 14) {
					Log.e(TAG, "Not enough columns in line: " + StringEscapeUtils.unescapeJava(readLine));
					continue;
				}

				// ================ Parse columns ================
				/*
					int id, String registryDate, int timeMoment, int quantity,
					double energy, double fats, double proteins, double carbohydrates,
					String category, String comments, String unity_measure, int counter, String name
				 */
				if (DEBUG) Log.i(TAG,"************ PARSE COLUMNS ************");
				// id = int
				int id = Integer.parseInt(array[0]);
				if (DEBUG) Log.i(TAG,"id = " + id);

				// registryDate = String (DateUtils.DATE_FORMAT_DAYMONTHYEAR == dd/MM/yyyy)
				String registryDate = array[1];
				if (DEBUG) Log.i(TAG,"registryDate = " + registryDate);

				// timeMoment = int (FoodTimeList.TIMEMOMENT_*)
				int timeMoment = Integer.parseInt(array[2]);
				if (DEBUG) Log.i(TAG,"timeMoment = " + timeMoment);

				// quantity = int (gr)
				int quantity = Integer.parseInt(array[3]);
				if (DEBUG) Log.i(TAG,"quantity = " + quantity);

				// energy = double (kcal)
				Double energy = Double.parseDouble(array[4]);
				if (DEBUG) Log.i(TAG,"energy = " + energy);

				// fats = double (gr)
				Double fats = Double.parseDouble(array[5]);
				if (DEBUG) Log.i(TAG,"fats = " + fats);

				// proteins = double (gr)
				Double proteins = Double.parseDouble(array[6]);
				if (DEBUG) Log.i(TAG,"proteins = " + proteins);

				// carbohydrates = double (gr)
				Double carbohydrates = Double.parseDouble(array[7]);
				if (DEBUG) Log.i(TAG,"carbohydrates = " + carbohydrates);

				// category = int (FoodCategory.FOODCATEGORY_*)
				int category = Integer.parseInt(array[8]);
				if (DEBUG) Log.i(TAG,"category = " + category);

				// comments = string
				String comments = array[9];
				if (DEBUG) Log.i(TAG,"comments = " + comments);

				// unity_measure = string
				String unity_measure = array[10];
				if (DEBUG) Log.i(TAG,"unity_measure = " + unity_measure);

				// counter = int -> 0 (default value)
				int counter = Integer.parseInt(array[11]);
				if (DEBUG) Log.i(TAG,"counter = " + counter);

				// name = string (codigo alimento)
				String name = array[12];
				if (DEBUG) Log.i(TAG,"name = " + name);

				// cod = string (codigo alimento)
				String cod = array[13];
				if (DEBUG) Log.i(TAG,"cod = " + cod);

				db.add(new Food(id,registryDate,timeMoment,quantity,energy,fats,proteins,carbohydrates,
						category,comments,unity_measure,counter,name,cod));

			}

			// Close the InputStream and BufferedReader
			is.close();
			br.close();

		} catch (IOException e) {
			Log.e(TAG, ":::: LoadText - Error reading file");
			e.printStackTrace();
		}

		return db;
	}
}
