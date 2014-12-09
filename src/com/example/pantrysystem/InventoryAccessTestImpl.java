/** InventoryAccessTestImpl class
 * 
 *  A quick-and-dirty implementation of the InventoryAccessInterface interface
 *  so that the inventory GUI has some data to work with.  The storage
 *  functions are not necessarily correct.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/01/2014
 *  Created InventoryAccessTestImpl class and implemented most of the required
 *  functionality.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/03/2014
 *  Implemented getItemTypes() function.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/05/2014
 *  Made the inventory variable static and applied the singleton pattern to it
 *  so that the saved data is the same for all view screens; enhanced the
 *  getItemTypes implementation.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Some code changes to reflect the changes to the Item class.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/09/2014
 *  Added proper date functionality using DateAssistentInterface.
 *  -Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;


public class InventoryAccessTestImpl implements InventoryAccessInterface {
	private static final String[] INIT_DATA = {
		"Apple","Dec-11-2014","6",
		"Bannanna", "Dec-29-2014", "0",
		"Coconut", "Jan-04-2015", "1",
		"Doritos", "Apr-12-2020", "2",
		"Espresso", "Feb-28-2005", "10"
	};
	
	static ArrayList<FullItem> inventory;
	private DateAssistentInterface dateAssistent;
	
	public InventoryAccessTestImpl() {
		dateAssistent = DateAssistent.getInstance();
		if (inventory == null) {
			inventory = new ArrayList<FullItem>();
		
			// Generate test data.
			String name;
			Date date;
			int quantity;
			for (int i = 0; i < INIT_DATA.length; i += 3) {
				name = INIT_DATA[i];
				try{
					date =  dateAssistent.createDate(INIT_DATA[i+1]);
				} catch (ParseException pe) {
					date = new Date();
				}
				try {
					quantity = Integer.parseInt(INIT_DATA[i+2]);
				} catch (NumberFormatException nfe) {
					quantity = 0;
				}
				inventory.add(new FullItem(name, date, quantity));
			}
		}
	}
	
	@Override
	public ArrayList<BasicItem> getItemTypes() {
		ArrayList<BasicItem> types = new ArrayList<BasicItem>();
		FullItem tmpItem;
		for (Iterator<FullItem> i = inventory.iterator(); i.hasNext(); ) {
			tmpItem = i.next();
			types.add(new BasicItem(tmpItem.getName()));
		}
		
		return types;
	}
	
	@Override
	public ArrayList<FullItem> getInventory() {
		return inventory;
	}

	@Override
	public ArrayList<FullItem> getStockedItems() {
		ArrayList<FullItem> stocked = new ArrayList<FullItem>();
		FullItem tmpItem;
		// Assemble list of all items with quantity > 0
		for (Iterator<FullItem> i = inventory.iterator(); i.hasNext(); ) {
			tmpItem = i.next();
			if (tmpItem.getQuantity() != 0) stocked.add(tmpItem);
		}
		
		return stocked;
	}

	@Override
	public ArrayList<FullItem> getExpiredItems() {
		ArrayList<FullItem> expired = new ArrayList<FullItem>();
		FullItem tmpItem;
		Date now = new Date();
		// Assemble list of all items with a date before the current date
		for (Iterator<FullItem> i = inventory.iterator(); i.hasNext(); ) {
			tmpItem = i.next();
			if (tmpItem.getExpiration_date().before(now)) expired.add(tmpItem);
		}
		
		return expired;
	}

	@Override
	public ArrayList<BasicItem> getItemsOfType(BasicItem item) {
		ArrayList<BasicItem> items = new ArrayList<BasicItem>();
		FullItem tmpItem;
		// Assemble list of all items with a date before the current date
		for (Iterator<FullItem> i = inventory.iterator(); i.hasNext(); ) {
			tmpItem = i.next();
			if (tmpItem.getName().equals(item.getName())) items.add(new BasicItem(tmpItem.name));
		}
		
		return items;
	}

	@Override
	public void addItem(FullItem item) {
		inventory.add(item);
	}

	@Override
	public void removeItem(FullItem item) {
		FullItem tmpItem;
		int i;
		i = inventory.indexOf(item);
		tmpItem = inventory.get(i);
		tmpItem.setQuantity(tmpItem.getQuantity() - item.getQuantity());
		inventory.set(i, tmpItem);
	}

	@Override
	public void modifyItem(FullItem item) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteItem(BasicItem item) {
		// TODO Auto-generated method stub

	}

}
