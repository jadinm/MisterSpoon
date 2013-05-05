package spoon.misterspoon;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.util.Log;

public class RestaurantList {

	public MySQLiteHelper sqliteHelper;

	static final String orderTable[] = new String[]{"abc","note","proximite","prix"};
	static final String filterTable[] = new String[]{"favori", "note", "prix"};
	String ordre;
	String town;
	Client client;

	ArrayList<Restaurant> removedRestaurant;
	ArrayList<Restaurant> filterList;

	public RestaurantList (MySQLiteHelper sqliteHelper, String town, Client client, LocationListener location, Activity active) {

		this.client = client;
		this.town = town;
		filterList = new ArrayList <Restaurant> ();
		removedRestaurant = new ArrayList <Restaurant> ();

		GPS gpsClient = getClientPosition(location, active);

		SQLiteDatabase db = sqliteHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(" SELECT R." + MySQLiteHelper.Restaurant_column[1] + ", R." + MySQLiteHelper.Restaurant_column[4] + ", R." + MySQLiteHelper.Restaurant_column[7] + ", R." + MySQLiteHelper.Restaurant_column[8] + " FROM " + MySQLiteHelper.TABLE_Restaurant + " R, " + MySQLiteHelper.TABLE_Address + " A WHERE R." + MySQLiteHelper.Restaurant_column[4] + " = A." + MySQLiteHelper.Address_column[1] + " AND A." + MySQLiteHelper.Address_column[4] + " = " + "'"+town+"'" , null);
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {
				Log.v("client", "FUCK");
				filterList.add(new Restaurant(sqliteHelper, cursor.getString(0), new GPS (cursor.getString(1)), cursor.getInt(2), cursor.getInt(3)));
				cursor.moveToNext();
			}
		}


		if (gpsClient==null) {

			this.sort(orderTable[0]);
			this.ordre = orderTable[0];
		}
		else {
			this.sort(orderTable[2]);
			this.ordre = orderTable[2];
		}


	}

	public GPS getClientPosition(LocationListener location, Activity active) {
		return client.getPosition(location,active);
	}

	public ArrayList <String> getNomRestaurants(){
		ArrayList<String> nomRestaurants = new ArrayList<String>();
		for(Restaurant resto : getfilterListVisible()){
			nomRestaurants.add(resto.getRestaurantName());
			Log.v("Resto", resto.getRestaurantName());
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

		if (Ordre.equals(orderTable[0])) {//name

			Restaurant [] array = new Restaurant[filterList.size()];
			for (int i =0; i<filterList.size(); i++) {
				array[i] = filterList.get(i);
			}

			for(int i=0; i<array.length; i++)
			{
				for(int j=i+1; j<array.length; j++)
				{
					if(array[i].getRestaurantName().compareTo(array[j].getRestaurantName()) > 0)// If the first is bigger than the second
					{
						Restaurant temp = array[j];
						array[j] = array[i];
						array[i] = temp;
					}
				}
			}

			filterList.clear();
			for (int i =0; i<array.length; i++) {
				filterList.add(array[i]);
			}
			this.ordre = orderTable[0];
		}


		else if (Ordre.equals(orderTable[1])) {//note

			Restaurant [] array = new Restaurant[filterList.size()];
			for (int i =0; i<filterList.size(); i++) {
				array[i] = filterList.get(i);
			}

			for(int i=0; i<array.length; i++)
			{
				for(int j=i+1; j<array.length; j++)
				{
					if(((double)array[i].getRestaurantNote(false)) /((double) array[i].getRestaurantNbrVotants(false))  > ((double)array[j].getRestaurantNote(false)) /((double) array[j].getRestaurantNbrVotants(false)))// If the first is better than the second
					{
						Restaurant temp = array[j];
						array[j] = array[i];
						array[i] = temp;
					}
				}
			}

			filterList.clear();
			for (int i =0; i<array.length; i++) {
				filterList.add(array[i]);
			}
			this.ordre = orderTable[1];
		}

		else if (Ordre.equals(orderTable[2])) {//gps

			Restaurant [] array = new Restaurant[filterList.size()];
			for (int i =0; i<filterList.size(); i++) {
				array[i] = filterList.get(i);
			}

			for(int i=0; i<array.length; i++)
			{
				for(int j=i+1; j<array.length; j++)
				{
					if(array[i].getRestaurantPosition(false).compareTo(this.client.getPosition(null, null)) > array[j].getRestaurantPosition(false).compareTo(this.client.getPosition(null, null)))// If the first is bigger than the second
					{
						Restaurant temp = array[j];
						array[j] = array[i];
						array[i] = temp;
					}
				}
			}

			filterList.clear();
			for (int i =0; i<array.length; i++) {
				filterList.add(array[i]);
			}
			this.ordre = orderTable[2];
		}

		if (Ordre.equals(orderTable[3])) {//prix

			Restaurant [] array = new Restaurant[filterList.size()];
			for (int i =0; i<filterList.size(); i++) {
				array[i] = filterList.get(i);
			}

			for(int i=0; i<array.length; i++)
			{
				for(int j=i+1; j<array.length; j++)
				{
					if(array[i].getRestaurantAveragePrice() > array[j].getRestaurantAveragePrice())// If the first is cheaper than the second
					{
						Restaurant temp = array[j];
						array[j] = array[i];
						array[i] = temp;
					}
				}
			}

			filterList.clear();
			for (int i =0; i<array.length; i++) {
				filterList.add(array[i]);
			}
			this.ordre = orderTable[3];
		}
	}



	public void listFilter(String Filter, int value) {

		if (Filter.equals(filterTable[0])) {
			for(int i = 0; i < this.filterList.size(); i++) {
				for(int j = 0; j < this.client.getRestFav(false).size(); j++) {
					if (!this.filterList.get(i).getRestaurantName().equals(this.client.getRestFav(false).get(j).getRestaurantName())) {
						this.removedRestaurant.add(this.filterList.get(i));
						this.filterList.remove(i);
					}
				}
			}
		}

		else if (Filter.equals(filterTable[1])) {
			for(int i = 0; i < this.filterList.size(); i++) {
				if (this.filterList.get(i).getRestaurantNote(false) < value) {
					this.removedRestaurant.add(this.filterList.get(i));
					this.filterList.remove(i);
				}
			}
		}

		else if (Filter.equals(filterTable[2])) {
			for(int i = 0; i < this.filterList.size(); i++) {
				if (this.filterList.get(i).getRestaurantAveragePrice() < value) {
					this.removedRestaurant.add(this.filterList.get(i));
					this.filterList.remove(i);
				}
			}
		}
	}
	
	public String getOrdre () {
		return ordre;
	}

	public void resetfilterList() {
		for (int i = 0; i < this.removedRestaurant.size() - 1; i++) {
			this.filterList.add(this.removedRestaurant.get(i));
		}
	}
}
