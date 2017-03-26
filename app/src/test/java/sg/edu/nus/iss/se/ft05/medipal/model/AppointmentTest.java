package sg.edu.nus.iss.se.ft05.medipal.model;

import org.junit.Test;


import sg.edu.nus.iss.se.ft05.medipal.domain.Appointment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by Ashish Katre on 3/20/2017.
 */

/**
 * Class for Appointment test
 */
public class AppointmentTest {

    Appointment appointment;

    /**
     * Method to test getters and setters
     */
    @Test
    public void testAppointment() {

        appointment = new Appointment();

        appointment.setId(10);
        appointment.setDate("2017-01-01");
        appointment.setTime("21:20");
        appointment.setClinic("Wellness");
        appointment.setTest("blood sugar");
        appointment.setPreTest("fasting");

        assertEquals(appointment.getId(), 10);
        assertEquals("2017-01-01", appointment.getDate());
        assertEquals("21:20", appointment.getTime());
        assertEquals("Wellness", appointment.getClinic());
        assertEquals("blood sugar", appointment.getTest());
        assertEquals("fasting", appointment.getPreTest());
    }

    /**
     * Method to test getters and setters
     */
    @Test
    public void testAppointmentFull() {

        appointment = new Appointment("2017-01-01", "21:20", "Wellness", "blood sugar", "fasting");

        assertNotNull(appointment);
        assertEquals("2017-01-01", appointment.getDate());
        assertEquals("21:20", appointment.getTime());
        assertEquals("Wellness", appointment.getClinic());
        assertEquals("blood sugar", appointment.getTest());
        assertEquals("fasting", appointment.getPreTest());
    }
}
