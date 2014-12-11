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
 *  12/09/2014
 *  Modified how recipes are handled (function, created new class var)
 *  currentRecipe is used to point to a particular recipe instead of 
 *  requiring specification by name. Functions were modified to allow
 *  calling them with reference to currentRecipe instead of a recipe
 *  name
 *  @author Jesse
 *  *
 *  12/10/2014
 *  Modified query inventory function so that all it needs is to call
 *  the getStockedItems and getExpiredItems functions in order
 *  to check if an item is in stock, expired, or out of stock.
 *  Also added constants for those values.
 *  @author Jesse
 *  *
 */
package com.example.pantrysystem;

import java.util.ArrayList;

public class RecipeHandler {
	// List of all recipes in application
	private ArrayList<Recipe> RecipeList;
	private Recipe currentRecipe;
	private final int IN_STOCK = 1, OUT_OF_STOCK = 0, EXPIRED = -1;
	
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
	
	private Recipe GetCurrentRecipe() {
		return this.currentRecipe;
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
	
	public void setCurrentRecipe(Recipe r) {
		this.currentRecipe = new Recipe(r);
	}
	
	/* Menu selection for adding a new recipe to the system
	 * The ingredients will be updated in another function
	 * Initializes the currentRecipe variable within the
	 * RecipeHandler
	 */
	public void createNewRecipe() {
		//create a new empty recipe
		this.setCurrentRecipe(new Recipe());
	}
	
	/* part 2 of adding a new recipe, this method updates
	 * an existing recipe with a list of ingredients
	 * *
	 * @Depreciated
	 */
	 public void fillRecipe(ArrayList<IngredientItem> items) {
		// set the supplied ingredients as the ingredients for the recipe
		this.GetCurrentRecipe().setIngredients(items);
	}
	
	/* alternative part 2 to adding a new recipe
	 * adds one ingredient at a time to a recipe
	 */
	public void addItem(IngredientItem item) {
		// set the supplied ingredients as the ingredients for the recipe
		this.GetCurrentRecipe().addIngredient(item);	
	}
	
	/* set the recipeName of the currentRecipe
	 * part 3 to adding a new recipe
	 */
	public void addName(String name) {
		this.GetCurrentRecipe().setName(name);
	}
	
	/* Called at the end of creating or editing a recipe
	 * will update file of recipes and return true or false
	 * depending on success of function
	 */
	public boolean finishRecipe() {
		//TODO save recipe to database
		
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
	
	/* version 2 of the modify function, assumes that the currentRecipe
	 * is the specified recipe
	 */
	public void modifyRecipe(int type, IngredientItem item) {
		// create a temporary version of the modified recipe
		Recipe tempRecipe = new Recipe(this.GetCurrentRecipe());

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
	
	/* version 2 of the delete recipe function
	 * assumes that the current recipe is the specified recipe to 
	 * delete
	 */
	public void deleteRecipe() {
		int target = this.GetRecipeIndex(this.GetCurrentRecipe().getName());
		
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
				case IN_STOCK:
					inStockItems.add(tempList.get(i));
					break;
				case OUT_OF_STOCK:
					missingItems.add(tempList.get(i));
					break;
				case EXPIRED:
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
	
	/* version 2 of the check inventory function 
	 * that assumes that the current recipe is the recipe to check
	 * against the inventory
	 */
	public ArrayList<ArrayList<IngredientItem>> checkInventory() {
		ArrayList<IngredientItem> tempList = 
			new ArrayList<IngredientItem>(this.GetCurrentRecipe().getList());
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
		int stock = 0;
		// query the inventory system for the stock of the specified item
		// TODO InventoryAccessInterface.getStockedItems()
		ArrayList<Item> currentStock = new ArrayList<Item>();
		
		// loop through supplied inventory to try to find an instance of the item
		for (int i = 0; i < currentStock.size(); i++) {
			// check if names match
			if (currentStock.get(i).getName().equalsIgnoreCase(item.getName()))
			{
				// add the quantity to the stock
				stock += currentStock.get(i).getQuantity();
				
				// check if we have sufficient quantity
				if (stock >= item.getQuantity())
					return IN_STOCK;
			}
		}
		
		// query the inventory system to see if the item is expired
		// TODO InventoryAccessInterface.getExpiredItems()
		ArrayList<Item> expiredStock = new ArrayList<Item>();
		
		// loop through supplied inventory to try to find an instance of the item
		for (int i = 0; i < expiredStock.size(); i++) {
			// check if names match
			if (expiredStock.get(i).getName().equalsIgnoreCase(item.getName()))
				return EXPIRED;
		}

		// if not in stock, and not expired then the item must be out of stock
		return OUT_OF_STOCK;
	}

}
