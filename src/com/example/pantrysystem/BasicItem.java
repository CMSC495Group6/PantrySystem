/** BasicItem class
 * 
 *  Base class for any Item type.  Only contains a name.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Created BasicItem class and implemented all required functionality.
 *  - Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

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
