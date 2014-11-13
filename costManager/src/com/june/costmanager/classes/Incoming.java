package com.june.costmanager.classes;

import java.util.Date;
import java.util.UUID;

public class Incoming {
	
	private double mIncom;
	private Date mIncomDate;
	private UUID mId;
	
	public double getIncom() {
		return mIncom;
	}
	public void setIncom(double incom) {
		mIncom = incom;
	}
	public Date getIncomDate() {
		return mIncomDate;
	}
	public void setIncomDate(Date incomDate) {
		mIncomDate = incomDate;
	}
	public UUID getId() {
		return mId;
	}
	public void setId(UUID id) {
		mId = id;
	}
	@Override
	public String toString() {
		return "Amount: "+mIncom+" Date: "+mIncomDate.toString();
	}
	public String getTitle() {
		return "Income" + "D: "+mIncom;
	}
}
