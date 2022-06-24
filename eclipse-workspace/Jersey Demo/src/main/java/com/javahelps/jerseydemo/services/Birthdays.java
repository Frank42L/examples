package com.javahelps.jerseydemo.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/birthdays/user/{user}")
public class Birthdays {
	
	
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
    @Path("/{surname}/{firstname}")
    @Produces("text/plain")
    public Response getBirthdayForPerson(@PathParam("user") String user, @PathParam("surname") String surname, @PathParam("firstname") String firstname) {
    	String json;
    	BirthdayList blAll;
    	
    	blAll = BirthdayList.readBirthdaysFromFile2Json(user);
    	json = "This is the List for " + firstname + " " + surname + "\n" + blAll.filterPerson(surname, firstname).toJson();
        return Response.ok() // 200
        		.entity(json)
        		.build();
    }
    


}
