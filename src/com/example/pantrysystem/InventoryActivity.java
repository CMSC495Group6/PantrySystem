/** InventoryActivity class
 * 
 *  Activity class implementing all of the inventory view's behavior.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/01/2014
 *  Created InventoryActivity class and implemented the item context menu.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/03/2014
 *  The Add Item button now starts the add item activity.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/05/2014
 *  Added the Add New Item dialog.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Added dialogs for the item context menu options; some code adjustments to
 *  reflect the changes to the Item class.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/10/2014
 *  Re-did the item list context menu to use an actual context menu, and
 *  replaced some string literals with references to string resources.
 *  - Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

import java.text.ParseException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;

public class InventoryActivity extends ActionBarActivity {
	static final int ADD_ITEM = 1;
	
	private ListView listView;
	private FullItemAdapter adapter;
	private Dialog dialog;
	private InventoryAccessInterface inventoryAccess;
	private FullItem selectedItem;
	private DateAssistentInterface dateAssistent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inventory);
		// Initialize database access object.
		inventoryAccess = new InventoryAccessTestImpl();
		// Set up list view element
		listView = (ListView) findViewById(R.id.inventory_list);
		adapter = new FullItemAdapter(this, inventoryAccess.getInventory());
		listView.setAdapter(adapter);
		//listView.setOnItemClickListener(new InventoryItemClickListener());
		registerForContextMenu(listView);
		// Initialize other variables
		selectedItem = null;
		dateAssistent = DateAssistent.getInstance();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// Update list view
		updateInventory();
	}
	
	private void updateInventory() {
		adapter.updateItems(inventoryAccess.getInventory());
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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// Inflate the recipe context menu.
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.inventory_context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Handle context menu item clicks.
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		selectedItem = (FullItem) listView.getItemAtPosition(info.position);
		switch (item.getItemId()) {
		case R.id.add_item:
			showAddItemDialog(selectedItem.name);
			return true;
		case R.id.remove_item:
			showRemoveItemDialog(selectedItem.name);
			return true;
		case R.id.edit_item:
			// Open Edit Item dialog
			EditItemDialog dialog = new EditItemDialog(InventoryActivity.this);
			dialog.show();
			return true;
		case R.id.delete_item:
			//TODO delete item from inventory
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	/** Displays the Add Item dialog. */
	private void showAddItemDialog(String title) {
		// Create the input dialog
		final EditText input = new EditText(InventoryActivity.this);
		AlertDialog.Builder alert = new AlertDialog.Builder(InventoryActivity.this);
		alert.setTitle(title);
		alert.setMessage("Add how many?");	//TODO: use string resource
		alert.setView(input);
		// Behavior for confirmation button
		alert.setPositiveButton(R.string.button_add, new DialogInterface.OnClickListener() {
			/** Listener for the "Add" button */
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int quantity;
				try {
					quantity = Integer.parseInt(input.getText().toString());
					addToSelectedItem(quantity);
					dialog.dismiss();
				} catch (NumberFormatException e) {
					displayError(R.string.error_positive_number);
				}
			}
		});
		// Behavior for "Cancel" button
		alert.setNegativeButton(R.string.button_cancel, new CancelButtonListener());
		alert.show();
	}
	/** Displays the Remove Item dialog. */
	private void showRemoveItemDialog(String title) {
		// Create the input dialog
		final EditText input = new EditText(InventoryActivity.this);
		AlertDialog.Builder alert = new AlertDialog.Builder(InventoryActivity.this);
		alert.setTitle(title);
		alert.setMessage("Remove how many?");	//TODO: use string resource
		alert.setView(input);
		// Behavior for confirmation button
		alert.setPositiveButton(R.string.button_remove, new DialogInterface.OnClickListener() {
			/** Listener for the "Add" button */
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int quantity;
				try {
					quantity = Integer.parseInt(input.getText().toString());
					removeFromSelectedItem(quantity);
					dialog.dismiss();
				} catch (NumberFormatException e) {
					displayError(R.string.error_positive_number);
				}
			}
		});
		// Behavior for "Cancel" button
		alert.setNegativeButton(R.string.button_cancel, new CancelButtonListener());
		alert.show();
	}
	/** Listener for the Add and Remove Item dialogs' Cancel buttons. */
	class CancelButtonListener implements DialogInterface.OnClickListener {
		/** Listener for the "Cancel" button */
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	}
	/** Dialog for editing the selected item. */
	class EditItemDialog extends AddItemDialog {
		/** Constructor */
		public EditItemDialog(Context context) {
			super(context);
			this.setTitle(selectedItem.getName());
			this.okButton.setText(R.string.button_edit);
			this.nameInput.setText(selectedItem.getName());
			this.nameInput.setEnabled(false);
			this.expirationDateText.setText(
					dateAssistent.formatDate(
							selectedItem.getExpiration_date()));
			this.quantityInput.setText(Integer.toString(
					selectedItem.getQuantity()));
		}
		/** Called by supertype's constructor */
		@Override
		protected void setListeners() {
			this.dateInputButton.setOnClickListener(null);
			this.okButton.setOnClickListener(new EditButtonListener());
			this.cancelButton.setOnClickListener(new CancelButtonListener());
		}
		/** Listener for the Edit dialog's confirmation button. */
		class EditButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				FullItem editedItem;
				editedItem = new FullItem();
				try {
					// Construct item to be added
					editedItem.setName(
							EditItemDialog.this.nameInput.getText().toString());
					editedItem.setExpiration_date(dateAssistent.createDate(
							EditItemDialog.this.expirationDateText.getText().toString()));
					editedItem.setQuantity(Integer.parseInt(
							EditItemDialog.this.quantityInput.getText().toString()));
					modifySelectedItem(editedItem);
					// Close dialog
					EditItemDialog.this.dismiss();
				} catch (ParseException pe) {
					displayError(R.string.error_date_format);
				} catch (NumberFormatException nfe) {
					displayError(R.string.error_positive_number);
				}
			}
		}
		/** Cancel Button listener */
		class CancelButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				// Close dialog
				EditItemDialog.this.dismiss();
			}
		}
	}
	/*-------------------------------------------------------------------------
	 * Code for handling the Add Item button
	 */
	/** Called when the user clicks the Add Item button */
	public void addItem(View view) {
		Intent intent = new Intent(this, AddItemActivity.class);
		// Ensure that any of the following dialogs can't be navigated back to.
		//TODO: decide if this is actually necessary
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ADD_ITEM) {
			if (resultCode == RESULT_OK) {
				//TODO: decide if dialog should return results rather than adding items to inventory itself
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
		/** Constructor */
		public AddNewItemDialog(Context context) {
			super(context);
			// Set dynamic text
			this.setTitle(R.string.inventory_button_add_new_item);
			this.okButton.setText(R.string.button_add);
		}
		/** Called by supertype's constructor */
		@Override
		protected void setListeners() {
			this.okButton.setOnClickListener(new AddButtonListener());
			this.cancelButton.setOnClickListener(new CancelButtonListener());
		}
		/** Add Button listener */
		class AddButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				FullItem newItem;
				try {
					// Construct item to be added
					newItem = new FullItem();
					newItem.setName(AddNewItemDialog.this.nameInput.getText().toString());
					newItem.setExpiration_date(dateAssistent.createDate(
							AddNewItemDialog.this.expirationDateText.getText().toString()));
					newItem.setQuantity(
							Integer.parseInt(AddNewItemDialog.this.quantityInput.getText().toString()));
					addNewItem(newItem);
					AddNewItemDialog.this.dismiss();
				} catch (NumberFormatException e) {
					displayError(R.string.error_positive_number);
				} catch (ParseException e) {
					displayError(R.string.error_date_format);
				}
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
	 * Utility functions
	 */
	/** Add a new item to the inventory. */
	private void addNewItem(FullItem newItem) {
		inventoryAccess.addItem(newItem);
		updateInventory();
	}
	/** Add the currently selected item to the inventory. */
	private void addToSelectedItem(int quantity) {
		inventoryAccess.addItem(new FullItem(selectedItem.getName(),
				selectedItem.getExpiration_date(), quantity));
		updateInventory();
	}
	/** Remove from the currently selected item in the inventory. */
	private void removeFromSelectedItem(int quantity) {
		inventoryAccess.removeItem(new FullItem(selectedItem.getName(),
				selectedItem.getExpiration_date(), quantity));
		updateInventory();
	}
	/** Modifies selected item in inventory. */
	private void modifySelectedItem(FullItem editedlItem) {
		inventoryAccess.modifyItem(selectedItem, editedlItem);
		updateInventory();
	}
	/** Deletes item from inventory. */
	private void deleteSelectedItem() {
		inventoryAccess.deleteItem(selectedItem);
		updateInventory();
	}
	/** Displays an alert with an error message. */
	private void displayError(int messageId) {
		AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
		builder.setMessage(messageId)
		       .setCancelable(false)
		       .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.dismiss();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
}
