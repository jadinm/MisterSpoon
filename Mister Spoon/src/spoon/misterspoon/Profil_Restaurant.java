package spoon.misterspoon;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Profil_Restaurant extends Activity {

	private RestaurantOwner r;
	private MySQLiteHelper sqliteHelper = new MySQLiteHelper(this);

	//Elements of the view
	private TextView email_perso;
	private TextView email_public;
	private EditText web;
	private EditText gsm;
	private EditText fax;
	private EditText rue;
	private EditText num;
	private EditText town;
	private EditText description;
	private EditText longitude;
	private EditText latitude;
	private EditText capa;
	
	//private HorizontalScrollView listview;
	private LinearLayout listview;
	
	private Button cooks;//Launch an alert box TODO
	private Button advantages;//Launch an alert box TODO
	private Button payments;//Launch an alert box TODO
	
	private EditText closingEdit;
	private Button closingButton;
	
	private Spinner openDay;
	private EditText openTime;
	private EditText closeTime;

	private Button update;
	private Button menu;

	private Button prebook;
	private Button book;


	//Useful for the alertBoxes
	private Context context = this;
	private String [] items;
	private boolean [] checkedItem;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_profil_restaurant);

		//We get the intent sent by Login
		Intent i = getIntent();
		//We take the informations about the person who's logged (!!!! label)
		String emailPerso = i.getStringExtra(Login.email);


		Log.d("Dans la classe profil restaurant, on a cet email", emailPerso);
		//We create the object Restaurant associated with this email and all his informations
		r = new RestaurantOwner (sqliteHelper, emailPerso);
		
		Log.d("help", "Nous avons construit l'objet RestaurantOwner");

		//We can now define all the widgets
		//listview = (HorizontalScrollView) findViewById(R.id.gallery);//TODO
		listview = (LinearLayout) findViewById(R.id.gallery_layout);//TODO
		
		email_perso = (TextView) findViewById(R.id.profil_restaurant_mail);
		email_public = (TextView) findViewById(R.id.profil_restaurant_mail_public);
		gsm = (EditText) findViewById(R.id.profil_restaurant_tel);
		web = (EditText) findViewById(R.id.profil_restaurant_web);
		fax = (EditText) findViewById(R.id.profil_restaurant_fax);
		rue = (EditText) findViewById(R.id.profil_restaurant_edit_rue); 
		num = (EditText) findViewById(R.id.profil_restaurant_edit_numero);
		town = (EditText) findViewById(R.id.profil_restaurant_edit_ville);
		description = (EditText) findViewById(R.id.profil_restaurant_description);
		longitude = (EditText) findViewById(R.id.profil_restaurant_edit_gps_longitude);
		latitude = (EditText) findViewById(R.id.profil_restaurant_edit_gps_latitude);
		capa = (EditText) findViewById(R.id.profil_restaurant_capacite);

		menu = (Button) findViewById(R.id.profil_restaurant_carte);		
		update = (Button) findViewById(R.id.profil_restaurant_appliquer);
		prebook = (Button) findViewById(R.id.profil_restaurant_prebooking_button);		
		book = (Button) findViewById(R.id.profil_restaurant_booking_button);
		
		
		
		//We already fill the data of the Client if they exist
		
		Log.d("Au départ, on a cet email", r.getEmail());//Debug
		Log.d("Au départ, on a ce nom", r.getRestaurant().getRestaurantName());
		Log.d("Au départ, on a cette coordonnée gps", r.getRestaurant().getRestaurantPosition(false).toString());
		Log.d("Au départ, on a cette adresse", r.getRestaurant().getRestaurantNumero(false) + ", " + r.getRestaurant().getRestaurantRue(false) + ", " + r.getRestaurant().getRestaurantVille(false));
		Log.d("Au départ, on a ce numéro", r.getRestaurant().getRestaurantPhone(false));
		Log.d("Au départ, on a cette capacité", r.getRestaurant().getRestaurantCapacity(false) + "");
		

		email_perso.setText(email_perso.getText() + " " + r.getEmail()); 
		
		gsm.setText(r.getRestaurant().getRestaurantPhone(false));
		rue.setText(r.getRestaurant().getRestaurantRue(false));
		town.setText(r.getRestaurant().getRestaurantVille(false));
		capa.setText(r.getRestaurant().getRestaurantCapacity(false)+ "");
		longitude.setText(r.getRestaurant().getRestaurantPosition(false).getLongitude()+ "");
		latitude.setText(r.getRestaurant().getRestaurantPosition(false).getLatitude() + "");
		
		if (r.getRestaurant().getRestaurantEmail(false)!=null) {
			email_public.setText(r.getRestaurant().getRestaurantEmail(false));
		}

		if (r.getRestaurant().getRestaurantFax(false)!=null) {
			fax.setText(r.getRestaurant().getRestaurantFax(false));
		}
		if (r.getRestaurant().getRestaurantWebSite(false)!=null) {
			web.setText(r.getRestaurant().getRestaurantWebSite(false));
		}
		if (r.getRestaurant().getRestaurantNumero(false)!=0) {
			num.setText(r.getRestaurant().getRestaurantNumero(false)+ "");
		}
		if (r.getRestaurant().getRestaurantDescription(false)!=null) {
			description.setText(r.getRestaurant().getRestaurantDescription(false));
		}


		//Gallery
		/*Integer int_data[] = new Integer[]
		{
				Integer.valueOf(R.drawable.pti1),
				Integer.valueOf(R.drawable.carte)
		};

		GalleryAdapter adapter = new GalleryAdapter(this,
				R.layout.layout_list_image_restaurant, int_data);
		listview.setAdapter(adapter);*/
		
		ImageView image = new ImageView (this);//TODO
		image.setImageDrawable( getResources().getDrawable(R.drawable.calendar));
		image.setPadding (20, 0, 20, 0);
		
		ImageView image2 = new ImageView (this);//TODO
		image2.setImageDrawable( getResources().getDrawable(R.drawable.carte));
		image2.setPadding (20, 0, 20, 0);
		
		listview.addView(image);
		listview.addView(image2);

//		View header = (View)getLayoutInflater().inflate(R.layout.layout_list_image_restaurant, null);
//		listview.addHeaderView(header);

		
		
		

		//We define all the listeners
		update.setOnClickListener(new View.OnClickListener() {//Update the informations
			@Override
			public void onClick(View v) {
				int value = RestaurantOwner.isInDatabase(sqliteHelper,r.getEmail(), r.getRestaurant().getRestaurantName(), longitude.getText().toString() + "," + latitude.getText().toString(), gsm.getText().toString() );
				if(value==3 && r.getRestaurant().getRestaurantPhone(false).equals(gsm.getText().toString())) {//If it already exists
					num.setText(r.getRestaurant().getRestaurantNumero(false));
					Toast toast = Toast.makeText(context, getString(R.string.profil_restaurant_toast_phone), Toast.LENGTH_SHORT);
					toast.show();

					return;
				}
				if(value==4 && r.getRestaurant().getRestaurantPosition(false).equals(new GPS(Double.parseDouble(longitude.getText().toString()), Double.parseDouble(latitude.getText().toString())))) {//If it already exists
					longitude.setText(r.getRestaurant().getRestaurantDescription(false));
					latitude.setText(r.getRestaurant().getRestaurantDescription(false));
					Toast toast = Toast.makeText(context, getString(R.string.profil_restaurant_toast_gps), Toast.LENGTH_SHORT);
					toast.show();

					return;
				}


				if (gsm.getText().toString().length()>0) {
					r.setRestaurantPhone(gsm.getText().toString());
				}
				else {
					Toast toast = Toast.makeText(context, getString(R.string.profil_restaurant_toast_missing), Toast.LENGTH_SHORT);
					toast.show();
				}
				if (fax.getText().toString().length()>0) {
					r.setRestaurantFax(fax.getText().toString());
				}

				if (web.getText().toString().length()>0) {
					r.setRestaurantWebSite(web.getText().toString());
				}

				if (email_public.getText().toString().length()>0) {
					r.setRestaurantEmail(email_public.getText().toString());
				}

				if (num.getText().toString().length()>0) {
					r.setRestaurantNumero(Integer.parseInt(num.getText().toString()));
				}


				if (rue.getText().toString().length()>0) {
					r.setRestaurantRue(rue.getText().toString());
				}
				else {
					Toast toast = Toast.makeText(context, getString(R.string.profil_restaurant_toast_missing), Toast.LENGTH_SHORT);
					toast.show();
				}
				if (town.getText().toString().length()>0) {
					r.setRestaurantVille(town.getText().toString());
				}
				else {
					Toast toast = Toast.makeText(context, getString(R.string.profil_restaurant_toast_missing), Toast.LENGTH_SHORT);
					toast.show();
				}
				if (longitude.getText().toString().length()>0 && latitude.getText().toString().length()>0) {
					r.setRestaurantPosition(new GPS(Double.parseDouble(longitude.getText().toString()), Double.parseDouble(latitude.getText().toString())));
				}
				else {
					Toast toast = Toast.makeText(context, getString(R.string.profil_restaurant_toast_missing), Toast.LENGTH_SHORT);
					toast.show();
				}
				if (capa.getText().toString().length()>0) {
					r.setRestaurantCapacity(Integer.parseInt(capa.getText().toString()));
				}
				else {
					Toast toast = Toast.makeText(context, getString(R.string.profil_restaurant_toast_missing), Toast.LENGTH_SHORT);
					toast.show();
				}
				if (description.getText().toString().length()>0) {
					r.setRestaurantDescription(description.getText().toString());
				}


				Toast toasted = Toast.makeText(context, getString(R.string.profil_client_toast_uptodate), Toast.LENGTH_SHORT);
				toasted.show();



			}

		});

		menu.setOnClickListener(new View.OnClickListener() {//launch an alert box
			@Override
			public void onClick(View v) {
				Toast toasted = Toast.makeText(context, "Le resto veut montrer sa carte mais ce n'est pas encore possibl :p ", Toast.LENGTH_SHORT);
				toasted.show();
			}
		});

		prebook.setOnClickListener(new View.OnClickListener() {//launch another view
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(context, "Un client veut voir ses pr�-r�servations", Toast.LENGTH_SHORT);
				toast.show();
				/*Intent i = new Intent(Profil_Client.this, PreBooking_Client.class);//TODO
				i.putExtra(Login.email, c.getEmail());//TODO
				startActivity(i);*/
			}
		});

		book.setOnClickListener(new View.OnClickListener() {//launch another view
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(context, "Un client veut voir ses r�servations", Toast.LENGTH_SHORT);
				toast.show();
				/*Intent i = new Intent(Profil_Client.this, Booking_Client.class);//TODO
				i.putExtra(Login.email, c.getEmail());//TODO
				startActivity(i);*/
			}
		});




	}



}
