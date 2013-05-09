package spoon.misterspoon;

import java.util.List;

public class PreReservationRestaurantItem {
	public String nom;
	public String telephone;
	public List<String> plat;
	
	public PreReservationRestaurantItem(){
		super();
	}
	
	public PreReservationRestaurantItem(String nom, String telephone, List<String> plat) {
		super();
		this.nom = nom;
		this.telephone = telephone;
		this.plat = plat;
	}
	
	
}
