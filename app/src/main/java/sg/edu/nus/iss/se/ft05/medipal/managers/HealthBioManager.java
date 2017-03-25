package sg.edu.nus.iss.se.ft05.medipal.managers;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.dao.HealthBioDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.HealthBioDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.domain.HealthBio;

/**
 * @author Moushumi Seal
 */

public class HealthBioManager {


    private HealthBio healthBio;
    private static HealthBioDAO healthBioAll;
    private HealthBioDAO healthBioDAO;

    public HealthBio getHealthBio() {
        return healthBio;
    }

    public void setHealthBio(HealthBio healthBio) {
        this.healthBio = healthBio;
    }

    public HealthBioManager(String mCondition, String mConditionType, String mStartDate) {

        healthBio = new HealthBio(mCondition, mConditionType, mStartDate);
    }

    public HealthBioManager() {
    }

    public static Cursor findAll(Context context) {

        healthBioAll = new HealthBioDAOImpl(context);
        return healthBioAll.findAll();
    }

    public HealthBio findById(Context context, int id) {

        healthBioAll = new HealthBioDAOImpl(context);
        healthBio = healthBioAll.findById(id);

        return healthBio;
    }

    public long save(Context context) {

        healthBioDAO = new HealthBioDAOImpl(context);
        return healthBioDAO.insert(healthBio);
    }

    public int update(Context context) {

        healthBioDAO = new HealthBioDAOImpl(context);
        return healthBioDAO.update(healthBio);
    }

    public int delete(Context context) {

        healthBioDAO = new HealthBioDAOImpl(context);
        return healthBioDAO.delete(healthBio.getId());
    }

}
