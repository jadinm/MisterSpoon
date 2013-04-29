package spoon.misterspoon;

import java.util.ArrayList;

public class Carte {
	MySQLiteHelper sqliteHelper;
	boolean ordrePrixCroissant;
	ArrayList <Meal> platsFav;
	ArrayList <String> filterList;
	
	public Carte(MySQLiteHelper sqliteHelper, boolean ordrePrixCroissant, ArrayList <Meal> platsFav,	ArrayList <String> filterList) {
		this.sqliteHelper = sqliteHelper;
		this.ordrePrixCroissant = ordrePrixCroissant;
		this.platsFav = platsFav;
		this.filterList = filterList;
	}
	
	public void sort(boolean sortPrixCroissant) {
		//hello ?
	}

}