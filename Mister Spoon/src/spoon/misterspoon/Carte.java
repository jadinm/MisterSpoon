package spoon.misterspoon;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Carte {
	//TODO Ultime todo mettre ingredient
	static final String orderMeal[] = new String[]{"abc","prix"};
	static final String categorie[] = new String[]{"entree","plat","dessert","boisson", "tout"};
	static final String filterMeal[] = new String[]{"prix", "favori"};
	MySQLiteHelper sqliteHelper;
	ArrayList <Meal> platsFav;
	ArrayList <Menu> menuList;
	ArrayList<Menu> entree;
	ArrayList<Menu> plat;
	ArrayList<Menu> dessert;
	ArrayList<Menu> boisson;
	String mealOrder;


	public Carte(MySQLiteHelper sqliteHelper, String restaurantName, Client client) {
		this.platsFav = new ArrayList <Meal>();
		this.menuList = new ArrayList <Menu>();
		this.mealOrder = new String();

		entree = new ArrayList<Menu>();
		plat = new ArrayList<Menu>();
		dessert = new ArrayList<Menu>();
		boisson = new ArrayList<Menu>();

		this.sqliteHelper = sqliteHelper;
		SQLiteDatabase db = sqliteHelper.getReadableDatabase();

		Cursor cursor = db.rawQuery("SELECT DISTINCT " + MySQLiteHelper.Menu_column[1] + ", " + MySQLiteHelper.Menu_column[2] + ", " + MySQLiteHelper.Menu_column[3] + " FROM " + MySQLiteHelper.TABLE_Menu + " WHERE " + MySQLiteHelper.Menu_column[3] + "=" + "'"+restaurantName+"'" + " GROUP BY " + MySQLiteHelper.Menu_column[1], null);
		if (cursor.moveToFirst()) {//If the information exists
			while (!cursor.isAfterLast()) {//As long as there is one element to read
				menuList.add(new Menu (sqliteHelper, cursor.getString(0), cursor.getString(2), cursor.getString(1)));
				cursor.moveToNext();
			}
		}
		if (client != null) {
			platsFav = client.getPlatFav(true);
		}

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

		mealOrder = Carte.orderMeal[0];
		this.sort();
	}

	public String getMealOrder() {
		return this.mealOrder;
	}

	public ArrayList<Menu> getMenuList() {
		return this.menuList;
	}

	/*
	 * Return the conversion of the list of menu into the list of CarteActivityHeader
	 */
	public ArrayList<CarteActivityHeader> getFilterList() {
		
		ArrayList <CarteActivityHeader> menuList = new ArrayList <CarteActivityHeader> ();
		
		for (int i = 0; i<this.menuList.size(); i++) {
			ArrayList <CarteActivityChild> mealList = new ArrayList <CarteActivityChild> ();
			for (int j = 0; j<this.menuList.get(i).getMealList(false).size(); j++) {
				mealList.add(new CarteActivityChild(this.menuList.get(i).getMealList(false).get(j).getMealName()));
			}
			menuList.add(new CarteActivityHeader (menuList.get(i).getMenuName(), mealList));
		}
		
		return menuList;
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

	public void setPlatsFav(ArrayList<Meal> platsFav) {
		this.platsFav = platsFav;
	}

	public void sort() {
		
		ArrayList<Menu> entree = new ArrayList<Menu>();
		ArrayList<Menu> plat = new ArrayList<Menu>();
		ArrayList<Menu> dessert = new ArrayList<Menu>();
		ArrayList<Menu> boisson = new ArrayList<Menu>();
		
		Log.v("sort_begin", menuList.size() + "");
		
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
				if (entree.get(i).getMenuName().toLowerCase().compareTo(entree.get(j).getMenuName().toLowerCase()) > 0){
					Menu temp = entree.get(j);
					entree.set(j, entree.get(i));
					entree.set(i, temp);
				}
			}
		}

		for (int i = 0; i < plat.size(); i++) {
			for (int j = i + 1; j < plat.size(); j++) {
				if (plat.get(i).getMenuName().toLowerCase().compareTo(plat.get(j).getMenuName().toLowerCase()) > 0){
					Menu temp = plat.get(j);
					plat.set(j, plat.get(i));
					plat.set(i, temp);
				}
			}
		}
		for (int i = 0; i < dessert.size(); i++) {
			for (int j = i + 1; j < dessert.size(); j++) {
				if (dessert.get(i).getMenuName().toLowerCase().compareTo(dessert.get(j).getMenuName().toLowerCase()) > 0){
					Menu temp = dessert.get(j);
					dessert.set(j, dessert.get(i));
					dessert.set(i, temp);
				}
			}
		}
		for (int i = 0; i < boisson.size(); i++) {
			for (int j = i + 1; j < boisson.size(); j++) {
				if (boisson.get(i).getMenuName().toLowerCase().compareTo(boisson.get(j).getMenuName().toLowerCase()) > 0){
					Menu temp = boisson.get(j);
					boisson.set(j, boisson.get(i));
					boisson.set(i, temp);
				}
			}
		}

		menuList = new ArrayList <Menu> ();
		for (int i = 0; i < entree.size(); i++) {
			menuList.add(entree.get(i));
		}		
		for (int i = 0; i < plat.size(); i++) {
			menuList.add(plat.get(i));
		}
		for (int i = 0; i < dessert.size(); i++) {
			menuList.add(dessert.get(i));
		}
		for (int i = 0; i < boisson.size(); i++) {
			menuList.add(boisson.get(i));
		}

		for (int i = 0; i < menuList.size(); i++) {
			ArrayList<Meal> meal = menuList.get(i).getMealList(false);
			Log.v("method sort", meal.size()+"");
			if (this.mealOrder.equals(orderMeal[0])) {
				Log.v("sort","here");
				for (int j = 0; j < meal.size(); j++) {
					for (int k = j + 1; k < meal.size(); k++) {
						if (meal.get(j).getMealName().toLowerCase().compareTo(meal.get(k).getMealName().toLowerCase()) > 0) {
							Meal temp = meal.get(k);
							meal.set(k, meal.get(j));
							meal.set(j, temp);
						}
					}
				}
			}
			else if (this.mealOrder.equals(orderMeal[1])) {
				Log.v("sort","No, there");
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
		}
	}

	public void filter (String filter, float value) {

		if (filter.equals(filterMeal[0])) {//price
			for(int i = 0; i < this.menuList.size(); i++) {
				for (int j = 0; j<this.menuList.get(i).getMealList(false).size(); j++) {

					if (this.menuList.get(i).getMealList(false).get(j).getMealPrice(false) > value) {

						this.menuList.get(i).getMealList(false).remove(j);
						j--;//Because size decreases
					}
				}
			}
		}

		else if (filter.equals(filterMeal[1])) {//favourite
			for(int i = 0; i < this.menuList.size(); i++) {
				for (int j = 0; j<this.menuList.get(i).getMealList(false).size(); j++) {
					boolean found = false;
					for(int k = 0; k < this.platsFav.size() && !found; k++) {
						if (this.menuList.get(i).getMealList(false).get(j).getMealName().equals(this.platsFav.get(k).getMealName())) {
							found = true;
						}
					}
					if (!found) {
						this.menuList.get(i).getMealList(false).remove(j);
						j--;//Because size decreases
					}
				}
			}
		}

		else if  (filter.equals(categorie[0])) {//appetizer
			this.menuList = entree;
		}

		else if  (filter.equals(categorie[1])) {//dishes
			this.menuList = plat;
		}

		else if  (filter.equals(categorie[2])) {//dessert
			this.menuList = dessert;
		}
		else if  (filter.equals(categorie[3])) {//drink
			this.menuList = boisson;
		}
		else if (filter.equals(categorie[4])) {//all
			menuList = new ArrayList <Menu> ();
			for (int i = 0; i < entree.size(); i++) {
				menuList.add(entree.get(i));
			}		
			for (int i = 0; i < plat.size(); i++) {
				menuList.add(plat.get(i));
			}
			for (int i = 0; i < dessert.size(); i++) {
				menuList.add(dessert.get(i));
			}
			for (int i = 0; i < boisson.size(); i++) {
				menuList.add(boisson.get(i));
			}
		}
	}

	public void resetfilterList() {

		menuList = new ArrayList <Menu> ();

		for (int i = 0; i < entree.size(); i++) {
			menuList.add(entree.get(i));
		}		
		for (int i = 0; i < plat.size(); i++) {
			menuList.add(plat.get(i));
		}
		for (int i = 0; i < dessert.size(); i++) {
			menuList.add(dessert.get(i));
		}
		for (int i = 0; i < boisson.size(); i++) {
			menuList.add(boisson.get(i));
		}

		for(int i = 0; i < this.menuList.size(); i++) {
			this.menuList.get(i).getMealList(true);//He takes all the meal from the database
		}
	}

	public void addMenu(Menu menu) {
		menuList.add(menu);
	}

	public void removeMeal(Menu menu) {
		menuList.remove(menu);
	}	

}