package spoon.misterspoon;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class CarteBuilderActivity extends Activity {

	private MySQLiteHelper sqliteHelper;
	private Context context = this;
	
	private ExpandableListView carteListView;
	private CarteActivityListAdapter adapter;
	
	private EditText menuName;
	private EditText menuPrice;
	private EditText mealName;
	private EditText mealPrice;
	private EditText addMealName;
	private Button update;
	
	
	private Spinner categorie;
	private List<String> categorieList;
	private ArrayAdapter<String> adapterCategorie;
	
	
	private String restName;
	private Carte carte;
	private Menu newMenu;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_carte_builder);
		sqliteHelper = new MySQLiteHelper(this);
		Intent i = getIntent();
		//restName = i.getStringExtra(); //TODO Recevoir restaurant
		carte = new Carte(sqliteHelper, restName, null);
		
		
		carteListView = (ExpandableListView) findViewById(R.id.carte_list);
		menuName = (EditText) findViewById(R.id.carte_builder_menu_name);
		menuPrice = (EditText) findViewById(R.id.carte_builder_price);
		mealName = (EditText) findViewById(R.id.carte_builder_meal_name);
		mealPrice = (EditText) findViewById(R.id.carte_builder_meal_price);
		addMealName = (EditText) findViewById(R.id.carte_builder_menu_name);
		update = (Button) findViewById(R.id.carte_builder_update);
		
		categorie = (Spinner) findViewById(R.id.carte_builder_categorie);
		
		//Spinner
		categorieList = Arrays.asList(Menu.categorie);
		adapterCategorie = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categorieList);
		adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categorie.setAdapter(adapterCategorie);
		categorie.setSelection(0);
		
		//ListView
		adapter = new CarteActivityListAdapter(this, carte.getFilterList());
		carteListView.setAdapter(adapter);
		
		
		categorie.setOnItemSelectedListener(categorieListener);
		
	}
	private OnItemSelectedListener categorieListener = new OnItemSelectedListener(){

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (categorie.getSelectedItem().equals(Menu.categorie[0])) {
				newMenu.setCategorie(Menu.categorie[0]);
			}
			else if (categorie.getSelectedItem().equals(Menu.categorie[1])) {
				newMenu.setCategorie(Menu.categorie[1]);
			}
			else if (categorie.getSelectedItem().equals(Menu.categorie[2])) {
				newMenu.setCategorie(Menu.categorie[2]);
			}
			else if (categorie.getSelectedItem().equals(Menu.categorie[3])) {
				newMenu.setCategorie(Menu.categorie[3]);
			}			
		
		}
		
		public void onNothingSelected(AdapterView<?> arg0) {
			// Nothing
		}
	};
	
	update.setOnClickListener(new View.OnClickListener() {//Update the informations
		@Override
		public void onClick(View v) {
	
		}
	};
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}
}
