package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import sg.edu.nus.iss.se.ft05.medipal.constants.DbConstants;
import sg.edu.nus.iss.se.ft05.medipal.model.PersonalBio;

/**
 * @author Moushumi Seal
 */

public class PersonalBioDAOImpl extends DBHelper implements PersonalBioDAO{

    private static final String LOG = "PersonalBioDAOImpl";

    public PersonalBioDAOImpl(Context context) {
        super(context);
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DbConstants.TABLE_PERSONAL_BIO, DbConstants.PERSONAL_BIO_KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    @Override
    public Cursor findAll() {
        String selectQuery = "SELECT  * FROM " + DbConstants.TABLE_PERSONAL_BIO;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    @Override
    public PersonalBio findById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder selectQuery = new StringBuilder()
                .append("SELECT  * FROM ")
                .append(DbConstants.TABLE_PERSONAL_BIO)
                .append(" WHERE ")
                .append(DbConstants.PERSONAL_BIO_KEY_ID)
                .append(" = ")
                .append(id);

        Log.e(LOG, selectQuery.toString());
        Cursor c = db.rawQuery(selectQuery.toString(), null);
        if (c != null) {
            c.moveToFirst();
        }

        PersonalBio personalBio = new PersonalBio();
        personalBio.setId(c.getInt(c.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_ID)));
        personalBio.setName(c.getString(c.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_NAME)));
        personalBio.setDob(c.getString(c.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_DOB)));
        personalBio.setIdNo(c.getString(c.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_IDNO)));
        personalBio.setAddress(c.getString(c.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_ADDRESS)));
        personalBio.setPostalCode(c.getString(c.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_POSTAL_CODE)));
        personalBio.setHeight(c.getString(c.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_HEIGHT)));
        personalBio.setBloodType(c.getString(c.getColumnIndex(DbConstants.PERSONAL_BIO_KEY_BLOOD_TYPE)));

        return personalBio;
    }

    @Override
    public long insert(PersonalBio personalBio) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.PERSONAL_BIO_KEY_NAME, personalBio.getName());
        values.put(DbConstants.PERSONAL_BIO_KEY_DOB, personalBio.getDob());
        values.put(DbConstants.PERSONAL_BIO_KEY_IDNO, personalBio.getIdNo());
        values.put(DbConstants.PERSONAL_BIO_KEY_ADDRESS, personalBio.getAddress());
        values.put(DbConstants.PERSONAL_BIO_KEY_POSTAL_CODE, personalBio.getPostalCode());
        values.put(DbConstants.PERSONAL_BIO_KEY_HEIGHT, personalBio.getHeight());
        values.put(DbConstants.PERSONAL_BIO_KEY_BLOOD_TYPE, personalBio.getBloodType());

        // insert row
        return db.insert(DbConstants.TABLE_PERSONAL_BIO, null, values);

    }

    @Override
    public int update(PersonalBio personalBio) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.PERSONAL_BIO_KEY_NAME, personalBio.getName());
        values.put(DbConstants.PERSONAL_BIO_KEY_DOB, personalBio.getDob());
        values.put(DbConstants.PERSONAL_BIO_KEY_IDNO, personalBio.getIdNo());
        values.put(DbConstants.PERSONAL_BIO_KEY_ADDRESS, personalBio.getAddress());
        values.put(DbConstants.PERSONAL_BIO_KEY_POSTAL_CODE, personalBio.getPostalCode());
        values.put(DbConstants.PERSONAL_BIO_KEY_HEIGHT, personalBio.getHeight());
        values.put(DbConstants.PERSONAL_BIO_KEY_BLOOD_TYPE, personalBio.getBloodType());
        // updating row
        return db.update(DbConstants.TABLE_PERSONAL_BIO, values, DbConstants.PERSONAL_BIO_KEY_ID + " = ?",
                new String[] { String.valueOf(personalBio.getId()) });
    }
}
