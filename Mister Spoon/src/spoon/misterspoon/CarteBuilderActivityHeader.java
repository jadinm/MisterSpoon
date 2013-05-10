package spoon.misterspoon;

import java.util.ArrayList;

public class CarteBuilderActivityHeader {

	private String menuName;
	private ArrayList<CarteActivityChild> mealList = new ArrayList<CarteActivityChild>();
	private boolean isSelected = false;
	
	public CarteBuilderActivityHeader (String menuName, ArrayList <CarteActivityChild> mealList) {
		this.menuName = menuName;
		this.mealList = mealList;
	}
	
	static public CarteBuilderActivityHeader convert (CarteActivityHeader header) {
		
		return new CarteBuilderActivityHeader (header.getMenuName(), header.getMealList());
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
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
