package spoon.misterspoon;

public class Time implements Comparable<Time> {
	
	private int hour;
	private int minute;
	private int second;
	
	public Time(String time) {// (example: 10:30:00)
		
		String timeBis [] = time.split(":");
		
		this.hour = Integer.parseInt(timeBis[0]);
		this.minute = Integer.parseInt(timeBis[1]);
		this.second = Integer.parseInt(timeBis[2]);
	}
	
	public Time(int hour, int minute, int second) { 
		this.hour   = hour;
		this.minute = minute;
		this.second = second;
	}
	
	public int getHour() {
		return this.hour;
	}
	
	public int getMinute() {
		return this.minute;
	}
	
	public int getSecond() {
		return this.second;
	}
	
	public void setHour(int newHour) {
		this.hour = newHour;
	}
	
	public void setMinute(int newMinute) {
		this.minute = newMinute;
	}
	
	public void setSecond(int newSecond) {
		this.second = newSecond;
	}
	
	public String toString () {
		return hour + ":" + minute + ":" + second;
	}
	
	public int compareTo(Time otherTime){
		int t1 = this.getHour()*10000 + this.getMinute()*100 + this.getSecond();
		System.out.println(t1);
		int t2 = otherTime.getHour()*10000 + otherTime.getMinute()*100 + otherTime.getSecond();
		System.out.println(t2);
		return t1-t2;
	}
	
}
