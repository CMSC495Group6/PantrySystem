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
}
