package com.ocasoft.drfood.utils;

import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;

/**
 * Created by Alex on 16/08/2015.
 */
public class CursorObserver extends ContentObserver {

	private static String TAG = "LOADER";

	private Loader<Cursor> loader;

	public CursorObserver(Handler handler, Loader<Cursor> loader) {
		super(handler);
		Log.i(TAG, ":::: CursorObserver");

		this.loader = loader;
	}

	@Override
	public boolean deliverSelfNotifications() {
		Log.i(TAG, ":::: deliverSelfNotifications");
		return true;
	}

	@Override
	public void onChange(boolean selfChange) {
		Log.i(TAG, ":::: onChange");

		if (null != loader) {
			loader.onContentChanged();
		}
		super.onChange(selfChange);
	}
}
