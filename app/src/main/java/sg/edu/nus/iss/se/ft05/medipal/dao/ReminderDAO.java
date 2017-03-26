package sg.edu.nus.iss.se.ft05.medipal.dao;


import sg.edu.nus.iss.se.ft05.medipal.domain.Reminder;

/**
 * Created by ethi on 13/03/17.
 */

/**
 * Interface for reminder database opertaion
 */
public interface ReminderDAO {
    /**
     * Delete operation
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * select operation with where clause using ID
     * @param id
     * @return
     */
    Reminder findById(int id);

    /**
     * Insert operation
     * @param reminder
     * @return
     */
    long insert(Reminder reminder);

    /**
     *
     * @param reminder
     * @return
     */
    int update(Reminder reminder);
}
