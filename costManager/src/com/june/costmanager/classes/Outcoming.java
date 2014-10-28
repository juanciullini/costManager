package com.june.costmanager.classes;

import java.util.Date;
import java.util.UUID;

public class Outcoming {
	
	private double mOutcom;
	private Date mOutcomDate;
	private UUID mId;
	
	public double getOutcom() {
		return mOutcom;
	}
	public void setOutcom(double outcom) {
		mOutcom = outcom;
	}
	public Date getOutcomDate() {
		return mOutcomDate;
	}
	public void setOutcomDate(Date outcomDate) {
		mOutcomDate = outcomDate;
	}
	public UUID getId() {
		return mId;
	}
	public void setId(UUID id) {
		mId = id;
	}
	
	

}
