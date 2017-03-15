package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import sg.edu.nus.iss.se.ft05.medipal.Contacts;

/**
 * Created by Ashish Katre
 */

public class ICEContactsDAOImpl extends DBHelper implements ICEContactsDAO {


    private static final String LOG = "ICEContactsDAOImpl";

    protected static final String DATABASE_COMMAND_SYMBOL = " = ?";
    protected static final String DATABASE_COMMAND_SYMBOL_EQUAL = " = ";
    protected static final String DATABASE_COMMAND_SELECT_ALL = "SELECT  * FROM ";
    protected static final String DATABASE_COMMAND_SELECT_WHERE = " WHERE ";
    protected static final String DATABASE_COMMAND_SELECT_MAXP = "MAXP";
    protected static final String DATABASE_COMMAND_ORDER_BY = " ORDER BY ";
    protected static final String DATABASE_COMMAND_SELECT_MAX_BEFORE = " SELECT  MAX(";
    protected static final String DATABASE_COMMAND_SELECT_MAX_BETWEEN = ") AS ";
    protected static final String DATABASE_COMMAND_SELECT_MAX_AFTER = " FROM ";
    protected static final String DATABASE_COMMAND_ASC = " ASC";

    public ICEContactsDAOImpl(Context context) {

        super(context);
    }

    @Override
    public int delete(long id) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        int result = sqLiteDatabase.delete(TABLE_ICE_CONTACTS, ICE_CONTACTS_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(id)});

        // TODO Priority

        return result;
    }

    @Override
    public Cursor findAll() {

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_ICE_CONTACTS + DATABASE_COMMAND_ORDER_BY + ICE_CONTACTS_KEY_PRIORITY + DATABASE_COMMAND_ASC;

        Log.e(LOG, selectQuery);

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (null != cursor) {

            cursor.moveToFirst();
        }

        return cursor;
    }

    @Override
    public Contacts findById(long id) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_ICE_CONTACTS + DATABASE_COMMAND_SELECT_WHERE
                + ICE_CONTACTS_KEY_ID + DATABASE_COMMAND_SYMBOL_EQUAL + id;

        Log.e(LOG, selectQuery);

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (null != cursor) {

            cursor.moveToFirst();
        }

        Contacts contacts = new Contacts();
        contacts.setId(cursor.getInt(cursor.getColumnIndex(ICE_CONTACTS_KEY_ID)));
        contacts.setName((cursor.getString(cursor.getColumnIndex(ICE_CONTACTS_KEY_NAME))));
        contacts.setDescription(cursor.getString(cursor.getColumnIndex(ICE_CONTACTS_KEY_DESC)));
        contacts.setPhone(cursor.getLong(cursor.getColumnIndex(ICE_CONTACTS_KEY_PHONE)));
        contacts.setType(cursor.getString(cursor.getColumnIndex(ICE_CONTACTS_KEY_TYPE)));
        contacts.setPriority(cursor.getInt(cursor.getColumnIndex(ICE_CONTACTS_KEY_PRIORITY)));

        return contacts;
    }

    @Override
    public int findMaxPriority() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String selectQuery = DATABASE_COMMAND_SELECT_MAX_BEFORE + ICE_CONTACTS_KEY_PRIORITY + DATABASE_COMMAND_SELECT_MAX_BETWEEN + DATABASE_COMMAND_SELECT_MAXP + DATABASE_COMMAND_SELECT_MAX_AFTER + TABLE_ICE_CONTACTS;

        Log.e(LOG, selectQuery);

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (null != cursor) {

            cursor.moveToFirst();
        }

        int priority = cursor.getInt(cursor.getColumnIndex(DATABASE_COMMAND_SELECT_MAXP));

        return priority;
    }

    /*
     * Creating a Contacts
     */
    @Override
    public long insert(Contacts contacts) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ICE_CONTACTS_KEY_NAME, contacts.getName());
        values.put(ICE_CONTACTS_KEY_DESC, contacts.getDescription());
        values.put(ICE_CONTACTS_KEY_PHONE, contacts.getPhone());
        values.put(ICE_CONTACTS_KEY_TYPE, contacts.getType());
        values.put(ICE_CONTACTS_KEY_PRIORITY, contacts.getPriority());

        // insert row
        long result = sqLiteDatabase.insert(TABLE_ICE_CONTACTS, null, values);

        return result;
    }

    @Override
    public int update(Contacts contacts) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ICE_CONTACTS_KEY_NAME, contacts.getName());
        values.put(ICE_CONTACTS_KEY_DESC, contacts.getDescription());
        values.put(ICE_CONTACTS_KEY_PHONE, contacts.getPhone());
        values.put(ICE_CONTACTS_KEY_TYPE, contacts.getType());
        values.put(ICE_CONTACTS_KEY_PRIORITY, contacts.getPriority());

        // updating row
        int result = db.update(TABLE_ICE_CONTACTS, values, ICE_CONTACTS_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(contacts.getId())});

        return result;
    }

    @Override
    public int updatePriority(Contacts contacts) {

        // TODO

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ICE_CONTACTS_KEY_PRIORITY, contacts.getPriority());

        // updating row
        int result = db.update(TABLE_ICE_CONTACTS, values, ICE_CONTACTS_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(contacts.getId())});

        return result;
    }
}
