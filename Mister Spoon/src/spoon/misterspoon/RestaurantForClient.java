package spoon.misterspoon;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Bitmap; 
import android.graphics.BitmapFactory;
import android.widget.Adapter;

public class RestaurantForClient extends Activity implements LocationListener {

	private Restaurant r;
	private Client c;
	private MySQLiteHelper sqliteHelper = new MySQLiteHelper(this);
	String restoName;
	String emailPerso;
	String closingString;

	private Uri mImageCaptureUri; 

	//Elements of the view
	private TextView email_perso;
	private TextView email_public;
	private TextView web;
	private TextView gsm;
	private TextView fax;
	private TextView rue;
	private TextView num;
	private TextView town;
	private TextView description;
	private TextView longitude;
	private TextView latitude;
	private TextView horaire;

	private LinearLayout listview;

	private RatingBar ratingBar;

	private Button addResto;
	private Button menu;
	private Button gpsButton;
	private Button avantages;
	private Button cuisine;
	private Button closingDay;
	private LocationManager lManager;
	private Location location;
	private String choix_source = "";


	//Useful for the alertBoxes
	private Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.activity_restaurant);

		lManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);


		//We get the intent sent by Login
		Intent i = getIntent();
		//We take the informations about the person who's logged (!!!! label)
		emailPerso = i.getStringExtra(RestaurantListActivity.emailLogin);
		restoName = i.getStringExtra(RestaurantListActivity.RESTAURANT);


		//We create the object Restaurant associated with this email and all his informations
		r = new Restaurant (sqliteHelper, restoName);
		c = new Client(sqliteHelper, emailPerso);
		c.setRestaurantEnCours(r);

		//We can now define all the widgets
		listview = (LinearLayout) findViewById(R.id.gallery_layout);

		email_perso = (TextView) findViewById(R.id.restaurant_mail);
		email_public = (TextView) findViewById(R.id.profil_restaurant_mail_public);
		gsm = (TextView) findViewById(R.id.restaurant_tel);
		web = (TextView) findViewById(R.id.restaurant_web);

		fax = (TextView) findViewById(R.id.restaurant_fax);
		rue = (TextView) findViewById(R.id.rue); 
		num = (TextView) findViewById(R.id.numero);
		town = (TextView) findViewById(R.id.ville);
		description = (TextView) findViewById(R.id.restaurant_description);
		longitude = (TextView) findViewById(R.id.gps_longitude);
		latitude = (TextView) findViewById(R.id.gps_latitude);
		horaire = (TextView) findViewById(R.id.restaurant_horaire);
		ratingBar = (RatingBar) findViewById(R.id.restaurant_rating);
		menu = (Button) findViewById(R.id.restaurant_carte);		
		gpsButton = (Button) findViewById(R.id.profil_restaurant_gps);		
		addResto = (Button) findViewById(R.id.restaurant_add);
		avantages = (Button) findViewById(R.id.restaurant_client_avantages);
		cuisine = (Button) findViewById(R.id.restaurant_client_cuisine);
		closingDay = (Button) findViewById(R.id.restaurant_closing_day);

		setTitle(String.format(restoName));

		ratingBar.setRating(r.getRestaurantNote(false));
		email_perso.setText(email_perso.getText() + " " + c.getEmail()); 
		gsm.setText(gsm.getText() + " " + r.getRestaurantPhone(false));
		rue.setText(rue.getText() + " " + r.getRestaurantRue(false));
		town.setText(town.getText() + " " + r.getRestaurantVille(false));
		longitude.setText(r.getRestaurantPosition(false).getLongitude()+ "");
		latitude.setText(r.getRestaurantPosition(false).getLatitude() + "");


		if (r.getRestaurantFax(false)!=null) {
			fax.setText(fax.getText() + " " + r.getRestaurantFax(false));
		}
		else {
			fax.setVisibility(0);
		}

		if (r.getRestaurantEmail(false)!=null){
			email_public.setText(email_public.getText() + " " + r.getRestaurantEmail(false));
		}
		else {
			email_public.setVisibility(0);
		}
		if (r.getRestaurantWebSite(false)!=null) {
			web.setText(r.getRestaurantWebSite(false));
		}
		else {
			web.setVisibility(0);
		}
		if (r.getRestaurantNumero(false)!=0) {
			num.setText(num.getText() + " " +r.getRestaurantNumero(false)+ "");
		}
		else{
			num.setVisibility(0);
		}
		if (r.getRestaurantDescription(false)!=null) {
			description.setText(r.getRestaurantDescription(false));
		}
		else {
			description.setVisibility(0);
		}


		if (r.getRestaurantHoraire(false)!=null) {
			String horaireString = new String();
			ArrayList<String> horaireList = new ArrayList<String>();
			ArrayList<Time> hourList = new ArrayList<Time>();
			ArrayList<Time> closeHourList = new ArrayList<Time>();
			hourList.add(r.getRestaurantHoraire(false).get(0).getOpenTime());
			closeHourList.add(r.getRestaurantHoraire(false).get(0).getCloseTime());
			horaireList.add(r.getRestaurantHoraire(false).get(0).getOpenDay());

			for(int l = 0; l<r.getRestaurantHoraire(false).size(); l++) {
				for(int j = 0; j<horaireList.size();j++) {
					if ((!r.getRestaurantHoraire(false).get(l).getOpenDay().equals(horaireList.get(j))) && r.getRestaurantHoraire(false).get(l).getOpenTime().equals(hourList.get(j)) && r.getRestaurantHoraire(false).get(l).getCloseTime().equals(closeHourList.get(j))) {
						horaireList.set(j, horaireList.get(j)+", "+r.getRestaurantHoraire(false).get(l).getOpenDay());
						j = horaireList.size();
					}
					else if (j == (horaireList.size() - 1) && (!r.getRestaurantHoraire(false).get(l).getOpenDay().equals(horaireList.get(j)))){
						hourList.add(r.getRestaurantHoraire(false).get(l).getOpenTime());
						horaireList.add(r.getRestaurantHoraire(false).get(l).getOpenDay());
						closeHourList.add(r.getRestaurantHoraire(false).get(l).getCloseTime());
						j = horaireList.size();
					}
				}

			}

			for (int m = 0; m < horaireList.size(); m++) {
				horaireString = horaireString + horaireList.get(m) + " - " + hourList.get(m).hourMin() + "/" + closeHourList.get(m).hourMin() + "\n";
			}
			horaire.setText(horaireString);

		}
		else {
			horaire.setVisibility(0);
		}


		//Gallery

		if (r.getRestaurantName().equals("Loungeatude")) {
			ImageView image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_1));
			image.setPadding (20, 0, 20, 0);
			image.setMaxHeight(100);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_2));
			image.setPadding (20, 0, 20, 0);
			image.setMaxHeight(100);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_3));
			image.setPadding (20, 0, 20, 0);
			image.setMaxHeight(100);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_4));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_5));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_6));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_7));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_8));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_9));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

		}

		else if (r.getRestaurantName().equals("Le Petit Vingtieme")) {
			ImageView image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.pti1));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.pti2));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.pti3));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.pti4));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.pti5));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.pti6));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

		}

		else if (r.getRestaurantName().equals("Creperie Bretonne")) {

			ImageView image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.bret1));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.bret2));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.bret3));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.bret4));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.bret5));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);
		}

		else if (r.getRestaurantName().equals("Comme chez soi")) {

			ImageView image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.chezsoi));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.chez1));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.chez2));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);

			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.chez3));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);


		}

		else
		{
			ImageView image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.restaurants));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);
		}

		//Ajout des images provenant de la base de donnée
		for(String pathImage : r.getRestaurantImageList(false)){
			addImageFromPath(pathImage);
		} 

		ratingBar.setOnRatingBarChangeListener(ratingListener);

		//We define all the listeners
		addResto.setOnClickListener(new View.OnClickListener() {//Update the informations
			@Override
			public void onClick(View v) {
				c.addRestFav(r);
				Toast toasted = Toast.makeText(context, getString(R.string.profil_restaurant_toast_resto), Toast.LENGTH_SHORT);
				toasted.show();
			}

		});

		menu.setOnClickListener(new View.OnClickListener() {//launch an alert box
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RestaurantForClient.this, CarteActivity.class);
				intent.putExtra(Login.email, c.getEmail());
				intent.putExtra(RestaurantListActivity.RESTAURANT, r.getRestaurantName());
				startActivity(intent);
			}
		});



		gpsButton.setOnClickListener(new View.OnClickListener() {//launch an alert box 
			@Override
			public void onClick(View v) {

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

				//set title and message
				alertDialogBuilder.setTitle(R.string.profil_restaurant_gps);
				alertDialogBuilder.setMessage(R.string.resto_gps_options);

				alertDialogBuilder.setCancelable(true);//We can go back with the return button

				//The other buttons
				alertDialogBuilder.setPositiveButton(R.string.resto_gps_itineraire,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						choisirSource();
						if (!choix_source.equals("gps")) {
							Toast toasted = Toast.makeText(context, getString(R.string.resto_toast_client_position), Toast.LENGTH_SHORT);
							toasted.show();
							return;
						}
						else {
							obtenirPosition();
							Location loc = lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (loc == null){
								loc = lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
								if (loc == null) return;
							}
							if (loc != null) {
								location = loc;
							}
							String currentPosition = String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());
							if (String.valueOf(location.getLatitude())!=null && String.valueOf(location.getLatitude())!=null ) {
								setProgressBarIndeterminateVisibility(false);
								String restoPosition = r.getRestaurantPosition(false).getLongitude()+ "," + r.getRestaurantPosition(false).getLatitude();
								Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="+currentPosition+"&daddr="+restoPosition));
								startActivity(intent);
								dialog.cancel();

							}
						}

					}
				});

				alertDialogBuilder.setNegativeButton(R.string.resto_gps_show,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {//Apply modifications and cancel the alert box						
						String restoPosition = r.getRestaurantPosition(false).getLongitude()+ "," + r.getRestaurantPosition(false).getLatitude();
						Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+restoPosition+" (" + r.getRestaurantName() + ")"));
						startActivity(intent);
						dialog.cancel();

					}
				});

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});


		avantages.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

				//set title and message
				alertDialogBuilder.setTitle(R.string.restaurant_avantages);


				String[] stringArray = new String[r.getRestaurantAvantages(false).size()];
				for (int i = 0; i < r.getRestaurantAvantages(false).size(); i++) {
					stringArray[i] = r.getRestaurantAvantages(false).get(i);
				}
				alertDialogBuilder.setItems(stringArray, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
					}
				});


				alertDialogBuilder.setCancelable(true);//We can go back with the return button

				//The other buttons
				alertDialogBuilder.setPositiveButton(R.string.profil_client_alert_back,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {//Cancel the alert box
						dialog.cancel();
					}
				});


				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		});

		cuisine.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

				//set title and message
				alertDialogBuilder.setTitle(R.string.restaurant_cuisine);


				String[] stringArray = new String[r.getRestaurantCuisine(false).size()];
				for (int i = 0; i < r.getRestaurantCuisine(false).size(); i++) {
					stringArray[i] = r.getRestaurantCuisine(false).get(i);
				}
				alertDialogBuilder.setItems(stringArray, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
					}
				});


				// create alert dialog
				alertDialogBuilder.setCancelable(true);//We can go back with the return button

				//The other buttons
				alertDialogBuilder.setPositiveButton(R.string.profil_client_alert_back,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {//Cancel the alert box
						dialog.cancel();
					}
				});
				AlertDialog alertDialog = alertDialogBuilder.create();


				// show it
				alertDialog.show();
			}
		});


		closingDay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

				//set title and message
				alertDialogBuilder.setTitle(R.string.restaurant_closing_day);


				String[] stringArray = new String[r.getRestaurantClosingDays(false).size()];
				for (int i = 0; i < r.getRestaurantClosingDays(false).size(); i++) {
					stringArray[i] = r.getRestaurantClosingDays(false).get(i).toString();
				}
				alertDialogBuilder.setItems(stringArray, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
					}
				});


				alertDialogBuilder.setCancelable(true);//We can go back with the return button

				//The other buttons
				alertDialogBuilder.setPositiveButton(R.string.profil_client_alert_back,new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {//Cancel the alert box
						dialog.cancel();
					}
				});


				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}
		});


	}

	private OnRatingBarChangeListener ratingListener = new OnRatingBarChangeListener() {

		public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
			c.setNoteRestaurant(rating);
			ratingBar.setEnabled(false);

		}
	};





	private void choisirSource() {
		List <String> providers = lManager.getProviders(true);
		final String[] sources = new String[providers.size()];
		int i =0;
		for(String provider : providers)
			sources[i++] = provider;
		choix_source = sources[1]; //On veut que ca soit le GPS
	}

	private void obtenirPosition() {
		setProgressBarIndeterminateVisibility(true);
		lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	}

	public void onProviderDisabled(String provider) {
		Toast.makeText(this,getString(R.string.resto_toast_client_gps),Toast.LENGTH_SHORT).show();
		lManager.removeUpdates(this);

	}

	public void onProviderEnabled(String provider) {
		return;
	}
	public void onStatusChanged(String provider, int status, Bundle extras) {
		return;
	}

	public void onLocationChanged(Location location) {
		this.location = location;
		lManager.removeUpdates(this);
	}

	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}

	public void onStart(){
		r = new Restaurant (sqliteHelper, restoName);
		c = new Client(sqliteHelper, emailPerso);
		c.setRestaurantEnCours(r);
		super.onStart();
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

}
