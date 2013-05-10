package spoon.misterspoon;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CarteBuilder {

	MySQLiteHelper sqliteHelper;
	RestaurantOwner restaurantOwner;
	Carte carte;

	public CarteBuilder(MySQLiteHelper sqliteHelper, RestaurantOwner r) {
		this.sqliteHelper = sqliteHelper;//blablabla !
		this.restaurantOwner = r;
		carte = new Carte (sqliteHelper, r.getRestaurant().getRestaurantName(), null);

	}

	public boolean createMenu(String menuName, double price, String categorie, String firstMeal, double firstPrice) {
		
		if (menuName == null) {return false;}
		if (categorie == null) {return false;}

		String restName = restaurantOwner.getRestaurant().getRestaurantName();


		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Menu_column[1] + " FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[1] + "= '" + menuName + "' AND " + MySQLiteHelper.Menu_column[3] + " = '" + restaurantOwner.getRestaurant().getRestaurantName() + "' AND " + MySQLiteHelper.Menu_column[2] + "='" + categorie + "'", null);
		if (cursor.moveToFirst()) {//If the information exists
			db.close();
			return false;
		}
		cursor = db.rawQuery("SELECT " + MySQLiteHelper.Menu_column[4] + " FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[4] + "= '" + firstMeal + "' AND " + MySQLiteHelper.Menu_column[3] + " = '" + restaurantOwner.getRestaurant().getRestaurantName() + "' AND " + MySQLiteHelper.Menu_column[2] + "='" + categorie + "'", null);
		if (cursor.moveToFirst()) {//The first meal already exists
			
			MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Menu + "(" + MySQLiteHelper.Menu_column[1] + ", " + MySQLiteHelper.Menu_column[2] +", " + MySQLiteHelper.Menu_column[3] +", " + MySQLiteHelper.Menu_column[4] +")  VALUES('" + menuName + "', '"+ categorie +"', '"+ restName + "', '" + firstMeal+"'); ");
			db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Menu + "(" + MySQLiteHelper.Menu_column[1] + ", " + MySQLiteHelper.Menu_column[2] +", " + MySQLiteHelper.Menu_column[3] +", " + MySQLiteHelper.Menu_column[4] +")  VALUES('" + menuName+ "', '"+categorie+"', '"+restName+"', '"+ firstMeal+"'); ");

			if (price != 0) {
				MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_MenuPrice + "(" + MySQLiteHelper.MenuPrice_column[1] + ", " + MySQLiteHelper.MenuPrice_column[2] +", " + MySQLiteHelper.MenuPrice_column[3] +", " + MySQLiteHelper.MenuPrice_column[4] +")  VALUES('" + restName + "', '" + menuName + "', '" + categorie + "', '" + price +"'); ");
				db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_MenuPrice + "(" + MySQLiteHelper.MenuPrice_column[1] + ", " + MySQLiteHelper.MenuPrice_column[2] +", " + MySQLiteHelper.MenuPrice_column[3] +", " + MySQLiteHelper.MenuPrice_column[4] +")  VALUES('" + restName + "', '" + menuName + "', '" + categorie + "', '" + price +"'); ");
			}
			
			db.close();
			
			carte = new Carte (sqliteHelper, restaurantOwner.getRestaurant().getRestaurantName(), null);
			
			return true;
		}
		
		
		MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Menu + "(" + MySQLiteHelper.Menu_column[1] + ", " + MySQLiteHelper.Menu_column[2] +", " + MySQLiteHelper.Menu_column[3] +", " + MySQLiteHelper.Menu_column[4] +")  VALUES('" + menuName + "', '"+ categorie +"', '"+ restName + "', '" + firstMeal+"'); ");
		db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Menu + "(" + MySQLiteHelper.Menu_column[1] + ", " + MySQLiteHelper.Menu_column[2] +", " + MySQLiteHelper.Menu_column[3] +", " + MySQLiteHelper.Menu_column[4] +")  VALUES('" + menuName+ "', '"+categorie+"', '"+restName+"', '"+ firstMeal+"'); ");

		if (price != 0) {
			MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_MenuPrice + "(" + MySQLiteHelper.MenuPrice_column[1] + ", " + MySQLiteHelper.MenuPrice_column[2] +", " + MySQLiteHelper.MenuPrice_column[3] +", " + MySQLiteHelper.MenuPrice_column[4] +")  VALUES('" + restName + "', '" + menuName + "', '" + categorie + "', '" + price +"'); ");
			db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_MenuPrice + "(" + MySQLiteHelper.MenuPrice_column[1] + ", " + MySQLiteHelper.MenuPrice_column[2] +", " + MySQLiteHelper.MenuPrice_column[3] +", " + MySQLiteHelper.MenuPrice_column[4] +")  VALUES('" + restName + "', '" + menuName + "', '" + categorie + "', '" + price +"'); ");
		}
		
		MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Meal + "(" + MySQLiteHelper.Meal_column[1] + ", " + MySQLiteHelper.Meal_column[2] +", " + MySQLiteHelper.Meal_column[3] +")  VALUES( '" + firstMeal + "', '"+ restName +"', '"+ firstPrice + "'); ");
		db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Meal + "(" + MySQLiteHelper.Meal_column[1] + ", " + MySQLiteHelper.Meal_column[2] +", " + MySQLiteHelper.Meal_column[3] +")  VALUES( '" + firstMeal + "', '"+ restName +"', '"+ firstPrice + "'); ");
		
		db.close();

		carte = new Carte (sqliteHelper, restaurantOwner.getRestaurant().getRestaurantName(), null);
		
		return true;
	}

	public Carte getCarte () {
		return carte;
	}

	public boolean setMenuName(Menu menuToChange, String name) {
		

		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Menu_column[4] + " FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[1] + "='" + name + "' AND " + MySQLiteHelper.Menu_column[3] + "='" + menuToChange.getRestName() + "' AND " + MySQLiteHelper.Menu_column[2] + "='" + menuToChange.getCategorie(false) + "'", null);
		if (cursor.moveToFirst()) {//If the information already exists
			db.close();
			return false;
		}
		
		
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Menu + " SET " + MySQLiteHelper.Menu_column[1] + " = '" + name + "' WHERE " + MySQLiteHelper.Menu_column[1] + "='" + menuToChange.getMenuName() + "' AND " + MySQLiteHelper.Menu_column[3] + "='" + menuToChange.getRestName() + "' AND " + MySQLiteHelper.Menu_column[2] + "='" + menuToChange.getCategorie(false) + "';");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Menu + " SET " + MySQLiteHelper.Menu_column[1] + " = '" + name + "' WHERE " + MySQLiteHelper.Menu_column[1] + "='" + menuToChange.getMenuName() + "' AND " + MySQLiteHelper.Menu_column[3] + "='" + menuToChange.getRestName() + "' AND " + MySQLiteHelper.Menu_column[2] + "='" + menuToChange.getCategorie(false) + "';");
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_MenuPrice + " SET " + MySQLiteHelper.MenuPrice_column[2] + " = '" + name + "' WHERE " + MySQLiteHelper.MenuPrice_column[2] + "='" + menuToChange.getMenuName() + "' AND " + MySQLiteHelper.MenuPrice_column[1] + "='" + menuToChange.getRestName() + "' AND " + MySQLiteHelper.MenuPrice_column[3] + "='" + menuToChange.getCategorie(false) + "';");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Menu + " SET " + MySQLiteHelper.MenuPrice_column[2] + " = '" + name + "' WHERE " + MySQLiteHelper.MenuPrice_column[2] + "='" + menuToChange.getMenuName() + "' AND " + MySQLiteHelper.MenuPrice_column[1] + "='" + menuToChange.getRestName() + "' AND " + MySQLiteHelper.MenuPrice_column[3] + "='" + menuToChange.getCategorie(false) + "';");
		db.close();

		carte = new Carte (sqliteHelper, restaurantOwner.getRestaurant().getRestaurantName(), null);
		
		return true;

	}

	public void setMenuPrice(Menu menuToChange, double price) {
		

		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_MenuPrice + " SET " + MySQLiteHelper.MenuPrice_column[4] + " = '" + price + "' WHERE " + MySQLiteHelper.MenuPrice_column[2] + "='" + menuToChange.getMenuName() + "' AND " + MySQLiteHelper.MenuPrice_column[1] + "='" + menuToChange.getRestName() + "' AND " + MySQLiteHelper.MenuPrice_column[3] + "='" + menuToChange.getCategorie(false) + "';");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_MenuPrice + " SET " + MySQLiteHelper.MenuPrice_column[4] + " = '" + price + "' WHERE " + MySQLiteHelper.MenuPrice_column[2] + "='" + menuToChange.getMenuName() + "' AND " + MySQLiteHelper.MenuPrice_column[1] + "='" + menuToChange.getRestName() + "' AND " + MySQLiteHelper.MenuPrice_column[3] + "='" + menuToChange.getCategorie(false) + "';");
		db.close();

		carte = new Carte (sqliteHelper, restaurantOwner.getRestaurant().getRestaurantName(), null);
	}

	public boolean setMenuCategorie(Menu menuToChange, String categorie) {
		
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Menu_column[4] + " FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[1] + "='" + menuToChange.getMenuName() + "' AND " + MySQLiteHelper.Menu_column[3] + "='" + menuToChange.getRestName() + "' AND " + MySQLiteHelper.Menu_column[2] + "='" + categorie + "'", null);
		if (cursor.moveToFirst()) {//If the information exists
			db.close();
			return false;
		}
		
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_MenuPrice + " SET " + MySQLiteHelper.MenuPrice_column[3] + " = '" + categorie + "' WHERE " + MySQLiteHelper.MenuPrice_column[2] + "='" + menuToChange.getMenuName() + "' AND " + MySQLiteHelper.MenuPrice_column[1] + "='" + menuToChange.getRestName() + "' AND " + MySQLiteHelper.MenuPrice_column[3] + "='" + menuToChange.getCategorie(false) + "';");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_MenuPrice + " SET " + MySQLiteHelper.MenuPrice_column[3] + " = '" + categorie + "' WHERE " + MySQLiteHelper.MenuPrice_column[2] + "='" + menuToChange.getMenuName() + "' AND " + MySQLiteHelper.MenuPrice_column[1] + "='" + menuToChange.getRestName() + "' AND " + MySQLiteHelper.MenuPrice_column[3] + "='" + menuToChange.getCategorie(false) + "';");
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Menu + " SET " + MySQLiteHelper.Menu_column[2] + " = '" + categorie + "' WHERE " + MySQLiteHelper.Menu_column[1] + "='" + menuToChange.getMenuName() + "' AND " + MySQLiteHelper.Menu_column[3] + "='" + menuToChange.getRestName() + "' AND " + MySQLiteHelper.Menu_column[2] + "='" + menuToChange.getCategorie(false) + "';");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Menu + " SET " + MySQLiteHelper.Menu_column[2] + " = '" + categorie + "' WHERE " + MySQLiteHelper.Menu_column[1] + "='" + menuToChange.getMenuName() + "' AND " + MySQLiteHelper.Menu_column[3] + "='" + menuToChange.getRestName() + "' AND " + MySQLiteHelper.Menu_column[2] + "='" + menuToChange.getCategorie(false) + "';");
		db.close();

		carte = new Carte (sqliteHelper, restaurantOwner.getRestaurant().getRestaurantName(), null);
		
		return true;
	}

	public boolean addMenuMeal(Menu menuToChange, String meal, double mealPrice) {
		

		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Menu_column[4] + " FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[1] + "='" + menuToChange.getMenuName() + "' AND " + MySQLiteHelper.Menu_column[3] + "='" + menuToChange.getRestName() + "' AND " + MySQLiteHelper.Menu_column[2] + "='" + menuToChange.getCategorie(false) + "' AND " + MySQLiteHelper.Menu_column[4] + "='" + meal + "'", null);
		if (cursor.moveToFirst()) {//If the information exists
			db.close();
			return false;
		}

		MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Menu + "(" + MySQLiteHelper.Menu_column[1] + ", " + MySQLiteHelper.Menu_column[2] +", " + MySQLiteHelper.Menu_column[3] +", " + MySQLiteHelper.Menu_column[4] +")  VALUES('" + menuToChange.getMenuName()+ "', '"+menuToChange.getCategorie(false)+"', '"+menuToChange.getRestName()+"', '"+meal+"'); ");
		db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Menu + "(" + MySQLiteHelper.Menu_column[1] + ", " + MySQLiteHelper.Menu_column[2] +", " + MySQLiteHelper.Menu_column[3] +", " + MySQLiteHelper.Menu_column[4] +")  VALUES('" + menuToChange.getMenuName()+ "', '"+menuToChange.getCategorie(false)+"', '"+menuToChange.getRestName()+"', '"+meal+"'); ");

		MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Meal + "(" + MySQLiteHelper.Meal_column[1] + ", " + MySQLiteHelper.Meal_column[2] +", " + MySQLiteHelper.Meal_column[3] +")  VALUES('" + meal + "', '"+menuToChange.getRestName()+"', '"+mealPrice+"'); ");
		db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Meal + "(" + MySQLiteHelper.Meal_column[1] + ", " + MySQLiteHelper.Meal_column[2] +", " + MySQLiteHelper.Meal_column[3] +")  VALUES('" + meal + "', '"+menuToChange.getRestName()+"', '"+mealPrice+"'); ");

		db.close();

		carte = new Carte (sqliteHelper, restaurantOwner.getRestaurant().getRestaurantName(), null);
		
		return true;

	}

	public void removeMenuMeal(Menu menuToChange, String mealName) {

		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		

		MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[4] + "='" + mealName + "' AND " + MySQLiteHelper.Menu_column[3] + "='" + menuToChange.getRestName() + "' AND " + MySQLiteHelper.Menu_column[2] + "='" + menuToChange.getCategorie(false) + "' AND " + MySQLiteHelper.Menu_column[1] + "='" + menuToChange.getMenuName() + "';");
		db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[4] + "='" + mealName + "' AND " + MySQLiteHelper.Menu_column[3] + "='" + menuToChange.getRestName() + "' AND " + MySQLiteHelper.Menu_column[2] + "='" + menuToChange.getCategorie(false) + "' AND " + MySQLiteHelper.Menu_column[1] + "='" + menuToChange.getMenuName() + "';");
		
		MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "='" + mealName + "' AND " + MySQLiteHelper.Meal_column[2] + "='" + menuToChange.getRestName() +"';");
		db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Meal + " WHERE " + MySQLiteHelper.Meal_column[1] + "='" + mealName + "' AND " + MySQLiteHelper.Meal_column[2] + "='" + menuToChange.getRestName() + "';");

		db.close();
	}

	public void removeMenu(Menu menu) {

		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		

		MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[1] + "='" + menu.getMenuName() + "' AND " + MySQLiteHelper.Menu_column[3] + "='" + menu.getRestName() + "' AND " + MySQLiteHelper.Menu_column[2] + "='" + menu.getCategorie(false) + "';");
		db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[4] + "='" + menu.getMenuName() + "' AND " + MySQLiteHelper.Menu_column[3] + "='" + menu.getRestName() + "' AND " + MySQLiteHelper.Menu_column[2] + "='" + menu.getCategorie(false) + "';");

		db.close();

	}

}