package kevinpage.com;


import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
 /**
  * This class creates an activity for the 'Browse Foods'
  * option in the application.
  */
public class AllFoods extends Activity {
	
	private MyFoodDatabase sqlDb;
	
	/**
	 * Fills a String array based on a cursor
	 * @param cursor The cursor to parse through
	 * @return A String array based on data in cursor
	 */
	private ArrayList<String> fillArray(Cursor cursor){
		ArrayList<String> temp;
		if(cursor == null){
			temp = new ArrayList<String>();
		}
		else{
			temp = new ArrayList<String>(cursor.getCount());
			for(int i = 0; i<cursor.getCount() && !(cursor.isLast()); i++){
				if(cursor.isNull(0)){
					break;
				}
				temp.add(cursor.getString(0));
				cursor.moveToNext();
			}
		}
		return temp;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.foods);
		
		sqlDb = new MyFoodDatabase(this);
		
		Cursor cursor = sqlDb.getAllFoods();
		ArrayList<String> foodNames = new ArrayList<String>();
		
		foodNames = fillArray(cursor);

		//The list view to display all the names of all foods
		ListView lv = (ListView) findViewById(R.id.makeable_foods);
		lv.setAdapter(new ArrayAdapter<String>(this, R.layout.check, foodNames));
		lv.setTextFilterEnabled(true);

		/**
		 * Handles user click on food 
		 */
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AlertDialog.Builder adb = new AlertDialog.Builder(
						AllFoods.this); //Opens alert dialog box displaying drink selected

				
				String foodName = (((TextView)view).getText()).toString();
				
				Cursor cFood = sqlDb.getFoodInfo(foodName);
				adb.setTitle(cFood.getString(0));
				
				String serving = cFood.getString(1);
				
				int calories = cFood.getInt(3);
				
				int fat = cFood.getInt(4);
				
				int cholesterol = cFood.getInt(5);
				
				int sodium = cFood.getInt(6);
				
				int carbs = cFood.getInt(7);
				
				int sugar = cFood.getInt(8);
				
				int protein = cFood.getInt(9);
				
				String message = "Serving: " + serving + "\n";
				message += "Calories: " + calories + "\n";
				message += "Fat: " + fat + "\n";
				message += "Cholesterol: " + cholesterol + "\n";
				message += "Sodium: " + sodium + "\n";
				message += "Carbs: " + carbs + "\n";
				message += "Sugar: " + sugar + "\n";
				message += "Protein: " + protein + "\n";
				
				
				adb.setMessage(message);
				adb.setPositiveButton("Ok", null);
				adb.show();
				

			}
		});
		
		/**
		 * Listener for long clicks on items
		 */
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, final View arg1,
					int arg2, long arg3) {
				
				final String foodName = (((TextView)arg1).getText()).toString();
				
				AlertDialog.Builder adb = new AlertDialog.Builder(AllFoods.this);
				adb.setMessage("Are you sure you want to delete this food?").setTitle("Delete Food");
				adb.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			               sqlDb.deleteFood(foodName);
			               Intent i = new Intent(arg1.getContext(), MyFood.class);
			               finish();
			               startActivity(i);
			           }
			       });
				adb.setNegativeButton("Cancel", null);
				
				adb.show();
				
				return false;
			}
			
		});
		

		/**
		 * Handles 'Main Menu' button to go back from this view to
		 * the main menu
		 */
		final Button mainButton = (Button) findViewById(R.id.main_menu2);
		mainButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//'Glue' to next activity (the main menu)
				Intent myIntent = new Intent(v.getContext(), MyFood.class);
				AllFoods.this.startActivity(myIntent);
			}
		});
		
	}
}
