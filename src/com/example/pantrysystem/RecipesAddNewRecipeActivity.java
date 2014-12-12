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
 *  ---------------------------------------------------------------------------
 *  12/11/2014
 *  Added context menu to list of ingredients, and refined outlined code some
 *  more.
 *  -Julian
 *  ***************************************************************************
 *  
 */
package com.example.pantrysystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class RecipesAddNewRecipeActivity extends ActionBarActivity {
	static final String KEY_DISPLAY_RECIPE = "KEY_DISPLAY_RECIPE";
	static final int SELECT_ITEM_REQUEST = 1;
	static final int CHANGE_ITEM_REQUEST = 2;
	
	private EditText nameInput;
	private ListView ingredientList;
	private IngredientItemAdapter adapter;
	private RecipeHandler recipeHandler;
	private IngredientItem selectedIngredient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipes_add_new_recipe);
		// Initialize GUI element variables
		this.recipeHandler = new RecipeHandler();
		this.nameInput = (EditText) findViewById(R.id.add_recipe_name_input);
		this.ingredientList = (ListView) findViewById(R.id.ingredients_list);
		
		//TODO: If a recipe name was passed to the activity, load that recipe and
		// display it.  Otherwise, display an empty editor for creating a new
		// recipe.
		String recipeName = getIntent().getStringExtra(KEY_DISPLAY_RECIPE);
		if (recipeName.equals("")) {
			// Set up recipeHandler to create a new recipe
			recipeHandler.createNewRecipe();
		} else {
			//TODO: set up recipeHandler to edit an existing recipe
			//recipeHandler.editRecipe(recipeName);
			//TODO: get recipe's name and display it in the name input field
			//this.nameInput.setText(this.recipeHandler.getRecipeName());
			// Change Add Recipe button's text to Update Recipe
			Button addRecipeButton;
			addRecipeButton = (Button) findViewById(R.id.recipes_add_recipe_button);
			addRecipeButton.setText("Update Recipe");	//TODO: use string resource
		}
		//TODO: set up list view to display ingredients in recipe
		//adapter = new IngredientItemAdapter(this, recipeHandler.getRecipeIngredients());
		ingredientList.setAdapter(adapter);
		// Set up the recipe context menu
		registerForContextMenu(ingredientList);
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
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		// Inflate the recipe context menu.
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.ingredient_context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// Handle context menu item clicks.
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		selectedIngredient = (IngredientItem) ingredientList.getItemAtPosition(info.position);
		switch (item.getItemId()) {
		case R.id.change_ingredient:
			Intent intent = new Intent(this, SelectItemActivity.class);
			// Ensure that any of the following dialogs can't be navigated back to.
			//TODO: decide if this is actually necessary
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra(SelectItemActivity.KEY_CHANGED_ITEM, selectedIngredient.getName());
			startActivityForResult(intent, CHANGE_ITEM_REQUEST);
			return true;
		case R.id.change_quantity:
			//TODO: Open Change Quantity dialog
			showChangeIngredientQuantityDialog(selectedIngredient.getName());
			return true;
		case R.id.remove_ingredient:
			//TODO: Ask for confirmation
			//recipeHandler.removeIngredient(ingredient.getName());
			{	// Testing
				recipeHandler.modifyRecipe(3, selectedIngredient);
			}
			return true;
		default:
			return super.onContextItemSelected(item);
		}
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
				recipeHandler.addItem(new IngredientItem(name, quantity));
				refreshIngredientsList();
			}
		} else if (requestCode == CHANGE_ITEM_REQUEST) {
			if (resultCode == RESULT_OK) {
				// Change item's quantity and update list.
				String oldIngredientName;
				String newIngredientname;
				oldIngredientName = data.getStringExtra(SelectItemActivity.KEY_CHANGED_ITEM);
				newIngredientname = data.getStringExtra(SelectItemActivity.KEY_RETURN_NAME);
				//recipeHandler.changeIngredient(oldIngredientName, newIngredientname);
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
		//adapter.updateItems(recipeHandler.getRecipeIngredients());
	}
	/** Called when the user clicks the Add Ingredient button */
	public void addIngredient(View view) {
		// When the user clicks the Add Ingredient button, display a list of
		// items in the inventory.
		Intent intent = new Intent(this, SelectItemActivity.class);
		// Ensure that any of the following dialogs can't be navigated back to.
		//TODO: decide if this is actually necessary
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(intent, SELECT_ITEM_REQUEST);
	}
	/** Called when the user clicks the Add Recipe button */
	public void addRecipe(View view) {
		//TODO: Send recipe name to recipe system
		//recipeHandler.setRecipeName(this.nameInput.getText().toString());
		recipeHandler.finishRecipe();
		finish();
	}
	/** Displays the Change Ingredient Quantity dialog. */
	private void showChangeIngredientQuantityDialog(String title) {
		// Create the input dialog
		final EditText input = new EditText(RecipesAddNewRecipeActivity.this);
		input.setText(Integer.toString(selectedIngredient.getQuantity()));
		AlertDialog.Builder alert = new AlertDialog.Builder(RecipesAddNewRecipeActivity.this);
		alert.setTitle(title);
		alert.setMessage("Change quantity to what?");	//TODO: use string resource
		alert.setView(input);
		// Behavior for confirmation button
		alert.setPositiveButton(R.string.button_change, new DialogInterface.OnClickListener() {
			/** Listener for the "Change" button */
			@Override
			public void onClick(DialogInterface dialog, int which) {
				int quantity;
				try {
					quantity = Integer.parseInt(input.getText().toString());
					//recipeHandler.changeIngredientQuantity(selectedIngredient.getName(), quantity);
					dialog.dismiss();
				} catch (NumberFormatException e) {
					displayError(R.string.error_positive_number);
				}
			}
		});
		// Behavior for "Cancel" button
		alert.setNegativeButton(R.string.button_cancel, new CancelButtonListener());
		alert.show();
	}
	/** Listener for the Add and Remove Item dialogs' Cancel buttons. */
	class CancelButtonListener implements DialogInterface.OnClickListener {
		/** Listener for the "Cancel" button */
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	}
	/** Displays an alert with an error message. */
	private void displayError(int messageId) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(messageId)
		       .setCancelable(false)
		       .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.dismiss();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
}
