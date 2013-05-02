package spoon.misterspoon;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CityList {
	
	public MySQLiteHelper sqliteHelper;
	
	ArrayList<City> cityList;
	String ordre;
	
	public CityList (MySQLiteHelper sqliteHelper) {
		
		cityList = new ArrayList<City>();
		
		SQLiteDatabase db = sqliteHelper.getReadableDatabase();
		
		
	}
	
	public void sort(String Ordre) {
		boolean state = false;
		City currentCity;
		
		if (Ordre.equals("abc")) {
			while (!state) {
				for(int i = 0; i < this.cityList.size() - 1; i++) {
					if (this.cityList.get(i).getCityName().compareTo(this.cityList.get(i + 1).getCityName()) > 0) {
						currentCity = this.cityList.get(i);
						this.cityList.set(i, this.cityList.get(i + 1));
						this.cityList.set(i + 1, currentCity);
					}
					else state = true;
				}
			}
		}
		
		if (Ordre.equals("proximite")) {
			while (!state) {
				for(int i = 0; i < this.cityList.size() - 1; i++) {
					if (distance(this.cityList.get(i).getPosition(), getClientPosition()) <  distance(this.cityList.get(i + 1).getPosition(), getClientPosition())) {
						currentCity = this.cityList.get(i);
						this.cityList.set(i, this.cityList.get(i + 1));
						this.cityList.set(i + 1, currentCity);
					}
					else state = true;
				}
			}
		}
	}
	
	public GPS getClientPosition() {
		boolean updateData;
		return client.getPosition(updateData);
	}
	

	public double distance(GPS a, GPS b) {
		double result = Math.sqrt(Math.pow(a.getLongitude() - b.getLongitude(), 2) + Math.pow(a.getLatitude() - b.getLatitude(), 2));
		return result;
	}
	
	public ArrayList<City> getCityList() {
		return this.cityList;
	}
	
}
