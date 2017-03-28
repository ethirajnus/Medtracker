package com.ethigeek.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static com.ethigeek.medipal.daoutils.DbConstants.*;

import com.ethigeek.medipal.daoutils.DBHelper;
import com.ethigeek.medipal.domain.Reminder;



/**
 * Implementation class for reminder database operation
 * @author Ethiraj Srinivasan
 */
public class ReminderDAOImpl extends DBHelper implements ReminderDAO {

    public ReminderDAOImpl(Context context) {

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
        return db.delete(TABLE_REMINDER, REMINDER_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(id)});
    }

    /**
     * select operation with where clause using ID
     * @param id
     * @return
     */
    @Override
    public Cursor findAll() {

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_REMINDER;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    @Override
    public Reminder findById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_REMINDER + DATABASE_COMMAND_SELECT_WHERE
                + REMINDER_KEY_ID + DATABASE_COMMAND_SYMBOL_EQUAL + id;


        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            c.moveToFirst();
        }

        Reminder reminder = new Reminder();
        reminder.setId(c.getInt(c.getColumnIndex(REMINDER_KEY_ID)));
        reminder.setFrequency((c.getInt(c.getColumnIndex(REMINDER_KEY_FREQUENCY))));
        reminder.setStartTime(c.getString(c.getColumnIndex(REMINDER_KEY_STARTTIME)));
        reminder.setInterval(c.getInt(c.getColumnIndex(REMINDER_KEY_INTERVAL)));
        db.close();
        return reminder;
    }

    /**
     * Insert operation
     * @param reminder
     * @return
     */
    @Override
    public long insert(Reminder reminder) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REMINDER_KEY_FREQUENCY, reminder.getFrequency());
        values.put(REMINDER_KEY_STARTTIME, reminder.getStartTime());
        values.put(REMINDER_KEY_INTERVAL, reminder.getInterval());

        // insert row
        return db.insert(TABLE_REMINDER, null, values);
    }

    /**
     * Update operation
     * @param reminder
     * @return
     */
    @Override
    public int update(Reminder reminder) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(REMINDER_KEY_FREQUENCY, reminder.getFrequency());
        values.put(REMINDER_KEY_STARTTIME, reminder.getStartTime());
        values.put(REMINDER_KEY_INTERVAL, reminder.getInterval());
        // updating row
        return db.update(TABLE_REMINDER, values, REMINDER_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(reminder.getId())});
    }
}
