package com.june.manager.fragments;

import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.june.costmanager.R;
import com.june.manager.models.Item;
import com.june.manager.models.lists.ItemList;

public class ItemFragment extends Fragment {
	
	private Item mItem;
	private TextView mTitle;
	
	public static final String EXTRA_ITEM_ID = "com.june.costmanager.manager.models.item.item_id";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		UUID itemId = (UUID)getArguments().getSerializable(EXTRA_ITEM_ID);	
		mItem = ItemList.get(getActivity()).getItem(itemId);
	
	}
	/**
	 * Within onCreateView(�), you explicitly inflate the fragment�s view by calling
	 * LayoutInflater.inflate(�) and passing in the layout resource ID. The second parameter is your
	 * view�s parent, which is usually needed to configure the widgets properly. The third parameter tells the
	 * layout inflater whether to add the inflated view to the view�s parent. You pass in false because you
	 * will add the view in the activity�s code.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_item, parent, false);
		
		mTitle = (TextView)v.findViewById(R.id.item_TextView);
		mTitle.setText(mItem.toString());

		return v;
	}
	
	public static ItemFragment newInstance(UUID itemId) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_ITEM_ID, itemId);
		
		ItemFragment fragment = new ItemFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	public void returnResult () {
		getActivity().setResult(Activity.RESULT_OK, null);
	}

}
