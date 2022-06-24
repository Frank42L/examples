package com.javahelps.jerseydemo.services;

import java.io.File;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BirthdayList {
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

	public String toJson() {
		String json = "N/A";
	    ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(this);    		
			//json = mapper.writeValueAsString(setBirthDayPersons);    		
	    } catch (Exception ex) {
		    ex.printStackTrace();
	    }
		System.out.println("ResultingJSONstring = " + json);
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

	static public BirthdayList readBirthdaysFromFile2Json( final String user ) {
	    ObjectMapper mapper = new ObjectMapper();
	    BirthdayList bl = new BirthdayList();
		String pathname = "G:\\Privat\\Frank\\PRIVAT\\Fingerübungen\\airhack_adam_bien\\Frank42L-examples\\geburtstagsliste\\src\\data\\" + user + ".json";
		System.out.print("Username = " + user);
		File f = Paths.get(pathname).toFile();
	    try {
	    	bl = mapper.readValue(f, BirthdayList.class);
			System.out.println('\t' + "Einträge = " + bl.getBirthdays().size() + '\t' + "Filename = " + pathname);
	    } catch (JsonProcessingException e) {
	       e.printStackTrace();
	    } catch (Exception ex) {
		    ex.printStackTrace();
	    }
		return bl;
	} 
	
}
