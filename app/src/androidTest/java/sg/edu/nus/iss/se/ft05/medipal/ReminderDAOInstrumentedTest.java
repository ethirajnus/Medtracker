package sg.edu.nus.iss.se.ft05.medipal;

import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import sg.edu.nus.iss.se.ft05.medipal.constants.DbConstants;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.dao.ReminderDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.domain.HealthBio;
import sg.edu.nus.iss.se.ft05.medipal.domain.Reminder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 * @author Ethiraj Srinivasan
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ReminderDAOInstrumentedTest {


    private ReminderDAOImpl reminderDAOImpl;
    private Reminder reminder1;
    private Reminder reminder2;
    private Reminder reminder7;

    @Before
    public void setUp() {

        reminderDAOImpl = new ReminderDAOImpl(InstrumentationRegistry.getTargetContext());

        Cursor cursor = reminderDAOImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            reminderDAOImpl.delete(cursor.getInt(cursor.getColumnIndex(DBHelper.REMINDER_KEY_ID)));
            cursor.moveToNext();
        }

        reminder1 = new Reminder(1, "2017-01-01", 1);

        reminderDAOImpl.insert(reminder1);

        reminder2 = new Reminder(2, "2017-01-02", 2);

        reminderDAOImpl.insert(reminder2);

        reminderDAOImpl.insert(new Reminder(3, "2017-01-03", 3));

        reminderDAOImpl.insert(new Reminder(4, "2017-01-04", 4));


        reminder7 = new Reminder(7, "2017-01-07", 7);
        reminder7.setId(3);
    }

    @After
    public void finish() {

        Cursor cursor = reminderDAOImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            reminderDAOImpl.delete(cursor.getInt(cursor.getColumnIndex(DBHelper.REMINDER_KEY_ID)));
            cursor.moveToNext();
        }

        reminderDAOImpl.close();
    }

    @Test
    public void testPreConditions() {

        assertNotNull(reminderDAOImpl);
    }


    @Test
    public void testFindAll() throws Exception {

        Cursor c = reminderDAOImpl.findAll();

        assertNotNull(c);

        c.moveToFirst();

        while (!c.isAfterLast()) {

            Reminder reminder = new Reminder();

            reminder.setId(c.getInt(c.getColumnIndex(DBHelper.REMINDER_KEY_ID)));
            reminder.setFrequency((c.getInt(c.getColumnIndex(DBHelper.REMINDER_KEY_FREQUENCY))));
            reminder.setStartTime(c.getString(c.getColumnIndex(DBHelper.REMINDER_KEY_STARTTIME)));
            reminder.setInterval(c.getInt(c.getColumnIndex(DBHelper.REMINDER_KEY_INTERVAL)));

            assertNotNull(reminder.getId());

            switch (reminder.getId()) {

                case 1:
                    assertNotNull(reminder.getFrequency());
                    assertEquals(reminder1.getFrequency(), reminder.getFrequency());
                    assertNotNull(reminder.getStartTime());
                    assertEquals(reminder1.getStartTime(), reminder.getStartTime());
                    assertNotNull(reminder.getInterval());
                    assertEquals(reminder1.getInterval(), reminder.getInterval());
                    break;

                case 2:
                    assertNotNull(reminder.getFrequency());
                    assertEquals(reminder2.getFrequency(), reminder.getFrequency());
                    assertNotNull(reminder.getStartTime());
                    assertEquals(reminder2.getStartTime(), reminder.getStartTime());
                    assertNotNull(reminder.getInterval());
                    assertEquals(reminder2.getInterval(), reminder.getInterval());
                    break;
            }

            c.moveToNext();
        }
    }

    @Test
    public void testFindById() throws Exception {

        findByIdTesting(1, reminder1);
    }

    private void findByIdTesting(int id, Reminder reminderTest) {

        Reminder reminder = reminderDAOImpl.findById(id);

        assertNotNull(reminder);

        assertNotNull(reminder.getFrequency());
        assertEquals(reminderTest.getFrequency(), reminder.getFrequency());
        assertNotNull(reminder.getStartTime());
        assertEquals(reminderTest.getStartTime(), reminder.getStartTime());
        assertNotNull(reminder.getInterval());
        assertEquals(reminderTest.getInterval(), reminder.getInterval());
    }

    @Test
    public void testDelete() throws Exception {

        int num = reminderDAOImpl.delete(4);

        assertNotNull(num);
        assertEquals(1, num);
    }

    @Test
    public void testUpdate() throws Exception {

        int num = reminderDAOImpl.update(reminder7);

        assertNotNull(num);
        assertEquals(1, num);
        findByIdTesting(3, reminder7);
    }
}
