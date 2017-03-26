package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.domain.HealthBio;




/**
 * Interface for Health bio database operations
 * @author Moushumi Seal
 */
public interface HealthBioDAO {
    /**
     * Delete operation
     * @param id
     * @return
     */
    public int delete(int id);

    /**
     * Select all function
     * @return
     */
    public Cursor findAll();

    /**
     *
     * @param id
     * @return
     */
    public HealthBio findById(int id);

    /**
     * Insert operation
     * @param healthBioManager
     * @return
     */
    public long insert(HealthBio healthBioManager);

    /**
     * Update operation
     * @param healthBioManager
     * @return
     */
    public int update(HealthBio healthBioManager);

    /**
     *
     * @return
     */
    public Cursor fetchAllHealthBioWithId();
}
