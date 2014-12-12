/** SelectItemActivity class
 * 
 *  Class that implements the recipe system's ingredient selection menu.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Created SelectItemActivity class and outlined the required methods.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Added context menu for inputting a quantity when the user selects an
 *  ingredient.  The result is not yet passed to the calling activity.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  The selected item and quantity are now passed to the calling activity.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/11/2014
 *  Added Edit Mode functionality.
 *  -Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class SelectItemActivity extends ItemListActivity {
	static final String KEY_RETURN_NAME = "Name";
	static final String KEY_RETURN_QUANTITY = "Quantity";
	static final String KEY_CHANGED_ITEM = "ChangedItem";
	
	private String changedItemName;
	/** Called by supertype's initialization code */
	@Override
	protected void setUpListView() {
		// If an ingredient is specified by name, that means we're handling a
		// request to change a selected ingredient, so we save that ingredient's
		// name and remove it from the list of options.
		changedItemName = getIntent().getStringExtra(KEY_CHANGED_ITEM);
		if (changedItemName != null) {
			String tmpName;
			for (Iterator<String> i = this.itemTypeNames.iterator(); i.hasNext(); ) {
				tmpName = i.next();
				if (tmpName.equals(changedItemName)) {
					this.itemTypeNames.remove(tmpName);
					break;
				}
			}
		}
		listView = (ListView) findViewById(R.id.item_select_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, itemTypeNames);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new ItemClickListener());
	}
	
	/** Called when the user clicks on a list item */
	class ItemClickListener implements OnItemClickListener {
		String itemName;
		int itemQuantity;
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// Retrieve item from item list in the same position as in the name list
			BasicItem selectedItem = itemTypes.get(position);
			itemName = selectedItem.getName();
			final EditText input = new EditText(SelectItemActivity.this);
			AlertDialog.Builder alert = new AlertDialog.Builder(SelectItemActivity.this);
			alert.setTitle(selectedItem.getName());
			alert.setMessage("Add how many?");	//TODO: use string resource
			alert.setView(input);
			// Behavior for "Add" button
			alert.setPositiveButton("Add", new DialogInterface.OnClickListener() {	//TODO: use string resource
				/** Listener for the "Add" button */
				@Override
				public void onClick(DialogInterface dialog, int which) {
					try {
						itemQuantity = Integer.parseInt(input.getText().toString());
					} catch (NumberFormatException e) {
						displayError("Please input a positive number!");	//TODO: use string resource
					} finally {
						dialog.dismiss();
						// Return selected item and quantity to calling activity
						Intent result = new Intent();
						result.putExtra(KEY_RETURN_NAME, itemName);
						result.putExtra(KEY_RETURN_QUANTITY, itemQuantity);
						result.putExtra(KEY_CHANGED_ITEM, changedItemName);
						setResult(Activity.RESULT_OK, result);
						finish();
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
			alert.show();
		}
		
		/** Displays an alert with an error message */
		private void displayError(String message) {
			AlertDialog.Builder builder = new AlertDialog.Builder(SelectItemActivity.this);
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
