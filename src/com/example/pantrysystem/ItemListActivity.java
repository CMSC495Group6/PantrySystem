package com.example.pantrysystem;

import java.util.ArrayList;
import java.util.Iterator;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

abstract public class ItemListActivity extends ActionBarActivity {
	protected ListView listView;
	protected ArrayList<BasicItem> itemTypes;
	protected ArrayList<String> itemTypeNames;
	protected InventoryAccessInterface inventoryAccess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_item);
		
		// Get list of item names
		inventoryAccess = new InventoryAccessTestImpl();
		itemTypes = inventoryAccess.getItemTypes();
		itemTypeNames = new ArrayList<String>();
		{
			BasicItem tmpItem;
			// Extract item names for list of item types
			for (Iterator<BasicItem> i = itemTypes.iterator(); i.hasNext(); ) {
				tmpItem = i.next();
				itemTypeNames.add(tmpItem.getName());
			}
		}
		// Set up list view element to display item names
		setUpListView();
	}
	
	abstract protected void setUpListView();

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inventory_add_item, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
