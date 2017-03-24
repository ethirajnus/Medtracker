package sg.edu.nus.iss.se.ft05.medipal;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.dao.ICEContactsDAOImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DBHelperInstrumentedTest {


    private ICEContactsDAOImpl iceContactsDAOImpl;

    @Before
    public void setUp() {

        iceContactsDAOImpl = new ICEContactsDAOImpl(InstrumentationRegistry.getTargetContext());
    }

    @After
    public void finish() {
        iceContactsDAOImpl.close();
    }

    @Test
    public void testPreConditions() {
        assertNotNull(iceContactsDAOImpl);
    }


    @Test
    public void testFindAll() throws Exception {

        Cursor cursor = iceContactsDAOImpl.findAll();

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            String name = cursor.getString(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_NAME));
            String type = cursor.getString(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_TYPE));
            long phone = cursor.getLong(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_PHONE));
            int id = cursor.getInt(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_ID));

            System.out.println("date : " + name);


            assertNotNull(name);
            assertEquals("swamini", name);
            assertNotNull(type);
            assertEquals("Next of Kin", type);
            assertNotNull(phone);
            assertEquals(85858585, phone);
            assertNotNull(id);
            assertEquals(1, id);


            cursor.moveToNext();
        }
    }
}
