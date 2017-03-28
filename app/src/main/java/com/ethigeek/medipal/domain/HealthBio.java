package com.ethigeek.medipal.domain;



/**
 * Domain class for Health bio
 * @author Moushumi Seal
 */
public class HealthBio {

    //variables
    private int id;
    private String mCondition;
    private String mConditionType;
    private String mStartDate;

    //Getters and Setters
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCondition() {
        return mCondition;
    }

    public void setCondition(String mCondition) {
        this.mCondition = mCondition;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String mStartDate) {
        this.mStartDate = mStartDate;
    }

    public String getConditionType() {
        return mConditionType;
    }

    public void setConditionType(String mConditionType) {
        this.mConditionType = mConditionType;
    }

    public HealthBio(String mCondition, String mConditionType, String mStartDate) {

        this.mCondition = mCondition;
        this.mConditionType = mConditionType;
        this.mStartDate = mStartDate;
    }

    public HealthBio() {
    }
}
