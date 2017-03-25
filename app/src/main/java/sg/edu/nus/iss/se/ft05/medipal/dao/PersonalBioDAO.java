package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.model.PersonalBio;

/**
 * @author Moushumi Seal
 */

public interface PersonalBioDAO {
    int delete(int id);
    Cursor findAll();
    PersonalBio findById(int id);
    long insert(PersonalBio personalBio);
    int update(PersonalBio personalBio);
    int findPersonalBioId(String name, String dob, String idNo);
}
