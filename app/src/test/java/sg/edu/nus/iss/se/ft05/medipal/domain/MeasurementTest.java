package sg.edu.nus.iss.se.ft05.medipal.domain;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


/**
 * Class for Measurement test
 * @author Aakash Deep Mangalore
 */
public class MeasurementTest {

    Measurement measurement;

    /**
     * Method to test getters and setters
     */
    @Test
    public void testMeasurement() {

        measurement = new Measurement();
        measurement.setId(10);
        measurement.setSystolic(12);
        measurement.setDiastolic(12);
        measurement.setPulse(121);
        measurement.setWeight(80);
        measurement.setTemperature(Float.parseFloat("37.5"));
        measurement.setMeasuredOn("2017-03-03");

        assertEquals(measurement.getId(), 10);
        assertEquals(measurement.getSystolic(), 12);
        assertEquals(measurement.getDiastolic(), 12);
        assertEquals(measurement.getPulse(), 121);
        assertEquals(measurement.getWeight(), 80);
        assertEquals(measurement.getTemperature(), Float.parseFloat("37.5"));
        assertEquals(measurement.getMeasuredOn(), "2017-03-03");
    }

    /**
     * Method to test getters and setters
     */
    @Test
    public void testMeasurementFull() {

        measurement = new Measurement(12, 12, 121, Float.parseFloat("37.5"), 80, "2017-03-03");

        assertNotNull(measurement);
        assertEquals(measurement.getSystolic(), 12);
        assertEquals(measurement.getDiastolic(), 12);
        assertEquals(measurement.getPulse(), 121);
        assertEquals(measurement.getWeight(), 80);
        assertEquals(measurement.getTemperature(), Float.parseFloat("37.5"));
        assertEquals(measurement.getMeasuredOn(), "2017-03-03");
    }
}

