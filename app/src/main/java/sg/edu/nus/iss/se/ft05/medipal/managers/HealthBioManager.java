package sg.edu.nus.iss.se.ft05.medipal.managers;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.dao.HealthBioDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.HealthBioDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.domain.HealthBio;

/**
 * @author Moushumi Seal
 */

/**
 * Class for Health Bio Manager
 */
public class HealthBioManager {


    private HealthBio healthBio;
    private static HealthBioDAO healthBioAll;
    private HealthBioDAO healthBioDAO;

    /**
     * Getter for Health bio
     *
     * @return Health bio
     */
    public HealthBio getHealthBio() {
        return healthBio;
    }

    /**
     * Setter for Health bio
     *
     * @param healthBio
     */
    public void setHealthBio(HealthBio healthBio) {
        this.healthBio = healthBio;
    }

    public HealthBioManager(String mCondition, String mConditionType, String mStartDate) {

        healthBio = new HealthBio(mCondition, mConditionType, mStartDate);
    }

    public HealthBioManager() {
    }

    /**
     * Method for finding all healthbio
     * @param context
     * @return
     */
    public static Cursor findAll(Context context) {

        healthBioAll = new HealthBioDAOImpl(context);
        return healthBioAll.findAll();
    }

    /**
     * Method for find healthbio by id
     * @param context
     * @param id
     * @return
     */
    public HealthBio findById(Context context, int id) {

        healthBioAll = new HealthBioDAOImpl(context);
        healthBio = healthBioAll.findById(id);

        return healthBio;
    }

    /**
     * Save Health Bio
     * @param context
     * @return
     */
    public long save(Context context) {

        healthBioDAO = new HealthBioDAOImpl(context);
        return healthBioDAO.insert(healthBio);
    }

    /**
     * Update HealthBio
     * @param context
     * @return
     */
    public int update(Context context) {

        healthBioDAO = new HealthBioDAOImpl(context);
        return healthBioDAO.update(healthBio);
    }

    /**
     * Delete Health bio
     * @param context
     * @return
     */

    public int delete(Context context) {

        healthBioDAO = new HealthBioDAOImpl(context);
        return healthBioDAO.delete(healthBio.getId());
    }

}
