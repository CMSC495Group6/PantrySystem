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
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

import java.util.Date;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class InventoryActivity extends ActionBarActivity {
	static final int ADD_ITEM = 1;
	
	private ListView listView;
	private FullItemAdapter adapter;
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
		adapter = new FullItemAdapter(this, inventoryAccess.getInventory());
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
		FullItem item;
		/** Constructor */
		public AddNewItemDialog(Context context) {
			super(context);
			// Set dynamic text
			this.setTitle("Add New Item");	//TODO: use string resource
			this.titleText.setText("Add New Item");	//TODO: remove this text element?
			this.okButton.setText("Add");	//TODO: use string resource
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
				// Construct item to be added
				item = new FullItem(
						AddNewItemDialog.this.nameInput.getText().toString(),
						new Date(),	//FIXME: find way to get a Date object from the input field.
						Integer.parseInt(AddNewItemDialog.this.quantityInput.getText().toString())
						);
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
			// Extract item from list
			FullItem selectedItem = (FullItem) listView.getItemAtPosition(position);
			// Instantiate item edit dialog
			dialog = new ItemContextDialog(InventoryActivity.this, selectedItem);
			// Display dialog
			dialog.show();
		}
	}
	
	class ItemContextDialog extends Dialog {
		private FullItem selectedItem;
		private Button addButton;
		private Button removeButton;
		private Button modifyButton;
		private Button deleteButton;
		private Button cancelButton;
		
		public ItemContextDialog(Context context, FullItem selectedItem) {
			super(context);
			this.setContentView(R.layout.dialog_inventory_item_selected);
			
			this.selectedItem = new FullItem(
					selectedItem.getName(),
					selectedItem.getExpiration_date(),
					selectedItem.getQuantity()
					);
			this.setTitle(selectedItem.getName());
			// initialize button variables
			addButton = (Button) this.findViewById(R.id.inventory_item_selected_add_button);
			removeButton = (Button) this.findViewById(R.id.inventory_item_selected_remove_button);
			modifyButton = (Button) this.findViewById(R.id.inventory_item_selected_modify_button);
			deleteButton = (Button) this.findViewById(R.id.inventory_item_selected_delete_button);
			cancelButton = (Button) this.findViewById(R.id.inventory_item_selected_cancel_button);
			// Set up button listeners
			addButton.setOnClickListener(new AddItemButtonListener());
			removeButton.setOnClickListener(new RemoveItemButtonListener());
			modifyButton.setOnClickListener(new ModifyItemButtonListener());
			deleteButton.setOnClickListener(new DeleteItemButtonListener());
			cancelButton.setOnClickListener(new CancelButtonListener());
		}

		/** Listener for the "Add" button. */
		class AddItemButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				// Create the input dialog
				final EditText input = new EditText(InventoryActivity.this);
				AlertDialog.Builder alert = new AlertDialog.Builder(InventoryActivity.this);
				alert.setTitle(selectedItem.getName());
				alert.setMessage("Add how many?");	//TODO: use string resource
				alert.setView(input);
				// Behavior for "Add" button
				alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {	//TODO: use string resource
					/** Listener for the "Add" button */
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int quantity;
						try {
							quantity = Integer.parseInt(input.getText().toString());
							if (quantity > 0) {	//FIXME: InventoryHandler should be in charge of this
								// add item to inventory
								selectedItem.setQuantity(quantity);
								inventoryAccess.addItem(selectedItem);
								// Update list view
								updateInventory();
								ItemContextDialog.this.dismiss();
							} else {
								displayError("Please input a positive number!");	//TODO: use string resource
							}
							
						} catch (NumberFormatException e) {
							displayError("Please input a positive number!");	//TODO: use string resource
						}
					}
				});
				// Behavior for "Cancel" button
				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {	//TODO: use string resource
					/** Listener for the "Cancel" button */
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				// Display input dialog
				alert.show();
			}
		}
		
		/** Listener for the "Remove" button. */
		class RemoveItemButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				// Create the input dialog
				final EditText input = new EditText(InventoryActivity.this);
				AlertDialog.Builder alert = new AlertDialog.Builder(InventoryActivity.this);
				alert.setTitle(selectedItem.getName());
				alert.setMessage("Remove how many?");	//TODO: use string resource
				alert.setView(input);
				// Behavior for "Remove" button
				alert.setPositiveButton("Remove", new DialogInterface.OnClickListener() {	//TODO: use string resource
					/** Listener for the "Remove" button */
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int quantity;
						int numberOfItems = selectedItem.getQuantity();
						try {
							quantity = Integer.parseInt(input.getText().toString());
							if (quantity > 0 && quantity <= numberOfItems) {	//FIXME: InventoryHandler should be in charge of this
								// remove item from inventory
								selectedItem.setQuantity(quantity);
								inventoryAccess.removeItem(selectedItem);
								// Update list view
								updateInventory();
								ItemContextDialog.this.dismiss();
							} else if (quantity > numberOfItems) {
								displayError("Can't remove more than " + numberOfItems + " items!");	//TODO: use string resource
							} else {
								displayError("Please input a positive number lower than" + numberOfItems + " !");
							}
							
						} catch (NumberFormatException e) {
							displayError("Please input a positive number lower than" + numberOfItems + " !");	//TODO: use string resource
						}
					}
				});
				// Behavior for "Cancel" button
				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {	//TODO: use string resource
					/** Listener for the "Cancel" button */
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				// Display input dialog
				alert.show();
			}
		}
		
		/** Listener for the "Modify" button. */
		class ModifyItemButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				// Open Edit Item dialog
				ModifyItemDialog dialog = new ModifyItemDialog(InventoryActivity.this);
				dialog.show();
			}
		}
		
		class ModifyItemDialog extends AddItemDialog {
			/** Constructor */
			public ModifyItemDialog(Context context) {
				super(context);
				this.setTitle(selectedItem.getName());
				this.titleText.setText("Modify Item");	//TODO: remove this text element?
				this.okButton.setText("Modify");	//TODO: use string resource
				this.nameInput.setText(selectedItem.getName());
				this.nameInput.setEnabled(false);
				//TODO: display item's expiration date in expiration date input field
				this.quantityInput.setText(Integer.toString(selectedItem.getQuantity()));
			}
			
			/** Called by supertype's constructor */
			@Override
			protected void setListeners() {
				this.okButton.setOnClickListener(new ModifyButtonListener());
				this.cancelButton.setOnClickListener(new CancelButtonListener());
			}
			
			class ModifyButtonListener implements View.OnClickListener {
				@Override
				public void onClick(View view) {
					// Construct item to be added
					selectedItem.setName(ModifyItemDialog.this.nameInput.getText().toString());
					selectedItem.setExpiration_date(new Date());	//FIXME: find way to get a Date object from the input field.
					selectedItem.setQuantity(Integer.parseInt(ModifyItemDialog.this.quantityInput.getText().toString()));
					// Add item to database
					inventoryAccess.modifyItem(selectedItem);
					// Update list view
					updateInventory();
					// Close dialog
					ModifyItemDialog.this.dismiss();
					ItemContextDialog.this.dismiss();
				}
			}
			
			/** Cancel Button listener */
			class CancelButtonListener implements View.OnClickListener {
				@Override
				public void onClick(View view) {
					// Close dialog
					ModifyItemDialog.this.dismiss();
				}
			}
		}
		
		/** Listener for the "Delete" button. */
		class DeleteItemButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				// TODO open alert asking user to confirm
				inventoryAccess.deleteItem(selectedItem);
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
		
		/** Displays an alert with an error message */
		private void displayError(String message) {
			AlertDialog.Builder builder = new AlertDialog.Builder(InventoryActivity.this);
			builder.setMessage(message)
			       .setCancelable(false)
			       .setPositiveButton("OK", new DialogInterface.OnClickListener() {	//TODO: use string resource
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.dismiss();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
	}
}
