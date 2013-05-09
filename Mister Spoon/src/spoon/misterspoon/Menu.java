package spoon.misterspoon;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Menu {
	
	MySQLiteHelper sqliteHelper;
	static final String categorie[] = new String[]{"entree","plat","dessert","boisson"};
	String MenuName;
	String restName;
	String Categorie;
	
	double MenuPrice;
	ArrayList <Meal> MealList;
	
	public Menu (MySQLiteHelper sqliteHelper, String MenuName, String restName, String Categorie) {
		this.sqliteHelper = sqliteHelper;
		this.MenuName = MenuName;
		this.restName = restName;
		this.Categorie = Categorie;
		
		SQLiteDatabase db = sqliteHelper.getReadableDatabase();
		
		//MenuPrice
		Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.MenuPrice_column[4] + " FROM " + MySQLiteHelper.TABLE_MenuPrice + " WHERE " + MySQLiteHelper.MenuPrice_column[1] + " = " + "'"+restName+"'" + " AND " + MySQLiteHelper.MenuPrice_column[2] + " = " + "'"+MenuName+"'" + " AND " + MySQLiteHelper.MenuPrice_column[3] + " = " + "'"+Categorie+"'", null);
		if (cursor.moveToFirst() && cursor.getString(0) != null) {//If the information exists
			MenuPrice = cursor.getDouble(0);
		}
		//MealList
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Menu_column[4] + " FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[1] + " = " + "'"+MenuName+"'" + " AND " + MySQLiteHelper.Menu_column[2] + " = " + "'"+Categorie+"'" + " AND " + MySQLiteHelper.Menu_column[3] + " = " + "'"+restName+"'", null);
		if (cursor.moveToFirst()) {//If the information exists
			MealList = new ArrayList <Meal> ();
			while (!cursor.isAfterLast()) {
				
				MealList.add(new Meal(cursor.getString(0), restName, sqliteHelper));
				cursor.moveToNext();
			}
		}
		
		db.close();
	}
	
	public String getMenuName() {
		
		return MenuName;
	}
	
	public String getRestName () {
		
		return restName;
	}
	
	public double getMenuPrice(boolean getFromDatabase) {
		
		if (getFromDatabase) {
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.MenuPrice_column[4] + " FROM " + MySQLiteHelper.TABLE_MenuPrice + " WHERE " + MySQLiteHelper.MenuPrice_column[1] + " = " + "'"+restName+"'" + " AND " + MySQLiteHelper.MenuPrice_column[2] + " = " + "'"+MenuName+"'" + " AND " + MySQLiteHelper.MenuPrice_column[3] + " = " + "'"+Categorie+"'", null);
			if (cursor.moveToFirst() && cursor.getString(0) != null) {//If the information exists
				MenuPrice = cursor.getDouble(0);
			}
			db.close();
		}
		
		return MenuPrice;
	}
	
	public String getCategorie(boolean getFromDatabase) {
		return Categorie;
	}
	
	public void setCategorie(String NewCategorie) {
		this.Categorie = NewCategorie;
	}
	
	public void setName(String NewName) {
		this.MenuName = NewName;
	}
	
	public void setRestName(String newName) {
		this.restName = newName;
	}
	
	public void setPrice(double NewPrice) {
		this.MenuPrice = NewPrice;
	}
	
	public void setMealList(ArrayList<Meal> NewML) {
		this.MealList = NewML;
	}
	
	public ArrayList <Meal> getMealList (boolean getFromDatabase) {
		
		if(getFromDatabase) {
			
			MealList.clear();
			
			SQLiteDatabase db = sqliteHelper.getReadableDatabase();
			
			Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Menu_column[4] + " FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[1] + " = " + "'"+MenuName+"'" + " AND " + MySQLiteHelper.Menu_column[2] + " = " + "'"+Categorie+"'" + " AND " + MySQLiteHelper.Menu_column[3] + " = " + "'"+restName+"'", null);
			if (cursor.moveToFirst()) {//If the information exists
				while (!cursor.isAfterLast()) {
					
					MealList.add(new Meal(cursor.getString(0), restName, sqliteHelper));
					cursor.moveToNext();
				}
			}
			
			db.close();
		}
		
		return MealList;
	}
	
	public void addMeal(Meal meal) {
		MealList.add(meal);
	}
	
	public void removeMeal(String mealName) {
		MealList.remove(mealName);//TODO
	}	
	
	
	
}