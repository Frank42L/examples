package name.loeliger.frank.birthdaylist2test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import org.apache.http.HttpEntity;
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
import com.javahelps.jerseydemo.services.BirthdayConfig;
import com.javahelps.jerseydemo.services.BirthdayList;
import com.javahelps.jerseydemo.services.BirthdayVersionInfo;
import com.javahelps.jerseydemo.services.MonthConfig;



/**
 * Unit test for simple App.
 */
@DisplayName("Unit Tests of BirthdayList REST API")
public class BirthdayListTestBase 
{
	private static final String HTTP_LOCALHOST_8080_JERSEYDEMO_BIRTHDAYS = "http://tomcat:8080/BirthdayList/birthdays";
//	private static final String HTTP_LOCALHOST_8080_JERSEYDEMO_BIRTHDAYS = "http://localhost:8080/jerseydemo/birthdays";
	protected static boolean VERBOSE = false;

	protected String getUri() {
    	return HTTP_LOCALHOST_8080_JERSEYDEMO_BIRTHDAYS;
    }

	protected String getUriWithUser(String user, String subPath) {
    	if (user.isBlank()) {
    		return getUriAllUsers(subPath);
    	}
    	return getUri() + "/user/" + user + subPath;
    }
    
	protected String getUriAllUsers(String subPath) {
    	return getUri() +  "/users"+ subPath;
    }

	protected String getUriVersion() {
    	return getUri() +  "/version";
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
	
	protected BirthdayConfig restCallGetConfigFile(String restUri, String comment) {
		BirthdayConfig config = null;
    	
    	try {
			HttpGet httpGet = new HttpGet(restUri);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(httpGet);
			String str = null;
			str = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			assertResponse(response, str);
			InputStream isResponse = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
			config = BirthdayConfig.getBirthdayConfigFromStream(isResponse, comment);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	assertTrue(config != null);
    	return config;
    }
	

	protected BirthdayVersionInfo restCallGetVersionInfo(String restUri, String comment) {
		BirthdayVersionInfo versionInfo = null;
    	
    	try {
			HttpGet httpGet = new HttpGet(restUri);
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(httpGet);
			String str = null;
			str = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			assertResponse(response, str);
			InputStream isResponse = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
			versionInfo = BirthdayVersionInfo.getBirthdayVersionInfoFromStream(isResponse, comment);
		} catch (Exception e) {
			e.printStackTrace();
		} 
    	assertTrue(versionInfo != null);
    	return versionInfo;
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
    
    protected void printBirthdays(String strTitle, BirthdayList bl, boolean info) {
	
		if (VERBOSE || info) {
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
    
    protected void printInfo(String str, boolean info) {
    	
		if (VERBOSE || info) {
			System.out.println("\t" + str);
		}
    }

	protected void printConfig(String strTitle, BirthdayConfig config, boolean info) {
    	
		if (VERBOSE || info) {
			Iterator<MonthConfig> iter;
			MonthConfig monthConfig;
			iter = config.getMonths().iterator();
			System.out.println("");
			System.out.println("<"+ strTitle + ">");
			while (iter.hasNext())
			{
				monthConfig = iter.next();
				System.out.println("\t" + monthConfig.toString());
			}
			System.out.println("\t" + "Total " + config.getMonths().size() + " Monatskonfigurationen" );
			System.out.println("");
		} 		

    }

    protected void printBirthdays(String strTitle, BirthdayList bl) {
    	printBirthdays(strTitle, bl, false);
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
