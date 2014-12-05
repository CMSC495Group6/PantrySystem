package com.example.pantrysystem;

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
		// Listeners should be set by subclasses.
	}
	
	
}
