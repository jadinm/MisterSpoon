package spoon.misterspoon;

import java.util.ArrayList;

public class ShowReservationClientActivityHeader {

	private String date;
	private ArrayList<ShowReservationClientItem> reservationList = new ArrayList<ShowReservationClientItem>();
	
	public ShowReservationClientActivityHeader(String date,
			ArrayList<ShowReservationClientItem> reservationList) {
		this.date = date;
		this.reservationList = reservationList;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public ArrayList<ShowReservationClientItem> getReservationList() {
		return reservationList;
	}
	
	public void setReservationList(
			ArrayList<ShowReservationClientItem> reservationList) {
		this.reservationList = reservationList;
	}
}
