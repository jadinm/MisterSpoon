package spoon.misterspoon;

public class PreReservationClientItem {

	public String restaurant;
	public String prix;
	public String plat;
	public String quantite;
	
	public PreReservationClientItem(){
		super();
	}
	
	public PreReservationClientItem(String restaurant, String prix, String plat, String quantite) {
		super();
		this.restaurant = restaurant;
		this.prix = prix;
		this.plat = plat;
		this.quantite=quantite;
	}
}
