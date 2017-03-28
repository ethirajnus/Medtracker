package com.ethigeek.medtracker.domain;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * @author Ethiraj Srinivasan
 */

/**
 * Class for Medicine test
 */
public class MedicineTest {

    Medicine medicine;

    /**
     * Method to test getters and setters
     */
    @Test
    public void testMedicine() {

        medicine = new Medicine();
        medicine.setId(10);
        medicine.setName("Name");
        medicine.setDescription("Description");
        medicine.setDateIssued("2017-01-01");
        medicine.setRemind(true);
        medicine.setCategoryId(1);
        medicine.setReminderId(2);
        medicine.setQuantity(3);
        medicine.setDosage(4);
        medicine.setConsumeQuantity(5);
        medicine.setThreshold(6);
        medicine.setExpireFactor(7);

        assertEquals(medicine.getId(), 10);
        assertEquals(medicine.getName(), "Name");
        assertEquals(medicine.getDescription(), "Description");
        assertEquals(medicine.getDateIssued(), "2017-01-01");
        assertEquals(medicine.getRemind(), true);
        assertEquals(medicine.getCategoryId(), 1);
        assertEquals(medicine.getReminderId(), 2);
        assertEquals(medicine.getQuantity(), 3);
        assertEquals(medicine.getDosage(), 4);
        assertEquals(medicine.getConsumeQuantity(), 5);
        assertEquals(medicine.getThreshold(), 6);
        assertEquals(medicine.getExpireFactor(), 7);
    }

    /**
     * Method to test getters and setters
     */
    @Test
    public void testMedicineFull() {

        medicine = new Medicine("Name", "Description", 1, 2, true, 3, 4, 5, 6, "2017-01-01", 7);

        assertNotNull(medicine);
        assertEquals(medicine.getName(), "Name");
        assertEquals(medicine.getDescription(), "Description");
        assertEquals(medicine.getDateIssued(), "2017-01-01");
        assertEquals(medicine.getRemind(), true);
        assertEquals(medicine.getCategoryId(), 1);
        assertEquals(medicine.getReminderId(), 2);
        assertEquals(medicine.getQuantity(), 3);
        assertEquals(medicine.getDosage(), 4);
        assertEquals(medicine.getConsumeQuantity(), 5);
        assertEquals(medicine.getThreshold(), 6);
        assertEquals(medicine.getExpireFactor(), 7);
    }
}
