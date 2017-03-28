package com.ethigeek.medipal.managers;

import android.content.Context;
import android.database.Cursor;

import com.ethigeek.medipal.dao.MeasurementDAO;
import com.ethigeek.medipal.dao.MeasurementDAOImpl;
import com.ethigeek.medipal.domain.Measurement;


/**
 * Class for Measurement manager
 * @author Aakash Deep Mangalore
 */
public class MeasurementManager {

    private static MeasurementDAO measurementAll;
    private MeasurementDAO measurementDAO;

    private Measurement measurement;

    public static Measurement recent_measurement;

    public MeasurementManager(int systolic, int diastolic, int pulse, float temperature, int weight, String measuredOn) {

        measurement = new Measurement(systolic, diastolic, pulse, temperature, weight, measuredOn);

        recent_measurement = measurement;
    }

    public MeasurementManager() {
    }

    /**
     * Method for finding all measurements
     * @param context
     * @return
     */
    public static Cursor findAll(Context context) {

        measurementAll = new MeasurementDAOImpl(context);
        return measurementAll.findAll();
    }

    /**
     * Method to find measurement by ID
     * @param context
     * @param id
     * @return
     */
    public Measurement findById(Context context, int id) {

        measurementAll = new MeasurementDAOImpl(context);

        measurement = measurementAll.findById(id);

        return measurement;
    }

    /**
     * Save Measurement
     * @param context
     * @return
     */
    public long save(Context context) {

        measurementDAO = new MeasurementDAOImpl(context);
        return measurementDAO.insert(measurement);
    }

    /**
     * Update Measurement
     * @param context
     * @return
     */
    public int update(Context context) {

        measurementDAO = new MeasurementDAOImpl(context);
        return measurementDAO.update(measurement);
    }

    /**
     * Delete Measurement
     * @param context
     * @return
     */
    public int delete(Context context) {

        measurementDAO = new MeasurementDAOImpl(context);
        return measurementDAO.delete(measurement.getId());
    }

    /**
     * Method to get Measuremnt details
     * @param context
     * @return
     */
    public Measurement findLatest(Context context) {

        measurementDAO=new MeasurementDAOImpl(context);
        return measurementDAO.findLatest();
    }

    public Cursor betweenDate(Context context, String dateFrom, String dateTo) {
        measurementAll = new MeasurementDAOImpl(context);
        return measurementAll.betweenDate(dateFrom,dateTo);
    }
}