package com.ethigeek.medtracker.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.ethigeek.medtracker.daoutils.DBHelper;
import com.ethigeek.medtracker.domain.Consumption;

import static com.ethigeek.medtracker.daoutils.DbConstants.*;



/**
 * Implementation class for consumption database operations
 *  @author Ethiraj Srinivasan
 */
public class ConsumptionDAOImpl extends DBHelper implements ConsumptionDAO {

    private static final String LOG = "ConsumptionDAOImpl";

    public ConsumptionDAOImpl(Context context) {
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
        return db.delete(TABLE_CONSUMPTION, CONSUMPTION_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(id)});
    }

    /**
     * Select all operation
     * @return
     */
    @Override
    public Cursor findAll() {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);


    }

    /**
     * select operation with where clause using ID
     * @param id
     * @return
     */
    @Override
    public Consumption findById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE
                + CONSUMPTION_KEY_ID + DATABASE_COMMAND_SYMBOL_EQUAL + id;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            c.moveToFirst();
        }

        Consumption consumption = new Consumption();
        consumption.setId(c.getInt(c.getColumnIndex(CONSUMPTION_KEY_ID)));
        consumption.setMedicineId(c.getInt(c.getColumnIndex(CONSUMPTION_KEY_MEDICINEID)));
        consumption.setQuantity(c.getInt(c.getColumnIndex(CONSUMPTION_KEY_QUANTITY)));
        consumption.setDate(c.getString(c.getColumnIndex(CONSUMPTION_KEY_DATE)));
        consumption.setTime(c.getString(c.getColumnIndex(CONSUMPTION_KEY_TIME)));

        db.close();
        return consumption;
    }

    /*
     * Creating a consumption
     */
    @Override
    public long insert(Consumption consumption) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONSUMPTION_KEY_MEDICINEID, consumption.getMedicineId());
        values.put(CONSUMPTION_KEY_QUANTITY, consumption.getQuantity());
        values.put(CONSUMPTION_KEY_DATE, consumption.getDate());
        values.put(CONSUMPTION_KEY_TIME, consumption.getTime());

        // insert row
        return db.insert(TABLE_CONSUMPTION, null, values);
    }

    /**
     * Update operation
     * @param consumption
     * @return
     */
    @Override
    public int update(Consumption consumption) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONSUMPTION_KEY_MEDICINEID, consumption.getMedicineId());
        values.put(CONSUMPTION_KEY_QUANTITY, consumption.getQuantity());
        values.put(CONSUMPTION_KEY_DATE, consumption.getDate());
        values.put(CONSUMPTION_KEY_TIME, consumption.getTime());
        // updating row
        return db.update(TABLE_CONSUMPTION, values, CONSUMPTION_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(consumption.getId())});
    }

    /**
     * list consumption
     * @param date
     * @return
     */
    @Override
    public List<Consumption> findByDate(String date) {

        List<Consumption> consumptionList = new ArrayList();

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_DATE + DATABASE_COMMAND_SYMBOL_EQUAL + DATABASE_COMMAND_SINGLE_QUOTE + date + DATABASE_COMMAND_SINGLE_QUOTE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {

            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(CONSUMPTION_KEY_TIME)));
            consumptionList.add(consumption);
            cursor.moveToNext();
        }
        db.close();
        return consumptionList;

    }

    /**
     *
     * @param date
     * @return
     */
    public Cursor filterDate(String date) {

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_DATE + DATABASE_COMMAND_SYMBOL_EQUAL + DATABASE_COMMAND_SINGLE_QUOTE + date + DATABASE_COMMAND_SINGLE_QUOTE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

    /**
     *
     * @param medicineId
     * @return
     */
    @Override
    public int totalQuantityConsumed(int medicineId) {
        String selectQuery = DATABASE_COMMAND_SELECT_SUM + DATABASE_COMMAND_OPEN_BRACKET + CONSUMPTION_KEY_QUANTITY + DATABASE_COMMAND_CLOSE_BRACKET + DATABASE_COMMAND_SELECT_FROM + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getInt(0);

    }

    /**
     *
     * @param medicineCategoryId
     * @return
     */
    @Override
    public Cursor fetchByCategory(int medicineCategoryId) {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_IN + DATABASE_COMMAND_OPEN_BRACKET + DATABASE_COMMAND_SELECT + DATABASE_COMMAND_ID + DATABASE_COMMAND_SELECT_FROM + TABLE_MEDICINE + DATABASE_COMMAND_SELECT_WHERE + MEDICINE_KEY_CATID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineCategoryId + DATABASE_COMMAND_CLOSE_BRACKET;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     *
     * @param medicineId
     * @return
     */
    @Override
    public Cursor fetchByMedicine(int medicineId) {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineId;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     *
     * @param medicineId
     * @param date
     * @return
     */
    @Override
    public Cursor fetchByMedicineAndDate(int medicineId, String date) {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineId + DATABASE_COMMAND_AND + CONSUMPTION_KEY_DATE + DATABASE_COMMAND_SYMBOL_EQUAL + DATABASE_COMMAND_SINGLE_QUOTE + date + DATABASE_COMMAND_SINGLE_QUOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     *
     * @param medicineId
     * @return
     */
    @Override
    public List<Consumption> findByMedicineID(int medicineId) {

        List<Consumption> consumptionList = new ArrayList();

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineId;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            Consumption consumption = new Consumption();
            consumption.setId(cursor.getInt(cursor.getColumnIndex(CONSUMPTION_KEY_ID)));
            consumption.setMedicineId(cursor.getInt(cursor.getColumnIndex(CONSUMPTION_KEY_MEDICINEID)));
            consumption.setQuantity(cursor.getInt(cursor.getColumnIndex(CONSUMPTION_KEY_QUANTITY)));
            consumption.setDate(cursor.getString(cursor.getColumnIndex(CONSUMPTION_KEY_DATE)));
            consumption.setTime(cursor.getString(cursor.getColumnIndex(CONSUMPTION_KEY_TIME)));
            consumptionList.add(consumption);
            cursor.moveToNext();
        }
        db.close();
        return consumptionList;

    }

    /**
     *
     * @param medicineId
     * @param year
     * @return
     */
    @Override
    public Cursor fetchByMedicineAndYear(Integer medicineId, String year) {

        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineId + DATABASE_COMMAND_AND + CONSUMPTION_KEY_DATE + " LIKE " + DATABASE_COMMAND_SINGLE_QUOTE + year + "-__-__" + DATABASE_COMMAND_SINGLE_QUOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     *
     * @param medicineId
     * @param year
     * @param month
     * @return
     */
    @Override
    public Cursor fetchByMedicineAndMonth(Integer medicineId, String year, String month) {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineId + DATABASE_COMMAND_AND + CONSUMPTION_KEY_DATE + " LIKE " + DATABASE_COMMAND_SINGLE_QUOTE + year + "-" + month + "-__" + DATABASE_COMMAND_SINGLE_QUOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     *
     * @param medicineId
     * @param date
     * @param time
     * @return
     */
    @Override
    public Cursor fetchByMedicineDateAndTime(int medicineId, String date, String time) {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineId + DATABASE_COMMAND_AND + CONSUMPTION_KEY_DATE + DATABASE_COMMAND_SYMBOL_EQUAL + DATABASE_COMMAND_SINGLE_QUOTE + date + DATABASE_COMMAND_SINGLE_QUOTE + DATABASE_COMMAND_AND + CONSUMPTION_KEY_TIME + DATABASE_COMMAND_SYMBOL_EQUAL + DATABASE_COMMAND_SINGLE_QUOTE + time + DATABASE_COMMAND_SINGLE_QUOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     *
     * @param medicineCategoryId
     * @param date
     * @return
     */
    @Override
    public Cursor fetchByCategoryAndDate(Integer medicineCategoryId, String date) {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_IN + DATABASE_COMMAND_OPEN_BRACKET + DATABASE_COMMAND_SELECT + DATABASE_COMMAND_ID + DATABASE_COMMAND_SELECT_FROM + TABLE_MEDICINE + DATABASE_COMMAND_SELECT_WHERE + MEDICINE_KEY_CATID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineCategoryId + DATABASE_COMMAND_CLOSE_BRACKET + DATABASE_COMMAND_AND + CONSUMPTION_KEY_DATE + DATABASE_COMMAND_SYMBOL_EQUAL + DATABASE_COMMAND_SINGLE_QUOTE + date + DATABASE_COMMAND_SINGLE_QUOTE;
        ;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     *
     * @param medicineCategoryId
     * @param year
     * @return
     */
    @Override
    public Cursor fetchByCategoryAndYear(Integer medicineCategoryId, String year) {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_IN + DATABASE_COMMAND_OPEN_BRACKET + DATABASE_COMMAND_SELECT + DATABASE_COMMAND_ID + DATABASE_COMMAND_SELECT_FROM + TABLE_MEDICINE + DATABASE_COMMAND_SELECT_WHERE + MEDICINE_KEY_CATID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineCategoryId + DATABASE_COMMAND_CLOSE_BRACKET + DATABASE_COMMAND_AND + CONSUMPTION_KEY_DATE + " LIKE " + DATABASE_COMMAND_SINGLE_QUOTE + year + "-__-__" + DATABASE_COMMAND_SINGLE_QUOTE;
        ;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     *
     * @param medicineCategoryId
     * @param year
     * @param month
     * @return
     */
    @Override
    public Cursor fetchByCategoryAndMonth(Integer medicineCategoryId, String year, String month) {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_IN + DATABASE_COMMAND_OPEN_BRACKET + DATABASE_COMMAND_SELECT + DATABASE_COMMAND_ID + DATABASE_COMMAND_SELECT_FROM + TABLE_MEDICINE + DATABASE_COMMAND_SELECT_WHERE + MEDICINE_KEY_CATID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineCategoryId + DATABASE_COMMAND_CLOSE_BRACKET + DATABASE_COMMAND_AND + CONSUMPTION_KEY_DATE + " LIKE " + DATABASE_COMMAND_SINGLE_QUOTE + year + "-" + month + "-__" + DATABASE_COMMAND_SINGLE_QUOTE;
        ;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     *
     * @param medicineId
     * @param date
     * @return
     */
    @Override
    public Cursor fetchByMedicineAndDateUnconsumed(Integer medicineId, String date) {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineId + DATABASE_COMMAND_AND + CONSUMPTION_KEY_DATE + DATABASE_COMMAND_SYMBOL_EQUAL + DATABASE_COMMAND_SINGLE_QUOTE + date + DATABASE_COMMAND_SINGLE_QUOTE + DATABASE_COMMAND_AND + CONSUMPTION_KEY_QUANTITY + DATABASE_COMMAND_SYMBOL_EQUAL + DATABASE_COMMAND_ZERO;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     *
     * @param medicineId
     * @param year
     * @return
     */
    @Override
    public Cursor fetchByMedicineAndYearUnconsumed(Integer medicineId, String year) {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineId + DATABASE_COMMAND_AND + CONSUMPTION_KEY_DATE + " LIKE " + DATABASE_COMMAND_SINGLE_QUOTE + year + "-__-__" + DATABASE_COMMAND_SINGLE_QUOTE + DATABASE_COMMAND_AND + CONSUMPTION_KEY_QUANTITY + DATABASE_COMMAND_SYMBOL_EQUAL + DATABASE_COMMAND_ZERO;
        ;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     *
     * @param medicineId
     * @param year
     * @param month
     * @return
     */
    @Override
    public Cursor fetchByMedicineAndMonthUnconsumed(Integer medicineId, String year, String month) {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineId + DATABASE_COMMAND_AND + CONSUMPTION_KEY_DATE + " LIKE " + DATABASE_COMMAND_SINGLE_QUOTE + year + "-" + month + "-__" + DATABASE_COMMAND_SINGLE_QUOTE + DATABASE_COMMAND_AND + CONSUMPTION_KEY_QUANTITY + DATABASE_COMMAND_SYMBOL_EQUAL + DATABASE_COMMAND_ZERO;
        ;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     *
     * @param medicineId
     * @return
     */
    @Override
    public int deleteAllForMedicine(int medicineId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_CONSUMPTION, CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(medicineId)});
    }

    /**
     *
     * @param dateFrom
     * @param dateTo
     * @return
     */
    @Override
    public Cursor betweenDate(String dateFrom, String dateTo) {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_DATE + DATABASE_COMMAND_BETWEEN + DATABASE_COMMAND_SINGLE_QUOTE + dateFrom + DATABASE_COMMAND_SINGLE_QUOTE + DATABASE_COMMAND_AND + DATABASE_COMMAND_SINGLE_QUOTE + dateTo + DATABASE_COMMAND_SINGLE_QUOTE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

    @Override
    public Cursor fetchByCategoryAndBetweenDates(int categoryId, String dateFrom, String dateTo) {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_IN + DATABASE_COMMAND_OPEN_BRACKET + DATABASE_COMMAND_SELECT + DATABASE_COMMAND_ID + DATABASE_COMMAND_SELECT_FROM + TABLE_MEDICINE + DATABASE_COMMAND_SELECT_WHERE + MEDICINE_KEY_CATID + DATABASE_COMMAND_SYMBOL_EQUAL + categoryId + DATABASE_COMMAND_CLOSE_BRACKET + DATABASE_COMMAND_AND + CONSUMPTION_KEY_DATE + DATABASE_COMMAND_BETWEEN +  DATABASE_COMMAND_SINGLE_QUOTE + dateFrom + DATABASE_COMMAND_SINGLE_QUOTE + DATABASE_COMMAND_AND  + DATABASE_COMMAND_SINGLE_QUOTE + dateTo + DATABASE_COMMAND_SINGLE_QUOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    @Override
    public Cursor fetchByMedicineAndBetweenDates(int medicineId, String dateFrom, String dateTo) {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineId + DATABASE_COMMAND_AND + CONSUMPTION_KEY_DATE + DATABASE_COMMAND_BETWEEN +  DATABASE_COMMAND_SINGLE_QUOTE + dateFrom + DATABASE_COMMAND_SINGLE_QUOTE + DATABASE_COMMAND_AND  + DATABASE_COMMAND_SINGLE_QUOTE + dateTo + DATABASE_COMMAND_SINGLE_QUOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    @Override
    public Cursor fetchByMedicineAndBetweenDatesUnconsumed(int medicineId, String dateFrom, String dateTo) {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_CONSUMPTION + DATABASE_COMMAND_SELECT_WHERE + CONSUMPTION_KEY_MEDICINEID + DATABASE_COMMAND_SYMBOL_EQUAL + medicineId + DATABASE_COMMAND_AND + CONSUMPTION_KEY_DATE + DATABASE_COMMAND_BETWEEN +  DATABASE_COMMAND_SINGLE_QUOTE + dateFrom + DATABASE_COMMAND_SINGLE_QUOTE + DATABASE_COMMAND_AND  + DATABASE_COMMAND_SINGLE_QUOTE + dateTo + DATABASE_COMMAND_SINGLE_QUOTE + DATABASE_COMMAND_AND + CONSUMPTION_KEY_QUANTITY + DATABASE_COMMAND_SYMBOL_EQUAL + DATABASE_COMMAND_ZERO;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

}
