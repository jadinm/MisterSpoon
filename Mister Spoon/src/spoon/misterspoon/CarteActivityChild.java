package spoon.misterspoon;

public class CarteActivityChild {

	private String mealName = "";
	private double price;
	private boolean selected = false;

	public CarteActivityChild(String mealName, double price) {
		this.mealName = mealName;
		this.price=price;
	}
	public double getMealPrice() {
		return price;
	}
	public void setMealPrice(double price) {
		this.price = price;
	}
	public String getMealName() {
		return mealName;
	}
	public void setMealName(String mealName) {
		this.mealName = mealName;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean check) {
		this.selected = check;
	}
}
