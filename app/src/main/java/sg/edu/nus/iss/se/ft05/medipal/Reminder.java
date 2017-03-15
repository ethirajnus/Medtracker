package sg.edu.nus.iss.se.ft05.medipal;

import android.content.Context;
import android.util.Log;
import android.widget.CheckBox;

import sg.edu.nus.iss.se.ft05.medipal.dao.CategoryDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.dao.ReminderDAOImpl;

/**
 * Created by ethi on 13/03/17.
 */

public class Reminder {

    private static ReminderDAOImpl reminderAll;
    private int id,frequency,interval;
    private String startTime;
    private ReminderDAOImpl reminderDAO;

    public Reminder(int frequency, String startTime,int interval) {
        this.frequency = frequency;
        this.interval = interval;
        this.startTime = startTime;
    }

    public Reminder() {

    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;

    }

    public long save(Context context) {
        reminderDAO = new ReminderDAOImpl(context);
        return reminderDAO.insert(this);
    }

    public static Reminder findById(Context context, int id){
        reminderAll = new ReminderDAOImpl(context);
        return  reminderAll.findById(id);
    }

    public int update(Context context) {
        reminderDAO = new ReminderDAOImpl(context);
        return reminderDAO.update(this);
    }
}
