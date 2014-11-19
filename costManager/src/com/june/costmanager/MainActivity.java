package com.june.costmanager;

import android.support.v4.app.Fragment;

import com.june.costmanager.fragments.BalanceFragment;

public class MainActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new BalanceFragment();
	}

}
