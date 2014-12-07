package com.example.pantrysystem;

/**
 * 
 * @author Julian
 * 
 * A basic item type that only holds the item's name.
 *
 */
public class BasicItem {
	protected String name;
	/** Constructors */
	public BasicItem() {}
	public BasicItem(String name) {
		this.name = name;
	}
	/** Getters */
	public String getName() {return this.name;}
	/** Setters */
	public void setName(String name) {this.name = name;}
}
