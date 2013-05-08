package spoon.misterspoon;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

public class CarteBuilderActivity extends Activity {

	private MySQLiteHelper sqliteHelper;
	private Context context = this;
	
	private ExpandableListView carteListView;
	private CarteActivityListAdapter adapter;
	
	private EditText menuName;
	private EditText menuPrice;
	private EditText mealName;
	private EditText mealPrice;
	private EditText menuName_addMealName;
	private Button update;
	
	
	
	private String emailPerso;
	private CarteBuilder carteBuilder;
	private Menu newMenu;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_carte_builder);
		sqliteHelper = new MySQLiteHelper(this);
		//Intent i = getIntent();
		//emailPerso = i.getStringExtra(); //TODO Recevoir emailPerso
		emailPerso="ludovic.fastre@student.uclouvain.be";
		RestaurantOwner r = new RestaurantOwner (sqliteHelper, emailPerso);
		carteBuilder = new CarteBuilder(sqliteHelper, r);
		
		menuName = (EditText) findViewById(R.id.carte_builder_menu_name);
		menuPrice = (EditText) findViewById(R.id.carte_builder_price);
		mealName = (EditText) findViewById(R.id.carte_builder_meal_name);
		mealPrice = (EditText) findViewById(R.id.carte_builder_meal_price);
		menuName_addMealName = (EditText) findViewById(R.id.carte_builder_add_meal_menu_name);
		update = (Button) findViewById(R.id.carte_builder_update);
		
		
		carteListView = (ExpandableListView) findViewById(R.id.carte_builder_list);
		adapter = new CarteActivityListAdapter(this, carteBuilder.getCarte().getFilterList());
		carteListView.setAdapter(adapter);//No listeners needed
		
		
		update.setOnClickListener(updateListener);
		
		
	}
	
	public OnClickListener updateListener = new OnClickListener() {
		@Override
		public void onClick(View v) {//We can know which element is checked by an instance variable in adapter (I will do this tomorrow)
			
		}
	};
	
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}
}
