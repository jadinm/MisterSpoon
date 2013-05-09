package spoon.misterspoon;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Meal {

	public MySQLiteHelper sqliteHelper;

	String mealname;
	String restName;
	ArrayList <String> imageList;
	double mealprice;
	int stock; 
	String description = null;

	public Meal (String mealname) {

		this.mealname = mealname;
	}

	public Meal (String mealname, int stock) {

		this.mealname = mealname;
		this.stock = stock;
	}

	public Meal(String mealname, String restName, MySQLiteHelper sqliteHelper) { 
		this.mealname = mealname;
		this.restName = restName;
		this.sqliteHelper = sqliteHelper;
		this.imageList = new ArrayList <String> ();
		SQLiteDatabase db = sqliteHelper.getReadableDatabase();
		
		// Select the price
		Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[3] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "= ? AND " + MySQLiteHelper.Meal_column[2] + " = " + "'"+restName+"'", new String []{mealname});
		if (cursor.moveToFirst()) {//If the information exists
			this.mealprice = cursor.getDouble(0);
		}
		
		// select stock
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[4] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "= ? AND " + MySQLiteHelper.Meal_column[2] + " = " + "'"+restName+"'", new String []{mealname});
		if (cursor.moveToFirst() && cursor.getString(0)!=null) {//If the information exists
			this.stock = cursor.getInt(0);
		}

		// select description
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[5] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "= ? AND " + MySQLiteHelper.Meal_column[2] + " = " + "'"+restName+"'", new String []{mealname});
		if (cursor.moveToFirst()) {//If the information exists
			this.description = cursor.getString(0);
		}
		
		//"imageList"
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.ImageMeal_column[3] + " FROM " + MySQLiteHelper.TABLE_ImageMeal +" WHERE " + MySQLiteHelper.ImageMeal_column[1] + "= ? AND " + MySQLiteHelper.ImageMeal_column[2] + " = " + "'"+restName+"'", new String []{mealname});
		if (cursor.moveToFirst()) {
			while (!cursor.isAfterLast()) {//If there is one element more to read
				imageList.add(cursor.getString(0));
				cursor.moveToNext();
			}
		}

		//db.close();

	}
	
	public void addImage(String path){
		this.imageList.add(path);
	}

	/*
	 * Return true if it already exists in the database and false if it doesn't
	 */
	public static boolean isInDatabase (MySQLiteHelper sqliteHelper, String mealName, String restName) {

		SQLiteDatabase db = sqliteHelper.getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[1] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "= ? AND " + MySQLiteHelper.Meal_column[2] + " = " + "'"+restName+"'", new String []{mealName});
		if (cursor.moveToFirst()) {//If the information exists
			return true;
		}
		return false;
	}

	public String getRestName() {
		return restName;
	}

	public String getMealName () {

		return this.mealname;
	}

	public void setMealName (String mealname) {
		this.mealname = mealname;
	}

	public int getMealStock(boolean getFromDatabase) {

		if (getFromDatabase) {
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[4] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "= ? AND " + MySQLiteHelper.Meal_column[2] + " = " + "'"+restName+"'", new String []{mealname});
			if (cursor.moveToFirst()) {//If the information exists
				this.stock = cursor.getInt(0);
			}
		}

		return this.stock; 
	}

	public void setMealStock (int mealStock) {
		this.stock = mealStock;
	}

	public String getMealDescription(boolean getFromDatabase) {

		if (getFromDatabase) {
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[5] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "= ? AND " + MySQLiteHelper.Meal_column[2] + " = " + "'"+restName+"'", new String []{mealname});
			if (cursor.moveToFirst()) {//If the information exists
				this.description = cursor.getString(0);
			}
		}

		return this.description; 
	}

	public void setMealDescription (String mealDescription) {
		this.description = mealDescription;
	}

	public double getMealPrice(boolean getFromDatabase) {

		if (getFromDatabase) {
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[3] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "= ? AND " + MySQLiteHelper.Meal_column[2] + " = " + "'"+restName+"'", new String []{mealname});
			if (cursor.moveToFirst()) {//If the information exists
				this.mealprice = cursor.getDouble(0);
			}
		}

		return this.mealprice;
	}

	public void setMealPrice (double mealPrice) {
		this.mealprice = mealPrice;
	}

	public ArrayList <String> getImageList(boolean getFromDatabase) {
		if (getFromDatabase) {

			imageList = new ArrayList <String> ();

			SQLiteDatabase db = sqliteHelper.getReadableDatabase();

			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.ImageMeal_column[3] + " FROM " + MySQLiteHelper.TABLE_ImageMeal + " WHERE " + MySQLiteHelper.ImageMeal_column[1] + "= ? AND " + MySQLiteHelper.ImageMeal_column[2] + " = " + "'"+restName+"'", new String []{mealname});
			if (cursor.moveToFirst()) {
				while (!cursor.isAfterLast()) {//If there is one element more to read
					imageList.add(cursor.getString(0));
					cursor.moveToNext();
				}
			}

			//db.close();
		}

		return imageList;
	}

}