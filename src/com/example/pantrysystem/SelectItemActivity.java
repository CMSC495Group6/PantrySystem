package com.example.pantrysystem;

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
						//TODO: Return selected item and quantity to calling activity
						Intent result = new Intent();
						result.putExtra(KEY_RETURN_NAME, itemName);
						result.putExtra(KEY_RETURN_QUANTITY, itemQuantity);
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
