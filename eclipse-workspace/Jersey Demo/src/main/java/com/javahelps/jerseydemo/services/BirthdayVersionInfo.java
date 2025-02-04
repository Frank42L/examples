package com.javahelps.jerseydemo.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/birthdays")
public class BirthdayVersionInfo {
	private static final Integer DEFAULT_VERSION_MAJOR = 1;
	private static final Integer DEFAULT_VERSION_MINOR = 0;
	private static final Integer DEFAULT_VERSION_REVISION = 8;
	private static final Integer DEFAULT_VERSION_DATA_MAJOR = 1;
	private static final Integer DEFAULT_VERSION_DATA_MINOR = 0;
	private static final Integer DEFAULT_VERSION_DATA_REVISION = 0;
	private static final String DEFAULT_VERSION_DESCRIPTION = "Alpha Release, Not stable";
	
	private static boolean VERBOSE = true;
	
	private Integer versionCodeMajor = DEFAULT_VERSION_MAJOR;
	private Integer versionCodeMinor = DEFAULT_VERSION_MINOR;
	private Integer versionCodeRevision = DEFAULT_VERSION_REVISION;
	private Integer versionDataMajor = DEFAULT_VERSION_DATA_MAJOR;
	private Integer versionDataMinor = DEFAULT_VERSION_DATA_MINOR;
	private Integer versionDataRevision = DEFAULT_VERSION_DATA_REVISION;
	private String  versionDescription = DEFAULT_VERSION_DESCRIPTION;
	
/*	BirthdayVersionInfo() {
		versionCodeMajor = DEFAULT_VERSION_MAJOR;
		versionCodeMinor = DEFAULT_VERSION_MINOR;
		versionCodeRevision = DEFAULT_VERSION_REVISION;
		versionDataMajor = DEFAULT_VERSION_DATA_MAJOR;
		versionDataMinor = DEFAULT_VERSION_DATA_MINOR;
		versionDataRevision = DEFAULT_VERSION_DATA_REVISION;
		versionDescription = DEFAULT_VERSION_DESCRIPTION;
	};
*/
    
	public String getVersion() {
		return versionCodeMajor + "." + versionCodeMinor + "." + versionCodeRevision;
	}

	public String getVersionData() {
		return versionDataMajor + "." + versionDataMinor + "." + versionDataRevision;
	}

	public String getVersionInfo() {
		return versionDescription + " [version=" + getVersion() + " - dataversion=" + getVersionData() + "]";				
	}

    @GET
    @Path("/version")
    @Produces("text/plain")
    @JsonIgnore
    public Response versionInfo() {
    	String json;
    	json = toJson();
    	return Response.ok() // 200
        		.entity(json)
        		.build();
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


	static public BirthdayVersionInfo getBirthdayVersionInfoFromStream(InputStream is, final String comment) throws IOException {
    	BufferedReader bis = null;
    	ObjectMapper mapper = new ObjectMapper();
    	BirthdayVersionInfo versionInfo = new BirthdayVersionInfo();
	    try {
	    	bis = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	    	versionInfo = mapper.readValue(bis, BirthdayVersionInfo.class);
	    	if (!comment.isBlank()) {
	    		UtilVerbose.println(VERBOSE, '\t' + comment + "\tVersion = " + versionInfo.getVersionInfo() );
	    	}
	    } catch (JsonProcessingException e) {
	       e.printStackTrace();
	       // Keep it empty
	    } catch (Exception ex) {
		    ex.printStackTrace();
	    } finally {
	    	if (bis != null) { bis.close(); }
	    }
	    return versionInfo;
	}
	
	public boolean compatibleWith(Integer major, Integer minor, Integer revision) {
		return versionCodeMajor.equals(major);
	}
	
	public boolean compatibleWith(BirthdayVersionInfo localVersion) {
		return compatibleWith(localVersion.versionCodeMajor, 
								localVersion.versionCodeMinor, 
								localVersion.versionCodeRevision);
	}
	
}
