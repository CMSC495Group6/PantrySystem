/** RecipeHandler Class for Pantry System android application
 *  CMSC 495
 *  Group 6
 *  12/05/2014
 *  **********
 *  RecipeHandler will manage the storage of recipes within
 *  the pantry system application. It contains a list of recipes
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
	
	// Menu selection for adding a recipe to the system
	public void addRecipe () {
		Recipe newRecipe = new Recipe();
		newRecipe.setName(promptName());
		
		boolean continueAdding = true;
		do
		{
			newRecipe.addIngredient(promptItem());
			if (newRecipe.getList().size() > 0) 
				continueAdding = promptLoop();
		} while(continueAdding);
			
		//TODO Update database?
	}
	
	// Menu selection for modifying a recipe
	public void modifyRecipe() {
		// create a temporary version of the modified recipe
		Recipe tempRecipe = new Recipe(this.GetRecipe(promptName()));
		
		boolean continueAdding = true;
		do {
			switch(promptModify()) {
				case 1: 
					tempRecipe.updateQuantity(promptItem());
					break;
				case 2: 
					tempRecipe.addIngredient(promptItem());
					break;
				case 3:
					tempRecipe.removeIngredient(promptItem());
					break;
				case 4:
					tempRecipe.changeIngredient(promptItem());
					break;
				default:
					// TODO add default interaction?
					break;					
			}
			
			// only allow saving the recipe if it meets the ingredient list requirement
			if (tempRecipe.getList().size() > 0) 
				continueAdding = promptLoop();
			else {
				// TODO prompt user to fix lack of ingredients
			}
				
		} while(continueAdding);
		
		// find and replace the list item
		int index = this.GetRecipeIndex(tempRecipe.getName());
		
		// check if specified recipe was found
		if (index >= 0)
			this.RecipeList.set(index, tempRecipe);
		else
		{
			// FIXME this shouldn't happen, since user specifies an existing recipe
		}
		
		// TODO Update database?
	}
	
	// Menu selection for removing a recipe
	public void deleteRecipe() {
		int target = this.GetRecipeIndex(promptName());
		
		// check if specified recipe was found
		if (target >= 0)
			this.RecipeList.remove(target);
		else
		{
			// FIXME this shouldn't happen, since user specifies an existing recipe
		}
		
		//TODO Update database?
	}
	
	// Menu selection for checking if inventory has ingredients
	public void checkInventory() {
		ArrayList<FullItem> tempList = new ArrayList<FullItem>(this.GetRecipe(promptName()).getList());
		ArrayList<FullItem> missingItems = new ArrayList<FullItem>();
		ArrayList<FullItem> expiredItems = new ArrayList<FullItem>();
		ArrayList<FullItem> inStockItems = new ArrayList<FullItem>();
		int size = tempList.size();
		
		// loop through the ingredients and place them into appropriate lists
		for (int i = 0; i < size; i++) 
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
				default:
					//TODO add default behavior?
					break;
			}
		
		// check if any missing or expired items were found
		if (missingItems.isEmpty() && expiredItems.isEmpty())
		{
			//TODO display "all ingredients found" dialog
		}
		else
		{
			//TODO send GUI list of missing items
			//TODO send GUI list of expired items
		}
	}
	
	/* interacts with the inventory system to determine if items are
	 * in/out of stock, or expired (in that order), with expired items
	 * taking precedence
	 */
	private int queryInventory(FullItem item) {
		int stock = 1;
		// TODO query the inventory system for the stock of the specified item
		
		// TODO query the inventory system to see if the item is expired
		// FIXME are expired and out of stock exclusive?
		return stock;
	}

	/* obtains name of recipe, does not verify if name is taken
	 * currently, only interacts with GUI
	 */
	private String promptName() {
		String newName = "";
		
		// TODO interact with GUI to get name
		
		return newName;
	}
	
	/* obtains type of modification the user will be performing
	 * for the recipe
	 */
	private int promptModify() {
		int type = 0;
		// TODO interact with GUI to obtain modifcation type
		// 1 = update quantity
		// 2 = add new ingredient
		// 3 = remove ingredient
		// 4 = change ingredient
		
		return type;
	}
	
	/* obtains an ingredient in the recipe from GUI
	 * returns item type (name and quantity)
	 */
	private FullItem promptItem() {
		FullItem newItem = new FullItem();
		
		//TODO possible requirement to specify if item must be existing or new?
		
		//TODO interact with GUI to get item name
		newItem.setName("NO NAME");
		
		//TODO interact with GUI to get item quantity
		newItem.setQuantity(1);
		
		return newItem;
	}
	
	/* Asks user if they want to continue doing the loop,
	 * whether it's adding, removing, or modifying
	 * FIXME Not sure if needed
	 */
	private boolean promptLoop() {
		String userInput = "N";
		
		//TODO interact with GUI to get loop feedback
		if (userInput.equalsIgnoreCase("N"))
			return false;
		else
			return true;
	}

}
