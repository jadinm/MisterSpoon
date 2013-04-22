package spoon.misterspoon;

import java.util.ArrayList;

public class Booking extends PreBooking {
		
	private Time heureReservation;
	private int nombrePlaces;
	
	public Booking(Restaurant restaurant, int nombrePlaces, Time heureReservation, ArrayList<Meal> commande) {//Constructor for the client
		super (restaurant, commande);
		this.heureReservation = heureReservation;
		this.nombrePlaces     = nombrePlaces;
	}
	
	public Booking(Client client, int nombrePlaces, Time heureReservation, ArrayList<Meal> commande) {//Constructor for the restaurantOwner
		super (client, commande);
		this.heureReservation = heureReservation;
		this.nombrePlaces     = nombrePlaces;
	}
	
	public Time getHeureReservation() {
		return this.heureReservation;
	}
	
	public int getNombrePlaces() {
		return this.nombrePlaces;
	}
}
