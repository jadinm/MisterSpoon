package spoon.misterspoon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Meal_BuilderActivity extends Activity {
	
	private MySQLiteHelper sqliteHelper;
	private String mealName;
	private String restName;
	
	Context context = this;
	MealBuilder mealB; 
	Meal meal;//lien vers le plat courant.
	EditText mealPrice = null;
	EditText mealStock = null;
	EditText mealDescription = null;
	Button update = null;
	ImageView mealImage = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_meal);
		
		
		
		
		
		//We get the intent sent by Login
		Intent i = getIntent();
		//We take the informations about the person who's logged (!!!! label)
		String emailResto = i.getStringExtra(Login.email);
		
		
		//We take the informations about the meal viewed
		mealName = i.getStringExtra(Carte.mealName);//TODO
		restName = i.getStringExtra(Profil_Restaurant.restName);
		
		//We create the object Meal associated with this mealName and all his informations
		sqliteHelper= new MySQLiteHelper(this);
		RestaurantOwner r = new RestaurantOwner(sqliteHelper , emailResto);
		mealB = new MealBuilder (sqliteHelper, r);
		meal = new Meal(mealName, restName, sqliteHelper);
		Log.v("start",emailResto);
		
		
		
		

		//We can now define all the widgets
		mealImage = (ImageView) findViewById(R.id.meal_imageview);
        mealImage.setImageResource(R.drawable.imageduplat);
		//TODO mettre images
		
        
        
        

        //Faudrait mettre un champ pour le nom du plat...(name) a moins qu'il soit deja connu ?
        
        
		mealPrice = (EditText) findViewById(R.id.meal_builder_price);
		mealStock = (EditText) findViewById(R.id.meal_builder_stock);
		mealDescription = (EditText) findViewById(R.id.meal_builder_description);
		
		
		if (""+meal.getMealPrice(true)!=null) {
			mealPrice.setText(""+meal.getMealPrice(true));
		}		
		if (""+meal.getMealStock(true)!=null) {
			mealStock.setText(""+meal.getMealStock(true));
		}
		if (meal.getMealDescription(true)!=null) {
			mealDescription.setText(meal.getMealDescription(true));
		}
		
		update = (Button) findViewById(R.id.meal_update);
		update.setOnClickListener(new View.OnClickListener() {//Update the informations
			@Override
			public void onClick(View v) {
				

				int value = Meal.isInDatabase(sqliteHelper, (String) meal.getMealName(), restName);
				if(value==2 && meal.getMealName().equals(name.getText().toString())) {//If it already exists
					name.setText(meal.getMealName());
					Toast toast = Toast.makeText(context, name.getText().toString() + getString(R.string.meal_builder_toast_already_exist), Toast.LENGTH_SHORT);
					toast.show();
					
					return;
				}
				meal.setName(name.getText().toString());
				
				if (mealPrice.getText().toString().length()>0) {
					mealB.setMealPrice(Integer.parseInt(mealPrice.getText().toString()));//TODO
				}
				
				if (mealDescription.getText().toString().length()>0) {
					mealB.setMealDescription(mealDescription.getText().toString());//TODO
				}
				if (mealStock.getText().toString().length()>0) {
					mealB.setMealStock(Integer.parseInt(mealStock.getText().toString()));//TODO
				}
				
				Toast toasted = Toast.makeText(context, getString(R.string.meal_builder_toast_uptodate), Toast.LENGTH_SHORT);
				toasted.show();
				
				
			}
			
		});
		
	}
	
}
