package com.ocasoft.drfood.infoobjects;

import android.support.annotation.NonNull;
import android.util.Log;

import com.github.mikephil.charting.data.BarEntry;
import com.ocasoft.drfood.utils.DateUtils;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dr Food
 * Created by Alex on 28/08/2015.
 */
public class FoodDiaryRecordList extends AbstractList {
	private static final String TAG = "DRFOOD_DiaRecList";
	private static final boolean DEBUG = true;

	private List<FoodDiaryRecord> list;

	public final static int CALORIES = 0;
	public final static int PROTEINS = 1;
	public final static int FATS = 2;
	public final static int CARBOHYDRATES = 3;

	public FoodDiaryRecordList() {
		list = new ArrayList<FoodDiaryRecord>();
	}

	/**
	 * Returns a count of how many objects this {@code Collection} contains.
	 * <p/>
	 * In this class this method is declared abstract and has to be implemented
	 * by concrete {@code Collection} implementations.
	 *
	 * @return how many objects this {@code Collection} contains, or {@code Integer.MAX_VALUE}
	 * if there are more than {@code Integer.MAX_VALUE} elements in this
	 * {@code Collection}.
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Adds a new element to the list
	 * @param record the new element
	 */
	public void add(FoodDiaryRecord record) {
		list.add(record);
	}

	public void refreshList() {
		HashMap<String,Integer> days = getListDates();

		for (FoodDiaryRecord f : list) {
			String strdate = f.getDate();
			Log.i(TAG, "+++ refreshList=> strdate=" + strdate + " +++ ");
			int id = days.get(strdate);
			Log.i(TAG, "+++ refreshList=> id=" + id + " +++ ");
			f.setId(id);
		}
	}

	public ArrayList<String> getDays() {
		ArrayList<String> result = new ArrayList<>();
		HashMap<String,Integer> days = getListDates();

		Log.i(TAG, "+++ getDays(ArrayList) HASHMAP +++ ");
		for(Map.Entry<String, Integer> f : days.entrySet()) {
			result.add(f.getKey());
			Log.i(TAG, "+++ getDays(HashMap)=> key=" + f.getKey() + " +++ ");
		}

		Log.i(TAG, "+++ getDays(ArrayList) ARRAYLIST +++ ");
		for(String s : result) {
			Log.i(TAG, "+++ getDays(ArrayList)=> key=" + s + " +++ ");
		}

		Collections.sort(result, new Comparator<String>() {
			public int compare(String o1, String o2) {
				if (o1 == null || o2 == null)
					return 0;

				// First order by date desc
				int result = DateUtils.compareDateStrings(o2, o1, DateUtils.DATE_FORMAT_DAYMONTHYEAR);

				return result;
			}
		});


		Log.i(TAG, "+++ getDays(ArrayList) SORTED ARRAYLIST +++ ");
		for(String s : result) {
			Log.i(TAG, "+++ getDays(ArrayList2)=> key=" + s + " +++ ");
		}

//		for (FoodDiaryRecord f : days) {
//			if (!result.contains(f.getDate()))
//				result.add(f.getDate());
//			Log.i(TAG, "+++ getDays current=" + f.getDate() + " +++ ");
//		}

		return result;
	}

	public FoodDiaryRecord getById(int id) {
		for (FoodDiaryRecord f : list) {
			if (f.getId() == id)
				return f;
		}

		return null;
	}

	public ArrayList<BarEntry> getBarEntry(int foodTimeId, int type) {
		ArrayList<BarEntry> valsFoodTime = new ArrayList<BarEntry>();

		for (FoodDiaryRecord f : list) {
			if (foodTimeId == f.getFoodTimeId() && type == CALORIES) {
				valsFoodTime.add(new BarEntry((float) f.getCalories(), f.getId()));
			} else if (foodTimeId == f.getFoodTimeId() && type == CARBOHYDRATES) {
				valsFoodTime.add(new BarEntry((float) f.getCarbohydrates(), f.getId()));
			} else if (foodTimeId == f.getFoodTimeId() && type == FATS) {
				valsFoodTime.add(new BarEntry((float) f.getFats(), f.getId()));
			} else if (foodTimeId == f.getFoodTimeId() && type == PROTEINS) {
				valsFoodTime.add(new BarEntry((float) f.getProteins(), f.getId()));
			}
		}
		return valsFoodTime;
	}

	public HashMap<String,Integer> getListDates() {
		HashMap<String,Integer> result = new HashMap<>();
		int counter = 0;

		FoodDiaryRecord maxFDR = this.max();
		String maxDate = maxFDR.getDate();

		Date currentDate = DateUtils.string2Date(maxDate, DateUtils.DATE_FORMAT_DAYMONTHYEAR);

		FoodDiaryRecord minFDR = this.min();
		String minDate = minFDR.getDate();

		Log.i(TAG, "+++ getListDates=> maxDate=" + maxDate + " ## minDate=" + minDate + " +++ ");


		while (true) {
			String currentDateStr = DateUtils.Date2String(currentDate, DateUtils.DATE_FORMAT_DAYMONTHYEAR);
			result.put(currentDateStr,counter);
			Log.i(TAG, "+++ getListDates=> currentDate = " + currentDateStr + " ##" + counter + "##" + minDate);
			if (currentDateStr.compareTo(minDate) == 0) {
				counter++;
				break;
			}
			currentDate = DateUtils.addDays(currentDate,-1);
			counter++;
		}

		return result;
	}

	/**
	 * Return the maximum FoodDiaryRecord, order by date.
	 * @return the maximum
	 */
	public FoodDiaryRecord max() {
		return Collections.max(list, new Comparator<FoodDiaryRecord>() {
			public int compare(FoodDiaryRecord o1, FoodDiaryRecord o2) {
				if (o1 == null || o2 == null)
					return 0;

				// First order by date desc
				int result = DateUtils.compareDateStrings(o1.getDate(), o2.getDate(), DateUtils.DATE_FORMAT_DAYMONTHYEAR);

				return result;
			}
		});
	}

	/**
	 * Return the minimum FoodDiaryRecord order by date
	 * @return the minimum
	 */
	public FoodDiaryRecord min() {
		return Collections.min(list, new Comparator<FoodDiaryRecord>() {
			public int compare(FoodDiaryRecord o1, FoodDiaryRecord o2) {
				if (o1 == null || o2 == null)
					return 0;
				return DateUtils.compareDateStrings(o1.getDate(), o2.getDate(), DateUtils.DATE_FORMAT_DAYMONTHYEAR);
			}
		});
	}

	public void sort() {
		Collections.sort(list, new Comparator<FoodDiaryRecord>() {
			public int compare(FoodDiaryRecord o1, FoodDiaryRecord o2) {
				if (o1 == null || o2 == null)
					return 0;

				// First order by date desc
				int result = DateUtils.compareDateStrings(o1.getDate(), o2.getDate(), DateUtils.DATE_FORMAT_DAYMONTHYEAR);

				// After order by foodTimeId asc
				if (result == 0) {
					if (o1.getFoodTimeId() == o2.getFoodTimeId())
						return 0;
					else if (o1.getFoodTimeId() > o2.getFoodTimeId())
						return -1;
					else if (o1.getFoodTimeId() < o2.getFoodTimeId())
						return 1;
				}

				return result;
			}
		});
	}
	/**
	 * Returns the element at the specified location in this list.
	 *
	 * @param location the index of the element to return.
	 * @return the element at the specified index.
	 * @throws IndexOutOfBoundsException if {@code location < 0 || location >= size()}
	 */
	@Override
	public Object get(int location) {
		return list.get(location);
	}


}
