package spoon.misterspoon;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class GPS {

	private double longitude;
	private double latitude;

	private LocationManager lManager;
	private Location location;

	public GPS (String coordonnees) {// (example: 50.668572,4.616146)

		String coordonneesBis [] = coordonnees.split(",");

		this.longitude = Double.parseDouble(coordonneesBis[0]);
		this.latitude = Double.parseDouble(coordonneesBis[1]);
	}

	public GPS(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude  = latitude;
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

	public boolean equals(GPS in) {
		return this.longitude == in.getLongitude() && this.latitude == in.getLatitude();
	}

	public void setLatitude(double newLat) {
		this.latitude = newLat;
	}

	public String toString () {
		return longitude + "," + latitude;
	}

	public int compareTo (GPS gps) {
		return (int) Math.sqrt((this.longitude - gps.longitude)*(this.longitude - gps.longitude) + (this.latitude - gps.latitude)*(this.latitude - gps.latitude));
	}

	public GPS updatePosition(Client client, LocationListener context, Activity active) {

		lManager = (LocationManager)active.getSystemService(Context.LOCATION_SERVICE);
		lManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, context);
		Location loc = lManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (loc == null){
			loc = lManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (loc == null) return null;
		}
		
		return new GPS (loc.getLongitude(), loc.getLatitude());
	}
}
