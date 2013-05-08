package spoon.misterspoon;

import java.util.List;

public class ReservationRestaurantItem {
	
	public String heure;
	public String nom;
	public String telephone;
	public String places;
	public List<String> plat;
	
	public ReservationRestaurantItem(){
		super();
	}
	
	public ReservationRestaurantItem (String heure, String nom, String telephone, String places, List<String> plat) {
		super();
		this.heure = heure;
		this.nom = nom;
		this.telephone = telephone;
		this.places = places;
		this.plat = plat;
	}

}
