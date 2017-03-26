package sg.edu.nus.iss.se.ft05.medipal.dao;

import android.database.Cursor;

import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.domain.Consumption;

/**
 * Created by ethi on 10/03/17.
 */

/**
 * Interface for consumption database operations
 */
public interface ConsumptionDAO {

    /**
     * Delete operation
     *
     * @param id
     * @return
     */
    public int delete(int id);

    /**
     * Select all function
     *
     * @return
     */
    public Cursor findAll();

    /**
     * Insert opertion
     *
     * @param consumption
     * @return
     */
    public long insert(Consumption consumption);

    /**
     * Update operation
     *
     * @param consumption
     * @return
     */
    public int update(Consumption consumption);

    /**
     * @param id
     * @return
     */
    public Consumption findById(int id);

    public List<Consumption> findByDate(String date);

    public int totalQuantityConsumed(int medicineId);

    public Cursor fetchByCategory(int medicineCategoryId);

    public Cursor fetchByMedicine(int medicineId);

    public Cursor fetchByMedicineAndDate(int medicineId, String date);

    public List<Consumption> findByMedicineID(int medicineId);

    public Cursor fetchByMedicineAndYear(Integer medicineId, String year);

    public Cursor fetchByMedicineAndMonth(Integer medicineId, String year, String month);

    public Cursor fetchByMedicineDateAndTime(int medicineId, String date, String time);

    public Cursor fetchByCategoryAndDate(Integer medicineCategoryId, String date);

    public Cursor fetchByCategoryAndYear(Integer medicineCategoryId, String year);

    public Cursor fetchByCategoryAndMonth(Integer medicineCategoryId, String year, String month);

    public Cursor fetchByMedicineAndDateUnconsumed(Integer medicineId, String date);

    public Cursor fetchByMedicineAndYearUnconsumed(Integer medicineId, String year);

    public Cursor fetchByMedicineAndMonthUnconsumed(Integer medicineId, String year, String month);

    public int deleteAllForMedicine(int medicineId);

    public Cursor betweenDate(String dateFrom, String dateTo);

    public Cursor fetchByCategoryAndBetweenDates(int categoryId, String dateFrom, String dateTo);

    public Cursor fetchByMedicineAndBetweenDates(int medicineId, String dateFrom, String dateTo);

    public Cursor fetchByMedicineAndBetweenDatesUnconsumed(int medicineId, String dateFrom, String dateTo);
}
