package com.example.pantrysystem;

import java.util.Date;

/**
 * 
 * @author Julian
 *
 * An extended item type that contains name, expiration date, and quantity.
 *
 */
public class FullItem extends BasicItem implements DatedItem, QuantifiedItem {
	private Date expiration_date;
	private int quantity;
	/** Constructors */
	public FullItem() {}
	public FullItem(String name, Date expiration_date, int quantity) {
		super(name);
		this.expiration_date = expiration_date;
		this.quantity = quantity;
	}
	/** Getters */
	@Override
	public Date getExpiration_date() {return this.expiration_date;}
	@Override
	public int getQuantity() {return this.quantity;}
	/** Setters */
	@Override
	public void setExpiration_date(Date expiration_date) {this.expiration_date = expiration_date;}
	@Override
	public void setQuantity(int quantity) {this.quantity = quantity;}
	
}
