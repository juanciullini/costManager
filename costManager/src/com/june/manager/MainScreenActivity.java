package com.june.manager;

import com.june.costmanager.fragments.BalanceFragment;
import com.june.costmanager.fragments.IncomListFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class MainScreenActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new BalanceFragment();
	}

}
