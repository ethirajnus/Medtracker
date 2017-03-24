package sg.edu.nus.iss.se.ft05.medipal.model;

import android.test.AndroidTestCase;

import org.junit.Assert;
import org.junit.Test;

import sg.edu.nus.iss.se.ft05.medipal.model.ICEContact;

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

        Assert.assertEquals(iceContact.getId(), 10);
        Assert.assertEquals(iceContact.getName(), "Name");
        Assert.assertEquals(iceContact.getDescription(), "Description");
        Assert.assertEquals(iceContact.getPhone(), 121212);
        Assert.assertEquals(iceContact.getPriority(), 1);
        Assert.assertEquals(iceContact.getType(), "Next to Keen");
    }
}
