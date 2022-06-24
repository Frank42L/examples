package com.javahelps.jerseydemo.services;

import java.util.Iterator;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/birthdays/users")
public class BirthdaysMultiple {
	
	private BirthdayList readBirthdaysFromFile2Json( final Set<String> users ) {
		Iterator<String> iter = users.iterator();
		BirthdayList bl = new BirthdayList();
	
		while (iter.hasNext())
		{
			bl.insert(BirthdayList.readBirthdaysFromFile2Json(iter.next()));
		}
		System.out.println("Resultat Einträge nach Duplikaterkennung = " + bl.getBirthdays().size());
		return bl;
	}
	
    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getBirthdayList(@QueryParam("user") final Set<String> users) {
        String json = "N/A";
        ObjectMapper mapper = new ObjectMapper();
        BirthdayList bl;
        try {
        	bl = readBirthdaysFromFile2Json(users);
    		json = mapper.writeValueAsString(bl);    		
    		System.out.println("ResultingJSONstring = " + json);
        } catch (Exception ex) {
    	    ex.printStackTrace();
        }
        return Response.ok() // 200
        		.entity(json)
        		.build();
    }
    
    @GET
    @Path("/{surname}/{firstname}")
    @Produces("text/plain")
    public Response getBirthdayForPerson(@QueryParam("user") final Set<String> users, @PathParam("surname") String surname, @PathParam("firstname") String firstname) {
    	String json;
    	BirthdayList blAll;
    	
    	blAll = readBirthdaysFromFile2Json(users);
    	json = "This is the List for " + firstname + " " + surname + "\n" + blAll.filterPerson(surname, firstname).toJson();
        return Response.ok() // 200
        		.entity(json)
        		.build();
    }
    


}
