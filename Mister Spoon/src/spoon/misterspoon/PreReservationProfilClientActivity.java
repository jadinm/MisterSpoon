package spoon.misterspoon;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class PreReservationProfilClientActivity extends Activity {

	Context context = this;
	MySQLiteHelper sql = new MySQLiteHelper(this);
	
	ListView preReservationList;
	private PreReservationProfilClientAdapter adapter;
	
	Client client;
	List<PreBooking> bookingList;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		setContentView(R.layout.activity_profil_client_prereservation);

		Intent i = getIntent();
		String sclient = i.getStringExtra(Login.email);
		
		client = new Client(sql, sclient);
		bookingList = client.getPreBooking(true);
		
		List<PreReservationProfilClientItem> preReservationProfilClientItem_data = new ArrayList<PreReservationProfilClientItem>();
		
		for(int j = 0 ; j < bookingList.size() ; j++){
			List<String> commandList = new ArrayList<String>();
			for(Meal meal : bookingList.get(j).getCommande()){
				commandList.add(PreBooking.nombrePlat(sql, meal.getMealName(), client.getEmail()) + " x " + meal.getMealName());
			}
			preReservationProfilClientItem_data.add(new PreReservationProfilClientItem(bookingList.get(j).getRestaurant().getRestaurantName(), commandList));
		}
		
		preReservationList = (ListView) findViewById(R.id.prereservation_profil_client);
		
		View header = (View)getLayoutInflater().inflate(R.layout.prereservation_profil_client_header, null);
        preReservationList.addHeaderView(header);
        
        adapter = new PreReservationProfilClientAdapter(context, R.layout.prereservation_profil_client_item_list, preReservationProfilClientItem_data);
		preReservationList.setAdapter(adapter);
	}
	
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out , R.anim.slide_up );
	}
}
