package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.model.Appointment;

import static sg.edu.nus.iss.se.ft05.medipal.constants.DbConstants.*;

/**
 * Created by Dhruv on 18/3/2017.
 */

public class AppointmentDAOImpl extends DBHelper implements AppointmentDAO {

    private static final String LOG = "AppointmentDAOImpl";
    private static final String SELECT = "SELECT * FROM ";

    public AppointmentDAOImpl(Context context) {

        super(context);
    }

    @Override
    public int delete(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(TABLE_APPOINTMENT, APPOINTMENT_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(id)});

        return result;
    }

    @Override
    public Cursor findAll() {

        String selectQuery = SELECT + TABLE_APPOINTMENT;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

    @Override
    public Appointment findById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = SELECT + TABLE_APPOINTMENT + DATABASE_COMMAND_SELECT_WHERE
                + APPOINTMENT_KEY_ID + DATABASE_COMMAND_SYMBOL_EQUAL + id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            c.moveToFirst();
        }

        Appointment appointment = new Appointment();

        appointment.setId(c.getInt(c.getColumnIndex(APPOINTMENT_KEY_ID)));
        appointment.setDate((c.getString(c.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_DATE))));
        appointment.setTime(c.getString(c.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_TIME)));
        appointment.setClinic(c.getString(c.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_CLINIC)));
        appointment.setTest(c.getString(c.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_TEST)));
        appointment.setPreTest(c.getString(c.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_PRE_TEST)));

        return appointment;
    }

    @Override
    public long insert(Appointment appointment) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(APPOINTMENT_KEY_APPOINTMENT_DATE, appointment.getDate());
        values.put(APPOINTMENT_KEY_APPOINTMENT_TIME, appointment.getTime());
        values.put(APPOINTMENT_KEY_APPOINTMENT_CLINIC, appointment.getClinic());
        values.put(APPOINTMENT_KEY_APPOINTMENT_TEST, appointment.getTest());
        values.put(APPOINTMENT_KEY_APPOINTMENT_PRE_TEST, appointment.getPreTest());

        // insert row
        long result = db.insert(TABLE_APPOINTMENT, null, values);

        return result;
    }

    @Override
    public int update(Appointment appointment) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(APPOINTMENT_KEY_APPOINTMENT_DATE, appointment.getDate());
        values.put(APPOINTMENT_KEY_APPOINTMENT_TIME, appointment.getTime());
        values.put(APPOINTMENT_KEY_APPOINTMENT_CLINIC, appointment.getClinic());
        values.put(APPOINTMENT_KEY_APPOINTMENT_TEST, appointment.getTest());
        values.put(APPOINTMENT_KEY_APPOINTMENT_PRE_TEST, appointment.getPreTest());

        // updating row
        int result = db.update(TABLE_APPOINTMENT, values, APPOINTMENT_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(appointment.getId())});

        return result;
    }

    @Override
    public List<Appointment> findByDate(String date) {

        List<Appointment> appointmentList = new ArrayList();

        String selectQuery = SELECT + TABLE_APPOINTMENT + " where " + APPOINTMENT_KEY_APPOINTMENT_DATE + " = '" + date + "'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.v("count", String.valueOf(cursor.getCount()));

        if (cursor != null) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            Appointment appointment = new Appointment();

            appointment.setId(cursor.getInt(cursor.getColumnIndex(APPOINTMENT_KEY_ID)));
            appointment.setDate((cursor.getString(cursor.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_DATE))));
            appointment.setTime(cursor.getString(cursor.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_TIME)));
            appointment.setTest(cursor.getString(cursor.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_TEST)));
            appointment.setClinic(cursor.getString(cursor.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_CLINIC)));

            appointmentList.add(appointment);

            cursor.moveToNext();
        }

        cursor.close();

        return appointmentList;
    }

    @Override
    public Cursor filterDate(String date) {

        String selectQuery = SELECT + TABLE_APPOINTMENT + " where " + APPOINTMENT_KEY_APPOINTMENT_DATE + " = '" + date + "'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.v("count", String.valueOf(cursor.getCount()));

        if (cursor != null) {

            cursor.moveToFirst();
        }

        return cursor;
    }
}
