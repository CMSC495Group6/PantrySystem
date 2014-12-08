/** DatedItem interface
 * 
 *  Interface defining the required functionality of an Item object with an
 *  expiration date.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Created DatedItem interface and defined all required functionality.
 *  - Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

import java.util.Date;

public interface DatedItem {
	public Date getExpiration_date();
	public void setExpiration_date(Date expiration_date);
}
