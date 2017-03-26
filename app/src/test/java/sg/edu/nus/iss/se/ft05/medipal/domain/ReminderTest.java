package sg.edu.nus.iss.se.ft05.medipal.domain;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Ashish Katre on 3/20/2017.
 */
public class ReminderTest {

    Reminder reminder;

    /**
     * Method to test getters and setters
     */
    @Test
    public void testReminder() {

        reminder = new Reminder();
        reminder.setId(10);
        reminder.setFrequency(5);
        reminder.setInterval(3);
        reminder.setStartTime("2017-01-01");

        assertEquals(reminder.getId(), 10);
        assertEquals(reminder.getFrequency(), 5);
        assertEquals(reminder.getInterval(), 3);
        assertEquals(reminder.getStartTime(), "2017-01-01");
    }

    /**
     * Method to test getters and setters
     */
    @Test
    public void testReminderFull() {

        reminder = new Reminder(5, "2017-01-01", 3);

        assertNotNull(reminder);
        assertEquals(reminder.getFrequency(), 5);
        assertEquals(reminder.getInterval(), 3);
        assertEquals(reminder.getStartTime(), "2017-01-01");
    }
}
