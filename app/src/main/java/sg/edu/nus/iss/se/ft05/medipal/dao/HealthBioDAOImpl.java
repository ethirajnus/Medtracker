package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import sg.edu.nus.iss.se.ft05.medipal.constants.DbConstants;
import sg.edu.nus.iss.se.ft05.medipal.model.HealthBio;

/**
 * @author Moushumi Seal
 */

public class HealthBioDAOImpl extends DBHelper implements HealthBioDAO {

    private static final String LOG = "HealthBioDAOImpl";

    public HealthBioDAOImpl(Context context) {
        super(context);
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DbConstants.TABLE_HEALTH_BIO, DbConstants.HEALTH_BIO_KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    @Override
    public Cursor findAll() {
        String selectQuery = "SELECT  * FROM " + DbConstants.TABLE_HEALTH_BIO;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    @Override
    public HealthBio findById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder selectQuery = new StringBuilder()
                .append("SELECT  * FROM ")
                .append(DbConstants.TABLE_HEALTH_BIO)
                .append(" WHERE ")
                .append(DbConstants.HEALTH_BIO_KEY_ID)
                .append(" = ")
                .append(id);

        Log.e(LOG, selectQuery.toString());
        Cursor c = db.rawQuery(selectQuery.toString(), null);
        if (c != null) {
            c.moveToFirst();
        }

        HealthBio healthBio = new HealthBio();
        healthBio.setId(c.getInt(c.getColumnIndex(DbConstants.HEALTH_BIO_KEY_ID)));
        healthBio.setCondition(c.getString(c.getColumnIndex(DbConstants.HEALTH_BIO_KEY_CONDITION)));
        healthBio.setConditionType(c.getString(c.getColumnIndex(DbConstants.HEALTH_BIO_KEY_CONDITION_TYPE)));
        healthBio.setStartDate(c.getString(c.getColumnIndex(DbConstants.HEALTH_BIO_KEY_START_DATE)));

        return healthBio;
    }

    /**
     * Creating a new healthbio record
     * @param healthBio
     * @return
     */
    @Override
    public long insert(HealthBio healthBio) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.HEALTH_BIO_KEY_CONDITION, healthBio.getCondition());
        values.put(DbConstants.HEALTH_BIO_KEY_CONDITION_TYPE, healthBio.getConditionType());
        values.put(DbConstants.HEALTH_BIO_KEY_START_DATE, healthBio.getStartDate());

        // insert row
        return db.insert(DbConstants.TABLE_HEALTH_BIO, null, values);
    }

    /**
     * Updating a healthbio record
     * @param healthBio
     * @return
     */
    @Override
    public int update(HealthBio healthBio) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbConstants.HEALTH_BIO_KEY_CONDITION, healthBio.getCondition());
        values.put(DbConstants.HEALTH_BIO_KEY_CONDITION_TYPE, healthBio.getConditionType());
        values.put(DbConstants.HEALTH_BIO_KEY_START_DATE, healthBio.getStartDate());
        // updating row
        return db.update(DbConstants.TABLE_HEALTH_BIO, values, DbConstants.HEALTH_BIO_KEY_ID + " = ?",
                new String[] { String.valueOf(healthBio.getId()) });
    }

    /**
     * Fetching id and condition for all the healthbio records
     * @return
     */
    @Override
    public Cursor fetchAllHealthBioWithId() {
        String selectQuery = "SELECT  id,condition FROM " + DbConstants.TABLE_HEALTH_BIO;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }
}
