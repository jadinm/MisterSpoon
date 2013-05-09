package spoon.misterspoon;

import android.database.sqlite.SQLiteDatabase;

public class MealBuilder {

	private MySQLiteHelper sqliteHelper;
	private RestaurantOwner owner;
	private Meal meal;

	public MealBuilder(MySQLiteHelper sqliteHelper, Meal meal, RestaurantOwner owner){
		this.sqliteHelper = sqliteHelper;
		this.owner = owner;
		this.meal = meal;
	}
	
	public Meal getMeal () {
		return meal;
	}
	
	public boolean addImage(String path){
		if(path != null){
			for(String currentImage : meal.getImageList(false)){
				if(path.equals(currentImage)) return true;
			}
			SQLiteDatabase db = sqliteHelper.getWritableDatabase();

			MySQLiteHelper.Additional_Orders.add("INSERT INTO " + MySQLiteHelper.TABLE_ImageMeal + " ( " + MySQLiteHelper.ImageMeal_column[1] + ", " + MySQLiteHelper.ImageMeal_column[2] + ", " + MySQLiteHelper.ImageMeal_column[3] + ") VALUES (" + "'"+meal.getMealName()+"'" + ", " + "'"+meal.getRestName()+"'" + ", " + "'"+path+"'" + " ) ;");
			db.execSQL("INSERT INTO " + MySQLiteHelper.TABLE_ImageMeal + " ( " + MySQLiteHelper.ImageMeal_column[1] + ", " + MySQLiteHelper.ImageMeal_column[2] + ", " + MySQLiteHelper.ImageMeal_column[3] + ") VALUES (" + "'"+meal.getMealName()+"'" + ", " + "'"+meal.getRestName()+"'" + ", " + "'"+path+"'" + " ) ;");
		}
		else {
			return false;
		}
		meal.addImage(path);
		return true;
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