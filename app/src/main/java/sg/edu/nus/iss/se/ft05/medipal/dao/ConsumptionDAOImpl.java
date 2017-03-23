package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.model.Appointment;
import sg.edu.nus.iss.se.ft05.medipal.model.Consumption;
import sg.edu.nus.iss.se.ft05.medipal.model.Medicine;

/**
 * Created by ethi on 10/03/17.
 */

public class ConsumptionDAOImpl extends DBHelper implements ConsumptionDAO {

    private static final String LOG = "ConsumptionDAOImpl";

    public ConsumptionDAOImpl(Context context) {
        super(context);
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CONSUMPTION, CONSUMPTION_KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    @Override
    public Cursor findAll() {
        String selectQuery = "SELECT  * FROM " + TABLE_CONSUMPTION;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);


    }

    @Override
    public Consumption findById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_CONSUMPTION + " WHERE "
                + CONSUMPTION_KEY_ID + " = " + id;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            c.moveToFirst();
        }

        Consumption consumption = new Consumption();
        consumption.setId(c.getInt(c.getColumnIndex(CONSUMPTION_KEY_ID)));
        consumption.setMedicineId(c.getInt(c.getColumnIndex(CONSUMPTION_KEY_MEDICINEID)));
        consumption.setQuantity(c.getInt(c.getColumnIndex(CONSUMPTION_KEY_QUANTITY)));
        consumption.setDate(c.getString(c.getColumnIndex(CONSUMPTION_KEY_DATE)));
        consumption.setTime(c.getString(c.getColumnIndex(CONSUMPTION_KEY_TIME)));
        return consumption;
    }

    /*
     * Creating a consumption
     */
    @Override
    public long insert(Consumption consumption) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONSUMPTION_KEY_MEDICINEID, consumption.getMedicineId());
        values.put(CONSUMPTION_KEY_QUANTITY, consumption.getQuantity());
        values.put(CONSUMPTION_KEY_DATE, consumption.getDate());
        values.put(CONSUMPTION_KEY_TIME, consumption.getTime());

        // insert row
        return db.insert(TABLE_CONSUMPTION, null, values);
    }

    @Override
    public int update(Consumption consumption) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONSUMPTION_KEY_MEDICINEID, consumption.getMedicineId());
        values.put(CONSUMPTION_KEY_QUANTITY, consumption.getQuantity());
        values.put(CONSUMPTION_KEY_DATE, consumption.getDate());
        values.put(CONSUMPTION_KEY_TIME, consumption.getTime());
        // updating row
        return db.update(TABLE_CONSUMPTION, values, CONSUMPTION_KEY_ID + " = ?",
                new String[]{String.valueOf(consumption.getId())});
    }

    @Override
    public List<Consumption> findByDate(String date) {
        List<Consumption> consumptions = new ArrayList();

        String selectQuery = "SELECT  * FROM " + TABLE_CONSUMPTION + " where " + CONSUMPTION_KEY_DATE + " = '" + date + "'";

        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(CONSUMPTION_KEY_TIME)));
            consumptions.add(consumption);
            cursor.moveToNext();
        }
        return  consumptions;

    }

    @Override
    public int totalQuantityConsumed(int medicineId) {
        String selectQuery = "SELECT  SUM(" +CONSUMPTION_KEY_QUANTITY+") FROM " + TABLE_CONSUMPTION + " where " + CONSUMPTION_KEY_MEDICINEID + " = " + medicineId ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        return  cursor.getInt(0);

    }

    public List<Consumption> findByMedicineID(int medicineId) {

        List<Consumption> consumptions = new ArrayList();

        String selectQuery = "SELECT  * FROM " + TABLE_CONSUMPTION + " where " + CONSUMPTION_KEY_MEDICINEID + " = " + medicineId;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
        {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(CONSUMPTION_KEY_TIME)));
            consumptions.add(consumption);
            cursor.moveToNext();
        }
        return  consumptions;

    }
}
