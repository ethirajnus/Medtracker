package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import sg.edu.nus.iss.se.ft05.medipal.model.Reminder;

/**
 * Created by ethi on 13/03/17.
 */

public class ReminderDAOImpl extends DBHelper implements ReminderDAO {
    public ReminderDAOImpl(Context context) {
        super(context);
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_REMINDER, REMINDER_KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    @Override
    public Reminder findById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_REMINDER + " WHERE "
                + REMINDER_KEY_ID + " = " + id;


        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            c.moveToFirst();
        }
        Log.v("reminder ciunt", String.valueOf(c.getCount()));
        Reminder reminder = new Reminder();
        reminder.setId(c.getInt(c.getColumnIndex(REMINDER_KEY_ID)));
        reminder.setFrequency((c.getInt(c.getColumnIndex(REMINDER_KEY_FREQUENCY))));
        reminder.setStartTime(c.getString(c.getColumnIndex(REMINDER_KEY_STARTTIME)));
        reminder.setInterval(c.getInt(c.getColumnIndex(REMINDER_KEY_INTERVAL)));
        Log.v("reminder",reminder.getStartTime());
        return reminder;
    }

    @Override
    public long insert(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v("starttme", reminder.getStartTime());
        ContentValues values = new ContentValues();
        values.put(REMINDER_KEY_FREQUENCY, reminder.getFrequency());
        values.put(REMINDER_KEY_STARTTIME, reminder.getStartTime());
        values.put(REMINDER_KEY_INTERVAL, reminder.getInterval());

        // insert row
        return db.insert(TABLE_REMINDER, null, values);
    }

    @Override
    public int update(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Log.v("reminder",String.valueOf(reminder.getFrequency()));
        values.put(REMINDER_KEY_FREQUENCY, reminder.getFrequency());
        values.put(REMINDER_KEY_STARTTIME, reminder.getStartTime());
        values.put(REMINDER_KEY_INTERVAL, reminder.getInterval());
        // updating row
        return db.update(TABLE_REMINDER, values, REMINDER_KEY_ID + " = ?",
                new String[] { String.valueOf(reminder.getId()) });
    }
}
