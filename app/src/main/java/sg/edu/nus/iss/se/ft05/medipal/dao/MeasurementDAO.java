package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.domain.Measurement;


/**
 * Created by ashu on 15-03-2017.
 */

public interface MeasurementDAO {

    public int delete(int id);

    public Cursor findAll();

    public Measurement findById(int id);

    public long insert(Measurement measurement);

    public int update(Measurement measurement);

    public Cursor fetchAllMeasurementsWithId();

    public int fetchMaxId();

    public Measurement findLatest();

    Cursor betweenDate(String dateFrom, String dateTo);
}
