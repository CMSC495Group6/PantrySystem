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
 *  ---------------------------------------------------------------------------
 *  12/11/2014
 *  Added Date Picker functionality to the Add Item dialog.
 *  - Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

import java.text.ParseException;
import java.util.Calendar;

import com.example.pantrysystem.R;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
			this.dateInputButton.setOnClickListener(new SetDateButtonListener());
			this.okButton.setOnClickListener(new AddButtonListener());
			this.cancelButton.setOnClickListener(new CancelButtonListener());
		}
		
		/** Add Button listener */
		class AddButtonListener implements View.OnClickListener {
			@Override
			public void onClick(View view) {
				// Construct item to be added
				FullItem newItem;
				newItem = new FullItem();
				try {
					newItem.setName(
							AddSelectedItemDialog.this.nameInput.getText().toString());
					newItem.setExpiration_date(DateAssistent.getInstance().createDate(
							AddSelectedItemDialog.this.expirationDateText.getText().toString()));
					newItem.setQuantity(
							Integer.parseInt(AddSelectedItemDialog.this.quantityInput.getText().toString()));
					// Add item to database
					inventoryAccess.addItem(newItem);
					// Close dialog
					AddSelectedItemDialog.this.dismiss();
					// Return result
					finish();
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
				AddSelectedItemDialog.this.dismiss();
			}
		}
	}
	/*-------------------------------------------------------------------------
	 * Date Picker used by the Add New Item and Edit Item dialogs.
	 */
	/** Listener for the Set Date button. */
	class SetDateButtonListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			DialogFragment newFragment = new DatePickerFragment();
		    newFragment.show(getSupportFragmentManager(), "datePicker");
		}
	}
	class DatePickerFragment extends DialogFragment implements
		OnDateSetListener {
	
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		    // Use the current date as the default date in the picker
		    final Calendar c = Calendar.getInstance();
		    int year = c.get(Calendar.YEAR);
		    int month = c.get(Calendar.MONTH);
		    int day = c.get(Calendar.DAY_OF_MONTH);
		
		    // Create a new instance of DatePickerDialog and return it
		    return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			dialog.setExpirationDateText(
					DateAssistent.getInstance().createDateString(year, monthOfYear, dayOfMonth));
		}
	
	}
	/*-------------------------------------------------------------------------
	 * Utility functions
	 */
	/** Displays an alert with an error message. */
	private void displayError(int messageId) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
