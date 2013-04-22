package spoon.misterspoon;

public class Meal {

	private String mealname;
	private double mealprice;
	private int stock; // On les sort d'ou ?
	private String description; // On les sort d'ou ?
	
	public Meal(String mealname, double mealprice) {
        this.mealname = mealname;
        this.mealprice = mealprice;
	}
	
	public String getMealName() {
		return this.mealname;
	}
	
	public int getMealStock() {
		return stock; // On les sort d'ou ?
	}
	
	public String getMealDescription() {
		return this.description; // On les sort d'ou ?
	}
	
	public double getMealPrice() {
		return this.mealprice;
	}
}
