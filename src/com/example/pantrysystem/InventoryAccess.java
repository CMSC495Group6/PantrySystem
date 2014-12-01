package com.example.pantrysystem;

import java.util.ArrayList;

public interface InventoryAccess {
	// Methods for accessing the inventory.
	/** Get entire inventory. */
	public ArrayList<Item> getInventory();
	/** Get all in-stock items. */
	public ArrayList<Item> getStockedItems();
	/** Get all expired items. */
	public ArrayList<Item> getExpiredItems();
	/** Get all items of a specified type. */
	public ArrayList<Item> getItemType(Item item);
	// Methods for changing the inventory.
	/** Add item to inventory. */
	public void addItem(Item item);
	/** Remove item from inventory. */
	//TODO: throw exception when amount is too high, or when no such item is in the inventory?
	public void removeItem(Item item);
	/** Modify an item's information. */
	//TODO: throw exception when supplied item is not present in inventory?
	public void modifyItem(Item item);
	/** Delete item from inventory. */
	//TODO: throw exception when items of this type with a quantity > 0 are in the inventory?
	public void deleteItem(Item item);
}
