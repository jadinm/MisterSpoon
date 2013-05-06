package spoon.misterspoon;

import java.util.ArrayList;
import java.util.ListIterator;

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
	
	public void createMenu(String menuName, ArrayList <Meal> mealList, double price, String categorie) {
		
		if (menuName == null) {return;}
		if (categorie == null) {return;}
		
		String restName = restaurantOwner.getRestaurant().getRestaurantName();
		
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Menu_column[4] + " FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[1] + "=" + menuName, null);
		if (cursor.moveToFirst()) {//If the information exists
			return;
		}
		ListIterator<Meal> it = mealList.listIterator();

		while (it.hasNext()) {
			Meal meal = (Meal) it.next();
			
			MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Menu + "(" + MySQLiteHelper.Menu_column[1] + ", " + MySQLiteHelper.Menu_column[2] +", " + MySQLiteHelper.Menu_column[3] +", " + MySQLiteHelper.Menu_column[4] +")  VALUES(" + menuName + ", "+ categorie +", "+ restName + ", " +meal.getMealName()+"); ");
			db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Menu + "(" + MySQLiteHelper.Menu_column[1] + ", " + MySQLiteHelper.Menu_column[2] +", " + MySQLiteHelper.Menu_column[3] +", " + MySQLiteHelper.Menu_column[4] +")  VALUES(" + menuName+ ", "+categorie+", "+restName+", "+meal.getMealName()+"); ");
					
			MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_MenuPrice + "(" + MySQLiteHelper.MenuPrice_column[1] + ", " + MySQLiteHelper.MenuPrice_column[2] +", " + MySQLiteHelper.MenuPrice_column[3] +", " + MySQLiteHelper.MenuPrice_column[4] +")  VALUES(" + restName + ", " + menuName + ", " + categorie + ", " + price +"); ");
			db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_MenuPrice + "(" + MySQLiteHelper.MenuPrice_column[1] + ", " + MySQLiteHelper.MenuPrice_column[2] +", " + MySQLiteHelper.MenuPrice_column[3] +", " + MySQLiteHelper.MenuPrice_column[4] +")  VALUES(" + restName + ", " + menuName + ", " + categorie + ", " + price +"); ");
			
		}

		
		db.close();

		Menu menu = new Menu(sqliteHelper, menuName, restName, categorie);
		carte.addMenu(menu);
	}
	
	public void setMenuName(Menu menuToChange, String name) {
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Menu + " SET " + MySQLiteHelper.Menu_column[1] + " = " + name + " WHERE " + MySQLiteHelper.Menu_column[1] + " = " + menuToChange.getMenuName() + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Menu + " SET " + MySQLiteHelper.Menu_column[1] + " = " + name + " WHERE " + MySQLiteHelper.Menu_column[1] + " = " + menuToChange.getMenuName() + " ;");
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_MenuPrice + " SET " + MySQLiteHelper.MenuPrice_column[2] + " = " + name + " WHERE " + MySQLiteHelper.MenuPrice_column[2] + " = " + menuToChange.getMenuName() + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Menu + " SET " + MySQLiteHelper.MenuPrice_column[2] + " = " + name + " WHERE " + MySQLiteHelper.MenuPrice_column[2] + " = " + menuToChange.getMenuName() + " ;");
		db.close();
		
		menuToChange.setName(name);
		
	}
	
	public void setMenuPrice(Menu menuToChange, double price) {
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_MenuPrice + " SET " + MySQLiteHelper.MenuPrice_column[4] + " = " + price + " WHERE " + MySQLiteHelper.MenuPrice_column[2] + " = " + menuToChange.getMenuName() + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_MenuPrice + " SET " + MySQLiteHelper.MenuPrice_column[4] + " = " + price + " WHERE " + MySQLiteHelper.MenuPrice_column[2] + " = " + menuToChange.getMenuName() + " ;");
		db.close();
		
		menuToChange.setPrice(price);
	}
	
	public void setMenuCategorie(Menu menuToChange, String categorie) {
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_MenuPrice + " SET " + MySQLiteHelper.MenuPrice_column[3] + " = " + categorie + " WHERE " + MySQLiteHelper.MenuPrice_column[2] + " = " + menuToChange.getMenuName() + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_MenuPrice + " SET " + MySQLiteHelper.MenuPrice_column[3] + " = " + categorie + " WHERE " + MySQLiteHelper.MenuPrice_column[2] + " = " + menuToChange.getMenuName() + " ;");
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Menu + " SET " + MySQLiteHelper.Menu_column[2] + " = " + categorie + " WHERE " + MySQLiteHelper.Menu_column[1] + " = " + menuToChange.getMenuName() + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Menu + " SET " + MySQLiteHelper.Menu_column[2] + " = " + categorie + " WHERE " + MySQLiteHelper.Menu_column[1] + " = " + menuToChange.getMenuName() + " ;");
		db.close();
		
		menuToChange.setCategorie(categorie);
	}
	
	public void addMenuMeal(Menu menuToChange, Meal meal) {

		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Menu_column[4] + " FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[4] + "=" + meal.getMealName(), null);
		if (cursor.moveToFirst()) {//If the information exists
			return;
		}
		
		
		MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Menu + "(" + MySQLiteHelper.Menu_column[1] + ", " + MySQLiteHelper.Menu_column[2] +", " + MySQLiteHelper.Menu_column[3] +", " + MySQLiteHelper.Menu_column[4] +")  VALUES(" + menuToChange.getMenuName()+ ", "+menuToChange.getCategorie(false)+", "+menuToChange.getRestName()+", "+meal.getMealName()+"); ");
		db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Menu + "(" + MySQLiteHelper.Menu_column[1] + ", " + MySQLiteHelper.Menu_column[2] +", " + MySQLiteHelper.Menu_column[3] +", " + MySQLiteHelper.Menu_column[4] +")  VALUES(" + menuToChange.getMenuName()+ ", "+menuToChange.getCategorie(false)+", "+menuToChange.getRestName()+", "+meal.getMealName()+"); ");

		MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_Meal + "(" + MySQLiteHelper.Meal_column[1] + ", " + MySQLiteHelper.Meal_column[2] +", " + MySQLiteHelper.Meal_column[3] +", " + MySQLiteHelper.Meal_column[4] +", " + MySQLiteHelper.Meal_column[5] +")  VALUES(" + menuToChange.getMenuName()+ ", "+menuToChange.getCategorie(false)+", "+menuToChange.getRestName()+", "+meal.getMealName()+"); ");
		db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_Menu + "(" + MySQLiteHelper.Menu_column[1] + ", " + MySQLiteHelper.Menu_column[2] +", " + MySQLiteHelper.Menu_column[3] +", " + MySQLiteHelper.Menu_column[4] +")  VALUES(" + meal.getMealName()+ ", "+meal.getRestName()+", "+meal.getMealPrice(false)+", "+meal.getMealDescription(false)+"); ");
		
		db.close();
		
		menuToChange.addMeal(meal);

	}	
	
	public void removeMenuMeal(Menu menuToChange, String mealName) {

		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[4] + "=" + mealName + "AND " + MySQLiteHelper.Menu_column[3] + "=" + menuToChange.getRestName());
		db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[4] + "=" + mealName + "AND " + MySQLiteHelper.Menu_column[3] + "=" + menuToChange.getRestName());

		db.close();
		
		menuToChange.removeMeal(mealName);
	}
	
	public void removeMenu(Menu menu) {
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		
		MySQLiteHelper.Additional_Orders.add("DELETE FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[1] + "=" + menu.getMenuName() + "AND " + MySQLiteHelper.Menu_column[3] + "=" + menu.getRestName());
		db.execSQL("DELETE FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[4] + "=" + menu.getMenuName() + "AND " + MySQLiteHelper.Menu_column[3] + "=" + menu.getRestName());

		db.close();
		
	}

}