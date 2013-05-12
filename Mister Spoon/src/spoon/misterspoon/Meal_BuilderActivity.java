package spoon.misterspoon;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Meal_BuilderActivity extends Activity {
	
	private MySQLiteHelper sqliteHelper;
	private String restName;
	
	Context context = this;
	
	MealBuilder mealB;
	
	private Uri mImageCaptureUri;
	private LinearLayout listview;
    
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;
	
	EditText mealPrice = null;
	EditText mealStock = null;
	EditText mealDescription = null;
	EditText mealName = null;
	Button update = null;
	//ImageView mealImage = null;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_meal_builder);
		
		
		
		
		
		//We get the intent sent by Login
		Intent i = getIntent();
		//We take the informations about the meal viewed
		String emailPerso = i.getStringExtra(CarteBuilderActivity.EMAIL);

		String mealNom = i.getStringExtra(CarteBuilderActivity.MEAL);
		
		//We create the object Meal associated with this mealName and all his informations
		sqliteHelper= new MySQLiteHelper(this);
		RestaurantOwner r = new RestaurantOwner(sqliteHelper , emailPerso);
		mealB = new MealBuilder (sqliteHelper, new Meal (mealNom, r.getRestaurant().getRestaurantName(), sqliteHelper), r);
		
		
		setTitle(String.format(mealNom));

		
		//For the image picker
		final String [] items           = new String [] {"From Camera", "From SD Card"};
		ArrayAdapter<String> adapter  = new ArrayAdapter<String> (this, android.R.layout.select_dialog_item,items);
		AlertDialog.Builder builder     = new AlertDialog.Builder(this);

		builder.setTitle(R.string.profil_restaurant_select_image);
		builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
			public void onClick( DialogInterface dialog, int item ) {
				if (item == 0) {
					Intent intent    = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					File file        = new File(Environment.getExternalStorageDirectory(),
							"tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
					mImageCaptureUri = Uri.fromFile(file);

					try {
						intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
						intent.putExtra("return-data", true);

						startActivityForResult(intent, PICK_FROM_CAMERA);
					} catch (Exception e) {
						e.printStackTrace();
					}

					dialog.cancel();
				} else {
					Intent intent = new Intent();

					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);

					startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
				}
			}
		} );

		final AlertDialog dialog = builder.create();

		((Button) findViewById(R.id.meal_builder_add_image)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.show();
			}
		});

		//We can now define all the widgets
		listview = (LinearLayout) findViewById(R.id.meal_builder_gallery_layout);
		
		//Ajout des images provenant de la base de donnée
		for(String pathImage : mealB.getMeal().getImageList(false)){
			addImageFromPath(pathImage);
		}
		
        
		mealName = (EditText) findViewById(R.id.meal_builder_name);
		mealPrice = (EditText) findViewById(R.id.meal_builder_price);
		mealStock = (EditText) findViewById(R.id.meal_builder_stock);
		mealDescription = (EditText) findViewById(R.id.meal_builder_description);
			
		mealPrice.setText(""+mealB.getMeal().getMealPrice(true));
		
		mealName.setText(mealB.getMeal().getMealName());
		
		if (""+mealB.getMeal().getMealStock(false)!=null) {
			mealStock.setText(""+mealB.getMeal().getMealStock(false));
		}
		if (mealB.getMeal().getMealDescription(false)!=null) {
			mealDescription.setText(mealB.getMeal().getMealDescription(false));
		}

		//mealB.setMeal() = new Meal(mealName.getText().toString(), restName, sqliteHelper);
		
		update = (Button) findViewById(R.id.meal_builder_update);
		update.setOnClickListener(new View.OnClickListener() {//Update the informations
			@Override
			public void onClick(View v) {
				

				boolean value = Meal.isInDatabase(sqliteHelper, mealB.getMeal().getMealName(), restName);
				if(value && !mealB.getMeal().getMealName().equals(mealName.getText().toString())) {//If it already exists
					mealName.setText(mealB.getMeal().getMealName());
					Toast toast = Toast.makeText(context, mealName.getText().toString() + getString(R.string.meal_builder_toast_already_exist), Toast.LENGTH_SHORT);
					toast.show();
					
					return;
				}
				else if (!mealB.getMeal().getMealName().equals(mealName.getText().toString())) {
					mealB.setMealName(mealName.getText().toString());
					setTitle(String.format(mealName.getText().toString()));
				}
				
				if (mealPrice.getText().toString().length()>0) {
					mealB.setMealPrice(Double.parseDouble(mealPrice.getText().toString()));
				}
				else {
					mealPrice.setText(mealB.getMeal().getMealPrice(true) + "");
				}
				
				if (mealDescription.getText().toString().length()>0) {
					mealB.setMealDescription(mealDescription.getText().toString());
				}
				else {
					mealDescription.setText(mealB.getMeal().getMealDescription(true));
				}
				
				if (mealStock.getText().toString().length()>0) {
					mealB.setMealStock(Integer.parseInt(mealStock.getText().toString()));
				}
				else {
					mealStock.setText(mealB.getMeal().getMealStock(true) + "");
				}
				
				Toast toasted = Toast.makeText(context, getString(R.string.meal_builder_toast_uptodate), Toast.LENGTH_SHORT);
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
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
 
        Bitmap bitmap   = null;
        String path     = "";
 
        if (requestCode == PICK_FROM_FILE) {
            mImageCaptureUri = data.getData();
            path = getRealPathFromURI(mImageCaptureUri); //from Gallery
 
            if (path == null)
                path = mImageCaptureUri.getPath(); //from File Manager
 
            if (path != null)
                bitmap  = BitmapFactory.decodeFile(path);
        } else {
            path    = mImageCaptureUri.getPath();
            bitmap  = BitmapFactory.decodeFile(path);
        }
 
        ImageView image = new ImageView (this);
		image.setImageBitmap(bitmap);
		image.setPadding (20, 0, 20, 0);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(377, 377);
		image.setLayoutParams(layoutParams);
		listview.addView(image);
		mealB.addImage(path);
    }
	
	public String getRealPathFromURI(Uri contentUri) {
        String [] proj      = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
		Cursor cursor       = managedQuery( contentUri, proj, null, null,null);
 
        if (cursor == null) return null;
 
        int column_index    = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
 
        cursor.moveToFirst();
 
        return cursor.getString(column_index);
    }
	
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}
	
}