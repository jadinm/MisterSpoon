package spoon.misterspoon;

import java.util.ArrayList;

public class PreBooking {
	
	protected ArrayList <Meal> commande;
	protected Restaurant restaurant;
	protected Client client;
	
	public PreBooking(Restaurant restaurant, ArrayList <Meal> commande) {//Constructor for the client
		this.commande = commande;
		this.restaurant = restaurant;
	}
	
	public PreBooking(Client client, ArrayList <Meal> commande) {//Constructor for the restaurantOwner
		this.commande = commande;
		this.client = client;
	}
	
	public Restaurant getRestaurant() {
		return restaurant;
	}
	
	public ArrayList <Meal> getCommande() {
		return commande;
	}
	
	public Client getClient() {
		return client;
	}
	
}
