package sg.edu.nus.iss.se.ft05.medipal.dao;

/**
 * Created by Dhruv on 18/3/2017.
 */
import android.database.Cursor;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.model.Appointment;
public interface AppointmentDAO {
    public int delete(long id);
    public Cursor findAll();
    public Appointment findById(long id);
    public long insert(Appointment appointment);
    public int update(Appointment appointment);
    List<Appointment> findByDate(String date);
}
