package com.unvil.location;

import android.support.v4.app.Fragment;

import com.unvil.location.fragments.LocationFragment;


public class MainActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new LocationFragment();
	}


}
