package com.ethigeek.medipal.domain;

import org.junit.Test;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Class for Appointment test
 * @author Dhruv Mandan Gopal
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
        appointment.setDescription("blood sugar");

        assertEquals(appointment.getId(), 10);
        assertEquals("2017-01-01", appointment.getDate());
        assertEquals("21:20", appointment.getTime());
        assertEquals("Wellness", appointment.getClinic());
        assertEquals("blood sugar", appointment.getDescription());
    }

    /**
     * Method to test getters and setters
     */
    @Test
    public void testAppointmentFull() {

        appointment = new Appointment("2017-01-01", "21:20", "Wellness", "blood sugar");

        assertNotNull(appointment);
        assertEquals("2017-01-01", appointment.getDate());
        assertEquals("21:20", appointment.getTime());
        assertEquals("Wellness", appointment.getClinic());
        assertEquals("blood sugar", appointment.getDescription());
    }
}
