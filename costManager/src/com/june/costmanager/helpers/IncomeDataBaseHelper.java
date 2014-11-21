package com.june.costmanager.helpers;

import java.util.UUID;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.june.costmanager.classes.Incoming;

public class IncomeDataBaseHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "income.sqlite";
	private static final int VERSION = 1;
	
	private static final String TABLE_INCOME = "income";
	private static final String COLUMN_INCOME_ID = "id";
	private static final String COLUMN_INCOME_DATE = "date"; 
	private static final String COLUMN_INCOME_AMOUNT = "amount";
	private static final String COLUMN_INCOME_DESCRIPTION = "description";
	
	public IncomeDataBaseHelper (Context context) {
		super (context, DB_NAME, null, VERSION);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table income (" +
				"id varchar(20) primary key, date varchar(20), "+
				"amount real, description varchar(100))");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
	public long insertIncome (Incoming i) {
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_INCOME_ID, i.getId().toString());
		cv.put(COLUMN_INCOME_DATE, i.getIncomDate());
		cv.put(COLUMN_INCOME_AMOUNT, i.getIncom());
		cv.put(COLUMN_INCOME_DESCRIPTION, i.getTitle());
		return getWritableDatabase().insert(TABLE_INCOME, null, cv);
	}
	
	public IncomeCursor queryRuns() {
		// Equivalent to "select * from run order by start_date asc"
		Cursor wrapped = getReadableDatabase().query(TABLE_INCOME,
		null, null, null, null, null, COLUMN_INCOME_DATE + " asc");
		
		return new IncomeCursor(wrapped);
	}
	
	/**
	* A convenience class to wrap a cursor that returns rows from the "income" table.
	* The {@link getIncome()} method will give you an Income instance representing
	* the current row.
	*/
	public class IncomeCursor extends CursorWrapper {
	
		public IncomeCursor(Cursor c) {
			super(c);
		}
		/**
		* Returns an Incoming object configured for the current row,
		* or null if the current row is invalid.
		*/
		public Incoming getIncome() {
			if (isBeforeFirst() || isAfterLast())
				return null;
			Incoming income = new Incoming();
			
			UUID incomeId = UUID.fromString(getString(getColumnIndex(COLUMN_INCOME_ID)));
			income.setId(incomeId);
			
			String incomDate = getString(getColumnIndex(COLUMN_INCOME_DATE));
			income.setIncomDate(incomDate);
			
			Double amount = getDouble(getColumnIndex(COLUMN_INCOME_AMOUNT));
			income.setIncom(amount);
			
			return income;
		}
	}
}
