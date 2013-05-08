package spoon.misterspoon;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

public class ClientReservationActivity extends Activity {

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
		setContentView(R.layout.activity_reservation_restaurant);
		
		Intent i = getIntent();
		
		emailClient = i.getStringExtra(Login.email);
		c = new Client(sql, emailClient);
		bookingList = c.getBooking(true);
		
		ReservationClientItem reservationClientItem_data[] = new ReservationClientItem[bookingList.size()];
		
		Log.v("Taille liste book : ",bookingList.size()+"");
		for(int j = 0 ; j < bookingList.size() ; j++){
			List<String> commandList = new ArrayList<String>();
			Log.v("Taille liste commandes : ",bookingList.get(j).getCommande().size()+"");
			for(Meal meal : bookingList.get(j).getCommande()){
				commandList.add(Booking.nombrePlat(sql, meal.getMealName(), bookingList.get(j).getClient().getEmail()) + " x " + meal.getMealName());
			}
			Restaurant superRest = new Restaurant(sql,bookingList.get(j).getRestaurant().getRestaurantName());
			reservationClientItem_data[j] = new ReservationClientItem(bookingList.get(j).getHeureReservation().getHour()+":"+bookingList.get(j).getHeureReservation().getMinute(), superRest.getRestaurantName(),superRest.getRestaurantPhone(true),bookingList.get(j).getNombrePlaces()+"", commandList);
		}
		
		ReservationClientActivityListAdapter adapter = new ReservationClientActivityListAdapter(this, getOPReservationList());
		
		reservationList = (ExpandableListView) findViewById(R.id.reservation_client_reservationList);
		
		View header = (View)getLayoutInflater().inflate(R.layout.reservation_client_header, null);
        reservationList.addHeaderView(header);
        
        reservationList.setAdapter(adapter);
        
        
		
        //reservationList.setOnChildClickListener(reservationListViewChild);
        //reservationList.setOnGroupClickListener(carteListViewGroup);
	}
	
	private ArrayList<ReservationClientActivityHeader> getOPReservationList(){
		ArrayList <ReservationClientActivityHeader> opReservationList = new ArrayList <ReservationClientActivityHeader>();
		ArrayList <ReservationClientItem> reservationClientItem = new ArrayList <ReservationClientItem>();
		for(int j = 0 ; j < bookingList.size() ; j++){
			List<String> commandList = new ArrayList<String>();
			for(Meal meal : bookingList.get(j).getCommande()){
				commandList.add(Booking.nombrePlat(sql, meal.getMealName(), bookingList.get(j).getRestaurant().getRestaurantName()) + " x " + meal.getMealName());
			}
			Restaurant superRest = new Restaurant(sql,bookingList.get(j).getRestaurant().getRestaurantName());
			if(j == 0){
				reservationClientItem.add(new ReservationClientItem(bookingList.get(j).getHeureReservation().getHour()+":"+bookingList.get(j).getHeureReservation().getMinute(), superRest.getRestaurantName(), superRest.getRestaurantPhone(false), bookingList.get(j).getNombrePlaces()+"", commandList));
			}
			else{
				if(bookingList.get(j).getDate().compareTo(bookingList.get(j-1).getDate()) == 0){
					reservationClientItem.add(new ReservationClientItem(bookingList.get(j).getHeureReservation().getHour()+":"+bookingList.get(j).getHeureReservation().getMinute(), superRest.getRestaurantName(), superRest.getRestaurantPhone(false), bookingList.get(j).getNombrePlaces()+"", commandList));
				}
				else{
					opReservationList.add(new ReservationClientActivityHeader(bookingList.get(j).getDate().toString(), reservationClientItem));
					reservationClientItem = new ArrayList <ReservationClientItem>();
					reservationClientItem.add(new ReservationClientItem(bookingList.get(j).getHeureReservation().getHour()+":"+bookingList.get(j).getHeureReservation().getMinute(), superRest.getRestaurantName(), superRest.getRestaurantPhone(false), bookingList.get(j).getNombrePlaces()+"", commandList));
				}
			}
			if(j == bookingList.size()-1){
				opReservationList.add(new ReservationClientActivityHeader(bookingList.get(j).getDate().toString(), reservationClientItem));
			}
		}
		
		return opReservationList;
	}
	
	/*private OnChildClickListener reservationListViewChild =  new OnChildClickListener() {

		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {//TODO launch an alert box
			
			ReservationRestaurantItem child = getOPReservationList().get(groupPosition).getReservationList().get(childPosition);
			Meal meal = carte.getMenuList().get(groupPosition).getMealList(false).get(childPosition);
			childView = (CheckBox) v.findViewById(R.id.carte_child_check);
			
			currentMeal = meal;
			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			
			builder.setTitle(meal.getMealName() + " == " + child.getMealName() + "?");
			builder.setMessage(getString(R.string.carte_alert_message));
			
			builder.setCancelable(true);//We can go back with the return button
			
			builder.setNegativeButton(getString(R.string.carte_alert_negative), new DialogInterface.OnClickListener() {

				@Override
				public void onClick (DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			builder.setNeutralButton(getString(R.string.carte_alert_neutre), new DialogInterface.OnClickListener() {

				@Override
				public void onClick (DialogInterface dialog, int id) {//Check the ckeckbox
					
					if (childView.isChecked()) {
						childView.setSelected(false);
						dialog.cancel();
						return;
					}
					childView.setSelected(true);
					dialog.cancel();
				}
			});
			builder.setPositiveButton(getString(R.string.carte_alert_positive), new DialogInterface.OnClickListener() {

				@Override
				public void onClick (DialogInterface dialog, int id) {//launch the last activity
					Intent i = new Intent(CarteActivity.this, MealActivity.class);
					i.putExtra(RESTAURANT, restName);
					i.putExtra(MEAL, currentMeal.getMealName());
					i.putExtra(CLIENT, client.getEmail());
					startActivity(i);
					return;
				}
			});
			
			AlertDialog alertDialog = builder.create();
			
			alertDialog.show();

			return true;
		}

	};*/
	
	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out , R.anim.slide_up );
	}
}
