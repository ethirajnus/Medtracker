package sg.edu.nus.iss.se.ft05.medipal.domain;



/**
 * Domain class for appointment
 * @author Dhruv Mandan Gopal
 */
public class Appointment {

    //variable
    private int id;
    private String date, time, clinic, description;

    //getters and settere
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

    public void setDescription(String description) {

        this.description = description;
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

    public String getDescription() {

        return description;
    }


    public int getId() {

        return id;
    }

    public Appointment(String date, String time, String clinic, String description) {

        this.date = date;
        this.time = time;
        this.clinic = clinic;
        this.description = description;

    }

    public Appointment() {

    }
}
