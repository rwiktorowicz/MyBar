package kevinpage.com;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFoods extends Activity {
    private Button savebutton;
    private Button menubutton;
    private EditText foodnametext;
    private EditText servingtext;
    private EditText calorietext;
    private EditText fattext;
    private EditText cholestext;
    private EditText sodiumtext;
    private EditText carbtext;
    private EditText sugartext;
    private EditText proteintext;
    
    private MyFoodDatabase sqlDb;
	private Context context;
    
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form2);
		
		sqlDb = new MyFoodDatabase(this);

		savebutton = (Button) findViewById(R.id.savebutton);
		menubutton = (Button) findViewById(R.id.menubutton);
		foodnametext = (EditText) findViewById(R.id.foodname);
		servingtext = (EditText) findViewById(R.id.serving);
		calorietext = (EditText) findViewById(R.id.calories);
		fattext = (EditText) findViewById(R.id.fat);
		cholestext = (EditText) findViewById(R.id.cholesterol);
		sodiumtext = (EditText) findViewById(R.id.sodium);
		carbtext = (EditText) findViewById(R.id.carbohydrates);
		sugartext = (EditText) findViewById(R.id.sugar);
		proteintext = (EditText) findViewById(R.id.protein);


		savebutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try{
					if(foodnametext.getText().toString().equals("") || servingtext.getText().toString().equals("")
							|| calorietext.getText().toString().equals("") || fattext.getText().toString().equals("") 
							|| cholestext.getText().toString().equals("") || sodiumtext.getText().toString().equals("")
							|| carbtext.getText().toString().equals("") || sugartext.getText().toString().equals("")
							|| proteintext.getText().toString().equals("")){
						Toast.makeText(context, "Please fill all fields and/or remove dups", Toast.LENGTH_SHORT).show();
					}
					else{sqlDb.insertFood(foodnametext.getText().toString(), servingtext.getText().toString(),
							calorietext.getText().toString(), fattext.getText().toString(), cholestext.getText().toString(),
							sodiumtext.getText().toString(), carbtext.getText().toString(), sugartext.getText().toString(),
							proteintext.getText().toString());}
						
				}catch(SQLiteConstraintException ex){
					Toast.makeText(context, "Please fill all fields and/or remove dups", Toast.LENGTH_SHORT).show();
				}
				
				Intent myIntent = new Intent(v.getContext(), MyFood.class);
				AddFoods.this.startActivity(myIntent);
				
				

			}
		});


		menubutton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(v.getContext(), MyFood.class);
				AddFoods.this.startActivity(myIntent);

			}
		});

}
}
