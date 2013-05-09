package spoon.misterspoon;

import java.util.List;

public class PreReservationRestaurantItem {
	public String nom;
	public String email;
	public String telephone;
	public List<String> plat;
	
	public PreReservationRestaurantItem(){
		super();
	}
	
	public PreReservationRestaurantItem(String nom, String email, String telephone, List<String> plat) {
		super();
		this.nom = nom;
		this.email = email;
		this.telephone = telephone;
		this.plat = plat;
	}
	
	
}
