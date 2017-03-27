package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.domain.Appointment;

import static sg.edu.nus.iss.se.ft05.medipal.constants.DbConstants.*;



/**
 * Implementation class for appointment database operations
 * @author Dhruv Mandan Gopal
 */
public class AppointmentDAOImpl extends DBHelper implements AppointmentDAO {

    private static final String LOG = "AppointmentDAOImpl";
    private static final String SELECT = "SELECT * FROM ";

    public AppointmentDAOImpl(Context context) {

        super(context);
    }

    /**
     * DElete operation
     * @param id
     * @return
     */
    @Override
    public int delete(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(TABLE_APPOINTMENT, APPOINTMENT_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(id)});

        db.close();

        return result;
    }

    /**
     * Select all operation
     * @return
     */
    @Override
    public Cursor findAll() {

        String selectQuery = SELECT + TABLE_APPOINTMENT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

       // db.close();

        return cursor;
    }

    /**
     * select operation with where clause using ID
     * @param id
     * @return
     */
    @Override
    public Appointment findById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = SELECT + TABLE_APPOINTMENT + DATABASE_COMMAND_SELECT_WHERE
                + APPOINTMENT_KEY_ID + DATABASE_COMMAND_SYMBOL_EQUAL + id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            c.moveToFirst();
        }

        Appointment appointment = new Appointment();

        appointment.setId(c.getInt(c.getColumnIndex(APPOINTMENT_KEY_ID)));
        appointment.setDate((c.getString(c.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_DATE))));
        appointment.setTime(c.getString(c.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_TIME)));
        appointment.setClinic(c.getString(c.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_CLINIC)));
        appointment.setDescription(c.getString(c.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_DESCRIPTION)));



        db.close();

        return appointment;
    }

    /**
     * Insert operation
     * @param appointment
     * @return
     */
    @Override
    public long insert(Appointment appointment) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(APPOINTMENT_KEY_APPOINTMENT_DATE, appointment.getDate());
        values.put(APPOINTMENT_KEY_APPOINTMENT_TIME, appointment.getTime());
        values.put(APPOINTMENT_KEY_APPOINTMENT_CLINIC, appointment.getClinic());
        values.put(APPOINTMENT_KEY_APPOINTMENT_DESCRIPTION, appointment.getDescription());


        // insert row
        long result = db.insert(TABLE_APPOINTMENT, null, values);

        db.close();

        return result;
    }

    /**
     * Update operation
     * @param appointment
     * @return
     */
    @Override
    public int update(Appointment appointment) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(APPOINTMENT_KEY_APPOINTMENT_DATE, appointment.getDate());
        values.put(APPOINTMENT_KEY_APPOINTMENT_TIME, appointment.getTime());
        values.put(APPOINTMENT_KEY_APPOINTMENT_CLINIC, appointment.getClinic());
        values.put(APPOINTMENT_KEY_APPOINTMENT_DESCRIPTION, appointment.getDescription());


        // updating row
        int result = db.update(TABLE_APPOINTMENT, values, APPOINTMENT_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(appointment.getId())});

        db.close();

        return result;
    }

    /**
     * List
     * @param date
     * @return
     */
    @Override
    public List<Appointment> findByDate(String date) {

        List<Appointment> appointmentList = new ArrayList();

        String selectQuery = SELECT + TABLE_APPOINTMENT + " where " + APPOINTMENT_KEY_APPOINTMENT_DATE + " = '" + date + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {

            cursor.moveToFirst();
        }

        while (!cursor.isAfterLast()) {

            Appointment appointment = new Appointment();

            appointment.setId(cursor.getInt(cursor.getColumnIndex(APPOINTMENT_KEY_ID)));
            appointment.setDate((cursor.getString(cursor.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_DATE))));
            appointment.setTime(cursor.getString(cursor.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_TIME)));
            appointment.setDescription(cursor.getString(cursor.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_DESCRIPTION)));

            appointment.setClinic(cursor.getString(cursor.getColumnIndex(APPOINTMENT_KEY_APPOINTMENT_CLINIC)));


            appointmentList.add(appointment);

            cursor.moveToNext();
        }


        db.close();

        return appointmentList;
    }

    /**
     * Filter date
     * @param date
     * @return
     */
    @Override
    public Cursor filterDate(String date) {

        String selectQuery = SELECT + TABLE_APPOINTMENT + " where " + APPOINTMENT_KEY_APPOINTMENT_DATE + " = '" + date + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null) {

            cursor.moveToFirst();
        }

        return cursor;
    }

    @Override
    public Cursor fetchByAppointmentDateAndTime(String date, String time){

        String selectQuery = SELECT + TABLE_APPOINTMENT + " where " + APPOINTMENT_KEY_APPOINTMENT_DATE + " = '" + date
                + "' "+ " AND "+APPOINTMENT_KEY_APPOINTMENT_TIME + " = '" +time + "'";
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if(cursor != null){

            cursor.moveToFirst();
        }

        return cursor;
    }
}
