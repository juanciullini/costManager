package com.june.manager;

import android.support.v4.app.Fragment;

import com.june.manager.fragments.MainScreenFragment;

public class MainScreenActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new MainScreenFragment();
	}

}
