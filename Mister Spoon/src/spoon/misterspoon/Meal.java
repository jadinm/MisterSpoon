package spoon.misterspoon;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Meal {

	public MySQLiteHelper sqliteHelper;
	
	private String mealname;
	private double mealprice;
	private int stock; 
	private String description;
	
	public Meal (String mealname) {
		
		this.mealname = mealname;
	}
	
	public Meal (String mealname, int stock) {
		
		this.mealname = mealname;
		this.stock = stock;
	}
	
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
		
		db.close();
		
	}
	
	public String getMealName () {
		
		return this.mealname;
	}
	
	public int getMealStock(boolean getFromDatabase) {
		
		if (getFromDatabase) {
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[4] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "=" + mealname, null);
			if (cursor.moveToFirst()) {//If the information exists
				this.stock = cursor.getInt(0);
			}
		}
		
		return this.stock; 
	}
	
	public String getMealDescription(boolean getFromDatabase) {
		
		if (getFromDatabase) {
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[5] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "=" + mealname, null);
			if (cursor.moveToFirst()) {//If the information exists
				this.description = cursor.getString(0);
			}
		}
		
		return this.description; 
	}
	
	public double getMealPrice(boolean getFromDatabase) {
		
		if (getFromDatabase) {
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[3] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "=" + mealname, null);
			if (cursor.moveToFirst()) {//If the information exists
				this.mealprice = cursor.getDouble(0);
			}
		}
		
		return this.mealprice;
	}

}
