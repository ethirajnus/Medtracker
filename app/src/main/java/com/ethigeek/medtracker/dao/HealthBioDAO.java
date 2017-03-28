package com.ethigeek.medtracker.dao;

import android.database.Cursor;

import com.ethigeek.medtracker.domain.HealthBio;




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
