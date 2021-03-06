package spoon.misterspoon;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PreReservationRestaurantActivity extends Activity {

	Context context = this;
	MySQLiteHelper sql = new MySQLiteHelper(this);

	ListView preReservationList;
	private PreReservationRestaurantAdapter adapter;

	RestaurantOwner restaurantOwner;
	List<PreBooking> bookingList;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition ( 0 , R.anim.slide_up );
		setContentView(R.layout.activity_prereservation_restaurant);

		Intent i = getIntent();
		String sEmail = i.getStringExtra(Profil_Restaurant.MAIL);
		restaurantOwner = new RestaurantOwner(sql, sEmail);
		bookingList = restaurantOwner.getRestaurantPreReservation(true);
		
		List<PreReservationRestaurantItem> preReservationRestaurantItem_data = new ArrayList<PreReservationRestaurantItem>();

		for(int j = 0 ; j < bookingList.size() ; j++){
			List<String> commandList = new ArrayList<String>();
			for(Meal meal : bookingList.get(j).getCommande()){
				commandList.add(PreBooking.nombrePlat(sql, meal.getMealName(), bookingList.get(j).getClient().getEmail()) + " x " + meal.getMealName());
			}
			Client superClient = new Client(sql,bookingList.get(j).getClient().getEmail());
			preReservationRestaurantItem_data.add(new PreReservationRestaurantItem(superClient.getName(true),superClient.getEmail(),superClient.getGsm(true), commandList));
		}

		preReservationList = (ListView) findViewById(R.id.prereservation_restaurant_list);
		
		View header = (View)getLayoutInflater().inflate(R.layout.prereservation_restaurant_header, null);
        preReservationList.addHeaderView(header);
        
		adapter = new PreReservationRestaurantAdapter(context, R.layout.prereservation_list_item_view, preReservationRestaurantItem_data);
		preReservationList.setAdapter(adapter);

		preReservationList.setOnItemClickListener(preReservationListListener);
	}

	public void onPause(){
		super.onPause();
		overridePendingTransition ( R.anim.slide_out , R.anim.slide_up );
	}
	
	private void refreshList(){
		bookingList = restaurantOwner.getRestaurantPreReservation(true);
		List<PreReservationRestaurantItem> preReservationRestaurantItem_data = new ArrayList<PreReservationRestaurantItem>();

		for(int j = 0 ; j < bookingList.size() ; j++){
			List<String> commandList = new ArrayList<String>();
			for(Meal meal : bookingList.get(j).getCommande()){
				commandList.add(PreBooking.nombrePlat(sql, meal.getMealName(), bookingList.get(j).getClient().getEmail()) + " x " + meal.getMealName());
			}
			Client superClient = new Client(sql,bookingList.get(j).getClient().getEmail());
			preReservationRestaurantItem_data.add(new PreReservationRestaurantItem(superClient.getName(true),superClient.getEmail(),superClient.getGsm(true), commandList));
		}
		
		adapter = new PreReservationRestaurantAdapter(context, R.layout.prereservation_list_item_view, preReservationRestaurantItem_data);
		preReservationList.setAdapter(adapter);
	}

	private OnItemClickListener preReservationListListener = new OnItemClickListener(){
		public void onItemClick(final AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);
			alertDialogBuilder.setTitle(R.string.prereservation_alert_title);
			alertDialogBuilder
			.setMessage(R.string.prereservation_alert_message)
			.setCancelable(false)
			.setPositiveButton(R.string.exit_confirm,new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					PreReservationRestaurantItem currentItem = (PreReservationRestaurantItem) arg0.getItemAtPosition(arg2);
					ArrayList<Meal> mealList = new ArrayList<Meal>(); 
					for(String mealName : currentItem.plat){
						mealList.add(new Meal(mealName.split(" x ")[1]));
					}
					restaurantOwner.removeRestaurantPreReservation(currentItem.email,mealList);
					
					refreshList();
				}
			  })
			.setNegativeButton(R.string.exit_cancel,new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				}
			});

			AlertDialog alertDialog = alertDialogBuilder.create();

			alertDialog.show();
		}


	};
}