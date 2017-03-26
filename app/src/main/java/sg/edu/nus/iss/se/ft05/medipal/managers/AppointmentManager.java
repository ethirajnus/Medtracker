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

/**
 * Class for Appointment manager
 */
public class AppointmentManager {
    private static AppointmentDAO appointmentAll;

    //DAO
    private AppointmentDAO appointmentDAO;

    /**
     * Getter for Appointment
     *
     * @return Appointment
     */
    public Appointment getAppointment() {

        return appointment;
    }

    /**
     * Setter for Appointment
     *
     * @param appointment
     */
    public void setAppointment(Appointment appointment) {

        this.appointment = appointment;
    }

    private Appointment appointment;

    public AppointmentManager() {

    }

    public AppointmentManager(String date, String time, String clinic, String test, String pre_test) {

        appointment = new Appointment(date, time, clinic, test, pre_test);
    }

    /**
     * Method for finding all appointments
     *
     * @param context
     * @return
     */
    public static Cursor findAll(Context context) {

        appointmentAll = new AppointmentDAOImpl(context);
        return appointmentAll.findAll();
    }

    /**
     * Method for find appointment by id
     * @param context
     * @param id
     * @return
     */
    public Appointment findById(Context context, int id) {

        appointmentAll = new AppointmentDAOImpl(context);
        this.appointment = appointmentAll.findById(id);

        return appointment;
    }

    /**
     * SAVE Appointment
     * @param context
     * @return
     */
    public long save(Context context) {

        appointmentDAO = new AppointmentDAOImpl(context);
        return appointmentDAO.insert(appointment);
    }

    /**
     * Update Appointment
     * @param context
     * @return
     */
    public long update(Context context) {

        appointmentDAO = new AppointmentDAOImpl(context);
        return appointmentDAO.update(this.appointment);
    }

    /**
     * Delete appointment
     * @param context
     * @return
     */
    public int delete(Context context) {

        appointmentDAO = new AppointmentDAOImpl(context);
        return appointmentDAO.delete(appointment.getId());
    }


   /* public int updateReminder(Context context, boolean isChecked) {

        appointmentDAO = new AppointmentDAOImpl(context);
        return appointmentDAO.update(this);
    }*/

    /**
     * List appointment
     * @param context
     * @param date
     * @return
     */
    public static List<Appointment> findByDate(Context context, String date) {

        appointmentAll = new AppointmentDAOImpl(context);

        return appointmentAll.findByDate(date);
    }

    /**
     * Filter appointment by date
     * @param context
     * @param date
     * @return
     */
    public static Cursor filterDate(Context context, String date) {

        appointmentAll = new AppointmentDAOImpl(context);
        return appointmentAll.filterDate(date);
    }
}
