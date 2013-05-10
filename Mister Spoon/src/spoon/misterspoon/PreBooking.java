package spoon.misterspoon;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PreBooking {
	
	protected ArrayList <Meal> commande;
	protected Restaurant restaurant;
	protected Client client;
	
	public PreBooking(Restaurant restaurant, ArrayList <Meal> commande) {//Constructor for the client
		this.commande = commande;
		this.restaurant = restaurant;
	}
	
	public PreBooking(Client client, ArrayList <Meal> commande) {//Constructor for the restaurantOwner
		this.commande = commande;
		this.client = client;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	public ArrayList <Meal> getCommande() {
		return commande;
	}
	
	public Client getClient() {
		return client;
	}
	
	public static int nombrePlat(MySQLiteHelper sqliteHelper, String mealName, String clientMail) {
		int plat = 0;
		SQLiteDatabase db = sqliteHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Order_column[5] + " FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[2] + " = " + "'"+clientMail+"'"+  " AND " + MySQLiteHelper.Order_column[4] + " = " + "'"+mealName+"'", null);
		if (cursor.moveToFirst()) {
			plat = Integer.parseInt(cursor.getString(0));
		}
		return plat;
	}
	
}
