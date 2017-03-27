package sg.edu.nus.iss.se.ft05.medipal;

import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.dao.AppointmentDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.daoutils.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.domain.Appointment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
/**
 * Class for Appointment database operations instrumented test
 * @author Dhruv Mandan Gopal
 */
public class AppointmentDAOInstrumentedTest {


    private AppointmentDAOImpl appointmentDAOImpl;
    private Appointment appointment1;
    private Appointment appointment2;
    private Appointment appointment5;
    private Appointment appointment6;

    @Before
    public void setUp() {

        appointmentDAOImpl = new AppointmentDAOImpl(InstrumentationRegistry.getTargetContext());

        Cursor cursor = appointmentDAOImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            appointmentDAOImpl.delete(cursor.getInt(cursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_ID)));
            cursor.moveToNext();
        }

        appointment1 = new Appointment("2017-01-01", "01:01", "Wellness1", "blood sugar1");

        appointmentDAOImpl.insert(appointment1);

        appointment2 = new Appointment("2017-01-02", "01:02", "Wellness2", "blood sugar2");

        appointmentDAOImpl.insert(appointment2);

        appointmentDAOImpl.insert(new Appointment("2017-01-03", "01:03", "Wellness3", "blood sugar3"));

        appointmentDAOImpl.insert(new Appointment("2017-01-04", "01:04", "Wellness4", "blood sugar4"));


        appointment6 = new Appointment("2017-01-02", "01:06", "Wellness6", "blood sugar6");

        appointmentDAOImpl.insert(appointment6);

        appointment5 = new Appointment("2017-01-05", "01:05", "Wellness5", "blood sugar5");
        appointment5.setId(3);
    }

    @After
    public void finish() {

        Cursor cursor = appointmentDAOImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            appointmentDAOImpl.delete(cursor.getInt(cursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_ID)));
            cursor.moveToNext();
        }

        appointmentDAOImpl.close();
    }

    @Test
    public void testPreConditions() {

        assertNotNull(appointmentDAOImpl);
    }


    @Test
    public void testFindAll() throws Exception {

        Cursor cursor = appointmentDAOImpl.findAll();

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            int id = cursor.getInt(cursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_ID));
            String date = cursor.getString(cursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_DATE));
            String time = cursor.getString(cursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_TIME));
            String clinic = cursor.getString(cursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_CLINIC));
            String description = cursor.getString(cursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_DESCRIPTION));

            assertNotNull(id);

            switch (id) {

                case 1:
                    assertNotNull(date);
                    assertEquals(appointment1.getDate(), date);
                    assertNotNull(time);
                    assertEquals(appointment1.getTime(), time);
                    assertNotNull(clinic);
                    assertEquals(appointment1.getClinic(), clinic);
                    assertNotNull(description);
                    assertEquals(appointment1.getDescription(), description);
                    break;

                case 2:
                    assertNotNull(date);
                    assertEquals(appointment2.getDate(), date);
                    assertNotNull(time);
                    assertEquals(appointment2.getTime(), time);
                    assertNotNull(clinic);
                    assertEquals(appointment2.getClinic(), clinic);
                    assertNotNull(description);
                    assertEquals(appointment2.getDescription(), description);
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFindById() throws Exception {

        findByIdTesting(1, appointment1);
    }

    /**
     * @param id
     * @param appointmentTest
     */
    private void findByIdTesting(int id, Appointment appointmentTest) {

        Appointment appointment = appointmentDAOImpl.findById(id);

        assertNotNull(appointment);

        assertNotNull(appointment.getDate());
        assertEquals(appointmentTest.getDate(), appointment.getDate());
        assertNotNull(appointment.getTime());
        assertEquals(appointmentTest.getTime(), appointment.getTime());
        assertNotNull(appointment.getClinic());
        assertEquals(appointmentTest.getClinic(), appointment.getClinic());
        assertNotNull(appointment.getDescription());
        assertEquals(appointmentTest.getDescription(), appointment.getDescription());
    }

    @Test
    public void testDelete() throws Exception {

        int num = appointmentDAOImpl.delete(4);

        assertNotNull(num);
        assertEquals(1, num);
    }

    @Test
    public void testUpdate() throws Exception {

        int num = appointmentDAOImpl.update(appointment5);

        assertNotNull(num);
        assertEquals(1, num);
        findByIdTesting(3, appointment5);
    }

    @Test
    public void testFindByDate() throws Exception {

        List<Appointment> appointmentList = appointmentDAOImpl.findByDate("2017-01-02");

        assertNotNull(appointmentList);
        assertFalse(appointmentList.isEmpty());

        for (Appointment appointment : appointmentList) {

            switch (appointment.getId()) {

                case 2:
                    assertNotNull(appointment.getDate());
                    assertEquals(appointment2.getDate(), appointment.getDate());
                    assertNotNull(appointment.getTime());
                    assertEquals(appointment2.getTime(), appointment.getTime());
                    assertNotNull(appointment.getClinic());
                    assertEquals(appointment2.getClinic(), appointment.getClinic());
                    assertNotNull(appointment.getDescription());
                    assertEquals(appointment2.getDescription(), appointment.getDescription());
                    break;

                case 5:
                    assertNotNull(appointment.getDate());
                    assertEquals(appointment6.getDate(), appointment.getDate());
                    assertNotNull(appointment.getTime());
                    assertEquals(appointment6.getTime(), appointment.getTime());
                    assertNotNull(appointment.getClinic());
                    assertEquals(appointment6.getClinic(), appointment.getClinic());
                    assertNotNull(appointment.getDescription());
                    assertEquals(appointment6.getDescription(), appointment.getDescription());
                    break;
            }
        }
    }

    @Test
    public void testFilterDate() throws Exception {

        Cursor cursor = appointmentDAOImpl.filterDate("2017-01-02");

        assertNotNull(cursor);

        while (!cursor.isAfterLast()) {

            int id = cursor.getInt(cursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_ID));
            String date = cursor.getString(cursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_DATE));
            String time = cursor.getString(cursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_TIME));
            String clinic = cursor.getString(cursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_CLINIC));
            String description = cursor.getString(cursor.getColumnIndex(DBHelper.APPOINTMENT_KEY_APPOINTMENT_DESCRIPTION));

            assertNotNull(id);

            switch (id) {

                case 2:
                    assertNotNull(date);
                    assertEquals(appointment2.getDate(), date);
                    assertNotNull(time);
                    assertEquals(appointment2.getTime(), time);
                    assertNotNull(clinic);
                    assertEquals(appointment2.getClinic(), clinic);
                    assertNotNull(description);
                    assertEquals(appointment2.getDescription(), description);
                    break;

                case 5:
                    assertNotNull(date);
                    assertEquals(appointment6.getDate(), date);
                    assertNotNull(time);
                    assertEquals(appointment6.getTime(), time);
                    assertNotNull(clinic);
                    assertEquals(appointment6.getClinic(), clinic);
                    assertNotNull(description);
                    assertEquals(appointment6.getDescription(), description);
                    break;
            }

            cursor.moveToNext();
        }
    }
}

