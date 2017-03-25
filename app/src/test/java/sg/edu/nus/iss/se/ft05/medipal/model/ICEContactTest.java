package sg.edu.nus.iss.se.ft05.medipal.model;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import sg.edu.nus.iss.se.ft05.medipal.domain.ICEContact;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Ashish Katre on 3/20/2017.
 */
public class ICEContactTest {

    ICEContact iceContact;

    /**
     * Method to test getters and setters
     */
    @Test
    public void testContacts() {

        iceContact = new ICEContact();
        iceContact.setId(10);
        iceContact.setName("Name");
        iceContact.setDescription("Description");
        iceContact.setPhone(121212);
        iceContact.setPriority(1);
        iceContact.setType("Next to Keen");

        assertEquals(iceContact.getId(), 10);
        assertEquals(iceContact.getName(), "Name");
        assertEquals(iceContact.getDescription(), "Description");
        assertEquals(iceContact.getPhone(), 121212);
        assertEquals(iceContact.getPriority(), 1);
        assertEquals(iceContact.getType(), "Next to Keen");
    }

    /**
     * Method to test getters and setters
     */
    @Test
    public void testContactsFull() {

        iceContact = new ICEContact("Name", "Description", "Next to Kin", Long.parseLong("121212"));

        assertNotNull(iceContact);
        assertEquals("Name", iceContact.getName());
        assertEquals("Description", iceContact.getDescription());
        assertEquals(Long.parseLong("121212"), iceContact.getPhone());
        assertEquals("Next to Kin", iceContact.getType());
    }
}
