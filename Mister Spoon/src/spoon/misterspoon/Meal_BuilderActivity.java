package spoon.misterspoon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Meal_BuilderActivity extends Activity {
	
	private MySQLiteHelper sqliteHelper;
	private String restName;
	
	Context context = this;
	MealBuilder mealB; 
	Meal meal;
	EditText mealPrice = null;
	EditText mealStock = null;
	EditText mealDescription = null;
	EditText mealName = null;
	Button update = null;
	ImageView mealImage = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_meal);
		
		
		
		
		
		//We get the intent sent by Login
		Intent i = getIntent();
		//We take the informations about the person who's logged (!!!! label)
		String emailResto = i.getStringExtra(Login.email);
		
		
		//We take the informations about the meal viewed
		String emailPerso = i.getStringExtra(Login.email);
		
		//We create the object Meal associated with this mealName and all his informations
		sqliteHelper= new MySQLiteHelper(this);
		RestaurantOwner r = new RestaurantOwner(sqliteHelper , emailPerso);
		mealB = new MealBuilder (sqliteHelper, r);
		Log.v("start",emailResto);
		
		

		mealName = (EditText) findViewById(R.id.meal_builder_name);


		if (""+meal.getMealName()!=null) {
			mealName.setText(meal.getMealName());
		}		

		//We can now define all the widgets
		mealImage = (ImageView) findViewById(R.id.meal_imageview);
        //mealImage.setImageResource(R.drawable.imageduplat);
		//TODO mettre images
		
        

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

		meal = new Meal(mealName.getText().toString(), restName, sqliteHelper);
		
		update = (Button) findViewById(R.id.meal_update);
		update.setOnClickListener(new View.OnClickListener() {//Update the informations
			@Override
			public void onClick(View v) {
				

				int value = 0;// meal.isInDatabase(sqliteHelper, (String) meal.getMealName(), restName);
				if(value==2 && meal.getMealName().equals(mealName.getText().toString())) {//If it already exists
					mealName.setText(meal.getMealName());
					Toast toast = Toast.makeText(context, mealName.getText().toString() + getString(R.string.meal_builder_toast_already_exist), Toast.LENGTH_SHORT);
					toast.show();
					
					return;
				}
				
				if (mealName.getText().toString().length()>0) {
					mealB.setMealName(mealName.getText().toString());
				}
				
				if (mealPrice.getText().toString().length()>0) {
					mealB.setMealPrice(Integer.parseInt(mealPrice.getText().toString()));
				}
				
				if (mealDescription.getText().toString().length()>0) {
					mealB.setMealDescription(mealDescription.getText().toString());
				}
				if (mealStock.getText().toString().length()>0) {
					mealB.setMealStock(Integer.parseInt(mealStock.getText().toString()));
				}
				
				Toast toasted = Toast.makeText(context, getString(R.string.meal_builder_toast_uptodate), Toast.LENGTH_SHORT);
				toasted.show();
				
				
			}
			
		});
		
	}
	
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}
	
}
