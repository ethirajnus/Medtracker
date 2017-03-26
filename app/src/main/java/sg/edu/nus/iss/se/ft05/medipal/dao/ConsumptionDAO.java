package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.model.Consumption;

/**
 * Created by ethi on 10/03/17.
 */

/**
 * Interface for consumption database operations
 */
public interface ConsumptionDAO {
    /**
     * Delete operation
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * Select all function
     * @return
     */
    Cursor findAll();

    /**
     * Insert opertion
     * @param consumption
     * @return
     */
    long insert(Consumption consumption);

    /**
     * Update operation
     * @param consumption
     * @return
     */
    int update(Consumption consumption);

    /**
     *
     * @param id
     * @return
     */
    Consumption findById(int id);

    List<Consumption> findByDate(String date);

    int totalQuantityConsumed(int medicineId);

    Cursor fetchByCategory(int medicineCategoryId);

    Cursor fetchByMedicine(int medicineId);

    Cursor fetchByMedicineAndDate(int medicineId, String date);

    List<Consumption> findByMedicineID(int medicineId);

    Cursor fetchByMedicineAndYear(Integer medicineId, String year);

    Cursor fetchByMedicineAndMonth(Integer medicineId, String year, String month);

    Cursor fetchByMedicineDateAndTime(int medicineId, String date, String time);

    Cursor fetchByCategoryAndDate(Integer medicineCategoryId, String date);

    Cursor fetchByCategoryAndYear(Integer medicineCategoryId, String year);

    Cursor fetchByCategoryAndMonth(Integer medicineCategoryId, String year, String month);

    Cursor fetchByMedicineAndDateUnconsumed(Integer medicineId, String date);

    Cursor fetchByMedicineAndYearUnconsumed(Integer medicineId, String year);

    Cursor fetchByMedicineAndMonthUnconsumed(Integer medicineId, String year, String month);

    int deleteAllForMedicine(int medicineId);

    Cursor betweenDate(String dateFrom, String dateTo);
}
