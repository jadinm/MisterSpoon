package spoon.misterspoon;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Booking extends PreBooking {

	private Time heureReservation;
	private int nombrePlaces;
	private Date date;
	MySQLiteHelper sqliteHelper;

	public Booking(Restaurant restaurant, int nombrePlaces, Time heureReservation, Date date, ArrayList<Meal> commande) {//Constructor for the client
		super (restaurant, commande);
		this.heureReservation = heureReservation;
		this.nombrePlaces     = nombrePlaces;
		this.date = date;


	}

	public Booking(Client client, int nombrePlaces, Time heureReservation, Date date, ArrayList<Meal> commande) {//Constructor for the restaurantOwner
		super (client, commande);
		this.heureReservation = heureReservation;
		this.nombrePlaces     = nombrePlaces;
		this.date = date;
	}

	public Time getHeureReservation() {
		return this.heureReservation;
	}

	public int getNombrePlaces() {
		return this.nombrePlaces;
	}

	public Date getDate() {
		return this.date;
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