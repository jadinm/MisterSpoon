package spoon.misterspoon;

import android.database.sqlite.SQLiteDatabase;

public class MealBuilder {
	
	private MySQLiteHelper sqliteHelper;
	private RestaurantOwner owner;
	
	private String name;
	private int stock;
	private String description;
	private double price;
	
	public MealBuilder(MySQLiteHelper sqliteHelper, RestaurantOwner owner){
		this.sqliteHelper = sqliteHelper;
		this.owner = owner;
	}
	
	public void setMealName(String name){
		if (name == null) return;
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[1] + " = " + name + " WHERE " + MySQLiteHelper.Menu_column[2] + " = " + owner.getRestaurant().getRestaurantName() + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[1] + " = " + name + " WHERE " + MySQLiteHelper.Meal_column[2] + " = " + owner.getRestaurant().getRestaurantName() + " ;");
		db.close();
		
	}
	
	public void setMealStock(int stock){
		if (stock <= 0) return;
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[4] + " = " + stock + " WHERE " + MySQLiteHelper.Menu_column[2] + " = " + owner.getRestaurant().getRestaurantName() + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[4] + " = " + stock + " WHERE " + MySQLiteHelper.Meal_column[2] + " = " + owner.getRestaurant().getRestaurantName() + " ;");
		db.close();
		
	}
	
	public void setMealDescription(String description){
		if (description == null) return;
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[5] + " = " + description + " WHERE " + MySQLiteHelper.Menu_column[2] + " = " + owner.getRestaurant().getRestaurantName() + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[5] + " = " + description + " WHERE " + MySQLiteHelper.Meal_column[2] + " = " + owner.getRestaurant().getRestaurantName() + " ;");
		db.close();
		
	}
	
	public void setMealPrice(double price){
		if (price <= 0) return;
		
		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[3] + " = " + price + " WHERE " + MySQLiteHelper.Menu_column[2] + " = " + owner.getRestaurant().getRestaurantName() + " ;");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[3] + " = " + price + " WHERE " + MySQLiteHelper.Meal_column[2] + " = " + owner.getRestaurant().getRestaurantName() + " ;");
		db.close();
		
	}
	
	

}