/** AddItemDialog abstract class
 * 
 *  This class acts as a template for the few similar dialogs for adding or
 *  modifying a new or existing item.  Its on-click functionality needs to be
 *  defined by implementing the setListeners() method.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/03/2014
 *  Created AddItemDialog class and implemented all required functionality.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/05/2014
 *  Some code changes to reflect the changes in some UI elements' names.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Split off the code for setting listeners in the constructor to the abstract
 *  setListeners() method.  With this, the dialog's buttons' on-click behaviors
 *  must be defined by the extending class, which was intended from the
 *  beginning.
 *  - Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

import com.example.pantrysystem.R;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public abstract class AddItemDialog extends Dialog {
	protected TextView titleText;
	protected EditText nameInput;
	protected EditText expirationDateInput;
	protected EditText quantityInput;
	protected Button okButton;
	protected Button cancelButton;
	
	/** Constructor */
	public AddItemDialog(Context context) {
		super(context);
		this.setContentView(R.layout.dialog_inventory_add_item);
		// Bind dialog elements to object variables.
		this.titleText = (TextView) this.findViewById(R.id.add_item_title);
		this.nameInput = (EditText) this.findViewById(R.id.add_item_name_input);
		this.expirationDateInput = (EditText) this.findViewById(R.id.add_item_expiration_date_input);
		this.quantityInput = (EditText) this.findViewById(R.id.add_item_quantity_input);
		this.okButton = (Button) this.findViewById(R.id.add_item_ok_button);
		this.cancelButton = (Button) this.findViewById(R.id.add_item_cancel_button);
		// Listeners should be set by subclasses
		setListeners();
	}
	
	abstract protected void setListeners();
}
