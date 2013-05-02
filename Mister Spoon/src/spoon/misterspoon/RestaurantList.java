package spoon.misterspoon;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RestaurantList {

	public MySQLiteHelper sqliteHelper;
	
	String ordre;
	String town;
	
	ArrayList<Restaurant> restaurantList;
	ArrayList<String> listFilter;
	
	public RestaurantList (MySQLiteHelper sqliteHelper, String town) {
		
		this.town = town;
		restaurantList = new ArrayList <Restaurant> ();
		
		SQLiteDatabase db = sqliteHelper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT" + MySQLiteHelper.Address_column[5] + " FROM " + MySQLiteHelper.TABLE_Address + " WHERE " + MySQLiteHelper.Address_column[4] + "=" + town, null); 
		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast()) {
				
				restaurantList.add(new Restaurant(cursor.getString(1)));
				cursor.moveToNext();
			}
		}
		
		db.close();
		
	}
	
	public String getTown(boolean getFromDatabase) {
		return town;
	}
	
	public Restaurant getRestaurantListVisible() {
		
	}
	
	public Restaurant getRestaurantListUnVisible() {
		
	}
	
	public void sort(String Ordre) {
		
		boolean state = false;
		Restaurant currentRestaurant;
		
		if (Ordre.equals("abc")) {
			while (!state) {
				for(int i = 0; i < this.restaurantList.size() - 1; i++) {
					if (this.restaurantList.get(i).restaurantName.compareTo(this.restaurantList.get(i + 1).restaurantName) > 0) {
						currentRestaurant = this.restaurantList.get(i);
						this.restaurantList.set(i, this.restaurantList.get(i + 1));
						this.restaurantList.set(i + 1, currentRestaurant);
					}
					else state = true;
				}
			}
		}
		
		
		else if (Ordre.equals("note")) {
			while (!state) {
				for(int i = 0; i < this.restaurantList.size() - 1; i++) {
					if (this.restaurantList.get(i).note < this.restaurantList.get(i + 1).note) {
						currentRestaurant = this.restaurantList.get(i);
						this.restaurantList.set(i, this.restaurantList.get(i + 1));
						this.restaurantList.set(i + 1, currentRestaurant);
					}
					else state = true;
				}
			}
		}
		
		else if (Ordre.equals("prix")) {
			//TODO Prix avec carte
		}
		
		if (Ordre.equals("proximite")) {
			while (!state) {
				for(int i = 0; i < this.restaurantList.size() - 1; i++) {
					if (distance(this.restaurantList.get(i).getPosition(), getClientPosition()) <  distance(this.restaurantList.get(i + 1).getPosition(), getClientPosition())) {
						currentRestaurant = this.restaurantList.get(i);
						this.restaurantList.set(i, this.restaurantList.get(i + 1));
						this.restaurantList.set(i + 1, currentRestaurant);
					}
					else state = true;
				}
			}
		}
	}
	
	
	
	public void listFilter(String Filter, int value) {
		ArrayList<Restaurant> removedRestaurant;
		
		if (Filter.equals("favori")) {
			removedRestaurant = new ArrayList <Restaurant> ();
			for(int i = 0; i < this.restaurantList.size() - 1; i++) {
				removedRestaurant.add(this.restaurantList.get(i));
			}
		}
		
		else if (Filter.equals("note")) {
			removedRestaurant = new ArrayList <Restaurant> ();
			for(int i = 0; i < this.restaurantList.size() - 1; i++) {
				if (this.restaurantList.get(i).note < value) {
					removedRestaurant.add(this.restaurantList.get(i));
					this.restaurantList.remove(i);
				}
			}
		}
		
		else if (Filter.equals("prix")) {
			//TODO Prix avec carte
		}
	}
	
	public double distance(GPS a, GPS b) {
		double result = Math.sqrt(Math.pow(a.getLongitude() - b.getLongitude(), 2) + Math.pow(a.getLatitude() - b.getLatitude(), 2));
		return result;
	}
	
	
}
