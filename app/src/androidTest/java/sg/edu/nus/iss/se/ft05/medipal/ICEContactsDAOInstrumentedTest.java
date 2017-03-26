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
import sg.edu.nus.iss.se.ft05.medipal.domain.ICEContact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

/**
 * Class for ICE contacts database operations instrumented test
 */
@RunWith(AndroidJUnit4.class)
public class ICEContactsDAOInstrumentedTest {


    private ICEContactsDAOImpl iceContactsDAOImpl;
    private ICEContact contact1;
    private ICEContact contact2;
    private ICEContact contact5;

    @Before
    public void setUp() {

        iceContactsDAOImpl = new ICEContactsDAOImpl(InstrumentationRegistry.getTargetContext());

        Cursor cursor = iceContactsDAOImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            iceContactsDAOImpl.delete(cursor.getInt(cursor.getColumnIndex(ICEContactsDAOImpl.ICE_CONTACTS_KEY_ID)));
            cursor.moveToNext();
        }

        contact1 = new ICEContact("Name1", "Desc1", "Next to Kin", Long.parseLong("10101010"));

        iceContactsDAOImpl.insert(contact1);

        contact2 = new ICEContact("Name2", "Desc2", "Others", Long.parseLong("25252525"));

        iceContactsDAOImpl.insert(contact2);

        iceContactsDAOImpl.insert(new ICEContact("Name3", "Desc3", "Physician", Long.parseLong("98989898")));

        iceContactsDAOImpl.insert(new ICEContact("Name4", "Desc4", "Others", Long.parseLong("36363636")));

        iceContactsDAOImpl.insert(new ICEContact("Name5", "Desc5", "Others", Long.parseLong("64646464")));

        iceContactsDAOImpl.insert(new ICEContact("Name6", "Desc6", "Others", Long.parseLong("92929292")));

        contact5 = new ICEContact("Name8", "Desc8", "Next to Kin", Long.parseLong("83838383"));
        contact5.setId(3);
        contact5.setPriority(3);
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

            int id = cursor.getInt(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_ID));
            String name = cursor.getString(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_NAME));
            String desc = cursor.getString(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_DESC));
            String type = cursor.getString(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_TYPE));
            long phone = cursor.getLong(cursor.getColumnIndex(DBHelper.ICE_CONTACTS_KEY_PHONE));

            assertNotNull(id);

            switch (id) {

                case 1:
                    assertNotNull(name);
                    assertEquals(contact1.getName(), name);
                    assertNotNull(desc);
                    assertEquals(contact1.getDescription(), desc);
                    assertNotNull(type);
                    assertEquals(contact1.getType(), type);
                    assertNotNull(phone);
                    assertEquals(contact1.getPhone(), phone);
                    break;

                case 2:
                    assertNotNull(name);
                    assertEquals(contact2.getName(), name);
                    assertNotNull(desc);
                    assertEquals(contact2.getDescription(), desc);
                    assertNotNull(type);
                    assertEquals(contact2.getType(), type);
                    assertNotNull(phone);
                    assertEquals(contact2.getPhone(), phone);
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFindById() throws Exception {

        findByIdTesting(1, contact1);
    }

    private void findByIdTesting(int id, ICEContact contactAssert) {

        ICEContact iceContact = iceContactsDAOImpl.findById(id);

        assertNotNull(iceContact);

        assertNotNull(iceContact.getName());
        assertEquals(contactAssert.getName(), iceContact.getName());
        assertNotNull(iceContact.getDescription());
        assertEquals(contactAssert.getDescription(), iceContact.getDescription());
        assertNotNull(iceContact.getType());
        assertEquals(contactAssert.getType(), iceContact.getType());
        assertNotNull(iceContact.getPhone());
        assertEquals(contactAssert.getPhone(), iceContact.getPhone());
    }

    @Test
    public void testDelete() throws Exception {

        int num = iceContactsDAOImpl.delete(4);

        assertNotNull(num);
        assertEquals(1, num);
    }

    @Test
    public void testUpdate() throws Exception {

        int num = iceContactsDAOImpl.update(contact5);

        assertNotNull(num);
        assertEquals(1, num);
        findByIdTesting(3, contact5);
    }

    @Test
    public void testUpdatePriority() throws Exception {

        int num = iceContactsDAOImpl.updatePriority(5, 8);

        assertNotNull(num);
        assertEquals(1, num);

        num = iceContactsDAOImpl.updatePriority(6, 5);

        assertNotNull(num);
        assertEquals(1, num);

        num = iceContactsDAOImpl.updatePriority(8, 6);

        assertNotNull(num);
        assertEquals(1, num);


        ICEContact iceContact = iceContactsDAOImpl.findById(6);

        assertNotNull(iceContact);
        assertNotNull(iceContact.getPriority());
        assertEquals(5, iceContact.getPriority());
    }

    @Test
    public void testFindMaxPriority() throws Exception {

        int priority = iceContactsDAOImpl.findMaxPriority();

        assertNotNull(priority);
        assertEquals(6, priority);
    }
}
