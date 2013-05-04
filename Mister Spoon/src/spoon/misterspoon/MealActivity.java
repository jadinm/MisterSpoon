package spoon.misterspoon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MealActivity extends Activity {
	
	private Client c;
	private MySQLiteHelper sqliteHelper;
	private String mealName;
	
	Context context = this;
	Meal meal; //lien vers le plat courant.
	TextView mealPrice = null;
	TextView mealStock = null;
	TextView mealDescription = null;
	Button update = null;
	ImageView mealImage = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_meal);
		
		
		
		
		
		//We get the intent sent by Login
		Intent i = getIntent();
		//We take the informations about the person who's logged (!!!! label)
		String emailClient = i.getStringExtra(Login.email);
		String restoName = i.getStringExtra(Profil_Restaurant.name);//Je le trouve où celui-ci ? Dans la vue précédente
		mealName = i.getStringExtra(Carte.meal);
		
		//We create the object Meal associated with this mealName and all his informations
		sqliteHelper= new MySQLiteHelper(this);
		c = new Client (sqliteHelper, emailClient);
		meal = new Meal (mealName, restoName, sqliteHelper);//?
		Log.v("start",emailClient);
		
		
		
		

		//We can now define all the widgets
		mealImage = (ImageView) findViewById(R.id.meal_imageview);
        mealImage.setImageResource(R.drawable.imageduplat);
		//TODO mettre images
		
        
        
        
        
        
        
        
		mealPrice = (TextView) findViewById(R.id.meal_price);
		mealPrice.setText("Prix : "+meal.getMealPrice(false));
		//prix en fonction du plat
		
		mealStock = (TextView) findViewById(R.id.meal_stock);
		mealStock.setText("En stock : "+meal.getMealStock(false));
		//stock en fonction du plat
		mealDescription = (TextView) findViewById(R.id.meal_description);
		mealDescription.setText(meal.getMealDescription(false));
		//description en fonction du plat
		
		update = (Button) findViewById(R.id.meal_update);
		update.setOnClickListener(new View.OnClickListener() {//Update the informations
			@Override
			public void onClick(View v) {
				

				c.addPlatFav(new Meal (mealName));

				
				Toast toasted = Toast.makeText(context, getString(R.string.profil_client_toast_uptodate), Toast.LENGTH_SHORT);
				toasted.show();
				
				
				
			}
			
		});
		
	}
	
}
