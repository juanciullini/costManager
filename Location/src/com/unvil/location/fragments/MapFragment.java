package com.unvil.location.fragments;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.unvil.location.R;
import com.unvil.location.managers.LocationReceiver;
import com.unvil.location.managers.PositionManager;

public class MapFragment extends SupportMapFragment {
	private static final String ARG_LOC_ID = "LOC_ID";
	private GoogleMap mGoogleMap;
	private Location mLastLocation;
	
	private BroadcastReceiver mLocationReceiver = new LocationReceiver() {
		
		@Override
		protected void onLocationReceived(Context context, Location loc) {
			mLastLocation = loc;
			if (isVisible())
				updateUI();
		}
		
		@Override
		protected void onProviderEnabledChanged(boolean enabled) {
			int toastText = enabled ? R.string.gps_enabled : R.string.gps_disabled;
			Toast.makeText(getActivity(), toastText, Toast.LENGTH_LONG).show();
		}
	};
	
	public static MapFragment newInstance(long locId) {
		Bundle args = new Bundle();
		args.putLong(ARG_LOC_ID, locId);
		MapFragment mf = new MapFragment();
		mf.setArguments(args);
		return mf;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, parent, savedInstanceState);
		// Stash a reference to the GoogleMap
		mGoogleMap = getMap();
		// Show the user's location
		mGoogleMap.setMyLocationEnabled(true);
		return v;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		getActivity().registerReceiver(mLocationReceiver,
				new IntentFilter(PositionManager.ACTION_LOCATION));
		}
	
	@Override
	public void onStop() {
		getActivity().unregisterReceiver(mLocationReceiver);
		super.onStop();
	}
	
	public void updateUI() {
		// Set up an overlay on the map for this run's locations
		// Create a polyline with all of the points
		PolylineOptions line = new PolylineOptions();
		
		// Also create a LatLngBounds so you can zoom to fit
		LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
		Location loc = mLastLocation;
		LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());
			
		MarkerOptions startMarkerOptions = new MarkerOptions()
			.position(latLng)
			.title("Marker: "+mLastLocation.getTime())
			.snippet("lat: "+ Double.toString(loc.getLatitude()) + " Long: "+ Double.toString(loc.getLatitude()));
		mGoogleMap.addMarker(startMarkerOptions);
		
		line.add(latLng);
		latLngBuilder.include(latLng);
		
		// Add the polyline to the map
		mGoogleMap.addPolyline(line);
		
		/*// Make the map zoom to show the track, with some padding
		// Use the size of the current display in pixels as a bounding box
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		
		// Construct a movement instruction for the map camera
		LatLngBounds latLngBounds = latLngBuilder.build();
		@SuppressWarnings("deprecation")
		CameraUpdate movement = CameraUpdateFactory.newLatLngBounds(latLngBounds,
		display.getWidth(), display.getHeight(), 15);
		mGoogleMap.moveCamera(movement);*/
	}
}

