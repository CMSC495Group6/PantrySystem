/** IngredientItem class
 * 
 *  Item type representing an item that consists of a name and quantity.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Created IngredientItem class and implemented all required functionality.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/12/2014
 *  Added comparison function that returns true if the two items have the same
 *  name and quantity.
 *  - Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

/**
 * 
 * @author Julian
 *
 * An extended item type that contains a name and quantity.
 *
 */
public class IngredientItem extends BasicItem implements QuantifiedItem {
	private int quantity;
	/** Constructors */
	public IngredientItem() {}
	public IngredientItem(String name, int quantity) {
		super(name);
		this.quantity = quantity;
	}
	/** Getters */
	@Override
	public int getQuantity() {return quantity;}
	/** Setters */
	@Override
	public void setQuantity(int quantity) {this.quantity = quantity;}
	/** Comparison function */
	public boolean equals(IngredientItem item) {
		return this.name.equals(item.getName());
	}
}
