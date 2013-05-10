package spoon.misterspoon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class CarteActivity extends Activity {

	public static final String RESTAURANT = "Selected Restaurant";
	public static final String MEALLIST = "List Meal";
	public static final String MEAL = "selected meal";
	public static final String CLIENT = "User";

	private MySQLiteHelper sqliteHelper;
	private Context context = this;

	private String restName;
	private Carte carte;
	private Client client;
	private Meal currentMeal;

	private CheckBox favori;
	private CheckBox prix;
	private EditText prix_edit;

	private EditText invisible;

	private Spinner categorie;
	private List<String> categorieList;
	private ArrayAdapter<String> adapterCategorie;

	private Spinner ordre;
	private List<String> orderList;
	private ArrayAdapter<String> adapterOrdre;


	private ExpandableListView carteListView;
	private CarteActivityListAdapter adapter;

	private Button preBooking;
	private Button booking;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		setContentView(R.layout.activity_carte);
		sqliteHelper = new MySQLiteHelper(this);
		Intent i = getIntent();
		restName = i.getStringExtra(RestaurantListActivity.RESTAURANT);
		client = new Client(sqliteHelper, i.getStringExtra(Login.email));
		carte = new Carte(sqliteHelper, restName, client);


		favori = (CheckBox) findViewById(R.id.carte_favori);
		prix = (CheckBox) findViewById(R.id.carte_price_check);
		prix_edit = (EditText) findViewById(R.id.carte_price_edit);
		categorie = (Spinner) findViewById(R.id.carte_categorie);
		ordre = (Spinner) findViewById(R.id.carte_ordre);

		carteListView = (ExpandableListView) findViewById(R.id.carte_expandableList);

		preBooking = (Button) findViewById(R.id.carte_prebooking_button);
		booking = (Button) findViewById(R.id.carte_booking_button);

		invisible = (EditText) findViewById(R.id.edit_invisible_focus_holder);
		invisible.setInputType(InputType.TYPE_NULL);
		invisible.requestFocus();

		//Spinners

		orderList = Arrays.asList(Carte.orderMeal);
		adapterOrdre = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,orderList);
		adapterOrdre.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ordre.setAdapter(adapterOrdre);
		ordre.setSelection(0);

		categorieList = Arrays.asList(Carte.categorie);
		adapterCategorie = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,categorieList);
		adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categorie.setAdapter(adapterCategorie);
		categorie.setSelection(4);

		//ListView
		adapter = new CarteActivityListAdapter(this, carte.getFilterList());

		carteListView.setAdapter(adapter);

		//Listeners
		favori.setOnCheckedChangeListener(favouriteListener);
		prix.setOnCheckedChangeListener(priceListener);
		categorie.setOnItemSelectedListener(categorieListener);
		ordre.setOnItemSelectedListener(ordreListener);

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

		preBooking.setOnClickListener(preBookingListener);
		booking.setOnClickListener(bookingListener);

	}

	private OnChildClickListener carteListViewChild =  new OnChildClickListener() {

		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


			Meal meal = carte.getMenuList().get(groupPosition).getMealList(false).get(childPosition);

			currentMeal = meal;

			AlertDialog.Builder builder = new AlertDialog.Builder(context);

			builder.setTitle(meal.getMealName());
			builder.setMessage(getString(R.string.carte_alert_message));

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
					Intent i = new Intent(CarteActivity.this, MealActivity.class);
					i.putExtra(RESTAURANT, restName);
					i.putExtra(MEAL, currentMeal.getMealName());
					i.putExtra(CLIENT, client.getEmail());
					startActivity(i);
					return;
				}
			});

			AlertDialog alertDialog = builder.create();

			alertDialog.show();

			return true;
		}

	};

	private OnItemSelectedListener categorieListener = new OnItemSelectedListener(){

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			if (categorie.getSelectedItem().equals(Carte.categorie[0])) {
				carte.filter(Carte.categorie[0], 0);
			}
			else if (categorie.getSelectedItem().equals(Carte.categorie[1])) {
				carte.filter(Carte.categorie[1], 0);
			}
			else if (categorie.getSelectedItem().equals(Carte.categorie[2])) {
				carte.filter(Carte.categorie[2], 0);
			}
			else if (categorie.getSelectedItem().equals(Carte.categorie[3])) {
				carte.filter(Carte.categorie[3], 0);
			}
			else if (categorie.getSelectedItem().equals(Carte.categorie[4])) {
				carte.filter(Carte.categorie[4], 0);
			}

			carte.sort();

			adapter = new CarteActivityListAdapter(context, carte.getFilterList());
			carteListView.setAdapter(adapter);
			for (int j=0; j<adapter.getGroupCount(); j++) {
				carteListView.expandGroup(j);
			}

		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// Nothing
		}

	};

	private OnItemSelectedListener ordreListener = new OnItemSelectedListener(){

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			if (ordre.getSelectedItem().equals(Carte.orderMeal[0])) {
				carte.setMealOrder(Carte.orderMeal[0]);
			}
			else if (ordre.getSelectedItem().equals(Carte.orderMeal[1])) {
				carte.setMealOrder(Carte.orderMeal[1]);
			}

			carte.sort();

			adapter = new CarteActivityListAdapter(context, carte.getFilterList());
			carteListView.setAdapter(adapter);
			for (int j=0; j<adapter.getGroupCount(); j++) {
				carteListView.expandGroup(j);
			}

		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// Nothing
		}

	};

	private OnCheckedChangeListener favouriteListener = new OnCheckedChangeListener() {

		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

			if (isChecked) {
				carte.filter(Carte.filterMeal[1], 0);

				adapter = new CarteActivityListAdapter(context, carte.getFilterList());
				carteListView.setAdapter(adapter);
				for (int j=0; j<adapter.getGroupCount(); j++) {
					carteListView.expandGroup(j);
				}
			}
			else {
				carte.resetfilterList();
				if(prix.isChecked() && prix_edit.getText().toString().length() > 0){
					carte.filter(Carte.filterMeal[0], Float.parseFloat(prix_edit.getText().toString()));
				}

				carte.sort();

				adapter = new CarteActivityListAdapter(context, carte.getFilterList());
				carteListView.setAdapter(adapter);
				for (int j=0; j<adapter.getGroupCount(); j++) {
					carteListView.expandGroup(j);
				}
			}
		}
	};

	private OnCheckedChangeListener priceListener = new OnCheckedChangeListener() {

		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

			if (isChecked && prix_edit.getText().toString().length() > 0) {
				carte.filter(Carte.filterMeal[0], Float.parseFloat(prix_edit.getText().toString()));

				adapter = new CarteActivityListAdapter(context, carte.getFilterList());
				carteListView.setAdapter(adapter);
				for (int j=0; j<adapter.getGroupCount(); j++) {
					carteListView.expandGroup(j);
				}
			}
			else if (isChecked && prix_edit.getText().toString().length() == 0) {

				prix.setChecked(false);

				Toast toast = Toast.makeText(context, R.string.carte_toast_no_price, Toast.LENGTH_SHORT);
				toast.show();
			}
			else {

				prix_edit.setText("");

				carte.resetfilterList();
				if(favori.isChecked()){
					carte.filter(Carte.filterMeal[1], 0);
				}

				carte.sort();

				adapter = new CarteActivityListAdapter(context, carte.getFilterList());
				carteListView.setAdapter(adapter);
				for (int j=0; j<adapter.getGroupCount(); j++) {
					carteListView.expandGroup(j);
				}
			}
		}
	};

	private OnClickListener preBookingListener = new OnClickListener() {
		public void onClick(View v) {

			ArrayList <String> mealList = new ArrayList <String> ();

			for (int j=0; j<adapter.getGroupCount(); j++) {
				for(int k=0; k<adapter.getChildrenCount(j); k++) {
					if (((CarteActivityChild) adapter.getChild(j, k)).isSelected()) {//If it's checked
						mealList.add(((CarteActivityChild) adapter.getChild(j, k)).getMealName());
					}
				}
			}
			
			if (mealList.size()==0) {
				Toast.makeText(context, R.string.carte_toast_empty, Toast.LENGTH_SHORT).show();
				return;
			}
			
			
			Intent i = new Intent(CarteActivity.this, PrereservationClientActivity.class);
			i.putExtra(CLIENT, client.getEmail());
			i.putExtra(RESTAURANT, restName);

			i.putExtra(MEALLIST, mealList);
			startActivity(i);
			return;
		}
	};


	private OnClickListener bookingListener = new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent(CarteActivity.this, ReservationClientActivity.class);
			i.putExtra(CLIENT, client.getEmail());
			i.putExtra(RESTAURANT, restName);

			ArrayList <String> mealList = new ArrayList <String> ();

			for (int j=0; j<adapter.getGroupCount(); j++) {
				for(int k=0; k<adapter.getChildrenCount(j); k++) {
					if (((CarteActivityChild) adapter.getChild(j, k)).isSelected()) {//If it's checked
						mealList.add(((CarteActivityChild) adapter.getChild(j, k)).getMealName());
					}
				}
			}

			i.putExtra(MEALLIST, mealList);
			startActivity(i);
		}
	};


	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}

	public static class ViewHolder {
		protected CheckBox cb;
		protected TextView tx;
		protected TextView price;
	}

	public class CarteActivityListAdapter extends BaseExpandableListAdapter {//Class for the expandableListView

		private Context context;
		private ArrayList<CarteActivityHeader> carte;
		//private ArrayList<Boolean> isCheckedList;

		public CarteActivityListAdapter(Context context, ArrayList<CarteActivityHeader> carte) {
			this.context = context;
			this.carte = carte;
			//this.isCheckedList = new ArrayList <Boolean> ();
		}

		/*public ArrayList <Boolean> getIsCheckedList () {
			return isCheckedList;
		}*/

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			if (carte != null && carte.size() > groupPosition && carte.get(groupPosition) != null) {
				if (carte.get(groupPosition).getMealList().size() >childPosition)
					return carte.get(groupPosition).getMealList().get(childPosition);
			}

			return null;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return (long)(groupPosition*1024 + childPosition);
		}

		@Override
		public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

			View view = null;
			if (convertView == null) {

				view = getLayoutInflater().inflate(R.layout.activity_carte_child_row, null);

				final ViewHolder childHolder = new ViewHolder();
				childHolder.cb = (CheckBox) view.findViewById(R.id.carte_child_check);
				childHolder.tx = (TextView) view.findViewById(R.id.carte_child_text);
				childHolder.price = (TextView) view.findViewById(R.id.carte_child_price);

				childHolder.cb
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton button, boolean isCheked) {

						CarteActivityChild item = (CarteActivityChild) childHolder.cb.getTag();//We get back the object associated
						item.setSelected(button.isChecked());

					}
				});

				view.setTag(childHolder);//We associate the view with his viewHolder
				childHolder.cb.setTag(carte.get(groupPosition).getMealList().get(childPosition));//We associate the checkBox with the "model" object


			}
			else {

				view = convertView;
				((ViewHolder) view.getTag()).cb.setTag(carte.get(groupPosition).getMealList().get(childPosition));//We re-associate the checkBox with another object
			}

			ViewHolder holder = (ViewHolder) view.getTag();
			holder.cb.setSelected(carte.get(groupPosition).getMealList().get(childPosition).isSelected());//We change what we have to
			holder.tx.setText(carte.get(groupPosition).getMealList().get(childPosition).getMealName());
			holder.price.setText(carte.get(groupPosition).getMealList().get(childPosition).getMealPrice() + " EUR");

			return view;

		}

		@Override
		public int getChildrenCount(int groupPosition) {

			if (carte != null && carte.size() > groupPosition && carte.get(groupPosition) != null) {
				ArrayList<CarteActivityChild> mealList = carte.get(groupPosition).getMealList();
				return mealList.size();
			}
			return 0;

		}

		@Override
		public Object getGroup(int groupPosition) {
			if (carte != null && carte.size() > groupPosition)
				return carte.get(groupPosition);

			return null;
		}

		@Override
		public int getGroupCount() {
			if (carte != null) {
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

			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.activity_carte_group_heading, null);
			}

			TextView tv = (TextView) convertView.findViewById(R.id.carte_heading);
			tv.setText(carte.get(groupPosition).getMenuName());

			//			CarteActivityHeader menu = (CarteActivityHeader) getGroup(groupPosition);
			//
			//			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//			view = inf.inflate(R.layout.activity_carte_group_heading, null);
			//
			//			TextView menuName = (TextView) view.findViewById(R.id.carte_heading);
			//			menuName.setText(menu.getMenuName());
			//			menuName.setGravity(Gravity.CENTER);
			//			menuName.setTypeface(null, Typeface.BOLD);

			if (groupPosition==0) {//We don't show the first divider
				ImageView image = (ImageView) convertView.findViewById(R.id.carte_divider);
				image.setVisibility(View.GONE);
			}


			return convertView;
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

