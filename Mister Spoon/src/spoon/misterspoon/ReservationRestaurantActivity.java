package spoon.misterspoon;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

public class ReservationRestaurantActivity extends Activity {

	Context context = this;
	MySQLiteHelper sql = new MySQLiteHelper(this);
	
	ExpandableListView reservationList;
	
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
			for(Meal meal : bookingList.get(j).getCommande()){
				commandList.add(Booking.nombrePlat(sql, meal.getMealName(), bookingList.get(j).getClient().getEmail()) + " x " + meal.getMealName());
			}
			Client superClient = new Client(sql,bookingList.get(j).getClient().getEmail());
			reservationRestaurantItem_data[j] = new ReservationRestaurantItem(bookingList.get(j).getHeureReservation().getHour()+":"+bookingList.get(j).getHeureReservation().getMinute(), superClient.getName(true),superClient.getGsm(true),bookingList.get(j).getNombrePlaces()+"", commandList);
		}
		
		ReservationRestaurantActivityListAdapter adapter = new ReservationRestaurantActivityListAdapter(this, getOPReservationList());
		
		reservationList = (ExpandableListView) findViewById(R.id.reservation_restaurant_reservationList);
		
		View header = (View)getLayoutInflater().inflate(R.layout.reservation_restaurant_header, null);
        reservationList.addHeaderView(header);
        
        reservationList.setAdapter(adapter);
        
        
		
        //reservationList.setOnChildClickListener(reservationListViewChild);
        //reservationList.setOnGroupClickListener(carteListViewGroup);
	}
	
	private ArrayList<ReservationRestaurantActivityHeader> getOPReservationList(){
		ArrayList <ReservationRestaurantActivityHeader> opReservationList = new ArrayList <ReservationRestaurantActivityHeader>();
		ArrayList <ReservationRestaurantItem> reservationRestaurantItem = new ArrayList <ReservationRestaurantItem>();
		for(int j = 0 ; j < bookingList.size() ; j++){
			List<String> commandList = new ArrayList<String>();
			for(Meal meal : bookingList.get(j).getCommande()){
				commandList.add(Booking.nombrePlat(sql, meal.getMealName(), bookingList.get(j).getClient().getEmail()) + " x " + meal.getMealName());
			}
			Client superClient = new Client(sql,bookingList.get(j).getClient().getEmail());
			if(j == 0){
				reservationRestaurantItem.add(new ReservationRestaurantItem(bookingList.get(j).getHeureReservation().getHour()+":"+bookingList.get(j).getHeureReservation().getMinute(), superClient.getName(false), superClient.getGsm(false), bookingList.get(j).getNombrePlaces()+"", commandList));
			}
			else{
				if(bookingList.get(j).getDate().compareTo(bookingList.get(j-1).getDate()) == 0){
					reservationRestaurantItem.add(new ReservationRestaurantItem(bookingList.get(j).getHeureReservation().getHour()+":"+bookingList.get(j).getHeureReservation().getMinute(), superClient.getName(false), superClient.getGsm(false), bookingList.get(j).getNombrePlaces()+"", commandList));
				}
				else{
					opReservationList.add(new ReservationRestaurantActivityHeader(bookingList.get(j-1).getDate().toString(), reservationRestaurantItem));
					reservationRestaurantItem = new ArrayList <ReservationRestaurantItem>();
					reservationRestaurantItem.add(new ReservationRestaurantItem(bookingList.get(j).getHeureReservation().getHour()+":"+bookingList.get(j).getHeureReservation().getMinute(), superClient.getName(false), superClient.getGsm(false), bookingList.get(j).getNombrePlaces()+"", commandList));
				}
			}
			if(j == bookingList.size()-1){
				opReservationList.add(new ReservationRestaurantActivityHeader(bookingList.get(j).getDate().toString(), reservationRestaurantItem));
			}
		}
		
		return opReservationList;
	}
	
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out , R.anim.slide_up );
	}
}
