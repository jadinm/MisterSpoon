package spoon.misterspoon;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class CarteBuilderActivity extends Activity {

	private MySQLiteHelper sqliteHelper;
	private Context context = this;

	//Intent
	public static String EMAIL = "Restaurant Owner";
	public static String MEAL = "Selected meal";

	//General
	private String emailPerso;
	private RestaurantOwner r;
	private CarteBuilder carteBuilder;
	private Meal currentMeal;

	//ExpandableListView
	private ExpandableListView carteListView;
	private CarteBuilderActivityListAdapter adapter;

	//Button
	private Button setMenu;
	private Button createMenu;
	private Button addMeal;
	private Button update;

	//AlertBox to set a menu
	private LinearLayout setMenuLayout;
	private EditText menuNameOld;
	private Spinner categorieOld;
	private ArrayList <String> categorieListOld;
	private ArrayAdapter <String> adapterCategorieOld;
	private EditText menuNameNew;
	private Spinner categorieNew;
	private ArrayList <String> categorieListNew;
	private ArrayAdapter <String> adapterCategorieNew;
	private EditText menuPriceNew;

	//AlertBox to create a menu
	private LinearLayout createMenuLayout;
	private EditText menuName;
	private Spinner categorie;
	private ArrayList <String> categorieList;
	private ArrayAdapter <String> adapterCategorie;
	private EditText menuPrice;
	private EditText mealName_addMenuName;
	private EditText mealPrice_addMenuName;

	//AlertBox to add a meal
	private LinearLayout addMealLayout;
	private EditText mealName;
	private EditText mealPrice;
	private EditText menuName_addMealName;
	private Spinner categorie_addMealName;
	private ArrayList <String> categorieList_addMealName;
	private ArrayAdapter <String> adapterCategorie_addMealName;



	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);

		setContentView(R.layout.activity_carte_builder);
		sqliteHelper = new MySQLiteHelper(this);

		Intent i = getIntent();
		emailPerso = i.getStringExtra(Profil_Restaurant.MAIL);
		r = new RestaurantOwner (sqliteHelper, emailPerso);
		carteBuilder = new CarteBuilder(sqliteHelper, r);
		
		setMenu = (Button) findViewById(R.id.carte_builder_set_menu);
		createMenu = (Button) findViewById(R.id.carte_builder_create_menu);
		addMeal = (Button) findViewById(R.id.carte_builder_add_meal);
		update = (Button) findViewById(R.id.carte_builder_update);

		//ExpandableListView
		carteListView = (ExpandableListView) findViewById(R.id.carte_builder_list);
		adapter = new CarteBuilderActivityListAdapter(this, carteBuilder.getCarte().getFilterList());
		carteListView.setAdapter(adapter);
		carteListView.setOnChildClickListener(carteListViewChild);

		//Listeners
		setMenu.setOnClickListener(setMenuListener);
		createMenu.setOnClickListener(createMenuListener);
		addMeal.setOnClickListener(addMealListener);
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

	public OnClickListener setMenuListener = new OnClickListener() {//TODO
		@Override
		public void onClick(View v) {

			if (setMenuLayout == null) {

				setMenuLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.carte_builder_alert_box_set_menu, null);

			}

			menuNameOld = (EditText) setMenuLayout.findViewById(R.id.carte_builder_menu_name_old);
			categorieOld = (Spinner) setMenuLayout.findViewById(R.id.carte_builder_categorie_old);
			menuNameNew = (EditText) setMenuLayout.findViewById(R.id.carte_builder_menu_name_new);
			categorieNew = (Spinner) setMenuLayout.findViewById(R.id.carte_builder_categorie_new);
			menuPriceNew = (EditText) setMenuLayout.findViewById(R.id.carte_builder_price_new);

			categorieListOld = (ArrayList<String>) Arrays.asList(Menu.categorie);
			adapterCategorieOld = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,categorieListOld);
			adapterCategorieOld.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			categorieOld.setAdapter(adapterCategorieOld);

			categorieListNew = (ArrayList<String>) Arrays.asList(Menu.categorie);
			adapterCategorieNew = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,categorieListNew);
			adapterCategorieNew.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			categorieNew.setAdapter(adapterCategorieNew);



			new AlertDialog.Builder(context)
			.setTitle(getString(R.string.carte_builder_set_menu_title))
			.setView(setMenuLayout)
			.setNegativeButton(getString(R.string.exit_cancel), null)
			.setPositiveButton(getString(R.string.exit_confirm), new DialogInterface.OnClickListener() {

				@Override
				public void onClick (DialogInterface arg0, int arg1) {//TODO

					boolean success = true;

					if (menuNameOld.getText().toString().length() > 0 && menuNameNew.getText().toString().length() > 0) {//We try to change the menu

						if (! menuNameOld.getText().toString().equals(menuNameNew.getText().toString())) {//We try to set the name

							//success = carteBuilder.setMenuName(new Menu (sqliteHelper, menuNameOld.getText().toString(), r.getRestaurant().getRestaurantName(), (String) categorieOld.getSelectedItem()), menuNameNew.getText().toString());

							if (!success) {
								Toast.makeText(context, R.string.carte_builder_toast_set_menu, Toast.LENGTH_SHORT).show();
								menuNameOld.setText("");
								menuNameNew.setText("");
								menuPriceNew.setText("");
								
								return;
							}

						}
						if (! ((String) categorieOld.getSelectedItem()).equals(((String) categorieNew.getSelectedItem())) && success) {//We try to change the categorie
							
							//success = carteBuilder.setMenuCategorie(new Menu (sqliteHelper, menuNameOld.getText().toString(), r.getRestaurant().getRestaurantName(), (String) categorieOld.getSelectedItem()), (String) categorieNew.getSelectedItem());
							
							if (!success) {
								Toast.makeText(context, R.string.carte_builder_toast_set_menu, Toast.LENGTH_SHORT).show();
								menuNameOld.setText("");
								menuNameNew.setText("");
								menuPriceNew.setText("");
								
								adapter = new CarteBuilderActivityListAdapter(context, carteBuilder.getCarte().getFilterList());
								carteListView.setAdapter(adapter);
								
								return;
							}
						}
						if (menuPriceNew.getText().toString().length() > 0 && success) {
							
							//carteBuilder.setMenuPrice(new Menu (sqliteHelper, menuNameOld.getText().toString(), r.getRestaurant().getRestaurantName(), (String) categorieOld.getSelectedItem()), Double.parseDouble(menuPriceNew.getText().toString()));
						}
						
						Toast.makeText(context, R.string.carte_builder_change, Toast.LENGTH_SHORT).show();
					}
					else {
						return;
					}
					
					adapter = new CarteBuilderActivityListAdapter(context, carteBuilder.getCarte().getFilterList());
					carteListView.setAdapter(adapter);
					
					menuNameOld.setText("");
					menuNameNew.setText("");
					menuPriceNew.setText("");
					arg0.cancel();

				}
			}).create().show();
		}
	};

	public OnClickListener createMenuListener = new OnClickListener() {//TODO
		@Override
		public void onClick(View v) {

			if (createMenuLayout == null) {

				createMenuLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.carte_builder_alert_box_create_menu, null);

			}
			
			menuName = (EditText) findViewById(R.id.carte_builder_menu_name);
			categorie = (Spinner) findViewById(R.id.carte_builder_categorie);
			menuPrice = (EditText) findViewById(R.id.carte_builder_price);
			mealName_addMenuName = (EditText) findViewById(R.id.carte_builder_add_menu_meal_name);
			mealPrice_addMenuName = (EditText) findViewById(R.id.carte_builder_add_menu_meal_price);
			
			categorieList = (ArrayList<String>) Arrays.asList(Menu.categorie);
			adapterCategorie = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,categorieList);
			adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			categorie.setAdapter(adapterCategorie);
			
			new AlertDialog.Builder(context)
			.setTitle(getString(R.string.carte_builder_create_menu_title))
			.setView(setMenuLayout)
			.setNegativeButton(getString(R.string.exit_cancel), null)
			.setPositiveButton(getString(R.string.exit_confirm), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick (DialogInterface arg0, int arg1) {

					boolean success;
					if (menuName.getText().toString().length() > 0 && mealName_addMenuName.getText().toString().length() > 0 && mealPrice_addMenuName.getText().toString().length() > 0) {
					
						if (menuPrice.getText().toString().length() > 0) {
							success = carteBuilder.createMenu(menuName.getText().toString(), Double.parseDouble(menuPrice.getText().toString()), (String) categorie.getSelectedItem(), mealName_addMenuName.getText().toString(), Double.parseDouble(mealPrice_addMenuName.getText().toString()));
						}
						else {
							success = carteBuilder.createMenu(menuName.getText().toString(), 0.0, (String) categorie.getSelectedItem(), mealName_addMenuName.getText().toString(), Double.parseDouble(mealPrice_addMenuName.getText().toString()));
						}

						if (!success) {
							Toast.makeText(context, R.string.carte_builder_toast_create_menu, Toast.LENGTH_SHORT).show();
							menuName.setText("");
							menuPrice.setText("");
							mealName_addMenuName.setText("");
							mealPrice_addMenuName.setText("");
							return;
						}
						
					}
					else {
						menuName.setText("");
						menuPrice.setText("");
						mealName_addMenuName.setText("");
						mealPrice_addMenuName.setText("");
						return;
					}
					
					menuName.setText("");
					menuPrice.setText("");
					mealName_addMenuName.setText("");
					mealPrice_addMenuName.setText("");
					
					adapter = new CarteBuilderActivityListAdapter(context, carteBuilder.getCarte().getFilterList());
					carteListView.setAdapter(adapter);
					
					arg0.cancel();
					return;
					
				}
			}).create().show();

		}
	};

	public OnClickListener addMealListener = new OnClickListener() {//TODO
		@Override
		public void onClick(View v) {

			
			if (addMealLayout == null) {

				addMealLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.carte_builder_alert_box_add_meal, null);

			}
			
			mealName = (EditText) findViewById(R.id.carte_builder_meal_name);
			mealPrice = (EditText) findViewById(R.id.carte_builder_meal_price);
			menuName_addMealName = (EditText) findViewById(R.id.carte_builder_add_meal_menu_name);
			categorie_addMealName = (Spinner) findViewById(R.id.carte_builder_add_meal_categorie);
			
			categorieList_addMealName = (ArrayList<String>) Arrays.asList(Menu.categorie);
			adapterCategorie_addMealName = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,categorieList_addMealName);
			adapterCategorie_addMealName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			categorie_addMealName.setAdapter(adapterCategorie_addMealName);
			
			new AlertDialog.Builder(context)
			.setTitle(getString(R.string.carte_builder_add_meal_title))
			.setView(setMenuLayout)
			.setNegativeButton(getString(R.string.exit_cancel), null)
			.setPositiveButton(getString(R.string.exit_confirm), new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick (DialogInterface arg0, int arg1) {

					boolean success = true;
					if (mealName.getText().toString().length() > 0 && mealPrice.getText().toString().length() > 0 && menuName_addMealName.getText().toString().length() > 0) {
						//TODO

						//success = carteBuilder.addMenuMeal(new Menu (sqliteHelper, menuName_addMealName.getText().toString(), r.getRestaurant().getRestaurantName(), (String) categorie_addMealName.getSelectedItem()), mealName.getText().toString());

						mealName.setText("");
						mealPrice.setText("");
						menuName_addMealName.setText("");
						
						if (!success) {
							
							Toast.makeText(context, R.string.carte_builder_toast_add_meal, Toast.LENGTH_SHORT).show();
							return;
						}
					}
					
					adapter = new CarteBuilderActivityListAdapter(context, carteBuilder.getCarte().getFilterList());
					carteListView.setAdapter(adapter);
					
					arg0.cancel();
					return;
					
				}
			}).create().show();

		}
	};

	public OnClickListener updateListener = new OnClickListener() {//TODO
		@Override
		public void onClick(View v) {

			/* Remove meal */
			//TODO


			/* Remove menu */
			//TODO




			adapter = new CarteBuilderActivityListAdapter(context, carteBuilder.getCarte().getFilterList());
			carteListView.setAdapter(adapter);
		}
	};

	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}
}
