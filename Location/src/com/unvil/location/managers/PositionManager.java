package com.unvil.location.managers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;

public class PositionManager {

	private static final String TAG = "PositionManager";
	public static final String ACTION_LOCATION = "com.unvil.location.managers.ACTION_LOCATION";
	private static PositionManager sPositionManager;
	private Context mAppContext;
	private LocationManager mLocationManager;
	
	// The private constructor forces users to use RunManager.get(Context)
	private PositionManager(Context appContext) {
		mAppContext = appContext;
		mLocationManager = (LocationManager)mAppContext
				.getSystemService(Context.LOCATION_SERVICE);
	}
	
	public static PositionManager get(Context c) {
		if (sPositionManager == null) {
			// Use the application context to avoid leaking activities
			sPositionManager = new PositionManager(c.getApplicationContext());
			Log.i(TAG, "PositionManager Created");
		}
		
		return sPositionManager;
	}
	
	private PendingIntent getLocationPendingIntent(boolean shouldCreate) {
		Intent broadcast = new Intent(ACTION_LOCATION);
		int flags = shouldCreate ? 0 : PendingIntent.FLAG_NO_CREATE;
	
		return PendingIntent.getBroadcast(mAppContext, 0, broadcast, flags);
	}
		
	public void startLocationUpdates() {
		String provider = LocationManager.GPS_PROVIDER;
		
		// Start updates from the location manager
		PendingIntent pi = getLocationPendingIntent(true);
		mLocationManager.requestLocationUpdates(provider, 0, 0, pi);
		Log.i(TAG, "Location Update Started");
	}
	
	public void stopLocationUpdates() {
		PendingIntent pi = getLocationPendingIntent(false);
		if (pi != null) {
			mLocationManager.removeUpdates(pi);
			pi.cancel();
		}
	}
	
	public boolean isTrackingRun() {
		return getLocationPendingIntent(false) != null;
	}
}

