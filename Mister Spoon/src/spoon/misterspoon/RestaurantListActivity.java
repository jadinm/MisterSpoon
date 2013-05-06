package spoon.misterspoon;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class RestaurantListActivity extends Activity implements LocationListener {
	
	public static final String RESTAURANT = "Selected Restaurant";
	public static final String emailLogin = "";
	Context context = this;
	MySQLiteHelper sql = new MySQLiteHelper(this);
	
	Client client;
	String sclient;
	Restaurant currentRestaurant;
	
	private View pressedView;
	
	ListView restaurantListView;
	RestaurantList restaurantList;
	List<String> nomRestaurants;
	ArrayAdapter<String> adapter;
	
	Spinner ordre;
	List<String> orderList;
	ArrayAdapter<String> adapterOrdre;
	
	CheckBox filtreNote;
	EditText textNote;
	CheckBox filtrePrix;
	EditText textPrix;
	CheckBox filtreFavori;
	
	Button selectRestaurant;
	
	Activity activity = this;
	LocationListener location = this;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_list_restaurant);
		
		Intent i = getIntent();
		String city = i.getStringExtra(CityListActivity.CITY);
		sclient = i.getStringExtra(CityListActivity.email);
		
		client = new Client (sql, sclient);
		restaurantList = new RestaurantList (sql, city, client, this, this);
		nomRestaurants = restaurantList.getNomRestaurants();  //Renvoie une liste de string correspondant aux noms des restaurants    
		
		
		restaurantListView = (ListView) findViewById(R.id.list_restaurant_restaurants);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, nomRestaurants);
		restaurantListView.setAdapter(adapter);
		
		ordre = (Spinner) findViewById(R.id.list_restaurant_ordre);
		orderList = Arrays.asList(RestaurantList.orderTable);
		adapterOrdre = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,orderList);
		adapterOrdre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ordre.setAdapter(adapterOrdre);
		if (restaurantList.getOrdre().equals(RestaurantList.orderTable[0])) {//Show the good one
			ordre.setSelection(0);
		}
		else if (restaurantList.getOrdre().equals(RestaurantList.orderTable[2])) {
			ordre.setSelection(2);
		}
		
		filtreNote = (CheckBox) findViewById(R.id.list_restaurant_filtre_note);
		textNote = (EditText) findViewById(R.id.list_restaurant_editText_note);
		filtrePrix = (CheckBox) findViewById(R.id.list_restaurant_filtre_prix);
		textPrix = (EditText) findViewById(R.id.list_restaurant_editText_prix);
		filtreFavori = (CheckBox) findViewById(R.id.list_restaurant_filtre_favori);
		
		//textNote.setInputType(0); // Hide the keyboard	
		
		selectRestaurant = (Button) findViewById(R.id.list_restaurant_selection);
		
		restaurantListView.setOnItemClickListener(restaurantListListener);
		
		ordre.setOnItemSelectedListener(ordreListener);
		filtreNote.setOnCheckedChangeListener(filtreNoteListener);
		filtrePrix.setOnCheckedChangeListener(filtrePrixListener);
		filtreFavori.setOnCheckedChangeListener(filtreFavoriListener);
		
		selectRestaurant.setOnClickListener(selectRestaurantListener);
	}
	
	private OnItemSelectedListener ordreListener = new OnItemSelectedListener(){

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (((String) ordre.getSelectedItem()).equals(RestaurantList.orderTable[2])) {//If he try to organize that with the gps
				if (client.getPosition(null, null)==null) {//If we didn't check yet
					if (client.getPosition(location, activity)==null) {//GPS not activated
						Toast toast = Toast.makeText(context, getString(R.string.list_restaurant_toast_gps), Toast.LENGTH_SHORT);
						toast.show();
						restaurantList.sort(RestaurantList.orderTable[0]);//"abc"
						nomRestaurants = restaurantList.getNomRestaurants();
						adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomRestaurants);
						restaurantListView.setAdapter(adapter);
						ordre.setSelection(0);
						return;
					}
				}
			}
			restaurantList.sort((String) ordre.getSelectedItem());
			nomRestaurants = restaurantList.getNomRestaurants();
			adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomRestaurants);
			restaurantListView.setAdapter(adapter);
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// Nothing
		}
		
	};
	
	private OnItemClickListener restaurantListListener = new OnItemClickListener(){
		@SuppressWarnings("deprecation")
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			currentRestaurant = new Restaurant(((TextView)arg1).getText().toString());
			Drawable originalBackground = arg1.getBackground();
			if(pressedView!=null)
				pressedView.setBackgroundDrawable(originalBackground);
			pressedView = arg1; // Point pressedView to new item
			pressedView.setBackgroundColor(Color.parseColor("#80B7DBE8")); // reset background of old item
		}

	};
	
	private OnCheckedChangeListener filtreNoteListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked && textNote.getText().toString().length() > 0) {
				restaurantList.listFilter(RestaurantList.filterTable[1], Integer.parseInt(textNote.getText().toString()));
				nomRestaurants = restaurantList.getNomRestaurants();
				adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomRestaurants);
				restaurantListView.setAdapter(adapter);
			}
			else {
				restaurantList.resetfilterList();
				if(filtrePrix.isChecked() && textPrix.getText().toString().length() > 0){
					restaurantList.listFilter(RestaurantList.filterTable[2], Double.parseDouble(textPrix.getText().toString()));
				}
				if(filtreFavori.isChecked()){
					restaurantList.listFilter(RestaurantList.filterTable[0], 0);
				}
				restaurantList.sort(((String) ordre.getSelectedItem()));
				nomRestaurants = restaurantList.getNomRestaurants();
				adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomRestaurants);
				restaurantListView.setAdapter(adapter);
			}
		}
	};
	
	private OnCheckedChangeListener filtrePrixListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked && textPrix.getText().toString().length() > 0) {
				restaurantList.listFilter(RestaurantList.filterTable[2], Double.parseDouble(textPrix.getText().toString()));
				nomRestaurants = restaurantList.getNomRestaurants();
				adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomRestaurants);
				restaurantListView.setAdapter(adapter);
			}
			else {
				restaurantList.resetfilterList();
				if(filtreNote.isChecked() && textNote.getText().toString().length() > 0){
					restaurantList.listFilter(RestaurantList.filterTable[1], Integer.parseInt(textNote.getText().toString()));
				}
				if(filtreFavori.isChecked()){
					restaurantList.listFilter(RestaurantList.filterTable[0], 0);
				}
				restaurantList.sort(((String) ordre.getSelectedItem()));
				nomRestaurants = restaurantList.getNomRestaurants();
				adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomRestaurants);
				restaurantListView.setAdapter(adapter);
			}
		}
	};
	
	private OnCheckedChangeListener filtreFavoriListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (filtreFavori.isChecked()) {
				restaurantList.listFilter(RestaurantList.filterTable[0], 0);
				nomRestaurants = restaurantList.getNomRestaurants();
				adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomRestaurants);
				restaurantListView.setAdapter(adapter);
			}
			else {
				restaurantList.resetfilterList();
				if(filtreNote.isChecked() && textNote.getText().toString().length() > 0){
					restaurantList.listFilter(RestaurantList.filterTable[1], Integer.parseInt(textNote.getText().toString()));
				}
				if(filtrePrix.isChecked() && textPrix.getText().toString().length() > 0){
					restaurantList.listFilter(RestaurantList.filterTable[2], Double.parseDouble(textPrix.getText().toString()));
				}
				restaurantList.sort(((String) ordre.getSelectedItem()));
				nomRestaurants = restaurantList.getNomRestaurants();
				adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomRestaurants);
				restaurantListView.setAdapter(adapter);
			}
		}
	};
	
	private OnClickListener selectRestaurantListener = new OnClickListener() {
		public void onClick(View v) {
			if (currentRestaurant==null) {
				Toast toast = Toast.makeText(context, getString(R.string.list_restaurant_toast), Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			//Toast toast = Toast.makeText(context, "Ceci est un leurre ! Mouhahaha !", Toast.LENGTH_SHORT);
			//toast.show();
			Intent intent = new Intent(RestaurantListActivity.this,RestaurantForClient.class);
			intent.putExtra(RESTAURANT, currentRestaurant.getRestaurantName());
			intent.putExtra(emailLogin, sclient);
			startActivity(intent);
			return;
		}
	};
	
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out , R.anim.slide_up );
	}

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
}
