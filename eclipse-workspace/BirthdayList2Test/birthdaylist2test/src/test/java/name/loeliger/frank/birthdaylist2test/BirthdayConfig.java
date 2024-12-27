package name.loeliger.frank.birthdaylist2test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javahelps.jerseydemo.services.BirthdayConfig;
import com.javahelps.jerseydemo.services.BirthdayVersionInfo;

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
	void testGetEffectiveConfiguredServerURL() {
    	printInfo("Configured URL = " + getUriWithUser(USER_NAME_DEFAULT_CONFIG, "/config"), true);
    	assertTrue(true);
	}

	@Test
	void testGetVersionInfo() {
		BirthdayVersionInfo localVersion = new BirthdayVersionInfo();
        BirthdayVersionInfo serverVersion = restCallGetVersionInfo(getUriVersion(), "");
    	printInfo("Local       Version = " + localVersion.getVersionInfo(), true);
    	printInfo("Server Side Version = " + serverVersion.getVersionInfo(), true);
    	assertTrue(serverVersion.compatibleWith(1, 0, 1));
    	assertTrue(serverVersion.compatibleWith(1, 1, 3));
    	assertFalse(serverVersion.compatibleWith(2, 0, 1));
    	assertTrue(serverVersion.compatibleWith(localVersion));
	}

	@Test
	void testGetConfig() {
        BirthdayConfig config = restCallGetConfigFile(
        		getUriWithUser(USER_NAME_DEFAULT_CONFIG, "/config"), "");
    	printConfig("testGetConfig", config, false);
    	assertEquals(13, config.getMonths().size(), "The config should contain 13 months positions");    	
	}

	@Test
	void testGetConfigUser() {
        BirthdayConfig config = restCallGetConfigFile(
        		getUriWithUser(USER_NAME_UNITTEST_CONFIG, "/config"), "");
    	printConfig("testGetConfigUser", config, false);
    	assertEquals(13, config.getMonths().size(), "The config should contain no months positions");    	
	}

	@Test
	void testGetConfigNotExisting() {
        BirthdayConfig config = restCallGetConfigFile(
        		getUriWithUser(USER_NAME_NOTEXISTING_CONFIG, "/config"), "");
    	printConfig("testGetConfigNotExisting", config, false);
    	assertEquals(13, config.getMonths().size(), "The config should contain no months positions");    	
	}
	
}
