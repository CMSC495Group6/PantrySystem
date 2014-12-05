package com.example.pantrysystem;

import java.util.Date;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class InventoryActivity extends ActionBarActivity {
	static final int ADD_ITEM = 1;
	
	private ListView listView;
	private ItemAdapter adapter;
	private Dialog dialog;
	private InventoryAccessInterface inventoryAccess;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);
		
		// Initialize database access object.
		inventoryAccess = new InventoryAccessTestImpl();
		
		// Set up list view element
		listView = (ListView) findViewById(R.id.inventory_list);
		adapter = new ItemAdapter(this, inventoryAccess.getStockedItems());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new InventoryItemClickListener());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// Update list view
		updateInventory();
	}
	
	private void updateInventory() {
		adapter.updateItems(inventoryAccess.getStockedItems());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inventory, menu);
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
	
	/*-------------------------------------------------------------------------
	 * Code for handling the Add Item button
	 */
	
	/** Called when the user clicks the Add Item button */
	public void addItem(View view) {
		Intent intent = new Intent(this, InventoryAddItemActivity.class);
		// Ensure that any of the following dialogs can't be navigated back to.
		//TODO: decide if this is actually necessary
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ADD_ITEM) {
			if (resultCode == RESULT_OK) {
				
			}
		}
	}
	
	/*-------------------------------------------------------------------------
	 * Code for handling the Add New Item button
	 */
	
	/** Called when the user clicks the Add New Item button */
	public void addNewItem(View view) {
		// Instantiate Add New Item dialog
		dialog = new AddNewItemDialog(InventoryActivity.this);
		// Display dialog
		dialog.show();
	}
	
	/** Add New Item Dialog class */
	class AddNewItemDialog extends AddItemDialog {
		Item item;
		/** Constructor */
		public AddNewItemDialog(Context context) {
			super(context);
			// Set displayed text
			this.setTitle("Add New Item");	//TODO: use string resource
			this.titleText.setText("Add New Item");
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
				// Update list view
				updateInventory();
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
	
	/*-------------------------------------------------------------------------
	 * Code for handling the item context menu
	 */
	
	/** Called when the user clicks on a list item */
	class InventoryItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// Instantiate item edit dialog
			dialog = new ItemContextDialog(InventoryActivity.this, (Item) listView.getItemAtPosition(position), inventoryAccess);
			// Display dialog
			dialog.show();
		}
	}
	
	class ItemContextDialog extends Dialog {
		private Item item;
		private Button addButton;
		private Button removeButton;
		private Button modifyButton;
		private Button deleteButton;
		private Button cancelButton;
		
		public ItemContextDialog(Context context, Item item, InventoryAccessInterface inventoryAccess) {
			super(context);
			this.setContentView(R.layout.dialog_inventory_item_selected);
			
			this.item = item;
			this.setTitle(item.getName());
			// Set up button listeners
			addButton = (Button) this.findViewById(R.id.inventory_item_selected_add_button);
			addButton.setOnClickListener(new AddItemButtonListener());
			removeButton = (Button) this.findViewById(R.id.inventory_item_selected_remove_button);
			removeButton.setOnClickListener(new RemoveItemButtonListener());
			modifyButton = (Button) this.findViewById(R.id.inventory_item_selected_modify_button);
			modifyButton.setOnClickListener(new ModifyItemButtonListener());
			deleteButton = (Button) this.findViewById(R.id.inventory_item_selected_delete_button);
			deleteButton.setOnClickListener(new DeleteItemButtonListener());
			cancelButton = (Button) this.findViewById(R.id.inventory_item_selected_cancel_button);
			cancelButton.setOnClickListener(new CancelButtonListener());
		}

		/** Listener for the "Add" button. */
		class AddItemButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				inventoryAccess.addItem(item);
				// Update list view
				updateInventory();
				ItemContextDialog.this.dismiss();
			}
		}
		
		/** Listener for the "Remove" button. */
		class RemoveItemButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				inventoryAccess.removeItem(item);
				// Update list view
				updateInventory();
				ItemContextDialog.this.dismiss();
			}
		}
		
		/** Listener for the "Modify" button. */
		class ModifyItemButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				inventoryAccess.modifyItem(item);
				// Update list view
				updateInventory();
				ItemContextDialog.this.dismiss();
			}
		}
		
		/** Listener for the "Delete" button. */
		class DeleteItemButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				inventoryAccess.deleteItem(item);
				// Update list view
				updateInventory();
				ItemContextDialog.this.dismiss();
			}
		}
		
		/** Listener for the "Cancel" button. */
		class CancelButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				ItemContextDialog.this.dismiss();
			}
		}
	}
}
