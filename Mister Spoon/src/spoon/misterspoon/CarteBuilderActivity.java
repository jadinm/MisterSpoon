package spoon.misterspoon;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
		adapter = new CarteBuilderActivityListAdapter(this, carteBuilder.getFilterList());
		carteListView.setAdapter(adapter);
		carteListView.setOnChildClickListener(carteListViewChild);
		
		for (int j=0; j<adapter.getGroupCount(); j++) {
			carteListView.expandGroup(j);
		}
		
		carteListView.setOnGroupClickListener(new OnGroupClickListener()
		{
			@Override
			public boolean onGroupClick(ExpandableListView parent, 
					View v, int groupPosition, long id)
			{
				return true;
			}
		});

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

	public OnClickListener setMenuListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			AlertDialog.Builder alert = new AlertDialog.Builder(context);

			if (setMenuLayout==null) {

				setMenuLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.carte_builder_alert_box_set_menu, null);

				menuNameOld = (EditText) setMenuLayout.findViewById(R.id.carte_builder_menu_name_old);
				categorieOld = (Spinner) setMenuLayout.findViewById(R.id.carte_builder_categorie_old);
				menuNameNew = (EditText) setMenuLayout.findViewById(R.id.carte_builder_menu_name_new);
				categorieNew = (Spinner) setMenuLayout.findViewById(R.id.carte_builder_categorie_new);
				menuPriceNew = (EditText) setMenuLayout.findViewById(R.id.carte_builder_price_new);
				alert.setView(setMenuLayout);

				categorieListOld = new ArrayList <String> ();
				for (int i=0; i<Menu.categorie.length; i++) {
					categorieListOld.add(Menu.categorie[i]);
				}
				adapterCategorieOld = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,categorieListOld);
				adapterCategorieOld.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				categorieOld.setAdapter(adapterCategorieOld);

				categorieListNew = new ArrayList <String> ();
				for (int i=0; i<Menu.categorie.length; i++) {
					categorieListNew.add(Menu.categorie[i]);
				}
				adapterCategorieNew = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,categorieListNew);
				adapterCategorieNew.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				categorieNew.setAdapter(adapterCategorieNew);

			}

			if (setMenuLayout.getParent() == null) {

				alert.setView(setMenuLayout);
			} 
			else {
				setMenuLayout = null; //set it to null

				setMenuLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.carte_builder_alert_box_set_menu, null);

				menuNameOld = (EditText) setMenuLayout.findViewById(R.id.carte_builder_menu_name_old);
				categorieOld = (Spinner) setMenuLayout.findViewById(R.id.carte_builder_categorie_old);
				menuNameNew = (EditText) setMenuLayout.findViewById(R.id.carte_builder_menu_name_new);
				categorieNew = (Spinner) setMenuLayout.findViewById(R.id.carte_builder_categorie_new);
				menuPriceNew = (EditText) setMenuLayout.findViewById(R.id.carte_builder_price_new);
				alert.setView(setMenuLayout);

				categorieListOld = new ArrayList <String> ();
				for (int i=0; i<Menu.categorie.length; i++) {
					categorieListOld.add(Menu.categorie[i]);
				}
				adapterCategorieOld = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,categorieListOld);
				adapterCategorieOld.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				categorieOld.setAdapter(adapterCategorieOld);

				categorieListNew = new ArrayList <String> ();
				for (int i=0; i<Menu.categorie.length; i++) {
					categorieListNew.add(Menu.categorie[i]);
				}
				adapterCategorieNew = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,categorieListNew);
				adapterCategorieNew.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				categorieNew.setAdapter(adapterCategorieNew);

				alert.setView(setMenuLayout);
			}



			alert.setTitle(getString(R.string.carte_builder_set_menu_title))
			.setNegativeButton(getString(R.string.exit_cancel), null)
			.setPositiveButton(getString(R.string.exit_confirm), new DialogInterface.OnClickListener() {

				@Override
				public void onClick (DialogInterface arg0, int arg1) {

					boolean success = true;

					if (menuNameOld.getText().toString().length() > 0 && menuNameNew.getText().toString().length() > 0) {//We try to change the menu

						if (! menuNameOld.getText().toString().equals(menuNameNew.getText().toString())) {//We try to set the name

							success = carteBuilder.setMenuName(new Menu (sqliteHelper, menuNameOld.getText().toString(), r.getRestaurant().getRestaurantName(), (String) categorieOld.getSelectedItem()), menuNameNew.getText().toString());

							if (!success) {
								Toast.makeText(context, R.string.carte_builder_toast_set_menu, Toast.LENGTH_SHORT).show();
								menuNameOld.setText("");
								menuNameNew.setText("");
								menuPriceNew.setText("");

								return;
							}

						}
						if (! ((String) categorieOld.getSelectedItem()).equals(((String) categorieNew.getSelectedItem())) && success) {//We try to change the categorie


							success = carteBuilder.setMenuCategorie(new Menu (sqliteHelper, menuNameOld.getText().toString(), r.getRestaurant().getRestaurantName(), (String) categorieOld.getSelectedItem()), (String) categorieNew.getSelectedItem());

							if (!success) {
								Toast.makeText(context, R.string.carte_builder_toast_set_menu, Toast.LENGTH_SHORT).show();
								menuNameOld.setText("");
								menuNameNew.setText("");
								menuPriceNew.setText("");

								adapter = new CarteBuilderActivityListAdapter(context, carteBuilder.getFilterList());
								carteListView.setAdapter(adapter);
								
								for (int j=0; j<adapter.getGroupCount(); j++) {
									carteListView.expandGroup(j);
								}

								return;
							}
						}
						if (menuPriceNew.getText().toString().length() > 0 && success) {


							carteBuilder.setMenuPrice(new Menu (sqliteHelper, menuNameOld.getText().toString(), r.getRestaurant().getRestaurantName(), (String) categorieOld.getSelectedItem()), Double.parseDouble(menuPriceNew.getText().toString()));
						}

						Toast.makeText(context, R.string.carte_builder_change, Toast.LENGTH_SHORT).show();
					}
					else {
						return;
					}

					adapter = new CarteBuilderActivityListAdapter(context, carteBuilder.getFilterList());
					carteListView.setAdapter(adapter);
					for (int j=0; j<adapter.getGroupCount(); j++) {
						carteListView.expandGroup(j);
					}

					menuNameOld.setText("");
					menuNameNew.setText("");
					menuPriceNew.setText("");
					arg0.cancel();

				}
			}).create().show();
		}
	};

	public OnClickListener createMenuListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			AlertDialog.Builder alert = new AlertDialog.Builder(context);

			if (createMenuLayout == null) {

				createMenuLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.carte_builder_alert_box_create_menu, null);

				menuName = (EditText) createMenuLayout.findViewById(R.id.carte_builder_menu_name);
				categorie = (Spinner) createMenuLayout.findViewById(R.id.carte_builder_categorie);
				menuPrice = (EditText) createMenuLayout.findViewById(R.id.carte_builder_price);
				mealName_addMenuName = (EditText) createMenuLayout.findViewById(R.id.carte_builder_add_menu_meal_name);
				mealPrice_addMenuName = (EditText) createMenuLayout.findViewById(R.id.carte_builder_add_menu_meal_price);
				categorieList = new ArrayList <String> ();
				for (int i=0; i<Menu.categorie.length; i++) {
					categorieList.add(Menu.categorie[i]);
				}
				adapterCategorie = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,categorieList);
				adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				categorie.setAdapter(adapterCategorie);

			}

			if (createMenuLayout.getParent()==null) {
				alert.setView(createMenuLayout);
			}
			else {
				createMenuLayout = null;

				createMenuLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.carte_builder_alert_box_create_menu, null);

				menuName = (EditText) createMenuLayout.findViewById(R.id.carte_builder_menu_name);
				categorie = (Spinner) createMenuLayout.findViewById(R.id.carte_builder_categorie);
				menuPrice = (EditText) createMenuLayout.findViewById(R.id.carte_builder_price);
				mealName_addMenuName = (EditText) createMenuLayout.findViewById(R.id.carte_builder_add_menu_meal_name);
				mealPrice_addMenuName = (EditText) createMenuLayout.findViewById(R.id.carte_builder_add_menu_meal_price);
				categorieList = new ArrayList <String> ();
				for (int i=0; i<Menu.categorie.length; i++) {
					categorieList.add(Menu.categorie[i]);
				}
				adapterCategorie = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,categorieList);
				adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				categorie.setAdapter(adapterCategorie);

				alert.setView(createMenuLayout);
			}

			alert.setTitle(getString(R.string.carte_builder_create_menu_title))
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

					adapter = new CarteBuilderActivityListAdapter(context,  carteBuilder.getFilterList());
					carteListView.setAdapter(adapter);
					for (int j=0; j<adapter.getGroupCount(); j++) {
						carteListView.expandGroup(j);
					}

					arg0.cancel();
					return;

				}
			}).create().show();

		}
	};

	public OnClickListener addMealListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			AlertDialog.Builder alert = new AlertDialog.Builder(context);


			if (addMealLayout == null) {

				addMealLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.carte_builder_alert_box_add_meal, null);

				mealName = (EditText) addMealLayout.findViewById(R.id.carte_builder_meal_name);
				mealPrice = (EditText) addMealLayout.findViewById(R.id.carte_builder_meal_price);
				menuName_addMealName = (EditText) addMealLayout.findViewById(R.id.carte_builder_add_meal_menu_name);
				categorie_addMealName = (Spinner) addMealLayout.findViewById(R.id.carte_builder_add_meal_categorie);

				categorieList_addMealName = new ArrayList <String> ();
				for (int i=0; i<Menu.categorie.length; i++) {
					categorieList_addMealName.add(Menu.categorie[i]);
				}
				adapterCategorie_addMealName = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,categorieList_addMealName);
				adapterCategorie_addMealName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				categorie_addMealName.setAdapter(adapterCategorie_addMealName);

				alert.setView(addMealLayout);

			}
			if (addMealLayout.getParent() == null) {

				alert.setView(addMealLayout);
			}
			else {

				addMealLayout = null;

				addMealLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.carte_builder_alert_box_add_meal, null);

				mealName = (EditText) addMealLayout.findViewById(R.id.carte_builder_meal_name);
				mealPrice = (EditText) addMealLayout.findViewById(R.id.carte_builder_meal_price);
				menuName_addMealName = (EditText) addMealLayout.findViewById(R.id.carte_builder_add_meal_menu_name);
				categorie_addMealName = (Spinner) addMealLayout.findViewById(R.id.carte_builder_add_meal_categorie);

				categorieList_addMealName = new ArrayList <String> ();
				for (int i=0; i<Menu.categorie.length; i++) {
					categorieList_addMealName.add(Menu.categorie[i]);
				}
				adapterCategorie_addMealName = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,categorieList_addMealName);
				adapterCategorie_addMealName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				categorie_addMealName.setAdapter(adapterCategorie_addMealName);

				alert.setView(addMealLayout);
			}



			alert.setTitle(getString(R.string.carte_builder_add_meal_title))
			.setNegativeButton(getString(R.string.exit_cancel), null)
			.setPositiveButton(getString(R.string.exit_confirm), new DialogInterface.OnClickListener() {

				@Override
				public void onClick (DialogInterface arg0, int arg1) {

					boolean success = true;
					if (mealName.getText().toString().length() > 0 && mealPrice.getText().toString().length() > 0 && menuName_addMealName.getText().toString().length() > 0) {


						success = carteBuilder.addMenuMeal(new Menu (sqliteHelper, menuName_addMealName.getText().toString(), r.getRestaurant().getRestaurantName(), (String) categorie_addMealName.getSelectedItem()), mealName.getText().toString(), Double.parseDouble(mealPrice.getText().toString()));

						mealName.setText("");
						mealPrice.setText("");
						menuName_addMealName.setText("");

						if (!success) {

							Toast.makeText(context, R.string.carte_builder_toast_add_meal, Toast.LENGTH_SHORT).show();
							return;
						}
					}

					adapter = new CarteBuilderActivityListAdapter(context,  carteBuilder.getFilterList());
					carteListView.setAdapter(adapter);
					for (int j=0; j<adapter.getGroupCount(); j++) {
						carteListView.expandGroup(j);
					}

					arg0.cancel();
					return;

				}
			}).create().show();

		}
	};

	public OnClickListener updateListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			/* Remove meal and remove meal */
			for (int j=0; j<adapter.getGroupCount(); j++) {
				Menu menu = carteBuilder.getCarte().getMenuList().get(j);
				if (((CarteBuilderActivityHeader)adapter.getGroup(j)).isSelected()) {//If we have to remove it, we do it in the database only (we recreate after that the list)
					carteBuilder.removeMenu(menu);
				}
				else {

					for(int k=0; k<menu.getMealList(false).size(); k++) {
						if (((CarteActivityChild)adapter.getChild(j, k)).isSelected()) {//If we have to remove it, we do it in the database only (we recreate after that the list)
							carteBuilder.removeMenuMeal(menu, menu.getMealList(false).get(k).getMealName());
						}
					}
				}
			}

			carteBuilder = new CarteBuilder (sqliteHelper, r);//We recreate the list

			adapter = new CarteBuilderActivityListAdapter(context, carteBuilder.getFilterList());
			carteListView.setAdapter(adapter);
			for (int j=0; j<adapter.getGroupCount(); j++) {
				carteListView.expandGroup(j);
			}
		}
	};

	public void onRestart() {//When we get back from the activity mealBuilder -> we get back the informations

		super.onRestart();

		sqliteHelper = new MySQLiteHelper(this);
		carteBuilder = new CarteBuilder(sqliteHelper, r);
		adapter = new CarteBuilderActivityListAdapter(context,  carteBuilder.getFilterList());
		carteListView.setAdapter(adapter);
		for (int j=0; j<adapter.getGroupCount(); j++) {
			carteListView.expandGroup(j);
		}
	}

	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}
	
	public static class ViewHolder {
		protected CheckBox cb;
		protected TextView tx;
		protected TextView price;
	}
	
	public static class ViewGroupHolder {
		protected CheckBox cb;
		protected TextView tx;
		protected ImageView image;
	}

	public class CarteBuilderActivityListAdapter extends BaseExpandableListAdapter {

		@SuppressWarnings("unused")
		private Context context;
		private ArrayList<CarteBuilderActivityHeader> carte;

		public CarteBuilderActivityListAdapter(Context context, ArrayList<CarteBuilderActivityHeader> carte) {
			this.context = context;
			this.carte = carte;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			
			if (carte != null && carte.size() > groupPosition && carte.get(groupPosition)!=null) {
				if (carte.get(groupPosition).getMealList().size() > childPosition) {
					return carte.get(groupPosition).getMealList().get(childPosition);
				}
			}
			return null;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			
			return (long) 1024*groupPosition + childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			
			View view = null;
			if (convertView == null) {
				
				view = getLayoutInflater().inflate(R.layout.activity_carte_child_row, null);
				
				final ViewHolder childHolder = new ViewHolder();
				childHolder.cb = (CheckBox) view.findViewById(R.id.carte_child_check);
				childHolder.tx = (TextView) view.findViewById(R.id.carte_child_text);
				childHolder.price = (TextView) view.findViewById(R.id.carte_child_price);
				
				childHolder.cb
				.setOnCheckedChangeListener(new OnCheckedChangeListener () {
					
					@Override
					public void onCheckedChanged(CompoundButton button, boolean isChecked) {
						
						CarteActivityChild item = (CarteActivityChild) childHolder.cb.getTag();
						item.setSelected(button.isChecked());
					}
				});
				
				view.setTag(childHolder);
				childHolder.cb.setTag(carte.get(groupPosition).getMealList().get(childPosition));
				
				
			}
			else {
				
				view = convertView;
				((ViewHolder) view.getTag()).cb.setTag(carte.get(groupPosition).getMealList().get(childPosition));
				
			}
			
			ViewHolder holder = (ViewHolder) view.getTag();
			holder.cb.setSelected(carte.get(groupPosition).getMealList().get(childPosition).isSelected());
			
			holder.tx.setText(carte.get(groupPosition).getMealList().get(childPosition).getMealName());
			holder.price.setText(carte.get(groupPosition).getMealList().get(childPosition).getMealPrice() + " EUR");
			
			return view;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			
			if (carte != null && carte.size() > groupPosition && carte.get(groupPosition)!=null) {
				ArrayList<CarteActivityChild> mealList = carte.get(groupPosition).getMealList();
				return mealList.size();
			}
			
			return 0;

		}

		@Override
		public Object getGroup(int groupPosition) {
			
			if (carte!=null && carte.size() > groupPosition) {
				return carte.get(groupPosition);
			}
			return null;
		}

		@Override
		public int getGroupCount() {
			
			if (carte!=null) {
				return carte.size();
			}
			return 0;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return (long) groupPosition*1024;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			
			View view = null;
			
			if (convertView==null) {
				
				view = getLayoutInflater().inflate(R.layout.activity_carte_builder_group_heading, null);
				
				final ViewGroupHolder groupHolder = new ViewGroupHolder();
				groupHolder.cb = (CheckBox) view.findViewById(R.id.carte_heading_check);
				groupHolder.tx = (TextView) view.findViewById(R.id.carte_heading);
				groupHolder.image = (ImageView) view.findViewById(R.id.carte_divider);
				
				groupHolder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener () {
					
					@Override
					public void onCheckedChanged(CompoundButton button, boolean isChecked) {
						
						CarteBuilderActivityHeader item = (CarteBuilderActivityHeader) groupHolder.cb.getTag();
						item.setSelected(button.isChecked());
					}
				});
				
				view.setTag(groupHolder);
				
				groupHolder.cb.setTag(carte.get(groupPosition));
			}
			else {
				
				view = convertView;
				((ViewGroupHolder) view.getTag()).cb.setTag(carte.get(groupPosition));
			}
			
			ViewGroupHolder holder = (ViewGroupHolder) view.getTag();
			holder.cb.setSelected(carte.get(groupPosition).isSelected());
			holder.tx.setText(carte.get(groupPosition).getMenuName());
			
			if (groupPosition==0) {
				holder.image.setVisibility(View.GONE);
			}
			
			return view;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}
}
