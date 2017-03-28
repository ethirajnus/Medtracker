package com.ethigeek.medipal;

import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.ethigeek.medipal.daoutils.DBHelper;
import com.ethigeek.medipal.dao.MeasurementDAOImpl;
import com.ethigeek.medipal.domain.Measurement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

/**
 * Class for measurement database operations instrumented test
 * @author Aakash Deep Mangalore
 */
@RunWith(AndroidJUnit4.class)
public class MeasurementDAOInstrumentedTest {


    private MeasurementDAOImpl measurementDAOImpl;
    private Measurement measurement1;
    private Measurement measurement2;
    private Measurement measurement4;
    private Measurement measurement5;
    private Measurement measurement6;
    private Measurement measurement7;

    @Before
    public void setUp() {

        measurementDAOImpl = new MeasurementDAOImpl(InstrumentationRegistry.getTargetContext());

        Cursor cursor = measurementDAOImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            measurementDAOImpl.delete(cursor.getInt(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_ID)));
            cursor.moveToNext();
        }

        measurement1 = new Measurement(11, 11, 111, Float.parseFloat("11.1"), 11, "2017-01-01");

        measurementDAOImpl.insert(measurement1);

        measurement2 = new Measurement(22, 22, 222, Float.parseFloat("22.2"), 22, "2017-01-02");

        measurementDAOImpl.insert(measurement2);

        measurementDAOImpl.insert(new Measurement(33, 33, 333, Float.parseFloat("33.3"), 33, "2017-01-03"));

        measurement4 = new Measurement(44, 44, 444, Float.parseFloat("44.4"), 44, "2017-01-04");

        measurementDAOImpl.insert(measurement4);

        measurement5 = new Measurement(55, 55, 555, Float.parseFloat("55.5"), 55, "2017-01-05");

        measurementDAOImpl.insert(measurement5);

        measurement6 = new Measurement(66, 66, 666, Float.parseFloat("66.6"), 66, "2017-01-06");

        measurementDAOImpl.insert(measurement6);

        measurement7 = new Measurement(77, 77, 777, Float.parseFloat("77.7"), 77, "2017-01-07");
        measurement7.setId(3);
    }

    @After
    public void finish() {

        Cursor cursor = measurementDAOImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            measurementDAOImpl.delete(cursor.getInt(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_ID)));
            cursor.moveToNext();
        }

        measurementDAOImpl.close();
    }

    @Test
    public void testPreConditions() {

        assertNotNull(measurementDAOImpl);
    }


    @Test
    public void testFindAll() throws Exception {

        Cursor cursor = measurementDAOImpl.findAll();

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            int id = cursor.getInt(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_ID));
            int systolic = cursor.getInt(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_SYSTOLIC));
            int diastolic = cursor.getInt(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_DIASTOLIC));
            int pulse = cursor.getInt(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_PULSE));
            int weight = cursor.getInt(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_WEIGHT));
            float temperature = cursor.getFloat(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_TEMPERATURE));
            String date = cursor.getString(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_MEASURED_ON));

            assertNotNull(id);

            switch (id) {

                case 1:
                    assertNotNull(systolic);
                    assertEquals(measurement1.getSystolic(), systolic);
                    assertNotNull(diastolic);
                    assertEquals(measurement1.getDiastolic(), diastolic);
                    assertNotNull(pulse);
                    assertEquals(measurement1.getPulse(), pulse);
                    assertNotNull(weight);
                    assertEquals(measurement1.getWeight(), weight);
                    assertNotNull(temperature);
                    assertEquals("" + measurement1.getTemperature(), "" + temperature);
                    assertNotNull(date);
                    assertEquals(measurement1.getMeasuredOn(), date);
                    break;

                case 2:
                    assertNotNull(systolic);
                    assertEquals(measurement2.getSystolic(), systolic);
                    assertNotNull(diastolic);
                    assertEquals(measurement2.getDiastolic(), diastolic);
                    assertNotNull(pulse);
                    assertEquals(measurement2.getPulse(), pulse);
                    assertNotNull(weight);
                    assertEquals(measurement2.getWeight(), weight);
                    assertNotNull(temperature);
                    assertEquals("" + measurement2.getTemperature(), "" + temperature);
                    assertNotNull(date);
                    assertEquals(measurement2.getMeasuredOn(), date);
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFindById() throws Exception {

        findByIdTesting(1, measurement1);
    }

    private void findByIdTesting(int id, Measurement measurementTest) {

        Measurement measurement = measurementDAOImpl.findById(id);

        assertNotNull(measurement);

        assertNotNull(measurement.getSystolic());
        assertEquals(measurementTest.getSystolic(), measurement.getSystolic());
        assertNotNull(measurement.getDiastolic());
        assertEquals(measurementTest.getDiastolic(), measurement.getDiastolic());
        assertNotNull(measurement.getPulse());
        assertEquals(measurementTest.getPulse(), measurement.getPulse());
        assertNotNull(measurement.getWeight());
        assertEquals(measurementTest.getWeight(), measurement.getWeight());
        assertNotNull(measurement.getTemperature());
        assertEquals("" + measurementTest.getTemperature(), "" + measurement.getTemperature());
        assertNotNull(measurement.getMeasuredOn());
        assertEquals(measurementTest.getMeasuredOn(), measurement.getMeasuredOn());
    }

    @Test
    public void testDelete() throws Exception {

        int num = measurementDAOImpl.delete(4);

        assertNotNull(num);
        assertEquals(1, num);
    }

    @Test
    public void testUpdate() throws Exception {

        int num = measurementDAOImpl.update(measurement7);

        assertNotNull(num);
        assertEquals(1, num);
        findByIdTesting(3, measurement7);
    }

    @Test
    public void testFetchAllMeasurementsWithId() throws Exception {

        Cursor cursor = measurementDAOImpl.fetchAllMeasurementsWithId();

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            int id = cursor.getInt(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_ID));
            String date = cursor.getString(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_MEASURED_ON));

            assertNotNull(date);

            switch (id) {

                case 1:
                    assertEquals(measurement1.getMeasuredOn(), date);
                    break;

                case 2:
                    assertEquals(measurement2.getMeasuredOn(), date);
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFetchMaxId() throws Exception {

        int idMax = measurementDAOImpl.fetchMaxId();

        assertNotNull(idMax);
        assertEquals(6, idMax);
    }

    @Test
    public void testFindLatest() throws Exception {

        Measurement measurement = measurementDAOImpl.findLatest();

        assertNotNull(measurement);

        assertNotNull(measurement.getSystolic());
        assertEquals(measurement6.getSystolic(), measurement.getSystolic());
        assertNotNull(measurement.getDiastolic());
        assertEquals(measurement6.getDiastolic(), measurement.getDiastolic());
        assertNotNull(measurement.getPulse());
        assertEquals(measurement6.getPulse(), measurement.getPulse());
        assertNotNull(measurement.getWeight());
        assertEquals(measurement6.getWeight(), measurement.getWeight());
        assertNotNull(measurement.getTemperature());
        assertEquals("" + measurement6.getTemperature(), "" + measurement.getTemperature());
    }

    @Test
    public void testBetweenDate() throws Exception {

        Cursor cursor = measurementDAOImpl.betweenDate("2017-01-03", "2017-01-05");

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            int id = cursor.getInt(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_ID));
            int systolic = cursor.getInt(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_SYSTOLIC));
            int diastolic = cursor.getInt(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_DIASTOLIC));
            int pulse = cursor.getInt(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_PULSE));
            int weight = cursor.getInt(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_WEIGHT));
            float temperature = cursor.getFloat(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_TEMPERATURE));
            String date = cursor.getString(cursor.getColumnIndex(DBHelper.MEASUREMENT_KEY_MEASURED_ON));

            assertNotNull(id);

            switch (id) {

                case 4:
                    assertNotNull(systolic);
                    assertEquals(measurement4.getSystolic(), systolic);
                    assertNotNull(diastolic);
                    assertEquals(measurement4.getDiastolic(), diastolic);
                    assertNotNull(pulse);
                    assertEquals(measurement4.getPulse(), pulse);
                    assertNotNull(weight);
                    assertEquals(measurement4.getWeight(), weight);
                    assertNotNull(temperature);
                    assertEquals("" + measurement4.getTemperature(), "" + temperature);
                    assertNotNull(date);
                    assertEquals(measurement4.getMeasuredOn(), date);
                    break;
            }

            cursor.moveToNext();
        }
    }
}
