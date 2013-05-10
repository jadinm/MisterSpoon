package spoon.misterspoon;


public class reservationClientActivityItem {
	
	private String mealName;
	private String mealQuantity;

	public reservationClientActivityItem (String mealName, String mealQuantity) {
		this.mealName = mealName;
		this.mealQuantity = mealQuantity;
	}

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String mealName) {
		this.mealName = mealName;
	}

	public String getMealQuantity() {
		return mealQuantity;
	}

	public void setMealQuantity(String mealQuantity) {
		this.mealQuantity = mealQuantity;
	}

}
