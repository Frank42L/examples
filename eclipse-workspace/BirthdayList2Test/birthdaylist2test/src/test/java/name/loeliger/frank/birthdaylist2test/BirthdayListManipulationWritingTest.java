package name.loeliger.frank.birthdaylist2test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;

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
@DisplayName("BirthdayList REST API (Manipulating)")
public class BirthdayListManipulationWritingTest extends BirthdayListTestBase
{
    private static final String USER_NAME_TEST = "BirthdayList.manipulations.unit.test";

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
	        postFile(getUriWithUser(USER_NAME_TEST, ""), "t_03_01.manipulations.regression.json", "setUp with t_03_01.manipulations.regression.json");
	        initialized = true;
		}
	}

	@AfterEach
	void tearDown() throws Exception {
	}

    @Test    
    public void testChangeBirthdayOfAlreadyExistingPerson() {
        BirthdayList bl;
        
        bl = restCallGetFile(getUriWithUser(USER_NAME_TEST, "/Meier/Paul"), "");
    	printBirthdays("testChangeBirthdayOfAlreadyExistingPerson 1", bl);
    	assertEquals(2, bl.getBirthdays().size(), "Paul Meier with missing Month is not the same as the other Paul Meier");
    	
    	BirthDayPerson p = null;
    	BirthDayPerson pFound = null;
    	Iterator<BirthDayPerson> iter = bl.getBirthdays().iterator();
    	while (iter.hasNext() && pFound == null) {
    		p = iter.next();
    		if (p.getDay() == 1 && p.getMonth() == 1 && p.getYear() == 0) {
    			pFound = p;
    		}
    	}
    	
    	assertEquals(-1090317285, pFound.hashCode(), "Paul Meier 1.1.0000 sollte den Hash -1090317285 haben");
    	
    	/* retrieve "Paul Meier" with 1.1.0000 */
        bl = restCallGetFile(getUriWithUser(USER_NAME_TEST, "/Meier/Paul/0/1/1"), "");
    	printBirthdays("testChangeBirthdayOfAlreadyExistingPerson 2", bl);
    	assertEquals(1, bl.getBirthdays().size(), "Only One Paul Meier with 01.01.0000 expected");

    	/* Change "Paul Meier" (1.1.0000) to 1.1.1968 */
        bl = restCallPost(getUriWithUser(USER_NAME_TEST, "/Meier/Paul/0/1/1"), 1968, 1, 1, "");
    	printBirthdays("testChangeBirthdayOfAlreadyExistingPerson 3", bl);
    	assertEquals(3, bl.getBirthdays().size(), "Resulting list does still contain 3 persons");

        bl = restCallGetFile(getUriWithUser(USER_NAME_TEST, "/Meier/Paul/0/1/1"), "");
    	printBirthdays("testChangeBirthdayOfAlreadyExistingPerson 4", bl);
    	assertEquals(0, bl.getBirthdays().size(), "No more Paul Meier with 01.01.0000 expected");

        bl = restCallGetFile(getUriWithUser(USER_NAME_TEST, "/Meier/Paul/1968/1/1"), "");
    	printBirthdays("testChangeBirthdayOfAlreadyExistingPerson 5", bl);
    	assertEquals(1, bl.getBirthdays().size(), "One Paul Meier with 01.01.1968 expected");
    	
    	/* Change "Paul Meier" (1.1.1968) back to 1.1.0 */
        bl = restCallPost(getUriWithUser(USER_NAME_TEST, "/Meier/Paul/1968/1/1"), 0, 1, 1, "");
    	printBirthdays("testChangeBirthdayOfAlreadyExistingPerson 6", bl);
    	assertEquals(3, bl.getBirthdays().size(), "Resulting list does still contain 3 persons");

        bl = restCallGetFile(getUriWithUser(USER_NAME_TEST, "/Meier/Paul/0/1/1"), "");
    	printBirthdays("testChangeBirthdayOfAlreadyExistingPerson 7", bl);
    	assertEquals(1, bl.getBirthdays().size(), "One Paul Meier with 01.01.0000 expected");

        bl = restCallGetFile(getUriWithUser(USER_NAME_TEST, "/Meier/Paul/1968/1/1"), "");
    	printBirthdays("testChangeBirthdayOfAlreadyExistingPerson 8", bl);
    	assertEquals(0, bl.getBirthdays().size(), "No more Paul Meier with 01.01.1968 expected");

    }
    
    @Test    
    public void testDeduplicationAfterCorrectionOfBirthday() {
        BirthdayList bl;
        
    	/* retrieve "Paul Meier" with 1.1.0000 */
        bl = restCallGetFile(getUriWithUser(USER_NAME_TEST, "/Meier/Paul/0/1/1"), "");
    	printBirthdays("testDeduplicationAfterCorrectionOfBirthday 1", bl);
    	assertEquals(1, bl.getBirthdays().size(), "Only One Paul Meier with 01.01.0000 expected");

    	/* Change "Paul Meier" (1.1.0000) to 1.1.1967 */
        bl = restCallPost(getUriWithUser(USER_NAME_TEST, "/Meier/Paul/0/1/1"), 1967, 1, 1, "");
    	printBirthdays("testDeduplicationAfterCorrectionOfBirthday 2", bl);
    	assertEquals(2, bl.getBirthdays().size(), "Resulting list should now consist of 2 persons");

    	/* restore original state */
        bl = postFile(getUriWithUser(USER_NAME_TEST, ""), "t_03_01.manipulations.regression.json", "setUp with t_03_01.manipulations.regression.json");
    	printBirthdays("testDeduplicationAfterCorrectionOfBirthday 3", bl);
    	assertEquals(3, bl.getBirthdays().size(), "Resulting list should now consist of 3 persons");

    }
	
    @Test    
    public void testUpdateMultiple() {
        BirthdayList bl;
        
        bl = patchFile(getUriWithUser(USER_NAME_TEST, ""),
        		"t_03_02.manipulations.regression.json", "testUpdateMultiple with t_03_02.manipulations.regression.json");
    	printBirthdays("testUpdateMultiple 1", bl);
        assertEquals(4, bl.getBirthdays().size());
        
        bl = restCallGetFile(getUriWithUser(USER_NAME_TEST, "/Meier/Paul/0/1/1"), "");
    	printBirthdays("testUpdateMultiple 2", bl);
    	assertEquals(0, bl.getBirthdays().size(), "no Paul Meier with 01.01.0000 expected");

    	bl = restCallGetFile(getUriWithUser(USER_NAME_TEST, "/Meier/Paul/1968/1/1"), "");
    	printBirthdays("testUpdateMultiple 2", bl);
    	assertEquals(1, bl.getBirthdays().size(), "One Paul Meier with 01.01.1968 expected");
		
    	/* restore original state */
        bl = postFile(getUriWithUser(USER_NAME_TEST, ""), "t_03_01.manipulations.regression.json", "setUp with t_03_01.manipulations.regression.json");
    	printBirthdays("testUpdateMultiple 3", bl);
    	assertEquals(3, bl.getBirthdays().size(), "Resulting list should now consist of 3 persons");

    }
    
    @Test    
    public void testChangeName() {
        BirthdayList bl;
        
        bl = patchFile(getUriWithUser(USER_NAME_TEST, ""),
        		"t_03_03.manipulations.regression.json", "testUpdateMultiple with t_03_03.manipulations.regression.json");
    	printBirthdays("testChangeName 1", bl);
        assertEquals(3, bl.getBirthdays().size());
        
        bl = restCallGetFile(getUriWithUser(USER_NAME_TEST, "/Meier/Paul"), "");
    	printBirthdays("testChangeName 2", bl);
    	assertEquals(0, bl.getBirthdays().size(), "no Paul Meier expected");

    	bl = restCallGetFile(getUriWithUser(USER_NAME_TEST, "/Meier-Müller/Paul-Hans/1967/1/1"), "");
    	printBirthdays("testChangeName 3", bl);
    	assertEquals(1, bl.getBirthdays().size(), "Paul Meier changed name to Paul-Hans Meier-Müller with 01.01.1967 expected");

    	bl = restCallGetFile(getUriWithUser(USER_NAME_TEST, "/Meier/Annatina"), "");
    	printBirthdays("testChangeName 4", bl);
    	assertEquals(1, bl.getBirthdays().size(), "Anja Meier should now be called Annatina Meier (Meier should still be there altough tried to set to empty");
		
    	/* restore original state */
        bl = postFile(getUriWithUser(USER_NAME_TEST, ""), "t_03_01.manipulations.regression.json", "setUp with t_03_01.manipulations.regression.json");
    	printBirthdays("testChangeName 5", bl);
    	assertEquals(3, bl.getBirthdays().size(), "Resulting list should now consist of 3 persons");

    }
}
