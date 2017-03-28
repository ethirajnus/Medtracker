package com.ethigeek.medtracker.domain;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Ethiraj Srinivasan
 */
public class ConsumptionTest {

    Consumption consumption;

    /**
     * Method to test getters and setters
     */
    @Test
    public void testConsumption() {

        consumption = new Consumption();
        consumption.setId(1);
        consumption.setMedicineId(1);
        consumption.setQuantity(2);
        consumption.setDate("2017-01-01");
        consumption.setTime("01:01");

        assertEquals(consumption.getId(), 1);
        assertEquals(consumption.getMedicineId(), 1);
        assertEquals(consumption.getQuantity(), 2);
        assertEquals(consumption.getDate(), "2017-01-01");
        assertEquals(consumption.getTime(), "01:01");
    }

    /**
     * Method to test getters and setters
     */
    @Test
    public void testConsumptionFull() {

        consumption = new Consumption(1, 1, "2017-01-01", "01:01");

        assertEquals(consumption.getMedicineId(), 1);
        assertEquals(consumption.getQuantity(), 1);
        assertEquals(consumption.getDate(), "2017-01-01");
        assertEquals(consumption.getTime(), "01:01");
    }
}
