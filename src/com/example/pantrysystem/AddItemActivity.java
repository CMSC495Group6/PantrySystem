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
			this.setTitle("Add Item");	//TODO: use string resource
			this.titleText.setText("Add " + item.getName());
			// Set name text field to selected item's name and disable input
			this.nameInput.setText(item.getName());
			this.nameInput.setEnabled(false);
			// Set okButton's text to Add
			this.okButton.setText("Add");	//TODO: use string resource
		}
		
		/** Called by supertype's constructor */
		protected void setListeners() {
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
