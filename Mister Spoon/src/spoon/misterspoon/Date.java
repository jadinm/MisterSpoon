package spoon.misterspoon;

public class Date {
	
	private String day;
	private String month;
	private String year;

	public Date(String date) {
		String tmp [] = date.split("-");
		if (tmp.length==2) {
			day = tmp[1];
			month = tmp[0];
			year = null;
		}
		else {
			day = tmp[2];
			month = tmp[1];
			year = tmp[0];
		}
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
		
		if(Integer.parseInt(month) < 10) {
			month = "0" + month;
		}
		if(Integer.parseInt(day) < 10) {
			day = "0" + day;
		}
		
		if (year==null) {
			
			return month + "-" + day;
		}
		
		return year + "-" + month + "-"  + day;
	}
	
	public boolean equals (Date date) {
		if (date.year == null && this.year == null) {
			return this.month.equals(date.month) && this.day.equals(date.day);
		}
		else if (date.year != null && this.year != null) {
			return this.year.equals(date.year) && this.month.equals(date.month) && this.day.equals(date.day);
		}
		return false;
	}

	public int compareTo(Date date) {
		if(Integer.parseInt(year) != Integer.parseInt(date.getYear())){
			return Integer.parseInt(year) - Integer.parseInt(date.getYear());
		}
		else if(Integer.parseInt(month) != Integer.parseInt(date.getMonth())){
			return Integer.parseInt(month) - Integer.parseInt(date.getMonth());
		}
		else if(Integer.parseInt(day) != Integer.parseInt(date.getDay())){
			return Integer.parseInt(day) - Integer.parseInt(date.getDay());
		}
		else return 0;
	}

}