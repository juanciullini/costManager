package com.june.manager.models.lists;

import android.content.Context;
import android.util.Log;

import com.june.manager.helpers.db.ItemDataBaseHelper;
import com.june.manager.helpers.db.ItemDataBaseHelper.ItemCursor;



public class ItemCursorList {
	
	private static final String TAG = "ItemCursorList";
	private ItemCursor mItemCursor;
	private static ItemCursorList sItemList;
	private Context mAppContext;
	
	private ItemCursorList(Context appContext) {
		mAppContext = appContext;
		mItemCursor = new ItemDataBaseHelper(mAppContext).queryRuns();
		Log.i(TAG, "items in cursor from db");
	}
	
	public static ItemCursorList get(Context c) {
		if (sItemList == null) {
			sItemList = new ItemCursorList(c.getApplicationContext());
		}
		return sItemList;
	}

	public ItemCursor getItemCursor () {
		return mItemCursor;
	}

}
