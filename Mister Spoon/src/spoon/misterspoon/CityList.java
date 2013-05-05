package spoon.misterspoon;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;

public class CityList {

	public MySQLiteHelper sqliteHelper;

	static final String orderTable[] = new String[]{"abc","proximite"};

	private ArrayList<City> cityList;
	private String ordre;
	private Client client;
	
	

	public CityList (MySQLiteHelper sqliteHelper, Client client, LocationListener location, Activity active) {

		this.cityList = new ArrayList<City>();
		this.client = client;

		SQLiteDatabase db = sqliteHelper.getReadableDatabase();

		GPS gpsClient = getClientPosition(location, active);

		if (gpsClient==null) {

			Cursor cursor = db.rawQuery("SELECT DISTINCT " + MySQLiteHelper.Address_column[4] + " FROM " + MySQLiteHelper.TABLE_Address + " GROUP BY " + MySQLiteHelper.Address_column[4], null);

			if (cursor.moveToFirst()) {

				while(!cursor.isAfterLast()) {
					cityList.add(new City (cursor.getString(0), null));
					cursor.moveToNext();
				}
			}

			this.sort(orderTable[0]);
			this.ordre = orderTable[0];
		}
		else {
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Address_column[1] + ", " + MySQLiteHelper.Address_column[4] + " FROM " + MySQLiteHelper.TABLE_Address + " GROUP BY " + MySQLiteHelper.Address_column[4], null);
			if (cursor.moveToFirst()) {
				String currentCity = cursor.getString(1);
				GPS minDistance = new GPS (cursor.getString(0));
				while(!cursor.isAfterLast()) {
					String newCity = cursor.getString(1);
					GPS newGps = new GPS (cursor.getString(0));

					if (currentCity.equals(newCity)) {//We are on the same town

						if (newGps.compareTo(gpsClient) < minDistance.compareTo(gpsClient)) {
							minDistance = newGps;
						}

						cursor.moveToNext();
					}
					else {//We change of town
						cityList.add(new City (currentCity, minDistance));//We stock the previous one

						currentCity = newCity;
						minDistance = newGps;

						cursor.moveToNext();
					}
				}

				cityList.add(new City (currentCity, minDistance));//We stock the last one
			}
			this.sort(orderTable[1]);
			this.ordre = orderTable[1];
		}

	}
	
	public String getOrdre () {
		return ordre;
	}

	/*
	 * Sort the list by alphabetic order or by proximity
	 */
	public void sort(String Ordre) {

		if (Ordre.equals(orderTable[0])) {//"abc"

			City [] array = new City[cityList.size()];
			for (int i =0; i<cityList.size(); i++) {
					array[i] = cityList.get(i);
			}

			for(int i=0; i<array.length; i++)
			{
				for(int j=i+1; j<array.length; j++)
				{
					if(array[i].getCityName().compareTo(array[j].getCityName()) < 0)// If the first is bigger than the second
					{
						City temp = array[j];
						array[j] = array[i];
						array[i] = temp;
					}
				}
			}

			cityList.clear();
			for (int i =0; i<array.length; i++) {
				cityList.add(array[i]);
			}
			this.ordre = orderTable[0];
		}

		else if (Ordre.equals(orderTable[1])) {//"proximite"

			City [] array = new City[cityList.size()];
			for (int i =0; i<cityList.size(); i++) {
					array[i] = cityList.get(i);
			}

			for(int i=0; i<array.length; i++)
			{
				for(int j=i+1; j<array.length; j++)
				{
					if(array[i].getPosition().compareTo(this.client.getPosition(null, null)) < array[j].getPosition().compareTo(this.client.getPosition(null, null)))// If the further is bigger than the second
					{
						City temp = array[j];
						array[j] = array[i];
						array[i] = temp;
					}
				}
			}
			this.ordre = orderTable[1];
			
			cityList.clear();
			for (int i =0; i<array.length; i++) {
				cityList.add(array[i]);
			}
		}
	}

	/*
	 * Create a list of the name of the cities
	 */
	public ArrayList <String> getNomCities(){
		ArrayList <String> nomCities = new ArrayList<String>();
		for(City city : getCityList()){
			nomCities.add(city.getCityName());
		}
		return nomCities;
	}

	public GPS getClientPosition(LocationListener location, Activity active) {
		return client.getPosition(location,active);
	}

	public ArrayList<City> getCityList() {
		return this.cityList;
	}

}