package spoon.misterspoon;

public class Date {
	
	private String day;
	private String month;
	private String year;

	public Date(String date) {
		String tmp [] = date.split("-");
		day = tmp[2];
		month = tmp[1];
		year = tmp[0];
	}
	
	public Date(String year, String month, String day) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public String getDay () {
		return day;
	}
	public String getMonth () {
		return month;
	}
	public String getYear () {
		return year;
	}
	
	public String toString () {
		return year + "-" + month + "-"  + day;
	}
	
	public boolean equals (Date date) {
		if (this.year.equals(date.year) && this.month.equals(date.month) && this.day.equals(date.day)) {
			return true;
		}
		return false;
	}

}
