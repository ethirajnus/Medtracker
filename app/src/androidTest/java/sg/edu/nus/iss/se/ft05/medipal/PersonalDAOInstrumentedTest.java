package sg.edu.nus.iss.se.ft05.medipal;

import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import sg.edu.nus.iss.se.ft05.medipal.constants.DbConstants;
import sg.edu.nus.iss.se.ft05.medipal.dao.PersonalBioDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.domain.PersonalBio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PersonalDAOInstrumentedTest {


    private PersonalBioDAOImpl personalBioDAOImpl;
    private PersonalBio personalBio1;
    private PersonalBio personalBio2;
    private PersonalBio personalBio7;

    @Before
    public void setUp() {

        personalBioDAOImpl = new PersonalBioDAOImpl(InstrumentationRegistry.getTargetContext());

        Cursor cursor = personalBioDAOImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            personalBioDAOImpl.delete(cursor.getInt(cursor.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_ID)));
            cursor.moveToNext();
        }

        personalBio1 = new PersonalBio("Name1", "2017-01-01", "IDNO1", "Address1", "111111", "111", "A+");

        personalBioDAOImpl.insert(personalBio1);

        personalBio2 = new PersonalBio("Name2", "2017-01-02", "IDNO2", "Address2", "222222", "222", "A-");

        personalBioDAOImpl.insert(personalBio2);

        personalBioDAOImpl.insert(new PersonalBio("Name3", "2017-01-03", "IDNO3", "Address3", "333333", "333", "B+"));

        personalBioDAOImpl.insert(new PersonalBio("Name1", "2017-01-04", "IDNO4", "Address4", "444444", "444", "B-"));

        personalBio7 = new PersonalBio("Name7", "2017-01-07", "IDNO7", "Address7", "777777", "777", "O+");
        personalBio7.setId(3);
    }

    @After
    public void finish() {

        personalBioDAOImpl = new PersonalBioDAOImpl(InstrumentationRegistry.getTargetContext());

        Cursor cursor = personalBioDAOImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            personalBioDAOImpl.delete(cursor.getInt(cursor.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_ID)));
            cursor.moveToNext();
        }

        personalBioDAOImpl.close();
    }

    @Test
    public void testPreConditions() {

        assertNotNull(personalBioDAOImpl);
    }


    @Test
    public void testFindAll() throws Exception {

        Cursor cursor = personalBioDAOImpl.findAll();

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            int id = cursor.getInt(cursor.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_ID));
            String name = cursor.getString(cursor.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_NAME));
            String dob = cursor.getString(cursor.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_DOB));
            String idno = cursor.getString(cursor.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_IDNO));
            String address = cursor.getString(cursor.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_ADDRESS));
            String zipcode = cursor.getString(cursor.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_POSTAL_CODE));
            String height = cursor.getString(cursor.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_HEIGHT));
            String blood = cursor.getString(cursor.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_BLOOD_TYPE));

            assertNotNull(id);

            switch (id) {

                case 1:
                    assertNotNull(name);
                    assertEquals(personalBio1.getName(), name);
                    assertNotNull(dob);
                    assertEquals(personalBio1.getDob(), dob);
                    assertNotNull(idno);
                    assertEquals(personalBio1.getIdNo(), idno);
                    assertNotNull(address);
                    assertEquals(personalBio1.getAddress(), address);
                    assertNotNull(zipcode);
                    assertEquals(personalBio1.getPostalCode(), zipcode);
                    assertNotNull(height);
                    assertEquals(personalBio1.getHeight(), height);
                    assertNotNull(blood);
                    assertEquals(personalBio1.getBloodType(), blood);
                    break;

                case 2:
                    assertNotNull(name);
                    assertEquals(personalBio2.getName(), name);
                    assertNotNull(dob);
                    assertEquals(personalBio2.getDob(), dob);
                    assertNotNull(idno);
                    assertEquals(personalBio2.getIdNo(), idno);
                    assertNotNull(address);
                    assertEquals(personalBio2.getAddress(), address);
                    assertNotNull(zipcode);
                    assertEquals(personalBio2.getPostalCode(), zipcode);
                    assertNotNull(height);
                    assertEquals(personalBio2.getHeight(), height);
                    assertNotNull(blood);
                    assertEquals(personalBio2.getBloodType(), blood);
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFindById() throws Exception {

        findByIdTesting(1, personalBio1);
    }

    private void findByIdTesting(int id, PersonalBio personalBioTest) {

        PersonalBio personalBio = personalBioDAOImpl.findById(id);

        assertNotNull(personalBio);

        assertNotNull(personalBio.getName());
        assertEquals(personalBioTest.getName(), personalBio.getName());
        assertNotNull(personalBio.getDob());
        assertEquals(personalBioTest.getDob(), personalBio.getDob());
        assertNotNull(personalBio.getIdNo());
        assertEquals(personalBioTest.getIdNo(), personalBio.getIdNo());
        assertNotNull(personalBio.getAddress());
        assertEquals(personalBioTest.getAddress(), personalBio.getAddress());
        assertNotNull(personalBio.getPostalCode());
        assertEquals(personalBioTest.getPostalCode(), personalBio.getPostalCode());
        assertNotNull(personalBio.getHeight());
        assertEquals(personalBioTest.getHeight(), personalBio.getHeight());
        assertNotNull(personalBio.getBloodType());
        assertEquals(personalBioTest.getBloodType(), personalBio.getBloodType());
    }

    @Test
    public void testDelete() throws Exception {

        int num = personalBioDAOImpl.delete(4);

        assertNotNull(num);
        assertEquals(1, num);
    }

    @Test
    public void testUpdate() throws Exception {

        int num = personalBioDAOImpl.update(personalBio7);

        assertNotNull(num);
        assertEquals(1, num);
        findByIdTesting(3, personalBio7);
    }

    @Test
    public void testFindPersonalBioId() throws Exception {

        int id = personalBioDAOImpl.findPersonalBioId("Name2", "2017-01-02", "IDNO2");

        assertNotNull(id);
        assertEquals(2, id);
    }
}
