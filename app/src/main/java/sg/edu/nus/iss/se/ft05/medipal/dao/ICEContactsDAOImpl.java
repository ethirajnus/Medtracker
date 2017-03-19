package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import sg.edu.nus.iss.se.ft05.medipal.model.ICEContact;

/**
 * Implementation class for emergency contacts database operations
 * Created by Ashish Katre
 */

public class ICEContactsDAOImpl extends DBHelper implements ICEContactsDAO {

    // Logger name
    private static final String LOG = "ICEContactsDAOImpl";

    // Constants
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

    /**
     * Constructor for ICEContactsDAOImpl
     *
     * @param context
     */
    public ICEContactsDAOImpl(Context context) {

        super(context);
    }

    /**
     * delete operation
     *
     * @param id int
     * @return int
     */
    @Override
    public int delete(int id) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        int result = sqLiteDatabase.delete(TABLE_ICE_CONTACTS, ICE_CONTACTS_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(id)});

        // TODO Priority

        return result;
    }

    /**
     * select all operation
     *
     * @return Cursor
     */
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

    /**
     * select operation with where clause using ID
     *
     * @param id int
     * @return ICEContact
     */
    @Override
    public ICEContact findById(int id) {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_ICE_CONTACTS + DATABASE_COMMAND_SELECT_WHERE
                + ICE_CONTACTS_KEY_ID + DATABASE_COMMAND_SYMBOL_EQUAL + id;

        Log.e(LOG, selectQuery);

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (null != cursor) {

            cursor.moveToFirst();
        }

        ICEContact contact = new ICEContact();
        contact.setId(cursor.getInt(cursor.getColumnIndex(ICE_CONTACTS_KEY_ID)));
        contact.setName((cursor.getString(cursor.getColumnIndex(ICE_CONTACTS_KEY_NAME))));
        contact.setDescription(cursor.getString(cursor.getColumnIndex(ICE_CONTACTS_KEY_DESC)));
        contact.setPhone(cursor.getLong(cursor.getColumnIndex(ICE_CONTACTS_KEY_PHONE)));
        contact.setType(cursor.getString(cursor.getColumnIndex(ICE_CONTACTS_KEY_TYPE)));
        contact.setPriority(cursor.getInt(cursor.getColumnIndex(ICE_CONTACTS_KEY_PRIORITY)));

        return contact;
    }


    /**
     * insert operation
     *
     * @param contact ICEContactsManager
     * @return long
     */
    @Override
    public long insert(ICEContact contact) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ICE_CONTACTS_KEY_NAME, contact.getName());
        values.put(ICE_CONTACTS_KEY_DESC, contact.getDescription());
        values.put(ICE_CONTACTS_KEY_PHONE, contact.getPhone());
        values.put(ICE_CONTACTS_KEY_TYPE, contact.getType());

        int priority = 0;

        try {

            priority = findMaxPriority();
            priority++;

        } catch (Exception e) {

            // TODO
            priority = 1;
        }

        values.put(ICE_CONTACTS_KEY_PRIORITY, (priority));

        // insert row
        long result = sqLiteDatabase.insert(TABLE_ICE_CONTACTS, null, values);

        Log.d(LOG, " Result of Insertion : " + result);

        return result;
    }

    /**
     * update operation
     *
     * @param contact ICEContactsManager
     * @return int
     */
    @Override
    public int update(ICEContact contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ICE_CONTACTS_KEY_NAME, contact.getName());
        values.put(ICE_CONTACTS_KEY_DESC, contact.getDescription());
        values.put(ICE_CONTACTS_KEY_PHONE, contact.getPhone());
        values.put(ICE_CONTACTS_KEY_TYPE, contact.getType());
        values.put(ICE_CONTACTS_KEY_PRIORITY, contact.getPriority());

        // updating row
        int result = db.update(TABLE_ICE_CONTACTS, values, ICE_CONTACTS_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(contact.getId())});

        Log.d(LOG, " Result of Update : " + result);

        return result;
    }


    /**
     * update operation for priority update
     *
     * @param currentPriority int
     * @param newPriority     int
     * @return int
     */
    @Override
    public int updatePriority(int currentPriority, int newPriority) {

        // TODO

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ICE_CONTACTS_KEY_PRIORITY, newPriority);

        // updating row
        int result = db.update(TABLE_ICE_CONTACTS, values, ICE_CONTACTS_KEY_PRIORITY + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(currentPriority)});

        Log.d(LOG, " Result of Update priority : " + result);

        return result;
    }

    /**
     * select operation with groupby priority to get maximum priority value
     *
     * @return int
     */
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
}
