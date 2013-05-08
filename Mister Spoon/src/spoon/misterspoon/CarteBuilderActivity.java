package spoon.misterspoon;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;

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
		
		
		carteListView = (ExpandableListView) findViewById(R.id.carte_builder_list);
		
		
	}
	
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}
}
