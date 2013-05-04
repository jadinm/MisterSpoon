package spoon.misterspoon;

import java.util.ArrayList;

public class Carte {
	MySQLiteHelper sqliteHelper;
	boolean ordrePrixCroissant;
	ArrayList <Meal> platsFav;
	ArrayList <String> filterList;
	ArrayList <Menu> menuList;
	
	public Carte(MySQLiteHelper sqliteHelper, ArrayList<Menu> menuList, boolean ordrePrixCroissant, ArrayList <Meal> platsFav,	ArrayList <String> filterList) {
		this.sqliteHelper = sqliteHelper;
		this.ordrePrixCroissant = ordrePrixCroissant;
		this.platsFav = platsFav;
		this.filterList = filterList;
		this.menuList = menuList;
	}
	
	public Carte(MySQLiteHelper sqliteHelper2, String restaurantName) {
		// TODO needed !!!
	}

	public void sort(boolean sortPrixCroissant) {
		//TODO
	}
	
	public void addMenu(Menu menu) {
		menuList.add(menu);
	}
	
	public void removeMeal(Menu menu) {
		menuList.remove(menu);
	}	

}