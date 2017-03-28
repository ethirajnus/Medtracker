package com.ethigeek.medtracker.domain;



/**
 * Domain class for Measurement
 * Created by aakash on 15-03-2017.
 */
public class Measurement {

    //variables
    private int id, systolic, diastolic, pulse, weight;
    private float temperature;
    private String measuredOn;

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

    public Measurement() {

    }

    public Measurement(int systolic, int diastolic, int pulse, float temperature, int weight, String measuredOn) {

        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
        this.temperature = temperature;
        this.weight = weight;
        this.measuredOn = measuredOn;
    }

    @Override
    public String toString(){
        return getSystolic() + ","+ getDiastolic()+ ","+getPulse()+","+getTemperature()+","+getWeight()+"\n";
    }

}
