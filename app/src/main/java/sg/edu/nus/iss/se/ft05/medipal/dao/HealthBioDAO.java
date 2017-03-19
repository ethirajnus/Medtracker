package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.model.HealthBio;

/**
 * @author Moushumi Seal
 */

public interface HealthBioDAO {
    int delete(int id);
    Cursor findAll();
    HealthBio findById(int id);
    long insert(HealthBio healthBio);
    int update(HealthBio healthBio);
    Cursor fetchAllHealthBioWithId();
}
