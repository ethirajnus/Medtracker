package sg.edu.nus.iss.se.ft05.medipal.model;

/**
 * Created by Dhruv on 12/3/2017.
 */

public class Appointment {

    private int id;
    private String date, time, clinic, test, pre_test;

    public void setId(int id) {

        this.id = id;
    }

    public void setDate(String date) {

        this.date = date;
    }

    public void setTime(String time) {

        this.time = time;
    }

    public void setClinic(String clinic) {

        this.clinic = clinic;
    }

    public void setTest(String test) {

        this.test = test;
    }

    public void setPreTest(String pre_test) {

        this.pre_test = pre_test;
    }

    public String getDate() {

        return date;
    }

    public String getTime() {

        return time;
    }

    public String getClinic() {

        return clinic;
    }

    public String getTest() {

        return test;
    }

    public String getPreTest() {

        return pre_test;
    }

    public int getId() {

        return id;
    }

    public Appointment(String date, String time, String clinic, String test, String pre_test) {

        this.date = date;
        this.time = time;
        this.clinic = clinic;
        this.test = test;
        this.pre_test = pre_test;
    }

    public Appointment() {

    }
}
