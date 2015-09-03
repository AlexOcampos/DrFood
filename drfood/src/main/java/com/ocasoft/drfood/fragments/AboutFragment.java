package com.ocasoft.drfood.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ocasoft.drfood.R;

import java.util.ArrayList;

/**
 * Created by alex on 23/08/15.
 */
public class AboutFragment extends Fragment {

	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_help,container,false);

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

	}
}