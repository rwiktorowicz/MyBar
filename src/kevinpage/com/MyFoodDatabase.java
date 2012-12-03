package kevinpage.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This is the class that deals entirely with the database for Food.
 * TODO Refactor code to make more SRP; don't return Cursors
 * @author Rich
 *
 */
public class MyFoodDatabase {
	
	private final DatabaseOpenHelper mDatabaseHelper; // used for queries later
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "foods.db";

	/**
	 * Constructor
	 * 
	 * @param context
	 *            The Context within which to work, used to create the DB
	 */
	public MyFoodDatabase(Context context) {
		mDatabaseHelper = new DatabaseOpenHelper(context);
		mDatabaseHelper.getReadableDatabase();
	}

	// TODO Build out QUERIES here

	/**
	 * Insert a food into the database with the appropriate fields.
	 * @param name The name of the food
	 * @param serving The serving of the food
	 * @param calories The calories of the food
	 * @param fat the fat content
	 * @param cholesterol The amount of cholesterol
	 * @param sodium The amount of sodium
	 * @param carbs The amount of carbohydrates
	 * @param sugar The amount of sugar
	 * @param protein The amount of protein
	 * @return True if successfully inserted
	 */
	public boolean insertFood(String name, String serving, String calories , String fat, String cholesterol, String sodium,
			String carbs, String sugar, String protein){
		
		long drink_id = -1;
		
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();	
		
		Log.d("Database", "Got writable database");
		
		ContentValues foodvalues = new ContentValues();
		
		foodvalues.put(FeedReaderContract2.FeedEntryFoods.KEY_FOOD, name);
		foodvalues.put(FeedReaderContract2.FeedEntryFoods.KEY_SERVING, serving);
		foodvalues.put(FeedReaderContract2.FeedEntryFoods.KEY_CALORIES, calories);
		foodvalues.put(FeedReaderContract2.FeedEntryFoods.KEY_FAT, fat);
		foodvalues.put(FeedReaderContract2.FeedEntryFoods.KEY_CHOLESTEROL, cholesterol);
		foodvalues.put(FeedReaderContract2.FeedEntryFoods.KEY_SODIUM, sodium);
		foodvalues.put(FeedReaderContract2.FeedEntryFoods.KEY_CARBS, carbs);
		foodvalues.put(FeedReaderContract2.FeedEntryFoods.KEY_SUGAR, sugar);
		foodvalues.put(FeedReaderContract2.FeedEntryFoods.KEY_PROTEIN, protein);
		
		try{
			db.insertOrThrow(FeedReaderContract2.FeedEntryFoods.TABLE1, null, foodvalues);
		}catch(SQLException ex){
			Log.d("Exception", "Could not insert drink-ingredient");
			return false;
		}
	
			
		
		
		Log.d("Got through", "ALL INFO ADDED");
		Log.d("Food Name", FeedReaderContract2.FeedEntryFoods.TABLE1);
		return true;
	}
	
	
	
	/**
	 * Returns a Cursor over the specified food and its rating
	 * @param selectionArgs The name of the food
	 * @return Cursor over food column
	 */
	public Cursor getFoodInfo(String selectionArgs){
		SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
		String[] projection = new String[] {FeedReaderContract2.FeedEntryFoods.KEY_FOOD, FeedReaderContract2.FeedEntryFoods.KEY_SERVING, FeedReaderContract2.FeedEntryFoods.KEY_ID1, FeedReaderContract2.FeedEntryFoods.KEY_CALORIES,
				FeedReaderContract2.FeedEntryFoods.KEY_FAT, FeedReaderContract2.FeedEntryFoods.KEY_CHOLESTEROL, FeedReaderContract2.FeedEntryFoods.KEY_SODIUM, FeedReaderContract2.FeedEntryFoods.KEY_CARBS,
				FeedReaderContract2.FeedEntryFoods.KEY_SUGAR, FeedReaderContract2.FeedEntryFoods.KEY_PROTEIN,};
		String selection = FeedReaderContract2.FeedEntryFoods.KEY_FOOD + " =?";
		String[] singleSelection = new String[] {selectionArgs};
		
		Cursor cursor = db.query(FeedReaderContract2.FeedEntryFoods.TABLE1, projection, selection, singleSelection, null, null, null);
		if (cursor == null) {
			return null;
		} else if (!cursor.moveToFirst()) {
			cursor.close();
			return null;
		}
		db.close();
		return cursor;
	}
	
	/**
	 * Returns a Cursor over all of the foods
	 * @return a Cursor over all of the foods in alphabetical order
	 */
	public Cursor getAllFoods(){
		SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
		String[] projection = new String[] { FeedReaderContract2.FeedEntryFoods.KEY_FOOD, FeedReaderContract2.FeedEntryFoods.KEY_ID1 };
		String ordering = " COLLATE NOCASE";
		
		Cursor cursor = db.query(FeedReaderContract2.FeedEntryFoods.TABLE1, projection, null, null, null, null, FeedReaderContract2.FeedEntryFoods.KEY_FOOD + ordering);
		
		if (cursor == null) {
			return null;
		} else if (!cursor.moveToFirst()) {
			cursor.close();
			return null;
		}
		db.close();
		return cursor;
	}

	
	
	/**
	 * Deletes the corresponding food from the all-foods table ONLY
	 * @param drinkName The name of the food to delete
	 * @return The number row of food
	 */
	public long deleteFood(String foodName){
		SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
		String selection = FeedReaderContract2.FeedEntryFoods.KEY_FOOD + " = ?";
		String[] selectionArgs = {foodName};
		
		return db.delete(FeedReaderContract2.FeedEntryFoods.TABLE1, selection, selectionArgs);
	}

	////////////////////////////////////////////////////////////////////////////////////

	/**
	 * This class is used to help create, get queries, and update or delete data
	 * from the database
	 * 
	 * @author Rich
	 * 
	 */
	public static class DatabaseOpenHelper extends SQLiteOpenHelper {

		private final Context mHelperContext;
		private SQLiteDatabase mDatabase;
		//TODO add UNIQUE qualifier
		/** SQL to create first table of foods */
		private static final String TABLE_CREATE1 = "CREATE TABLE "
				+ FeedReaderContract2.FeedEntryFoods.TABLE1 + " ("
				+ FeedReaderContract2.FeedEntryFoods.KEY_ID1	+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ FeedReaderContract2.FeedEntryFoods.KEY_FOOD + " TEXT UNIQUE NOT NULL, "
				+ FeedReaderContract2.FeedEntryFoods.KEY_SERVING	+ " TEXT NOT NULL, "
				+ FeedReaderContract2.FeedEntryFoods.KEY_CALORIES + " TEXT NOT NULL, "
			    + FeedReaderContract2.FeedEntryFoods.KEY_FAT + " TEXT NOT NULL, "
				+ FeedReaderContract2.FeedEntryFoods.KEY_CHOLESTEROL + " TEXT NOT NULL, "
				+ FeedReaderContract2.FeedEntryFoods.KEY_SODIUM + " TEXT NOT NULL, "
				+ FeedReaderContract2.FeedEntryFoods.KEY_CARBS + " TEXT NOT NULL, "
				+ FeedReaderContract2.FeedEntryFoods.KEY_SUGAR + " TEXT NOT NULL, "
				+ FeedReaderContract2.FeedEntryFoods.KEY_PROTEIN + " TEXT NOT NULL" + ");";
		

		private static final String SQL_DELETE_TABLE1 = "DROP TABLE IF EXISTS "
				+ FeedReaderContract2.FeedEntryFoods.TABLE1;
		

		DatabaseOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			mHelperContext = context;
			
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			mDatabase = db;
			db.execSQL(TABLE_CREATE1);
			db.execSQL("PRAGMA foreign_keys=ON;");// enable foreign keys
		}


		/**
		 * Method to update the 'has' column in the ingredients table
		 * 
		 * @param has
		 *            The value to update to
		 * @param ingredName
		 *            The name of the ingredient to update
		 * @return How many rows were affected
		 */

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(SQL_DELETE_TABLE1);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
			onCreate(db);
		}
	}
}
