package com.ethigeek.medipal.managers;

import android.content.Context;

import com.ethigeek.medipal.dao.ReminderDAOImpl;
import com.ethigeek.medipal.domain.Reminder;



/**
 * Class for Reminder Manager
 * Created by ethiraj srinivasan on 13/03/17.
 */
public class ReminderManager {

    private Reminder reminder;

    private ReminderDAOImpl reminderDAO;

    public Reminder getReminder() {
        return reminder;
    }

    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    public ReminderManager(int frequency, String startTime, int interval) {

        reminder = new Reminder(frequency, startTime, interval);
    }

    public ReminderManager() {

    }

    /**
     * Save operation
     * @param context
     * @return
     */
    public long save(Context context) {

        reminderDAO = new ReminderDAOImpl(context);
        return reminderDAO.insert(reminder);
    }

    /**
     * Method to find reminder by id
     * @param context
     * @param id
     * @return
     */
    public Reminder findById(Context context, int id) {

        reminderDAO = new ReminderDAOImpl(context);
        reminder = reminderDAO.findById(id);
        return reminder;
    }

    /**
     * Update Reminder
     * @param context
     * @return
     */
    public int update(Context context) {

        reminderDAO = new ReminderDAOImpl(context);
        return reminderDAO.update(reminder);
    }

    /**
     * Delete Reminder
     * @param context
     * @return
     */
    public int delete(Context context) {
        reminderDAO = new ReminderDAOImpl(context);
        return reminderDAO.delete(this.reminder.getId());
    }
}
