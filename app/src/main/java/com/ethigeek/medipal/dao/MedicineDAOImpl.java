package com.ethigeek.medipal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ethigeek.medipal.daoutils.DBHelper;
import com.ethigeek.medipal.domain.Medicine;

import static com.ethigeek.medipal.daoutils.DbConstants.*;



/**
 * Implementation class for medicine database operations
 * @author Ethiraj Srinivasan
 */
public class MedicineDAOImpl extends DBHelper implements MedicineDAO {

    public MedicineDAOImpl(Context context) {
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
        return db.delete(TABLE_MEDICINE, MEDICINE_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(id)});
    }

    /**
     * Select all operation
     * @return
     */
    @Override
    public Cursor findAll() {
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_MEDICINE;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);
    }

    /**
     * select operation with where clause using ID
     * @param id
     * @return
     */
    @Override
    public Medicine findById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_MEDICINE + DATABASE_COMMAND_SELECT_WHERE
                + MEDICINE_KEY_ID + DATABASE_COMMAND_SYMBOL_EQUAL + id;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            c.moveToFirst();
        }

        Medicine medicine = new Medicine();

        medicine.setId(c.getInt(c.getColumnIndex(MEDICINE_KEY_ID)));
        medicine.setName((c.getString(c.getColumnIndex(MEDICINE_KEY_MEDICINE))));
        medicine.setDescription(c.getString(c.getColumnIndex(MEDICINE_KEY_DESCRIPTION)));
        medicine.setCategoryId(c.getInt(c.getColumnIndex(MEDICINE_KEY_CATID)));
        medicine.setReminderId(c.getInt(c.getColumnIndex(MEDICINE_KEY_REMINDERID)));
        medicine.setRemind(c.getInt(c.getColumnIndex(CATEGORY_KEY_REMIND)) == 1);
        medicine.setQuantity(c.getInt(c.getColumnIndex(MEDICINE_KEY_QUANTITY)));
        medicine.setDosage(c.getInt(c.getColumnIndex(MEDICINE_KEY_DOSAGE)));
        medicine.setConsumeQuantity(c.getInt(c.getColumnIndex(MEDICINE_KEY_CONSUME_QUALITY)));
        medicine.setThreshold(c.getInt(c.getColumnIndex(MEDICINE_KEY_THRESHOLD)));
        medicine.setDateIssued(c.getString(c.getColumnIndex(MEDICINE_KEY_DATE_ISSUED)));
        medicine.setExpireFactor(c.getInt(c.getColumnIndex(MEDICINE_KEY_EXPIRE_FACTOR)));
        db.close();
        return medicine;
    }

    /**
     * Insert operation
     * @param medicine
     * @return
     */
    @Override
    public long insert(Medicine medicine) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MEDICINE_KEY_MEDICINE, medicine.getName());
        values.put(MEDICINE_KEY_DESCRIPTION, medicine.getDescription());
        values.put(MEDICINE_KEY_CATID, medicine.getCategoryId());
        values.put(MEDICINE_KEY_REMINDERID, medicine.getReminderId());
        values.put(MEDICINE_KEY_REMIND, medicine.getRemind());
        values.put(MEDICINE_KEY_QUANTITY, medicine.getQuantity());
        values.put(MEDICINE_KEY_DOSAGE, medicine.getDosage());
        values.put(MEDICINE_KEY_CONSUME_QUALITY, medicine.getConsumeQuantity());
        values.put(MEDICINE_KEY_THRESHOLD, medicine.getThreshold());
        values.put(MEDICINE_KEY_DATE_ISSUED, medicine.getDateIssued());
        values.put(MEDICINE_KEY_EXPIRE_FACTOR, medicine.getExpireFactor());

        // insert row
        return db.insert(TABLE_MEDICINE, null, values);
    }

    /**
     * Update operation
     * @param medicine
     * @return
     */
    @Override
    public int update(Medicine medicine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MEDICINE_KEY_MEDICINE, medicine.getName());
        values.put(MEDICINE_KEY_DESCRIPTION, medicine.getDescription());
        values.put(MEDICINE_KEY_CATID, medicine.getCategoryId());
        values.put(MEDICINE_KEY_REMINDERID, medicine.getReminderId());
        values.put(MEDICINE_KEY_REMIND, medicine.getRemind());
        values.put(MEDICINE_KEY_QUANTITY, medicine.getQuantity());
        values.put(MEDICINE_KEY_DOSAGE, medicine.getDosage());
        values.put(MEDICINE_KEY_CONSUME_QUALITY, medicine.getConsumeQuantity());
        values.put(MEDICINE_KEY_THRESHOLD, medicine.getThreshold());
        values.put(MEDICINE_KEY_DATE_ISSUED, medicine.getDateIssued());
        values.put(MEDICINE_KEY_EXPIRE_FACTOR, medicine.getExpireFactor());
        // updating row
        return db.update(TABLE_MEDICINE, values, MEDICINE_KEY_ID + DATABASE_COMMAND_SYMBOL,
                new String[]{String.valueOf(medicine.getId())});

    }

    /**
     *
     * @return
     */
    @Override
    public Cursor fetchAllMedicinesWithId() {
        String selectQuery = DATABASE_COMMAND_SELECT_ID_MEDICINE + TABLE_MEDICINE;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(selectQuery, null);

    }

    @Override
    public Medicine fetchMedicineByNameandDateIssued(String medicineName, String medicineDateIssued) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = DATABASE_COMMAND_SELECT_ALL + TABLE_MEDICINE + DATABASE_COMMAND_SELECT_WHERE
                + MEDICINE_KEY_MEDICINE + DATABASE_COMMAND_SYMBOL_EQUAL + DATABASE_COMMAND_SINGLE_QUOTE +  medicineName + DATABASE_COMMAND_SINGLE_QUOTE + DATABASE_COMMAND_AND + MEDICINE_KEY_DATE_ISSUED + DATABASE_COMMAND_SYMBOL_EQUAL + DATABASE_COMMAND_SINGLE_QUOTE + medicineDateIssued + DATABASE_COMMAND_SINGLE_QUOTE;
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            c.moveToFirst();
        }

        Medicine medicine = new Medicine();

        medicine.setId(c.getInt(c.getColumnIndex(MEDICINE_KEY_ID)));
        medicine.setName((c.getString(c.getColumnIndex(MEDICINE_KEY_MEDICINE))));
        medicine.setDescription(c.getString(c.getColumnIndex(MEDICINE_KEY_DESCRIPTION)));
        medicine.setCategoryId(c.getInt(c.getColumnIndex(MEDICINE_KEY_CATID)));
        medicine.setReminderId(c.getInt(c.getColumnIndex(MEDICINE_KEY_REMINDERID)));
        medicine.setRemind(c.getInt(c.getColumnIndex(CATEGORY_KEY_REMIND)) == 1);
        medicine.setQuantity(c.getInt(c.getColumnIndex(MEDICINE_KEY_QUANTITY)));
        medicine.setDosage(c.getInt(c.getColumnIndex(MEDICINE_KEY_DOSAGE)));
        medicine.setConsumeQuantity(c.getInt(c.getColumnIndex(MEDICINE_KEY_CONSUME_QUALITY)));
        medicine.setThreshold(c.getInt(c.getColumnIndex(MEDICINE_KEY_THRESHOLD)));
        medicine.setDateIssued(c.getString(c.getColumnIndex(MEDICINE_KEY_DATE_ISSUED)));
        medicine.setExpireFactor(c.getInt(c.getColumnIndex(MEDICINE_KEY_EXPIRE_FACTOR)));
        db.close();
        return medicine;
    }
}
