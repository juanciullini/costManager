package com.june.manager.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import com.june.costmanager.R;

@SuppressLint("InflateParams") 
public class DialogBoxFragment extends DialogFragment {

	public static final String DIALOG_TITLE = "com.june.manager.fragments.main.title";
	public static final String AMOUNT = "com.june.manager.fragment.models.item.amount";
	public static final String DESCRIPTION = "com.june.manager.fragment.models.item.description";
	private String mTitle;
	private Double mAmount;
	private String mDescription;
	
	@Override
	public Dialog onCreateDialog (Bundle savedInstanceState) {
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_box_item, null);
		
		mTitle = getArguments().getString(DIALOG_TITLE);
		
		final EditText amount = (EditText)v.findViewById(R.id.edit_amount);
		final EditText description = (EditText)v.findViewById(R.id.edit_description);
		
		return new AlertDialog.Builder(getActivity())
		.setView(v)
		.setTitle(mTitle)
		.setPositiveButton(
				android.R.string.ok, 
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mAmount = Double.valueOf(amount.getText().toString());
						mDescription = description.getText().toString();
						sendResult(Activity.RESULT_OK);
					}
				})
		.create();
	}
	
	public static DialogBoxFragment newInstance (String title) {
		Bundle args = new Bundle();
		args.putString(DIALOG_TITLE, title);
		
		DialogBoxFragment fragment = new DialogBoxFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	/**
	 * Send result back to caller activity
	 * @param resultCode
	 */
	private void sendResult (int resultCode) {
		if (getTargetFragment() == null) {
			return;
		}
		
		Intent i = new Intent();
		i.putExtra(AMOUNT, mAmount);
		i.putExtra(DESCRIPTION, mDescription);
	
		getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
		
	}
}
