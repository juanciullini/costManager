package com.june.costmanager.fragments;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

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
		
		ArrayAdapter<Incoming> adapter = new ArrayAdapter<Incoming>(getActivity(), android.R.layout.simple_list_item_1, mIncomes);
	}
}
