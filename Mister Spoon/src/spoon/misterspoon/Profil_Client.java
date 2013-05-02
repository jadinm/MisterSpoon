package spoon.misterspoon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Profil_Client extends Activity {

	private Client c;
	private MySQLiteHelper sqliteHelper;
	
	//Elements of the view
	private TextView email;
	private TextView name;
	private TextView gsm;
	
	private TextView new_specificity;
	private TextView new_favourite_meal;
	private TextView new_favourite_restaurant;
	
	private Button specificity_list;
	private Button favourite_meal_list;
	private Button favourite_restaurant_list;
	private Button allergy_list;
	private Button preBooking;
	private Button booking;
	
	private Button update;
	private Button next;
	
	//Useful for the alertBoxes
	private Context context = this;
	private boolean [] checkedItems;
	private String [] items;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profil_client);
		
		//We get the intent sent by Login
		Intent i = getIntent();
		//We take the informations about the person who's logged (!!!! label)
		String emailClient = i.getStringExtra(Login.email);
		//We create the object Client associated with this email and all his informations
		sqliteHelper = new MySQLiteHelper(this);
		c = new Client (sqliteHelper, emailClient);//TODO

		//We can now define all the widgets
		email = (TextView) findViewById(R.id.profil_client_email_edit_text);
		name = (TextView) findViewById(R.id.profil_client_name_edit_text);
		gsm = (TextView) findViewById(R.id.profil_client_gsm_edit_text);
		
		new_specificity = (TextView) findViewById(R.id.profil_client_new_specificity_edit_text);
		new_favourite_meal = (TextView) findViewById(R.id.profil_client_new_favourite_meal_edit_text);
		new_favourite_restaurant = (TextView) findViewById(R.id.profil_client_new_favourite_restaurant_edit_text);
		
		specificity_list = (Button) findViewById(R.id.profil_client_specificity_list_button);
		favourite_meal_list = (Button) findViewById(R.id.profil_client_favourite_meal_list_button);
		favourite_restaurant_list = (Button) findViewById(R.id.profil_client_favourite_restaurant_list_button);
		allergy_list = (Button) findViewById(R.id.profil_client_allergy_list_button);
		preBooking = (Button) findViewById(R.id.profil_client_preBooking_button);
		booking = (Button) findViewById(R.id.profil_client_booking_button);
		
		update = (Button) findViewById(R.id.profil_client_update_button);
		next = (Button) findViewById(R.id.profil_client_next_button);
		
		//We already fill the data of the Client if they exist
		email.setText(c.getEmail()); 
		name.setText(c.getName(false)); 
		if (c.getGsm(false)!=null) {
			gsm.setText(c.getGsm(false));
		}
		
		//We define all the listeners
		specificity_list.setOnClickListener(new View.OnClickListener() {//launch an alert box 
			@Override
			public void onClick(View v) {
				
				if (c.getSpecificite(false).size()==0) {//If there is no element to show
					Toast toast = Toast.makeText(context, getString(R.string.profil_client_alert_null), Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		 
				//set title and message
				alertDialogBuilder.setTitle(R.string.profil_client_specificity_list);
				alertDialogBuilder.setMessage(R.string.profil_client_alert_message);
				
				//set the list of checkBoxes
				(c.getSpecificite(false)).toArray(items);
				checkedItems = new boolean [items.length];
				for(int i = 0; i<checkedItems.length; i++) {//Indicates whether or not the checkBox is checked
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
							if (!checkedItems[i]) {//If one element has to be removed
								c.removeSpecificite(items[i]);
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
		});
		
		favourite_meal_list.setOnClickListener(new View.OnClickListener() {//launch an alert box
			@Override
			public void onClick(View v) {
				
				if (c.getPlatFav(false).size()==0) {//If there is no element to show
					Toast toast = Toast.makeText(context, getString(R.string.profil_client_alert_null), Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				 
				//set title and message
				alertDialogBuilder.setTitle(R.string.profil_client_favourite_meal_list);
				alertDialogBuilder.setMessage(R.string.profil_client_alert_message);
				
				//set the list of checkBoxes
				items = new String [c.getPlatFav(false).size()];
				checkedItems = new boolean [items.length];//Indicates whether or not the checkBox is checked
				for(int i = 0; i<checkedItems.length; i++) {
					items[i] = (c.getPlatFav(false).get(i)).getMealName();
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
							if (!checkedItems[i]) {//If one element has to be removed
								c.removePlatFav(new Meal(items[i]));
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
		});
		
		favourite_restaurant_list.setOnClickListener(new View.OnClickListener() {//launch an alert box
			@Override
			public void onClick(View v) {

				if (c.getRestFav(false).size()==0) {//If there is no element to show
					Toast toast = Toast.makeText(context, getString(R.string.profil_client_alert_null), Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				 
				//set title and message
				alertDialogBuilder.setTitle(R.string.profil_client_favourite_restaurant_list);
				alertDialogBuilder.setMessage(R.string.profil_client_alert_message);
				
				//set the list of checkBoxes
				items = new String [c.getRestFav(false).size()];
				checkedItems = new boolean [items.length];//Indicates whether or not the checkBox is checked
				for(int i = 0; i<checkedItems.length; i++) {
					items[i] = (c.getRestFav(false).get(i)).getRestaurantName();
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
							if (!checkedItems[i]) {//If one element has to be removed
								c.removeRestFav(new Restaurant(items[i]));
							}
						}
						
						dialog.cancel();
					}
				});
			}
		});
		
		allergy_list.setOnClickListener(new View.OnClickListener() {//launch an alert box
			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				 
				//set title and message
				alertDialogBuilder.setTitle(R.string.profil_client_favourite_restaurant_list);
				alertDialogBuilder.setMessage(R.string.profil_client_alert_message);
				
				//set the list of checkBoxes
				items = new String []{getString(R.string.profil_client_allergy_gluten), getString(R.string.profil_client_allergy_noix), getString(R.string.profil_client_allergy_cacahuete), getString(R.string.profil_client_allergy_banane)};
				checkedItems = new boolean [items.length];//Indicates whether or not the checkBox is checked
				for(int i = 0; i<checkedItems.length; i++) {
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
								c.addAllergie(items[i]);
							}
							else {//or removed
								c.removeAllergie(items[i]);
							}
						}
						
						dialog.cancel();
					}
				});
			}
		});
		
		preBooking.setOnClickListener(new View.OnClickListener() {//launch another view
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(context, "Un client veut voir ses prŽ-rŽservations", Toast.LENGTH_SHORT);
				toast.show();
				/*Intent i = new Intent(Profil_Client.this, PreBooking_Client.class);//TODO
				i.putExtra(Login.email, c.getEmail());//TODO
				startActivity(i);*/
			}
		});
		
		booking.setOnClickListener(new View.OnClickListener() {//launch another view
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(context, "Un client veut voir ses rŽservations", Toast.LENGTH_SHORT);
				toast.show();
				/*Intent i = new Intent(Profil_Client.this, Booking_Client.class);//TODO
				i.putExtra(Login.email, c.getEmail());//TODO
				startActivity(i);*/
			}
		});
		
		update.setOnClickListener(new View.OnClickListener() {//Update the informations
			@Override
			public void onClick(View v) {
				if(email.getText()==null) {//If important information is not filled
					Toast toast = Toast.makeText(context, getString(R.string.profil_client_email) + getString(R.string.profil_client_toast_mandatory), Toast.LENGTH_SHORT);
					toast.show();
					email.setText(c.getEmail());
					return;
				}
				if(name.getText()==null) {//If important information is not filled
					Toast toast = Toast.makeText(context, getString(R.string.profil_client_name) + getString(R.string.profil_client_toast_mandatory), Toast.LENGTH_SHORT);
					toast.show();
					name.setText(c.getName(false));
					return;
				}
				if (!(c.setEmail((String) email.getText()))) {//If it already exists
					Toast toast = Toast.makeText(context, email.getText() + getString(R.string.profil_client_toast_already_exist), Toast.LENGTH_SHORT);
					toast.show();
					email.setText(c.getEmail());
				}
				if(!(c.setName((String) name.getText()))) {//If it already exists
					Toast toast = Toast.makeText(context, name.getText() + getString(R.string.profil_client_toast_already_exist), Toast.LENGTH_SHORT);
					toast.show();
					name.setText(c.getName(false));
					return;
				}
				c.setGsm((String) gsm.getText());
				
				c.addSpecificite((String) new_specificity.getText());
				c.addPlatFav(new Meal ((String) new_favourite_meal.getText()));
				c.addRestFav(new Restaurant ((String) new_favourite_restaurant.getText()));
			}
		});
		
		next.setOnClickListener(new View.OnClickListener() {//launch an other activity with an intent with the object Client
			@Override
			public void onClick(View v) {
				Toast toast = Toast.makeText(context, "Un client veut passer ˆ la suite", Toast.LENGTH_SHORT);
				toast.show();
				/*Intent i = new Intent(Profil_Client.this, City_List.class);//TODO
				i.putExtra(Login.email, c.getEmail());//TODO
				startActivity(i);*/
			}
		});
		
	}
	
}
