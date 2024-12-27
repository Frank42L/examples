package com.javahelps.jerseydemo.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BirthdayList {
	private static boolean VERBOSE = false;
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
	

	
	private Set<BirthDayPerson> setBirthDayPersons;

	BirthdayList() {
		setBirthDayPersons = new TreeSet<BirthDayPerson>();
	};

	public Set<BirthDayPerson> getBirthdays() {
		return setBirthDayPersons;
	}

	public void setBirthdays(Set<BirthDayPerson> setBirthDayPersons) {
		this.setBirthDayPersons = setBirthDayPersons;
	}

	public void insert(BirthdayList bl) {
		setBirthDayPersons.addAll(bl.getBirthdays());
	}

	public void remove(BirthdayList bl) {
		setBirthDayPersons.removeAll(bl.getBirthdays());
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
	
	public BirthdayList filterPerson(String surname, String firstname) {
		BirthDayPerson bdp;
		BirthdayList bl = new BirthdayList();
		Iterator<BirthDayPerson> iter = setBirthDayPersons.iterator();
		
		while (iter.hasNext() ) {
			bdp = iter.next();
			if ( (firstname.compareTo(bdp.getFirstname()) == 0) && 
				 (surname.compareTo(bdp.getSurname()) == 0)) {
				bl.getBirthdays().add(bdp);
			}
		}
	    return bl;
	}

	public BirthdayList filterPerson(String surname, String firstname, 
			Integer year, Integer month, Integer day) {
		BirthDayPerson bdp;
		BirthdayList bl = new BirthdayList();
		Iterator<BirthDayPerson> iter = setBirthDayPersons.iterator();
		
		while (iter.hasNext() ) {
			bdp = iter.next();
			if ( (firstname.compareTo(bdp.getFirstname()) == 0) && 
				 (surname.compareTo(bdp.getSurname()) == 0) &&
				 (year.compareTo(bdp.getYear()) == 0) &&
				 (month.compareTo(bdp.getMonth()) == 0) &&
				 (day.compareTo(bdp.getDay()) == 0)) {
				bl.getBirthdays().add(bdp);
			}
		}
	    return bl;
	}

	static public BirthdayList readBirthdaysFromFile2Json( File f, final String user ) {
	    ObjectMapper mapper = new ObjectMapper();
	    BirthdayList bl = new BirthdayList();
		print("Username = " + user);
	    try {
	    	bl = mapper.readValue(f, BirthdayList.class);
			println('\t' + "Eintr�ge = " + bl.getBirthdays().size() + '\t' + "Filename = " + f.getAbsolutePath());
	    } catch (JsonProcessingException e) {
		       e.printStackTrace();
		    } catch (Exception ex) {
			    ex.printStackTrace();
		    }
		return bl;
	} 
	

	static public BirthdayList readBirthdaysFromFile2Json( final String user ) {
		String pathname = BirthdayConfig.getServerSideDataDir() + user + ".json";
		File f = Paths.get(pathname).toFile();
		return readBirthdaysFromFile2Json(f, user);
	} 
	
	static public File getFileAndCreateIfNotExists( final String user ) throws IOException {
		String pathname = BirthdayConfig.getServerSideDataDir() + user + ".json";
		println("Username = " + user);
		File f = Paths.get(pathname).toFile();
			if (f.createNewFile()) {
				println("File has been created: " + f.getAbsoluteFile()); 
			} else {
				println("File already exists: " + f.getAbsoluteFile());			
			}			
 		return f;
	} 
	
	
	static public BirthdayList getBirthdaysFromStream(InputStream is, final String comment) throws IOException {
    	BufferedReader bis = null;
    	ObjectMapper mapper = new ObjectMapper();
	    BirthdayList bl = new BirthdayList();
	    try {
	    	bis = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	    	bl = mapper.readValue(bis, BirthdayList.class);
	    	if (!comment.isBlank()) {
	    		println('\t' + comment + "\tEintr�ge = " + bl.getBirthdays().size());
	    	}
	    } catch (JsonProcessingException e) {
	       e.printStackTrace();
	       // Keep it empty
	    } catch (Exception ex) {
		    ex.printStackTrace();
	    } finally {
	    	if (bis != null) { bis.close(); }
	    }
	    return bl;
	}

	
	static public void writeBirthdays2File(final String user, BirthdayList bl) throws IOException {
    	File f;
    	FileOutputStream fos = null;
    	Writer out = null;

    	try {
        	f =  BirthdayList.getFileAndCreateIfNotExists( user );
			fos = new FileOutputStream(f);
        	out = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
        	out.write(bl.toJson());
		} finally {
			if (out != null) { out.close(); }
			if (fos != null) { try { fos.close(); } catch (IOException e) {	e.printStackTrace(); } }
		}
	}
}
