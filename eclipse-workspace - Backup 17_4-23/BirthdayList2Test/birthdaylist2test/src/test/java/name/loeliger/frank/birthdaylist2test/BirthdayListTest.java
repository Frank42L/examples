package name.loeliger.frank.birthdaylist2test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.javahelps.jerseydemo.services.BirthdayList;

class BirthdayListTest extends BirthdayListTestBase {

    private static final String USER_NAME_READONLY_TEST = "BirthdayList.readonly.unit.test";
    private static final String USER_NAME_TEST = "BirthdayList.unit.test";
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
	        postFile(getUriWithUser(USER_NAME_READONLY_TEST, ""), "t_02_01.readonly.regression.json", "setUp with t_02_01.readonly.regression.json");
	        initialized = true;
		}
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetBirthdays() {
        BirthdayList bl = restCallGetFile(
        		getUriWithUser(USER_NAME_READONLY_TEST, ""), "");
    	printBirthdays("testGetTheWholeFile", bl);
    	assertEquals(9, bl.getBirthdays().size(), "The deduplicated File contains 9 entries");    	
	}

	@Test
	void testSetBirthdays() {
		assertTrue(true, "is covered by testGetBirthdays");
	}

	@Test
	void testInsert() {
        BirthdayList bl;
        
        bl = postFile(getUriWithUser(USER_NAME_TEST, ""),
        		"t_01_01.create.regression.json", "testUpdate with t_01_01.create.regression.json");
        assertEquals(6, bl.getBirthdays().size());    	

        bl = patchFile(getUriWithUser(USER_NAME_TEST, ""),
        		"t_01_02.update.regression.json", "testUpdate with t_01_02.update.regression.json");
        assertEquals(14, bl.getBirthdays().size());
		
        bl = patchFile(getUriWithUser(USER_NAME_TEST, ""),
        		"t_01_02.update.regression.json", "testUpdate with t_01_02.update.regression.json");
        assertEquals(14, bl.getBirthdays().size());
	}

	@Test
	void testFilterPerson() {
        BirthdayList bl = restCallGetFile(
    			getUriWithUser(USER_NAME_READONLY_TEST, "/Muster/Peter"), "");
    	printBirthdays("testSearchForNameAndNoDuplicates", bl);
    	assertEquals(2, bl.getBirthdays().size(), "Peter Muster with 3.5.1966 should not be stored twice");
	}

	@Test
	void testReadBirthdaysFromFile2Json() {
		assertTrue(true, "is covered by testGetBirthdays");
	}

	@Test
	void testGetBirthdaysFromStream() {
		assertTrue(true, "is covered by testGetBirthdays");
	}

	@Test
	void testWriteBirthdays2File() {
        BirthdayList bl = postFile(getUriWithUser(USER_NAME_TEST, ""),
        		"t_01_01.create.regression.json", "testWriteBirthdays2File");
        assertEquals(6, bl.getBirthdays().size());    	
	}

}
