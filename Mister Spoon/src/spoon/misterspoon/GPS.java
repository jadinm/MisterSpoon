package spoon.misterspoon;

public class GPS {

	private double longitude;
	private double latitude;
	
	public GPS (String coordonnees) {// (example: 50.668572,4.616146)
		
		String coordonneesBis [] = coordonnees.split(",");
		
		this.longitude = Double.parseDouble(coordonneesBis[0]);
		this.latitude = Double.parseDouble(coordonneesBis[1]);
	}
	
	public GPS(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude  = latitude;
	}
	
	public GPS getItineraire (GPS depart, GPS arrivee) {//To discuss
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
	
	public String toString () {
		return longitude + "," + latitude;
	}
	
	public GPS updatePosition(Client client) {//To discuss
		return null;
	}
}
