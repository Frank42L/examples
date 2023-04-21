package com.javahelps.jerseydemo.services;

import java.util.Objects;

public class BirthDayPerson extends Object implements Comparable<BirthDayPerson>{
	private String uuid; // unique ID
	private String uuid2Original; // unique ID to original or master instance of this person (if known)
	private int hash; // MD5-512 
	private String firstname;
	private String surname;
	private Integer day;
	private Integer month;
	private Integer year;
	private BirthDayPerson updatevalues;
	
	public BirthDayPerson() {
		firstname = "";
		surname = "";
		day = 0;
		month = 0;
		year = 0;
		updatevalues = null;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getHash() {
		// is recalculated whenever used instead of taken from the file
		// The calculated hash may be written to a file to be used in other languages (wihtout recalculation need)
		hash = this.hashCode();
		return hash;
	}
	public void setHash(String hashAsString) {
		this.hash = Integer.parseInt(hashAsString);
	}
	public String getUuid2Original() {
		return uuid2Original;
	}
	public void setUuid2Original(String uuid2Original) {
		this.uuid2Original = uuid2Original;
	}
	public void setUpdatevalues(BirthDayPerson updatevalues) {
		this.updatevalues = updatevalues;
	}
	public BirthDayPerson getUpdatevalues() {
		return this.updatevalues;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = (day == null) ? 0: day;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = (month == null) ? 0: month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) { 
		this.year = (year == null) ? 0: year;
	}
	public String toString() {
		return String.format("%02d", getDay()) + "." 
				+ String.format("%02d", getMonth()) +"." 
				+ String.format("%04d", getYear()) 
				+ "\t" + getFirstname() 
				+ " " + getSurname();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(day, firstname, month, surname, year);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof BirthDayPerson))
			return false;
		BirthDayPerson other = (BirthDayPerson) obj;
		return Objects.equals(day, other.day) && Objects.equals(firstname, other.firstname)
				&& Objects.equals(month, other.month) && Objects.equals(surname, other.surname)
				&& Objects.equals(year, other.year);
	}
	@Override
	public int compareTo(BirthDayPerson bdp) {
		int result = 0;
		if (result == 0) { result = this.surname.compareTo(bdp.surname); }
		if (result == 0) { result = this.firstname.compareTo(bdp.firstname); }
		if (result == 0) { result = this.year.compareTo(bdp.year) ; }
		if (result == 0) { result = this.month.compareTo(bdp.month); }
		if (result == 0) { result = this.day.compareTo(bdp.day); }
		return result;
	}

	public void update() {
		BirthDayPerson p = getUpdatevalues();
		if (p != null) {
			if (p.getFirstname() != null && !p.getFirstname().isEmpty()) { setFirstname(p.getFirstname()); }
			if (p.getSurname()   != null && !p.getSurname().isEmpty()) { setSurname(p.getSurname()); }
			if (p.getDay()       != null) { setDay(p.getDay()); }
			if (p.getMonth()     != null) { setMonth(p.getMonth()); }
			if (p.getYear()      != null) { setYear(p.getYear()); }
		}
		markAsUpdated();
	}
	
	private void markAsUpdated() {
		setUpdatevalues(null);
	}

}
