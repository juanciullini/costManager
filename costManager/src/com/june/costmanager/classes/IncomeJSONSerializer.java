package com.june.costmanager.classes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

public class IncomeJSONSerializer {
	
	private Context mContext;
	private String mFilename;
	
	public IncomeJSONSerializer(Context context, String filename) {
		super();
		mContext = context;
		mFilename = filename;
	}
	
	public ArrayList<Incoming> loadIncomes ()  throws JSONException, IOException {
		
		ArrayList<Incoming> incomes = new ArrayList<Incoming>();
		BufferedReader reader = null;
		
		try {
			// Open and read the file into a StringBuilder
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				// Line breaks are omitted and irrelevant
				jsonString.append(line);
			}
			// Parse the JSON using JSONTokener
			JSONObject array = (JSONObject) new JSONTokener(jsonString.toString())
			.nextValue();
			
			incomes.add(new Incoming(array));
			
			// Build the array of incomes from JSONObjects
			//for (int i = 0; i < array.length(); i++) {
			//	incomes.add(new Incoming(array.getJSONObject(i)));
			//}
		} catch (FileNotFoundException e) {
			// Ignore this one; it happens when starting fresh
		} catch (Exception e){
			e.printStackTrace();
			
		}finally {
			
			if (reader != null)
			reader.close();
		}
		
		return incomes;
	}
	
	public void saveIncome(Incoming income) throws JSONException, IOException {
		Writer writer = null;
		
		try {
			OutputStream out = mContext.
					openFileOutput (mFilename, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(income.toJson().toString());
			
		} finally {
			if (writer != null)
				writer.close();
		}
	}
	
	public void saveIncomes(ArrayList<Incoming> incomes) throws JSONException, IOException {
		
		JSONArray array = new JSONArray();
		for (Incoming i : incomes)
			array.put(i.toJson());
		
		Writer writer = null;
		
		try {
			OutputStream out = mContext.
					openFileOutput (mFilename, Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
			
		} finally {
			if (writer != null)
				writer.close();
		}
	}
}
