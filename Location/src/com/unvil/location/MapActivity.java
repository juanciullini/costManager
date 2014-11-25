package com.unvil.location;

import android.support.v4.app.Fragment;

import com.unvil.location.fragments.MapFragment;

public class MapActivity extends SingleFragmentActivity {
	/** A key for passing a Loc ID as a long */
	public static final String EXTRA_LOC_ID = "com.unvil.location.loc_id";
	
	@Override
	protected Fragment createFragment() {
		long locId = getIntent().getLongExtra(EXTRA_LOC_ID, -1);
		if (locId != -1) {
			return MapFragment.newInstance(locId);
		} else {
			return new MapFragment();
		}
	}


}
