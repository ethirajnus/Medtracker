package com.ethigeek.medipal.dao;


import android.database.Cursor;

import com.ethigeek.medipal.domain.Reminder;



/**
 * Interface for reminder database opertaion
 * @author Ethiraj Srinivasan
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
