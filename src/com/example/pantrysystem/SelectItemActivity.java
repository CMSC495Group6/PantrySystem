package com.example.pantrysystem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectItemActivity extends ItemListActivity {

	/** Called by supertype's initialization code */
	@Override
	protected void setUpListView() {
		listView = (ListView) findViewById(R.id.item_select_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, itemTypeNames);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ItemClickListener());
	}
	
	/** Called when the user clicks on a list item */
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// Retrieve item from item list in the same position as in the name list
			BasicItem item = itemTypes.get(position);
			//TODO: Display prompt asking for quantity
			
		}
	}
	
	//TODO: Return selected item and quantity to calling activity

}
