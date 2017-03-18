package sg.edu.nus.iss.se.ft05.medipal;

/**
 * Created by Dhruv on 18/3/2017.
 */
import android.content.Context;
import android.database.Cursor;

import sg.edu.nus.iss.se.ft05.medipal.dao.AppointmentDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.AppointmentDAOImpl;
public class Appointment {
    private static AppointmentDAO appointmentAll;
    private Integer id;
    private String date, time, clinic, test, pre_test;
    private AppointmentDAO appointmentDAO;

    public Appointment() {

    }

    public Appointment(Integer id, String date, String time, String clinic, String test, String pre_test) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.clinic = clinic;
        this.test = test;
        this.pre_test = pre_test;
    }

    public static Cursor findAll(Context context) {
        appointmentAll = new AppointmentDAOImpl(context);
        return appointmentAll.findAll();
    }

    public static Appointment findById(Context context, long id) {
        appointmentAll = new AppointmentDAOImpl(context);
        return appointmentAll.findById(id);
    }

    public void setId(Integer id) {
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

    public long save(Context context) {
        appointmentDAO = new AppointmentDAOImpl(context);
        return appointmentDAO.insert(this);
    }

    public long update(Context context) {
        appointmentDAO = new AppointmentDAOImpl(context);
        return appointmentDAO.update(this);
    }

    public int delete(Context context) {
        appointmentDAO = new AppointmentDAOImpl(context);
        return appointmentDAO.delete(this.getId());
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

    public Integer getId() {
        return id;
    }

    public int updateReminder(Context context, boolean isChecked) {
        appointmentDAO = new AppointmentDAOImpl(context);
        return appointmentDAO.update(this);
    }

}
