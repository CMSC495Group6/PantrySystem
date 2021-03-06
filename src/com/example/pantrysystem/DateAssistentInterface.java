/** DateAssistentInterface interface
 * 
 *  Interface defining the required functionality of any object implementing
 *  DateAssistentInterface.  This takes care of all date-related processing,
 *  including formatting dates into strings and creating dates from strings.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/09/2014
 *  Created DateAssistentInterface interface and defined all required methods.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/11/2014
 *  Added a function for converting a combination of year, month and day into
 *  a correctly formatted string representation of the date.
 *  - Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

import java.text.ParseException;
import java.util.Date;

public interface DateAssistentInterface {
	/** Returns current date without time information. */
	public Date getCurrentDate();
	/** Returns a string with the date format. */
	public String getDateFormat();
	/** Creates a Date object using the provided formatted string. */
	public Date createDate(String dateString) throws ParseException;
	/** Returns a formatted string representation of the provided date. */
	public String formatDate(Date date);
	/** Returns a correctly formatted date string. */
	public String createDateString(int year, int month, int day);
	/** Recalculate the current date and replace it if the date changed. */
	public void updateCurrentDate();
}
