package spoon.misterspoon;

import java.util.ArrayList;

public class ReservationRestaurantActivityHeader {

	private String date;
	private ArrayList<ReservationRestaurantItem> reservationList = new ArrayList<ReservationRestaurantItem>();
	
	public ReservationRestaurantActivityHeader(String date,
			ArrayList<ReservationRestaurantItem> reservationList) {
		this.date = date;
		this.reservationList = reservationList;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public ArrayList<ReservationRestaurantItem> getReservationList() {
		return reservationList;
	}
	
	public void setReservationList(
			ArrayList<ReservationRestaurantItem> reservationList) {
		this.reservationList = reservationList;
	}
}
