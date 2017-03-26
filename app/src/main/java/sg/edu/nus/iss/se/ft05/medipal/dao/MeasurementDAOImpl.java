package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import sg.edu.nus.iss.se.ft05.medipal.domain.Measurement;
import sg.edu.nus.iss.se.ft05.medipal.managers.MeasurementManager;

import static sg.edu.nus.iss.se.ft05.medipal.constants.DbConstants.*;

/**
 * Created by ashu on 15-03-2017.
 */

public class MeasurementDAOImpl extends DBHelper implements MeasurementDAO {

    private static final String LOG = "MeasurementDAOImpl";

    public MeasurementDAOImpl(Context context) {
        super(context);
    }

    @Override
    public int delete(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_MEASUREMENT, MEASUREMENT_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(id)});
    }

    @Override
    public Cursor findAll() {

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_MEASUREMENT ;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);


    }

    @Override
    public Measurement findById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_MEASUREMENT + DATABASE_COMMAND_SELECT_WHERE
                + MEASUREMENT_KEY_ID + DATABASE_COMMAND_SYMBOL_EQUAL + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            c.moveToFirst();
        }

        Measurement measurement = new Measurement();

        measurement.setId(c.getInt(c.getColumnIndex(MEASUREMENT_KEY_ID)));
        measurement.setSystolic(c.getInt(c.getColumnIndex(MEASUREMENT_KEY_SYSTOLIC)));
        measurement.setDiastolic(c.getInt(c.getColumnIndex(MEASUREMENT_KEY_DIASTOLIC)));
        measurement.setPulse(c.getInt(c.getColumnIndex(MEASUREMENT_KEY_PULSE)));
        measurement.setTemperature(c.getInt(c.getColumnIndex(MEASUREMENT_KEY_TEMPERATURE)));
        measurement.setWeight(c.getInt(c.getColumnIndex(MEASUREMENT_KEY_WEIGHT)));
        measurement.setMeasuredOn(c.getString(c.getColumnIndex(MEASUREMENT_KEY_MEASURED_ON)));
        db.close();
        return measurement;
    }

    /*
     * Creating a category
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

    @Override
    public Cursor fetchAllMeasurementsWithId() {

        String selectQuery = "SELECT  id,measuredOn FROM " + TABLE_MEASUREMENT;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

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

    @Override
    public Measurement findLatest()
    {
        Measurement measurement=new Measurement();
        Cursor cursor;
        SQLiteDatabase db=this.getReadableDatabase();

        String selectQuery=DATABASE_COMMAND_SELECT_ALL + TABLE_MEASUREMENT+ " order by measuredON";
        cursor=db.rawQuery(selectQuery,null);

        if(cursor!=null)
        {
            cursor.moveToFirst();
        }
        while(cursor.moveToNext())
        {
            if(cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_SYSTOLIC))!=0)
            {
                measurement.setSystolic(cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_SYSTOLIC)));
            }
            if(cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_DIASTOLIC))!=0)
            {
                measurement.setDiastolic(cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_DIASTOLIC)));
            }
            if(cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_TEMPERATURE))!=0)
            {
                measurement.setTemperature(cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_TEMPERATURE)));
            }
            if(cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_PULSE))!=0)
            {
                measurement.setPulse(cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_PULSE)));
            }
            if(cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_WEIGHT))!=0)
            {
                measurement.setWeight(cursor.getInt(cursor.getColumnIndex(MEASUREMENT_KEY_WEIGHT)));
            }
        }
        db.close();
        return measurement;
    }
}