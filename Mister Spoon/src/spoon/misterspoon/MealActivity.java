package spoon.misterspoon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

public class MealActivity extends Activity {
	
	private Client c;
	private MySQLiteHelper sqliteHelper;
	private String mealName;
	
	Context context = this;
	Meal meal;
	TextView mealPrice = null;
	TextView mealStock = null;
	TextView mealDescription = null;
	Button update = null;
	
	private Uri mImageCaptureUri;
	private LinearLayout listview;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_meal);
		
		//We get the intent sent by Login
		Intent i = getIntent();

		String emailClient = i.getStringExtra(CarteActivity.CLIENT);
		String restoName = i.getStringExtra(CarteActivity.RESTAURANT);
		mealName = i.getStringExtra(CarteActivity.MEAL);
		
		setTitle(String.format(mealName));
		
		//We create the object Meal associated with this mealName and all his informations
		sqliteHelper= new MySQLiteHelper(this);
		c = new Client (sqliteHelper, emailClient);
		meal = new Meal (mealName, restoName, sqliteHelper);
		
		setTitle(String.format(mealName));
		

		//We can now define all the widgets
		
		listview = (LinearLayout) findViewById(R.id.meal_gallery_layout);
		//Ajout des images provenant de la base de donnée
		for(String pathImage : meal.getImageList(false)){
			addImageFromPath(pathImage);
		}
		
		mealPrice = (TextView) findViewById(R.id.meal_price);
		mealPrice.setText("Prix : "+meal.getMealPrice(false));
		
		mealStock = (TextView) findViewById(R.id.meal_stock);
		mealStock.setText("En stock : "+meal.getMealStock(false));
		
		mealDescription = (TextView) findViewById(R.id.meal_description);
		mealDescription.setText(meal.getMealDescription(false));
		
		//Gallery a modifier

//		if (c.getEmail().equals("mathieu.jadin@student.uclouvain.be")) {
//			ImageView image = new ImageView (this);
//			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_1));
//			image.setPadding (20, 0, 20, 0);
//			listview.addView(image);
//			
//			image = new ImageView (this);
//			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_2));
//			image.setPadding (20, 0, 20, 0);
//			listview.addView(image);
//			
//			image = new ImageView (this);
//			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_3));
//			image.setPadding (20, 0, 20, 0);
//			listview.addView(image);
//			
//			image = new ImageView (this);
//			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_4));
//			image.setPadding (20, 0, 20, 0);
//			listview.addView(image);
//			
//			image = new ImageView (this);
//			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_5));
//			image.setPadding (20, 0, 20, 0);
//			listview.addView(image);
//			
//			image = new ImageView (this);
//			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_6));
//			image.setPadding (20, 0, 20, 0);
//			listview.addView(image);
//			
//			image = new ImageView (this);
//			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_7));
//			image.setPadding (20, 0, 20, 0);
//			listview.addView(image);
//			
//			image = new ImageView (this);
//			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_8));
//			image.setPadding (20, 0, 20, 0);
//			listview.addView(image);
//			
//			image = new ImageView (this);
//			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_9));
//			image.setPadding (20, 0, 20, 0);
//			listview.addView(image);
//
//		}
//		
		update = (Button) findViewById(R.id.meal_update);
		update.setOnClickListener(new View.OnClickListener() {//Add to favorite
			@Override
			public void onClick(View v) {
				

				c.addPlatFav(new Meal (mealName));

				
				Toast toasted = Toast.makeText(context, getString(R.string.profil_client_toast_uptodate), Toast.LENGTH_SHORT);
				toasted.show();
			}
			
		});
		
	}
	
	private void addImageFromPath(String path){
		Bitmap bitmap   = null;
 
        if (path == null)
                path = mImageCaptureUri.getPath(); //from File Manager
 
        if (path != null)
                bitmap  = BitmapFactory.decodeFile(path);
 
        ImageView image = new ImageView (this);
		image.setImageBitmap(bitmap);
		image.setPadding (20, 0, 20, 0);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(377, 377);
		image.setLayoutParams(layoutParams);
		listview.addView(image);
	}
	
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}
	
}
