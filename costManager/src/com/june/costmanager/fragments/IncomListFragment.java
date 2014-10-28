package com.june.costmanager.fragments;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ListFragment;
import android.os.Bundle;

import com.june.costmanager.R;
import com.june.costmanager.classes.IncomList;
import com.june.costmanager.classes.Incoming;

@SuppressLint("NewApi") 
public class IncomListFragment extends ListFragment {
	private ArrayList<Incoming> mIncomes;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.incoming_title);
		mIncomes = IncomList.get(getActivity()).getIncoms();
	}
}
