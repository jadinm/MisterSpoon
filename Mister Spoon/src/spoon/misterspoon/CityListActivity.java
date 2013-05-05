package spoon.misterspoon;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class CityListActivity extends Activity {

	public static final String CITY = "Selected City";

	Context context = this;
	MySQLiteHelper sql; 
	
	Client client;

	City currentCity;

	ListView cityListView;
	CityList cityList;
	List<String> nomCities;
	ArrayAdapter<String> adapter;

	Spinner ordre;
	List<String> orderList;
	ArrayAdapter<String> adapterOrdre;

	Button selectRestaurant;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_list_city);
		
		Intent i = getIntent();
		String sclient = i.getStringExtra(Login.email);
		
		
		sql = new MySQLiteHelper(this);
		client = new Client (sql, sclient);
		
		// ListView
		cityList = new CityList(sql, client, true);
		//cityList.sort("abc");
		
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
		
		// Button
		selectRestaurant = (Button) findViewById(R.id.list_city_restaurant_selection);
		
		cityListView.setOnItemClickListener(cityListListener);
		
		ordre.setOnItemSelectedListener(ordreListener);
		
		selectRestaurant.setOnClickListener(selectRestaurantListener);
	}
	
	private OnItemClickListener cityListListener = new OnItemClickListener(){
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			currentCity = (City) cityListView.getSelectedItem();
		}
		
	};
	
	private OnItemSelectedListener ordreListener = new OnItemSelectedListener(){

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			//cityList.sort((String) ordre.getSelectedItem());
			nomCities = cityList.getNomCities();
			adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomCities);
			cityListView.setAdapter(adapter);
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			Log.v("fuck", "you");

		}
		
	};
	
	private OnClickListener selectRestaurantListener = new OnClickListener() {
		public void onClick(View v) {
			if (currentCity==null) {
				Toast toast = Toast.makeText(context, getString(R.string.list_city_toast), Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			Intent intent = new Intent(CityListActivity.this,RestaurantListActivity.class);
			intent.putExtra(CITY, currentCity.getCityName());
			startActivity(intent);
			return;
		}
	};
}
