package spoon.misterspoon;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.ExpandableListView.OnChildClickListener;

public class CarteBuilderActivity extends Activity {

	private MySQLiteHelper sqliteHelper;
	private Context context = this;
	
	public static String EMAIL = "Restaurant Owner";
	public static String MEAL = "Selected meal";
	
	private ExpandableListView carteListView;
	private CarteActivityListAdapter adapter;
	
	private Spinner categorie;
	private ArrayList <String> categorieList;
	private ArrayAdapter <String> adapterCategorie;
	
	private EditText menuName;
	private EditText menuPrice;
	private EditText mealName;
	private EditText mealPrice;
	private EditText menuName_addMealName;
	private Button update;
	
	
	
	private String emailPerso;
	private CarteBuilder carteBuilder;
	private Meal currentMeal;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		
		setContentView(R.layout.activity_carte_builder);
		sqliteHelper = new MySQLiteHelper(this);
		
		Intent i = getIntent();
		emailPerso = i.getStringExtra(Profil_Restaurant.name);
		RestaurantOwner r = new RestaurantOwner (sqliteHelper, emailPerso);
		carteBuilder = new CarteBuilder(sqliteHelper, r);
		
		menuName = (EditText) findViewById(R.id.carte_builder_menu_name);
		categorie = (Spinner) findViewById(R.id.carte_builder_categorie);
		menuPrice = (EditText) findViewById(R.id.carte_builder_price);
		mealName = (EditText) findViewById(R.id.carte_builder_meal_name);
		mealPrice = (EditText) findViewById(R.id.carte_builder_meal_price);
		menuName_addMealName = (EditText) findViewById(R.id.carte_builder_add_meal_menu_name);
		update = (Button) findViewById(R.id.carte_builder_update);
		
		//Spinner
		categorieList = (ArrayList<String>) Arrays.asList(Menu.categorie);
		adapterCategorie = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categorieList);
		adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categorie.setAdapter(adapterCategorie);
		
		//ExpandableListView
		carteListView = (ExpandableListView) findViewById(R.id.carte_builder_list);
		adapter = new CarteActivityListAdapter(this, carteBuilder.getCarte().getFilterList());
		carteListView.setAdapter(adapter);
		carteListView.setOnChildClickListener(carteListViewChild);
		
		
		update.setOnClickListener(updateListener);
		
		
	}
	
	private OnChildClickListener carteListViewChild =  new OnChildClickListener() {

		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
			
			Meal meal = carteBuilder.getCarte().getMenuList().get(groupPosition).getMealList(false).get(childPosition);
			
			currentMeal = meal;
			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			
			builder.setTitle(meal.getMealName());
			builder.setMessage(getString(R.string.carte_builder_alert_message));
			
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
					Intent i = new Intent(CarteBuilderActivity.this, Meal_BuilderActivity.class);
					i.putExtra(MEAL, currentMeal.getMealName());
					i.putExtra(EMAIL, emailPerso);
					startActivity(i);
					return;
				}
			});
			
			AlertDialog alertDialog = builder.create();
			
			alertDialog.show();

			return true;
		}

	};
	
	public OnClickListener updateListener = new OnClickListener() {//TODO -> interaction with the database (and so mealBuilder) + check the elements in the list
		@Override
		public void onClick(View v) {//We can know which element is checked by an instance variable in adapter (I will do this tomorrow)
			
			/* Add a new menu */
			if (menuName.getText().toString().length() > 0) {
				//TODO
				
				menuName.setText("");
				menuPrice.setText("");
			}
			else if (menuPrice.getText().toString().length() > 0){//empty field
				menuPrice.setText("");
			}
			
			/* Add a new meal */
			if (mealName.getText().toString().length() > 0 && mealPrice.getText().toString().length() > 0 && menuName_addMealName.getText().toString().length() > 0) {
				//TODO
				
				mealName.setText("");
				mealPrice.setText("");
				menuName_addMealName.setText("");
			}
			if (mealName.getText().toString().length() == 0) {//empty field
				mealName.setText("");
			}
			else if (mealName.getText().toString().length() > 0 && mealPrice.getText().toString().length() == 0) {//empty field
				mealPrice.setText("");
			}
			else if (mealName.getText().toString().length() > 0 && mealPrice.getText().toString().length() > 0 && menuName_addMealName.getText().toString().length() == 0) {//empty field
				menuName_addMealName.setText("");
			}
			
			/* Remove meal */
			//TODO -> change the adapter (chekbox checked at the begining) :-(
			
			
			/* Remove menu */
			//TODO -> change the adapter (chekbox needed) :-(
		}
	};
	
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}
}
