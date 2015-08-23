package com.ocasoft.drfood.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by alex on 23/08/15.
 */
public class AboutFragment extends ListFragment{

	ArrayList<String> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		list = new ArrayList<>();

		for (int i = 0;i< 50 ;i++) {
			list.add("Item "+ (i+1));
		}
		setListAdapter(new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, list));
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();

	}
}