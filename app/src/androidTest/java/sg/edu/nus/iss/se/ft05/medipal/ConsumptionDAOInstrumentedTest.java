package sg.edu.nus.iss.se.ft05.medipal;

import android.database.Cursor;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.constants.DbConstants;
import sg.edu.nus.iss.se.ft05.medipal.dao.ConsumptionDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper;
import sg.edu.nus.iss.se.ft05.medipal.domain.Appointment;
import sg.edu.nus.iss.se.ft05.medipal.domain.Consumption;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @author Ethiraj Srinivasan
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ConsumptionDAOInstrumentedTest {


    private ConsumptionDAOImpl consumptionDAOImpl;
    private Consumption consumption1;
    private Consumption consumption2;
    private Consumption consumption5;
    private Consumption consumption7;
    private Consumption consumption8;

    @Before
    public void setUp() {

        consumptionDAOImpl = new ConsumptionDAOImpl(InstrumentationRegistry.getTargetContext());

        Cursor cursor = consumptionDAOImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            consumptionDAOImpl.delete(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_ID)));
            cursor.moveToNext();
        }

        consumption1 = new Consumption(1, 11, "2017-01-01", "01:01");

        consumptionDAOImpl.insert(consumption1);

        consumption2 = new Consumption(2, 22, "2017-02-02", "02:02");

        consumptionDAOImpl.insert(consumption2);

        consumptionDAOImpl.insert(new Consumption(3, 33, "2017-01-03", "03:03"));

        consumptionDAOImpl.insert(new Consumption(4, 44, "2017-01-04", "04:04"));

        consumption5 = new Consumption(5, 55, "2017-01-02", "05:05");

        consumptionDAOImpl.insert(consumption5);

        consumptionDAOImpl.insert(new Consumption(1, 66, "2017-01-06", "06:06"));

        consumptionDAOImpl.insert(new Consumption(8, 88, "2017-01-08", "08:08"));

        consumption8 = new Consumption(8, 0, "2017-01-09", "09:09");

        consumptionDAOImpl.insert(consumption8);

        consumption7 = new Consumption(7, 77, "2017-01-07", "07:07");
        consumption7.setId(3);
    }

    @After
    public void finish() {

        Cursor cursor = consumptionDAOImpl.findAll();

        if (null != cursor) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            consumptionDAOImpl.delete(cursor.getInt(cursor.getColumnIndex(DbConstants.HEALTH_BIO_KEY_ID)));
            cursor.moveToNext();
        }
        consumptionDAOImpl.close();
    }

    @Test
    public void testPreConditions() {

        assertNotNull(consumptionDAOImpl);
    }


    @Test
    public void testFindAll() throws Exception {

        Cursor cursor = consumptionDAOImpl.findAll();

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_TIME)));

            assertNotNull(consumption.getId());

            switch (consumption.getId()) {

                case 1:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption1.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption1.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption1.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption1.getTime(), consumption.getTime());
                    break;

                case 2:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption2.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption2.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption2.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption2.getTime(), consumption.getTime());
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFindById() throws Exception {

        findByIdTesting(1, consumption1);
    }

    private void findByIdTesting(int id, Consumption consumptionTest) {

        Consumption consumption = consumptionDAOImpl.findById(id);

        assertNotNull(consumption);

        assertNotNull(consumption.getMedicineId());
        assertEquals(consumptionTest.getMedicineId(), consumption.getMedicineId());
        assertNotNull(consumption.getQuantity());
        assertEquals(consumptionTest.getQuantity(), consumption.getQuantity());
        assertNotNull(consumption.getDate());
        assertEquals(consumptionTest.getDate(), consumption.getDate());
        assertNotNull(consumption.getTime());
        assertEquals(consumptionTest.getTime(), consumption.getTime());
    }

    @Test
    public void testDelete() throws Exception {

        int num = consumptionDAOImpl.delete(4);

        assertNotNull(num);
        assertEquals(1, num);
    }

    @Test
    public void testDeleteForAllMedicine() throws Exception {

        int num = consumptionDAOImpl.deleteAllForMedicine(8);

        assertNotNull(num);
        assertEquals(2, num);
    }

    @Test
    public void testUpdate() throws Exception {

        int num = consumptionDAOImpl.update(consumption7);

        assertNotNull(num);
        assertEquals(1, num);
        findByIdTesting(3, consumption7);
    }

    @Test
    public void testFindByDate() throws Exception {

        List<Consumption> consumptionList = consumptionDAOImpl.findByDate("2017-01-02");

        assertNotNull(consumptionList);
        assertFalse(consumptionList.isEmpty());

        for (Consumption consumption : consumptionList) {

            switch (consumption.getId()) {

                case 2:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption2.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption2.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption2.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption2.getTime(), consumption.getTime());
                    break;

                case 5:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption5.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption5.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption5.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption5.getTime(), consumption.getTime());
                    break;
            }
        }
    }

    @Test
    public void testFindByMedicineID() throws Exception {

        List<Consumption> consumptionList = consumptionDAOImpl.findByMedicineID(5);

        assertNotNull(consumptionList);
        assertFalse(consumptionList.isEmpty());

        for (Consumption consumption : consumptionList) {

            switch (consumption.getId()) {

                case 5:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption5.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption5.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption5.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption5.getTime(), consumption.getTime());
                    break;
            }
        }
    }

    @Test
    public void testTotalQuantityConsumed() throws Exception {

        int quantity = consumptionDAOImpl.totalQuantityConsumed(1);

        assertNotNull(quantity);
        assertEquals(77, quantity);
    }

    @Test
    public void testBetweenDate() throws Exception {

        Cursor cursor = consumptionDAOImpl.betweenDate("2017-01-01", "2017-01-03");

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_TIME)));

            assertNotNull(consumption.getId());

            switch (consumption.getId()) {

                case 1:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption1.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption1.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption1.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption1.getTime(), consumption.getTime());
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFetchByMedicine() throws Exception {

        Cursor cursor = consumptionDAOImpl.fetchByMedicine(1);

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_TIME)));

            assertNotNull(consumption.getId());

            switch (consumption.getId()) {

                case 1:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption1.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption1.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption1.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption1.getTime(), consumption.getTime());
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFetchByMedicineAndDate() throws Exception {

        Cursor cursor = consumptionDAOImpl.fetchByMedicineAndDate(1, "2017-01-01");

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_TIME)));

            assertNotNull(consumption.getId());

            switch (consumption.getId()) {

                case 1:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption1.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption1.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption1.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption1.getTime(), consumption.getTime());
                    break;
            }

            cursor.moveToNext();
        }
    }


    @Test
    public void testFetchByMedicineAndYear() throws Exception {

        Cursor cursor = consumptionDAOImpl.fetchByMedicineAndYear(1, "2017");

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_TIME)));

            assertNotNull(consumption.getId());

            switch (consumption.getId()) {

                case 1:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption1.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption1.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption1.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption1.getTime(), consumption.getTime());
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFetchByMedicineDateAndTime() throws Exception {

        Cursor cursor = consumptionDAOImpl.fetchByMedicineDateAndTime(1, "2017-01-01", "01:01");

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_TIME)));

            assertNotNull(consumption.getId());

            switch (consumption.getId()) {

                case 1:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption1.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption1.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption1.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption1.getTime(), consumption.getTime());
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFetchByMedicineAndMonth() throws Exception {

        Cursor cursor = consumptionDAOImpl.fetchByMedicineAndMonth(1, "2017", "1");

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_TIME)));

            assertNotNull(consumption.getId());

            switch (consumption.getId()) {

                case 1:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption1.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption1.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption1.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption1.getTime(), consumption.getTime());
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFetchByMedicineAndDateUnconsumed() throws Exception {

        Cursor cursor = consumptionDAOImpl.fetchByMedicineAndDateUnconsumed(8, "2017-01-09");

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_TIME)));

            assertNotNull(consumption.getId());

            switch (consumption.getId()) {

                case 8:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption8.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption8.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption8.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption8.getTime(), consumption.getTime());
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFetchByMedicineAndYearUnconsumed() throws Exception {

        Cursor cursor = consumptionDAOImpl.fetchByMedicineAndYearUnconsumed(8, "2017");

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_TIME)));

            assertNotNull(consumption.getId());

            switch (consumption.getId()) {

                case 8:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption8.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption8.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption8.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption8.getTime(), consumption.getTime());
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFetchByMedicineAndMonthUnconsumed() throws Exception {

        Cursor cursor = consumptionDAOImpl.fetchByMedicineAndMonthUnconsumed(8, "2017", "1");

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_TIME)));

            assertNotNull(consumption.getId());

            switch (consumption.getId()) {

                case 8:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption8.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption8.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption8.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption8.getTime(), consumption.getTime());
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFetchByMedicineAndBetweenDates() throws Exception {

        Cursor cursor = consumptionDAOImpl.fetchByMedicineAndMonthUnconsumed(1, "2017-01-01", "2017-01-02");

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_TIME)));

            assertNotNull(consumption.getId());

            switch (consumption.getId()) {

                case 1:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption1.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption1.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption1.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption1.getTime(), consumption.getTime());
                    break;
            }

            cursor.moveToNext();
        }
    }

    @Test
    public void testFetchByMedicineAndBetweenDatesUnconsumed() throws Exception {

        Cursor cursor = consumptionDAOImpl.fetchByMedicineAndBetweenDatesUnconsumed(8, "2017-01-08", "2017-01-10");

        assertNotNull(cursor);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(DBHelper.CONSUMPTION_KEY_TIME)));

            assertNotNull(consumption.getId());

            switch (consumption.getId()) {

                case 8:
                    assertNotNull(consumption.getMedicineId());
                    assertEquals(consumption8.getMedicineId(), consumption.getMedicineId());
                    assertNotNull(consumption.getQuantity());
                    assertEquals(consumption8.getQuantity(), consumption.getQuantity());
                    assertNotNull(consumption.getDate());
                    assertEquals(consumption8.getDate(), consumption.getDate());
                    assertNotNull(consumption.getTime());
                    assertEquals(consumption8.getTime(), consumption.getTime());
                    break;
            }

            cursor.moveToNext();
        }
    }
}
