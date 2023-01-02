package name.loeliger.frank.birthdaylist2test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.javahelps.jerseydemo.services.BirthDayPerson;
import com.javahelps.jerseydemo.services.BirthdayList;



/**
 * Unit test for simple App.
 */
@DisplayName("BirthdayList REST API (Readonly)")
public class BirthdayListReadonlyTest extends BirthdayListTestBase
{
    private static final String USER_NAME_READONLY_TEST = "BirthdayList.readonly.unit.test";
    private static final String USER_NAME_READONLY_TEST_2 = "BirthdayList.readonly.2.unit.test";
    private static final String USER_NAME_MERGE_TEST_1 = "BirthdayList.mergetest.1.unit.test";
    private static final String USER_NAME_MERGE_TEST_2 = "BirthdayList.mergetest.2.unit.test";
    private static final String USER_NAME_MERGE_TEST_3 = "BirthdayList.mergetest.3.unit.test";

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
	        postFile(getUriWithUser(USER_NAME_READONLY_TEST_2, ""), "t_02_02.readonly.regression.json", "setUp with t_02_02.readonly.regression.json");
	        postFile(getUriWithUser(USER_NAME_MERGE_TEST_1, ""), "t_04_01.mergetest.regression.json", "setUp with t_04_01.mergetest.regression.json");
	        postFile(getUriWithUser(USER_NAME_MERGE_TEST_2, ""), "t_04_02.mergetest.regression.json", "setUp with t_04_02.mergetest.regression.json");
	        postFile(getUriWithUser(USER_NAME_MERGE_TEST_3, ""), "t_04_03.mergetest.regression.json", "setUp with t_04_03.mergetest.regression.json");
	        initialized = true;
		}
	}

	@AfterEach
	void tearDown() throws Exception {
	}


    @Test    
    public void testContains() {
        BirthdayList bl = restCallGetFile(
        		getUriWithUser(USER_NAME_READONLY_TEST, ""), "");

    	BirthDayPerson bdp = new BirthDayPerson();
		bdp.setFirstname("Peter");
		bdp.setSurname("Muster");
		bdp.setDay(2);
		bdp.setMonth(5);
		bdp.setYear(1966);
		assertTrue(bl.getBirthdays().contains(bdp), "Paul Muster mit 2.5.1966 nicht im File gefunden!");
		bdp.setDay(10);
		assertFalse(bl.getBirthdays().contains(bdp), "Paul Muster mit 10.5.1966 unerwarteterweise im File gefunden!");		
    }

    @Test    
    public void testSearchForNameAndNoDuplicates()
    {
        BirthdayList bl = restCallGetFile(
    			getUriWithUser(USER_NAME_READONLY_TEST, "/Muster/Peter"), "");
    	printBirthdays("testSearchForNameAndNoDuplicates", bl);
    	assertEquals(2, bl.getBirthdays().size(), "Peter Muster with 3.5.1966 should not be stored twice");
    }
    
    @Test    
    public void testOnlyExactBirthdaysDoMatchAsDuplicate()
    {
        BirthdayList bl = restCallGetFile(
    			getUriWithUser(USER_NAME_READONLY_TEST, "/Meier/Paul"), "");
    	printBirthdays("testOnlyExactBirthdaysDoMatchAsDuplicate", bl);
    	assertEquals(3, bl.getBirthdays().size(), "Paul Meier with missing Month is not the same as the other Paul Meier");
    }
    
    @Test    
    public void testDeduplicationOfSeveralFiles()
    {
        BirthdayList bl = restCallGetFile( 
        		getUriAllUsers("/Muster/Frank?user=BirthdayList.mergetest.1.unit.test&user=BirthdayList.mergetest.2.unit.test"), "");
    	printBirthdays("testDeduplicationOfSeveralFiles", bl, true);
    	assertEquals(2, bl.getBirthdays().size());
    }

    @Test    
    public void testDeduplicationOfThreeFiles()
    {
        BirthdayList bl = restCallGetFile(
        		getUriAllUsers("/Muster/Frank?user=BirthdayList.mergetest.1.unit.test&user=BirthdayList.mergetest.2.unit.test&user=BirthdayList.mergetest.3.unit.test"), "");
    	printBirthdays("testDeduplicationOfThreeFiles", bl, true);
		assertEquals(3, bl.getBirthdays().size());
    }
    
    @Test    
    public void testDeduplicationOfThreeFiles2()
    {
        BirthdayList bl = restCallGetFile(
        		getUriAllUsers("/Muster/Irène?user=BirthdayList.mergetest.1.unit.test&user=BirthdayList.mergetest.2.unit.test&user=BirthdayList.mergetest.3.unit.test"), "");
    	printBirthdays("testDeduplicationOfThreeFiles", bl, true);
		assertEquals(1, bl.getBirthdays().size());
    }
    @Test    
    public void testDeduplicationOfMultipleFiles()
    {
        BirthdayList bl = restCallGetFile(
        		getUriAllUsers("?user=BirthdayList.readonly.unit.test&user=BirthdayList.readonly.2.unit.test"), "");
    	printBirthdays("testDeduplicationOfMultipleFiles", bl, true);
		assertEquals(10, bl.getBirthdays().size());
    }

}
