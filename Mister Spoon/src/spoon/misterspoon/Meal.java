package spoon.misterspoon;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Meal {

	public MySQLiteHelper sqliteHelper;

	String mealname;
	String restName;
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
		SQLiteDatabase db = sqliteHelper.getReadableDatabase();
		
		Log.d("mealprice", "SELECT " + MySQLiteHelper.Meal_column[3] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "=" + "'"+mealname+"'" + " AND " + MySQLiteHelper.Meal_column[2] + " = " + "'"+restName+"'");

		// Select the price
		Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[3] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "= ? AND " + MySQLiteHelper.Meal_column[2] + " = " + "'"+restName+"'", new String []{mealname});
		if (cursor.moveToFirst()) {//If the information exists
			Log.d("mealprice", cursor.getString(0));
			this.mealprice = cursor.getDouble(0);
		}
		Log.d("mealprice", mealprice +"");

		
		Log.d("stock", "SELECT " + MySQLiteHelper.Meal_column[4] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "=" + "'"+mealname+"'" + " AND " + MySQLiteHelper.Meal_column[2] + " = " + "'"+restName+"'");
		// select stock
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[4] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "= ? AND " + MySQLiteHelper.Meal_column[2] + " = " + "'"+restName+"'", new String []{mealname});
		if (cursor.moveToFirst() && cursor.getString(0)!=null) {//If the information exists
			Log.d("stock", cursor.getString(0));
			this.stock = cursor.getInt(0);
		}

		Log.d("stock", stock + "");

		Log.d("description", "SELECT " + MySQLiteHelper.Meal_column[5] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "=" + "'"+mealname+"'" + " AND " + MySQLiteHelper.Meal_column[2] + " = " + "'"+restName+"'");
		// select description
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Meal_column[5] + " FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "= ? AND " + MySQLiteHelper.Meal_column[1] + " = " + "'"+restName+"'", new String []{mealname});
		if (cursor.moveToFirst()) {//If the information exists
			Log.d("description", cursor.getString(0));
			this.description = cursor.getString(0);
		}

		//db.close();

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

}