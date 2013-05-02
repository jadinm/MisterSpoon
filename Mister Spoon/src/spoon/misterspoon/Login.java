package spoon.misterspoon;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

	private Context context = this;
	MySQLiteHelper sql = new MySQLiteHelper(this);

	static String email = "LOGIN_EMAIL";

	private Button themeD = null;
	private Button themeL = null;

	private EditText email_login;
	private Button login;

	private RadioGroup resto_client;
	private RadioButton rad_resto;

	private EditText nom_register;
	private EditText email_register;
	private EditText phone_register;
	private TextView gps;
	private EditText gps_longitude_register;
	private TextView gps2;
	private EditText gps_latitude_register;
	private EditText numero;
	private EditText rue;
	private EditText ville;
	private EditText capacite_register;

	private Button register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.onActivityCreateSetTheme(this);
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
		gps = (TextView) findViewById(R.id.gps);
		gps_longitude_register = (EditText) findViewById(R.id.edit_gps_longitude);
		gps2 = (TextView) findViewById(R.id.gps2);
		gps_latitude_register = (EditText) findViewById(R.id.edit_gps_latitude);
		numero = (EditText) findViewById(R.id.edit_numero);;
		rue = (EditText) findViewById(R.id.edit_rue);;
		ville = (EditText) findViewById(R.id.edit_ville);;
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
			if (email_login.getText().toString().length()==0) {
				Toast toast = Toast.makeText(context, getString(R.string.activity_login_toast), Toast.LENGTH_SHORT);
				toast.show();
				return;
			}

			if (Client.isInDatabase(sql, email_login.getText().toString())) {
				Intent i = new Intent(Login.this, Profil_Client.class);
				i.putExtra(email, email_login.getText().toString());
				startActivity(i);


			}
			else if (RestaurantOwner.isInDatabase(sql, email_login.getText().toString())) {
				Toast toast = Toast.makeText(context, "Un restaurant vient de se connecter correctement", Toast.LENGTH_SHORT);
				toast.show();

				/*TODO Intent i = new Intent(Login.this, Profil_Restaurant.class);
				i.putExtra(email, email_login.toString());
				startActivity(i);*/
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
				gps.setVisibility(0);
				gps_longitude_register.setVisibility(0);
				gps2.setVisibility(0);
				gps_latitude_register.setVisibility(0);
				numero.setVisibility(0);
				rue.setVisibility(0);
				ville.setVisibility(0);
				capacite_register.setVisibility(0);
			}
			else {//We hide them
				phone_register.setVisibility(4);
				gps.setVisibility(4);
				gps_longitude_register.setVisibility(4);
				gps2.setVisibility(4);
				gps_latitude_register.setVisibility(4);
				numero.setVisibility(4);
				rue.setVisibility(4);
				ville.setVisibility(4);
				capacite_register.setVisibility(4);
			}
		}
	};

	private OnClickListener registerListener = new OnClickListener() { 
		@Override
		public void onClick(View v) {
			if (email_register.getText().toString().length()==0 || nom_register.getText().toString().length()==0) {
				Toast toast = Toast.makeText(context, getString(R.string.activity_register_toast_empty), Toast.LENGTH_SHORT);
				toast.show();
				return;
			}
			if (!rad_resto.isChecked()) {//if we have a Client
				int InDatabase = Client.isInDatabase(sql, email_register.getText().toString(), nom_register.getText().toString());
				if (InDatabase==0) {//If there is no problem
					Client.createClient(sql, email_register.getText().toString(), nom_register.getText().toString());
					Intent i = new Intent(Login.this, Profil_Client.class);
					i.putExtra(email, email_login.getText().toString());
					startActivity(i);
					return;

				}
				Toast toast;
				switch (InDatabase) {//There is one information that already exists

				case 1:

					toast = Toast.makeText(context, getString(R.string.activity_register_same_email), Toast.LENGTH_SHORT);
					toast.show();
					break;
				case 2:

					toast = Toast.makeText(context, getString(R.string.activity_register_same_name), Toast.LENGTH_SHORT);
					toast.show();
					break;
				}
				return;
			}
			else {//If we have a restaurantOwner
				int InDatabase = RestaurantOwner.isInDatabase(sql, email_register.getText().toString(), nom_register.getText().toString(), gps_longitude_register.getText().toString() + "," + gps_latitude_register.getText().toString(), phone_register.getText().toString());

				if (rad_resto.isChecked() && gps_longitude_register.getText().toString().length()!=0 && gps_latitude_register.getText().toString().length()!=0 && rue.getText().toString().length()!=0 && ville.getText().toString().length()!=0 && capacite_register.getText().toString().length()!=0  && InDatabase==0) {
					if (numero.toString().length()!=0) {
						RestaurantOwner.createRestaurant(sql, email_register.getText().toString(), nom_register.getText().toString(), gps_longitude_register.getText().toString() + "," + gps_latitude_register.getText().toString(), Integer.parseInt(numero.getText().toString()), rue.getText().toString(), ville.getText().toString(), phone_register.getText().toString(), Integer.parseInt(capacite_register.getText().toString()));
					}
					else {
						RestaurantOwner.createRestaurant(sql, email_register.getText().toString(), nom_register.getText().toString(), gps_longitude_register.getText().toString() + "," + gps_latitude_register.getText().toString(), 0, rue.getText().toString(), ville.getText().toString(), phone_register.getText().toString(), Integer.parseInt(capacite_register.getText().toString()));
					}
					Toast toast = Toast.makeText(context, "Un restaurant vient de s'enregister correctement", Toast.LENGTH_SHORT);
					toast.show();
					//TODO Intent i = new Intent(Login.this, Profil_Restaurant.class);
					//i.putExtra(email, email_register.toString());
					//startActivity(i);
					return;
				}
				Toast toast;
				switch (InDatabase) {

				case 1:

					toast = Toast.makeText(context, getString(R.string.activity_register_same_email), Toast.LENGTH_SHORT);
					toast.show();
					break;
				case 2:

					toast = Toast.makeText(context, getString(R.string.activity_register_same_name), Toast.LENGTH_SHORT);
					toast.show();
					break;
				case 3:

					toast = Toast.makeText(context, getString(R.string.activity_register_same_gps), Toast.LENGTH_SHORT);
					toast.show();
					break;
				case 4:

					toast = Toast.makeText(context, getString(R.string.activity_register_same_phone), Toast.LENGTH_SHORT);
					toast.show();
					break;
				default:
					toast = Toast.makeText(context, getString(R.string.activity_register_toast_empty), Toast.LENGTH_SHORT);
					toast.show();
				}

			}

		}
	};

}
