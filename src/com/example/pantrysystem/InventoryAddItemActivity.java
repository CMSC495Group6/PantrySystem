package com.example.pantrysystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class InventoryAddItemActivity extends ActionBarActivity {
	private ListView listView;
	private ArrayList<Item> itemTypes;
	private ArrayList<String> itemTypeNames;
	private InventoryAccessInterface inventoryAccess;
	private AddNewItemDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory_add_item);
		
		// Initialize database access object.
		inventoryAccess = new InventoryAccessTestImpl();
		
		// Retrieve list of item types.
		itemTypes = inventoryAccess.getItemTypes();
		
		// Extract item type names and add them to a list.
		itemTypeNames = new ArrayList<String>();
		{
			Item tmpItem;
			for (Iterator<Item> i = itemTypes.iterator(); i.hasNext(); ) {
				tmpItem = i.next();
				itemTypeNames.add(tmpItem.getName());
			}
		}
		
		// Set up list view element
		listView = (ListView) findViewById(R.id.add_item_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, itemTypeNames);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new InventoryAddItemClickListener());
	}

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
	
	/** Called when the user clicks on a list item */
	class InventoryAddItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// Retrieve item from item list in the same position as in the name list
			Item item = itemTypes.get(position);
			// Instantiate Add Item dialog
			dialog = new AddNewItemDialog(InventoryAddItemActivity.this, item);
			// Display dialog
			dialog.show();
		}
	}
	
	/** Add Item Dialog class */
	class AddNewItemDialog extends AddItemDialog {
		Item item;
		/** Constructor */
		public AddNewItemDialog(Context context, Item item) {
			super(context);
			this.item = item;
			// Set displayed text
			this.setTitle("Add Item");	//TODO: use string resource
			this.titleText.setText("Add " + item.getName());
			// Set name text field to selected item's name and disable input
			this.nameInput.setText(item.getName());
			this.nameInput.setEnabled(false);
			// Set okButton's text to Add
			this.okButton.setText("Add");	//TODO: use string resource
			// Set Listeners
			this.okButton.setOnClickListener(new AddButtonListener());
			this.cancelButton.setOnClickListener(new CancelButtonListener());
		}
		
		/** Add Button listener */
		class AddButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				// Construct item to be added
				item = new Item(
						AddNewItemDialog.this.nameInput.getText().toString(),
						new Date(),	//TODO: find way to get a Date object from the input field.
						Integer.parseInt(AddNewItemDialog.this.quantityInput.getText().toString()));
				// Add item to database
				inventoryAccess.addItem(item);
				// Close dialog
				AddNewItemDialog.this.dismiss();
			}
		}
		
		/** Cancel Button listener */
		class CancelButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				// Close dialog
				AddNewItemDialog.this.dismiss();
			}
		}
	}
}
