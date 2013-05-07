package spoon.misterspoon;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ReservationRestaurantActivity extends Activity {

	Context context = this;
	MySQLiteHelper sql = new MySQLiteHelper(this);
	
	ListView reservationList;
	
	Booking currentBooking;
	
	RestaurantOwner restaurantOwner;
	List<Booking> bookingList;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		setContentView(R.layout.activity_reservation_restaurant);
		
		Intent i = getIntent();
		String sEmail = i.getStringExtra(Profil_Restaurant.MAIL);
		restaurantOwner = new RestaurantOwner(sql, sEmail);
		bookingList = restaurantOwner.getRestaurantReservation(true);
		
		ReservationRestaurantItem reservationRestaurantItem_data[] = new ReservationRestaurantItem[bookingList.size()];
		
		for(int j = 0 ; j < bookingList.size() ; j++){
			List<String> commandList = new ArrayList<String>();
			Log.v("Taille liste commandes : ",bookingList.get(j).getCommande().size()+"");
			for(Meal meal : bookingList.get(j).getCommande()){
				commandList.add(meal.getMealName());
			}
			Client superClient = new Client(sql,bookingList.get(j).getClient().getEmail());
			reservationRestaurantItem_data[j] = new ReservationRestaurantItem(bookingList.get(j).getHeureReservation().getHour()+":"+bookingList.get(j).getHeureReservation().getMinute(), superClient.getName(true),superClient.getGsm(true),bookingList.get(j).getNombrePlaces()+"", commandList);
		}
		
		ReservationRestaurantAdapter adapter = new ReservationRestaurantAdapter(this, R.layout.reservation_restaurant_list_item_view, reservationRestaurantItem_data);
		
		reservationList = (ListView) findViewById(R.id.reservation_restaurant_reservationList);
		
		View header = (View)getLayoutInflater().inflate(R.layout.reservation_restaurant_header, null);
        reservationList.addHeaderView(header);
        
        reservationList.setAdapter(adapter);
		
		reservationList.setOnItemClickListener(reservationListListener);
	}
	
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out , R.anim.slide_up );
	}
	
	private OnItemClickListener reservationListListener = new OnItemClickListener(){
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			currentBooking = (Booking) reservationList.getSelectedItem();
		}
		
	};
}
