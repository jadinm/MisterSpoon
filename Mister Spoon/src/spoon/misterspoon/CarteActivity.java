package spoon.misterspoon;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


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
	Button preOrder;
	Button preBooking;
	Button booking;
	Button selectMeal;
	ArrayAdapter<String> adapter;
	List<String> menuName;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.onActivityCreateSetTheme(this);
		
		Intent i = getIntent();
		String sclient = i.getStringExtra(Login.email);
		
		
		restName = i.getStringExtra(Profil_Restaurant.name); //Je vois pas ou le trouver
		carte = new Carte(sqliteHelper, restName);
		
		
		carteListView = (ListView) findViewById(R.id.carte_list);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menuName);
		carteListView.setAdapter(adapter);
		
		
		preOrder = (Button) findViewById(R.id.carte_preorder);
		preBooking = (Button) findViewById(R.id.carte_prebooking_button);
		booking = (Button) findViewById(R.id.carte_booking_button);
		
		selectMeal.setOnClickListener(selectMealListener);
		preBooking.setOnClickListener(preBookingListener);
		booking.setOnClickListener(bookingListener);
		preOrder.setOnClickListener(preOrderListener);
	}
	
	private OnClickListener selectMealListener = new OnClickListener() {
		public void onClick(View v) {
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
	};
	
	private OnClickListener preBookingListener = new OnClickListener() {
		public void onClick(View v) {
			Toast toast = Toast.makeText(context, getString(R.string.carte_prebooking_toast), Toast.LENGTH_SHORT);
			toast.show();
			return;
		}
	};
	
	private OnClickListener preOrderListener = new OnClickListener() {
		public void onClick(View v) {
			Toast toast = Toast.makeText(context, getString(R.string.carte_preorder_toast), Toast.LENGTH_SHORT);
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
	
	
}
