package spoon.misterspoon;

import java.util.ArrayList;
import java.util.ListIterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class PrereservationClientActivity extends Activity {
	
	private Restaurant r;
	private Client c;
	private MySQLiteHelper sqliteHelper1 = new MySQLiteHelper(this);
	String restoName;
	String emailPerso;

	public MySQLiteHelper sqliteHelper;
	
    private ArrayList<Meal> myCommand;
    private ArrayList<String> myCommandString;
    
    private Button preBook;
	protected Context context;
	private ListView commandListView;
	private ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		Utils.onActivityCreateSetTheme(this);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.activity_prereservation_client);
		
		//We get the intent sent by Login
		Intent i = getIntent();
		
		//We take the informations about the person who's logged (!!!! label)
		emailPerso = i.getStringExtra(Login.email);
		//restoName = i.getStringExtra(RestaurantListActivity.RESTAURANT);
		restoName = "Loungeatude";
		//We create the object Restaurant associated with this email and all his informations
		r = new Restaurant (sqliteHelper1, restoName);
		c = new Client(sqliteHelper1, emailPerso);
		c.setRestaurantEnCours(r);
		
		myCommandString = (ArrayList<String>) i.getStringArrayListExtra("La ou je vais recuperer ma commande");//TODO
		
		ListIterator<String> it = myCommandString.listIterator();
		while (it.hasNext()) {
			String meal = (String) it.next();
			myCommand.add(new Meal(meal, restoName, sqliteHelper1));
		}
		
		//TODO vérifier ceci :.
		commandListView = (ListView) findViewById(R.id.lvListe);
		adapter = new ArrayAdapter<String>(this, R.id.lvListe, myCommandString);//pas sur ici...
		commandListView.setAdapter(adapter);
        
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

