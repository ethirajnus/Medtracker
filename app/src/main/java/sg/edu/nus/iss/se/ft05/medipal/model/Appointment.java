package sg.edu.nus.iss.se.ft05.medipal.model;

/**
 * Created by Dhruv on 18/3/2017.
 */
import android.content.Context;
import android.database.Cursor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sg.edu.nus.iss.se.ft05.medipal.dao.AppointmentDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.AppointmentDAOImpl;

import static sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper.MEDICINE_KEY_ID;
import static sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper.MEDICINE_KEY_REMINDERID;

public class Appointment {
    private static AppointmentDAO appointmentAll;
    private int id;
    private String date, time, clinic, test, pre_test;
    private AppointmentDAO appointmentDAO;

    public Appointment() {

    }

    public Appointment(String date, String time, String clinic, String test, String pre_test) {
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

    public int getId() {
        return id;
    }

    public int updateReminder(Context context, boolean isChecked) {
        appointmentDAO = new AppointmentDAOImpl(context);
        return appointmentDAO.update(this);
    }

    public static List<Appointment> findByDate(Context context, String date) {
        appointmentAll = new AppointmentDAOImpl(context);
        return appointmentAll.findByDate(date);
    }
    public static Cursor filterDate(Context context,String date){
        appointmentAll = new AppointmentDAOImpl(context);
        return appointmentAll.filterDate(date);
    }
}
