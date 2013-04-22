package spoon.misterspoon;

public class City {

	private String city;
	private GPS position;
	
	public City(String city, GPS position) {
        this.city     = city;
        this.position = position;
	}
	
	public String getCityName() {
		return this.city;
	}
	
	public GPS getPosition() {
		return this.position;
	}
	
	public void setPosition(GPS position) {
		this.position = position;
	}
}
