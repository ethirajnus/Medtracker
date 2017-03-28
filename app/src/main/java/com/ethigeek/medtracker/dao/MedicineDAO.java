package com.ethigeek.medtracker.dao;

import android.database.Cursor;

import com.ethigeek.medtracker.domain.Medicine;



/**
 * Interface for medicine database operations
 * @author Ethiraj Srinivasan
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

    Medicine fetchMedicineByNameandDateIssued(String medicineName, String medicineDateIssued);
}
