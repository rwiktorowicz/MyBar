package kevinpage.com;

import android.provider.BaseColumns;

public class FeedReaderContract2{
	
	/** Prevent this class from being instantiated*/
	private  FeedReaderContract2() {}	
	
	/** The columns included in our all-drinks table */
	public static abstract class FeedEntryFoods implements BaseColumns {		
		public static final String TABLE1 = "foods";
		public static final String KEY_ID1 = "food_id";
		public static final String KEY_FOOD = "name";
		public static final String KEY_SERVING = "serving";
		public static final String KEY_CALORIES = "calories";
		public static final String KEY_FAT = "fat";
		public static final String KEY_CHOLESTEROL = "cholesterol";
		public static final String KEY_SODIUM = "sodium";
		public static final String KEY_CARBS = "carbs";
		public static final String KEY_SUGAR = "sugar";
		public static final String KEY_PROTEIN = "protein";
	}
	
}
