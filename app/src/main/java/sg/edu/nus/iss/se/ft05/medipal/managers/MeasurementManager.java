package sg.edu.nus.iss.se.ft05.medipal.managers;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.dao.MeasurementDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.MeasurementDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.domain.Measurement;

/**
 * Created by ashu on 15-03-2017.
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

    public static Cursor findAll(Context context) {

        measurementAll = new MeasurementDAOImpl(context);
        return measurementAll.findAll();
    }

    public Measurement findById(Context context, int id) {

        measurementAll = new MeasurementDAOImpl(context);

        measurement = measurementAll.findById(id);

        return measurement;
    }

    public long save(Context context) {

        measurementDAO = new MeasurementDAOImpl(context);
        return measurementDAO.insert(measurement);
    }

    public int update(Context context) {

        measurementDAO = new MeasurementDAOImpl(context);
        return measurementDAO.update(measurement);
    }

    public int delete(Context context) {

        measurementDAO = new MeasurementDAOImpl(context);
        return measurementDAO.delete(measurement.getId());
    }

    public int getMaxId(Context context) {

        measurementDAO = new MeasurementDAOImpl(context);
        return measurementDAO.fetchMaxId();
    }
    public Measurement findLatest(Context context) {

        measurementDAO=new MeasurementDAOImpl(context);
        return measurementDAO.findLatest();
    }
}