package sg.edu.nus.iss.se.ft05.medipal.model;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.dao.HealthBioDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.HealthBioDAOImpl;

/**
 * @author Moushumi Seal
 */

public class HealthBio {

    private int id;
    private String mCondition;
    private String mConditionType;
    private String mStartDate;

    private static HealthBioDAO healthBioAll;
    private HealthBioDAO healthBioDAO;

    public HealthBio(String mCondition, String mConditionType, String mStartDate) {
        this.mCondition = mCondition;
        this.mConditionType = mConditionType;
        this.mStartDate = mStartDate;
    }

    public HealthBio() {
    }


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

    public static Cursor findAll(Context context){
        healthBioAll = new HealthBioDAOImpl(context);
        return healthBioAll.findAll();
    }

    public static HealthBio findById(Context context, int id){
        healthBioAll = new HealthBioDAOImpl(context);
        return  healthBioAll.findById(id);
    }

    public long save(Context context){
        healthBioDAO = new HealthBioDAOImpl(context);
        return healthBioDAO.insert(this);
    }

    public int update(Context context){
        healthBioDAO = new HealthBioDAOImpl(context);
        return healthBioDAO.update(this);
    }

    public int delete(Context context){
        healthBioDAO = new HealthBioDAOImpl(context);
        return healthBioDAO.delete(this.getId());
    }

}
