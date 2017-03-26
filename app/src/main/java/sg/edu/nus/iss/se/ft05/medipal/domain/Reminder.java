package sg.edu.nus.iss.se.ft05.medipal.domain;

/**
 * Created by e0146812 on 3/25/2017.
 */

/**
 * Domain class for Reminder
 */
public class Reminder {

    //Variables
    private int id, frequency, interval;
    private String startTime;

    //Getters and Setters
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

    public Reminder(int frequency, String startTime, int interval) {

        this.frequency = frequency;
        this.interval = interval;
        this.startTime = startTime;
    }

    public Reminder() {

    }
}
