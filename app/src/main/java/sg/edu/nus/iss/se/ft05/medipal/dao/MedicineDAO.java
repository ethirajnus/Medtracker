package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.domain.Medicine;

/**
 * Created by ethi on 12/03/17.
 */

/**
 * Interface for medicine database operations
 */
public interface MedicineDAO {
    /**
     * Delete operation
     * @param id
     * @return
     */
    public int delete(int id);

    /**
     * Selection all operation
     * @return
     */
    public Cursor findAll();

    /**
     * select operation with where clause using ID
     * @param id
     * @return
     */
    public Medicine findById(int id);

    /**
     * Insert operation
     * @param medicine
     * @return
     */
    public long insert(Medicine medicine);

    /**
     * Update operation
     * @param medicine
     * @return
     */
    public int update(Medicine medicine);

    /**
     *
     * @return
     */
    public Cursor fetchAllMedicinesWithId();
}
