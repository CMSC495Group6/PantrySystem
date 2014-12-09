/** DateAssistent class
 * 
 *  Implements the DateAssistentInterface interface.
 *  
 *  Uses the singleton pattern, so call the getInstance() method instead of
 *  calling the constructor.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/09/2014
 *  Created DateAssistent class and implement all required methods.
 *  - Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateAssistent implements DateAssistentInterface {
	private static final String DATE_FORMAT = "MMM-dd-yyyy";
	private static DateAssistent instance;
	static SimpleDateFormat dateFormatter;
	private static Date now;
	
	/** Returns a DateAssistent object.
	 * 
	 *  Use this instead of calling the constructor.
	 *  
	 */
	public static DateAssistent getInstance() {
		if (instance == null) {
			instance = new DateAssistent();
		}
		return instance;
	}
	/** Returns current date without time information. */
	@Override
	public Date getCurrentDate() {
		return now;
	}
	/** Returns a string with the date format. */
	@Override
	public String getDateFormat() {
		return DATE_FORMAT;
	}
	/** Creates a Date object using the provided formatted string. */
	@Override
	public Date createDate(String dateString) throws ParseException {
		Date date;
		date = dateFormatter.parse(dateString);
		return date;
	}
	/** Returns a formatted string representation of the provided date. */
	@Override
	public String formatDate(Date date) {
		return dateFormatter.format(date);
	}
	/** Recalculate the current date and replace it if the date changed. */
	@Override
	public void updateCurrentDate() {
		// Determine the current date and remove the time information.
		String nowString;
		now = new Date();
		nowString = dateFormatter.format(now);
		try {
			now = dateFormatter.parse(nowString);
		} catch (ParseException pE) {
			// If parsing fails, use the exact date and time.
		}
	}
	/** Private constructor.
	 * 
	 *  Call getInstance() to get a DateAssistent object.
	 *  
	 */
	private DateAssistent() {
		dateFormatter = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		updateCurrentDate();
	}
}
