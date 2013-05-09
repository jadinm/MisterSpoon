package spoon.misterspoon;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PrereservationClientActivity extends Activity {
	
	private Restaurant r;
	private Client c;
	String restoName;
	String emailPerso;

	private Context context = this;
	public MySQLiteHelper sqliteHelper = new MySQLiteHelper(this);
	
    private ArrayList<Meal> myCommand;
    private ArrayList<String> myCommandString;
    
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
		}
		
		List<PreReservationClientItem> preReservationClientItem_data = new ArrayList<PreReservationClientItem>();
		

		for(Meal meal : myCommand){
			preReservationClientItem_data.add(new PreReservationClientItem(restoName,""+meal.getMealPrice(true),meal.getMealName()));
		}

		//TODO vérifier ceci :.
		commandListView = (ListView) findViewById(R.id.lvListe);
		
		View header = (View)getLayoutInflater().inflate(R.layout.prereservation_client_header, null);
		commandListView.addHeaderView(header);
		
		adapter = new PreReservationClientAdapter(context, R.layout.prereservation_client_list_item, preReservationClientItem_data);
		commandListView.setAdapter(adapter);
		
		total = (TextView) findViewById(R.id.prereservation_client_total);
		total.setText(prixTotal + " euro");
        
        preBook = (Button)findViewById(R.id.prereservation_client_prereserve);

        preBook.setOnClickListener( new View.OnClickListener () {
			@Override
			public void onClick(View v) {
				
				if (! c.preBook(myCommand)){
					Toast toast = Toast.makeText(context, "ERROR : Préréservation non effectuée !", Toast.LENGTH_SHORT);
					toast.show(); //TODO Gerer l'erreur ?
					return;
				}
				Toast toast = Toast.makeText(context, "Préréservation effectuée !", Toast.LENGTH_SHORT);
				toast.show();
				/*Intent i = new Intent(PrereservationClientActivity.this, CarteActivity.class);
				i.putExtra(Login.email, c.getEmail());
				startActivity(i);
				*/
			}
        });

    }

}

