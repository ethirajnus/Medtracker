package sg.edu.nus.iss.se.ft05.medipal.dao;


import sg.edu.nus.iss.se.ft05.medipal.Reminder;

/**
 * Created by ethi on 13/03/17.
 */

public interface ReminderDAO {
    int delete(int id);
    Reminder findById(int id);
    long insert(Reminder reminder);
    int update(Reminder reminder);
}
