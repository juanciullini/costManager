package com.june.manager.helpers.db;

import java.util.Date;
import java.util.UUID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.june.manager.models.Item;

public class ItemDataBaseHelper extends SQLiteOpenHelper {

	private String TAG = "Sqlite DB";
	private static final String DB_NAME = "item.sqlite";
	private static final int VERSION = 1;
	
	private static final String TABLE_ITEM = "item";
	private static final String COLUMN_ITEM_ID = "_id";
	private static final String COLUMN_ITEM_DATE = "date"; 
	private static final String COLUMN_ITEM_AMOUNT = "amount";
	private static final String COLUMN_ITEM_DESCRIPTION = "description";
	private static final String COLUMN_ITEM_TYPE = "type";
	
	public ItemDataBaseHelper (Context context) {
		super (context, DB_NAME, null, VERSION);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table item (" +
				"_id varchar(20) primary key, date varchar(20), "+
				"amount real, description varchar(100), type int)");
		Log.i(TAG,"Table income created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	public long insertItem (Item i) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ITEM_ID, i.getId().toString());
		cv.put(COLUMN_ITEM_DATE, i.getDate().getTime());
		cv.put(COLUMN_ITEM_AMOUNT, i.getAmount());
		cv.put(COLUMN_ITEM_DESCRIPTION, i.getDescription());
		cv.put(COLUMN_ITEM_TYPE, i.getType());
		long response = getWritableDatabase().insert(TABLE_ITEM, null, cv); 
		
		if (response > 0) {
			Log.i(TAG,"item line inserted");
			return response;
		} else {
			Log.i(TAG,"item line with problems");
			return response;
		}
	}
	
	public ItemCursor queryRuns() {
		// Equivalent to "select * from run order by start_date asc"
		Cursor wrapped = getReadableDatabase().query(TABLE_ITEM,
		null, null, null, null, null, COLUMN_ITEM_DATE + " asc");
		
		if (wrapped != null) {
			Log.i(TAG,"Query Item table returned Cursors");
		}
		else {
			Log.i(TAG,"Query Item table returned nothing :(");
		}
		return new ItemCursor(wrapped);
	}
	
	/**
	* A convenience class to wrap a cursor that returns rows from the "income" table.
	* The {@link getIncome()} method will give you an Income instance representing
	* the current row.
	*/
	public class ItemCursor extends CursorWrapper {
	
		public ItemCursor(Cursor c) {
			super(c);
		}
		/**
		* Returns an Incoming object configured for the current row,
		* or null if the current row is invalid.
		*/
		public Item getItem() {
			if (isBeforeFirst() || isAfterLast())
				return null;
			Item item = new Item();
			
			UUID itemID = UUID.fromString(getString(getColumnIndex(COLUMN_ITEM_ID)));
			item.setId(itemID);
			
			Long itemDate = getLong(getColumnIndex(COLUMN_ITEM_DATE));
			item.setDate(new Date(itemDate));
			
			Double itemAmount = getDouble(getColumnIndex(COLUMN_ITEM_AMOUNT));
			item.setAmount(itemAmount);
			
			return item;
		}
	}
}
