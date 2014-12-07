package com.example.pantrysystem;

import java.util.Date;

/**
 * 
 * @author Julian
 *
 * An interface defining the functionality of an item with an expiration date.
 *
 */
public interface DatedItem {
	public Date getExpiration_date();
	public void setExpiration_date(Date expiration_date);
}
