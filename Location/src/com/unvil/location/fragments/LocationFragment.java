package com.unvil.location.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.unvil.location.R;
import com.unvil.location.clases.Position;
import com.unvil.location.managers.LocationReceiver;
import com.unvil.location.managers.PositionManager;

public class LocationFragment extends Fragment {
	
	private Button mStartButton, mStopButton;
	private TextView mStartedTextView, mLatitudeTextView,
	mLongitudeTextView, mAltitudeTextView, mDurationTextView;
	private Location mLastLocation;
	private PositionManager mPositionManager;
	private Position mPosition;
	
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		mPositionManager = PositionManager.get(getActivity());
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_location, container, false);
		mStartedTextView = (TextView)view.findViewById(R.id.run_startedTextView);
		mLatitudeTextView = (TextView)view.findViewById(R.id.run_latitudeTextView);
		mLongitudeTextView = (TextView)view.findViewById(R.id.run_longitudeTextView);
		mAltitudeTextView = (TextView)view.findViewById(R.id.run_altitudeTextView);
		mDurationTextView = (TextView)view.findViewById(R.id.run_durationTextView);
		
		mStartButton = (Button)view.findViewById(R.id.run_startButton);
		mStartButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPositionManager.startLocationUpdates();
				mPosition = new Position();
				updateUI();
				
			}
		});
		mStopButton = (Button)view.findViewById(R.id.run_stopButton);
		mStartButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mPositionManager.stopLocationUpdates();
				updateUI();
				
			}
		});
		updateUI();
		
		return view;
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
	
	private void updateUI() {
		boolean started = mPositionManager.isTrackingRun();
		
		if (mPosition != null)
			mStartedTextView.setText(mPosition.getStartDate().toString());
			int durationSeconds = 0;
			if (mPosition != null && mLastLocation != null) {
				durationSeconds = mPosition.getDurationSeconds(mLastLocation.getTime());
				mLatitudeTextView.setText(Double.toString(mLastLocation.getLatitude()));
				mLongitudeTextView.setText(Double.toString(mLastLocation.getLongitude()));
				mAltitudeTextView.setText(Double.toString(mLastLocation.getAltitude()));
			}
			mDurationTextView.setText(Position.formatDuration(durationSeconds));
		
		mStartButton.setEnabled(!started);
		mStopButton.setEnabled(started);
	}

}
