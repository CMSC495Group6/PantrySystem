/** MainActivity class
 * 
 *  This class implements all of the main menu screen's actions.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/01/2014
 *  Created MainActivity class; added Inventory, Expired Items, and Recipes
 *  buttons; and made it so that the Inventory button call the inventory
 *  activity.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Made the Recipes button call the recipes activity.
 *  - Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;


import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/** Called when user clicks the Inventory button */
    public void viewInventory(View view) {
    	Intent intent = new Intent(this, InventoryActivity.class);
    	//TODO: decide whether this activity should use FLAG_ACTIVITY_CLEAR_TOP
    	startActivity(intent);
    }
    
    /** Called when user clicks the Recipes button */
    public void viewRecipes(View view) {
    	Intent intent = new Intent(this, RecipesActivity.class);
    	//TODO: decide whether this activity should use FLAG_ACTIVITY_CLEAR_TOP
    	startActivity(intent);
    }
}
