package sg.edu.nus.iss.se.ft05.medipal.dao;

/**
 * Created by Dhruv on 18/3/2017.
 */

import android.database.Cursor;

import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.domain.Appointment;

/**
 * Interface for Appointement database operatiosn
 */
public interface AppointmentDAO {
    /**
     * Delete operation
     * @param id
     * @return
     */
    public int delete(int id);

    /**
     * select all function
     * @return
     */
    public Cursor findAll();

    /**
     * select operation with where clause using ID
     * @param id
     * @return
     */
    public Appointment findById(int id);

    /**
     * Insert operation
     * @param appointment
     * @return
     */
    public long insert(Appointment appointment);

    /**
     * Update operation
     * @param appointment
     * @return
     */
    public int update(Appointment appointment);

    /**
     * Listing
     * @param date
     * @return
     */
    public List<Appointment> findByDate(String date);

    public Cursor filterDate(String date);

    public Cursor fetchByAppointmentDateAndTime(String date, String time);
}
