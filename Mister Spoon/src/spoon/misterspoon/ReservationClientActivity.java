package spoon.misterspoon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.ListIterator;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class ReservationClientActivity extends Activity {

	private Restaurant r;
	private Client c;
	private MySQLiteHelper sqliteHelper = new MySQLiteHelper(this);
	String restoName;
	String emailPerso;

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

	private ArrayList<Meal> myCommand;

	private Button book;
	protected Context context;
	private ArrayList<String> myCommandString;
	private int nbrPlaces;
	private ListView commandListView;
	private ArrayAdapter<String> adapter;
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

		myCommandString = (ArrayList<String>) i.getStringArrayListExtra(CarteActivity.MEALLIST);//TODO
		

		prixTotal = 0;
		
		ListIterator<String> it = myCommandString.listIterator();
		while (it.hasNext()) {
			String mealName = (String) it.next();
			Meal meal = new Meal(mealName, restoName, sqliteHelper);
			prixTotal = prixTotal + meal.getMealPrice(false);
		}
		
		Log.v("Prix " , prixTotal +"");
		
		

		commandListView = (ListView) findViewById(R.id.meal_reservation_listing);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myCommandString);//pas sur ici...
		commandListView.setAdapter(adapter);

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
		
		Log.v("nbrPlace" , nbrPlaces +"");
		
		placesDispo.setText(placesDispo.getText() +" "+ nbrPlaces);

		book = (Button)findViewById(R.id.prereservation_client_reserve);

		book.setOnClickListener( new View.OnClickListener () {
			@Override
			public void onClick(View v) {

				if (! c.book(myCommand, nbrPlaces, resTime, resDate)){
					Toast toast = Toast.makeText(context, "ERROR : Réservation non effectuee !", Toast.LENGTH_SHORT);
					toast.show(); //TODO Gérer l'erreur
					return;
				}
				Toast toast = Toast.makeText(context, "Reservation effectuee !", Toast.LENGTH_SHORT);
				toast.show();
				/*Intent i = new Intent(ReservationClientActivity.this, Profil_Client.class);
				i.putExtra(Login.email, c.getEmail());
				startActivity(i);
				 */
			}
		});

	}

	private void updateTime() {
		timeDisplay.setText(resTime.toString());

	}

	private void updateDate() {
		dateDisplay.setText(dateDisplay.getText() + " " +resDate.toString());

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
}

