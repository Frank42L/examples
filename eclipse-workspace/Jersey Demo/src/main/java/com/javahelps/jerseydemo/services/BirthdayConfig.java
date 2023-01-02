package com.javahelps.jerseydemo.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BirthdayConfig {
	private static final String DEFAULT_CONFIG_NAME = "default";
	private static final String SERVER_SIDE_DATA_DIR = "G:\\Privat\\Frank\\PRIVAT\\Fingerübungen\\Geburtstagsliste\\data_serverside\\";
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
	

	
	private ArrayList<MonthConfig> listMonthConfigs;

	BirthdayConfig() {
		listMonthConfigs = new ArrayList<MonthConfig>();
	};

	public ArrayList<MonthConfig> getMonths() {
		return listMonthConfigs;
	}

	public void setMonths(ArrayList<MonthConfig> listMonthConfigs) {
		this.listMonthConfigs = listMonthConfigs;
	}

	public String toJson() {
		String json = "N/A";
	    ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(this);    		
	    } catch (Exception ex) {
		    ex.printStackTrace();
	    }
		println("ResultingJSONstring = " + json);
	    return json;		
	}

	
	static private BirthdayConfig readConfigFromFile(final String user) {
	    ObjectMapper mapper = new ObjectMapper();
	    BirthdayConfig config = new BirthdayConfig();
		String pathname = SERVER_SIDE_DATA_DIR + user + ".config.json";
		println("Configuration for Username = " + user);
		File f = Paths.get(pathname).toFile();
	    try {
			if (f.exists()) {
		    	config = mapper.readValue(f, BirthdayConfig.class);
				println('\t' + "Einträge = " + config.getMonths().size() + '\t' + "Filename = " + pathname);
			} 
	    } catch (JsonProcessingException e) {
	       e.printStackTrace();
	    } catch (Exception ex) {
		    ex.printStackTrace();
	    }			
		return config;
	} 
	
	static public void prepareDefaultConfigFile() {  
		InputStream is;
		String resourceFileName = DEFAULT_CONFIG_NAME + ".config.json";
		String pathname = SERVER_SIDE_DATA_DIR + resourceFileName;
		Path path = Paths.get(pathname);
		File f = Paths.get(pathname).toFile();
	    try {
			if (!f.exists()) {
				is = new BirthdayConfig().getClass().getResourceAsStream("/" + resourceFileName);
				Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
			}
	    } catch (Exception ex) {
		    ex.printStackTrace();
	    }			
	} 

	static public BirthdayConfig readDefaultConfig() {
	    BirthdayConfig config;
	    
		prepareDefaultConfigFile();
	    // Read Default Config now.
	    return readConfigFromFile(DEFAULT_CONFIG_NAME);
	} 

	static public BirthdayConfig readConfigFromFile2Json( final String user ) {
	    BirthdayConfig config = readConfigFromFile(user);
	    if (config.getMonths().size() <= 0) {
	    	config = readDefaultConfig();
	    }
	    return config;
	} 

	static public BirthdayConfig getBirthdayConfigFromStream(InputStream is, final String comment) throws IOException {
    	BufferedReader bis = null;
    	ObjectMapper mapper = new ObjectMapper();
    	BirthdayConfig config = new BirthdayConfig();
	    try {
	    	bis = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	    	config = mapper.readValue(bis, BirthdayConfig.class);
	    	if (!comment.isBlank()) {
	    		println('\t' + comment + "\tEinträge = " + config.getMonths().size());
	    	}
	    } catch (JsonProcessingException e) {
	       e.printStackTrace();
	       // Keep it empty
	    } catch (Exception ex) {
		    ex.printStackTrace();
	    } finally {
	    	if (bis != null) { bis.close(); }
	    }
	    return config;
	}

}
