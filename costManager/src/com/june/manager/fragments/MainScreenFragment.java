package com.june.manager.fragments;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.june.costmanager.MainActivity;
import com.june.costmanager.R;
import com.june.manager.helpers.db.ItemDataBaseHelper;
import com.june.manager.models.Item;

public class MainScreenFragment extends Fragment {
	
	public static final String TITLE_INCOMINGS = "Incomings";
	public static final String TITLE_OUTCOMINGS = "Outcomings";
	
	private static final int REQUEST_INCOME = 0;
	private static final int REQUEST_OUTCOME = 1;
	
	private Button mButtonIncoming;
	private Button mButtonOutcoming;
	private Button mButtonListIncoming;
	
	private String[] mStringOptions;
	private DrawerLayout mDrawerLayout;
	private ListView mListOptions;
	private CharSequence mTitle, mDrawerTitle;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private ItemDataBaseHelper mItemDBHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mItemDBHelper = new ItemDataBaseHelper(getActivity());
	
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
		
		View v = inflater.inflate(R.layout.fragment_main_screen, parent, false);
		
		mTitle = mDrawerTitle = getActivity().getTitle();
		
		mStringOptions = getResources().getStringArray(R.array.navigation_options);
		mDrawerLayout = (DrawerLayout)v.findViewById(R.id.drawer_layout);
		mListOptions = (ListView)v.findViewById(R.id.left_drawer);
		
		mListOptions.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mStringOptions));
		mListOptions.setOnItemClickListener(new DrawerItemClickListener());

		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActivity().getActionBar().setTitle(mTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().getActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        getActivity().getActionBar().setHomeButtonEnabled(true);
        
       /* @Override
        public boolean onPrepareOptionsMenu(Menu menu) {
            // If the nav drawer is open, hide action items related to the content view
            boolean drawerOpen = mDrawerLayout.isDrawerOpen(mListOptions);
            menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
            return super.onPrepareOptionsMenu(menu);
        }*/
		
		mButtonIncoming = (Button)v.findViewById(R.id.button_incomes);
		mButtonIncoming.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				StartDialogBox(REQUEST_INCOME, TITLE_INCOMINGS);
				
			}
		});
		
		mButtonOutcoming = (Button)v.findViewById(R.id.button_outcomes);
		mButtonOutcoming.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StartDialogBox(REQUEST_OUTCOME, TITLE_OUTCOMINGS);
				
			}
		});

		return v;
	}

	private void StartDialogBox(int request, String title) {
		FragmentManager fm = getActivity().getSupportFragmentManager();
		DialogBoxFragment dialog = DialogBoxFragment.newInstance(title);
		dialog.setTargetFragment(this, request);
		dialog.show(fm, title);
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){

		if(resultCode != Activity.RESULT_OK) {
			return;
		} else {
			Double amount = (Double)data.getSerializableExtra(DialogBoxFragment.AMOUNT);
			String description = (String)data.getSerializableExtra(DialogBoxFragment.DESCRIPTION);

			Item item = new Item();
			
			item.setId(UUID.randomUUID());
			item.setDescription(description);
			item.setDate(new Date());
			
			if (requestCode == REQUEST_INCOME) {
				item.setAmount(amount);
				item.setType(REQUEST_INCOME);
			} else {
				amount *= -1;
				item.setAmount(amount);
				item.setType(REQUEST_OUTCOME);
			}
				
			
			mItemDBHelper.insertItem(item);
		} 
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		FragmentTransaction tx = getActivity().getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.main, new ItemCursorFragment());
        tx.commit();
		mListOptions.setItemChecked(position, true);
	    setTitle(mStringOptions[position]);
	    mDrawerLayout.closeDrawer(mListOptions);
		
	}

	public void setTitle(CharSequence title) {
	    mTitle = title;
	    getActivity().getActionBar().setTitle(mTitle);
	}

}
