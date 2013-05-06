package spoon.misterspoon;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MealBuilder {

	private MySQLiteHelper sqliteHelper;
	private RestaurantOwner owner;
	private Meal meal;

	public MealBuilder(MySQLiteHelper sqliteHelper, Meal meal, RestaurantOwner owner){
		this.sqliteHelper = sqliteHelper;
		this.owner = owner;
		this.meal = meal;
		if (meal == null) {
			Log.d("Fuck", "Chieur");
		}
	}
	
	public Meal getMeal () {
		if (meal == null) {
			Log.d("Fuck", "Chieur");
		}
		return meal;
	}

	public void setMealName(String name){
		if (name == null) return;

		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[1] + " = " + "'"+name+"'" + " WHERE " + MySQLiteHelper.Meal_column[2] + " = " + "'"+owner.getRestaurant().getRestaurantName()+"'" + " AND " + MySQLiteHelper.Meal_column[1] + " = " + "'"+meal.getMealName()+"'" + ";");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[1] + " = " + "'"+name+"'" + " WHERE " + MySQLiteHelper.Meal_column[2] + " = " + "'"+owner.getRestaurant().getRestaurantName()+"'" + " AND " + MySQLiteHelper.Meal_column[1] + " = " + "'"+meal.getMealName()+"'" + ";");
		db.close();
		
		meal.setMealName(name);

	}

	public void setMealStock(int stock){
		if (stock <= 0) return;

		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[4] + " = " + stock + " WHERE " + MySQLiteHelper.Meal_column[2] + " = " + "'"+owner.getRestaurant().getRestaurantName()+"'" + " AND " + MySQLiteHelper.Meal_column[1] + " = " + "'"+meal.getMealName()+"'" + ";");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[4] + " = " + stock + " WHERE " + MySQLiteHelper.Meal_column[2] + " = " + "'"+owner.getRestaurant().getRestaurantName()+"'" + " AND " + MySQLiteHelper.Meal_column[1] + " = " + "'"+meal.getMealName()+"'" + ";");
		db.close();
		
		meal.setMealStock(stock);

	}

	public void setMealDescription(String description){
		if (description == null) {
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[5] + " = NULL WHERE " + MySQLiteHelper.Meal_column[2] + " = " + "'"+owner.getRestaurant().getRestaurantName()+"'" + " AND " + MySQLiteHelper.Meal_column[1] + " = " + "'"+meal.getMealName()+"'" + ";");
			db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[5] + " = NULL WHERE " + MySQLiteHelper.Meal_column[2] + " = " + "'"+owner.getRestaurant().getRestaurantName()+"'" + " AND " + MySQLiteHelper.Meal_column[1] + " = " + "'"+meal.getMealName()+"'" + ";");
			db.close();
		}

		else {
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();
			MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[5] + " = '" + description + "' WHERE " + MySQLiteHelper.Meal_column[2] + " = " + "'"+owner.getRestaurant().getRestaurantName()+"'" + " AND " + MySQLiteHelper.Meal_column[1] + " = " + "'"+meal.getMealName()+"'" + ";");
			db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[5] + " = '" + description + "' WHERE " + MySQLiteHelper.Meal_column[2] + " = " + "'"+owner.getRestaurant().getRestaurantName()+"'" + " AND " + MySQLiteHelper.Meal_column[1] + " = " + "'"+meal.getMealName()+"'" + ";");
			db.close();
		}
		
		meal.setMealDescription(description);

	}

	public void setMealPrice(double price){
		if (price <= 0) return;

		SQLiteDatabase db = sqliteHelper.getWritableDatabase();
		MySQLiteHelper.Additional_Orders.add("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[3] + " = " + price + " WHERE " + MySQLiteHelper.Meal_column[2] + " = " + "'"+owner.getRestaurant().getRestaurantName()+"'" + " AND " + MySQLiteHelper.Meal_column[1] + " = " + "'"+meal.getMealName()+"'" + ";");
		db.execSQL("UPDATE " + MySQLiteHelper.TABLE_Meal + " SET " + MySQLiteHelper.Meal_column[3] + " = " + price + " WHERE " + MySQLiteHelper.Meal_column[2] + " = " + "'"+owner.getRestaurant().getRestaurantName()+"'" + " AND " + MySQLiteHelper.Meal_column[1] + " = " + "'"+meal.getMealName()+"'" + ";");
		db.close();
		
		meal.setMealPrice(price);

	}



}