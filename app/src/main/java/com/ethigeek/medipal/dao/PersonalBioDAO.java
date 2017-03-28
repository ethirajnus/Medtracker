package com.ethigeek.medipal.dao;

import android.database.Cursor;

import com.ethigeek.medipal.domain.PersonalBio;




/**
 * Interface for personal bio database operations
 * @author Moushumi Seal
 */
public interface PersonalBioDAO {
    /**
     * Delete operation
     * @param id
     * @return
     */
    public int delete(int id);

    /**
     * Select all operation
     * @return
     */
    public Cursor findAll();

    /**
     * select operation with where clause using ID
     * @param id
     * @return
     */
    public PersonalBio findById(int id);

    /**
     * Insert operation
     * @param personalBio
     * @return
     */
    public long insert(PersonalBio personalBio);

    /**
     * update operation
     * @param personalBio
     * @return
     */
    public int update(PersonalBio personalBio);

    /**
     *
     * @param name
     * @param dob
     * @param idNo
     * @return
     */
    public int findPersonalBioId(String name, String dob, String idNo);
}
