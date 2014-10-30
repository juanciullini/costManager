package com.june.costmanager;

import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;

import com.june.costmanager.classes.Incoming;
import com.june.costmanager.fragments.IncomListFragment;

public class IncomeListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new IncomListFragment();
	}

}
