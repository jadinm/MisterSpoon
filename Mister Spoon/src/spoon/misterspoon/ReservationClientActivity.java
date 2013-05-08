package spoon.misterspoon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


public class ReservationClientActivity extends Activity {
	
	private Restaurant r;
	private Client c;
	private MySQLiteHelper sqliteHelper = new MySQLiteHelper(this);
	String restoName;
	String emailPerso;
	
	private Button book;
	//Useful for the alertBoxes
	private Context context = this;
	
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
		emailPerso = i.getStringExtra(RestaurantListActivity.emailLogin);
		restoName = i.getStringExtra(RestaurantListActivity.RESTAURANT);
		//We create the object Restaurant associated with this email and all his informations
		r = new Restaurant (sqliteHelper, restoName);
		c = new Client(sqliteHelper, emailPerso);
		c.setRestaurantEnCours(r);

		book = (Button) findViewById(R.id.prereservation_client_reserve);

		book.setOnClickListener(new View.OnClickListener() {//launch another view
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(ReservationClientActivity.this, CarteActivity.class);
				intent.putExtra(Login.email, c.getEmail());
				intent.putExtra(RestaurantListActivity.RESTAURANT, r.getRestaurantName());
				startActivity(intent);
				
				Toast toast = Toast.makeText(context, "Un client veut choisir l heure et la date de reservation", Toast.LENGTH_SHORT);
				toast.show();
				//TODO
			}
		});
	}
	
}
