package sg.edu.nus.iss.se.ft05.medipal.dao;


import android.database.Cursor;

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
     *
     * @param id
     * @return
     */
    public int delete(int id);

    /**
     * select operation with where clause using ID
     *
     * @param id
     * @return
     */
    public Reminder findById(int id);

    /**
     * Insert operation
     *
     * @param reminder
     * @return
     */
    public long insert(Reminder reminder);

    /**
     * @param reminder
     * @return
     */
    public int update(Reminder reminder);

    public Cursor findAll();

}
