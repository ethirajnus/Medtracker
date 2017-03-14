package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.Contacts;

/**
 * Created by Ashish Katre
 */

public interface ICEContactsDAO {

    public int delete(long id);

    public Cursor findAll();

    public Contacts findById(long id);

    public long insert(Contacts contact);

    public int update(Contacts contact);

    public int updatePriority(Contacts contact);
}
