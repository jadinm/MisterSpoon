package spoon.misterspoon;

import java.util.ArrayList;

public class CarteActivityHeader {

	private String menuName;
	private ArrayList<CarteActivityChild> mealList = new ArrayList<CarteActivityChild>();
	
	public CarteActivityHeader (String menuName, ArrayList <CarteActivityChild> mealList) {
		this.menuName = menuName;
		this.mealList = mealList;
	}

	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public ArrayList<CarteActivityChild> getMealList() {
		return mealList;
	}
	public void setMealList(ArrayList<CarteActivityChild> mealList) {
		this.mealList = mealList;
	}
	
}
