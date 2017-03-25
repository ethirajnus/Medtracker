package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.domain.HealthBio;


/**
 * @author Moushumi Seal
 */

public interface HealthBioDAO {

    public int delete(int id);

    public Cursor findAll();

    public HealthBio findById(int id);

    public long insert(HealthBio healthBioManager);

    public int update(HealthBio healthBioManager);

    public Cursor fetchAllHealthBioWithId();
}
