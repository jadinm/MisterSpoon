package spoon.misterspoon;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Profil_Client extends Activity {

	private Client c;
	private MySQLiteHelper sqliteHelper;
	String emailClient;
	
	//Elements of the view
	private TextView email;
	private EditText name;
	private EditText gsm;
	private EditText invisible;
	
	private EditText new_specificity;
	private EditText new_favourite_meal;
	private EditText new_favourite_restaurant;
	
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
	String [] items_test;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_profil_client);
		
		//We get the intent sent by Login
		Intent i = getIntent();
		//We take the informations about the person who's logged (!!!! label)
		emailClient = i.getStringExtra(Login.email);
				
		//We create the object Client associated with this email and all his informations
		sqliteHelper = new MySQLiteHelper(this);
		c = new Client (sqliteHelper, emailClient);
		//We can now define all the widgets
		invisible = (EditText) findViewById(R.id.edit_invisible_focus_holder);
		invisible.setInputType(InputType.TYPE_NULL);
		invisible.requestFocus();
		email = (TextView) findViewById(R.id.profil_client_email_text_view);
		name = (EditText) findViewById(R.id.profil_client_name_edit_text);
		gsm = (EditText) findViewById(R.id.profil_client_gsm_edit_text);
		
		new_specificity = (EditText) findViewById(R.id.profil_client_new_specificity_edit_text);
		new_favourite_meal = (EditText) findViewById(R.id.profil_client_new_favourite_meal_edit_text);
		new_favourite_restaurant = (EditText) findViewById(R.id.profil_client_new_favourite_restaurant_edit_text);
		
		//name.setInputType(0); // Hide the keyboard	
		
		specificity_list = (Button) findViewById(R.id.profil_client_specificity_list_button);
		favourite_meal_list = (Button) findViewById(R.id.profil_client_favourite_meal_list_button);
		favourite_restaurant_list = (Button) findViewById(R.id.profil_client_favourite_restaurant_list_button);
		allergy_list = (Button) findViewById(R.id.profil_client_allergy_list_button);
		preBooking = (Button) findViewById(R.id.profil_client_prebooking_button);
		booking = (Button) findViewById(R.id.profil_client_booking_button);
		
		update = (Button) findViewById(R.id.profil_client_update_button);
		next = (Button) findViewById(R.id.profil_client_next_button);
		
		email.setText(email.getText() + " " + c.getEmail()); 
		name.setText(c.getName(true));
		if (c.getGsm(true)!=null) {
			gsm.setText(c.getGsm(true));
		}

		//We define all the listeners
		specificity_list.setOnClickListener(new View.OnClickListener() {//launch an alert box 
			@Override
			public void onClick(View v) {
				
				if (c.getSpecificite(true).size()==0) {//If there is no element to show
					Toast toast = Toast.makeText(context, getString(R.string.profil_client_alert_null), Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		 
				//set title and message
				alertDialogBuilder.setTitle(R.string.profil_client_specificity_list);
				//alertDialogBuilder.setMessage(R.string.profil_client_alert_message);
				//set the list of checkBoxes
				items = new String [c.getSpecificite(false).size()];
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
				//alertDialogBuilder.setMessage(R.string.profil_client_alert_message);
				
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
				//alertDialogBuilder.setMessage(R.string.profil_client_alert_message);
				
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
		
		
		
		allergy_list.setOnClickListener(new View.OnClickListener() {//launch an alert box
			@Override
			public void onClick(View v) {
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				 
				//set title and message
				alertDialogBuilder.setTitle(R.string.profil_client_allergy_list);
				//alertDialogBuilder.setMessage(R.string.profil_client_alert_message);
				
				//set the list of checkBoxes (items_test is useful when we change of language)
				items_test = new String[]{getString(R.string.profil_client_allergy_gluten_test), getString(R.string.profil_client_allergy_noix_test), getString(R.string.profil_client_allergy_cacahuete_test), getString(R.string.profil_client_allergy_banane_test)};
				items = new String []{getString(R.string.profil_client_allergy_gluten), getString(R.string.profil_client_allergy_noix), getString(R.string.profil_client_allergy_cacahuete), getString(R.string.profil_client_allergy_banane)};
				checkedItems = new boolean [items.length];//Indicates whether or not the checkBox is checked
				for(int i = 0; i<c.getAllergie(true).size(); i++) {
					
					for(int j=0; j<items.length; j++) {
						if(c.getAllergie(false).get(i).equals(items_test[j])) {
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
								c.addAllergie(items_test[i]);
							}
							else {//or removed
								c.removeAllergie(items_test[i]);
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
		
		preBooking.setOnClickListener(new View.OnClickListener() {//launch another view
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Profil_Client.this, PreReservationProfilClientActivity.class);
				i.putExtra(Login.email, c.getEmail());//TODO
				startActivity(i);
			}
		});
		
		booking.setOnClickListener(new View.OnClickListener() {//launch another view
			@Override
			public void onClick(View v) {
				//Toast toast = Toast.makeText(context, "Un client veut voir ses r�servations", Toast.LENGTH_SHORT);
				//toast.show();
				Intent i = new Intent(Profil_Client.this, ShowReservationClientActivity.class);
				i.putExtra(Login.email, c.getEmail());//TODO
				startActivity(i);
			}
		});
		
		update.setOnClickListener(new View.OnClickListener() {//Update the informations
			@Override
			public void onClick(View v) {
				if(name.getText().toString()==null) {//If important information is not filled
					name.setText(c.getName(false));
					Toast toast = Toast.makeText(context, getString(R.string.profil_client_name) + getString(R.string.profil_client_toast_mandatory), Toast.LENGTH_SHORT);
					toast.show();
					
					return;
				}
				int value = Client.isInDatabase(sqliteHelper, (String) c.getEmail(), name.getText().toString());
				if(value==2 && c.getName(false).equals(name.getText().toString())) {//If it already exists
					name.setText(c.getName(false));
					Toast toast = Toast.makeText(context, name.getText().toString() + getString(R.string.profil_client_toast_already_exist), Toast.LENGTH_SHORT);
					toast.show();
					
					return;
				}
				c.setName(name.getText().toString());
				
				if (gsm.getText().toString().length()>0) {
					c.setGsm(gsm.getText().toString());
				}
				
				if (new_specificity.getText().toString().length()>0) {
					c.addSpecificite(new_specificity.getText().toString());
					new_specificity.setText("");
				}
				if (new_favourite_meal.getText().toString().length()>0) {
					c.addPlatFav(new Meal (new_favourite_meal.getText().toString()));
					new_favourite_meal.setText("");
				}
				if (new_favourite_restaurant.getText().toString().length()>0) {
					c.addRestFav(new Restaurant (new_favourite_restaurant.getText().toString()));
					new_favourite_restaurant.setText("");
				}
				
				Toast toasted = Toast.makeText(context, getString(R.string.profil_client_toast_uptodate), Toast.LENGTH_SHORT);
				toasted.show();
				
				
				
			}
			
		});
		
		next.setOnClickListener(new View.OnClickListener() {//launch an other activity with an intent with the object Client
			@Override
			public void onClick(View v) {
				
				/////////// Si le client ne clique pas sur mise a jour mais sur suivant ces infos sont quand meme sauv�es !/////////
				
				if(name.getText().toString()==null) {//If important information is not filled
					name.setText(c.getName(false));
					Toast toast = Toast.makeText(context, getString(R.string.profil_client_name) + getString(R.string.profil_client_toast_mandatory), Toast.LENGTH_SHORT);
					toast.show();
					
					return;
				}
				int value = Client.isInDatabase(sqliteHelper, (String) c.getEmail(), name.getText().toString());
				if(value==2 && c.getName(false).equals(name.getText().toString())) {//If it already exists
					name.setText(c.getName(false));
					Toast toast = Toast.makeText(context, name.getText().toString() + getString(R.string.profil_client_toast_already_exist), Toast.LENGTH_SHORT);
					toast.show();
					
					return;
				}
				c.setName(name.getText().toString());
				
				if (gsm.getText().toString().length()>0) {
					c.setGsm(gsm.getText().toString());
				}
				
				if (new_specificity.getText().toString().length()>0) {
					c.addSpecificite(new_specificity.getText().toString());
					new_specificity.setText("");
				}
				if (new_favourite_meal.getText().toString().length()>0) {
					c.addPlatFav(new Meal (new_favourite_meal.getText().toString()));
					new_favourite_meal.setText("");
				}
				if (new_favourite_restaurant.getText().toString().length()>0) {
					c.addRestFav(new Restaurant (new_favourite_restaurant.getText().toString()));
					new_favourite_restaurant.setText("");
				}
				
				//////////////////////////////////////////////////////
				Intent i = new Intent(Profil_Client.this, CityListActivity.class);
				i.putExtra(Login.email, c.getEmail());
				startActivity(i);
				return;
			}
		});
		
	}
	
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}
	
	@Override
	public void onStop() {
		
		new_specificity.setText("");
		new_favourite_meal.setText("");
		new_favourite_restaurant.setText("");
		super.onStop();
	}
	
	@Override
	public void onStart() {
		c = new Client (sqliteHelper, emailClient);
		super.onStart();
	}
	
}