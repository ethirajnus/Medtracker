package sg.edu.nus.iss.se.ft05.medipal.dao;

/**
 * Created by Dhruv on 18/3/2017.
 */

import android.database.Cursor;

import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.model.Appointment;

public interface AppointmentDAO {

    public int delete(int id);

    public Cursor findAll();

    public Appointment findById(int id);

    public long insert(Appointment appointment);

    public int update(Appointment appointment);

    public List<Appointment> findByDate(String date);

    public Cursor filterDate(String date);
}
