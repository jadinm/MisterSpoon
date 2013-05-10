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
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


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

	private ArrayList <reservationClientActivityItem> command;
	
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
		
		ListIterator<String> it = myCommandString.listIterator();
		while (it.hasNext()) {
			String mealName = (String) it.next();
			Meal meal = new Meal(mealName, restoName, sqliteHelper);
			prixTotal = prixTotal + meal.getMealPrice(false);
			command.add(new reservationClientActivityItem(mealName, "1"));
		}
		
		
		
		

		commandListView = (ListView) findViewById(R.id.meal_reservation_listing);
		adapter = new reservationClientActivityAdapter (context, command);
		commandListView.setAdapter(adapter);
		
		commandListView.setOnItemClickListener(commandListViewListener);

		total = (TextView) findViewById(R.id.prereservation_client_total);
		total.setText(prixTotal + " EUR");

		placesDispo = (TextView) findViewById(R.id.prereservation_client_places);
		

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

		updateDate();

		timeDisplay = (TextView)findViewById(R.id.reservation_client_text_time);
		pickTime = (Button)findViewById(R.id.reservation_client_butt_time);

		pickTime.setOnClickListener( new View.OnClickListener () {

			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);//pourquoi il le barre ?

			}

		});

		hours = cal.get(Calendar.HOUR);
		min = cal.get(Calendar.MINUTE);
		resTime = new Time(hours, min, 0);

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

				persoAlert = (LinearLayout) getLayoutInflater().inflate(R.layout.carte_builder_alert_box_set_menu, null);
				
				messageAlert = (TextView) persoAlert.findViewById(R.id.reservation_client_alert_box_quantite_message);
				editAlert = (EditText) persoAlert.findViewById(R.id.reservation_client_alert_box_edit);
				
				alert.setView(persoAlert);
			}



			alert.setTitle(currentMeal.getMealName())
			.setNegativeButton(getString(R.string.exit_cancel), null)
			.setPositiveButton(getString(R.string.exit_change), new DialogInterface.OnClickListener() {

				@Override
				public void onClick (DialogInterface arg0, int arg1) {//TODO

					
					arg0.cancel();

				}
			}).create().show();
		}
		
	};
	
	private OnClickListener bookListener = new OnClickListener() {
		public void onClick(View v) {
			/*Toast toast = Toast.makeText(context, "Réservation prise envoyée au restaurateur", Toast.LENGTH_SHORT);
			toast.show();
			
			Intent i = new Intent(ReservationClientActivity.this, Profil_Client.class);
			
			ArrayList<Meal> mealList = new ArrayList<Meal>();
			for (int l = 0; l<command.size(); l++) {
				Meal meal = new Meal(command.get(l).getMealName(), command);
				mealList.add(meal);
			}
			Booking b = new Booking(r,nbrPlaces, resTime, resDate ,mealList);
			c.addBooking(b);
			Client client = new Client(c.getEmail());
			
			
			i.putExtra(Login.email, client.getEmail());
			startActivity(i);*/
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
		placesDispo.setText(getString(R.string.prereservation_client_places1) +" "+ nbrPlaces);//TODO -> change strings

	}

	private DatePickerDialog.OnDateSetListener dateListener = 
			new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int yr, int monthOfYear,
				int dayOfMonth) {
			resDate = new Date(""+yr, ""+monthOfYear, ""+dayOfMonth);
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

