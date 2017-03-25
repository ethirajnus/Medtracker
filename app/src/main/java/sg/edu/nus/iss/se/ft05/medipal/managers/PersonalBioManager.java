package sg.edu.nus.iss.se.ft05.medipal.managers;

import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.dao.PersonalBioDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.PersonalBioDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.domain.PersonalBio;

/**
 * @author Moushumi Seal
 */

public class PersonalBioManager {


    private PersonalBio personalBio;
    private PersonalBioDAO personalBioDAO;
    private static PersonalBioDAO personalBioDAOAll;

    public PersonalBio getPersonalBio() {
        return personalBio;
    }

    public void setPersonalBio(PersonalBio personalBio) {
        this.personalBio = personalBio;
    }

    public PersonalBioManager(String name, String dob,
                              String idNo, String address, String postalCode,
                              String height, String bloodType) {

        personalBio = new PersonalBio(name, dob,
                idNo, address, postalCode,
                height, bloodType);
    }

    public PersonalBioManager() {
    }


    public static Cursor findAll(Context context) {

        personalBioDAOAll = new PersonalBioDAOImpl(context);
        return personalBioDAOAll.findAll();
    }

    public PersonalBio findById(Context context, int id) {

        personalBioDAOAll = new PersonalBioDAOImpl(context);
        personalBio = personalBioDAOAll.findById(id);

        return personalBio;
    }

    public long save(Context context) {
        personalBioDAO = new PersonalBioDAOImpl(context);
        return personalBioDAO.insert(personalBio);
    }

    public int update(Context context) {
        personalBioDAO = new PersonalBioDAOImpl(context);
        return personalBioDAO.update(personalBio);
    }

    public int delete(Context context) {

        personalBioDAO = new PersonalBioDAOImpl(context);
        return personalBioDAO.delete(personalBio.getId());
    }

    public int findPersonalBioId(Context context, String name, String dob, String idNo) {

        personalBioDAO = new PersonalBioDAOImpl(context);
        return personalBioDAO.findPersonalBioId(name, dob, idNo);
    }
}
