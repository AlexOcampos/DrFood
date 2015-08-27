package com.ocasoft.drfood;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.ocasoft.drfood.contentprovider.FoodContentProvider;
import com.ocasoft.drfood.database.FoodTable;
import com.ocasoft.drfood.database.TrackDiaryTable;
import com.ocasoft.drfood.utils.DateUtils;

import org.w3c.dom.Text;

public class HistoryActivity extends AppCompatActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {
	private static final String TAG = "DRFOOD_History";
	private static final boolean DEBUG = true;

	// The Loader's id (this id is specific to the ListFragment's LoaderManager)
	private static final int LOADER_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		// Initialize a Loader with id '1'. If the Loader with this id already
		// exists, then the LoaderManager will reuse the existing Loader.
		getLoaderManager().initLoader(LOADER_ID, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_history, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**********************/
	/** LOADER CALLBACKS **/
	/**********************/
	/**
	 * Instantiate and return a new Loader for the given ID.
	 *
	 * @param id   The ID whose loader is to be created.
	 * @param args Any arguments supplied by the caller.
	 * @return Return a new Loader instance that is ready to start loading.
	 */
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projectionTrackDiary = {
				TrackDiaryTable.COLUMN_NAME_DIARY_DATE,
				TrackDiaryTable.COLUMN_NAME_DIARY_TIMEFOOD,
				TrackDiaryTable.COLUMN_NAME_DIARY_CALORIES,
				TrackDiaryTable.COLUMN_NAME_DIARY_CARBOHYDRATES,
				TrackDiaryTable.COLUMN_NAME_DIARY_FATS,
				TrackDiaryTable.COLUMN_NAME_DIARY_PROTEINS,
				TrackDiaryTable.COLUMN_NAME_DIARY_USERID
		};
		String mSelectionClause = TrackDiaryTable.COLUMN_NAME_DIARY_USERID + " = ?";
		String[] mSelectionArgs = {
				"1" // UserId
		};
		return new CursorLoader(HistoryActivity.this, FoodContentProvider.CONTENT_URI_DIARY,
				projectionTrackDiary, mSelectionClause, mSelectionArgs, TrackDiaryTable.COLUMN_NAME_DIARY_DATE + " DESC, "
				+ TrackDiaryTable.COLUMN_NAME_DIARY_TIMEFOOD + " ASC");
	}

	/**
	 * Called when a previously created loader has finished its load.  Note
	 * that normally an application is <em>not</em> allowed to commit fragment
	 * transactions while in this call, since it can happen after an
	 * activity's state is saved.  See {@link FragmentManager#beginTransaction()
	 * FragmentManager.openTransaction()} for further discussion on this.
	 * <p/>
	 * <p>This function is guaranteed to be called prior to the release of
	 * the last data that was supplied for this Loader.  At this point
	 * you should remove all use of the old data (since it will be released
	 * soon), but should not do your own release of the data since its Loader
	 * owns it and will take care of that.  The Loader will take care of
	 * management of its data so you don't have to.  In particular:
	 * <p/>
	 * <ul>
	 * <li> <p>The Loader will monitor for changes to the data, and report
	 * them to you through new calls here.  You should not monitor the
	 * data yourself.  For example, if the data is a {@link Cursor}
	 * and you place it in a {@link CursorAdapter}, use
	 * the {@link CursorAdapter#CursorAdapter(Context,
	 * Cursor, int)} constructor <em>without</em> passing
	 * in either {@link CursorAdapter#FLAG_AUTO_REQUERY}
	 * or {@link CursorAdapter#FLAG_REGISTER_CONTENT_OBSERVER}
	 * (that is, use 0 for the flags argument).  This prevents the CursorAdapter
	 * from doing its own observing of the Cursor, which is not needed since
	 * when a change happens you will get a new Cursor throw another call
	 * here.
	 * <li> The Loader will release the data once it knows the application
	 * is no longer using it.  For example, if the data is
	 * a {@link Cursor} from a {@link CursorLoader},
	 * you should not call close() on it yourself.  If the Cursor is being placed in a
	 * {@link CursorAdapter}, you should use the
	 * {@link CursorAdapter#swapCursor(Cursor)}
	 * method so that the old Cursor is not closed.
	 * </ul>
	 *
	 * @param loader The Loader that has finished.
	 * @param data   The data generated by the Loader.
	 */
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		String result = "";
		if (DEBUG) Log.i(TAG, "+++ onLoadFinished() called! +++");

		if (!data.isFirst())
			data.moveToNext();

		do {
			result += "\n" + data.getString(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_DATE))
					+ " # " + data.getString(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_TIMEFOOD))
					+ " # " + data.getString(data.getColumnIndex(TrackDiaryTable.COLUMN_NAME_DIARY_CALORIES));
			if (DEBUG) Log.i(TAG, "+++ onLoadFinished() result = " +  result + " +++");
		} while (data.moveToNext());

		TextView historyTextView = (TextView) findViewById(R.id.historyTextView);
		historyTextView.setText(result);

	}

	/**
	 * Called when a previously created loader is being reset, and thus
	 * making its data unavailable.  The application should at this point
	 * remove any references it has to the Loader's data.
	 *
	 * @param loader The Loader that is being reset.
	 */
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}
}
