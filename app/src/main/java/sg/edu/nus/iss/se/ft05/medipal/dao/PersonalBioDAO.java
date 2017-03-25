package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.domain.PersonalBio;


/**
 * @author Moushumi Seal
 */
public interface PersonalBioDAO {

    public int delete(int id);

    public Cursor findAll();

    public PersonalBio findById(int id);

    public long insert(PersonalBio personalBio);

    public int update(PersonalBio personalBio);

    public int findPersonalBioId(String name, String dob, String idNo);
}
