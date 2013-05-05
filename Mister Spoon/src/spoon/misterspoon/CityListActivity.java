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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CityListActivity extends Activity implements LocationListener {

	public static final String CITY = "Selected City";
	public static final String email = null;
	Context context = this;
	MySQLiteHelper sql; 
	
	Client client;

	City currentCity;
	
	private View pressedView;

	ListView cityListView;
	CityList cityList;
	List<String> nomCities;
	ArrayAdapter<String> adapter;

	Spinner ordre;
	List<String> orderList;
	ArrayAdapter<String> adapterOrdre;

	Button selectRestaurant;
	
	Activity activity = this;
	LocationListener location = this;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_list_city);
		
		Intent i = getIntent();
		String sclient = i.getStringExtra(Login.email);
		
		
		sql = new MySQLiteHelper(this);
		client = new Client (sql, sclient);
		
		// ListView
		cityList = new CityList(sql, client, this, this);
		
		nomCities = cityList.getNomCities();
		cityListView = (ListView) findViewById(R.id.list_city);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, nomCities);
		cityListView.setAdapter(adapter);
		
		// Spinner
		ordre = (Spinner) findViewById(R.id.list_city_ordre);
		orderList = Arrays.asList(CityList.orderTable);
		adapterOrdre = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,orderList);
		adapterOrdre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ordre.setAdapter(adapterOrdre);
		
		if (cityList.getOrdre().equals(CityList.orderTable[0])) {//Show the good one
			ordre.setSelection(0);
		}
		else {
			ordre.setSelection(1);
		}
		
		
		// Button
		selectRestaurant = (Button) findViewById(R.id.list_city_restaurant_selection);
		
		cityListView.setOnItemClickListener(cityListListener);
		
		ordre.setOnItemSelectedListener(ordreListener);
		
		selectRestaurant.setOnClickListener(selectRestaurantListener);
	}
	
	private OnItemClickListener cityListListener = new OnItemClickListener(){
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			currentCity = new City(((TextView)arg1).getText().toString(),null);
			Drawable originalBackground = arg1.getBackground();
			if(pressedView!=null)
				pressedView.setBackgroundDrawable(originalBackground);
			pressedView = arg1; // Point pressedView to new item
			pressedView.setBackgroundColor(Color.parseColor("#80B7DBE8")); // reset background of old item
		}
		
	};
	
	private OnItemSelectedListener ordreListener = new OnItemSelectedListener(){

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			if (((String) ordre.getSelectedItem()).equals(RestaurantList.orderTable[2])) {//If he try to organize that with the gps
				if (client.getPosition(null, null)==null) {//If we didn't check yet
					if (client.getPosition(location, activity)==null) {//GPS not activated
						Toast toast = Toast.makeText(context, getString(R.string.list_restaurant_toast_gps), Toast.LENGTH_SHORT);
						toast.show();
						cityList.sort(RestaurantList.orderTable[0]);//"abc"
						nomCities = cityList.getNomCities();
						adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomCities);
						cityListView.setAdapter(adapter);
						ordre.setSelection(0);
						return;
					}
				}
			}

			cityList.sort((String) ordre.getSelectedItem());
			nomCities = cityList.getNomCities();
			adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomCities);
			cityListView.setAdapter(adapter);
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
		
	};
	
	private OnClickListener selectRestaurantListener = new OnClickListener() {
		public void onClick(View v) {
			if (currentCity==null) {
				Toast toast = Toast.makeText(context, getString(R.string.list_city_toast), Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			
			//Toast toast = Toast.makeText(context, "Il veut acceder a l'activite suivante avec la ville " + currentCity.getCityName(), Toast.LENGTH_SHORT);
			//toast.show();
			Intent i = getIntent();
			String sclient = i.getStringExtra(Login.email);
			Intent intent = new Intent(CityListActivity.this,RestaurantListActivity.class);
			intent.putExtra(CITY, currentCity.getCityName());
			intent.putExtra(email, sclient);
			startActivity(intent);
			return;
		}
	};

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}
}