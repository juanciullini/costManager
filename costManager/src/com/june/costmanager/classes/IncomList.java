package com.june.costmanager.classes;

import java.util.ArrayList;
import java.util.UUID;

import com.june.costmanager.helpers.IncomeDataBaseHelper;
import com.june.costmanager.helpers.IncomeDataBaseHelper.IncomeCursor;

import android.content.Context;
import android.util.Log;

public class IncomList {
	
	private static final String TAG = "IncomeList";
	//private static final String FILENAME = "incomes.json";
	
	private ArrayList<Incoming> mIncoms;
	private IncomeJSONSerializer mSerializer;
	
	private IncomeCursor mIncomeCursor;
	
	private static IncomList sIncomList;
	private Context mAppContext;
	
	private IncomList(Context appContext) {
		mAppContext = appContext;
		mIncomeCursor = new IncomeDataBaseHelper(mAppContext).queryRuns();
		
		/**
		 * Loads incomes from a JSon file
		 * mSerializer = new IncomeJSONSerializer(mAppContext, FILENAME);

		
		try {
			mIncoms = mSerializer.loadIncomes();
		} catch (Exception e) {
			mIncoms = new ArrayList<Incoming>();
			Log.e(TAG, "Error loading crimes: ", e);
		}		 */

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
	
	public IncomeCursor getIncomesCursor () {
		return mIncomeCursor;
	}

	public Incoming getIncom(UUID id) {
		for (Incoming i : mIncoms) {
			if(i.getId().equals(id))
				return i;
		}
		return null;
	}
	
	public void setIncome(Incoming incom) {
		mIncoms.add(incom);
	}
	
	public boolean saveIncomes() {
		try {
			mSerializer.saveIncomes(mIncoms);
			Log.d(TAG, "crimes saved to file");
			return true;
		} catch (Exception e) {
			Log.e(TAG, "Error saving crimes: ", e);
			return false;
		}
	}
	
}
