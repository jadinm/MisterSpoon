package spoon.misterspoon;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Client {
	
	private SQLiteDatabase db;
	private String email;
	
	private String gsm;//data avaible in the database
	private String name;
	private ArrayList <Restaurant> restFav = new ArrayList <Restaurant> ();
	private ArrayList <String> specificite = new ArrayList <String> ();
	private ArrayList <String> allergie = new ArrayList <String> ();
	private ArrayList <Meal> platFav = new ArrayList <Meal> ();
	private ArrayList <PreBooking> preBooking = new ArrayList <PreBooking> ();
	private ArrayList <Booking> booking = new ArrayList <Booking> ();
	
	private GPS position;//data actualized by the program
	private Restaurant restaurantEnCours;
	
	
	//Rajouter un constructeur pour la cas où le restaurant Owner se connecte !!!!!!!
	
	/*
	 * @param : sqliteHelper and email are initialized
	 * @post : Create an object Client and initialized all the instance variable
	 * except position and restaurantEnCours.
	 */
	public Client (MySQLiteHelper sqliteHelper, String email) {
		
		this.db = sqliteHelper.getReadableDatabase();
		this.email = email;
		
		//GSM
		Cursor cursor = db.rawQuery("SELECT INTO " + MySQLiteHelper.Client_column[3] + " FROM " + MySQLiteHelper.TABLE_Client + " WHERE " + MySQLiteHelper.Client_column[1] + "=" + email, null);
		if (cursor.moveToFirst()) {//If the information exists
			this.gsm = cursor.getString(0);
		}
		
		//GSM
		cursor = db.rawQuery("SELECT INTO " + MySQLiteHelper.Client_column[2] + " FROM " + MySQLiteHelper.TABLE_Client + " WHERE " + MySQLiteHelper.Client_column[1] + "=" + email, null);
		if (cursor.moveToFirst()) {//If the information exists
			this.name = cursor.getString(0);
		}
		
		//restFav
		cursor = db.rawQuery("SELECT INTO " + MySQLiteHelper.FavouriteRestaurant_column[3] + " FROM " + MySQLiteHelper.TABLE_FavouriteRestaurant + " WHERE " + MySQLiteHelper.FavouriteRestaurant_column[1] + "=" + email, null);
		if (cursor.moveToFirst()) {//If the information exists
			while (!cursor.isAfterLast()) {//As long as there is one element to read
				restFav.add(new Restaurant (cursor.getString(0)));
				cursor.moveToNext();
			}
		}
		
		//specificite
		cursor = db.rawQuery("SELECT INTO " + MySQLiteHelper.Specificity_column[2] + " FROM " + MySQLiteHelper.TABLE_Specificity + " WHERE " + MySQLiteHelper.Specificity_column[1] + "=" + email, null);
		if (cursor.moveToFirst()) {//If the information exists
			while (!cursor.isAfterLast()) {//As long as there is one element to read
				specificite.add(cursor.getString(0));
			}
		}
		
		//allergie
		cursor = db.rawQuery("SELECT INTO " + MySQLiteHelper.Allergy_column[2] + " FROM " + MySQLiteHelper.TABLE_Allergy + " WHERE " + MySQLiteHelper.Allergy_column[1] + "=" + email, null);
		if (cursor.moveToFirst()) {//If the information exists
			while (!cursor.isAfterLast()) {//As long as there is one element to read
				allergie.add(cursor.getString(0));
				cursor.moveToNext();
			}
		}
		
		//platFav
		cursor = db.rawQuery("SELECT INTO " + MySQLiteHelper.FavouriteMeal_column[2] + " FROM " + MySQLiteHelper.TABLE_FavouriteMeal + " WHERE " + MySQLiteHelper.FavouriteMeal_column[1] + "=" + email, null);
		if (cursor.moveToFirst()) {//If the information exists
			while (!cursor.isAfterLast()) {//As long as there is one element to read
				platFav.add(new Meal(cursor.getString(0)));
				cursor.moveToNext();
			}
		}
		
		//preBooking
		cursor = db.rawQuery("SELECT INTO " + MySQLiteHelper.Order_column[1] + ", " + MySQLiteHelper.Order_column[4] + ", " + MySQLiteHelper.Order_column[5] + " FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[2] + "=" + email + " GROUP BY " + MySQLiteHelper.Order_column[1], null);
		if (cursor.moveToFirst()) {//If the information exists
			ArrayList <Meal> Commande;
			ArrayList <Integer> Quantite;
			while (!cursor.isAfterLast()) {//As long as there is one element to read
				String currentResto = cursor.getString(0);
				Commande = new ArrayList <Meal> ();
				while(!cursor.isAfterLast() && currentResto == cursor.getString(0)) {
					Commande.add(new Meal (cursor.getString(1), cursor.getInt(2)));//We stock the quantity of the meal with the instance variable "stock"
					cursor.moveToNext();
				}
				preBooking.add(new preBooking(currentResto, Commande, Quantite));
			}
		}
		
		//Booking
		cursor = db.rawQuery("SELECT INTO B." + MySQLiteHelper.Booking_column[1] + ", B." + MySQLiteHelper.Booking_column[3] + ", B." + MySQLiteHelper.Booking_column[4] + ", O." + MySQLiteHelper.Order_column[4] + ", O." + MySQLiteHelper.Order_column[5] + " FROM " + MySQLiteHelper.TABLE_Booking + " B, " + MySQLiteHelper.TABLE_Order + " O WHERE O." + MySQLiteHelper.Order_column[2] + "=" + email + " AND B." + MySQLiteHelper.Booking_column + "=" + email + " AND B." + MySQLiteHelper.Booking_column[1] + " = O." + MySQLiteHelper.Order_column[1] + " AND B." + MySQLiteHelper.Booking_column[4] + " = O." + MySQLiteHelper.Order_column[3] + " GROUP BY " + MySQLiteHelper.Order_column[1], null);
		if (cursor.moveToFirst()) {//If the information exists
			ArrayList <Meal> Commande;
			ArrayList <Integer> Quantite;
			while (!cursor.isAfterLast()) {//As long as there is one element to read
				
				String currentResto = cursor.getString(0);
				String time = cursor.getString(2);
				
				Commande = new ArrayList <Meal> ();
				Quantite = new ArrayList <Integer> ();
				while(!cursor.isAfterLast() && currentResto == cursor.getString(0)) {//We take a command if it exists
					if(cursor.getString(3)!=null) {
						Commande.add(new Meal (cursor.getString(3), cursor.getInt(4)));//We stock the quantity of the meal with the instance variable "stock"
						cursor.moveToNext();
					}
				}
				cursor.moveToNext();
				
				booking.add(new Booking(currentResto, new Time(time) , Commande, Quantite));
			}
		}
		
		this.db.close();//We don't forget to close the database
		
	}
	
	public String getEmail () {
		
	}
	
	public boolean setEmail (String email) {
		
	}
	
	public GPS getPosition (boolean updateData) {
		
	}
	
	public String getGsm (boolean getFromDatabase) {
		
	}
	
	public boolean setGsm (String gsm) {
		
	}
	
	public String getName (boolean getFromDatabase) {
		
	}
	
	public void setName (String name) {
		
	}
	
	public ArrayList <Restaurant> getestFav (boolean getFromDatabase) {
		
	}
	
	public void addRestFav (Restaurant restaurant) {
		
	}
	
	public void removeRestFav (Restaurant restaurant) {
		
	}
	
	public ArrayList <String> getSpecificite (boolean getFromDatabase) {
		
	}
	
	public void addSpecificite (String specificite) {
		
	}
	
	public boolean removeSpecificite (String specificite) {
		
	}
	
	public ArrayList <String> getAllergie (boolean getFromDatabase) {
		
	}
	
	public void addAllergie (String allergie) {
		
	}
	
	public boolean removeAllergie (String allergie) {
		
	}
	
	public ArrayList <Meal> getPlatFav (boolean getFromDatabase) {
		
	}
	
	public void addPlatFav (Meal platFav) {
		
	}
	
	public boolean removePlatFav (Meal platFav) {
		
	}
	
	public ArrayList <PreBooking> getPreBooking (boolean getFromDatabase) {
		
	}
	
	public void addPreBooking (PreBooking preBooking) {
		
	}
	
	public boolean removePreBooking (PreBooking preBooking) {
		
	}
	
	public boolean isPreReservationPossible (PreBooking prebooking) {
		
	}
	
	public boolean preBook (ArrayList <Meal> commande) {
		
	}
	
	public ArrayList <Booking> getBooking (boolean getFromDatabase) {
		
	}
	
	public void addPreBooking (Booking booking) {
		
	}
	
	public boolean removeBooking (Booking booking) {
		
	}
	
	public boolean isReservationPossible (Booking booking) {
		
	}
	
	public boolean book (ArrayList <Meal> commande, Time time) {
		
	}
	

}
