//package spoon.misterspoon;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.EditText;
//import android.widget.ListView;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//public class RestaurantListActivity extends Activity {
//	
//	public static final String RESTAURANT = "Selected Restaurant";
//	Context context = this;
//	MySQLiteHelper sql = new MySQLiteHelper(this);
//	
//	Restaurant currentRestaurant;
//	
//	ListView restaurantListView;
//	RestaurantList restaurantList;
//	List<String> nomRestaurants;
//	ArrayAdapter<String> adapter;
//	
//	Spinner ordre;
//	List<String> orderList;
//	
//	CheckBox filtreNote;
//	EditText textNote;
//	CheckBox filtrePrix;
//	EditText textPrix;
//	CheckBox filtreFavori;
//	
//	Button selectRestaurant;
//
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_list_restaurant);
//		
//		Intent i = getIntent();
//		String sclient = i.getStringExtra(CityListActivity.email);
//		String city = i.getStringExtra(CityListActivity.city);
//		
//		Client client = new Client (sql, sclient);
//		
//		restaurantList = new RestaurantList (sql, city, client, true);
//		restaurantList.sort("abc");
//		nomRestaurants = restaurantList.getNomRestaurants(); // Renvoie une liste de string correspondant aux noms des restaurants    
//		
//		restaurantListView = (ListView) findViewById(R.id.list_restaurant_restaurants);
//		
//		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, nomRestaurants);
//		restaurantListView.setAdapter(adapter);
//		
//		ordre = (Spinner) findViewById(R.id.list_restaurant_ordre);
//		orderList = Arrays.asList(RestaurantList.orderTable);
//		ArrayAdapter<String> adapterOrdre = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,orderList);
//		adapterOrdre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		ordre.setAdapter(adapterOrdre);
//		
//		filtreNote = (CheckBox) findViewById(R.id.list_restaurant_filtre_note);
//		textNote = (EditText) findViewById(R.id.list_restaurant_editText_note);
//		filtrePrix = (CheckBox) findViewById(R.id.list_restaurant_filtre_prix);
//		textPrix = (EditText) findViewById(R.id.list_restaurant_editText_prix);
//		filtreFavori = (CheckBox) findViewById(R.id.list_restaurant_filtre_favori);
//		
//		selectRestaurant = (Button) findViewById(R.id.list_restaurant_selection);
//		
//		restaurantListView.setOnItemClickListener(restaurantListListener);
//		
//		ordre.setOnItemClickListener(ordreListener);
//		
//		filtreNote.setOnCheckedChangeListener(filtreNoteListener);
//		filtrePrix.setOnCheckedChangeListener(filtrePrixListener);
//		filtreFavori.setOnCheckedChangeListener(filtreFavoriListener);
//		
//		selectRestaurant.setOnClickListener(selectRestaurantListener);
//	}
//	
//	
//	private OnItemClickListener restaurantListListener = new OnItemClickListener(){
//		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//			currentRestaurant = (Restaurant) restaurantListView.getSelectedItem();
//		}
//		
//	};
//	
//	private OnItemClickListener ordreListener = new OnItemClickListener(){
//		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//				long arg3) {
//			restaurantList.sort((String) ordre.getSelectedItem());
//			nomRestaurants = restaurantList.getNomRestaurants();
//			adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomRestaurants);
//			restaurantListView.setAdapter(adapter);
//		}
//		
//	};
//	
//	private OnCheckedChangeListener filtreNoteListener = new OnCheckedChangeListener() {
//		public void onCheckedChanged(CompoundButton buttonView,
//				boolean isChecked) {
//			if (isChecked) {
//				restaurantList.listFilter("note", Integer.parseInt(textNote.getText().toString()));
//				nomRestaurants = restaurantList.getNomRestaurants();
//				adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomRestaurants);
//				restaurantListView.setAdapter(adapter);
//			}
//			else {
//				restaurantList.resetfilterList();
//				if(filtrePrix.isChecked()){
//					restaurantList.listFilter("prix", Integer.parseInt(textPrix.getText().toString()));
//				}
//				if(filtreFavori.isChecked()){
//					restaurantList.listFilter("favori", 0);
//				}
//				nomRestaurants = restaurantList.getNomRestaurants();
//				adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomRestaurants);
//				restaurantListView.setAdapter(adapter);
//			}
//		}
//	};
//	
//	private OnCheckedChangeListener filtrePrixListener = new OnCheckedChangeListener() {
//		public void onCheckedChanged(CompoundButton buttonView,
//				boolean isChecked) {
//			if (isChecked) {
//				restaurantList.listFilter("prix", Integer.parseInt(textPrix.getText().toString()));
//				nomRestaurants = restaurantList.getNomRestaurants();
//				adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomRestaurants);
//				restaurantListView.setAdapter(adapter);
//			}
//			else {
//				restaurantList.resetfilterList();
//				if(filtreNote.isChecked()){
//					restaurantList.listFilter("note", Integer.parseInt(textNote.getText().toString()));
//				}
//				if(filtreFavori.isChecked()){
//					restaurantList.listFilter("favori", 0);
//				}
//				nomRestaurants = restaurantList.getNomRestaurants();
//				adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomRestaurants);
//				restaurantListView.setAdapter(adapter);
//			}
//		}
//	};
//	
//	private OnCheckedChangeListener filtreFavoriListener = new OnCheckedChangeListener() {
//		public void onCheckedChanged(CompoundButton buttonView,
//				boolean isChecked) {
//			if (filtreFavori.isChecked()) {
//				restaurantList.listFilter("favori", 0);
//				nomRestaurants = restaurantList.getNomRestaurants();
//				adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomRestaurants);
//				restaurantListView.setAdapter(adapter);
//			}
//			else {
//				restaurantList.resetfilterList();
//				if(filtreNote.isChecked()){
//					restaurantList.listFilter("note", Integer.parseInt(textNote.getText().toString()));
//				}
//				if(filtrePrix.isChecked()){
//					restaurantList.listFilter("prix", Integer.parseInt(textPrix.getText().toString()));
//				}
//				nomRestaurants = restaurantList.getNomRestaurants();
//				adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, nomRestaurants);
//				restaurantListView.setAdapter(adapter);
//			}
//		}
//	};
//	
//	private OnClickListener selectRestaurantListener = new OnClickListener() {
//		public void onClick(View v) {
//			if (currentRestaurant==null) {
//				Toast toast = Toast.makeText(context, getString(R.string.list_restaurant_toast), Toast.LENGTH_SHORT);
//				toast.show();
//				return;
//			}
//			Intent intent = new Intent(RestaurantListActivity.this,RestaurantActivity.class);
//			intent.putExtra(RESTAURANT, currentRestaurant.getRestaurantName());
//			startActivity(intent);
//		}
//	};
//}
