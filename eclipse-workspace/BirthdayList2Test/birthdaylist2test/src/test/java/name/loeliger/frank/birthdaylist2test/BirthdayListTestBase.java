package name.loeliger.frank.birthdaylist2test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.DisplayName;

import com.javahelps.jerseydemo.services.BirthDayPerson;
import com.javahelps.jerseydemo.services.BirthdayList;



/**
 * Unit test for simple App.
 */
@DisplayName("Unit Tests of BirthdayList REST API")
public class BirthdayListTestBase 
{
	private static final String HTTP_LOCALHOST_8080_JERSEYDEMO_BIRTHDAYS = "http://localhost:8080/jerseydemo/birthdays";
	protected static boolean VERBOSE = false;

	protected String getUriWithUser(String user, String subPath) {
    	if (user.isBlank()) {
    		return getUriAllUsers(subPath);
    	}
    	return HTTP_LOCALHOST_8080_JERSEYDEMO_BIRTHDAYS + "/user/" + user + subPath;
    }
    
	protected String getUriAllUsers(String subPath) {
    	return HTTP_LOCALHOST_8080_JERSEYDEMO_BIRTHDAYS +  "/users"+ subPath;
    }

	protected void assertResponse(CloseableHttpResponse response, String fullResponseContent) {
		if (response.getStatusLine().getStatusCode() >= 300) {
			System.out.println("Response Code >= 300");
			System.out.println("\tCode    = " + response.getStatusLine().getStatusCode());
			System.out.println("\tReason  = " + response.getStatusLine().getReasonPhrase());
			System.out.println("\tContent = " + fullResponseContent);
		}
		assertTrue(response.getStatusLine().getStatusCode() < 300);
	}
	
	protected BirthdayList restCallGetFile(String restUri, String comment) {
    	BirthdayList bl = null;
    	
    	try {
			HttpGet httpGet = new HttpGet(restUri);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(httpGet);
			String str = null;
			str = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			assertResponse(response, str);
			InputStream isResponse = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
			bl = BirthdayList.getBirthdaysFromStream(isResponse, comment);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	assertTrue(bl != null);
    	return bl;
    }

    private BirthdayList restCallPostFile(String restUri, String resourceFileName, String restCallMethod, String comment) {

    	BirthdayList bl = null;
    	InputStream is = getClass().getResourceAsStream("/" + resourceFileName);

    	try {
			InputStreamBody isBody = new InputStreamBody(is, resourceFileName);
			FormBodyPartBuilder bpb = FormBodyPartBuilder.create("file", isBody);
			
			MultipartEntityBuilder mpb = MultipartEntityBuilder.create() // FORM
				.addPart(bpb.build());
			if (restCallMethod != null) {
				mpb.addTextBody("_rest_method", "patch");
			}
			HttpEntity httpEntity = mpb.build();
			
			HttpPost httpPost = new HttpPost(restUri);
			httpPost.setEntity(httpEntity);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(httpPost);
			String str = null;
			str = EntityUtils.toString(response.getEntity());
			InputStream isResponse = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
			bl = BirthdayList.getBirthdaysFromStream(isResponse, comment);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	assertTrue(bl != null);
    	return bl;
    }
    
    private BirthdayList restCallPost(String restUri, String restCallMethod, Integer year, Integer month, Integer day, String comment) {

    	BirthdayList bl = null;

    	try {		
			MultipartEntityBuilder mpb = MultipartEntityBuilder.create(); // FORM
			if (restCallMethod != null) {
				mpb.addTextBody("_rest_method", "patch");
			}
			mpb.addTextBody("year", year.toString());
			mpb.addTextBody("month", month.toString());
			mpb.addTextBody("day", day.toString());
			
			HttpEntity httpEntity = mpb.build();
			
			HttpPost httpPost = new HttpPost(restUri);
			httpPost.setEntity(httpEntity);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(httpPost);
			String str = null;
			str = EntityUtils.toString(response.getEntity());
			assertResponse(response, str);
			InputStream isResponse = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
			bl = BirthdayList.getBirthdaysFromStream(isResponse, comment);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	assertTrue(bl != null);
    	return bl;
    }
    
    protected void printBirthdays(String strTitle, BirthdayList bl) {
	
		if (VERBOSE) {
			Iterator<BirthDayPerson> iter;
			BirthDayPerson p;
			iter = bl.getBirthdays().iterator();
			System.out.println("");
			System.out.println("<"+ strTitle + ">");
			while (iter.hasNext())
			{
				p = iter.next();
				System.out.println("\t" + p.toString());
			}
			System.out.println("\t" + "Total " + bl.getBirthdays().size() + " Personen" );
			System.out.println("");
		} 		

    }
    

    protected BirthdayList restCallPostFile(String restUri, String resourceFileName, String comment) {
    	return restCallPostFile(restUri, resourceFileName, null, comment);
    }
    
    protected BirthdayList postFile(String restUri, String resourceFileName, String comment) {
    	return restCallPostFile(restUri, resourceFileName, comment);
    }
    
    protected BirthdayList patchFile(String restUri, String resourceFileName, String comment) {
    	return restCallPostFile(restUri, resourceFileName, "patch", comment);
    }
    
    protected BirthdayList restCallPost(String restUri, Integer year, Integer month, Integer day, String comment) {
    	return restCallPost(restUri, "patch", year, month, day, comment);
    }

}
