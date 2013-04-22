package spoon.misterspoon;

public class OpenHour {
	
	private Time openTime;
	private Time closeTime;
	private String openDay;
	
	public OpenHour(String openDay, Time openTime, Time closeTime) {
		this.openTime  = openTime;
		this.closeTime = closeTime;
		this.openDay   = openDay;
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
	
}
