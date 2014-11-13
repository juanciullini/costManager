package com.june.manager.models;

import java.util.Date;
import java.util.UUID;

public class Item {
	
	private UUID mId;
	private Date mDate;
	private String mDescription;
	private Double mAmount;
	private int mType;

	public UUID getId() {
		return mId;
	}
	public void setId(UUID id) {
		mId = id;
	}
	public Date getDate() {
		return mDate;
	}
	public void setDate(Date date) {
		mDate = date;
	}
	public String getDescription() {
		return mDescription;
	}
	public void setDescription(String description) {
		mDescription = description;
	}
	public Double getAmount() {
		return mAmount;
	}
	public void setAmount(Double amount) {
		mAmount = amount;
	};
		
	public int getType() {
		return mType;
	}
	public void setType(int type) {
		mType = type;
	}

}
