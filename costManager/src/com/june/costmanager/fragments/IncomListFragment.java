package com.june.costmanager.fragments;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.june.costmanager.R;
import com.june.costmanager.classes.IncomList;
import com.june.costmanager.classes.Incoming;

@SuppressLint("NewApi") 
public class IncomListFragment extends ListFragment {
	private ArrayList<Incoming> mIncomes;
	private static final String TAG = "IncomeListFragment";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.incoming_title);
		mIncomes = IncomList.get(getActivity()).getIncoms();
		
		IncomeAdapter adapter = new IncomeAdapter(mIncomes);
		
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Incoming income = ((IncomeAdapter)(getListAdapter())).getItem(position);
		Log.i(TAG, income.toString());
	}
	

	private class IncomeAdapter extends ArrayAdapter<Incoming> {
		
		public IncomeAdapter(ArrayList<Incoming> incomes) {
			super(getActivity(), 0, incomes);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// If we weren't given a view, inflate one
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater()
						.inflate(R.layout.list_item, null);
			}
			// Configure the view for this Crime
			Incoming i = getItem(position);
			TextView titleTextView =
				(TextView)convertView.findViewById(R.id.incomeTitle_list_item_TextView);
			titleTextView.setText(i.getTitle());
			
			TextView dateTextView =
				(TextView)convertView.findViewById(R.id.income_list_item_TextView);
			dateTextView.setText(i.toString());
			
			ImageView icon =
					(ImageView)convertView.findViewById(R.id.balance_image);
			
			icon.setImageResource(R.drawable.ic_launcher);
			
			return convertView;
		}
	}
}
