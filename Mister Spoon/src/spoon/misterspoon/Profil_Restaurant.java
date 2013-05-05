package spoon.misterspoon;


import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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
	
	static final String name = "Nom Restaurant";

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

	private LinearLayout listview;

	private Button cooks;//Launch an alert box
	private Button advantages;//Launch an alert box
	private Button payments;//Launch an alert box

	private EditText closingEdit;
	private Button closingButton;//Launch an alert box

	private Spinner openDay;
	ArrayAdapter<String> adapter;
	private EditText openTime;
	private EditText closeTime;
	private Button horaire;//Launch an alert box

	private Button update;
	private Button menu;

	private Button prebook;
	private Button book;


	//Useful for the alertBoxes
	private Context context = this;
	private String [] items;
	private boolean [] checkedItems;
	private String [] items_test;


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
		listview = (LinearLayout) findViewById(R.id.gallery_layout);

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
		
		cooks = (Button) findViewById(R.id.profil_restaurant_cooks_button);
		advantages = (Button) findViewById(R.id.profil_restaurant_advantages_button);
		payments = (Button) findViewById(R.id.profil_restaurant_payments_button);

		closingEdit = (EditText) findViewById(R.id.profil_restaurant_closing_edit);
		closingButton = (Button) findViewById(R.id.profil_restaurant_closing_button);

		openDay = (Spinner) findViewById(R.id.profil_restaurant_schedule_openday);
		openTime = (EditText) findViewById(R.id.profil_restaurant_schedule_opentime);
		closeTime = (EditText) findViewById(R.id.profil_restaurant_schedule_closetime);
		horaire = (Button) findViewById(R.id.profil_restaurant_schedule_button);

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
		
		
		setTitle(String.format(r.getRestaurant().getRestaurantName()));
		
		//Spinner
		
		List <String> openDayList = Arrays.asList(OpenHour.openDayTable);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, openDayList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		openDay.setAdapter(adapter);


		//Gallery

		if (r.getEmail().equals("mathieu.jadin@student.uclouvain.be")) {
			ImageView image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_1));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);
			
			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_2));
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);
			
			image = new ImageView (this);
			image.setImageDrawable(getResources().getDrawable(R.drawable.loungeatude_3));
			image.setPadding (20, 0, 20, 0);
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
		
		else if (r.getEmail().equals("antoine.walsdorff@student.uclouvain.be")) {
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

		else if (r.getEmail().equals("ludovic.fastre@student.uclouvain.be")) {
			
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
		
		/*for(String imageName : r.getRestaurant().getRestaurantImageList(false)) { TODO -> I don't know how to do this
			ImageView image = new ImageView (this);
			if (imageName.equals("bret1")) {

			}
			else if (imageName.equals("bret2")) {

			}
			image.setImageDrawable(getResources().getDrawable(R.drawable.);
			image.setPadding (20, 0, 20, 0);
			listview.addView(image);
		}*/



		//We define all the listeners
		
		cooks.setOnClickListener(cooksListener);//TODO
		advantages.setOnClickListener(advantagesListener);
		payments.setOnClickListener(paymentsListener);
		closingButton.setOnClickListener(closingListener);
		horaire.setOnClickListener(scheduleListener);
		
		
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
				if (closingEdit.getText().toString().length()>0) {
					String []tmp = closingEdit.getText().toString().split("-");
					r.addRestaurantClosingDays(new Date(null, tmp[0], tmp[1]));
					closingEdit.setText("");
				}
				if (openTime.getText().toString().length()>0 && closeTime.getText().toString().length()>0) {
					r.addRestaurantHoraire(new OpenHour(openDay.getSelectedItem().toString(), new Time (openTime.getText().toString()), new Time (closeTime.getText().toString())));
					openTime.setText("");
					closeTime.setText("");
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
				Intent i = new Intent(Profil_Restaurant.this, CarteActivity.class);//TODO
				i.putExtra(name, r.getRestaurant().getRestaurantName());//TODO
				startActivity(i);
			}
		});

		prebook.setOnClickListener(new View.OnClickListener() {//launch another view
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(context, "Un client veut voir ses pr�-r�servations", Toast.LENGTH_SHORT);
				toast.show();
				/*Intent i = new Intent(Profil_Client.this, PreBooking_Client.class);//TODO
				i.putExtra(Login.email, r.getEmail());//TODO
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

	private View.OnClickListener cooksListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			 
			//set title and message
			alertDialogBuilder.setTitle(R.string.profil_restaurant_cooks_button);
			
			//set the list of checkBoxes (items_test is useful when we change of language)
			items_test = new String[]{getString(R.string.profil_restaurant_cooks_alert_traditional_test), getString(R.string.profil_restaurant_cooks_alert_french_test), getString(R.string.profil_restaurant_cooks_alert_regional_test), getString(R.string.profil_restaurant_cooks_alert_fusion_test), getString(R.string.profil_restaurant_cooks_alert_molecular_test), getString(R.string.profil_restaurant_cooks_alert_seafood_test), getString(R.string.profil_restaurant_cooks_alert_brewery_test), getString(R.string.profil_restaurant_cooks_alert_pancakes_test), getString(R.string.profil_restaurant_cooks_alert_mediterranean_test), getString(R.string.profil_restaurant_cooks_alert_maghreb_test), getString(R.string.profil_restaurant_cooks_alert_oriental_test), getString(R.string.profil_restaurant_cooks_alert_asian_test), getString(R.string.profil_restaurant_cooks_alert_other_test)};
			items = new String []{getString(R.string.profil_restaurant_cooks_alert_traditional), getString(R.string.profil_restaurant_cooks_alert_french), getString(R.string.profil_restaurant_cooks_alert_regional), getString(R.string.profil_restaurant_cooks_alert_fusion), getString(R.string.profil_restaurant_cooks_alert_molecular), getString(R.string.profil_restaurant_cooks_alert_seafood), getString(R.string.profil_restaurant_cooks_alert_brewery), getString(R.string.profil_restaurant_cooks_alert_pancakes), getString(R.string.profil_restaurant_cooks_alert_mediterranean), getString(R.string.profil_restaurant_cooks_alert_maghreb), getString(R.string.profil_restaurant_cooks_alert_oriental), getString(R.string.profil_restaurant_cooks_alert_asian), getString(R.string.profil_restaurant_cooks_alert_other)};
			checkedItems = new boolean [items.length];//Indicates whether or not the checkBox is checked
			for(int i = 0; i<r.getRestaurant().getRestaurantCuisine(true).size(); i++) {
				
				for(int j=0; j<items.length; j++) {
					if(r.getRestaurant().getRestaurantCuisine(false).get(i).equals(items_test[j])) {
						checkedItems[j]=true;
					}
				}
				
				
			}

			alertDialogBuilder.setMultiChoiceItems (items, checkedItems, new DialogInterface.OnMultiChoiceClickListener () {
				
				@Override
				public void onClick (DialogInterface dialog, int which, boolean isChecked) {
					checkedItems[which]=isChecked;
				}
			});
			
			
			alertDialogBuilder.setCancelable(true);//We can go back with the return button
			
			//The other buttons
			alertDialogBuilder.setPositiveButton(R.string.profil_client_alert_back,new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {//Cancel the alert box
					dialog.cancel();
				}
			});
			
			alertDialogBuilder.setNegativeButton(R.string.profil_client_alert_update,new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {//Apply modifications and cancel the alert box
					
					for(int i = 0; i<checkedItems.length; i++) {
						if (checkedItems[i]) {//If one element has to be added
							r.addRestaurantCuisine(items_test[i]);
						}
						else {//or removed
							r.removeRestaurantCuisine(items_test[i]);
						}
					}
					
					dialog.cancel();
				}
			});
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
	 
			// show it
			alertDialog.show();
		}
		
	};
	
	private View.OnClickListener advantagesListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			 
			//set title and message
			alertDialogBuilder.setTitle(R.string.profil_restaurant_advantages_button);
			
			//set the list of checkBoxes (items_test is useful when we change of language)
			items_test = new String[]{getString(R.string.profil_restaurant_advantages_alert_parking_test), getString(R.string.profil_restaurant_advantages_alert_lounge_test), getString(R.string.profil_restaurant_advantages_alert_valet_test), getString(R.string.profil_restaurant_advantages_alert_vegetarian_test), getString(R.string.profil_restaurant_advantages_alert_wifi_test), getString(R.string.profil_restaurant_advantages_alert_disable_test), getString(R.string.profil_restaurant_advantages_alert_caterer_test), getString(R.string.profil_restaurant_advantages_alert_terrace_test), getString(R.string.profil_restaurant_advantages_alert_air_conditioning_test), getString(R.string.profil_restaurant_advantages_alert_children_test)};
			items = new String []{getString(R.string.profil_restaurant_advantages_alert_parking), getString(R.string.profil_restaurant_advantages_alert_lounge), getString(R.string.profil_restaurant_advantages_alert_valet), getString(R.string.profil_restaurant_advantages_alert_vegetarian), getString(R.string.profil_restaurant_advantages_alert_wifi), getString(R.string.profil_restaurant_advantages_alert_disable), getString(R.string.profil_restaurant_advantages_alert_caterer), getString(R.string.profil_restaurant_advantages_alert_terrace), getString(R.string.profil_restaurant_advantages_alert_air_conditioning), getString(R.string.profil_restaurant_advantages_alert_children)};
			checkedItems = new boolean [items.length];//Indicates whether or not the checkBox is checked
			for(int i = 0; i<r.getRestaurant().getRestaurantAvantages(true).size(); i++) {
				
				for(int j=0; j<items.length; j++) {
					if(r.getRestaurant().getRestaurantAvantages(false).get(i).equals(items_test[j])) {
						checkedItems[j]=true;
					}
				}
				
				
			}

			alertDialogBuilder.setMultiChoiceItems (items, checkedItems, new DialogInterface.OnMultiChoiceClickListener () {
				
				@Override
				public void onClick (DialogInterface dialog, int which, boolean isChecked) {
					checkedItems[which]=isChecked;
				}
			});
			
			
			alertDialogBuilder.setCancelable(true);//We can go back with the return button
			
			//The other buttons
			alertDialogBuilder.setPositiveButton(R.string.profil_client_alert_back,new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {//Cancel the alert box
					dialog.cancel();
				}
			});
			
			alertDialogBuilder.setNegativeButton(R.string.profil_client_alert_update,new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {//Apply modifications and cancel the alert box
					
					for(int i = 0; i<checkedItems.length; i++) {
						if (checkedItems[i]) {//If one element has to be added
							r.addRestaurantAvantage(items_test[i]);
						}
						else {//or removed
							r.removeRestaurantAvantage(items_test[i]);
						}
					}
					
					dialog.cancel();
				}
			});
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
	 
			// show it
			alertDialog.show();
		}
		
	};
	
	private View.OnClickListener paymentsListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			 
			//set title and message
			alertDialogBuilder.setTitle(R.string.profil_restaurant_payments_button);
			
			//set the list of checkBoxes (items_test is useful when we change of language)
			items_test = new String[]{getString(R.string.profil_restaurant_payments_alert_cash_test), getString(R.string.profil_restaurant_payments_alert_bancontact_test), getString(R.string.profil_restaurant_payments_alert_visa_test), getString(R.string.profil_restaurant_payments_alert_ticket_restaurant_test), getString(R.string.profil_restaurant_payments_alert_proton_test), getString(R.string.profil_restaurant_payments_alert_mastercard_test), getString(R.string.profil_restaurant_payments_alert_check_test), getString(R.string.profil_restaurant_payments_alert_transfer_test)};
			items = new String []{getString(R.string.profil_restaurant_payments_alert_cash), getString(R.string.profil_restaurant_payments_alert_bancontact), getString(R.string.profil_restaurant_payments_alert_visa), getString(R.string.profil_restaurant_payments_alert_ticket_restaurant), getString(R.string.profil_restaurant_payments_alert_proton), getString(R.string.profil_restaurant_payments_alert_mastercard), getString(R.string.profil_restaurant_payments_alert_check), getString(R.string.profil_restaurant_payments_alert_transfer)};
			checkedItems = new boolean [items.length];//Indicates whether or not the checkBox is checked
			for(int i = 0; i<r.getRestaurant().getRestaurantTypePaiements(true).size(); i++) {
				
				for(int j=0; j<items.length; j++) {
					if(r.getRestaurant().getRestaurantTypePaiements(false).get(i).equals(items_test[j])) {
						checkedItems[j]=true;
					}
				}
				
				
			}

			alertDialogBuilder.setMultiChoiceItems (items, checkedItems, new DialogInterface.OnMultiChoiceClickListener () {
				
				@Override
				public void onClick (DialogInterface dialog, int which, boolean isChecked) {
					checkedItems[which]=isChecked;
				}
			});
			
			
			alertDialogBuilder.setCancelable(true);//We can go back with the return button
			
			//The other buttons
			alertDialogBuilder.setPositiveButton(R.string.profil_client_alert_back,new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {//Cancel the alert box
					dialog.cancel();
				}
			});
			
			alertDialogBuilder.setNegativeButton(R.string.profil_client_alert_update,new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {//Apply modifications and cancel the alert box
					
					for(int i = 0; i<checkedItems.length; i++) {
						if (checkedItems[i]) {//If one element has to be added
							r.addRestaurantTypePaiements(items_test[i]);
						}
						else {//or removed
							r.removeRestaurantTypePaiements(items_test[i]);
						}
					}
					
					dialog.cancel();
				}
			});
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
	 
			// show it
			alertDialog.show();
			
		}
		
	};

	private View.OnClickListener closingListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			 
			//set title and message
			alertDialogBuilder.setTitle(R.string.profil_restaurant_closing_button);
			
			//set the list of checkBoxes
			items = new String [r.getRestaurant().getRestaurantClosingDays(true).size()];
			checkedItems = new boolean [items.length];//Indicates whether or not the checkBox is checked
			for(int i = 0; i<r.getRestaurant().getRestaurantClosingDays(false).size(); i++) {
				
				items[i] = (r.getRestaurant().getRestaurantClosingDays(false).get(i)).toString();
				checkedItems[i] = true;
				
			}

			alertDialogBuilder.setMultiChoiceItems (items, checkedItems, new DialogInterface.OnMultiChoiceClickListener () {
				
				@Override
				public void onClick (DialogInterface dialog, int which, boolean isChecked) {
					checkedItems[which]=isChecked;
				}
			});
			
			
			alertDialogBuilder.setCancelable(true);//We can go back with the return button
			
			//The other buttons
			alertDialogBuilder.setPositiveButton(R.string.profil_client_alert_back,new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {//Cancel the alert box
					dialog.cancel();
				}
			});
			
			alertDialogBuilder.setNegativeButton(R.string.profil_client_alert_update,new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {//Apply modifications and cancel the alert box
					
					for(int i = 0; i<checkedItems.length; i++) {
						if (checkedItems[i]) {//If one element has to be added
							String []tmp = items[i].split("-");
							r.addRestaurantClosingDays(new Date (null, tmp[0], tmp[1]));
						}
						else {//or removed
							String []tmp = items[i].split("-");
							r.removeRestaurantClosingDays(new Date (null, tmp[0], tmp[1]));
						}
					}
					
					dialog.cancel();
				}
			});
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
	 
			// show it
			alertDialog.show();
		}
		
	};
	
	private View.OnClickListener scheduleListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			 
			//set title and message
			alertDialogBuilder.setTitle(R.string.profil_restaurant_schedule_button);
			
			//set the list of checkBoxes
			items = new String [r.getRestaurant().getRestaurantHoraire(true).size()];
			checkedItems = new boolean [items.length];//Indicates whether or not the checkBox is checked
			for(int i = 0; i<r.getRestaurant().getRestaurantHoraire(false).size(); i++) {
				
				items[i] = (r.getRestaurant().getRestaurantHoraire(false).get(i)).toString();
				checkedItems[i] = true;
				
			}

			alertDialogBuilder.setMultiChoiceItems (items, checkedItems, new DialogInterface.OnMultiChoiceClickListener () {
				
				@Override
				public void onClick (DialogInterface dialog, int which, boolean isChecked) {
					checkedItems[which]=isChecked;
				}
			});
			
			
			alertDialogBuilder.setCancelable(true);//We can go back with the return button
			
			//The other buttons
			alertDialogBuilder.setPositiveButton(R.string.profil_client_alert_back,new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {//Cancel the alert box
					dialog.cancel();
				}
			});
			
			alertDialogBuilder.setNegativeButton(R.string.profil_client_alert_update,new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {//Apply modifications and cancel the alert box
					
					for(int i = 0; i<checkedItems.length; i++) {
						if (checkedItems[i]) {//If one element has to be added
							r.addRestaurantHoraire(new OpenHour (items[i]));
						}
						else {//or removed
							
							OpenHour horaire = new OpenHour (items[i]);
							r.removeRestaurantHoraire(horaire);
						}
					}
					
					dialog.cancel();
				}
			});
			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();
	 
			// show it
			alertDialog.show();
		}
		
	};

}