package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import static sg.edu.nus.iss.se.ft05.medipal.constants.DbConstants.*;

import sg.edu.nus.iss.se.ft05.medipal.domain.HealthBio;



/**
 * Implementation class for health bio database operation
 * @author Moushumi Seal
 */
public class HealthBioDAOImpl extends DBHelper implements HealthBioDAO {

    private static final String LOG = "HealthBioDAOImpl";

    public HealthBioDAOImpl(Context context) {
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
        return db.delete(TABLE_HEALTH_BIO, HEALTH_BIO_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(id)});
    }

    /**
     * select all operation
     * @return
     */
    @Override
    public Cursor findAll() {

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_HEALTH_BIO;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     * select operation with where clause using ID
     * @param id
     * @return
     */
    @Override
    public HealthBio findById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        StringBuilder selectQuery = new StringBuilder()
                .append(DATABASE_COMMAND_SELECT_ALL)
                .append(TABLE_HEALTH_BIO)
                .append(DATABASE_COMMAND_SELECT_WHERE)
                .append(HEALTH_BIO_KEY_ID)
                .append(DATABASE_COMMAND_SYMBOL_EQUAL)
                .append(id);

        Cursor c = db.rawQuery(selectQuery.toString(), null);
        if (c != null) {
            c.moveToFirst();
        }

        HealthBio healthBio = new HealthBio();

        healthBio.setId(c.getInt(c.getColumnIndex(HEALTH_BIO_KEY_ID)));
        healthBio.setCondition(c.getString(c.getColumnIndex(HEALTH_BIO_KEY_CONDITION)));
        healthBio.setConditionType(c.getString(c.getColumnIndex(HEALTH_BIO_KEY_CONDITION_TYPE)));
        healthBio.setStartDate(c.getString(c.getColumnIndex(HEALTH_BIO_KEY_START_DATE)));

        db.close();

        return healthBio;
    }

    /**
     * Creating a new healthbio record
     *
     * @param healthBio
     * @return
     */
    @Override
    public long insert(HealthBio healthBio) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HEALTH_BIO_KEY_CONDITION, healthBio.getCondition());
        values.put(HEALTH_BIO_KEY_CONDITION_TYPE, healthBio.getConditionType());
        values.put(HEALTH_BIO_KEY_START_DATE, healthBio.getStartDate());

        // insert row
        return db.insert(TABLE_HEALTH_BIO, null, values);
    }

    /**
     * Updating a healthbio record
     *
     * @param healthBio
     * @return
     */
    @Override
    public int update(HealthBio healthBio) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HEALTH_BIO_KEY_CONDITION, healthBio.getCondition());
        values.put(HEALTH_BIO_KEY_CONDITION_TYPE, healthBio.getConditionType());
        values.put(HEALTH_BIO_KEY_START_DATE, healthBio.getStartDate());
        // updating row
        return db.update(TABLE_HEALTH_BIO, values, HEALTH_BIO_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(healthBio.getId())});
    }

    /**
     * Fetching id and condition for all the healthbio records
     *
     * @return
     */
    @Override
    public Cursor fetchAllHealthBioWithId() {

        String selectQuery = "SELECT  id,condition FROM " + TABLE_HEALTH_BIO;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }
}
