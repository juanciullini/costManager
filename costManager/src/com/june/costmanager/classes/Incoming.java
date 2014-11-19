package com.june.costmanager.classes;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

public class Incoming {
	
	private static final String JSON_AMOUNT = "amount";
	private static final String JSON_DATE = "date";
	private static final String JSON_ID = "id";
	
	private double mIncom;
	private String mIncomDate;
	private UUID mId;
	
	public Incoming() {
		mId = UUID.randomUUID();
	}
	
	public Incoming (JSONObject json) throws JSONException {
		mId = UUID.fromString(json.getString(JSON_ID));
		if (json.has(JSON_AMOUNT))
			mIncom = json.getDouble(JSON_AMOUNT);
		mIncomDate = json.getString(JSON_DATE);

	}
	
	public double getIncom() {
		return mIncom;
	}
	public void setIncom(double incom) {
		mIncom = incom;
	}
	public String getIncomDate() {
		return mIncomDate;
	}
	public void setIncomDate(String incomDate) {
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
	public JSONObject toJson() throws JSONException{
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_AMOUNT, String.valueOf(mIncom));
		json.put(JSON_DATE, mIncomDate);
		return json;
	}
}
