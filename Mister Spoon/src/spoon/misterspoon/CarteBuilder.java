package spoon.misterspoon;

import static android.provider.BaseColumns._ID;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;

public class CarteBuilder {
	
	MySQLiteHelper sqliteHelper;
	RestaurantOwner restaurantOwner;
	
	public CarteBuilder(MySQLiteHelper sqliteHelper) {
		this.sqliteHelper = sqliteHelper;
		
	}
	
	public void createMenu(String menuName, ArrayList <Meal> mealList, double price, String categorie) {
		
	}
	
	public void setMenuName(Menu menuToChange, String name) {
		if ( menuToChange == null) {
			return;
		}
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Menu + " SET " + MySQLiteHelper.Menu_column[1] + " = " + name + " WHERE " + MySQLiteHelper.Menu_column[1] + " = " + email + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Menu + " SET " + MySQLiteHelper.Menu_column[2] + " = " + name + " WHERE " + MySQLiteHelper.Menu_column[2] + " = " + email + " ;");
		db.close();
		
		this.gsm = gsm;
	}//yg
	
	public static final String TABLE_Menu= "Menu";
	public static final String[] Menu_column = new String[]{_ID, "menuNom", "categorie", "restNom", "platNom"};
	
	
	public static final String TABLE_Client= "Client";
	public static final String[] Client_column = new String[]{_ID, "email", "nom", "gsm"};

	
	public void setMenuPrice(Menu menuToChange, double price) {
		
	}
	
	public void setMenuCategorie(Menu menuToChange, String categorie) {
		
	}
	
	public void addMenuMeal(Menu menuToChange, Meal meal) {
		
	}
	
	public void removeMenuMeal(Menu menuToChnage, String mealName) {
		
	}
	
	public void removeMenu(Menu menu) {
		
	}

}