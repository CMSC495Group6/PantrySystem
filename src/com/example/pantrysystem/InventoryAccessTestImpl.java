package com.example.pantrysystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


public class InventoryAccessTestImpl implements InventoryAccessInterface {
	static ArrayList<FullItem> inventory;
	
	public InventoryAccessTestImpl() {
		if (inventory == null) {
			inventory = new ArrayList<FullItem>();
		
			// Generate test data.
			inventory.add(new FullItem("Apple", new Date(), 6));
			inventory.add(new FullItem("Bannanna", new Date(), 0));
			inventory.add(new FullItem("Coconut", new Date(), 1));
			inventory.add(new FullItem("Doritos", new Date(), 2));
			inventory.add(new FullItem("Espresso", new Date(), 10));
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
