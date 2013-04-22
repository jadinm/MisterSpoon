package spoon.misterspoon;

public class GPS {

	private double longitude;
	private double latitude;
	
	public GPS (String coordonnees) {//To implement
		
	}
	
	public GPS(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude  = latitude;
	}
	
	public GPS getItineraire (GPS depart, GPS arrivee) {//To correct
		return null;
	}
	
	public double getLongitude() {
		return this.longitude;
	}
	
	public double getLatitude() {
		return this.latitude;
	}
	
	public void setLongitude(double newLong) {
		this.longitude = newLong;
	}
	
	public void setLatitude(double newLat) {
		this.latitude = newLat;
	}
	
	public String toString () {//To implement
		
	}
	
	public GPS updatePosition(Client client) {//To correct
		return null;
	}
}
