package spoon.misterspoon;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RestaurantList {

	public MySQLiteHelper sqliteHelper;
	
	static final String orderTable[] = new String[]{"abc","note","prix","proximite"};
	
	String ordre;
	String town;
	Client client;
	Boolean getFromDatabase;
	
	ArrayList<Restaurant> removedRestaurant;
	ArrayList<Restaurant> filterList;
	
	public RestaurantList (MySQLiteHelper sqliteHelper, String town, Client client, Boolean getFromDatabase) {
		
		this.getFromDatabase = getFromDatabase;
		this.client = client;
		this.town = town;
		filterList = new ArrayList <Restaurant> ();
		removedRestaurant = new ArrayList <Restaurant> ();
		
		SQLiteDatabase db = sqliteHelper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT" + MySQLiteHelper.Restaurant_column[2] + " FROM " + MySQLiteHelper.TABLE_Restaurant + " WHERE " + MySQLiteHelper.Restaurant_column[5] + "=" + "SELECT" + MySQLiteHelper.Address_column[1] + "FROM" + MySQLiteHelper.Address_column + "WHERE" + MySQLiteHelper.Address_column[4] + "=" + town , null);
		if (cursor.moveToFirst()) {
			while (cursor.isAfterLast()) {
				
				filterList.add(new Restaurant(cursor.getString(1)));
				cursor.moveToNext();
			}
		}
		
		db.close();
		
	}
	
	public List<String> getNomRestaurants(){
		List<String> nomRestaurants = new ArrayList<String>();
		for(Restaurant resto : getfilterListVisible()){
			nomRestaurants.add(resto.getRestaurantName());
		}
		return nomRestaurants;
	}
	
	public String getTown(boolean getFromDatabase) {
		return town;
	}
	
	public ArrayList<Restaurant> getfilterListVisible() {
		return this.filterList;
	}
	
	public ArrayList<Restaurant> getfilterListUnVisible() {
		return this.removedRestaurant;
	}
	
	public void sort(String Ordre) {
		
		boolean state = false;
		Restaurant currentRestaurant;
		
		if (Ordre.equals("abc")) {
			while (!state) {
				for(int i = 0; i < this.filterList.size() - 1; i++) {
					if (this.filterList.get(i).restaurantName.compareTo(this.filterList.get(i + 1).restaurantName) > 0) {
						currentRestaurant = this.filterList.get(i);
						this.filterList.set(i, this.filterList.get(i + 1));
						this.filterList.set(i + 1, currentRestaurant);
					}
					else state = true;
				}
			}
		}
		
		
		else if (Ordre.equals("note")) {
			while (!state) {
				for(int i = 0; i < this.filterList.size() - 1; i++) {
					if (this.filterList.get(i).note < this.filterList.get(i + 1).note) {
						currentRestaurant = this.filterList.get(i);
						this.filterList.set(i, this.filterList.get(i + 1));
						this.filterList.set(i + 1, currentRestaurant);
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
				for(int i = 0; i < this.filterList.size() - 1; i++) {
					if (distance(this.filterList.get(i).getRestaurantPosition(getFromDatabase), this.client.getPosition(null)) <  distance(this.filterList.get(i + 1).getRestaurantPosition(getFromDatabase), this.client.getPosition(null))) {
						currentRestaurant = this.filterList.get(i);
						this.filterList.set(i, this.filterList.get(i + 1));
						this.filterList.set(i + 1, currentRestaurant);
					}
					else state = true;
				}
			}
		}
	}
	
	
	
	public void listFilter(String Filter, int value) {
		
		if (Filter.equals("favori")) {
			for(int i = 0; i < this.filterList.size() - 1; i++) {
				this.removedRestaurant.add(this.filterList.get(i));
			}
		}
		
		else if (Filter.equals("note")) {
			for(int i = 0; i < this.filterList.size() - 1; i++) {
				if (this.filterList.get(i).note < value) {
					this.removedRestaurant.add(this.filterList.get(i));
					this.filterList.remove(i);
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
	
	public void resetfilterList() {
		for (int i = 0; i < this.removedRestaurant.size() - 1; i++) {
			this.filterList.add(this.removedRestaurant.get(i));
		}
	}
}
