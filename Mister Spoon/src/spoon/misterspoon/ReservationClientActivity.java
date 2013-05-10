package spoon.misterspoon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.ListIterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class ReservationClientActivity extends Activity {

	private Restaurant r;
	private Client c;
	private MySQLiteHelper sqliteHelper = new MySQLiteHelper(this);
	private String restoName;
	private String emailPerso;

	static final int DATE_DIALOG_ID = 1;
	static final int TIME_DIALOG_ID = 2;
	private TextView dateDisplay;
	private Button pickDate;
	private int year, month, day;
	private TextView timeDisplay;
	private Button pickTime;
	private int hours, min;
	private Time resTime;
	private Date resDate;
	private TextView messageAlert;
	private EditText editAlert;
	
	private EditText nbrPlacesWanted;

	private ArrayList <reservationClientActivityItem> command;
	private ArrayList <Meal> mealList;
	
	private reservationClientActivityItem currentMeal;
	
	private LinearLayout persoAlert;

	private Button book;
	protected Context context = this;
	private ArrayList<String> myCommandString;
	private int nbrPlaces;
	private ListView commandListView;
	private reservationClientActivityAdapter adapter;
	private TextView total;
	private TextView placesDispo;
	private double prixTotal;
	private Date date;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.activity_reservation_client);

		//We get the intent sent by Login
		Intent i = getIntent();

		//We take the informations about the person who's logged (!!!! label)
		emailPerso = i.getStringExtra(CarteActivity.CLIENT);
		restoName = i.getStringExtra(CarteActivity.RESTAURANT);
		//We create the object Restaurant associated with this email and all his informations
		r = new Restaurant (sqliteHelper, restoName);
		c = new Client(sqliteHelper, emailPerso);
		c.setRestaurantEnCours(r);

		myCommandString = (ArrayList<String>) i.getStringArrayListExtra(CarteActivity.MEALLIST);
		

		prixTotal = 0;
		
		mealList = new ArrayList <Meal> ();
		command = new ArrayList <reservationClientActivityItem> ();
		
		ListIterator<String> it = myCommandString.listIterator();
		while (it.hasNext()) {
			String mealName = (String) it.next();
			Meal meal = new Meal(mealName, restoName, sqliteHelper);
			prixTotal = prixTotal + meal.getMealPrice(false);
			mealList.add(meal);
			command.add(new reservationClientActivityItem(mealName, "1"));
		}
		
		
		
		

		commandListView = (ListView) findViewById(R.id.meal_reservation_listing);
		adapter = new reservationClientActivityAdapter (context, command);
		commandListView.setAdapter(adapter);
		
		commandListView.setOnItemClickListener(commandListViewListener);

		total = (TextView) findViewById(R.id.prereservation_client_total);
		total.setText(getString(R.string.prereservation_client_total) + prixTotal + " EUR");

		placesDispo = (TextView) findViewById(R.id.prereservation_client_places);
		
		nbrPlacesWanted = (EditText) findViewById(R.id.reservation_client_nbr_places);
		

		dateDisplay = (TextView) findViewById(R.id.reservation_client_text_date);
		pickDate = (Button) findViewById(R.id.reservation_client_butt_date);

		pickDate.setOnClickListener( new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}

		});

		final Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
		resDate = new Date(""+year, ""+(month+1), ""+day);
		
		hours = cal.get(Calendar.HOUR);
		min = cal.get(Calendar.MINUTE);
		resTime = new Time(hours, min, 0);

		timeDisplay = (TextView)findViewById(R.id.reservation_client_text_time);
		pickTime = (Button)findViewById(R.id.reservation_client_butt_time);

		pickTime.setOnClickListener( new View.OnClickListener () {

			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);

			}

		});
		updateDate();
		updateTime();
		
		nbrPlaces = r.getRestaurantCapacity(false) - r.getRestaurantBooking(restoName, resDate ,resTime);
		
		placesDispo.setText(getString(R.string.prereservation_client_places1) +" "+ nbrPlaces);

		book = (Button)findViewById(R.id.prereservation_client_reserve);
		
		book.setOnClickListener(bookListener);

		

	}
	
	private OnItemClickListener commandListViewListener = new OnItemClickListener(){
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			
			AlertDialog.Builder alert = new AlertDialog.Builder(context);
			
			currentMeal = command.get(arg2);

			if (persoAlert==null) {

				persoAlert = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_reservation_client_alert_box, null);

				messageAlert = (TextView) persoAlert.findViewById(R.id.reservation_client_alert_box_quantite_message);
				editAlert = (EditText) persoAlert.findViewById(R.id.reservation_client_alert_box_edit);

			}

			if (persoAlert.getParent() == null) {

				alert.setView(persoAlert);
			} 
			else {
				persoAlert = null; //set it to null

				persoAlert = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_reservation_client_alert_box, null);
				
				messageAlert = (TextView) persoAlert.findViewById(R.id.reservation_client_alert_box_quantite_message);
				editAlert = (EditText) persoAlert.findViewById(R.id.reservation_client_alert_box_edit);
				
				alert.setView(persoAlert);
			}



			alert.setTitle(currentMeal.getMealName())
			.setNegativeButton(getString(R.string.exit_cancel), null)
			.setPositiveButton(getString(R.string.exit_change), new DialogInterface.OnClickListener() {

				@Override
				public void onClick (DialogInterface arg0, int arg1) {

					
					if (editAlert.getText().toString().length() > 0) {//We change
						currentMeal.setMealQuantity(editAlert.getText().toString());
					}
					
					adapter = new reservationClientActivityAdapter (context, command);//We redraw the list
					commandListView.setAdapter(adapter);
					
					prixTotal = 0;//We modify the total
					for(int i=0; i<mealList.size(); i++) {
						prixTotal = prixTotal + mealList.get(i).getMealPrice(false) * Integer.parseInt(command.get(i).getMealQuantity());
					}
					
					total.setText(getString(R.string.prereservation_client_total) + prixTotal + " EUR");
					
					arg0.cancel();

				}
			}).create().show();
		}
		
	};
	
	private OnClickListener bookListener = new OnClickListener() {
		public void onClick(View v) {
			
			for (int l = 0; l<command.size(); l++) {
				mealList.get(l).setMealStock(Integer.parseInt(command.get(l).getMealQuantity()));
			}

			if (nbrPlacesWanted.getText().toString().length() == 0) {
				
				Toast.makeText(context, R.string.reservation_client_echec, Toast.LENGTH_SHORT).show();//Error
				return;
			}
			if(nbrPlaces - Integer.parseInt(nbrPlacesWanted.getText().toString()) < 0) {
				Toast.makeText(context, R.string.reservation_client_echec, Toast.LENGTH_SHORT).show();//Error
				return;
			}
			
			boolean test = c.book(mealList, Integer.parseInt(nbrPlacesWanted.getText().toString()), resTime, resDate);
			if (!test) {
				Toast.makeText(context, R.string.reservation_client_echec, Toast.LENGTH_SHORT).show();//Error
				return;
			}
			
			Intent i = new Intent(ReservationClientActivity.this, Profil_Client.class);			
			
			i.putExtra(Login.email, c.getEmail());
			startActivity(i);
		}
	};

	private void updateTime() {
		timeDisplay.setText(resTime.toString());
		nbrPlaces = r.getRestaurantCapacity(false) - r.getRestaurantBooking(restoName, resDate ,resTime);
		placesDispo.setText(getString(R.string.prereservation_client_places1) +" "+ nbrPlaces);

	}

	private void updateDate() {
		dateDisplay.setText(getString(R.string.reservation_client_date_text) + " " +resDate.toString());
		nbrPlaces = r.getRestaurantCapacity(false) - r.getRestaurantBooking(restoName, resDate ,resTime);
		placesDispo.setText(getString(R.string.prereservation_client_places1) +" "+ nbrPlaces);

	}

	private DatePickerDialog.OnDateSetListener dateListener = 
			new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int yr, int monthOfYear,
				int dayOfMonth) {
			resDate = new Date(""+yr, ""+(monthOfYear+1), ""+dayOfMonth);
			updateDate();
		}
	};

	private TimePickerDialog.OnTimeSetListener timeListener = 
			new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			resTime = new Time(hourOfDay, minute, 0);
			updateTime();
		}

	};
	protected Dialog onCreateDialog(int id){
		switch(id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, dateListener, year, month, day);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this, timeListener, hours, min, false);
		}
		return null;

	}
	
	public void onPause(){
		super.onPause();
		
		overridePendingTransition ( R.anim.slide_out, R.anim.slide_up );
	}
	
}

