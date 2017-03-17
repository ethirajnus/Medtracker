package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.Contacts;

/**
 * Created by Ashish Katre
 */

public interface ICEContactsDAO {

    public int delete(int id);

    public Cursor findAll();

    public Contacts findById(int id);

    public long insert(Contacts contact);

    public int update(Contacts contact);

    public int updatePriority(Contacts contact);

    public int findMaxPriority();
}
