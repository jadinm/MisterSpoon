package spoon.misterspoon;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PrereservationClientActivity extends Activity {

	private Restaurant r;
	private Client c;
	String restoName;
	String emailPerso;

	private Context context = this;
	public MySQLiteHelper sqliteHelper = new MySQLiteHelper(this);

	private ArrayList<Meal> myCommand;
	private ArrayList<String> myCommandString;
	
	private Meal currentMeal;
	private LinearLayout persoAlert;
	private TextView messageAlert;
	private EditText editAlert;

	private Button preBook;
	private ListView commandListView;
	private PreReservationClientAdapter adapter;
	private double prixTotal;
	private TextView total;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.activity_prereservation_client);

		//We get the intent sent by Login
		Intent i = getIntent();

		emailPerso = i.getStringExtra(CarteActivity.CLIENT);
		restoName = i.getStringExtra(CarteActivity.RESTAURANT);
		//We create the object Restaurant associated with this email and all his informations
		r = new Restaurant (sqliteHelper, restoName);
		c = new Client(sqliteHelper, emailPerso);
		c.setRestaurantEnCours(r);

		myCommand = new ArrayList<Meal>();

		myCommandString = (ArrayList<String>) i.getStringArrayListExtra(CarteActivity.MEALLIST);

		prixTotal = 0;

		ListIterator<String> it = myCommandString.listIterator();
		while (it.hasNext()) {
			String mealName = (String) it.next();
			Meal meal = new Meal(mealName, restoName, sqliteHelper);
			myCommand.add(meal);
			prixTotal = prixTotal + meal.getMealPrice(false);
			meal.setMealStock(1);
		}

		List<PreReservationClientItem> preReservationClientItem_data = new ArrayList<PreReservationClientItem>();
		for(Meal meal : myCommand){
			preReservationClientItem_data.add(new PreReservationClientItem(restoName,""+meal.getMealPrice(false),meal.getMealName(), meal.getMealStock(false) + ""));
		}

		commandListView = (ListView) findViewById(R.id.lvListe);

		View header = (View)getLayoutInflater().inflate(R.layout.prereservation_client_header, null);
		commandListView.addHeaderView(header);

		adapter = new PreReservationClientAdapter(context, R.layout.prereservation_client_list_item, preReservationClientItem_data);
		commandListView.setAdapter(adapter);
		
		commandListView.setOnItemClickListener(commandListViewListener);

		total = (TextView) findViewById(R.id.prereservation_client_total);
		total.setText(prixTotal + " euro");

		preBook = (Button)findViewById(R.id.prereservation_client_prereserve);

		preBook.setOnClickListener( new View.OnClickListener () {
			@Override
			public void onClick(View v) {

				if (! c.preBook(myCommand)) {
					Toast toast = Toast.makeText(context, "ERROR : Préréservation non effectuée !", Toast.LENGTH_SHORT);
					toast.show();
					return;
				}
				Toast toast = Toast.makeText(context, "Préréservation effectuée !", Toast.LENGTH_SHORT);
				toast.show();
				Intent i = new Intent(PrereservationClientActivity.this, Profil_Client.class);
				i.putExtra(Login.email, c.getEmail());
				startActivity(i);
				
			}
		});

	}

	private OnItemClickListener commandListViewListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

			AlertDialog.Builder alert = new AlertDialog.Builder(context);

			currentMeal = myCommand.get(arg2 - 1);

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
						currentMeal.setMealStock(Integer.parseInt(editAlert.getText().toString()));
					}

					List<PreReservationClientItem> preReservationClientItem_data = new ArrayList<PreReservationClientItem>();
					for(Meal meal : myCommand){
						preReservationClientItem_data.add(new PreReservationClientItem(restoName,""+meal.getMealPrice(false),meal.getMealName(), meal.getMealStock(false) + ""));
					}
					
					adapter = new PreReservationClientAdapter(context, R.layout.prereservation_client_list_item, preReservationClientItem_data);
					commandListView.setAdapter(adapter);

					prixTotal = 0;//We modify the total
					for(int i=0; i<myCommand.size(); i++) {
						prixTotal = prixTotal + myCommand.get(i).getMealPrice(false) * myCommand.get(i).getMealStock(false);
					}

					total.setText(getString(R.string.prereservation_client_total) + prixTotal + " EUR");

					arg0.cancel();

				}
			}).create().show();
		}

	};

}

