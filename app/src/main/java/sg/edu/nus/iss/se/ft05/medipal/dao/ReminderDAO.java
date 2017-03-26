package sg.edu.nus.iss.se.ft05.medipal.dao;


import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.domain.Reminder;

/**
 * Created by ethi on 13/03/17.
 */

public interface ReminderDAO {

    public Cursor findAll();

    public int delete(int id);

    public Reminder findById(int id);

    public long insert(Reminder reminder);

    public int update(Reminder reminder);
}
