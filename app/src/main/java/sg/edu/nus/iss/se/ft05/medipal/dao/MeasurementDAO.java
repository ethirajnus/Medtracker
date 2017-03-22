package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;


import sg.edu.nus.iss.se.ft05.medipal.Measurement;

/**
 * Created by ashu on 15-03-2017.
 */

public interface MeasurementDAO {
    int delete(int id);
    Cursor findAll();
    Measurement findById(int id);
    long insert(Measurement measurement);
    int update(Measurement measurement);
    Cursor fetchAllMeasurementsWithId();
}
