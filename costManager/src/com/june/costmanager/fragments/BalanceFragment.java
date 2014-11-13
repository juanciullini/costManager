package com.june.costmanager.fragments;

import java.util.Date;
import java.util.UUID;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.june.costmanager.IncomeListActivity;
import com.june.costmanager.R;
import com.june.costmanager.classes.Balance;
import com.june.costmanager.classes.IncomList;
import com.june.costmanager.classes.Incoming;

public class BalanceFragment extends Fragment {
	private Balance mBalance;
	private Button mButtonIncoming;
	private Button mButtonOutcoming;
	private Button mButtonList;
	
	private static final String DIALOG_INCOME = "income";
	private static final String DIALOG_OUTCOME = "outcome";
	private static final int REQUEST_INCOME = 0;
	private static final int REQUEST_OUTCOME = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBalance = new Balance();
	
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
		View v = inflater.inflate(R.layout.fragment_main, parent, false);
		
		mButtonIncoming = (Button)v.findViewById(R.id.button_incoming);
		mButtonOutcoming = (Button)v.findViewById(R.id.button_outcoming);
		mButtonList = (Button)v.findViewById(R.id.button_list);
		
		mButtonList.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), IncomeListActivity.class);
				startActivity(i);
				
			}
		});
		
		mButtonIncoming.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePicketFragment dialog = DatePicketFragment.newInstance(DIALOG_INCOME);
				dialog.setTargetFragment(BalanceFragment.this, REQUEST_INCOME);
				dialog.show(fm, DIALOG_INCOME);
			}
		});
		
		mButtonOutcoming.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePicketFragment dialog = DatePicketFragment.newInstance(DIALOG_OUTCOME);
				dialog.setTargetFragment(BalanceFragment.this, REQUEST_OUTCOME);
				dialog.show(fm, DIALOG_OUTCOME);
			}
		});

		return v;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(resultCode == 1) {
			Incoming income = new Incoming();
			Double amount = (Double)data.getSerializableExtra(DatePicketFragment.BALANCE);
			income.setId(UUID.randomUUID());
			income.setIncom(amount);
			income.setIncomDate(new Date());
			IncomList.get(getActivity()).setIncome(income);
		} else {
			return;
		}
	}
}
