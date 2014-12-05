package com.june.costmanager.fragments;

import java.util.UUID;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.june.costmanager.R;
import com.june.costmanager.classes.IncomList;
import com.june.costmanager.classes.Item;

@TargetApi(11)
public class IncomeFragment extends Fragment {
	
	private Item mIncome;
	private TextView mTitle;
	
	public static final String EXTRA_INCOME_ID = "com.june.costmanager.classes.Incoming.incoming_id";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		
		UUID incomeId = (UUID)getArguments().getSerializable(EXTRA_INCOME_ID);	
		mIncome = IncomList.get(getActivity()).getIncom(incomeId);
	
	}
	/**
	 * Within onCreateView(…), you explicitly inflate the fragment’s view by calling
	 * LayoutInflater.inflate(…) and passing in the layout resource ID. The second parameter is your
	 * view’s parent, which is usually needed to configure the widgets properly. The third parameter tells the
	 * layout inflater whether to add the inflated view to the view’s parent. You pass in false because you
	 * will add the view in the activity’s code.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.income_fragment, parent, false);
		
		mTitle = (TextView)v.findViewById(R.id.income_TextView);
		mTitle.setText(mIncome.toString());

		return v;
	}
	
	public static IncomeFragment newInstance(UUID incomeId) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_INCOME_ID, incomeId);
		
		IncomeFragment fragment = new IncomeFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	public void returnResult () {
		getActivity().setResult(Activity.RESULT_OK, null);
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if (NavUtils.getParentActivityName(getActivity()) != null) {
					NavUtils.navigateUpFromSameTask(getActivity());
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
