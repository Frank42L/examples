package com.javahelps.jerseydemo.services;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;


// TODO Umbau auf REST API Ansatz gemäss https://jax.de/blog/software-architecture-design/restful-apis-richtig-gemacht/

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
	    	System.out.println("Routing POST/PUT/PATCH/DELETE if coming from normal HTML Pages which only support GET and POST");    	
	    	System.out.print("_rest_method = " + restMethod);    	
			if (restMethod.compareToIgnoreCase("patch") == 0) {
		    	System.out.println(" routed to : mergeBirthdayList");    	
				return mergeBirthdayList(user, uploadedInputStream, fileDetail);
			} else if (restMethod.compareToIgnoreCase("put") == 0) {
		    	System.out.println(" not supported yet");    	
			} else if (restMethod.compareToIgnoreCase("delete") == 0) {
		    	System.out.println(" not supported yet");    	
			}
		    throw new RuntimeException("Rest Method '_rest_method = " + restMethod + " is not supported");
		}
    	System.out.println("POST : routed to uploadBirthdayList");    	
		return uploadBirthdayList(user, uploadedInputStream, fileDetail);
	}
    
	
    private Response uploadBirthdayList(@PathParam("user") String user,
    		@FormDataParam("file") InputStream uploadedInputStream,
    		@FormDataParam("file") FormDataContentDisposition fileDetail) {
    	String json;  	
		BirthdayList bl;
    	
    	System.out.println("Upload and Replace (user = " + user + ") with File : " + fileDetail.getFileName());
    	try {
    		bl = BirthdayList.getBirthdaysFromStream(user, uploadedInputStream);
			BirthdayList.writeBirthdays2File(user, bl);
		} catch (IOException e) {
			e.printStackTrace();
		} 

    	json = BirthdayList.readBirthdaysFromFile2Json(user).toJson();
        return Response.ok() // 200
        		.entity(json)
        		.build();
    }
    
	@PATCH
    @Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
    public Response mergeBirthdayList(@PathParam("user") String user,
    		@FormDataParam("file") InputStream uploadedInputStream,
    		@FormDataParam("file") FormDataContentDisposition fileDetail) {
    	String json;  	
    	
    	System.out.println("Merge (user = " + user + ") with File : " + fileDetail.getFileName());
    	
    	BirthdayList blAll;
    	BirthdayList blToMerge;
    	
    	try {
			blToMerge = BirthdayList.getBirthdaysFromStream(user, uploadedInputStream);
        	blAll = BirthdayList.readBirthdaysFromFile2Json(user);
        	blAll.insert(blToMerge);
			BirthdayList.writeBirthdays2File(user, blAll);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	
    	json = BirthdayList.readBirthdaysFromFile2Json(user).toJson();
        return Response.ok() // 200
        		.entity(json)
        		.build();
    }

}
