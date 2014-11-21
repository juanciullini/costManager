package com.june.costmanager.fragments;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.june.costmanager.R;
import com.june.costmanager.classes.OutcomList;
import com.june.costmanager.classes.Outcoming;

@SuppressLint("NewApi") 
public class OutcomeListFragment extends ListFragment {
	private ArrayList<Outcoming> mOutcome;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.outcoming_title);
		mOutcome = OutcomList.get(getActivity()).getOutcoms();
		mOutcome.add(null);
	}

}
