package sg.edu.nus.iss.se.ft05.medipal.model;

import org.junit.Test;

import sg.edu.nus.iss.se.ft05.medipal.domain.PersonalBio;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Ashish Katre on 3/20/2017.
 */
public class PersonalBioTest {

    PersonalBio personalBio;

    /**
     * Method to test getters and setters
     */
    @Test
    public void testPersonalBio() {

        personalBio = new PersonalBio();
        personalBio.setId(10);
        personalBio.setName("Name");
        personalBio.setDob("1990-01-01");
        personalBio.setIdNo("BHPKY1122F");
        personalBio.setAddress("pune india");
        personalBio.setPostalCode("111000");
        personalBio.setHeight("180");
        personalBio.setBloodType("A+");

        assertEquals(personalBio.getId(), 10);
        assertEquals(personalBio.getName(), "Name");
        assertEquals(personalBio.getDob(), "1990-01-01");
        assertEquals(personalBio.getIdNo(), "BHPKY1122F");
        assertEquals(personalBio.getAddress(), "pune india");
        assertEquals(personalBio.getPostalCode(), "111000");
        assertEquals(personalBio.getHeight(), "180");
        assertEquals(personalBio.getBloodType(), "A+");
    }

    /**
     * Method to test getters and setters
     */
    @Test
    public void testPersonalBioFull() {

        personalBio = new PersonalBio("Name", "1990-01-01", "BHPKY1122F", "pune india", "111000", "180", "A+");

        assertNotNull(personalBio);
        assertEquals(personalBio.getName(), "Name");
        assertEquals(personalBio.getDob(), "1990-01-01");
        assertEquals(personalBio.getIdNo(), "BHPKY1122F");
        assertEquals(personalBio.getAddress(), "pune india");
        assertEquals(personalBio.getPostalCode(), "111000");
        assertEquals(personalBio.getHeight(), "180");
        assertEquals(personalBio.getBloodType(), "A+");
    }
}
