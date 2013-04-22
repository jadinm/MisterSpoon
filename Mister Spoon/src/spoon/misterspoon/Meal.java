package spoon.misterspoon;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Meal {

	public MySQLiteHelper sqliteHelper;
	
	private String mealname;
	private double mealprice;
	private int stock; 
	private String description;
	
	public Meal(String mealname, MySQLiteHelper sqliteHelper) { 
		this.mealname = mealname;
        this.sqliteHelper = sqliteHelper;
		SQLiteDatabase db = sqliteHelper.getReadableDatabase();
		
		// Select the price
		Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[3] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "=" + mealname, null);
		if (cursor.moveToFirst()) {//If the information exists
			this.mealprice = cursor.getDouble(0);
		}
		
		// Check stock
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[4] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "=" + mealname, null);
		if (cursor.moveToFirst()) {//If the information exists
			this.stock = cursor.getInt(0);
		}
		
		// Description
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[5] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "=" + mealname, null);
		if (cursor.moveToFirst()) {//If the information exists
			this.description = cursor.getString(0);
		}
		
	}
	
	public String getMealName() {
		return this.mealname;
	}
	
	public int getMealStock() {
		return this.stock; 
	}
	
	public String getMealDescription() {
		return this.description; 
	}
	
	public double getMealPrice() {
		return this.mealprice;
	}

}
