package com.javahelps.jerseydemo.services;

public class MonthConfig {
	private String cnt;
	private String month;
	private String monthShort;
	private String monthNumber;
	
	
	MonthConfig() {
		cnt = "1";
		month = "Januar";
		monthShort = "Jan";
		monthNumber = "01";
	};

	public String getCnt() {
		return cnt;
	}
	public void setCnt(String cnt) {
		this.cnt = cnt;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getMonth_short() {
		return monthShort;
	}
	public void setMonth_short(String monthShort) {
		this.monthShort = monthShort;
	}
	public String getMonth_number() {
		return monthNumber;
	}
	public void setMonth_number(String monthNumber) {
		this.monthNumber = monthNumber;
	}

	public String toString() {
		return cnt + " " + monthNumber + " " + month + " " + monthShort;
	}
}
