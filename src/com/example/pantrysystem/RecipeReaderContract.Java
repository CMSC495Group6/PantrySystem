/** Recipe Class for Pantry System android application
 *  CMSC 495
 *  Group 6
 *  12/09/2014
 *  **********
 *  Database related class for storing the recipes. Defines the tables
 *  to be used within the Contract class, and the inside classes are the
 *  actual tables.
 *  *
 *  Structure is: [Recipe] <--> [List] <--> [Ingredients]
 *  Recipe: RecipeKey, RecipeName
 *  List: RecipeKey, IngredientKey, Quantity
 *  Ingredients: IngredientKey, IngredientName
 *  **
 *  Revisions: 
 *  *
 *  12/10/2014
 *  Added header comment and revision history. 
 *  @author Jesse
 */

package com.example.pantrysystem;

public final class RecipeReaderContract {
	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "recipes.db";
    
	// empty constructor to prevent instantiation
	public RecipeReaderContract() {}
	
	/* inside classes to define tables
	 * table and columns names are stored as variables to be placed inside
	 * another variable that contains the table creation string
	 */
	
	/* recipe table
	 * contains recipe names, and recipe key
	 */
	public static abstract class RecipeTable implements BaseColumns {
		public static final String TABLE_NAME = "recipe";
		public static final String COLUMN_NAME_RECIPE_ID = "recipeId";
		public static final String COLUMN_NAME_RECIPE_NAME = "recipeName";
		
		public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_RECIPE_ID + " TEXT," +
                COLUMN_NAME_RECIPE_NAME + " TEXT," + " )";
		public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
	
	/* list table
	 * contains all records within a recipe: recipe key, ingredient key, quantity
	 */
	public static abstract class ListTable implements BaseColumns {
		public static final String TABLE_NAME = "list";
		public static final String COLUMN_NAME_RECIPE_ID = "recipeId";
		public static final String COLUMN_NAME_INGREDIENT_ID = "ingredientId";
		public static final String COLUMN_NAME_QUANTITY = "quantity";
		
		public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_RECIPE_ID + " TEXT," +
                COLUMN_NAME_INGREDIENT_ID + " TEXT," +
                COLUMN_NAME_QUANTITY + " TEXT," + " )";
		public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
	
	/* ingredient table
	 * contains ingredient key and name
	 */
	public static abstract class IngredientTable implements BaseColumns {
		public static final String TABLE_NAME = "recipe";
		public static final String COLUMN_NAME_INGREDIENT_ID = "ingredientId";
		public static final String COLUMN_NAME_INGREDIENT_NAME = "ingredientName";
		
		public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_INGREDIENT_ID + " TEXT," +
                COLUMN_NAME_INGREDIENT_NAME + " TEXT," + " )";
		public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
