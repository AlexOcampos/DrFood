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
	private static final boolean DEBUG = true; //TODO : Disable DEBUG
	private static String TAG = "DRFOOD_CursorObserver";

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
