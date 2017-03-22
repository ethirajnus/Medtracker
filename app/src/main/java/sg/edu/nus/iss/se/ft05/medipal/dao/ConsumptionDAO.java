package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.animation.ObjectAnimator;
import android.database.Cursor;

import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.model.Consumption;
import sg.edu.nus.iss.se.ft05.medipal.model.Medicine;

/**
 * Created by ethi on 10/03/17.
 */

public interface ConsumptionDAO {
    int delete(int id);
    Cursor findAll();
    long insert(Consumption consumption);
    int update(Consumption consumption);
    Consumption findById(int id);
    List<Consumption> findByDate(String date);
    int totalQuantityConsumed(int medicineId);
}
