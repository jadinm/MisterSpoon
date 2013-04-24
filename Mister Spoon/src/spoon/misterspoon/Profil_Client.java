package spoon.misterspoon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Profil_Client extends Activity {

	private Client c;
	private MySQLiteHelper sqliteHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//We get the intent sent by Login
		Intent i = getIntent();
		//We take the informations about the person who's logged (!!!! label)
		String emailClient = i.getStringExtra(Login.email);
		//We create the object Client associated with this email and all his informations
		sqliteHelper = new MySQLiteHelper(this);
		c = new Client (sqliteHelper, email);
		
		//We can now define all the listeners
	}
	
}
