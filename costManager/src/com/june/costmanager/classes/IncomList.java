package com.june.costmanager.classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import android.content.Context;

public class IncomList {
	
	private ArrayList<Incoming> mIncoms;
	private static IncomList sIncomList;
	private Context mAppContext;
	
	private IncomList(Context appContext) {
		mAppContext = appContext;
		mIncoms = new ArrayList<Incoming>();
		
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			Incoming incom = new Incoming();
			incom.setId(UUID.randomUUID());
			incom.setIncom(100.0 + (100.0 - 10.0) * r.nextDouble());
			incom.setIncomDate(new Date());
			mIncoms.add(incom);
		}
	}
	
	public static IncomList get(Context c) {
		if (sIncomList == null) {
			sIncomList = new IncomList(c.getApplicationContext());
		}
		return sIncomList;
	}

	public ArrayList<Incoming> getIncoms() {
		return mIncoms;
	}

	public Incoming getIncom(UUID id) {
		for (Incoming i : mIncoms) {
			if(i.getId().equals(id))
				return i;
		}
		return null;
	}
	
}
