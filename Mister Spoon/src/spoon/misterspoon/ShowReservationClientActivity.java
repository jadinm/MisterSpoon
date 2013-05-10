package spoon.misterspoon;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

public class ShowReservationClientActivity extends Activity {

	Context context = this;
	MySQLiteHelper sql = new MySQLiteHelper(this);
	
	ExpandableListView reservationList;
	
	Booking currentBooking;
	private Client c;
	List<Booking> bookingList;
	private String emailClient;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		setContentView(R.layout.activity_client_reservation);
		
		Intent i = getIntent();
		
		emailClient = i.getStringExtra(Login.email);
		c = new Client(sql, emailClient);
		bookingList = c.getBooking(true);
		
		ShowReservationClientItem reservationClientItem_data[] = new ShowReservationClientItem[bookingList.size()];
		
		for(int j = 0 ; j < bookingList.size() ; j++){
			List<String> commandList = new ArrayList<String>();
			for(Meal meal : bookingList.get(j).getCommande()){
				commandList.add(Booking.nombrePlat(sql, meal.getMealName(), c.getEmail()) + " x " + meal.getMealName());
			}
			Restaurant superRest = new Restaurant(sql,bookingList.get(j).getRestaurant().getRestaurantName());
			reservationClientItem_data[j] = new ShowReservationClientItem(bookingList.get(j).getHeureReservation().getHour()+":"+bookingList.get(j).getHeureReservation().getMinute(), superRest.getRestaurantName(),superRest.getRestaurantPhone(true),bookingList.get(j).getNombrePlaces()+"", commandList);
		}
		
		ShowReservationClientActivityListAdapter adapter = new ShowReservationClientActivityListAdapter(this, getOPReservationList());
		
		reservationList = (ExpandableListView) findViewById(R.id.reservation_client_reservationList);
		
		View header = (View)getLayoutInflater().inflate(R.layout.reservation_client_header, null);
        reservationList.addHeaderView(header);
        
        reservationList.setAdapter(adapter);
        
        
		
        //reservationList.setOnChildClickListener(reservationListViewChild);
        //reservationList.setOnGroupClickListener(carteListViewGroup);
	}
	
	private ArrayList<ShowReservationClientActivityHeader> getOPReservationList(){
		ArrayList <ShowReservationClientActivityHeader> opReservationList = new ArrayList <ShowReservationClientActivityHeader>();
		ArrayList <ShowReservationClientItem> reservationClientItem = new ArrayList <ShowReservationClientItem>();
		for(int j = 0 ; j < bookingList.size() ; j++){
			List<String> commandList = new ArrayList<String>();
			for(Meal meal : bookingList.get(j).getCommande()){
				commandList.add(Booking.nombrePlat(sql, meal.getMealName(), c.getEmail()) + " x " + meal.getMealName());
			}
			Restaurant superRest = new Restaurant(sql,bookingList.get(j).getRestaurant().getRestaurantName());
			if(j == 0){
				reservationClientItem.add(new ShowReservationClientItem(bookingList.get(j).getHeureReservation().getHour()+":"+bookingList.get(j).getHeureReservation().getMinute(), superRest.getRestaurantName(), superRest.getRestaurantPhone(false), bookingList.get(j).getNombrePlaces()+"", commandList));
			}
			else{
				if(bookingList.get(j).getDate().compareTo(bookingList.get(j-1).getDate()) == 0){
					reservationClientItem.add(new ShowReservationClientItem(bookingList.get(j).getHeureReservation().getHour()+":"+bookingList.get(j).getHeureReservation().getMinute(), superRest.getRestaurantName(), superRest.getRestaurantPhone(false), bookingList.get(j).getNombrePlaces()+"", commandList));
				}
				else{
					opReservationList.add(new ShowReservationClientActivityHeader(bookingList.get(j-1).getDate().toString(), reservationClientItem));
					reservationClientItem = new ArrayList <ShowReservationClientItem>();
					reservationClientItem.add(new ShowReservationClientItem(bookingList.get(j).getHeureReservation().getHour()+":"+bookingList.get(j).getHeureReservation().getMinute(), superRest.getRestaurantName(), superRest.getRestaurantPhone(false), bookingList.get(j).getNombrePlaces()+"", commandList));
				}
			}
			if(j == bookingList.size()-1){
				opReservationList.add(new ShowReservationClientActivityHeader(bookingList.get(j).getDate().toString(), reservationClientItem));
			}
		}
		
		return opReservationList;
	}
	
	
	
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out , R.anim.slide_up );
	}
}
