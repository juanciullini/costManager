package com.june.manager.models.lists;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import android.content.Context;

import com.june.manager.models.Item;

public class ItemList {
	
	private ArrayList<Item> mItem;
	private static ItemList sItemList;
	//private Context mAppContext;
	
	private ItemList(Context appContext) {
		//mAppContext = appContext;
		mItem = new ArrayList<Item>();
		
		Random r = new Random();
		for (int i = 0; i < 100; i++) {
			Item item = new Item();
			item.setId(UUID.randomUUID());
			item.setAmount(100.0 + (100.0 - 10.0) * r.nextDouble());
			item.setDate(new Date());
			item.setDescription("description "+i);
			mItem.add(item);
		}
	}
	
	public static ItemList get(Context c) {
		if (sItemList == null) {
			sItemList = new ItemList(c.getApplicationContext());
		}
		return sItemList;
	}

	public ArrayList<Item> getItems() {
		return mItem;
	}

	public Item getItem(UUID id) {
		for (Item i : mItem) {
			if(i.getId().equals(id))
				return i;
		}
		return null;
	}
	
}
