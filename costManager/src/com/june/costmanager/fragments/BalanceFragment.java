package com.june.costmanager.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;

import com.june.costmanager.R;
import com.june.costmanager.classes.Balance;

public class BalanceFragment extends Fragment {
	private Balance mBalance;
	private Button mButtonIncoming;
	private Button mButtonOutcoming;
	
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

		return v;
	}

}
