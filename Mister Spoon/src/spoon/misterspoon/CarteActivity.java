package spoon.misterspoon;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Spinner;
import android.widget.Toast;


public class CarteActivity extends Activity {

	public static final String RESTAURANT = "Selected Restaurant";
	public static final String MEAL = "Selected Meal";
	public static final String CLIENT = "User";

	private MySQLiteHelper sqliteHelper;
	private Context context = this;

	private String restName;
	private Carte carte;
	private Client client;
	private Meal currentMeal;

	private CheckBox favori;
	private CheckBox prix;
	private EditText prix_edit;

	private EditText invisible;

	private Spinner categorie;
	private List<String> categorieList;
	private ArrayAdapter<String> adapterCategorie;

	private Spinner ordre;
	private List<String> orderList;
	private ArrayAdapter<String> adapterOrdre;


	private ExpandableListView carteListView;
	private CarteActivityListAdapter adapter;

	private Button preBooking;
	private Button booking;
	
	//useful for the alertBox
	private CheckBox childView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_carte);
		sqliteHelper = new MySQLiteHelper(this);
		Intent i = getIntent();
		restName = i.getStringExtra(RestaurantListActivity.RESTAURANT);
		client = new Client(sqliteHelper, i.getStringExtra(Login.email));
		carte = new Carte(sqliteHelper, restName, client);


		favori = (CheckBox) findViewById(R.id.carte_favori);
		prix = (CheckBox) findViewById(R.id.carte_price_check);
		prix_edit = (EditText) findViewById(R.id.carte_price_edit);
		categorie = (Spinner) findViewById(R.id.carte_categorie);
		ordre = (Spinner) findViewById(R.id.carte_ordre);

		carteListView = (ExpandableListView) findViewById(R.id.carte_expandableList);

		preBooking = (Button) findViewById(R.id.carte_prebooking_button);
		booking = (Button) findViewById(R.id.carte_booking_button);

		invisible = (EditText) findViewById(R.id.edit_invisible_focus_holder);
		invisible.setInputType(InputType.TYPE_NULL);
		invisible.requestFocus();

		//Spinners

		orderList = Arrays.asList(Carte.orderMeal);
		adapterOrdre = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,orderList);
		adapterOrdre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ordre.setAdapter(adapterOrdre);
		ordre.setSelection(0);

		categorieList = Arrays.asList(Carte.categorie);
		adapterCategorie = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categorieList);
		adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categorie.setAdapter(adapterCategorie);
		categorie.setSelection(4);

		//ListView
		adapter = new CarteActivityListAdapter(this, carte.getFilterList());
		
		carteListView.setAdapter(adapter);

		//Listeners
		favori.setOnCheckedChangeListener(favouriteListener);
		prix.setOnCheckedChangeListener(priceListener);
		categorie.setOnItemSelectedListener(categorieListener);
		ordre.setOnItemSelectedListener(ordreListener);

		carteListView.setOnChildClickListener(carteListViewChild);

		preBooking.setOnClickListener(preBookingListener);
		booking.setOnClickListener(bookingListener);

	}

	private OnChildClickListener carteListViewChild =  new OnChildClickListener() {

		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
			
			Log.v("listener", "we are here");
			
			Meal meal = carte.getMenuList().get(groupPosition).getMealList(false).get(childPosition);
			
			currentMeal = meal;
			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			
			builder.setTitle(meal.getMealName());
			builder.setMessage(getString(R.string.carte_alert_message));
			
			builder.setCancelable(true);//We can go back with the return button
			
			builder.setNegativeButton(getString(R.string.carte_alert_negative), new DialogInterface.OnClickListener() {

				@Override
				public void onClick (DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			builder.setPositiveButton(getString(R.string.carte_alert_positive), new DialogInterface.OnClickListener() {

				@Override
				public void onClick (DialogInterface dialog, int id) {//launch the last activity
					Intent i = new Intent(CarteActivity.this, MealActivity.class);
					i.putExtra(RESTAURANT, restName);
					i.putExtra(MEAL, currentMeal.getMealName());
					i.putExtra(CLIENT, client.getEmail());
					startActivity(i);
					return;
				}
			});
			
			AlertDialog alertDialog = builder.create();
			
			alertDialog.show();

			return true;
		}

	};

	private OnItemSelectedListener categorieListener = new OnItemSelectedListener(){

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			if (categorie.getSelectedItem().equals(Carte.categorie[0])) {
				carte.filter(Carte.categorie[0], 0);
			}
			else if (categorie.getSelectedItem().equals(Carte.categorie[1])) {
				carte.filter(Carte.categorie[1], 0);
			}
			else if (categorie.getSelectedItem().equals(Carte.categorie[2])) {
				carte.filter(Carte.categorie[2], 0);
			}
			else if (categorie.getSelectedItem().equals(Carte.categorie[3])) {
				carte.filter(Carte.categorie[3], 0);
			}
			else if (categorie.getSelectedItem().equals(Carte.categorie[4])) {
				carte.filter(Carte.categorie[4], 0);
			}

			carte.sort();

			adapter = new CarteActivityListAdapter(context, carte.getFilterList());
			carteListView.setAdapter(adapter);

		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// Nothing
		}

	};

	private OnItemSelectedListener ordreListener = new OnItemSelectedListener(){

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			if (ordre.getSelectedItem().equals(Carte.orderMeal[0])) {
				carte.setMealOrder(Carte.orderMeal[0]);
			}
			else if (ordre.getSelectedItem().equals(Carte.orderMeal[1])) {
				carte.setMealOrder(Carte.orderMeal[1]);
			}

			carte.sort();
			
			adapter = new CarteActivityListAdapter(context, carte.getFilterList());
			carteListView.setAdapter(adapter);

		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// Nothing
		}

	};

	private OnCheckedChangeListener favouriteListener = new OnCheckedChangeListener() {

		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

			if (isChecked) {
				carte.filter(Carte.filterMeal[1], 0);
				
				adapter = new CarteActivityListAdapter(context, carte.getFilterList());
				carteListView.setAdapter(adapter);
			}
			else {
				carte.resetfilterList();
				if(prix.isChecked() && prix_edit.getText().toString().length() > 0){
					carte.filter(Carte.filterMeal[0], Float.parseFloat(prix_edit.getText().toString()));
				}

				carte.sort();
				
				adapter = new CarteActivityListAdapter(context, carte.getFilterList());
				carteListView.setAdapter(adapter);
			}
		}
	};

	private OnCheckedChangeListener priceListener = new OnCheckedChangeListener() {

		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

			if (isChecked && prix_edit.getText().toString().length() > 0) {
				carte.filter(Carte.filterMeal[0], Float.parseFloat(prix_edit.getText().toString()));
				
				adapter = new CarteActivityListAdapter(context, carte.getFilterList());
				carteListView.setAdapter(adapter);
			}
			else if (isChecked && prix_edit.getText().toString().length() == 0) {

				prix.setChecked(false);

				Toast toast = Toast.makeText(context, R.string.carte_toast_no_price, Toast.LENGTH_SHORT);
				toast.show();
			}
			else {

				prix_edit.setText("");

				carte.resetfilterList();
				if(favori.isChecked()){
					carte.filter(Carte.filterMeal[1], 0);
				}

				carte.sort();
				
				adapter = new CarteActivityListAdapter(context, carte.getFilterList());
				carteListView.setAdapter(adapter);
			}
		}
	};

	private OnClickListener preBookingListener = new OnClickListener() {
		public void onClick(View v) {
			Toast toast = Toast.makeText(context, getString(R.string.carte_prebooking_toast), Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
	};


	private OnClickListener bookingListener = new OnClickListener() {
		public void onClick(View v) {
			Toast toast = Toast.makeText(context, getString(R.string.carte_booking_toast), Toast.LENGTH_SHORT);
			toast.show();
			/*Intent i = new Intent(CarteActivity.this, ReservationClientActivity.class);
			i.putExtra(Login.email, c.getEmail());//TODO
			startActivity(i);
			*/
			return;
		}
	};


	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}


}
