package spoon.misterspoon;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Carte {
	
	static final String orderMeal[] = new String[]{"abc","prix"};
	static final String categorie[] = new String[]{"entree","plat","dessert","boisson"};
	MySQLiteHelper sqliteHelper;
	ArrayList <Meal> platsFav;
	ArrayList <String> filterList;
	ArrayList <Menu> menuList;
	String mealOrder;
	
	public Carte(MySQLiteHelper sqliteHelper, ArrayList<Menu> menuList, String mealOrder, ArrayList <Meal> platsFav,	ArrayList <String> filterList) {
		this.sqliteHelper = sqliteHelper;
		this.mealOrder = mealOrder;
		this.platsFav = platsFav;
		this.filterList = filterList;
		this.menuList = menuList;
	}
	
	public Carte(MySQLiteHelper sqliteHelper2, String restaurantName) {
		this.platsFav = new ArrayList <Meal>();
		this.filterList = new ArrayList <String>();
		this.menuList = new ArrayList <Menu>();
		this.mealOrder = new String();
		
		this.sqliteHelper = sqliteHelper;
		SQLiteDatabase db = sqliteHelper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT " + MySQLiteHelper.Menu_column[1] + ", " + MySQLiteHelper.Menu_column[2] + ", " + MySQLiteHelper.Menu_column[3] + " FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[3] + "=" + "'"+restaurantName+"'", null);
		if (cursor.moveToFirst()) {//If the information exists
			while (!cursor.isAfterLast()) {//As long as there is one element to read
				menuList.add(new Menu (sqliteHelper2, cursor.getString(0), cursor.getString(2), cursor.getString(1)));
				cursor.moveToNext();
			}
		}
		db.close();
	}
	
	public String getMealOrder() {
		return this.mealOrder;
	}
	
	public ArrayList<Menu> getMenuList() {
		return this.menuList;
	}
	
	public ArrayList<String> getFilterList() {
		return this.filterList;
	}
	
	public ArrayList<Meal> getPlatsFav() {
		return this.platsFav;
	}
	
	public void setMealOrder(String order) {
		this.mealOrder = order;
	}
	
	public void setMenuList(ArrayList<Menu> menuList) {
		this.menuList = menuList;
	}
	
	public void setFilterList(ArrayList<String> filterList) {
		this.filterList = filterList;
	}
	
	public void setPlatsFav(ArrayList<Meal> platsFav) {
		this.platsFav = platsFav;
	}
	
	public void sort(boolean sortPrixCroissant) {
		ArrayList<Menu> entree = new ArrayList<Menu>();
		ArrayList<Menu> plat = new ArrayList<Menu>();
		ArrayList<Menu> dessert = new ArrayList<Menu>();
		ArrayList<Menu> boisson = new ArrayList<Menu>();
		ArrayList<Menu> menu = new ArrayList<Menu>();
		ArrayList<String> result = new ArrayList<String>();
		
		String string = "Menu ";
		for (int i = 0; i < menuList.size(); i++) {
			if(menuList.get(i).getCategorie(false).equals(categorie[0]))
							entree.add(menuList.get(i));
			else if(menuList.get(i).getCategorie(false).equals(categorie[1]))
							plat.add(menuList.get(i));
			else if(menuList.get(i).getCategorie(false).equals(categorie[2]))
							dessert.add(menuList.get(i));
			else if(menuList.get(i).getCategorie(false).equals(categorie[3]))
							boisson.add(menuList.get(i));
			
		}
		
		for (int i = 0; i < entree.size(); i++) {
			for (int j = i + 1; j < entree.size(); j++) {
				if (entree.get(i).getMenuName().compareTo(entree.get(j).getMenuName()) > 0){
					Menu temp = entree.get(j);
					entree.set(j, entree.get(i));
					entree.set(i, temp);
				}
			}
		}
		
		for (int i = 0; i < plat.size(); i++) {
			for (int j = i + 1; j < plat.size(); j++) {
				if (plat.get(i).getMenuName().compareTo(plat.get(j).getMenuName()) > 0){
					Menu temp = plat.get(j);
					plat.set(j, plat.get(i));
					plat.set(i, temp);
				}
			}
		}
		for (int i = 0; i < dessert.size(); i++) {
			for (int j = i + 1; j < dessert.size(); j++) {
				if (dessert.get(i).getMenuName().compareTo(dessert.get(j).getMenuName()) > 0){
					Menu temp = dessert.get(j);
					dessert.set(j, dessert.get(i));
					dessert.set(i, temp);
				}
			}
		}
		for (int i = 0; i < boisson.size(); i++) {
			for (int j = i + 1; j < boisson.size(); j++) {
				if (boisson.get(i).getMenuName().compareTo(boisson.get(j).getMenuName()) > 0){
					Menu temp = boisson.get(j);
					boisson.set(j, boisson.get(i));
					boisson.set(i, temp);
				}
			}
		}
		
		for (int i = 0; i < entree.size(); i++) {
			menu.add(entree.get(i));
		}		
		for (int i = 0; i < plat.size(); i++) {
			menu.add(plat.get(i));
		}
		for (int i = 0; i < dessert.size(); i++) {
			menu.add(dessert.get(i));
		}
		for (int i = 0; i < boisson.size(); i++) {
			menu.add(boisson.get(i));
		}
		
		for (int i = 0; i < menu.size(); i++) {
			result.add(string + menu.get(i).getMenuName());
			ArrayList<Meal> meal = menu.get(i).getMealList(false);
			if (this.mealOrder.equals(orderMeal[0])) {
				for (int j = 0; j < meal.size(); j++) {
					for (int k = j + 1; k < meal.size(); k++) {
						if (meal.get(j).getMealName().compareTo(meal.get(k).getMealName()) > 0) {
							Meal temp = meal.get(k);
							meal.set(k, meal.get(j));
							meal.set(j, temp);
						}
					}
				}
			}
			else if (this.mealOrder.equals(orderMeal[1])) {
				for (int j = 0; j < meal.size(); j++) {
					for (int k = j + 1; k < meal.size(); k++) {
						if (meal.get(j).getMealPrice(false) > meal.get(k).getMealPrice(false)) {
							Meal temp = meal.get(k);
							meal.set(k, meal.get(j));
							meal.set(j, temp);
						}
					}
				}
			}
			for (int j = 0; j < meal.size(); j++) {
				result.add(meal.get(j).getMealName());
			}
		}
		this.filterList = result;
	}
	
	public void addMenu(Menu menu) {
		menuList.add(menu);
	}
	
	public void removeMeal(Menu menu) {
		menuList.remove(menu);
	}	

}