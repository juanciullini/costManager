package com.june.manager.fragments;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.june.costmanager.IncomeActivity;
import com.june.costmanager.R;
import com.june.costmanager.classes.IncomList;
import com.june.costmanager.classes.Incoming;
import com.june.costmanager.fragments.IncomeFragment;
import com.june.manager.models.Item;
import com.june.manager.models.lists.ItemList;

@SuppressLint("NewApi") 
public class ItemListFragment extends ListFragment {
	private ArrayList<Item> mItems;
	private static final String TAG = "ItemsListFragment";
	private static final int REQUEST_ITEM = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.incoming_title);
		mItems = ItemList.get(getActivity()).getItems();
		
		ItemAdapter adapter = new ItemAdapter(mItems);
		
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Item item = ((ItemAdapter)(getListAdapter())).getItem(position);
		// start new incoming class
		Intent i = new Intent(getActivity(), IncomeActivity.class);
		i.putExtra(ItemFragment.EXTRA_ITEM_ID, item.getId());
		startActivityForResult(i, REQUEST_ITEM);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		((ItemAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == REQUEST_ITEM) {
			// Handle result
			}
		
	}
	

	private class ItemAdapter extends ArrayAdapter<Item> {
		
		public ItemAdapter(ArrayList<Item> items) {
			super(getActivity(), 0, items);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			// If we weren't given a view, inflate one
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater()
						.inflate(R.layout.fragment_list_item, null);
			}
			Item i = getItem(position);
			TextView titleTextView =
				(TextView)convertView.findViewById(R.id.itemTitle_list_item_TextView);
			titleTextView.setText(i.getDescription());
			
			TextView dateTextView =
				(TextView)convertView.findViewById(R.id.item_list_item_TextView);
			dateTextView.setText(i.getAmount().toString()+" done "+i.getDate().toString());
			
			ImageView icon =
					(ImageView)convertView.findViewById(R.id.balance_image);
			
			icon.setImageResource(R.drawable.ic_launcher);
			
			return convertView;
		}
	}
}
