/** RecipesAddNewRecipeActivity class
 * 
 *  Class implementing the Add New Recipe screen's functionality.
 *  
 *  CMSC 495
 *  Group 6
 *  ***************************************************************************
 *  Revision History
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Created RecipesAddNewRecipeActivity class and outlined all the required
 *  functions.  Clicking the Add Ingredient button displays the Add Ingredient
 *  screen.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/07/2014
 *  Replaced the FullItemAdapter with a IngredientItemAdapter.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/08/2014
 *  Activity now accepts results returned from the Add Ingredient activity.
 *  - Julian
 *  ---------------------------------------------------------------------------
 *  12/08/2014
 *  Outlined the initialization code that distinguishes between when the user
 *  is creating a new recipe or editing an existing one, and outlined the code
 *  for the Add Recipe button.
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
import android.widget.ListView;

public class RecipesAddNewRecipeActivity extends ActionBarActivity {
	static final String KEY_DISPLAY_RECIPE = "KEY_DISPLAY_RECIPE";
	static final int SELECT_ITEM_REQUEST = 1;
	
	private ListView ingredientList;
	private IngredientItemAdapter adapter;
	RecipeHandler recipeHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipes_add_new_recipe);
		
		//TODO: If a recipe name was passed to the activity, load that recipe and
		// display it.  Otherwise, display an empty editor for creating a new
		// recipe.
		String recipeName = getIntent().getStringExtra(KEY_DISPLAY_RECIPE);
		if (recipeName.equals("")) {
			//TODO: set up recipeHandler to create a new recipe
		} else {
			//TODO: set up recipeHandler to edit an existing recipe
			//TODO: get recipe's name and display it in the name input field
			//TODO: change Add Recipe button's text to Update Recipe
		}
		//TODO: set up list view to display ingredients in recipe
		//ingredientList = (ListView) findViewById(R.id.inventory_list);
		//adapter = new IngredientItemAdapter(this, recipeHandler.getRecipeIngredients());
		//ingredientList.setAdapter(adapter);
		//ingredientList.setOnItemClickListener(null);	//FIXME: implement OnItemClickListner
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recipes_add_new_recipe, menu);
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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// React to input from the ingredient selector.
		if (requestCode == SELECT_ITEM_REQUEST) {
			if (resultCode == RESULT_OK) {
				// If the user confirmed, add the selected ingredient with the
				// specified quantity to the current recipe's ingredient list.
				String name;
				int quantity;
				name = data.getStringExtra(SelectItemActivity.KEY_RETURN_NAME);
				quantity = data.getIntExtra(SelectItemActivity.KEY_RETURN_QUANTITY, -1);
				//TODO: add selected item to ingredients list
				//recipeHandler.addItem(new IngredientItem(name, quantity));
				refreshIngredientsList();
			}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		// update list of selected ingredients
		refreshIngredientsList();
	}
	
	private void refreshIngredientsList() {
		//TODO: refresh ingredients list
	}
	
	//TODO: handle results from called activities
	
	/** Called when the user clicks the Add Ingredient button */
	public void addIngredient(View view) {
		// When the user clicks the Add Ingredient button, display a list of
		// items in the inventory.
		Intent intent = new Intent(this, SelectItemActivity.class);
		// Ensure that any of the following dialogs can't be navigated back to.
		//TODO: decide if this is actually necessary
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(intent, SELECT_ITEM_REQUEST);	//TODO: change call to expect a return result
	}
	
	/** Called when the user clicks the Add Recipe button */
	public void addRecipe(View view) {
		//TODO: Send recipe name to recipe system
		
		//TODO: Call finishRecipe() method
	}
}
