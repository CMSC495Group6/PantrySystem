/** InventoryAccessInterface interface
 * 
 *  This interface defines all the interactions the GUI can make with the
 *  inventory database.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/01/2014
 *  Created InventoryAccess interface and defined methods.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/03/2014
 *  Renamed to InventoryAccessInterface.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Some code changes to reflect the changes to the Item class.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/10/2014
 *  Changed the modifyItem() method to also require the original item as input.
 *  - Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

import java.util.ArrayList;


public interface InventoryAccessInterface {
	// Methods for accessing the inventory.
	/** Get list of item types. */
	public ArrayList<BasicItem> getItemTypes();
	/** Get entire inventory. */
	public ArrayList<FullItem> getInventory();
	/** Get all in-stock items. */
	public ArrayList<FullItem> getStockedItems();
	/** Get all expired items. */
	public ArrayList<FullItem> getExpiredItems();
	/** Get all items of a specified type. */
	public ArrayList<BasicItem> getItemsOfType(BasicItem item);
	// Methods for changing the inventory.
	/** Add item to inventory. */
	public void addItem(FullItem item);
	/** Remove item from inventory. */
	//TODO: throw exception when amount is too high, or when no such item is in the inventory?
	public void removeItem(FullItem item);
	/** Modify an item's information. */
	//TODO: throw exception when supplied item is not present in inventory?
	public void modifyItem(FullItem originalItem, FullItem editedItem);
	/** Delete item from inventory. */
	//TODO: throw exception when items of this type with a quantity > 0 are in the inventory?
	public void deleteItem(BasicItem item);
}
