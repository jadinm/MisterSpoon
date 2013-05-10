package spoon.misterspoon;

import java.util.ArrayList;

public class Booking extends PreBooking {

	private Time heureReservation;
	private int nombrePlaces;
	private Date date;
	MySQLiteHelper sqliteHelper;

	public Booking(Restaurant restaurant, int nombrePlaces, Time heureReservation, Date date, ArrayList<Meal> commande) {//Constructor for the client
		super (restaurant, commande);
		this.heureReservation = heureReservation;
		this.nombrePlaces     = nombrePlaces;
		this.date = date;


	}

	public Booking(Client client, int nombrePlaces, Time heureReservation, Date date, ArrayList<Meal> commande) {//Constructor for the restaurantOwner
		super (client, commande);
		this.heureReservation = heureReservation;
		this.nombrePlaces     = nombrePlaces;
		this.date = date;
	}

	public Time getHeureReservation() {
		return this.heureReservation;
	}

	public int getNombrePlaces() {
		return this.nombrePlaces;
	}

	public Date getDate() {
		return this.date;
	}
}