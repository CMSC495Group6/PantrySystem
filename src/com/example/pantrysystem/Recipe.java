/** Recipe Class for Pantry System android application
 *  CMSC 495
 *  Group 6
 *  12/05/2014
 *  **********
 *  Recipe class stores a list of ingredient items (name, quantity)
 *  and the name of the recipe. It is designed to be implemented 
 *  with the RecipeHandler class, which will contain all the recipes
 *  and handle adding or removing recipes from the application.
 *  **
 *  Revisions: 
 *  *
 *  12/07/2014
 *  Converted item types from FullItem to IngredientItem, due to 
 *  expiration date not being required within a recipes ingredient list.
 *  Removed notifyUser function
 *  @author Jesse
 *  *
 *  
 */
package com.example.pantrysystem;

import java.util.ArrayList;

public class Recipe {
	// name of recipe
	private String name;
	
	// list of items needed to make recipe
	private ArrayList<IngredientItem> ingredients;
	
	// Constructors
	public Recipe() {
		this.ingredients = new ArrayList<IngredientItem>();
	}	
	
	public Recipe(ArrayList<IngredientItem> ingredients) {
		this.ingredients = new ArrayList<IngredientItem>(ingredients);
	}
	
	public Recipe(Recipe r) {
		this.ingredients = new ArrayList<IngredientItem>(r.getList());
		this.name = r.getName();
	}
	
	public Recipe(String s) {
		this.ingredients = new ArrayList<IngredientItem>();
		this.name = s;
	}
	
	// Getters 
	public String getName() {
		return this.name;
	}
	
	public ArrayList<IngredientItem> getList() {
		return this.ingredients;
	}
	
	// Setters
	public void setName(String s) {
		this.name = s;
	}
	
	public void addIngredient(IngredientItem i) {
		// check if recipe already contains item
		if (this.ingredients.contains(i))
		{
			//TODO add to quantity or prompt user to redo?
		}
		else
			this.ingredients.add(i);
	}

	public void setIngredients(ArrayList<IngredientItem> newList) {
		this.ingredients = new ArrayList<IngredientItem>(newList);
	}
	
	private int getSize() {
		return this.ingredients.size();
	}
	
	private int getIngredientIndex(IngredientItem i) {
		int size = this.getSize();
		
		// loop through the ingredient list and find item with same name
		for (int index = 0; index < size; index++) 
			if (this.getList().get(index).getName().equalsIgnoreCase(i.getName()))
				return index;
		
		// if not found return an invalid index
		return -1;
	}

	/* updates a specified ingredient quantity to a new set amount
	 * Does not test for valid quantity
	 */
	public void updateQuantity(IngredientItem i) {
		int target = this.getIngredientIndex(i);

		// check if the item is found
		if (target >= 0)
			this.ingredients.get(target).setQuantity(i.getQuantity());
	}

	/* removes a specified ingredient
	 * 
	 */
	public void removeIngredient(IngredientItem i) {
		int target = this.getIngredientIndex(i);
		
		// check if the item is found
		if (target >= 0)
			this.ingredients.remove(target);
	}

	/* converts a specified ingredient into a specified one
	 * does not modify quantity, nor does it test quantity
	 */
	public void changeIngredient(IngredientItem i) {
		int target = this.getIngredientIndex(i);
		
		// check if the item is found
		if (target >= 0)
			this.ingredients.get(target).setName(i.getName());
	}
}
