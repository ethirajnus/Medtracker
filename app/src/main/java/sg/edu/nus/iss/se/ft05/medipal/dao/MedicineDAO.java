package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.domain.Medicine;

/**
 * Created by ethi on 12/03/17.
 */
public interface MedicineDAO {

    public int delete(int id);

    public Cursor findAll();

    public Medicine findById(int id);

    public long insert(Medicine medicine);

    public int update(Medicine medicine);

    public Cursor fetchAllMedicinesWithId();
}
