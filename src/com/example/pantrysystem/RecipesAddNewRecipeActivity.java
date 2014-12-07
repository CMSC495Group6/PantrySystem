package com.example.pantrysystem;


import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class RecipesAddNewRecipeActivity extends ActionBarActivity {
	private ListView listView;
	private FullItemAdapter adapter;	// TODO: use IngredientItemAdapter

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recipes_add_new_recipe);
		
		//TODO: call startRecipe() method and assign recipe to adapter
		
		//TODO: set up list view to display ingredients in recipe
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
	protected void onResume() {
		super.onResume();
		// update list of selected ingredients
		refreshIngredientsList();
	}
	
	private void refreshIngredientsList() {
		//TODO: refresh ingredients list
	}
	
	/** Called when the user clicks the Add Ingredient button */
	public void addIngredient(View view) {
		Intent intent = new Intent(this, SelectItemActivity.class);
		// Ensure that any of the following dialogs can't be navigated back to.
		//TODO: decide if this is actually necessary
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Add Recipe button */
	public void addRecipe(View view) {
		//TODO: Send recipe name to recipe system
		
		//TODO: Call finishRecipe() method
	}
}
