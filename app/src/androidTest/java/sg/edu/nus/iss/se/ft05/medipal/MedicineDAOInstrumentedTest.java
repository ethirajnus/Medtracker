package sg.edu.nus.iss.se.ft05.medipal;

import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.dao.MedicineDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.domain.Medicine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MedicineDAOInstrumentedTest {


    private MedicineDAOImpl medicineDAOImpl;
    private Medicine medicine1;
    private Medicine medicine7;
    private Medicine medicine2;


    @Before
    public void setUp() {

        medicineDAOImpl = new MedicineDAOImpl(InstrumentationRegistry.getTargetContext());

        Cursor cursor = medicineDAOImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            medicineDAOImpl.delete(cursor.getInt(cursor.getColumnIndex(DBHelper.MEDICINE_KEY_ID)));
            cursor.moveToNext();
        }

        medicine1 = new Medicine("Name1", "Description1", 1, 1, true, 11, 11, 11, 11, "2017-01-01", 11);

        medicineDAOImpl.insert(medicine1);

        medicine2 = new Medicine("Name2", "Description2", 2, 2, true, 22, 22, 22, 22, "2017-01-02", 22);

        medicineDAOImpl.insert(medicine2);

        medicineDAOImpl.insert(new Medicine("Name3", "Description3", 3, 3, true, 33, 33, 33, 33, "2017-01-03", 33));

        medicineDAOImpl.insert(new Medicine("Name4", "Description4", 4, 4, true, 44, 44, 44, 44, "2017-01-04", 44));

        medicine7 = new Medicine("Name7", "Description7", 7, 7, true, 77, 77, 77, 77, "2017-01-07", 77);
        medicine7.setId(3);
    }

    @After
    public void finish() {

        medicineDAOImpl = new MedicineDAOImpl(InstrumentationRegistry.getTargetContext());

        Cursor cursor = medicineDAOImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            medicineDAOImpl.delete(cursor.getInt(cursor.getColumnIndex(DBHelper.MEDICINE_KEY_ID)));
            cursor.moveToNext();
        }

        medicineDAOImpl.close();
    }

    @Test
    public void testPreConditions() {

        assertNotNull(medicineDAOImpl);
    }


    @Test
    public void testFindAll() throws Exception {

        Cursor c = medicineDAOImpl.findAll();

        assertNotNull(c);

        c.moveToFirst();

        while (!c.isAfterLast()) {

            Medicine medicine = new Medicine();

            medicine.setId(c.getInt(c.getColumnIndex(DBHelper.MEDICINE_KEY_ID)));
            medicine.setName((c.getString(c.getColumnIndex(DBHelper.MEDICINE_KEY_MEDICINE))));
            medicine.setDescription(c.getString(c.getColumnIndex(DBHelper.MEDICINE_KEY_DESCRIPTION)));
            medicine.setCategoryId(c.getInt(c.getColumnIndex(DBHelper.MEDICINE_KEY_CATID)));
            medicine.setReminderId(c.getInt(c.getColumnIndex(DBHelper.MEDICINE_KEY_REMINDERID)));
            medicine.setRemind(c.getInt(c.getColumnIndex(DBHelper.CATEGORY_KEY_REMIND)) == 1);
            medicine.setQuantity(c.getInt(c.getColumnIndex(DBHelper.MEDICINE_KEY_QUANTITY)));
            medicine.setDosage(c.getInt(c.getColumnIndex(DBHelper.MEDICINE_KEY_DOSAGE)));
            medicine.setConsumeQuantity(c.getInt(c.getColumnIndex(DBHelper.MEDICINE_KEY_CONSUME_QUALITY)));
            medicine.setThreshold(c.getInt(c.getColumnIndex(DBHelper.MEDICINE_KEY_THRESHOLD)));
            medicine.setDateIssued(c.getString(c.getColumnIndex(DBHelper.MEDICINE_KEY_DATE_ISSUED)));
            medicine.setExpireFactor(c.getInt(c.getColumnIndex(DBHelper.MEDICINE_KEY_EXPIRE_FACTOR)));

            assertNotNull(medicine.getId());

            switch (medicine.getId()) {

                case 1:
                    assertNotNull(medicine.getName());
                    assertEquals(medicine1.getName(), medicine.getName());
                    assertNotNull(medicine.getDescription());
                    assertEquals(medicine1.getDescription(), medicine.getDescription());
                    assertNotNull(medicine.getCategoryId());
                    assertEquals(medicine1.getCategoryId(), medicine.getCategoryId());
                    assertNotNull(medicine.getReminderId());
                    assertEquals(medicine1.getReminderId(), medicine.getReminderId());
                    assertNotNull(medicine.getRemind());
                    assertEquals(medicine1.getRemind(), medicine.getRemind());
                    assertNotNull(medicine.getQuantity());
                    assertEquals(medicine1.getQuantity(), medicine.getQuantity());
                    assertNotNull(medicine.getDosage());
                    assertEquals(medicine1.getDosage(), medicine.getDosage());
                    assertNotNull(medicine.getConsumeQuantity());
                    assertEquals(medicine1.getConsumeQuantity(), medicine.getConsumeQuantity());
                    assertNotNull(medicine.getThreshold());
                    assertEquals(medicine1.getThreshold(), medicine.getThreshold());
                    assertNotNull(medicine.getDateIssued());
                    assertEquals(medicine1.getDateIssued(), medicine.getDateIssued());
                    assertNotNull(medicine.getExpireFactor());
                    assertEquals(medicine1.getExpireFactor(), medicine.getExpireFactor());
                    break;

                case 2:
                    assertNotNull(medicine.getName());
                    assertEquals(medicine2.getName(), medicine.getName());
                    assertNotNull(medicine.getDescription());
                    assertEquals(medicine2.getDescription(), medicine.getDescription());
                    assertNotNull(medicine.getCategoryId());
                    assertEquals(medicine2.getCategoryId(), medicine.getCategoryId());
                    assertNotNull(medicine.getReminderId());
                    assertEquals(medicine2.getReminderId(), medicine.getReminderId());
                    assertNotNull(medicine.getRemind());
                    assertEquals(medicine2.getRemind(), medicine.getRemind());
                    assertNotNull(medicine.getQuantity());
                    assertEquals(medicine2.getQuantity(), medicine.getQuantity());
                    assertNotNull(medicine.getDosage());
                    assertEquals(medicine2.getDosage(), medicine.getDosage());
                    assertNotNull(medicine.getConsumeQuantity());
                    assertEquals(medicine2.getConsumeQuantity(), medicine.getConsumeQuantity());
                    assertNotNull(medicine.getThreshold());
                    assertEquals(medicine2.getThreshold(), medicine.getThreshold());
                    assertNotNull(medicine.getDateIssued());
                    assertEquals(medicine2.getDateIssued(), medicine.getDateIssued());
                    assertNotNull(medicine.getExpireFactor());
                    assertEquals(medicine2.getExpireFactor(), medicine.getExpireFactor());
                    break;
            }
            c.moveToNext();
        }
    }

    @Test
    public void testFindById() throws Exception {

        findByIdTesting(1, medicine1);
    }

    private void findByIdTesting(int id, Medicine medicineTest) {

        Medicine medicine = medicineDAOImpl.findById(id);

        assertNotNull(medicine);

        assertNotNull(medicine.getName());
        assertEquals(medicineTest.getName(), medicine.getName());
        assertNotNull(medicine.getDescription());
        assertEquals(medicineTest.getDescription(), medicine.getDescription());
        assertNotNull(medicine.getCategoryId());
        assertEquals(medicineTest.getCategoryId(), medicine.getCategoryId());
        assertNotNull(medicine.getReminderId());
        assertEquals(medicineTest.getReminderId(), medicine.getReminderId());
        assertNotNull(medicine.getRemind());
        assertEquals(medicineTest.getRemind(), medicine.getRemind());
        assertNotNull(medicine.getQuantity());
        assertEquals(medicineTest.getQuantity(), medicine.getQuantity());
        assertNotNull(medicine.getDosage());
        assertEquals(medicineTest.getDosage(), medicine.getDosage());
        assertNotNull(medicine.getConsumeQuantity());
        assertEquals(medicineTest.getConsumeQuantity(), medicine.getConsumeQuantity());
        assertNotNull(medicine.getThreshold());
        assertEquals(medicineTest.getThreshold(), medicine.getThreshold());
        assertNotNull(medicine.getDateIssued());
        assertEquals(medicineTest.getDateIssued(), medicine.getDateIssued());
        assertNotNull(medicine.getExpireFactor());
        assertEquals(medicineTest.getExpireFactor(), medicine.getExpireFactor());
    }

    @Test
    public void testDelete() throws Exception {

        int num = medicineDAOImpl.delete(4);

        assertNotNull(num);
        assertEquals(1, num);
    }

    @Test
    public void testUpdate() throws Exception {

        int num = medicineDAOImpl.update(medicine7);

        assertNotNull(num);
        assertEquals(1, num);
        findByIdTesting(3, medicine7);
    }

    @Test
    public void testFetchAllMedicinesWithId() throws Exception {

        Cursor cursor = medicineDAOImpl.fetchAllMedicinesWithId();

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            int id = cursor.getInt(cursor.getColumnIndex(DBHelper.MEDICINE_KEY_ID));
            String medicine = cursor.getString(cursor.getColumnIndex(DBHelper.MEDICINE_KEY_MEDICINE));

            assertNotNull(id);

            switch (id) {

                case 1:
                    assertNotNull(medicine);
                    assertEquals(medicine1.getName(), medicine);
                    break;

                case 2:
                    assertNotNull(medicine);
                    assertEquals(medicine2.getName(), medicine);
                    break;
            }

            cursor.moveToNext();
        }
    }
}
