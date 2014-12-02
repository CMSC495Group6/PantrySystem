package com.example.pantrysystem;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class InventoryAccessTestImpl implements InventoryAccessInterface {
	ArrayList<Item> inventory;
	
	public InventoryAccessTestImpl() {
		inventory = new ArrayList<Item>();
		
		// Generate test data.
		inventory.add(new Item("Apple", new Date(), 6));
		inventory.add(new Item("Bannanna", new Date(), 0));
		inventory.add(new Item("Coconut", new Date(), 1));
		inventory.add(new Item("Doritos", new Date(), 2));
		inventory.add(new Item("Espresso", new Date(), 10));
	}
	
	@Override
	public ArrayList<Item> getItemTypes() {
		inventory.clear();
		inventory.add(new Item("Apple", null, 0));
		inventory.add(new Item("Bananna", null, 0));
		
		return this.inventory;
	}
	
	@Override
	public ArrayList<Item> getInventory() {
		return this.inventory;
	}

	@Override
	public ArrayList<Item> getStockedItems() {
		ArrayList<Item> stocked = new ArrayList<Item>();
		Item tmpItem;
		// Assemble list of all items with quantity > 0
		for (Iterator<Item> i = inventory.iterator(); i.hasNext(); ) {
			tmpItem = i.next();
			if (tmpItem.getQuantity() != 0) stocked.add(tmpItem);
		}
		
		return stocked;
	}

	@Override
	public ArrayList<Item> getExpiredItems() {
		ArrayList<Item> expired = new ArrayList<Item>();
		Item tmpItem;
		Date now = new Date();
		// Assemble list of all items with a date before the current date
		for (Iterator<Item> i = inventory.iterator(); i.hasNext(); ) {
			tmpItem = i.next();
			if (tmpItem.getExpiration_date().before(now)) expired.add(tmpItem);
		}
		
		return expired;
	}

	@Override
	public ArrayList<Item> getItemsOfType(Item item) {
		ArrayList<Item> items = new ArrayList<Item>();
		Item tmpItem;
		// Assemble list of all items with a date before the current date
		for (Iterator<Item> i = inventory.iterator(); i.hasNext(); ) {
			tmpItem = i.next();
			if (tmpItem.getName().equals(item.getName())) items.add(tmpItem);
		}
		
		return items;
	}

	@Override
	public void addItem(Item item) {
		inventory.add(item);
	}

	@Override
	public void removeItem(Item item) {
		Item tmpItem;
		int i;
		i = inventory.indexOf(item);
		tmpItem = inventory.get(i);
		tmpItem.setQuantity(tmpItem.getQuantity() - item.getQuantity());
		inventory.set(i, tmpItem);
	}

	@Override
	public void modifyItem(Item item) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteItem(Item item) {
		// TODO Auto-generated method stub

	}

}
