package spoon.misterspoon;



public class OpenHour {
	
	private Time openTime;
	private Time closeTime;
	private String openDay;
	
	public static final String [] openDayTable = new String [] {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};
	
	public OpenHour(String openDay, Time openTime, Time closeTime) {
		this.openTime  = openTime;
		this.closeTime = closeTime;
		this.openDay   = openDay;
	}
	
	public OpenHour(String text) {//text must be got by the toString() method
		String []tmp = text.split(" ");


		this.openDay   = tmp[1];
		this.openTime  = new Time (tmp[3]);
		this.closeTime = new Time (tmp[5]);
		
	}
	
	public Time getOpenTime() {
		return this.openTime;
	}
	
	public Time getCloseTime() {
		return this.closeTime;
	}
	
	public String getOpenDay() {
		return this.openDay;
	}
	
	public void setOpenTime(Time newOpenTime) {
		this.openTime = newOpenTime;
	}
	
	public void setCloseTime(Time newCloseTime) {
		this.closeTime = newCloseTime;
	}
	
	public void setOpenDay(String newOpenDay) {
		this.openDay = newOpenDay;
	}
	
	public String toString () {
		
		return "Le " + openDay + " de " + openTime.toString() + " à " + closeTime.toString();
	}
}