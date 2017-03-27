package sg.edu.nus.iss.se.ft05.medipal;

import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import sg.edu.nus.iss.se.ft05.medipal.daoutils.DbConstants;
import sg.edu.nus.iss.se.ft05.medipal.dao.HealthBioDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.domain.HealthBio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 * @author Moushumi Seal
 */
@RunWith(AndroidJUnit4.class)
public class HealthBioDAOInstrumentedTest {


    private HealthBioDAOImpl healthBioImpl;
    private HealthBio healthBio1;
    private HealthBio healthBio2;
    private HealthBio healthBio7;

    @Before
    public void setUp() {

        healthBioImpl = new HealthBioDAOImpl(InstrumentationRegistry.getTargetContext());

        Cursor cursor = healthBioImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            healthBioImpl.delete(cursor.getInt(cursor.getColumnIndex(DbConstants.HEALTH_BIO_KEY_ID)));
            cursor.moveToNext();
        }

        healthBio1 = new HealthBio("Condition1", "ConditionType1", "2017-01-01");

        healthBioImpl.insert(healthBio1);

        healthBio2 = new HealthBio("Condition2", "ConditionType2", "2017-01-02");

        healthBioImpl.insert(healthBio2);

        healthBioImpl.insert(new HealthBio("Condition3", "ConditionType3", "2017-01-03"));

        healthBioImpl.insert(new HealthBio("Condition4", "ConditionType4", "2017-01-04"));

        healthBio7 = new HealthBio("Condition7", "ConditionType7", "2017-01-07");
        healthBio7.setId(3);
    }

    @After
    public void finish() {

        Cursor cursor = healthBioImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            healthBioImpl.delete(cursor.getInt(cursor.getColumnIndex(DbConstants.HEALTH_BIO_KEY_ID)));
            cursor.moveToNext();
        }

        healthBioImpl.close();
    }

    @Test
    public void testPreConditions() {

        assertNotNull(healthBioImpl);
    }


    @Test
    public void testFindAll() throws Exception {

        Cursor cursor = healthBioImpl.findAll();

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            int id = cursor.getInt(cursor.getColumnIndex(DbConstants.HEALTH_BIO_KEY_ID));
            String condition = cursor.getString(cursor.getColumnIndex(DbConstants.HEALTH_BIO_KEY_CONDITION));
            String conditionType = cursor.getString(cursor.getColumnIndex(DbConstants.HEALTH_BIO_KEY_CONDITION_TYPE));
            String date = cursor.getString(cursor.getColumnIndex(DbConstants.HEALTH_BIO_KEY_START_DATE));

            assertNotNull(id);

            switch (id) {

                case 1:
                    assertNotNull(condition);
                    assertEquals(healthBio1.getCondition(), condition);
                    assertNotNull(conditionType);
                    assertEquals(healthBio1.getConditionType(), conditionType);
                    assertNotNull(date);
                    assertEquals(healthBio1.getStartDate(), date);
                    break;

                case 2:
                    assertNotNull(condition);
                    assertEquals(healthBio2.getCondition(), condition);
                    assertNotNull(conditionType);
                    assertEquals(healthBio2.getConditionType(), conditionType);
                    assertNotNull(date);
                    assertEquals(healthBio2.getStartDate(), date);
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFindById() throws Exception {

        findByIdTesting(1, healthBio1);
    }

    private void findByIdTesting(int id, HealthBio healthBioTest) {

        HealthBio healthBio = healthBioImpl.findById(id);

        assertNotNull(healthBio);

        assertNotNull(healthBio.getCondition());
        assertEquals(healthBioTest.getCondition(), healthBio.getCondition());
        assertNotNull(healthBio.getConditionType());
        assertEquals(healthBioTest.getConditionType(), healthBio.getConditionType());
        assertNotNull(healthBio.getStartDate());
        assertEquals(healthBioTest.getStartDate(), healthBio.getStartDate());
    }

    @Test
    public void testDelete() throws Exception {

        int num = healthBioImpl.delete(4);

        assertNotNull(num);
        assertEquals(1, num);
    }

    @Test
    public void testUpdate() throws Exception {

        int num = healthBioImpl.update(healthBio7);

        assertNotNull(num);
        assertEquals(1, num);
        findByIdTesting(3, healthBio7);
    }

    @Test
    public void testFetchAllHealthBioWithId() throws Exception {

        Cursor cursor = healthBioImpl.fetchAllHealthBioWithId();

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            int id = cursor.getInt(cursor.getColumnIndex(DbConstants.HEALTH_BIO_KEY_ID));
            String condition = cursor.getString(cursor.getColumnIndex(DbConstants.HEALTH_BIO_KEY_CONDITION));

            assertNotNull(id);

            switch (id) {

                case 1:
                    assertNotNull(condition);
                    assertEquals(healthBio1.getCondition(), condition);
                    break;

                case 2:
                    assertNotNull(condition);
                    assertEquals(healthBio2.getCondition(), condition);
                    break;
            }

            cursor.moveToNext();
        }
    }
}
