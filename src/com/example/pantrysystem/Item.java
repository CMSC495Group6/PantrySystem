package com.example.pantrysystem;

import java.util.Date;

public class Item {
	private String name;
	private Date expiration_date;
	private int quantity;
	
	public Item() {}
	
	public Item(String name, Date expiration_date, int quantity) {
		this.name = name;
		this.expiration_date = expiration_date;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getExpiration_date() {
		return expiration_date;
	}

	public void setExpiration_date(Date expiration_date) {
		this.expiration_date = expiration_date;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
