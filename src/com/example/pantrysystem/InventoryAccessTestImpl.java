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
 *  ---------------------------------------------------------------------------
 *  12/10/2014
 *  Updated modifyItem() method to conform to changes in interface.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/12/2014
 *  Fixed misspelling of banana.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/12/2014
 *  Added findItem() function and fixed the data-management functions.
 *  -Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class InventoryAccessTestImpl implements InventoryAccessInterface {
	private static final String[] INIT_DATA = {
		"Apple","Dec-11-2014","6",
		"Banana", "Dec-29-2014", "0",
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
			if (tmpItem.getQuantity() > 0) stocked.add(tmpItem);
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
		int quantity;
		int index = findItem(item);
		if (index >= 0) {
			quantity = inventory.get(index).getQuantity() + item.getQuantity();
			inventory.get(index).setQuantity(quantity);
			return;
		} else {
			// Add new item to inventory if not match was found
			inventory.add(item);
		}
	}

	@Override
	public void removeItem(FullItem item) {
		int quantity;
		int index = findItem(item);
		if (index >= 0) {
			quantity = inventory.get(index).getQuantity() - item.getQuantity();
			//TODO: throw exception if result is negative
			inventory.get(index).setQuantity(quantity);
			return;
		} else {
			//TODO: throw exception if item wasn't found
		}
	}

	@Override
	public void modifyItem(FullItem originalItem, FullItem editedItem) {
		int index = findItem(originalItem);
		if (index >= 0) {
			inventory.set(index, editedItem);
			return;
		} else {
			//TODO: throw exception if item wasn't found
		}
	}

	@Override
	public void deleteItem(FullItem item) {
		int index = findItem(item);
		if (index >= 0) {
			inventory.remove(index);
			return;
		} else {
			//TODO: throw exception if item wasn't found
		}

	}
	/** Searches the inventory for an item identical to the one provided and
	 * returns its index, -1 if no such item exists. */
	private int findItem(FullItem item) {
		FullItem tmpItem;
		for (Iterator<FullItem> i = inventory.iterator(); i.hasNext(); ) {
			tmpItem = i.next();
			if (tmpItem.equals(item)) {
				return inventory.indexOf(tmpItem);
			}
		}
		return -1;
	}

}
