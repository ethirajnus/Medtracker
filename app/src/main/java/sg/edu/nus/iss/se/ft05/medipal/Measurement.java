package sg.edu.nus.iss.se.ft05.medipal;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.dao.MeasurementDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.MeasurementDAOImpl;

/**
 * Created by ashu on 15-03-2017.
 */

public class Measurement {

    private int id;
    private int systolic;
    private int diastolic;
    private int pulse;
    private float temperature;
    private int weight;
    private String measuredOn;

    private static MeasurementDAO measurementAll;
    private MeasurementDAO measurementDAO;

    public Measurement(int systolic,int diastolic,int pulse,float temperature,int weight,String measuredOn){
        //this.id=id;
        this.systolic=systolic;
        this.diastolic=diastolic;
        this.pulse=pulse;
        this.temperature=temperature;
        this.weight=weight;
        this.measuredOn=measuredOn;
    }

    public Measurement() {}

    public String toString(){
        return id+"-"+systolic+"-"+diastolic+"-"+pulse+"-"+temperature+"-"+weight+"-"+measuredOn;
    }
    public static Cursor findAll(Context context){
        measurementAll = new MeasurementDAOImpl(context);
        return measurementAll.findAll();
    }

    public static Measurement findById(Context context, int id){
        measurementAll = new MeasurementDAOImpl(context);
        return  measurementAll.findById(id);
    }

    public long save(Context context){
        measurementDAO = new MeasurementDAOImpl(context);
        return measurementDAO.insert(this);
    }

    public int update(Context context){
        measurementDAO = new MeasurementDAOImpl(context);
        return measurementDAO.update(this);
    }

    public int delete(Context context){
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
}