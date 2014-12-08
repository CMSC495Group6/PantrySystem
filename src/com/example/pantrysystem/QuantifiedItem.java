/** QuantifiedItem interface
 * 
 *  Interface that defines the behavior of an item that can exist in
 *  quantities.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Created QuantifiedItem interface and defined all required functionality.
 *  - Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

/**
 * 
 * @author Julian
 *
 * An interface defining the functionality of an item with a quantity.
 *
 */
public interface QuantifiedItem {
	public int getQuantity();
	public void setQuantity(int quantity);
}
