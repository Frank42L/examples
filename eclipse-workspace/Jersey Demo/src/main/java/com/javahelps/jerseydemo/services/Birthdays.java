package com.javahelps.jerseydemo.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVFormat.Predefined;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;


// TODO Umbau auf REST API Ansatz gemäss https://jax.de/blog/software-architecture-design/restful-apis-richtig-gemacht/

@Path("/birthdays/user/{user}")
public class Birthdays {
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
	private static void println(String s, boolean info) {
		if (VERBOSE || info) { 
			System.out.println(s);
		}
	}
	
    static final Character SEMICOLON = Character.valueOf(';');


    /**
     * Standard Semicolon Separated Value format, as for {@link #RFC4180} but allowing empty lines.
     *
     * <p>
     * The {@link Builder} settings are:
     * </p>
     * <ul>
     * <li>{@code setDelimiter(';')}</li>
     * <li>{@code setQuote('"')}</li>
     * <li>{@code setRecordSeparator("\r\n")}</li>
     * <li>{@code setIgnoreEmptyLines(true)}</li>
     * <li>{@code setAllowDuplicateHeaderNames(true)}</li>
     * <li>{@code setHeader()}</li>
     * </ul>
     *
     * @see Predefined#Default
     */
    public static final CSVFormat DEFAULT_SEMICOLON = CSVFormat.DEFAULT.builder()
    		.setHeader()
    		.setDelimiter(SEMICOLON).build();



	@GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getBirthdayList(@PathParam("user") String user) {
    	String json;  	
    	json = BirthdayList.readBirthdaysFromFile2Json(user).toJson();
        return Response.ok() // 200
        		.entity(json)
        		.build();
    }
    	
    @GET
    @Path("/config")
    @Produces("text/plain")
    public Response getConfigrationForPerson(@PathParam("user") String user) {
    	String json;
    	json = BirthdayConfig.readConfigFromFile2Json(user).toJson();
    	return Response.ok() // 200
        		.entity(json)
        		.build();
    }

    @GET
    @Path("/{surname}/{firstname}")
    @Produces("text/plain")
    public Response getBirthdayForPerson(@PathParam("user") String user, @PathParam("surname") String surname, @PathParam("firstname") String firstname) {
    	String json;
    	BirthdayList blAll;
    	
    	blAll = BirthdayList.readBirthdaysFromFile2Json(user);
    	json = blAll.filterPerson(surname, firstname).toJson();
        return Response.ok() // 200
        		.entity(json)
        		.build();
    }
    
    @GET
    @Path("/{surname}/{firstname}/{year}/{month}/{day}")
    @Produces("text/plain")
    public Response getBirthdayForPerson(@PathParam("user") String user, @PathParam("surname") String surname, @PathParam("firstname") String firstname,
    		@PathParam("year") String year, @PathParam("month") String month, @PathParam("day") String day) {
    	String json;
    	BirthdayList blAll;

    	println("Search within (user = " + user + ") for : " + firstname + " " + surname + " " + day + "." + month + "." + year);

    	blAll = BirthdayList.readBirthdaysFromFile2Json(user);
    	json = blAll.filterPerson(
    			surname, 
    			firstname, 
    			Integer.valueOf(year), 
    			Integer.valueOf(month),
    			Integer.valueOf(day)).toJson();
        return Response.ok() // 200
        		.entity(json)
        		.build();
    }
	
    @POST
    @Path("/{surname}/{firstname}/{year}/{month}/{day}")
    @Produces("text/plain")
    public Response updateBirthdayForPerson(@PathParam("user") String user, @PathParam("surname") String surname, @PathParam("firstname") String firstname,
    		@PathParam("year") String year, @PathParam("month") String month, @PathParam("day") String day,
    		@FormDataParam("_rest_method") String restMethod,
    		@FormDataParam("year")  String updateYear,
    		@FormDataParam("month") String updateMonth,
    		@FormDataParam("day")   String updateDay
    		) {
    	String json;
    	BirthdayList blAll;
    	BirthdayList blUpdated;
    	BirthDayPerson p;

    	println("Update (user = " + user + ") for : " + firstname + " " + surname + " " + day + "." + month + "." + year);
    	println("\twith " + updateDay + "." + updateMonth + "." + updateYear);

    	// Search person(s)
    	blAll = BirthdayList.readBirthdaysFromFile2Json(user);
    	blUpdated = blAll.filterPerson(
    			surname, 
    			firstname, 
    			Integer.valueOf(year), 
    			Integer.valueOf(month),
    			Integer.valueOf(day));

    	// Remove the old persons  
    	blAll.remove(blUpdated);
    	
    	// Update birthdate now
    	Iterator<BirthDayPerson> iter;
    	iter = blUpdated.getBirthdays().iterator();
    	while (iter.hasNext()) {
    		p = iter.next();
    		p.setDay(Integer.valueOf(updateDay));
    		p.setMonth(Integer.valueOf(updateMonth));
    		p.setYear(Integer.valueOf(updateYear));
    	}
    	
    	// Update File now
    	blAll.insert(blUpdated);
    	
    	// read from file (might be not necessary, but ensures, file has fully been written
		try {
			BirthdayList.writeBirthdays2File(user, blAll);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
    	json = BirthdayList.readBirthdaysFromFile2Json(user).toJson();
		
        return Response.ok() // 200
        		.entity(json)
        		.build();
    }

    // Because normal HTML browser do only support POST and GET, they do not support the 
    // typically REST Calls PUT/DELETE/PATCH. To support this in case one wants to use the REST API
    // with normal browsers, the can add a hidden field (one of the following three optionas):
    // - <input name="_rest_method" type="hidden" value="patch">
    // - <input name="_rest_method" type="hidden" value="delete">
    // - <input name="_rest_method" type="hidden" value="put">
    // example
    // <form action="http://localhost:8080/jerseydemo/birthdays/user/frank.loeliger.test" 
    //	    method="post"
    //	    enctype="multipart/form-data">
    //	        <p>Ergänze die Geburtstagsliste (Update)</p>
    //	        <input name="_rest_method" type="hidden" value="patch">
    //	        <input type="file" id="upload_file" name="file" accept="application/json">
    //	        <input type="submit">
    //	    </form>
    // see fore further info: https://laraveldaily.com/theres-no-putpatchdelete-method-or-how-to-build-a-laravel-form-manually/
    // 
	@POST
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	public Response routingToRESTSubroutines(@PathParam("user") String user,
    		@FormDataParam("_rest_method") String restMethod,
    		@FormDataParam("file") InputStream uploadedInputStream,
    		@FormDataParam("file") FormDataContentDisposition fileDetail) {
		if (restMethod != null) {
	    	println("Routing POST/PUT/PATCH/DELETE if coming from normal HTML Pages which only support GET and POST");    	
	    	print("_rest_method = " + restMethod);    	
			if (restMethod.compareToIgnoreCase("patch") == 0) {
		    	println(" routed to : mergeBirthdayList");    	
				return mergeBirthdayList(user, uploadedInputStream, fileDetail);
			} else if (restMethod.compareToIgnoreCase("put") == 0) {
		    	println(" not supported yet");    	
			} else if (restMethod.compareToIgnoreCase("delete") == 0) {
		    	println(" not supported yet");    	
			}
		    throw new RuntimeException("Rest Method '_rest_method = " + restMethod + " is not supported");
		}
    	println("POST : routed to uploadBirthdayList");    	
		return uploadBirthdayList(user, uploadedInputStream, fileDetail);
	}
    
	
    private Response uploadBirthdayListFromJSON(String user,
    		InputStream uploadedInputStream,
    		FormDataContentDisposition fileDetail) {
    	String json;  	
		BirthdayList bl;
    	
    	println("Upload and Replace (user = " + user + ") with File : " + fileDetail.getFileName());
    	try {
    		bl = BirthdayList.getBirthdaysFromStream(uploadedInputStream, user);
			BirthdayList.writeBirthdays2File(user, bl);
		} catch (IOException e) {
			e.printStackTrace();
		} 

    	json = BirthdayList.readBirthdaysFromFile2Json(user).toJson();
        return Response.ok() // 200
        		.entity(json)
        		.build();
    }
   
    private Response uploadBirthdayListFromCSV(String user,
    		InputStream uploadedInputStream,
    		FormDataContentDisposition fileDetail) {
    	String json;  	
	   	BirthdayList blAll;
    	BirthdayList blToMerge;
 	
		CSVParser parser;
		Reader reader = new InputStreamReader(uploadedInputStream);
		int ignoredRows = 0;
		int processedRows = 0;
		String ignored = " rows ignored because of missing ";
		String ignoredPrename = "\tprename:\t";
		String ignoredSurname = "\tsurname:\t";
		String ignoredBirthdate = "\tbirthdate:\t";
    	
    	println("Upload and Replace (user = " + user + ") with File : " + fileDetail.getFileName());
    	
		blToMerge = new BirthdayList();

    	try {
    		parser = CSVParser.parse(reader, Birthdays.DEFAULT_SEMICOLON);
    		for (String sHeader : parser.getHeaderNames()) {
    			println("\t" + sHeader, true);    			
    		}
    		for (CSVRecord record : parser) {
    			String prename = record.get(2);
    			String surname = record.get(1);
    			String dateWithYear = record.get(18);
    			String dateWithoutYear = record.get(19);
    			
    			if (prename.isEmpty()) {
    				ignoredRows++;
    				if (ignoredPrename.length() > 10) {ignoredPrename += ",";} 
    				ignoredPrename += record.getRecordNumber();	
    			} else if (surname.isEmpty()) {
    				ignoredRows++;
    				if (ignoredSurname.length() > 10) {ignoredSurname += ",";} 
    				ignoredSurname += record.getRecordNumber();
    			} else if (dateWithYear.isEmpty() && dateWithoutYear.isEmpty()) {
    				ignoredRows++;
    				if (ignoredBirthdate.length() > 12) {ignoredBirthdate += ",";} 
    				ignoredBirthdate += record.getRecordNumber();
    			} else {
    				String date;
    				String sDay;
    				String sMonth;
    				String sYear;
    				processedRows++;
    				if (dateWithYear.isEmpty()) {
    					date = dateWithoutYear + "0000";
    				} else {
    					date = dateWithYear;
    				}
    	   			println(record.getRecordNumber() 
					+ "\t" + prename
					+ "\t" + surname
					+ "\t" + date
					, true);	
    	   			String[] s = date.split("\\.");
    	   			sDay = s[0];
    	   			sMonth = s[1];
    	   			sYear = s[2];
    	   			BirthDayPerson p = new BirthDayPerson();
    	   			p.setFirstname(prename);
    	   			p.setSurname(surname);
    	   			p.setDay(Integer.parseInt(sDay));
    	   			p.setMonth(Integer.parseInt(sMonth));
    	   			p.setYear(Integer.parseInt(sYear));
    	   			blToMerge.getBirthdays().add(p);
    			}	
    		}
    		println("Uploading CSV for user " + user + " file " + fileDetail.getFileName(), true);
    		println("Total " + processedRows + " rows processed and " + ignoredRows + " " + ignored, true);
    		println(ignoredPrename, true);
    		println(ignoredSurname, true);
    		println(ignoredBirthdate, true);
    		
        	try {
            	File f =  BirthdayList.getFileAndCreateIfNotExists(user);
            	blAll = BirthdayList.readBirthdaysFromFile2Json(user);
            	blAll = mergeUpdatedValues(blAll, blToMerge);
    			BirthdayList.writeBirthdays2File(user, blAll);
    		} catch (IOException e1) {
    			e1.printStackTrace();
    		}

		} catch (IOException e) {
			e.printStackTrace();
		} 

      	json = BirthdayList.readBirthdaysFromFile2Json(user).toJson();
        return Response.ok() // 200
        		.entity(json)
        		.build();
    }
 
    private String getExtension(final String filename) {
    	String extension = "";
    	int i = filename.lastIndexOf('.');
    	if (i > 0) {
    	    extension = filename.substring(i+1);
    	}
    	return extension;
    }

    private Response uploadBirthdayList(String user,
    		InputStream uploadedInputStream,
    		FormDataContentDisposition fileDetail) {
        String extension = getExtension(fileDetail.getFileName());
    	if (extension.equalsIgnoreCase("json")) {
    		return uploadBirthdayListFromJSON(user, uploadedInputStream, fileDetail);
    	} else if (extension.equalsIgnoreCase("csv")) {
    		return uploadBirthdayListFromCSV(user, uploadedInputStream, fileDetail);
    	} else {
    		return Response.status(404, "<" + extension + "> is not a supported type").build();
    	}
    }
    
	@PATCH
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
    public Response mergeBirthdayList(@PathParam("user") String user,
    		@FormDataParam("file") InputStream uploadedInputStream,
    		@FormDataParam("file") FormDataContentDisposition fileDetail) {
    	String json;  	
    	
    	println("Merge (user = " + user + ") with File : " + fileDetail.getFileName());
    	
    	BirthdayList blAll;
    	BirthdayList blToMerge;
    	
    	try {
			blToMerge = BirthdayList.getBirthdaysFromStream(uploadedInputStream, user);
        	blAll = BirthdayList.readBirthdaysFromFile2Json(user);
        	blAll = mergeUpdatedValues(blAll, blToMerge);
			BirthdayList.writeBirthdays2File(user, blAll);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	
    	json = BirthdayList.readBirthdaysFromFile2Json(user).toJson();
        return Response.ok() // 200
        		.entity(json)
        		.build();
    }
	
	private BirthdayList mergeUpdatedValues(BirthdayList blAll, BirthdayList blToMerge) {
		for (BirthDayPerson p : blToMerge.getBirthdays()) {
			print("\t " + p.toString() + "\t: ");
			if (p.getUpdatevalues() != null) {
				// remove outdated persons 
				if (blAll.getBirthdays().remove(p)) { 
					print("-");
				}
				p.update();
				print("u");
			}
			if (blAll.getBirthdays().add(p)) {
				print("+");
			};
			println("");
		}
		
    	return blAll;
	}
	
	

}
