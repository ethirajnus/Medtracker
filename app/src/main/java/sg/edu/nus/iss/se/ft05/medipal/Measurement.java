package sg.edu.nus.iss.se.ft05.medipal;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.dao.MeasurementDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.MeasurementDAOImpl;

/**
 * Created by ashu on 15-03-2017.
 */

public class Measurement {

    private int id, systolic, diastolic, pulse, weight;
    private float temperature;
    private String measuredOn;
    public static int recent_systolic,recent_diastolic,recent_pulse,recent_weight;
    public static float recent_temperature;

    private static MeasurementDAO measurementAll;
    private MeasurementDAO measurementDAO;

    public Measurement(int systolic, int diastolic, int pulse, float temperature, int weight, String measuredOn) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
        this.temperature = temperature;
        this.weight = weight;
        this.measuredOn = measuredOn;
        recent_systolic=systolic;
        recent_diastolic=diastolic;
        recent_pulse=pulse;
        recent_weight=weight;
        recent_temperature=temperature;
    }

    public Measurement() {
    }

    public static Cursor findAll(Context context) {
        measurementAll = new MeasurementDAOImpl(context);
        return measurementAll.findAll();
    }

    public static Measurement findById(Context context, int id) {
        measurementAll = new MeasurementDAOImpl(context);
        return measurementAll.findById(id);
    }

    public long save(Context context) {
        measurementDAO = new MeasurementDAOImpl(context);
        return measurementDAO.insert(this);
    }

    public int update(Context context) {
        measurementDAO = new MeasurementDAOImpl(context);
        return measurementDAO.update(this);
    }

    public int delete(Context context) {
        measurementDAO = new MeasurementDAOImpl(context);
        return measurementDAO.delete(this.getId());
    }

    //Getters & Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSystolic() {
        return systolic;
    }


    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getMeasuredOn() {
        return measuredOn;
    }

    public void setMeasuredOn(String measuredOn) {
        this.measuredOn = measuredOn;
    }

    static public int getRecentSystolic() {return recent_systolic;}

    static public int getRecent_diastolic() {return recent_diastolic;}

    static public int getRecent_pulse() {return recent_pulse;}

    static public int getRecent_weight() {return recent_weight;}

    static public float getRecent_temperature() {return recent_temperature;}
}