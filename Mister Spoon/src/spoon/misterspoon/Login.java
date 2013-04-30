package spoon.misterspoon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class Login extends Activity {
	
	Context context = this;
	MySQLiteHelper sql = new MySQLiteHelper(this);
	
	static String email = "LOGIN_EMAIL";
	
	Button themeD = null;
	Button themeL = null;
	
	EditText email_login;
	Button login;
	
	RadioGroup resto_client;
	RadioButton rad_resto;
	
	EditText nom_register;
	EditText email_register;
	EditText phone_register;
	EditText gps_register;
	EditText capacite_register;
	
	Button register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		themeD = (Button)findViewById(R.id.activity_login_dark);
		themeL = (Button)findViewById(R.id.activity_login_light);
		
		email_login = (EditText) findViewById(R.id.email);
		login = (Button) findViewById(R.id.butlogin);
		
		resto_client = (RadioGroup) findViewById(R.id.radGrpRC);
		rad_resto = (RadioButton) findViewById(R.id.rad_resto);
		
		nom_register = (EditText) findViewById(R.id.edit_name);
		email_register = (EditText) findViewById(R.id.edit_mail);
		phone_register = (EditText) findViewById(R.id.edit_phone);
		gps_register = (EditText) findViewById(R.id.edit_gps);
		capacite_register = (EditText) findViewById(R.id.edit_capa);
		
		register = (Button) findViewById(R.id.register);
		
		
		
		
		themeD.setOnClickListener(themeDListener);
		themeL.setOnClickListener(themeLListener);
		
		login.setOnClickListener(loginListener);
		
		resto_client.setOnCheckedChangeListener(radioGroupListener);
		
		register.setOnClickListener(registerListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	
	//C'est la partie pour le changement de theme
	
	private OnClickListener themeLListener = new OnClickListener() { @Override
		public void onClick(View v) {
		Utils.changeToTheme(Login.this, Utils.THEME_DEFAULT); 
	}
	};

	private OnClickListener themeDListener = new OnClickListener() { @Override
		public void onClick(View v) {
		Utils.changeToTheme(Login.this, Utils.THEME_DARK); 
	}
	};
	
	private OnClickListener loginListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (email_login.toString()==null) {
				Toast toast = Toast.makeText(context, getString(R.string.activity_login_toast), Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			if (Client.isInDatabase(sql, email_login.toString())) {
				Intent i = new Intent(Login.this, Profil_Client.class);
				i.putExtra(email, email_login.toString());
				startActivity(i);
				
			}
			else if (Restaurant.isInDatabase(sql, email_login.toString())) {
				Intent i = new Intent(Login.this, Profil_Restaurant.class);
				i.putExtra(email, email_login.toString());
				startActivity(i);
				return;
			}
			else {
				Toast toast = Toast.makeText(context, getString(R.string.activity_login_toast_incorrect), Toast.LENGTH_SHORT);
				toast.show();
			}
			
		}
	};
	
	private OnCheckedChangeListener radioGroupListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (rad_resto.isChecked()) {//We show the editText for the restaurant
				phone_register.setVisibility(0);
				gps_register.setVisibility(0);
				capacite_register.setVisibility(0);
			}
			else {
				phone_register.setVisibility(4);
				gps_register.setVisibility(4);
				capacite_register.setVisibility(4);
			}
		}
	};
	
	private OnClickListener registerListener = new OnClickListener() { 
		@Override
		public void onClick(View v) {
			if (email_register.toString()==null || nom_register.toString()==null) {
				Toast toast = Toast.makeText(context, getString(R.string.activity_register_toast_empty), Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			boolean isInDatabase = Client.isInDatabase(sql, email_register.toString());
			if (rad_resto.isChecked() && !isInDatabase) {
				Intent i = new Intent(Login.this, Profil_Client.class);
				i.putExtra(email, email_login.toString());
				startActivity(i);
				return;
				
			}
			int InDatabase = Restaurant.isInDatabase(sql, email_register.toString(), nom_register.toString(), gps_register.toString(), phone_register.toString());
			
			if (!rad_resto.isChecked() && gps_register.toString()==null && capacite_register.toString()==null  && InDatabase==0) {
				Restaurant.createRestaurant(sql, email_register.toString(), nom_register.toString(), gps_register.toString(), phone_register.toString(), Integer.parseInt(capacite_register.toString()));
				Intent i = new Intent(Login.this, Profil_Restaurant.class);
				i.putExtra(email, email_login.toString());
				startActivity(i);
				return;
			}
			Toast toast;
			switch (InDatabase) {
			
			case 1:
				
				toast = Toast.makeText(context, getString(R.string.activity_register_same_email), Toast.LENGTH_SHORT);
				toast.show();
				return;
				break;
			case 2:
				
				toast = Toast.makeText(context, getString(R.string.activity_register_same_name), Toast.LENGTH_SHORT);
				toast.show();
				return;
				break;
			case 3:
				
				toast = Toast.makeText(context, getString(R.string.activity_register_same_gps), Toast.LENGTH_SHORT);
				toast.show();
				return;
				break;
			case 4:
				
				toast = Toast.makeText(context, getString(R.string.activity_register_same_phone), Toast.LENGTH_SHORT);
				toast.show();
				return;
				break;
			}
			
			toast = Toast.makeText(context, getString(R.string.activity_register_toast_empty), Toast.LENGTH_SHORT);
			toast.show();
			
		}
	};

}
