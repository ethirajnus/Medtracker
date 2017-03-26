package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.domain.Measurement;
import sg.edu.nus.iss.se.ft05.medipal.managers.MeasurementManager;


/**
 * Created by ashu on 15-03-2017.
 */

/**
 * Interface for Measurement database operations
 */
public interface MeasurementDAO {
    /**
     * Delete operation
     * @param id
     * @return
     */
    public int delete(int id);

    /**
     * Select all operation
     * @return
     */
    public Cursor findAll();

    /**
     * select operation with where clause using ID
     * @param id
     * @return
     */
    public Measurement findById(int id);

    /**
     * Insert operation
     * @param measurement
     * @return
     */
    public long insert(Measurement measurement);

    /**
     * Update operation
     * @param measurement
     * @return
     */
    public int update(Measurement measurement);

    /**
     *
     * @return
     */
    public Cursor fetchAllMeasurementsWithId();

    /**
     *
     * @return
     */
    public int fetchMaxId();

    /**
     *
     * @return
     */
    public Measurement findLatest();

    Cursor betweenDate(String dateFrom, String dateTo);
}
