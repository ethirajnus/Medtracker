package com.ethigeek.medtracker.dao;

import android.database.Cursor;

import java.util.List;

import com.ethigeek.medtracker.domain.Consumption;

/**
 * Created by ethiraj srinivasan on 10/03/17.
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

    /**
     * List consumption
     * @param date
     * @return
     */
    public List<Consumption> findByDate(String date);

    /**
     * total quantity consumed
     * @param medicineId
     * @return
     */
    public int totalQuantityConsumed(int medicineId);


/**
     * list consumption by medicine id
     * @param medicineId
     * @return
     */
    public List<Consumption> findByMedicineID(int medicineId);

  /**
     * delete medicine
     * @param medicineId
     * @return
     */
    public int deleteAllForMedicine(int medicineId);

  /**
     * data between date
     * @param dateFrom
     * @param dateTo
     * @return date
     */
    public Cursor betweenDate(String dateFrom, String dateTo);

      /**
     * Fetch medicine categoryid
     * @param medicineCategoryId
     * @return
     */
    public Cursor fetchByCategory(int medicineCategoryId);

    /**
     * fetch medicine by medicineid
     * @param medicineId
     * @return
     */
    public Cursor fetchByMedicine(int medicineId);

    /**
     * fetch by medicine and date
     * @param medicineId
     * @param date
     * @return
     */
    public Cursor fetchByMedicineAndDate(int medicineId, String date);


    /**
     * fetch consumption by medicine and year
     * @param medicineId
     * @param year
     * @return
     */
    public Cursor fetchByMedicineAndYear(Integer medicineId, String year);

    /**
     * fetch consumption by medicine and month
     * @param medicineId
     * @param year
     * @param month
     * @return
     */
    public Cursor fetchByMedicineAndMonth(Integer medicineId, String year, String month);

    /**
     * fetch consumption by medicine date and time
     * @param medicineId
     * @param date
     * @param time
     * @return
     */
    public Cursor fetchByMedicineDateAndTime(int medicineId, String date, String time);

    /**
     * fetch consumption by category and date
     * @param medicineCategoryId
     * @param date
     * @return
     */
    public Cursor fetchByCategoryAndDate(Integer medicineCategoryId, String date);

    /**
     * fetch consumption by category and year
     * @param medicineCategoryId
     * @param year
     * @return
     */
    public Cursor fetchByCategoryAndYear(Integer medicineCategoryId, String year);

    /**
     * fetch consumption by category and month
     * @param medicineCategoryId
     * @param year
     * @param month
     * @return
     */
    public Cursor fetchByCategoryAndMonth(Integer medicineCategoryId, String year, String month);

    /**
     * fetch unconsumed by medicine and date
     * @param medicineId
     * @param date
     * @return
     */
    public Cursor fetchByMedicineAndDateUnconsumed(Integer medicineId, String date);

    /**
     * fetch unconsumed by medicine and year
     * @param medicineId
     * @param year
     * @return
     */
    public Cursor fetchByMedicineAndYearUnconsumed(Integer medicineId, String year);

    /**
     * fetch unconsumed by medicine and month
     * @param medicineId
     * @param year
     * @param month
     * @return
     */
    public Cursor fetchByMedicineAndMonthUnconsumed(Integer medicineId, String year, String month);

    /**
     * fetch category between dates
     * @param categoryId
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public Cursor fetchByCategoryAndBetweenDates(int categoryId, String dateFrom, String dateTo);

    /**
     * fetch medicine between dates
     * @param medicineId
     * @param dateFrom
     * @param dateTo
     * @return dates
     */
    public Cursor fetchByMedicineAndBetweenDates(int medicineId, String dateFrom, String dateTo);

    /**
     * fetch unconsumed medicine between dates
     * @param medicineId
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public Cursor fetchByMedicineAndBetweenDatesUnconsumed(int medicineId, String dateFrom, String dateTo);
}
