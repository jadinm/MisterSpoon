package spoon.misterspoon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PrereservationRestaurantActivity extends Activity {

	Context context = this;
	MySQLiteHelper sql = new MySQLiteHelper(this);
	
	ListView prereservationList;
	
	PreBooking currentPreBooking;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_restaurant);
		
		prereservationList = (ListView) findViewById(R.id.prereservation_restaurant_prereservationList);
		//TODO Remplir la liste de restaurants
		
		prereservationList.setOnItemClickListener(prereservationListListener);
	}
	
	private OnItemClickListener prereservationListListener = new OnItemClickListener(){
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			currentPreBooking = (PreBooking) prereservationList.getSelectedItem();
		}
		
	};
}
