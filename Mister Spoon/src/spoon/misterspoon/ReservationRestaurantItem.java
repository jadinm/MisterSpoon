package spoon.misterspoon;

import java.util.List;

public class ReservationRestaurantItem {
	
	private String heure;
	private String nom;
	private String telephone;
	private String places;
	private List<String> plat;
	
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

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPlaces() {
		return places;
	}

	public void setPlaces(String places) {
		this.places = places;
	}

	public List<String> getPlat() {
		return plat;
	}

	public void setPlat(List<String> plat) {
		this.plat = plat;
	}

}
