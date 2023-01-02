package name.loeliger.frank.birthdaylist2test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javahelps.jerseydemo.services.BirthdayConfig;
import com.javahelps.jerseydemo.services.BirthdayList;

class BirthdayConfigTest extends BirthdayListTestBase {

    private static final String USER_NAME_DEFAULT_CONFIG= "default";
    private static final String USER_NAME_UNITTEST_CONFIG = "unit.test";
    private static final String USER_NAME_NOTEXISTING_CONFIG = "unit.test.notexists";

	private static boolean initialized = false;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		initialized = false;
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	synchronized void setUp() throws Exception {
		if (!initialized) {
	        initialized = true;
		}
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetConfig() {
        BirthdayConfig config = restCallGetConfigFile(
        		getUriWithUser(USER_NAME_DEFAULT_CONFIG, "/config"), "");
    	printConfig("testGetConfig", config, true);
    	assertEquals(13, config.getMonths().size(), "The config should contain 13 months positions");    	
	}

	@Test
	void testGetConfigUser() {
        BirthdayConfig config = restCallGetConfigFile(
        		getUriWithUser(USER_NAME_UNITTEST_CONFIG, "/config"), "");
    	printConfig("testGetConfigUser", config, true);
    	assertEquals(13, config.getMonths().size(), "The config should contain no months positions");    	
	}

	@Test
	void testGetConfigNotExisting() {
        BirthdayConfig config = restCallGetConfigFile(
        		getUriWithUser(USER_NAME_NOTEXISTING_CONFIG, "/config"), "");
    	printConfig("testGetConfigNotExisting", config, true);
    	assertEquals(13, config.getMonths().size(), "The config should contain no months positions");    	
	}
	
}
