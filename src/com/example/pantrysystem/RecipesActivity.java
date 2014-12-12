/** RecipesActivity class
 * 
 *  Class implementing the Recipes screen's functionality.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Created RecipesActivity class and outlined all required functionality.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/08/2014
 *  Outlined the code for displaying the list of recipes; added the context
 *  menu for checking, editing, or deleting a recipe, and outlined its code.
 *  -Julian
 *  ---------------------------------------------------------------------------
 *  12/11/2014
 *  Filled in missing function calls.
 *  -Julian
 *  ---------------------------------------------------------------------------
 *  12/11/2014
 *  Uncommented the list initialization code, and added updateRecipeList().
 *  -Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RecipesActivity extends ActionBarActivity {
	ListView recipeList;
	RecipeHandler recipeHandler;
	ArrayList<String> recipeNames;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipe);
		
		//TODO: Initialize database access object
		recipeHandler = new RecipeHandler();
		
		//TODO: initialize recipe list view and array adapter
		recipeList = (ListView) findViewById(R.id.recipe_list);
		recipeNames = recipeHandler.getRecipeNames();
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, recipeNames);
		recipeList.setAdapter(adapter);
		// Set up the recipe context menu
		registerForContextMenu(recipeList);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateRecipeList();
	}
	
	private void updateRecipeList() {
		recipeNames = recipeHandler.getRecipeNames();
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recipe, menu);
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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// Inflate the recipe context menu.
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.recipe_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Handle context menu item clicks.
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		String recipeName = (String) recipeList.getItemAtPosition(info.position);
		switch (item.getItemId()) {
		case R.id.check_recipe:
			recipeHandler.checkInventory(recipeName);
			return true;
		case R.id.edit_recipe:
			// Display the Recipe Editor screen with the selected recipe
			Intent intent = new Intent(this, RecipesAddNewRecipeActivity.class);
			// Ensure that any of the following dialogs can't be navigated back to.
			//TODO: decide if this is actually necessary
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra(RecipesAddNewRecipeActivity.KEY_DISPLAY_RECIPE, recipeName);
			startActivity(intent);
			return true;
		case R.id.delete_recipe:
			recipeHandler.deleteRecipe(recipeName);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}
	
	/** Called when the user clicks the Add New Recipe button */
	public void addNewRecipe(View view) {
		Intent intent = new Intent(this, RecipesAddNewRecipeActivity.class);
		// Ensure that any of the following dialogs can't be navigated back to.
		//TODO: decide if this is actually necessary
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(RecipesAddNewRecipeActivity.KEY_DISPLAY_RECIPE, "");
		startActivity(intent);
	}
}
