package com.june.costmanager.fragments;

import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.june.costmanager.IncomePagerActivity;
import com.june.costmanager.R;
import com.june.costmanager.classes.IncomList;
import com.june.costmanager.classes.Incoming;
import com.june.costmanager.helpers.IncomeDataBaseHelper.IncomeCursor;
import com.june.costmanager.helpers.SQLiteCursorLoader;

@TargetApi(11)
@SuppressLint("NewApi") 
public class IncomListFragment extends ListFragment implements LoaderCallbacks<Cursor> {
	//private ArrayList<Incoming> mIncomes;
	//private IncomeCursor mIncomesCursor;
	//private static final String TAG = "IncomeListFragment";
	private static final int REQUEST_INCOME = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		getActivity().setTitle(R.string.incoming_title);
		
		/*mIncomesCursor = IncomList.get(getActivity()).getIncomesCursor();
		IncomeCursorAdapter adapter = new IncomeCursorAdapter(getActivity(), mIncomesCursor);*/
		
		/*mIncomes = IncomList.get(getActivity()).getIncoms();
		IncomeAdapter adapter = new IncomeAdapter(mIncomes);*/
		
		//setListAdapter(adapter);
		
		getLoaderManager().initLoader(0, null, this);
	}

	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Incoming income = ((IncomeAdapter)(getListAdapter())).getItem(position);
		// start new incoming class
		Intent i = new Intent(getActivity(), IncomePagerActivity.class);
		i.putExtra(IncomeFragment.EXTRA_INCOME_ID, income.getId());
		startActivityForResult(i, REQUEST_INCOME);
	}
	
	/*	
	 * using array list as income
	 * @Override
	public void onResume() {
		super.onResume();
		((IncomeAdapter)getListAdapter()).notifyDataSetChanged();
	}*/
	
	@Override
	public void onResume() {
		super.onResume();
		//((IncomeCursorAdapter)getListAdapter()).notifyDataSetChanged();
		getLoaderManager().restartLoader(0, null, this);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if(resultCode == REQUEST_INCOME) {
			// Ahora lo voy a hacer solo para incoming, para ver como guarda
/*			Incoming income = new Incoming();
			
			Double amount = (Double)data.getSerializableExtra(DatePicketFragment.BALANCE);
			income.setIncom(amount);
			income.setIncomDate((new Date()).toString());

			//realizar en el onPause de la lista de income	
			IncomList.get(getActivity()).setIncome(income);
			((IncomeAdapter)getListAdapter()).notifyDataSetChanged();*/
			
			Incoming income = new Incoming();
			
			Double amount = (Double)data.getSerializableExtra(DatePicketFragment.BALANCE);
			income.setIncom(amount);
			income.setIncomDate((new Date()).toString());
			
		} else {
			return;
		}
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.income_list_menu, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				if (NavUtils.getParentActivityName(getActivity()) != null) {
					NavUtils.navigateUpFromSameTask(getActivity());
				}
				return true;
			case R.id.menu_item_show_subtitle:
				getActivity().getActionBar().setSubtitle(R.string.show_subtitle);
				return true;
			case R.id.menu_income_list:
				FragmentManager fm = getActivity().getSupportFragmentManager();
				DatePicketFragment dialog = DatePicketFragment.newInstance("income");
				dialog.setTargetFragment(IncomListFragment.this, REQUEST_INCOME);
				dialog.show(fm, "income");
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private class IncomeAdapter extends ArrayAdapter<Incoming> {
		
		public IncomeAdapter(ArrayList<Incoming> incomes) {
			super(getActivity(), 0, incomes);
		}
		
		@SuppressLint("InflateParams") @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// If we weren't given a view, inflate one
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater()
						.inflate(R.layout.list_item, null);
			}
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

	private static class IncomeListCursorLoader extends SQLiteCursorLoader {

		public IncomeListCursorLoader(Context context) {
			super(context);
		}
		
		@Override
		protected Cursor loadCursor() {
			return IncomList.get(getContext()).getIncomesCursor();
		}
		
	}
	
	private class IncomeCursorAdapter extends CursorAdapter {
		
		private IncomeCursor mIncomeCursor;
		
		public IncomeCursorAdapter(Context context, IncomeCursor cursor) {
			super(context, cursor, 0);
			mIncomeCursor = cursor;
		}
		
		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			// Use a layout inflater to get a row view
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			return inflater.inflate(R.layout.list_item, parent, false);
		}
		
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			// Get the run for the current row
			Incoming i = mIncomeCursor.getIncome();
			
			// Set up the start date text view
			TextView titleTextView = (TextView)view.findViewById(R.id.incomeTitle_list_item_TextView);
				titleTextView.setText(i.getTitle());
				
			TextView dateTextView = (TextView)view.findViewById(R.id.income_list_item_TextView);
				dateTextView.setText(i.toString());
				
			ImageView icon = (ImageView)view.findViewById(R.id.balance_image);
				icon.setImageResource(R.drawable.ic_launcher);
		
		}
		
		/*@SuppressLint("InflateParams") @Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// If we weren't given a view, inflate one
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater()
						.inflate(R.layout.list_item, null);
				
			}
			Incoming i = mIncomeCursor.getIncome();
			
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
			
		}*/

		
	}

	@Override
	public void onLoadFinished(android.support.v4.content.Loader<Cursor> arg0,
			Cursor cursor) {
		// Create an adapter to point at this cursor
				IncomeCursorAdapter adapter = new IncomeCursorAdapter(getActivity(), (IncomeCursor)cursor);
				setListAdapter(adapter);
		
	}

	@Override
	public void onLoaderReset(android.support.v4.content.Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		setListAdapter(null);
	}


	@Override
	public android.support.v4.content.Loader<Cursor> onCreateLoader(int arg0,
			Bundle arg1) {
		// TODO Auto-generated method stub
		return new IncomeListCursorLoader(getActivity());
	}

}
