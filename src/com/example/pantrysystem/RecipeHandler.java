/** RecipeHandler Class for Pantry System android application
 *  CMSC 495
 *  Group 6
 *  12/05/2014
 *  **********
 *  RecipeHandler will manage the storage of recipes within
 *  the pantry system application. It contains a list of recipes
 *  **
 *  Revisions: 
 *  *
 *  12/07/2014
 *  Converted item types from FullItem to IngredientItem, due to 
 *  expiration date not being required within a recipes ingredient list.
 *  Also rewrote sections to better suit the recipe system being called 
 *  by the GUI.
 *  @author Jesse
 *  *
 */
package com.example.pantrysystem;

import java.util.ArrayList;

public class RecipeHandler {
	// List of all recipes in application
	private ArrayList<Recipe> RecipeList;
	
	// Constructor
	public RecipeHandler() {
		this.RecipeList = new ArrayList<Recipe>();
	}
	
	public RecipeHandler(Recipe r) {
		this.RecipeList = new ArrayList<Recipe>();
		this.RecipeList.add(r);
	}
	
	private int GetListSize() {
		return this.RecipeList.size();
	}
	
	private int GetRecipeIndex(String s) {
		int size = this.GetListSize();
		
		// search the list for recipe of specified name
		for (int i = 0; i < size; i++)
			if (this.RecipeList.get(i).getName().equalsIgnoreCase(s))
				return i;
		
		return -1;
	}
	
	// Find a recipe by name from the list of recipes, return null if DNE
	public Recipe GetRecipe(String s) {
		int size = this.GetListSize();
		
		// search the list for recipe of specified name
		for (int i = 0; i < size; i++)
			if (this.RecipeList.get(i).getName().equalsIgnoreCase(s))
				return this.RecipeList.get(i);
		
		// recipe not found in list
		return null;
	}
	
	/* Menu selection for adding a new recipe to the system
	 * the function requires the name of the recipe
	 * The ingredients will be updated in another function
	 */
	public void createNewRecipe(String name) {
		//create a new recipe with an empty ingredients list
		RecipeList.add(new Recipe(name));	
	}
	
	/* part 2 of adding a new recipe, this method updates
	 * an existing recipe with a list of ingredients
	 * *
	 * @Depreciated
	 */
	 public void fillRecipe(String name, ArrayList<IngredientItem> items) {
		// find the specified recipe in the recipe list
		int index = GetRecipeIndex(name);
		
		// set the supplied ingredients as the ingredients for the recipe
		this.RecipeList.get(index).setIngredients(items);
	}
	
	/* alternative part 2 to adding a new recipe
	 * adds one ingredient at a time to a recipe
	 */
	public void addItem(String name, IngredientItem item) {
		// find the specified recipe in the recipe list
		int index = GetRecipeIndex(name);
		
		// check if recipe was found
		if (index >= 0)
			// set the supplied ingredients as the ingredients for the recipe
			this.RecipeList.get(index).addIngredient(item);	
	}
	
	/* Called at the end of creating or editing a recipe
	 * will update file of recipes and return true or false
	 * depending on success of function
	 */
	public boolean finishRecipe() {
		// TODO update recipe database/file
		return true;
	}
	
	/* Menu selection for modifying a recipe by name
	 * Function is called specifying the recipe name, the type of
	 * Modification requested, and the item being modified.
	 */
	public void modifyRecipe(String name, int type, IngredientItem item) {
		// create a temporary version of the modified recipe
		Recipe tempRecipe = new Recipe(this.GetRecipe(name));

		switch(type) {
			case 1: 
				tempRecipe.updateQuantity(item);
				break;
			case 2: 
				tempRecipe.addIngredient(item);
				break;
			case 3:
				tempRecipe.removeIngredient(item);
				break;
			case 4:
				tempRecipe.changeIngredient(item);
				break;				
		}
		
		// find and replace the list item
		int index = this.GetRecipeIndex(tempRecipe.getName());
		
		// check if specified recipe was found
		if (index >= 0)
			this.RecipeList.set(index, tempRecipe);
	}
	
	/* Menu selection for removing a recipe by name
	 * 
	 */
	public void deleteRecipe(String name) {
		int target = this.GetRecipeIndex(name);
		
		// check if specified recipe was found
		if (target >= 0)
			this.RecipeList.remove(target);
	}
	
	/* Menu selection for checking if inventory has ingredients
	 * sorts ingredients into 3 distinct lists, then returns all the lists
	 * as a multidimensional arraylist (instock, missing, expired).
	 */
	public ArrayList<ArrayList<IngredientItem>> checkInventory(String name) {
		ArrayList<IngredientItem> tempList = new ArrayList<IngredientItem>(this.GetRecipe(name).getList());
		ArrayList<IngredientItem> missingItems = new ArrayList<IngredientItem>();
		ArrayList<IngredientItem> expiredItems = new ArrayList<IngredientItem>();
		ArrayList<IngredientItem> inStockItems = new ArrayList<IngredientItem>();
		ArrayList<ArrayList<IngredientItem>> allLists = new ArrayList<ArrayList<IngredientItem>>();
		int size = tempList.size();
		
		// loop through the ingredients and place them into appropriate lists
		for (int i = 0; i < size; i++) { 
			// handle queries through a hierarchy
			switch(queryInventory(tempList.get(i)))
			{
				case 1:
					inStockItems.add(tempList.get(i));
					break;
				case 0:
					missingItems.add(tempList.get(i));
					break;
				case -1:
					expiredItems.add(tempList.get(i));
					break;
			}
		}
		
		// add all lists to the multidimensional arraylist
		allLists.add(inStockItems);
		allLists.add(missingItems);
		allLists.add(expiredItems);
		
		return allLists;
	}
	
	/* interacts with the inventory system to determine if items are
	 * in/out of stock, or expired (in that order), with expired items
	 * taking precedence
	 */
	private int queryInventory(IngredientItem item) {
		int stock = 1;
		// TODO query the inventory system for the stock of the specified item
		
		// TODO query the inventory system to see if the item is expired
		// FIXME are expired and out of stock exclusive?
		return stock;
	}

}
