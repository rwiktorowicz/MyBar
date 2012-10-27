package kevinpage.com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import kevinpage.com.FeedReaderContract.FeedEntry1;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import android.util.Log;

public class SqlDatabase {
	
	private static final String TAG = "SqlDatabase";
	private final DatabaseOpenHelper mDatabaseHelper; //used for queries later
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "drinks.db";
	
	/**
	 * Constructor
	 * 
	 * @param context
	 *            The Context within which to work, used to create the DB
	 */
	public SqlDatabase(Context context) {
		mDatabaseHelper = new DatabaseOpenHelper(context);
	}
	
	//TODO Build out QUERIES here
	
	/**
     * Returns a Cursor positioned at the drink specified by rowId
     *
     * @param rowId id of drink to retrieve
     * @param columns The columns to include, if null then all are included
     * @return Cursor positioned to matching word, or null if not found.
     */
   /* public Cursor getDrink(String rowId, String[] columns) {
        String selection = "rowid = ?";
        String[] selectionArgs = new String[] {rowId};
        // columns will probably look like (KEY_DRINK, KEY_RATING, KEY_INSTRUCTIONS)
        // selection like * (null) if we wanted all drinks
        // selectionArgs specifying WHERE clause (based on columns chosen)
        return query(selection, selectionArgs, columns);

         This builds a query that looks like:
         *     SELECT <columns> FROM <table> WHERE rowid = <rowId>
         
    }*/
    
    /**
     * Returns a Cursor positioned at all ingredients from table
     * @param columns The columns to include
     * @return Cursor positioned over all ingredients
     */
    public Cursor getAllIngredients(){
    	SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
    	// Projection that specifies which columns we will use for query
    	String[] projection = new String[] {FeedReaderContract.FeedEntry2.KEY_sINGREDIENT};
    	return db.query(FeedReaderContract.FeedEntry2.TABLE2, projection, null, null, 
    			null, null, null);
    }
	 //TODO may not need the next 3 methods...
	/**
	 * Performs a database query on the table of drinks
	 * 
	 * @param selection
	 *            The selection clause
	 * @param selectionArgs
	 *            Selection arguments for "?" components in the selection
	 * @param columns
	 *            The columns to return
	 * @return A Cursor over all rows matching the query
	 */
	private Cursor queryDrinks(String selection, String[] selectionArgs,
			String[] columns) {
		/*
		 * The SQLiteBuilder provides a map for all possible columns requested
		 * to actual columns in the database, creating a simple column alias
		 * mechanism by which the ContentProvider does not need to know the real
		 * column names
		 */
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(FeedReaderContract.FeedEntry1.TABLE1);
		//builder.setProjectionMap(mDrinkColumnMap);

		Cursor cursor = builder.query(mDatabaseHelper.getReadableDatabase(),
				columns, selection, selectionArgs, null, null, null);

		if (cursor == null) {
			return null;
		} else if (!cursor.moveToFirst()) {
			cursor.close();
			return null;
		}
		return cursor;
	}
	
	/**
	 * Performs a database query on the table of ingredients
	 * @param selection The selection clause
	 * @param selectionArgs Selection arguments for "?" components in the selection
	 * @param columns The columns to return
	 * @return A Cursor over all rows matching the query
	 */
	private Cursor queryIngredients(String selection, String[] selectionArgs,
			String[] columns) {
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(FeedReaderContract.FeedEntry2.TABLE2);
		//builder.setProjectionMap(mIngredientsColumnMap);

		Cursor cursor = builder.query(mDatabaseHelper.getReadableDatabase(),
				columns, selection, selectionArgs, null, null, null);
		
		if (cursor == null) {
			return null;
		} else if (!cursor.moveToFirst()) {
			cursor.close();
			return null;
		}
		return cursor;
	}

	/**
	 * Performs a database query on the table of drink ingredients
	 * @param selection The selection clause
	 * @param selectionArgs Selection arguments for "?" components in the selection
	 * @param columns The columns to return
	 * @return A Cursor over all rows matching the query
	 */
	private Cursor queryDrinkIngredients(String selection, String[] selectionArgs,
			String[] columns) {
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(FeedReaderContract.FeedEntry3.TABLE3);
		//builder.setProjectionMap(mDrinkIngredientsColumnMap);

		Cursor cursor = builder.query(mDatabaseHelper.getReadableDatabase(),
				columns, selection, selectionArgs, null, null, null);
		
		if (cursor == null) {
			return null;
		} else if (!cursor.moveToFirst()) {
			cursor.close();
			return null;
		}
		return cursor;
	}
	
	public static class DatabaseOpenHelper extends SQLiteOpenHelper{

		private final Context mHelperContext;
		private SQLiteDatabase mDatabase;
		
		/** SQL to create first table of drinks */
		private static final String TABLE_CREATE1 = "CREATE TABLE " + FeedReaderContract.FeedEntry1.TABLE1
				+ " (" + FeedReaderContract.FeedEntry1.KEY_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ FeedReaderContract.FeedEntry1.KEY_DRINK + " TEXT NOT NULL, " 
				+ FeedReaderContract.FeedEntry1.KEY_RATING + " INTEGER NOT NULL, " 
				+ FeedReaderContract.FeedEntry1.KEY_INSTRUCTIONS + " TEXT NOT NULL" 
				+ ");";
		/** SQL to create second table of ingredients */
		private static final String TABLE_CREATE2 = "CREATE TABLE " + FeedReaderContract.FeedEntry2.TABLE2
				+ " (" + FeedReaderContract.FeedEntry2.KEY_ID2 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ FeedReaderContract.FeedEntry2.KEY_sINGREDIENT + " TEXT NOT NULL," 
				+ FeedReaderContract.FeedEntry2.KEY_HAS + " INTEGER NOT NULL"
				+ ");";		
		/** SQL to create third table of drink-ingredients */
		private static final String TABLE_CREATE3 = "CREATE TABLE " + FeedReaderContract.FeedEntry3.TABLE3
				+ " (" + FeedReaderContract.FeedEntry3.KEY_ID3 + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ FeedReaderContract.FeedEntry3.KEY_subID1 + " INTEGER NOT NULL,"
				+ FeedReaderContract.FeedEntry3.KEY_subID2 + " INTEGER NOT NULL,"
				+ FeedReaderContract.FeedEntry3.KEY_AMOUNT + " TEXT NOT NULL,"
				+ "FOREIGN KEY(" + FeedReaderContract.FeedEntry3.KEY_subID1 + ") REFERENCES "
				+ FeedReaderContract.FeedEntry1.TABLE1 + "(" + FeedReaderContract.FeedEntry1.KEY_ID1 + "),"
				+ "FOREIGN KEY(" + FeedReaderContract.FeedEntry3.KEY_subID2 + ") REFERENCES "
				+ FeedReaderContract.FeedEntry2.TABLE2 + "(" + FeedReaderContract.FeedEntry2.KEY_ID2 + ")"
				+ ");";
		
		private static final String SQL_DELETE_TABLE1 = 
				"DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry1.TABLE1;
		private static final String SQL_DELETE_TABLE2 =
				"DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry2.TABLE2;
		private static final String SQL_DELETE_TABLE3 =
				"DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry3.TABLE3;
		
		
		DatabaseOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			mHelperContext = context;
		}		
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(TABLE_CREATE1);
			db.execSQL(TABLE_CREATE2);
			db.execSQL(TABLE_CREATE3);
			db.execSQL("PRAGMA foreign_keys=ON;");// enable foreign keys
			loadTableData();
		}
		
		/**
		 * Starts a thread to load the database table with words
		 */
		private void loadTableData() {
			new Thread(new Runnable() {
				public void run() {
					try {
						loadTables();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}).start();
		}
		
		//TODO edit to correctly scan in raw ingredients & other data
		private void loadTables() throws IOException {
			//Log.d(TAG, "Loading ingredients...");
			//Log.d(TAG2, "Loading drinks...");
			final Resources resources = mHelperContext.getResources();
			InputStream inputStream = resources.openRawResource(R.raw.drinks);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));

			try {
				Log.d(TAG, "Loading database...");
				String line;	
				ArrayList<String> drinkIngredients = new ArrayList<String>();
				ArrayList<String> amounts = new ArrayList<String>();
				while ((line = reader.readLine()) != null) {//get Drink name
					drinkIngredients.clear();
					amounts.clear();
					long ingredRowId = -1, drinkRowId = -1;	
					int r = Integer.parseInt(reader.readLine());//get Drink rating
					while (true) {
						String ing = reader.readLine(); //get ingred name
						if (ing.equals("0")) //stop if it was 0
							break;
						ingredRowId = addIngredient(ing, 0); //add that ingredient name and get rowId
						String amount = reader.readLine(); //get amount for ingredient
						
						amounts.add(amount);		 //add to arraylist				
						drinkIngredients.add(ing);	//add to arraylist
					}
					String instruct = reader.readLine();	//instructions for drink
					drinkRowId = addDrink(line, r, instruct);	//add the drink
					/** If ingredients were read in for the drink,
					 *  add it to the db with the correct row id's
					 */
					if(!(drinkIngredients.isEmpty())){
						for(int i = 0; i < drinkIngredients.size(); i++){
							addDrinkIngredient(amounts.get(i), ingredRowId, drinkRowId);
						}
					}
				}
			} finally {
				reader.close();
			}
			Log.d(TAG, "Done loading database");
		}
	
		/**
		 * Add an ingredient to the table.
		 * 
		 * @return rowId or -1 if failed
		 */
		public long addIngredient(String ingredient, int has) {
			ContentValues initialValues = new ContentValues();
			initialValues.put(FeedReaderContract.FeedEntry2.KEY_sINGREDIENT, ingredient);
			initialValues.put(FeedReaderContract.FeedEntry2.KEY_HAS, has); //assume they don't have it

			return mDatabase.insert(FeedReaderContract.FeedEntry2.TABLE2, null, initialValues);
		}
		
		/**
		 * Add a drink to the table.
		 * 
		 * @return rowId or -1 if failed
		 */
		public long addDrink(String name, int rating, String instructions) {

			ContentValues initialValues = new ContentValues();
			initialValues.put(FeedReaderContract.FeedEntry1.KEY_DRINK, name);
			initialValues.put(FeedReaderContract.FeedEntry1.KEY_RATING, rating);
			initialValues.put(FeedReaderContract.FeedEntry1.KEY_INSTRUCTIONS, instructions);
			/*
			 * for(int i = 0; i < ingredients.size(); i ++){
			 * initialValues.put(KEY_dINGREDIENT, ingredients.get(i)); }
			 */
			/*for (int j = 0; j < amounts.size(); j++) {
				initialValues.put(KEY_AMOUNT, amounts.get(j));
			}*/
			
			return mDatabase.insert(FeedReaderContract.FeedEntry1.TABLE1, null, initialValues);
		}
		
		public long addDrinkIngredient(String amount, long ingredId, long drinkId){
			
			ContentValues initialValues = new ContentValues();
			initialValues.put(FeedReaderContract.FeedEntry3.KEY_subID1, drinkId);
			initialValues.put(FeedReaderContract.FeedEntry3.KEY_subID2, ingredId);
			initialValues.put(FeedReaderContract.FeedEntry3.KEY_AMOUNT, amount);			
			
			return mDatabase.insert(FeedReaderContract.FeedEntry3.TABLE3, null, initialValues);
		}		

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(SQL_DELETE_TABLE1);
			db.execSQL(SQL_DELETE_TABLE2);
			db.execSQL(SQL_DELETE_TABLE3);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
			onCreate(db);			
		}
	}
}
