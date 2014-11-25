package com.unvil.location.clases;

import java.util.Date;
import java.util.UUID;

public class Position {
	
	private Date mStartDate;
	private UUID mId;
	
	public Position() {
		mStartDate = new Date();
		mId = UUID.randomUUID();
	}
	
	public UUID getId () {
		return mId;
	}
	
	public Date getStartDate() {
		return mStartDate;
	}
	
	public void setStartDate(Date startDate) {
		mStartDate = startDate;
	}
	
	public int getDurationSeconds(long endMillis) {
		return (int)((endMillis - mStartDate.getTime()) / 1000);
	}
	
	public static String formatDuration(int durationSeconds) {
		int seconds = durationSeconds % 60;
		int minutes = ((durationSeconds - seconds) / 60) % 60;
		int hours = (durationSeconds - (minutes * 60) - seconds) / 3600;
		return String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

}
