package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import sg.edu.nus.iss.se.ft05.medipal.domain.Measurement;

import static sg.edu.nus.iss.se.ft05.medipal.constants.DbConstants.*;



/**
 * Implementatin class for measurement database operations
 * @author Aakash Deep Mangalore
 */
public class MeasurementDAOImpl extends DBHelper implements MeasurementDAO {

    private static final String LOG = "MeasurementDAOImpl";

    public MeasurementDAOImpl(Context context) {
        super(context);
    }

    /**
     * Delete operation
     * @param id
     * @return
     */
    @Override
    public int delete(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_MEASUREMENT, MEASUREMENT_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(id)});
    }

    /**
     * Select all operation
     * @return
     */
    @Override
    public Cursor findAll() {

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_MEASUREMENT;

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);


    }

    /**
     * select operation with where clause using ID
     * @param id
     * @return
     */
    @Override
    public Measurement findById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_MEASUREMENT + DATABASE_COMMAND_SELECT_WHERE
                + MEASUREMENT_KEY_ID + DATABASE_COMMAND_SYMBOL_EQUAL + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            c.moveToFirst();
        }

        Measurement measurement = new Measurement();

        measurement.setId(c.getInt(c.getColumnIndex(MEASUREMENT_KEY_ID)));
        measurement.setSystolic(c.getInt(c.getColumnIndex(MEASUREMENT_KEY_SYSTOLIC)));
        measurement.setDiastolic(c.getInt(c.getColumnIndex(MEASUREMENT_KEY_DIASTOLIC)));
        measurement.setPulse(c.getInt(c.getColumnIndex(MEASUREMENT_KEY_PULSE)));
        measurement.setTemperature(c.getFloat(c.getColumnIndex(MEASUREMENT_KEY_TEMPERATURE)));
        measurement.setWeight(c.getInt(c.getColumnIndex(MEASUREMENT_KEY_WEIGHT)));
        measurement.setMeasuredOn(c.getString(c.getColumnIndex(MEASUREMENT_KEY_MEASURED_ON)));
        db.close();
        return measurement;
    }

    /*
     * Creating a measurement
     */
    @Override
    public long insert(Measurement measurement) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MEASUREMENT_KEY_SYSTOLIC, measurement.getSystolic());
        values.put(MEASUREMENT_KEY_DIASTOLIC, measurement.getDiastolic());
        values.put(MEASUREMENT_KEY_PULSE, measurement.getPulse());
        values.put(MEASUREMENT_KEY_TEMPERATURE, measurement.getTemperature());
        values.put(MEASUREMENT_KEY_WEIGHT, measurement.getWeight());
        values.put(MEASUREMENT_KEY_MEASURED_ON, measurement.getMeasuredOn());

        // insert row
        return db.insert(TABLE_MEASUREMENT, null, values);
    }

    /**
     * Update operation
     * @param measurement
     * @return
     */
    @Override
    public int update(Measurement measurement) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(MEASUREMENT_KEY_SYSTOLIC, measurement.getSystolic());
        values.put(MEASUREMENT_KEY_DIASTOLIC, measurement.getDiastolic());
        values.put(MEASUREMENT_KEY_PULSE, measurement.getPulse());
        values.put(MEASUREMENT_KEY_TEMPERATURE, measurement.getTemperature());
        values.put(MEASUREMENT_KEY_WEIGHT, measurement.getWeight());
        values.put(MEASUREMENT_KEY_MEASURED_ON, measurement.getMeasuredOn());

        // updating row
        return db.update(TABLE_MEASUREMENT, values, MEASUREMENT_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(measurement.getId())});
    }

    /**
     *
     * @return
     */
    @Override
    public Cursor fetchAllMeasurementsWithId() {

        String selectQuery = "SELECT  id,measuredOn FROM " + TABLE_MEASUREMENT;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     *
     * @return
     */
    @Override
    public int fetchMaxId() {

        int id = -1;

        String selectQuery = "SELECT MAX(id) AS MAXID FROM " + TABLE_MEASUREMENT;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            c.moveToFirst();
            id = c.getInt(c.getColumnIndex("MAXID"));
        }
        db.close();
        return id;
    }

    /**
     *
     * @return
     */
    @Override
    public Measurement findLatest() {

        Measurement measurement = new Measurement();
        Cursor cursor;
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_MEASUREMENT + " order by measuredON";
        cursor = db.rawQuery(selectQuery, null);

        boolean systolic = true;
        boolean diastolic = true;
        boolean temperature = true;
        boolean pulse = true;
        boolean weight = true;

        if (cursor != null) {

            cursor.moveToLast();
        }

        while (!cursor.isBeforeFirst()) {

            if (cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_SYSTOLIC)) != 0 && systolic) {

                measurement.setSystolic(cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_SYSTOLIC)));
                systolic = false;
            }

            if (cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_DIASTOLIC)) != 0 && diastolic) {

                measurement.setDiastolic(cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_DIASTOLIC)));
                diastolic = false;
            }

            if (cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_TEMPERATURE)) != 0 && temperature) {

                measurement.setTemperature(cursor.getFloat(cursor.getColumnIndex(MEASUREMENT_KEY_TEMPERATURE)));
                temperature = false;
            }

            if (cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_PULSE)) != 0 && pulse) {

                measurement.setPulse(cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_PULSE)));
                pulse = false;
            }

            if (cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_WEIGHT)) != 0 && weight) {

                measurement.setWeight(cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_WEIGHT)));
                weight = false;
            }

            cursor.moveToPrevious();
        }

        return measurement;
    }

    /**
     *
     * @param dateFrom
     * @param dateTo
     * @return
     */
    @Override
    public Cursor betweenDate(String dateFrom, String dateTo) {

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_MEASUREMENT + DATABASE_COMMAND_SELECT_WHERE
                + MEASUREMENT_KEY_MEASURED_ON + DATABASE_COMMAND_BETWEEN + DATABASE_COMMAND_SINGLE_QUOTE
                + dateFrom + DATABASE_COMMAND_SINGLE_QUOTE + DATABASE_COMMAND_AND + DATABASE_COMMAND_SINGLE_QUOTE
                + dateTo + DATABASE_COMMAND_SINGLE_QUOTE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }
}