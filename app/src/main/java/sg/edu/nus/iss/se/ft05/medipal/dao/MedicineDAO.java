package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.model.Medicine;

/**
 * Created by ethi on 12/03/17.
 */

public interface MedicineDAO {
    int delete(int id);
    Cursor findAll();
    Medicine findById(int id);
    long insert(Medicine medicine);
    int update(Medicine medicine);
}
