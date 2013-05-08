package spoon.misterspoon;

import java.util.ArrayList;

public class ReservationClientActivityHeader {

	private String date;
	private ArrayList<ReservationClientItem> reservationList = new ArrayList<ReservationClientItem>();
	
	public ReservationClientActivityHeader(String date,
			ArrayList<ReservationClientItem> reservationList) {
		this.date = date;
		this.reservationList = reservationList;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public ArrayList<ReservationClientItem> getReservationList() {
		return reservationList;
	}
	
	public void setReservationList(
			ArrayList<ReservationClientItem> reservationList) {
		this.reservationList = reservationList;
	}
}
