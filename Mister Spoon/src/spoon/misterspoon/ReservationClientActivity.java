package spoon.misterspoon;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;


public class ReservationClientActivity extends Activity {
	
	private static final int DATE_DIALOG_ID = 3;
	private Restaurant r;
	private Client c;
	private MySQLiteHelper sqliteHelper = new MySQLiteHelper(this);
	String restoName;
	String emailPerso;
	
	private Button book;
	private TextView date;
	
    private Calendar cal;
    int mYear;
    int mMonth;
    int mDay;
	
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

		book.setOnClickListener(new View.OnClickListener() {//launch the dialog
			
			public void onClick(View v) 
            {
                showDialog(DATE_DIALOG_ID);
            }
        });
		
        cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        updateDisplay();
	}
	
	protected Dialog onCreateDialog(int id) 
	{
	        switch (id) 
	        {
	                case DATE_DIALOG_ID:
	                return new DatePickerDialog(this,
	                            mDateSetListener,
	                            mYear, mMonth, mDay);
	        }
	        return null;
	}
	private void updateDisplay() {
		date.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("-")
                    .append(mDay).append("-")
                    .append(mYear).append(" ")
                    );
    }
	
	private DatePickerDialog.OnDateSetListener mDateSetListener =
			new DatePickerDialog.OnDateSetListener() {

		         public void onDateSet(DatePicker view, int year, int monthOfYear,
		                 int dayOfMonth) {
		             mYear = year;
		             mMonth = monthOfYear;
		             mDay = dayOfMonth;
		             updateDisplay();
		         }
	};
}

