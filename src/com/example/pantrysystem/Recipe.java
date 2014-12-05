/** Recipe Class for Pantry System android application
 *  CMSC 495
 *  Group 6
 *  12/05/2014
 *  **********
 *  Recipe class stores a list of ingredient items (name, quantity)
 *  and the name of the recipe. It is designed to be implemented 
 *  with the RecipeHandler class, which will contain all the recipes
 *  and handle adding or removing recipes from the application.
 */
package com.example.pantrysystem;

import java.util.ArrayList;

public class Recipe {
	// name of recipe
	private String name;
	
	// list of items needed to make recipe
	//TODO extend item class into a RecipeItem class?
	private ArrayList<Item> ingredients;
	
	// Constructors
	public Recipe() {
		this.ingredients = new ArrayList<Item>();
	}	
	
	public Recipe(ArrayList<Item> ingredients) {
		this.ingredients = new ArrayList<Item>(ingredients);
	}
	
	public Recipe(Recipe r) {
		this.ingredients = new ArrayList<Item>(r.getList());
		this.name = r.getName();
	}
	
	// Getters 
	public String getName() {
		return this.name;
	}
	
	public ArrayList<Item> getList() {
		return this.ingredients;
	}
	
	// Setters
	public void setName(String s) {
		this.name = s;
	}
	
	public void addIngredient(Item i) {
		// check if recipe already contains item
		if (this.ingredients.contains(i))
		{
			notifyUser(1);
			//TODO add to quantity or prompt user to redo?
		}
		else
			this.ingredients.add(i);
	}

	public void setIngredients(ArrayList<Item> newList) {
		this.ingredients = new ArrayList<Item>(newList);
	}
	
	/* this function is used to handle unexpected events
	 * based on the error type.
	 */
	void notifyUser(int err) {
		if (err == 1)
			System.out.println("Recipe already contained ingredient");
		else if (err == 2)
			System.out.println("Recipe does not contain ingredient");
	}
	
	private int getSize() {
		return this.ingredients.size();
	}
	
	private int getIngredientIndex(Item i) {
		int size = this.getSize();
		
		// loop through the ingredient list and find item with same name
		for (int index = 0; index < size; index++) 
			if (this.getList().get(index).getName().equalsIgnoreCase(i.getName()))
				return index;
		
		// if not found return an invalid index
		notifyUser(2);
		return -1;
	}

	/* updates a specified ingredient quantity to a new set amount
	 * Does not test for valid quantity
	 */
	public void updateQuantity(Item i) {
		int target = this.getIngredientIndex(i);

		// check if the item is found
		if (target >= 0)
			this.ingredients.get(target).setQuantity(i.getQuantity());
		else
			notifyUser(2);
	}

	/* removes a specified ingredient
	 * 
	 */
	public void removeIngredient(Item i) {
		int target = this.getIngredientIndex(i);
		
		// check if the item is found
		if (target >= 0)
			this.ingredients.remove(target);
		else
			notifyUser(2);
	}

	/* converts a specified ingredient into a specified one
	 * does not modify quantity, nor does it test quantity
	 */
	public void changeIngredient(Item i) {
		int target = this.getIngredientIndex(i);
		
		// check if the item is found
		if (target >= 0)
			this.ingredients.get(target).setName(i.getName());
		else
			notifyUser(2);
	}
}
