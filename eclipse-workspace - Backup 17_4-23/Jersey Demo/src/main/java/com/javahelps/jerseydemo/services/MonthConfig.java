package com.javahelps.jerseydemo.services;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MonthConfig {
	private static boolean VERBOSE = true;
	private static void print(String s) {
		if (VERBOSE) { 
			System.out.print(s);
		}
	}
	private static void println(String s) {
		if (VERBOSE) { 
			System.out.println(s);
		}
	}	
	
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

}
