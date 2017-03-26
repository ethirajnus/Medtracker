package sg.edu.nus.iss.se.ft05.medipal.managers;

import android.content.Context;

import sg.edu.nus.iss.se.ft05.medipal.dao.ReminderDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.domain.Reminder;

/**
 * Created by ethi on 13/03/17.
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

    public long save(Context context) {

        reminderDAO = new ReminderDAOImpl(context);
        return reminderDAO.insert(reminder);
    }

    public Reminder findById(Context context, int id) {

        reminderDAO = new ReminderDAOImpl(context);
        reminder = reminderDAO.findById(id);
        return reminder;
    }

    public int update(Context context) {

        reminderDAO = new ReminderDAOImpl(context);
        return reminderDAO.update(reminder);
    }

    public int delete(Context context) {

        reminderDAO = new ReminderDAOImpl(context);
        return reminderDAO.delete(this.reminder.getId());
        // TODO Priority
    }
}
