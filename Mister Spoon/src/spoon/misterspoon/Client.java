package spoon.misterspoon;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Client {
	
	public MySQLiteHelper sqliteHelper;
	private String email;
	
	private String gsm;//data available in the database
	private String name;
	private ArrayList <Restaurant> restFav = new ArrayList <Restaurant> ();
	private ArrayList <String> specificite = new ArrayList <String> ();
	private ArrayList <String> allergie = new ArrayList <String> ();
	private ArrayList <Meal> platFav = new ArrayList <Meal> ();
	private ArrayList <PreBooking> preBooking = new ArrayList <PreBooking> ();
	private ArrayList <Booking> booking = new ArrayList <Booking> ();
	
	private GPS position;//data actualized by the program
	private Restaurant restaurantEnCours;
	
	
	//Rajouter un constructeur pour la cas ou le restaurant Owner se connecte !!!!!!!
	
	/*
	 * @param : sqliteHelper and email are initialized
	 * @post : Create an object Client and initialized all the instance variable
	 * except position and restaurantEnCours.
	 */
	public Client (MySQLiteHelper sqliteHelper, String email) {
		
		this.sqliteHelper = sqliteHelper;
		SQLiteDatabase db = sqliteHelper.getReadableDatabase();
		this.email = email;
		
		//GSM
		Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Client_column[3] + " FROM " + MySQLiteHelper.TABLE_Client + " WHERE " + MySQLiteHelper.Client_column[1] + "=" + email, null);
		if (cursor.moveToFirst()) {//If the information exists
			this.gsm = cursor.getString(0);
		}
		
		//name
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Client_column[2] + " FROM " + MySQLiteHelper.TABLE_Client + " WHERE " + MySQLiteHelper.Client_column[1] + "=" + email, null);
		if (cursor.moveToFirst()) {//If the information exists
			this.name = cursor.getString(0);
		}
		
		//restFav
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.FavouriteRestaurant_column[3] + " FROM " + MySQLiteHelper.TABLE_FavouriteRestaurant + " WHERE " + MySQLiteHelper.FavouriteRestaurant_column[1] + "=" + email, null);
		if (cursor.moveToFirst()) {//If the information exists
			while (!cursor.isAfterLast()) {//As long as there is one element to read
				restFav.add(new Restaurant (sqliteHelper, cursor.getString(0)));
				cursor.moveToNext();
			}
		}
		
		//specificite
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Specificity_column[2] + " FROM " + MySQLiteHelper.TABLE_Specificity + " WHERE " + MySQLiteHelper.Specificity_column[1] + "=" + email, null);
		if (cursor.moveToFirst()) {//If the information exists
			while (!cursor.isAfterLast()) {//As long as there is one element to read
				specificite.add(cursor.getString(0));
			}
		}
		
		//allergie
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Allergy_column[2] + " FROM " + MySQLiteHelper.TABLE_Allergy + " WHERE " + MySQLiteHelper.Allergy_column[1] + "=" + email, null);
		if (cursor.moveToFirst()) {//If the information exists
			while (!cursor.isAfterLast()) {//As long as there is one element to read
				allergie.add(cursor.getString(0));
				cursor.moveToNext();
			}
		}
		
		//platFav
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.FavouriteMeal_column[2] + " FROM " + MySQLiteHelper.TABLE_FavouriteMeal + " WHERE " + MySQLiteHelper.FavouriteMeal_column[1] + "=" + email, null);
		if (cursor.moveToFirst()) {//If the information exists
			while (!cursor.isAfterLast()) {//As long as there is one element to read
				platFav.add(new Meal(cursor.getString(0)));
				cursor.moveToNext();
			}
		}
		
		//preBooking
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Order_column[1] + ", " + MySQLiteHelper.Order_column[4] + ", " + MySQLiteHelper.Order_column[5] + " FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[2] + "=" + email + " GROUP BY " + MySQLiteHelper.Order_column[1], null);
		if (cursor.moveToFirst()) {//If the information exists
			ArrayList <Meal> Commande;
			while (!cursor.isAfterLast()) {//As long as there is one element to read
				String currentResto = cursor.getString(0);
				Commande = new ArrayList <Meal> ();
				while(!cursor.isAfterLast() && currentResto == cursor.getString(0)) {
					Commande.add(new Meal (cursor.getString(1), cursor.getInt(2)));//We stock the quantity of the meal with the instance variable "stock"
					cursor.moveToNext();
				}
				preBooking.add(new PreBooking(currentResto, Commande));
			}
		}
		
		//Booking
		cursor = db.rawQuery("SELECT B." + MySQLiteHelper.Booking_column[1] + ", B." + MySQLiteHelper.Booking_column[3] + ", B." + MySQLiteHelper.Booking_column[4] + ", O." + MySQLiteHelper.Order_column[4] + ", O." + MySQLiteHelper.Order_column[5] + " FROM " + MySQLiteHelper.TABLE_Booking + " B, " + MySQLiteHelper.TABLE_Order + " O WHERE O." + MySQLiteHelper.Order_column[2] + "=" + email + " AND B." + MySQLiteHelper.Booking_column + "=" + email + " AND B." + MySQLiteHelper.Booking_column[1] + " = O." + MySQLiteHelper.Order_column[1] + " AND B." + MySQLiteHelper.Booking_column[4] + " = O." + MySQLiteHelper.Order_column[3] + " GROUP BY " + MySQLiteHelper.Order_column[1], null);
		if (cursor.moveToFirst()) {//If the information exists
			ArrayList <Meal> Commande;
			while (!cursor.isAfterLast()) {//As long as there is one element to read
				
				String currentResto = cursor.getString(0);
				String time = cursor.getString(2);
				
				Commande = new ArrayList <Meal> ();
				while(!cursor.isAfterLast() && currentResto == cursor.getString(0)) {//We take a command if it exists
					if(cursor.getString(3)!=null) {
						Commande.add(new Meal (cursor.getString(3), cursor.getInt(4)));//We stock the quantity of the meal with the instance variable "stock"
						cursor.moveToNext();
					}
				}
				cursor.moveToNext();
				
				booking.add(new Booking(currentResto, new Time(time) , Commande));
			}
		}
		
		this.db.close();//We don't forget to close the database
		
	}
	
	/*
	 * @post : return the email of the client
	 */
	public String getEmail () {
		return this.email;
	}
	
	/* 
	 * @param : email is not null and not already in the database
	 * @post : try to change the database and return true or false
	 * if it's a success or not
	 */
	public boolean setEmail (String email) {
		
		if(email==null) {
			return false;
		}
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		//Test
		Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Client_column[1] + " FROM " + MySQLiteHelper.TABLE_Client + " WHERE " + MySQLiteHelper.Client_column[1] + "=" + email, null);
		if (cursor.moveToFirst()) {//If the email already exists
			db.close();
			return false;
		}
		
		// We modify the database
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Client + " SET " + MySQLiteHelper.Client_column[1] + " = " + email + " WHERE " + MySQLiteHelper.Client_column[1] + " = " + email + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Client + " SET " + MySQLiteHelper.Client_column[1] + " = " + email + " WHERE " + MySQLiteHelper.Client_column[1] + " = " + email + " ;");
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_FavouriteMeal + " SET " + MySQLiteHelper.FavouriteMeal_column[1] + " = " + email + " WHERE " + MySQLiteHelper.FavouriteMeal_column[1] + " = " + email + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_FavouriteMeal + " SET " + MySQLiteHelper.FavouriteMeal_column[1] + " = " + email + " WHERE " + MySQLiteHelper.FavouriteMeal_column[1] + " = " + email + " ;");
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Booking + " SET " + MySQLiteHelper.Booking_column[2] + " = " + email + " WHERE " + MySQLiteHelper.Booking_column[2] + " = " + email + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Booking + " SET " + MySQLiteHelper.Booking_column[2] + " = " + email + " WHERE " + MySQLiteHelper.Booking_column[2] + " = " + email + " ;");
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Order + " SET " + MySQLiteHelper.Order_column[2] + " = " + email + " WHERE " + MySQLiteHelper.Order_column[2] + " = " + email + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Order + " SET " + MySQLiteHelper.Order_column[2] + " = " + email + " WHERE " + MySQLiteHelper.Order_column[2] + " = " + email + " ;");
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_FavouriteRestaurant + " SET " + MySQLiteHelper.FavouriteRestaurant_column[1] + " = " + email + " WHERE " + MySQLiteHelper.FavouriteRestaurant_column[1] + " = " + email + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_FavouriteRestaurant + " SET " + MySQLiteHelper.FavouriteRestaurant_column[1] + " = " + email + " WHERE " + MySQLiteHelper.FavouriteRestaurant_column[1] + " = " + email + " ;");
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Specificity + " SET " + MySQLiteHelper.Specificity_column[1] + " = " + email + " WHERE " + MySQLiteHelper.Specificity_column[1] + " = " + email + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Specificity + " SET " + MySQLiteHelper.Specificity_column[1] + " = " + email + " WHERE " + MySQLiteHelper.Specificity_column[1] + " = " + email + " ;");
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Allergy + " SET " + MySQLiteHelper.Allergy_column[1] + " = " + email + " WHERE " + MySQLiteHelper.Allergy_column[1] + " = " + email + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Allergy + " SET " + MySQLiteHelper.Allergy_column[1] + " = " + email + " WHERE " + MySQLiteHelper.Allergy_column[1] + " = " + email + " ;");
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_MealSpecificity + " SET " + MySQLiteHelper.MealSpecificity_column[1] + " = " + email + " WHERE " + MySQLiteHelper.MealSpecificity_column[1] + " = " + email + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_MealSpecificity + " SET " + MySQLiteHelper.MealSpecificity_column[1] + " = " + email + " WHERE " + MySQLiteHelper.MealSpecificity_column[1] + " = " + email + " ;");
		
		db.close();
		this.email = email;
		return true;
	}
	
	/*
	 * @post : if updateData is true, we will ask to the Class GPS to get the new coordinates
	 * (and set the current value of 'position')
	 * else, we will return the current value of 'position'
	 */
	public GPS getPosition (boolean updateData) {
		
		if (updateData) {
			position = updatePosition(this);
		}
		
		return position;
	}
	
	/*
	 * @post : return the value of the instance variable 'restaurantEnCours'
	 */
	public Restaurant getRestaurantEnCours () {
		
		return restaurantEnCours;
	}
	
	/*
	 * @param : restaurant is not null
	 * @post : the instance variable, 'restaurantEnCours', is set to the value restaurant
	 */
	public void setRestaurantEnCours (Restaurant restaurant) {
		
		if (restaurant != null) {
			restaurantEnCours = restaurant;
		}
	}
	
	/*
	 * @post : if getFormDatabase is true, we will get the information from the database (and set 'gsm')
	 * else, we will return the current value of 'gsm'
	 */
	public String getGsm (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Client_column[3] + " FROM " + MySQLiteHelper.TABLE_Client + " WHERE " + MySQLiteHelper.Client_column[1] + "=" + email,null);
			if (cursor.moveToFirst()) {
				gsm = cursor.getString(0);
			}
			
			db.close();
		}
		
		return gsm;
	}
	
	/*
	 * @post : The instance variable, gsm, will be set to the value of the parameter
	 * (if the parameter, gsm, is null, the value in the database and the instance variable are set to null too)
	 */
	public void setGsm (String gsm) {
		
		if (gsm.equals(this.gsm)) {
			return;
		}
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		if (gsm == null) {
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Client + " SET " + MySQLiteHelper.Client_column[3] + " = " + "NULL" + " WHERE " + MySQLiteHelper.Client_column[1] + " = " + email + " ;");
			db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Client + " SET " + MySQLiteHelper.Client_column[3] + " = " + "NULL" + " WHERE " + MySQLiteHelper.Client_column[1] + " = " + email + " ;");
		}
		else {
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Client + " SET " + MySQLiteHelper.Client_column[3] + " = " + gsm + " WHERE " + MySQLiteHelper.Client_column[1] + " = " + email + " ;");
			db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Client + " SET " + MySQLiteHelper.Client_column[3] + " = " + gsm + " WHERE " + MySQLiteHelper.Client_column[1] + " = " + email + " ;");
		}
		
		db.close();
		
		this.gsm = gsm;
		
	}
	
	/*
	 * @post : if getFormDatabase is true, we will get the information from the database (and set 'name')
	 * else, we will return the current value of 'name'
	 */
	public String getName (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Client_column[2] + " FROM " + MySQLiteHelper.TABLE_Client + " WHERE " + MySQLiteHelper.Client_column[1] + "=" + email,null);
			if (cursor.moveToFirst()) {
				name = cursor.getString(0);
			}
			
			db.close();
		}
		
		return name;
	}
	
	/*
	 * @post : The instance variable, name, will be set to the value of the parameter
	 * (if the parameter, name, is null, the function return false because we have to have a name)
	 */
	public boolean setName (String name) {
		
		if (name.equals(this.name)) {
			return true;
		}
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		if (name == null) {
			return false;
		}
		else {
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Client + " SET " + MySQLiteHelper.Client_column[2] + " = " + gsm + " WHERE " + MySQLiteHelper.Client_column[1] + " = " + email + " ;");
			db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Client + " SET " + MySQLiteHelper.Client_column[2] + " = " + gsm + " WHERE " + MySQLiteHelper.Client_column[1] + " = " + email + " ;");
		}
		
		db.close();
		
		this.name = name;
		
		return false;
	}
	
	/*
	 * @post : If getFormDatabase is true, we will get the information from the database (and set 'restFav')
	 * else, we will return the current value of 'restFav'
	 */
	public ArrayList <Restaurant> getRestFav (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			restFav.clear();//We remove all the elements
			
			Cursor cursor = db.rawQuery("SELECT" + MySQLiteHelper.FavouriteRestaurant_column[3] + " FROM " + MySQLiteHelper.TABLE_FavouriteRestaurant + " WHERE " + MySQLiteHelper.FavouriteRestaurant_column[1] + "=" + email, null);
			if (cursor.moveToFirst()) {//If the information exists
				while (!cursor.isAfterLast()) {//As long as there is one element to read
					restFav.add(new Restaurant (sqliteHelper, cursor.getString(0)));
					cursor.moveToNext();
				}
			}
			
			db.close();
		}
		
		return restFav;
	}
	
	/*
	 * @post : Add the element to the list 'restFav' (on the database and on the instance variable)
	 * If the restaurant is null, the restaurant isn't added to the list
	 * If the restaurant is already in the list, it isn't added to the list
	 */
	public void addRestFav (Restaurant restaurant) {
		
		if (restaurant == null) {//If the restaurant is null
			return ;
		}
		
		for (int i; i<restFav.size(); i++) {
			if ((restaurant.getRestaurantName()).equals((restFav.get(i)).getRestaurantName())) {//If the restaurant is already in the list
				return ;
			}
		}
		
		restFav.add(restaurant);
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_FavouriteRestaurant + "(" + MySQLiteHelper.FavouriteRestaurant_column[1] + ", "  + MySQLiteHelper.FavouriteRestaurant_column[2] + ") VALUES (" + email + ", " + restaurant.getRestaurantName() + ");");
		MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_FavouriteRestaurant + "(" + MySQLiteHelper.FavouriteRestaurant_column[1] + ", "  + MySQLiteHelper.FavouriteRestaurant_column[2] + ") VALUES (" + email + ", " + restaurant.getRestaurantName() + ");");
		
		db.close();
	}
	
	/*
	 * @post : Remove the element to the list 'restFav' (on the database and on the instance variable)
	 * If the restaurant is null, nothing happens
	 * If the restaurant is not in the list, nothing happens too
	 */
	public void removeRestFav (Restaurant restaurant) {
		
		if (restaurant == null) {//If the restaurant is null
			return ;
		}
		
		boolean found = false;
		int index;
		
		for (int i = 0; i<restFav.size() && !found; i++) {
			if ((restaurant.getRestaurantName()).equals((restFav.get(i)).getRestaurantName())) {//If the restaurant is in the list
				found = true;
				index = i;
			}
		}
		
		if (!found) {//If the restaurant is not in the list
			return ;
		}
		
		restFav.remove(index);
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_FavouriteRestaurant + " WHERE " + MySQLiteHelper.FavouriteRestaurant_column[2] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.FavouriteRestaurant_column[1] + " = " + email + ";");
		MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_FavouriteRestaurant + " WHERE " + MySQLiteHelper.FavouriteRestaurant_column[2] + " = " + restaurant.getRestaurantName() + " AND " + MySQLiteHelper.FavouriteRestaurant_column[1] + " = " + email + ";");
		
		db.close();
		
	}
	
	/*
	 * @post : If getFormDatabase is true, we will get the information from the database (and set 'specificite')
	 * else, we will return the current value of 'specificite'
	 */
	public ArrayList <String> getSpecificite (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			specificite.clear();//We remove all the elements
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Specificity_column[2] + " FROM " + MySQLiteHelper.TABLE_Specificity + " WHERE " + MySQLiteHelper.Specificity_column[1] + "=" + email, null);
			if (cursor.moveToFirst()) {//If the information exists
				while (!cursor.isAfterLast()) {//As long as there is one element to read
					specificite.add(cursor.getString(0));
					cursor.moveToNext();
				}
			}
			
			db.close();
		}
		
		return specificite;
	}
	
	/*
	 * @post : Add the element to the list 'specificite' (on the database and on the instance variable)
	 * If the string is null, the string isn't added to the list
	 * If the string is already in the list, it isn't added to the list
	 */
	public void addSpecificite (String specificite) {
		
		if (specificite == null) {//If the parameter is null
			return ;
		}
		
		for (int i; i<this.specificite.size(); i++) {
			if ((this.specificite.get(i)).equals(specificite)) {//If the parameter is already in the list
				return ;
			}
		}
		
		this.specificite.add(specificite);
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Specificity + "(" + MySQLiteHelper.Specificity_column[1] + ", "  + MySQLiteHelper.Specificity_column[2] + ") VALUES (" + email + ", " + specificite + ");");
		MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Specificity + "(" + MySQLiteHelper.Specificity_column[1] + ", "  + MySQLiteHelper.Specificity_column[2] + ") VALUES (" + email + ", " + specificite + ");");
		
		db.close();
	}
	
	/*
	 * @post : Remove the element to the list 'specificite' (on the database and on the instance variable)
	 * If the string is null, nothing happens
	 * If the string is not in the list, nothing happens too
	 */
	public void removeSpecificite (String specificite) {
		
		if (specificite == null) {//If the parameter is null
			return ;
		}
		
		boolean found = false;
		int index;
		
		for (int i = 0; i<this.specificite.size() && !found; i++) {
			if (specificite.equals(this.specificite.get(i))) {//If the parameter is in the list
				found = true;
				index = i;
			}
		}
		
		if (!found) {//If the parameter is not in the list
			return ;
		}
		
		this.specificite.remove(index);
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Specificity + " WHERE " + MySQLiteHelper.Specificity_column[2] + " = " + specificite + " AND " + MySQLiteHelper.Specificity_column[1] + " = " + email + ";");
		MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Specificity + " WHERE " + MySQLiteHelper.Specificity_column[2] + " = " + specificite + " AND " + MySQLiteHelper.Specificity_column[1] + " = " + email + ";");
		
		db.close();
	}
	
	/*
	 * @post : If getFormDatabase is true, we will get the information from the database (and set 'allergie')
	 * else, we will return the current value of 'allergie'
	 */
	public ArrayList <String> getAllergie (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			allergie.clear();//We remove all the elements
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Allergy_column[2] + " FROM " + MySQLiteHelper.TABLE_Allergy + " WHERE " + MySQLiteHelper.Allergy_column[1] + "=" + email, null);
			if (cursor.moveToFirst()) {//If the information exists
				while (!cursor.isAfterLast()) {//As long as there is one element to read
					allergie.add(cursor.getString(0));
					cursor.moveToNext();
				}
			}
			
			db.close();
		}
		
		return allergie;
	}
	
	/*
	 * @post : Add the element to the list 'allergie' (on the database and on the instance variable)
	 * If the string is null, the string isn't added to the list
	 * If the string is already in the list, it isn't added to the list
	 */
	public void addAllergie (String allergie) {
		
		if (allergie == null) {//If the parameter is null
			return ;
		}
		
		for (int i; i<this.allergie.size(); i++) {
			if ((this.allergie.get(i)).equals(allergie)) {//If the parameter is already in the list
				return ;
			}
		}
		
		this.allergie.add(allergie);
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Allergy + "(" + MySQLiteHelper.Allergy_column[1] + ", "  + MySQLiteHelper.Allergy_column[2] + ") VALUES (" + email + ", " + allergie + ");");
		MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Allergy + "(" + MySQLiteHelper.Allergy_column[1] + ", "  + MySQLiteHelper.Allergy_column[2] + ") VALUES (" + email + ", " + allergie + ");");
		
		db.close();
	}
	
	
	/*
	 * @post : Remove the element to the list 'allergie' (on the database and on the instance variable)
	 * If the string is null, nothing happens
	 * If the string is not in the list, nothing happens too
	 */
	public void removeAllergie (String allergie) {
		
		if (allergie == null) {//If the parameter is null
			return ;
		}
		
		boolean found = false;
		int index;
		
		for (int i = 0; i<this.allergie.size() && !found; i++) {
			if (allergie.equals(this.allergie.get(i))) {//If the parameter is in the list
				found = true;
				index = i;
			}
		}
		
		if (!found) {//If the parameter is not in the list
			return ;
		}
		
		this.allergie.remove(index);
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Allergy + " WHERE " + MySQLiteHelper.Allergy_column[2] + " = " + allergie + " AND " + MySQLiteHelper.Allergy_column[1] + " = " + email + ";");
		MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Allergy + " WHERE " + MySQLiteHelper.Allergy_column[2] + " = " + allergie + " AND " + MySQLiteHelper.Allergy_column[1] + " = " + email + ";");
		
		db.close();
	}
	
	/*
	 * @post : If getFormDatabase is true, we will get the information from the database (and set 'platFav')
	 * else, we will return the current value of 'platFav'
	 */
	public ArrayList <Meal> getPlatFav (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			platFav.clear();//We remove all the elements
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.FavouriteMeal_column[2] + " FROM " + MySQLiteHelper.TABLE_FavouriteMeal + " WHERE " + MySQLiteHelper.FavouriteMeal_column[1] + "=" + email, null);
			if (cursor.moveToFirst()) {//If the information exists
				while (!cursor.isAfterLast()) {//As long as there is one element to read
					allergie.add(new Meal (cursor.getString(0)));
					cursor.moveToNext();
				}
			}
			
			db.close();
		}
		
		return platFav;
	}
	
	/*
	 * @post : Add the element to the list 'platFav' (on the database and on the instance variable)
	 * If the meal is null, the meal isn't added to the list
	 * If the meal is already in the list, it isn't added to the list
	 */
	public void addPlatFav (Meal platFav) {
		
		if (platFav == null) {//If the parameter is null
			return ;
		}
		
		for (int i; i<this.platFav.size(); i++) {
			if (((this.platFav.get(i)).getMealName()).equals(platFav.getMealName())) {//If the parameter is already in the list
				return ;
			}
		}
		
		this.platFav.add(platFav);
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_FavouriteMeal + "(" + MySQLiteHelper.FavouriteMeal_column[1] + ", "  + MySQLiteHelper.FavouriteMeal_column[2] + ") VALUES (" + email + ", " + platFav.getMealName() + ");");
		MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_FavouriteMeal + "(" + MySQLiteHelper.FavouriteMeal_column[1] + ", "  + MySQLiteHelper.FavouriteMeal_column[2] + ") VALUES (" + email + ", " + platFav.getMealName() + ");");
		
		db.close();
	}
	
	/*
	 * @post : Remove the element to the list 'platFav' (on the database and on the instance variable)
	 * If the meal is null, nothing happens
	 * If the meal is not in the list, nothing happens too
	 */
	public void removePlatFav (Meal platFav) {
		
		if (platFav == null) {//If the parameter is null
			return ;
		}
		
		boolean found = false;
		int index;
		
		for (int i = 0; i<this.platFav.size() && !found; i++) {
			if ((platFav.getMealName()).equals((this.platFav.get(i).getMealName()))) {//If the parameter is in the list
				found = true;
				index = i;
			}
		}
		
		if (!found) {//If the parameter is not in the list
			return ;
		}
		
		this.platFav.remove(index);
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_FavouriteMeal + " WHERE " + MySQLiteHelper.FavouriteMeal_column[2] + " = " + platFav.getMealName() + " AND " + MySQLiteHelper.FavouriteMeal_column[1] + " = " + email + ";");
		MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_FavouriteMeal + " WHERE " + MySQLiteHelper.FavouriteMeal_column[2] + " = " + platFav.getMealName() + " AND " + MySQLiteHelper.FavouriteMeal_column[1] + " = " + email + ";");
		
		db.close();
	}
	
	/*
	 * @post : If getFormDatabase is true, we will get the information from the database (and set 'prebooking')
	 * else, we will return the current value of 'prebooking'
	 */
	public ArrayList <PreBooking> getPreBooking (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			preBooking.clear();//We remove all the elements
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Order_column[1] + ", " + MySQLiteHelper.Order_column[4] + ", " + MySQLiteHelper.Order_column[5] + " FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[2] + "=" + email + " GROUP BY " + MySQLiteHelper.Order_column[1], null);
			if (cursor.moveToFirst()) {//If the information exists
				ArrayList <Meal> Commande;
				while (!cursor.isAfterLast()) {//As long as there is one element to read
					String currentResto = cursor.getString(0);
					Commande = new ArrayList <Meal> ();
					while(!cursor.isAfterLast() && currentResto == cursor.getString(0)) {
						Commande.add(new Meal (cursor.getString(1), cursor.getInt(2)));//We stock the quantity of the meal with the instance variable "stock"
						cursor.moveToNext();
					}
					preBooking.add(new PreBooking(currentResto, Commande));
				}
			}
			
			db.close();
		}
		
		return preBooking;
	}
	
	/*
	 * @post : Add the element to the list 'prebooking' (on the database and on the instance variable)
	 * If the prebooking is null, the prebooking isn't added to the list
	 * If the prebooking is already in the list, it isn't added to the list
	 */
	public void addPreBooking (PreBooking preBooking) {
		
		if (preBooking == null) {//If the parameter is null
			return ;
		}
		
		String RestaurantName = preBooking.getRestaurant().getRestaurantName();//Name of the restaurant where we are ordering
		
		for (int i=0; i<this.preBooking.size(); i++) {
			
			String IemeRestaurantName = this.preBooking.get(i).getRestaurant().getRestaurantName();//Name of the restaurant where we have ordered
			
			if (IemeRestaurantName.equals(RestaurantName)) {//We compare the order
				
				int counter = 0;//Count the number of same meals in their ordered
				
				for (int j=0; j<this.preBooking.get(i).getCommande().size(); j++) {
					
					Meal JemeMealoOrdered = (this.preBooking.get(i)).getCommande().get(j);// Jth meal of one order already registered
					
					boolean MealFound = false;
					
					for (int k=0; k<preBooking.getCommande().size() && !MealFound; k++) {
						
						Meal KemeMealOrdered = preBooking.getCommande().get(k);// Kth meal of the order not registered
						
						if (JemeMealOrdered.getMealName().equals(KemeMealOrdered.getMealName()) && JemeMealOrdered.getMealStock() == KemeMealOrdered.getMealStock()) {//if it's the same meal in the same quantities
							counter++;
							MealFound = true;
						}
					}
				}
				
				if (counter == this.preBooking.get(i).getCommande().size()) {//All the order match -> the order is already in the list
					return;
				}
			}
		}
		
		this.preBooking.add(preBooking);
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		String restNom = preBooking.getRestaurant().getRestaurantName();
		
		for(int i=0; i<preBooking.getCommande().size(); i++) {
			String MealName = (preBooking.getCommande().get(i)).getMealName();
			int quantite = (preBooking.getCommande().get(i)).getMealStock();
			
			db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Order + "(" + MySQLiteHelper.Order_column[1] + ", "  + MySQLiteHelper.Order_column[2] +  ", "  + MySQLiteHelper.Order_column[4] + ", "  + MySQLiteHelper.Order_column[5] + ") VALUES (" + restNom + ", " + email + ", " + MealName + ", " + quantite + ");");
			MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Order + "(" + MySQLiteHelper.Order_column[1] + ", "  + MySQLiteHelper.Order_column[2] +  ", "  + MySQLiteHelper.Order_column[4] + ", "  + MySQLiteHelper.Order_column[5] + ") VALUES (" + restNom + ", " + email + ", " + MealName + ", " + quantite + ");");
		}
		
		db.close();
	}
	
	/*
	 * @post : Remove the element to the list 'prebooking' (on the database and on the instance variable)
	 * If the prebooking is null, nothing happens
	 * If the prebooking is not in the list, nothing happens too
	 */
	public void removePreBooking (PreBooking preBooking) {
		
		if (preBooking == null) {//If the parameter is null
			return ;
		}
		
		boolean found = false;
		int index;
		
		String RestaurantName = preBooking.getRestaurant().getRestaurantName();//Name of the restaurant where we are ordering
		
		for (int i=0; i<this.preBooking.size() && !found; i++) {
			
			String IemeRestaurantName = this.preBooking.get(i).getRestaurant().getRestaurantName();//Name of the restaurant where we have ordered
			
			if (IemeRestaurantName.equals(RestaurantName)) {//We compare the order
				
				int counter = 0;//Count the number of same meals in their ordered
				
				for (int j=0; j<this.preBooking.get(i).getCommande().size(); j++) {
					
					Meal JemeMealoOrdered = (this.preBooking.get(i)).getCommande().get(j);// Jth meal of one order already registered
					
					boolean MealFound = false;
					
					for (int k=0; k<preBooking.getCommande().size() && !MealFound; k++) {
						
						Meal KemeMealOrdered = preBooking.getCommande().get(k);// Kth meal of the order not registered
						
						if (JemeMealOrdered.getMealName().equals(KemeMealOrdered.getMealName()) && JemeMealOrdered.getMealStock() == KemeMealOrdered.getMealStock()) {//if it's the same meal in the same quantities
							counter++;
							MealFound = true;
						}
					}
				}
				
				if (counter == this.preBooking.get(i).getCommande().size()) {//All the order match -> the order is in the list
					found = true;
					index = i;
				}
			}
		}
		
		if (!found) {//The parameter is not in the list
			return;
		}
		
		this.preBooking.remove(index);
		
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		String restNom = preBooking.getRestaurant().getRestaurantName();
			
		db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[1] + " = " + restNom + " AND " + MySQLiteHelper.Order_column[2] + " = " + email + " AND " + MySQLiteHelper.Order_column[3] + " is NULL ;");
		MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[1] + " = " + restNom + " AND " + MySQLiteHelper.Order_column[2] + " = " + email + " AND " + MySQLiteHelper.Order_column[3] + " is NULL ;");
		
		db.close();
		
	}
	
	/*
	 * @post : check if the preBooking is possible (if the stocks match) and return true or false
	 */
	public boolean isPreReservationPossible (PreBooking preBooking) {
		
		if (preBooking == null) {
			return false;
		}
		
		//Test for the stocks
		SQLiteDatabase db = sqliteHelper.getReadableDatabase();//Transcript the statement below
		
		for (int i=0; i<preBooking.getCommande().size(); i++) {
			
			Meal currentMeal = preBooking.getCommande().get(i);
			
			Cursor cursor = db.rawQuery("SELECT DISTINCT P.platNom FROM Plat P, Commande C, (SELECT C.platNom, total(C.quantite) as quantite FROM Commande C WHERE C.restNom = "+ preBooking.getRestaurant().getRestaurantName() +" GROUP BY C.platNom) AS Somme WHERE P.platNom = " + currentMeal.getMealName() + " AND P.platNom = C.platNom AND P.RestNom = C.restNom AND  C.RestNom = " + preBooking.getRestaurant().getRestaurantname() + " AND P.platNom=Somme.platNom AND P.stock - Somme.quantite - " + currentMeal.getMealStock() + " >= 0 ", null);
			if (!cursor.moveToFirst()) {//If there isn't enough stock for that meal
				db.close();
				return false;
			}
		}
		
		db.close();
		
		return true;
		
	}
	
	/*
	 * @param : commande != null
	 * @post : try to prebook in a restaurant with the parameters asked and return true or false if it's a success or not
	 */
	public boolean preBook (ArrayList <Meal> commande) {
		
		if (commande.isEmpty || restaurantEnCours == null) {
			return false;
		}
		
		PreBooking preBooking = new PreBooking (restaurantEnCours, commande);
		if (!isPreReservationPossible(preBooking)) {
			return false;
		}
		
		this.addPreBooking(preBooking);
		
		return true;
		
	}
	
	/*
	 * @post : If getFormDatabase is true, we will get the information from the database (and set 'booking')
	 * else, we will return the current value of 'booking'
	 */
	public ArrayList <Booking> getBooking (boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Booking.clear();//We remove all the elements
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Booking_column[1] + ", " + MySQLiteHelper.Booking_column[3] + ", " + MySQLiteHelper.Booking_column[4] + " FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[2] + "=" + email + " GROUP BY " + MySQLiteHelper.Booking_column[1], null);
			if (cursor.moveToFirst()) {//If the information exists
				
				ArrayList <Meal> Commande;
				Restaurant currentResto = new Restaurant (sqliteHelper, cursor.getString(0));
				int nbrReservation = cursor.getInt(1);
				Time time = new Time (cursor.getString(2));
				
				Cursor cursor2 = db.rawQuery("SELECT " + MySQLiteHelper.Order_column[4] + ", " + MySQLiteHelper.Order_column[5] + " FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[2] + "=" + email + " AND " + MySQLiteHelper.Order_column[1] + " = " + Cursor.getString(0) + " AND " + MySQLiteHelper.Order_column[3] + " = " + Cursor.getString(4) , null);
				if (cursor2.moveToFirst()) {//If there is an order for this restaurant
					
					while (!cursor2.isAfterLast()) {//As long as there is one element to read
						
						Commande = new ArrayList <Meal> ();
						while(!cursor2.isAfterLast()) {
							Commande.add(new Meal (cursor2.getString(0), cursor2.getInt(1)));//We stock the quantity of the meal with the instance variable "stock"
							cursor2.moveToNext();
						}
					}
				}
				
				booking.add(new Booking(currentResto, nbrReservation, time, Commande));
				
				cursor.moveToNext();
			}
			
			db.close();
		}
		
		return booking;
		
	}
	
	/*
	 * @post : Add the element to the list 'booking' (on the database and on the instance variable)
	 * If the booking is null, the booking isn't added to the list
	 * If the booking is already in the list, it isn't added to the list
	 */
	public void addPreBooking (Booking booking) {
		
		if (booking == null) {//If the parameter is null
			return ;
		}
		
		String RestaurantName = booking.getRestaurant().getRestaurantName();//Name of the restaurant where we are reserving
		int nbrPlace = booking.getNombrePlaces();//time when we are reserving
		Time time = booking.getHeureReservation();//number of places we are reserving
		
		
		for (int i=0; i<this.booking.size(); i++) {//We test if the booking isn't already registered
			
			String IemeRestaurantName = this.booking.get(i).getRestaurant().getRestaurantName();//Name of the restaurant where we have reserved
			Time IemeTime = this.booking.get(i).getHeureReservation();//time when we have reserved
			int IemeNbrPlace = this.booking.get(i).getNombrePlaces();//number of places we have reserved
			
			if (IemeRestaurantName.equals(RestaurantName) && time.equals(IemeTime) && nbrPlace==IemeNbrPlace) {//We compare reservations
				
				int counter = 0;//Count the number of same meals in their ordered
				
				if (this.booking.get(i).getCommande() != null && booking.getCommande() != null) {//If both have an order
					for (int j=0; j<this.booking.get(i).getCommande().size(); j++) {
						
						Meal JemeMealoOrdered = (this.booking.get(i)).getCommande().get(j);// Jth meal of one order already registered
						
						boolean MealFound = false;
						
						for (int k=0; k<booking.getCommande().size() && !MealFound; k++) {
							
							Meal KemeMealOrdered = booking.getCommande().get(k);// Kth meal of the order not registered
							
							if (JemeMealOrdered.getMealName().equals(KemeMealOrdered.getMealName()) && JemeMealOrdered.getMealStock() == KemeMealOrdered.getMealStock()) {//if it's the same meal in the same quantities
								counter++;
								MealFound = true;
							}
						}
					}
				
					if (counter == this.booking.get(i).getCommande().size()) {//All the order match -> the reservation is already in the list
						return;
					}
				}
				else if (this.booking.get(i).getCommande() == null && booking.getCommande() == null) {//both have no order (and other informations are the same) -> the reservation is already in the list
					return;
				}
			}
		}
		
		this.booking.add(booking);
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		String restNom = booking.getRestaurant().getRestaurantName();
		
		db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Booking + "(" + MySQLiteHelper.Booking_column[1] + ", " + MySQLiteHelper.Booking_column[2] + ", " + MySQLiteHelper.Booking_column[3] + ", " + MySQLiteHelper.Booking_column[4] + ") VALUES (" + RestaurantName + ", " + email + ", " + nbrPlace + ", " + time.toString() + ");");
		MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Booking + "(" + MySQLiteHelper.Booking_column[1] + ", " + MySQLiteHelper.Booking_column[2] + ", " + MySQLiteHelper.Booking_column[3] + ", " + MySQLiteHelper.Booking_column[4] + ") VALUES (" + RestaurantName + ", " + email + ", " + nbrPlace + ", " + time.toString() + ");");
		
		if (booking.getCommande().size() != null) {
			for(int i=0; i<booking.getCommande().size(); i++) {
				String MealName = (booking.getCommande().get(i)).getMealName();
				int quantite = (booking.getCommande().get(i)).getMealStock();
				
				db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Order + "(" + MySQLiteHelper.Order_column[1] + ", "  + MySQLiteHelper.Order_column[2] +  ", " + MySQLiteHelper.Order_column[3] + ", "  + MySQLiteHelper.Order_column[4] + ", "  + MySQLiteHelper.Order_column[5] + ") VALUES (" + RestaurantName + ", " + email + ", " + time.toString() + ", " + MealName + ", " + quantite + ");");
				MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Order + "(" + MySQLiteHelper.Order_column[1] + ", "  + MySQLiteHelper.Order_column[2] +  ", " + MySQLiteHelper.Order_column[3] + ", "  + MySQLiteHelper.Order_column[4] + ", "  + MySQLiteHelper.Order_column[5] + ") VALUES (" + RestaurantName + ", " + email + ", " + time.toString() + ", " + MealName + ", " + quantite + ");");
			}
		}
		
		db.close();
	}
	
	/*
	 * @post : Remove the element to the list 'booking' (on the database and on the instance variable)
	 * If the booking is null, nothing happens
	 * If the booking is not in the list, nothing happens too
	 */
	public void removeBooking (Booking booking) {
		
		if (booking == null) {//If the parameter is null
			return ;
		}
		
		boolean found = false;
		int index;
		
		String RestaurantName = booking.getRestaurant().getRestaurantName();//Name of the restaurant where we are reserving
		int nbrPlace = booking.getNombrePlaces();//time when we are reserving
		Time time = booking.getHeureReservation();//number of places we are reserving
		
		for (int i=0; i<this.booking.size() && !found; i++) {//We test if the booking isn't already registered
			
			String IemeRestaurantName = this.booking.get(i).getRestaurant().getRestaurantName();//Name of the restaurant where we have reserved
			Time IemeTime = this.booking.get(i).getHeureReservation();//time when we have reserved
			int IemeNbrPlace = this.booking.get(i).getNombrePlaces();//number of places we have reserved
			
			if (IemeRestaurantName.equals(RestaurantName) && time.equals(IemeTime) && nbrPlace==IemeNbrPlace) {//We compare reservations
				
				int counter = 0;//Count the number of same meals in their ordered
				
				if (this.booking.get(i).getCommande() != null && booking.getCommande() != null) {//If both have an order
					for (int j=0; j<this.booking.get(i).getCommande().size(); j++) {
						
						Meal JemeMealoOrdered = (this.booking.get(i)).getCommande().get(j);// Jth meal of one order already registered
						
						boolean MealFound = false;
						
						for (int k=0; k<booking.getCommande().size() && !MealFound; k++) {
							
							Meal KemeMealOrdered = booking.getCommande().get(k);// Kth meal of the order not registered
							
							if (JemeMealOrdered.getMealName().equals(KemeMealOrdered.getMealName()) && JemeMealOrdered.getMealStock() == KemeMealOrdered.getMealStock()) {//if it's the same meal in the same quantities
								counter++;
								MealFound = true;
							}
						}
					}
				
					if (counter == this.booking.get(i).getCommande().size()) {//All the order match -> the reservation is already in the list
						found=true;
						index = i;
					}
				}
				else if (this.booking.get(i).getCommande() == null && booking.getCommande() == null) {//both have no order (and other informations are the same) -> the reservation is already in the list
					found=true;
					index = i;
				}
			}
		}
		
		if (!found) {//The parameter is not in the list
			return;
		}
		
		this.booking.remove(index);
		
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		String restNom = booking.getRestaurant().getRestaurantName();
		
		db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Booking + " WHERE " + MySQLiteHelper.Booking_column[1] + " = " + RestaurantName + " AND " + MySQLiteHelper.Booking_column[2] + " = " + email + " AND " + MySQLiteHelper.booking_column[4] + " = " + nbrPlaces + ";");
		MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Booking + " WHERE " + MySQLiteHelper.Booking_column[1] + " = " + RestaurantName + " AND " + MySQLiteHelper.Booking_column[2] + " = " + email + " AND " + MySQLiteHelper.booking_column[4] + " = " + nbrPlaces + ";");
		
		if(booking.getCommande()!=null) {//We had the order if it exists
			db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[1] + " = " + RestaurantName + " AND " + MySQLiteHelper.Order_column[2] + " = " + email + " AND " + MySQLiteHelper.Order_column[3] + " = " + nbrPlaces + ";");
			MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Order + " WHERE " + MySQLiteHelper.Order_column[1] + " = " + RestaurantName + " AND " + MySQLiteHelper.Order_column[2] + " = " + email + " AND " + MySQLiteHelper.Order_column[3] + " = " + nbrPlaces + ";");
		}
		
		db.close();
	}
	
	/*
	 * @post : check if the preBooking is possible (if the schedule and the stocks match) and return true or false
	 */
	public boolean isReservationPossible (Booking booking) {
		
		if (preBooking == null) {
			return false;
		}
		
		SQLiteDatabase db = sqliteHelper.getReadableDatabase();
		
		//Test for the stocks
		if (booking.getCommande()!=null) {
			for (int i=0; i<booking.getCommande().size(); i++) {
				
				Meal currentMeal = booking.getCommande().get(i);
				
				Cursor cursor = db.rawQuery("SELECT DISTINCT P.platNom FROM Plat P, Commande C, (SELECT C.platNom, total(C.quantite) as quantite FROM Commande C WHERE C.restNom = "+ booking.getRestaurant().getRestaurantName() +" GROUP BY C.platNom) AS Somme WHERE P.platNom = " + currentMeal.getMealName() + " AND P.platNom = C.platNom AND P.RestNom = C.restNom AND  C.RestNom = " + booking.getRestaurant().getRestaurantname() + " AND P.platNom=Somme.platNom AND P.stock - Somme.quantite - " + currentMeal.getMealStock() + " >= 0 ", null);
				if (!cursor.moveToFirst()) {//If there isn't enough stock for that meal
					db.close();
					return false;
				}
			}
		}
		
		//Test for the schedule
		Cursor cursor2 = db.rawQuery("SELECT DISTINCT R.restNom FROM Restaurant R, Reservation B, Horaire H, (SELECT R.restNom, total(B.NbrReservation) AS total FROM Restaurant R, Reservation B WHERE R.restNom = " + booking.getRestaurant().getRestaurantName() + " (R.restNom = B.restNom AND B.heure=" + booking.getHeureReservation().toString() + ") GROUP BY B.restNom) AS TotalReservation WHERE R.restNom=B.restNom AND B.restNom=H.restNom AND ((R.restNom=TotalReservation.restNom AND R.capaTotale - TotalReservation.total >= "+ booking.getNbrPlaces() +")	OR (R.restNom not in (SELECT DISTINCT B.restNom FROM Reservation B WHERE B.heure=" + booking.getHeureReservation() + " GROUP BY B.restNom) AND R.capaTotale >= "+ booking.getNbrPlaces() +")) AND H.openHour <=" + booking.getHeureReservation() + " AND H.closeHour > " + booking.getHeureReservation() + " AND R.restNom = H.restNom AND H.jourOuverture in (SELECT case cast(strftime('%w','now') as INTEGER) when 0 then 'Dimanche' when 1 then 'Lundi' when 2 then 'Mardi' when 3 then 'Mercredi' when 4 then 'Jeudi' when 5 then 'Vendredi' else 'Samedi' end as DayOfWeek)", null);
		if (cursor2.moveToNext()) {//If it's possible
			db.close();
			return true;
		}
		else {
			db.close();
			return false;
		}		
	}
	
	/*
	 * @param : time != null
	 * 			nbrPlaces > 0
	 * @post : try to book in a restaurant with the parameters asked and return true or false if it's a success or not
	 */
	public boolean book (ArrayList <Meal> commande, int nbrPlaces, Time time) {
		
		if (restaurantEnCours == null || time == null || nbrPlaces <= 0) {//If the reservation has no sense
			return false;
		}
		
		Booking booking = new Booking (restaurantEnCours, nbrPlaces, commande);
		if (!isReservationPossible(booking)) {
			return false;
		}
		
		this.addBooking(booking);
		
		return true;
	}
	

}