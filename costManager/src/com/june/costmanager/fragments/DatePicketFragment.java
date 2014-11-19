package com.june.costmanager.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.EditText;

import com.june.costmanager.R;

public class DatePicketFragment extends DialogFragment {

	public static final String DIALOG_TITLE = "com.june.costmanager.clasess.balance.title";
	public static final String BALANCE = "com.june.costmanager.classess.balance";
	private String mTitle;
	private Double mAmount;
	
	private void sendResult (int resultCode) {
		if (getTargetFragment() == null) {
			return;
		}
		
		Intent i = new Intent();
		if (mTitle.equals("income")) {
			i.putExtra(BALANCE, mAmount);
		} else {
			mAmount *= -1;
			i.putExtra(BALANCE, mAmount);
		}
	
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
		
	}
	
	@Override
	public Dialog onCreateDialog (Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_box, null);
		
		mTitle = getArguments().getString(DIALOG_TITLE);
		
		final EditText amount = (EditText)v.findViewById(R.id.edit_amount);
		
		
		return new AlertDialog.Builder(getActivity())
		.setView(v)
		.setTitle(mTitle)
		.setPositiveButton(
				android.R.string.ok, 
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mAmount = Double.valueOf(amount.getText().toString());
						if (mTitle.equals("income"))
							sendResult(1);
						else
							sendResult(2);
					}
				})
		.create();
	}
	
	public static DatePicketFragment newInstance (String title) {
		Bundle args = new Bundle();
		args.putString(DIALOG_TITLE, title);
		
		DatePicketFragment fragment = new DatePicketFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
}
