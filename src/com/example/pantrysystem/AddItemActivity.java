/** AddItemActivity class
 * 
 *  This class implements the Add Item activity, which displays a list of all
 *  item types.  Clicking on an item displays a dialog where the user can input
 *  how many of the selected item to add to the inventory.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/03/2014
 *  Created InventoryAddItemActivity class and implemented all required
 *  functionality.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/05/2014
 *  Renamed AddNewItemDialog variable to AddSelectedItemDialog.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Renamed to AddItemActivity.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/10/2014
 *  Replaced some string literals with references to string resources.
 *  - Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

import java.util.Date;

import com.example.pantrysystem.R;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class AddItemActivity extends ItemListActivity {
	private AddSelectedItemDialog dialog;

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
			// Instantiate Add Item dialog
			dialog = new AddSelectedItemDialog(AddItemActivity.this, item);
			// Display dialog
			dialog.show();
		}
	}
	
	/** Add Item Dialog class */
	class AddSelectedItemDialog extends AddItemDialog {
		BasicItem item;
		/** Constructor */
		public AddSelectedItemDialog(Context context, BasicItem item) {
			super(context);
			this.item = item;
			// Set displayed text
			this.setTitle(item.getName());
			// Set name text field to selected item's name and disable input
			this.nameInput.setText(item.getName());
			this.nameInput.setEnabled(false);
			// Set okButton's text to Add
			this.okButton.setText(R.string.button_add);
		}
		
		/** Called by supertype's constructor */
		protected void setListeners() {
			this.dateInputButton.setOnClickListener(null);
			this.okButton.setOnClickListener(new AddButtonListener());
			this.cancelButton.setOnClickListener(new CancelButtonListener());
		}
		
		/** Add Button listener */
		class AddButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				// Construct item to be added
				FullItem newItem;
				newItem = new FullItem(
						AddSelectedItemDialog.this.nameInput.getText().toString(),
						new Date(),	//TODO: find way to get a Date object from the input field.
						Integer.parseInt(AddSelectedItemDialog.this.quantityInput.getText().toString()));
				// Add item to database
				inventoryAccess.addItem(newItem);
				// Close dialog
				AddSelectedItemDialog.this.dismiss();
				// Return result
				finish();
			}
		}
		
		/** Cancel Button listener */
		class CancelButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				// Close dialog
				AddSelectedItemDialog.this.dismiss();
			}
		}
	}
}
