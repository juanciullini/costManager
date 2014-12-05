package com.june.manager.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.june.costmanager.R;
import com.june.costmanager.classes.IncomList;
import com.june.costmanager.helpers.SQLiteCursorLoader;
import com.june.manager.helpers.db.ItemDataBaseHelper.ItemCursor;
import com.june.manager.models.Item;
import com.june.manager.models.ItemType;
import com.june.manager.models.lists.ItemCursorList;

@SuppressLint("NewApi") 
public class ItemCursorFragment extends ListFragment implements LoaderCallbacks<Cursor>{
	private static final String TAG = "ItemsCursorFragment";
	private ItemCursor mItemCursor;
	private static final int REQUEST_ITEM = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.incoming_title);
		
		mItemCursor = ItemCursorList.get(getActivity()).getItemCursor();
		ItemCursorAdapter adapter = new ItemCursorAdapter(getActivity(), mItemCursor);
		
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// set on click item
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mItemCursor.requery();
		((ItemCursorAdapter)getListAdapter()).notifyDataSetChanged();
		Log.d(TAG, "List reload");
	}
	
	@Override
	public void onDestroy() {
		mItemCursor.close();
		super.onDestroy();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode == REQUEST_ITEM) {
			mItemCursor.requery();
			((ItemCursorAdapter)getListAdapter()).notifyDataSetChanged();
		}
		
	}
	

	private static class ItemListCursorLoader extends SQLiteCursorLoader {

		public ItemListCursorLoader(Context context) {
			super(context);
		}
		
		@Override
		protected Cursor loadCursor() {
			return IncomList.get(getContext()).getIncomesCursor();
		}
		
	}
	
	private class ItemCursorAdapter extends CursorAdapter {
		
		private ItemCursor mItemCursor;
		
		public ItemCursorAdapter(Context context, ItemCursor cursor) {
			super(context, cursor, 0);
			mItemCursor = cursor;
		}
		
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.fragment_list_item, parent, false);
		}
		
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the run for the current row
			Item i = mItemCursor.getItem();
			
			// Set up the start date text view
			TextView titleTextView = (TextView)view.findViewById(R.id.itemTitle_list_item_TextView);
			if (i.getType() == ItemType.INCOME)
				titleTextView.setText(ItemType.INCOME_TITLE);
			else
				titleTextView.setText(ItemType.OUTCOME_TITLE);
				
			TextView dateTextView = (TextView)view.findViewById(R.id.item_list_item_TextView);
				dateTextView.setText(i.toString());
				
			ImageView icon = (ImageView)view.findViewById(R.id.item_image);
				icon.setImageResource(R.drawable.ic_launcher);
		
			Log.d(TAG, "Cursor binded");
		}
	}

	@Override
	public void onLoadFinished(android.support.v4.content.Loader<Cursor> arg0, Cursor cursor) {
		// Create an adapter to point at this cursor
				ItemCursorAdapter adapter = new ItemCursorAdapter(getActivity(), (ItemCursor)cursor);
				setListAdapter(adapter);
		
	}

	@Override
	public void onLoaderReset(android.support.v4.content.Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		setListAdapter(null);
	}


	@Override
	public android.support.v4.content.Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
		// TODO Auto-generated method stub
		return new ItemListCursorLoader(getActivity());
	}
}
