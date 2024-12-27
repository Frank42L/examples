package com.javahelps.jerseydemo.services;

public class UtilVerbose {
	public static void print(boolean VERBOSE, String s) {
		print(VERBOSE, false, s);
	}

	public static void println(boolean VERBOSE, String s) {
		println(VERBOSE, false, s);
	}
	
	public static void print(boolean VERBOSE, boolean INFO, String s) {
		if (VERBOSE || INFO) { 
			System.out.print(s);
		}
	}

	public static void println(boolean VERBOSE, boolean INFO, String s) {
		if (VERBOSE || INFO) { 
			System.out.println("XXXXX"+s);
		}
	}
}
