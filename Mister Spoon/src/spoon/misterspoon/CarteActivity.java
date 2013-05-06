package spoon.misterspoon;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class CarteActivity extends Activity {
	
	public static final String RESTAURANT = "Selected Restaurant";
	public static final String MEAL = "Selected Meal";
	MySQLiteHelper sqliteHelper;
	Context context = this;
	String restName;
	Carte carte;
	ListView carteListView;
	Restaurant currentRestaurant;
	Menu currentMenu;
	Button preBooking;
	Button booking;
	ArrayAdapter<String> adapter;
	Client client;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_carte);
		sqliteHelper = new MySQLiteHelper(this);
		Intent i = getIntent();
		//restName = i.getStringExtra(Profil_Restaurant.name); //TODO
		client = new Client(sqliteHelper, i.getStringExtra(Login.email));
		carte = new Carte(sqliteHelper, "Loungeatude", client);
		carteListView = (ListView) findViewById(R.id.carte_list);
		
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, carte.getFilterList());
		carteListView.setAdapter(adapter);
		
		
		preBooking = (Button) findViewById(R.id.carte_prebooking_button);
		booking = (Button) findViewById(R.id.carte_booking_button);
		
		carteListView.setOnItemSelectedListener(selectMealListener);
		preBooking.setOnClickListener(preBookingListener);
		booking.setOnClickListener(bookingListener);
	}
	
	private AdapterView.OnItemSelectedListener selectMealListener = new AdapterView.OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			if (currentMenu==null) {
				Toast toast = Toast.makeText(context, getString(R.string.list_carte_toast), Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			Intent intent = new Intent(CarteActivity.this,MealActivity.class);
			intent.putExtra(RESTAURANT, currentRestaurant.getRestaurantName());
			startActivity(intent);
			return;
		}
		
		public void onNothingSelected(AdapterView<?> arg0) {
			
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
			return;
		}
	};
	
	
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}
	
	
}
