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
	private static final String SERVER_SIDE_DATA_DIR2 = "/data_serverside/";
	private static boolean VERBOSE = true;
	
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
		UtilVerbose.println(VERBOSE, "ResultingJSONstring = " + json);
	    return json;		
	}

	
	static private BirthdayConfig readConfigFromFile(final String user) {
	    ObjectMapper mapper = new ObjectMapper();
	    BirthdayConfig config = new BirthdayConfig();
		String pathname = getServerSideDataDir() + user + ".config.json";
		UtilVerbose.println(VERBOSE, "Configuration for Username = " + user);
		File f = Paths.get(pathname).toFile();
	    try {
			if (f.exists()) {
		    	config = mapper.readValue(f, BirthdayConfig.class);
				UtilVerbose.println(VERBOSE, '\t' + "Einträge = " + config.getMonths().size() + '\t' + "Filename = " + pathname);
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
		String pathname = getServerSideDataDir() + resourceFileName;
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
	    		UtilVerbose.println(VERBOSE, '\t' + comment + "\tEinträge = " + config.getMonths().size());
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
	
	static private boolean dirExists(String pathname) {
		boolean exists = false;
		File f = Paths.get(pathname).toFile();
		exists = (f.exists() && f.isDirectory());
		UtilVerbose.println(VERBOSE, "\t try server-side-directory Version 3.2  " + pathname + " : " + exists);
		// System.err.UtilVerbose.println(VERBOSE, "\t try server-side-directory " + pathname + " : " + exists);
    	return exists;
	}
	
	static public String getServerSideDataDir() {
		if (dirExists(SERVER_SIDE_DATA_DIR2)) {
			return SERVER_SIDE_DATA_DIR2;
		} else if (dirExists(SERVER_SIDE_DATA_DIR)) {
			return SERVER_SIDE_DATA_DIR;
		} else {
			return "\\server-side-dir-does-not-exist";
		}
	}

}
