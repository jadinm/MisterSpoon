package spoon.misterspoon;


import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class RestaurantForClient extends Activity implements LocationListener {

	private Restaurant r;
	private Client c;
	private MySQLiteHelper sqliteHelper = new MySQLiteHelper(this);

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

	private ListView listview;
	
	private RatingBar ratingBar;

	private Button addResto;
	private Button menu;
	private Button gpsButton;
	private Button prebook;
	private Button book;

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
		String emailPerso = i.getStringExtra(RestaurantListActivity.emailLogin);
		//String restoName = i.getStringExtra("restoName qu on envoie quand on clique sur le resto"); //TODO
		String restoName = i.getStringExtra(RestaurantListActivity.RESTAURANT);;
		
		
		//We create the object Restaurant associated with this email and all his informations
		r = new Restaurant (sqliteHelper, restoName);
		c = new Client(sqliteHelper, emailPerso);
		c.setRestaurantEnCours(r);

		//We can now define all the widgets
		listview = (ListView) findViewById(R.id.gallery);

		email_perso = (TextView) findViewById(R.id.restaurant_mail);
		email_public = (TextView) findViewById(R.id.profil_restaurant_mail_public);
		gsm = (TextView) findViewById(R.id.restaurant_tel);
		web = (TextView) findViewById(R.id.restaurant_web);
		ratingBar = (RatingBar) findViewById(R.id.restaurant_rating);
		fax = (TextView) findViewById(R.id.restaurant_fax);
		rue = (TextView) findViewById(R.id.rue); 
		num = (TextView) findViewById(R.id.numero);
		town = (TextView) findViewById(R.id.ville);
		description = (TextView) findViewById(R.id.restaurant_description);
		longitude = (TextView) findViewById(R.id.gps_longitude);
		latitude = (TextView) findViewById(R.id.gps_latitude);

		menu = (Button) findViewById(R.id.restaurant_carte);		
		gpsButton = (Button) findViewById(R.id.profil_restaurant_gps);		
		addResto = (Button) findViewById(R.id.restaurant_add);
		prebook = (Button) findViewById(R.id.restaurant_prereservation);		
		book = (Button) findViewById(R.id.restaurant_reservation);

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


		//Gallery TODO
		Integer int_data[] = new Integer[]
				{
				Integer.valueOf(R.drawable.pti1),
				Integer.valueOf(R.drawable.carte)
				};

		GalleryAdapter adapter = new GalleryAdapter(this,
				R.layout.layout_list_image_restaurant, int_data);

		listview.setAdapter(adapter);

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

		prebook.setOnClickListener(new View.OnClickListener() {//launch another view
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(context, "Un client veut lancer l'activit� preBooking", Toast.LENGTH_SHORT);
				toast.show();
				//TODO
			}
		});

		book.setOnClickListener(new View.OnClickListener() {//launch another view
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(context, "Un client veut lancer l'activit� Booking", Toast.LENGTH_SHORT);
				toast.show();
				//TODO
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

	}
	
	private OnRatingBarChangeListener ratingListener = new OnRatingBarChangeListener() {

		public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
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
		ratingBar.setRating(r.getRestaurantNote(false));
		super.onStart();
	}

}
