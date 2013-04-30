package spoon.misterspoon;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Login extends Activity {
	
	Button themeD = null;
	Button themeL = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		themeD = (Button)findViewById(R.id.activity_login_dark);
		themeL = (Button)findViewById(R.id.activity_login_light);
		themeD.setOnClickListener(themeDListener);
		themeL.setOnClickListener(themeLListener);
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

}
