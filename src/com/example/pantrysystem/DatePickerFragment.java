/** DatePickerFragment class
 * 
 *  Handles the functionality of the date picker that's used in a few inventory
 *  dialogs.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/10/2014
 *  Created DatePickerFragment class.
 *  - Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements
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
		// TODO Auto-generated method stub

	}

}
