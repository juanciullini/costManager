package com.june.costmanager.classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import android.content.Context;

public class OutcomList {
	
	private ArrayList<Outcoming> mOutcoms;
	private static OutcomList sOutcomList;
	
	private OutcomList(Context appContext) {
		mOutcoms = new ArrayList<Outcoming>(); 
		
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			Outcoming outcome = new Outcoming();
			outcome.setOutcom(50.0 + (50.0 - 2.0) * r.nextDouble());
			outcome.setOutcomDate(new Date());
			mOutcoms.add(outcome);
		}
	}
	
	public static OutcomList get(Context c) {
		if (sOutcomList == null) {
			sOutcomList = new OutcomList(c.getApplicationContext());
		}
		return sOutcomList;
	}

	public ArrayList<Outcoming> getOutcoms() {
		return mOutcoms;
	}

	public Outcoming getIncom(UUID id) {
		for (Outcoming i : mOutcoms) {
			if(i.getId().equals(id))
				return i;
		}
		return null;
	}
}
