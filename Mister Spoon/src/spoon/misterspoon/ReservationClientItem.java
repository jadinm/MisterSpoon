package spoon.misterspoon;

import java.util.List;

public class ReservationClientItem {
	
	private String heure;
	private String nomRest;
	private String telephone;
	private String places;
	private List<String> plat;
	
	public ReservationClientItem(){
		super();
	}
	
	public ReservationClientItem (String heure, String nomRest, String telephone, String places, List<String> plat) {
		super();
		this.heure = heure;
		this.nomRest = nomRest;
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

	public String getNomRest() {
		return nomRest;
	}

	public void setNomRest(String nom) {
		this.nomRest = nom;
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
