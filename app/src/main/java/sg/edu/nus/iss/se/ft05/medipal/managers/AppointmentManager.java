package sg.edu.nus.iss.se.ft05.medipal.managers;

/**
 * Created by Dhruv on 18/3/2017.
 */

import android.content.Context;
import android.database.Cursor;

import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.dao.AppointmentDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.AppointmentDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.domain.Appointment;

public class AppointmentManager {
    private static AppointmentDAO appointmentAll;

    private AppointmentDAO appointmentDAO;

    public Appointment getAppointment() {

        return appointment;
    }

    public void setAppointment(Appointment appointment) {

        this.appointment = appointment;
    }

    private Appointment appointment;

    public AppointmentManager() {

    }

    public AppointmentManager(String date, String time, String clinic, String description) {

        appointment = new Appointment(date, time, clinic, description);
    }

    public static Cursor findAll(Context context) {

        appointmentAll = new AppointmentDAOImpl(context);
        return appointmentAll.findAll();
    }

    public Appointment findById(Context context, int id) {

        appointmentAll = new AppointmentDAOImpl(context);
        this.appointment = appointmentAll.findById(id);

        return appointment;
    }


    public long save(Context context) {

        appointmentDAO = new AppointmentDAOImpl(context);
        return appointmentDAO.insert(appointment);
    }

    public long update(Context context) {

        appointmentDAO = new AppointmentDAOImpl(context);
        return appointmentDAO.update(this.appointment);
    }

    public int delete(Context context) {

        appointmentDAO = new AppointmentDAOImpl(context);
        return appointmentDAO.delete(appointment.getId());
    }


   /* public int updateReminder(Context context, boolean isChecked) {

        appointmentDAO = new AppointmentDAOImpl(context);
        return appointmentDAO.update(this);
    }*/

    public static List<Appointment> findByDate(Context context, String date) {

        appointmentAll = new AppointmentDAOImpl(context);

        return appointmentAll.findByDate(date);
    }

    public static Cursor filterDate(Context context, String date) {

        appointmentAll = new AppointmentDAOImpl(context);
        return appointmentAll.filterDate(date);
    }

    public static boolean exists(Context context, String date, String time) {
        appointmentAll = new AppointmentDAOImpl(context);
        Cursor cursor = appointmentAll.fetchByAppointmentDateAndTime(date, time);
        return cursor.getCount() > 0;
    }
}
