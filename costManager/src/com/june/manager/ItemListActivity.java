package com.june.manager;

import android.support.v4.app.Fragment;

import com.june.manager.fragments.ItemCursorFragment;

public class ItemListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new ItemCursorFragment();
	}

}
