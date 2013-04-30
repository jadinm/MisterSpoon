package spoon.misterspoon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class RestaurantListActivity extends Activity {
	
	Context context = this;
	MySQLiteHelper sql = new MySQLiteHelper(this);
	
	Restaurant currentRestaurant;
	
	ListView restaurantList;
	String currentOrdre;
	
	Spinner ordre;
	
	CheckBox filtreNote;
	EditText textNote;
	CheckBox filtrePrix;
	EditText textPrix;
	CheckBox filtreFavori;
	
	Button selectRestaurant;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_restaurant);
		
		restaurantList = (ListView) findViewById(R.id.list_restaurant_restaurants);
		//TODO Remplir la liste de restaurants
				
		ordre = (Spinner) findViewById(R.id.list_restaurant_ordre);
		
		filtreNote = (CheckBox) findViewById(R.id.list_restaurant_filtre_note);
		textNote = (EditText) findViewById(R.id.list_restaurant_editText_note);
		filtrePrix = (CheckBox) findViewById(R.id.list_restaurant_filtre_prix);
		textPrix = (EditText) findViewById(R.id.list_restaurant_editText_prix);
		filtreFavori = (CheckBox) findViewById(R.id.list_restaurant_filtre_favori);
		
		selectRestaurant = (Button) findViewById(R.id.list_restaurant_selection);
		
		restaurantList.setOnItemClickListener(restaurantListListener);
		
		ordre.setOnItemClickListener(ordreListener);
		
		filtreNote.setOnCheckedChangeListener(filtreNoteListener);
		filtrePrix.setOnCheckedChangeListener(filtrePrixListener);
		filtreFavori.setOnCheckedChangeListener(filtreFavoriListener);
		
		selectRestaurant.setOnClickListener(selectRestaurantListener);
	}
	
	private OnItemClickListener restaurantListListener = new OnItemClickListener(){
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			currentRestaurant = (Restaurant) restaurantList.getSelectedItem();
		}
		
	};
	
	private OnItemClickListener ordreListener = new OnItemClickListener(){
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			currentOrdre = (String) ordre.getSelectedItem();
		}
		
	};
	
	private OnCheckedChangeListener filtreNoteListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (filtreNote.isChecked()) {
				//TODO Appliquer le filtre
			}
			else {
				//TODO Enlever le filtre
			}
		}
	};
	
	private OnCheckedChangeListener filtrePrixListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (filtrePrix.isChecked()) {
				//TODO Appliquer le filtre
			}
			else {
				//TODO Enlever le filtre
			}
		}
	};
	
	private OnCheckedChangeListener filtreFavoriListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (filtreFavori.isChecked()) {
				//TODO Appliquer le filtre
			}
			else {
				//TODO Enlever le filtre
			}
		}
	};
	
	private OnClickListener selectRestaurantListener = new OnClickListener() {
		public void onClick(View v) {
			if (currentRestaurant.toString()==null) {
				//TODO Message d'erreur
				return;
			}
			//TODO Si un resto est checké, aller à la page resto
		}
	};
}
