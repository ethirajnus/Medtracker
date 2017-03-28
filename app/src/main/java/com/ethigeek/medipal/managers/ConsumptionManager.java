package com.ethigeek.medipal.managers;

import android.content.Context;
import android.database.Cursor;


import java.util.ArrayList;
import java.util.List;

import com.ethigeek.medipal.dao.ConsumptionDAOImpl;
import com.ethigeek.medipal.domain.Consumption;
import com.ethigeek.medipal.domain.Medicine;

/**
 * Created by ethiraj srinivasan on 10/03/17.
 */


public class ConsumptionManager {

    private static ConsumptionDAOImpl consumptionAll;

    private ConsumptionDAOImpl consumptionDAO;

    private Consumption consumption;

    public Consumption getConsumption() {
        return consumption;
    }

    public void setConsumption(Consumption consumption) {
        this.consumption = consumption;
    }

    public ConsumptionManager(int medicineId, int quantity, String date, String time) {

        this.consumption = new Consumption(medicineId, quantity, date, time);
    }

    public ConsumptionManager() {

    }

    /**
     * Method for finding all consumption
     *
     * @param context
     * @return
     */
    public static Cursor findAll(Context context) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.findAll();
    }

    /**
     * Method to filter consumption by date
     *
     * @param context
     * @param date
     * @return
     */
    public static Cursor filterDate(Context context, String date) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.filterDate(date);
    }


    /**
     * Method to find consumption by id
     *
     * @param context
     * @param id
     * @return
     */
    public Consumption findById(Context context, int id) {
        consumptionDAO = new ConsumptionDAOImpl(context);
        consumption = consumptionDAO.findById(id);
        return consumption;
    }

    /**
     * SAve consumption
     *
     * @param context
     * @return
     */
    public long save(Context context) {
        consumptionDAO = new ConsumptionDAOImpl(context);
        return consumptionDAO.insert(consumption);
    }

    /**
     * Update consumption
     *
     * @param context
     * @return
     */
    public int update(Context context) {
        consumptionDAO = new ConsumptionDAOImpl(context);
        return consumptionDAO.update(consumption);
    }

    /**
     * Delete consumption
     *
     * @param context
     * @return
     */
    public int delete(Context context) {
        consumptionDAO = new ConsumptionDAOImpl(context);
        return consumptionDAO.delete(this.consumption.getId());
    }

    /**
     * Method to get medicine
     *
     * @param context
     * @return
     */
    public Medicine getMedicine(Context context) {
        MedicineManager medicineManager = new MedicineManager();
        return medicineManager.findById(context, consumption.getMedicineId());
    }

    /**
     * List all consumption
     *
     * @param context
     * @param date
     * @return
     */
    public static List<Consumption> findByDate(Context context, String date) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.findByDate(date);
    }

    public static int totalQuantityConsumed(Context context, int medicineId) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.totalQuantityConsumed(medicineId);
    }

    public static List<Consumption> findByMedicineID(Context context, int medicineId) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.findByMedicineID(medicineId);
    }

    public static List<Consumption> filterByDate(List<Consumption> consumptionList, String date) {

        List<Consumption> filteredConsumptionList = new ArrayList<>();

        for (Consumption consumption : consumptionList) {

            if (consumption.getDate().contentEquals(date)) {

                filteredConsumptionList.add(consumption);
            }
        }
        return filteredConsumptionList;
    }

    /**
     * Fetch consumption based on category
     *
     * @param context
     * @param medicineCategoryId
     * @return
     */
    public static Cursor fetchByCategory(Context context, int medicineCategoryId) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByCategory(medicineCategoryId);
    }

    /**
     * Fetch consumption based on medicine
     *
     * @param context
     * @param medicineId
     * @return
     */
    public static Cursor fetchByMedicine(Context context, int medicineId) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicine(medicineId);
    }

    /**
     * Fetch consumption based on medicine and date
     *
     * @param context
     * @param medicineId
     * @param date
     * @return
     */
    public static Cursor fetchByMedicineAndDate(Context context, Integer medicineId, String date) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndDate(medicineId, date);
    }

    /**
     * Fetch consumption based on medicine and year
     *
     * @param context
     * @param medicineId
     * @param year
     * @return
     */
    public static Cursor fetchByMedicineAndYear(Context context, Integer medicineId, String year) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndYear(medicineId, year);
    }

    /**
     * Fetch consumption based on medicine and month
     *
     * @param context
     * @param medicineId
     * @param year
     * @param month
     * @return
     */
    public static Cursor fetchByMedicineAndMonth(Context context, Integer medicineId, String year, String month) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndMonth(medicineId, year, month);
    }

    /**
     * Fetch consumption based on category and date
     *
     * @param context
     * @param medicineCategoryId
     * @param date
     * @return
     */
    public static Cursor fetchByCategoryAndDate(Context context, Integer medicineCategoryId, String date) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByCategoryAndDate(medicineCategoryId, date);
    }

    /**
     * Fetch consumption based on category and year
     *
     * @param context
     * @param medicineCategoryId
     * @param year
     * @return
     */
    public static Cursor fetchByCategoryAndYear(Context context, Integer medicineCategoryId, String year) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByCategoryAndYear(medicineCategoryId, year);
    }

    /**
     * Fetch consumption based on category and month
     *
     * @param context
     * @param medicineCategoryId
     * @param year
     * @param month
     * @return
     */
    public static Cursor fetchByCategoryAndMonth(Context context, Integer medicineCategoryId, String year, String month) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByCategoryAndMonth(medicineCategoryId, year, month);
    }

    public static boolean exists(Context context, int medicineId, String date, String time) {
        consumptionAll = new ConsumptionDAOImpl(context);
        Cursor cursor = consumptionAll.fetchByMedicineDateAndTime(medicineId, date, time);
        return cursor.getCount() > 0;
    }

    /**
     * Fetch unconsumed based on medicine and date
     *
     * @param context
     * @param medicineId
     * @param date
     * @return
     */
    public static Cursor fetchByMedicineAndDateUnconsumed(Context context, Integer medicineId, String date) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndDateUnconsumed(medicineId, date);
    }

    /**
     * Fetch unconsumed based on medicine and year
     *
     * @param context
     * @param medicineId
     * @param year
     * @return
     */
    public static Cursor fetchByMedicineAndYearUnconsumed(Context context, Integer medicineId, String year) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndYearUnconsumed(medicineId, year);
    }

    /**
     * Fetch unconsumed based on medicine and month
     *
     * @param context
     * @param medicineId
     * @param year
     * @param month
     * @return
     */
    public static Cursor fetchByMedicineAndMonthUnconsumed(Context context, Integer medicineId, String year, String month) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndMonthUnconsumed(medicineId, year, month);
    }


    /**
     * Delete all
     *
     * @param context
     * @param medicineId
     * @return
     */
    public static int deleteAllForMedicine(Context context, int medicineId) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.deleteAllForMedicine(medicineId);

    }


    /**
     * find data between dates
     *
     * @param context
     * @param dateFrom
     * @param dateTo
     * @return Cursor
     */
    public static Cursor betweenDate(Context context, String dateFrom, String dateTo) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.betweenDate(dateFrom, dateTo);
    }

    /**
     * find data between dates
     * @param context
     * @param categoryId
     * @param dateFrom
     * @param dateTo
     * @return cursor
     */

    public static Cursor fetchByCategoryAndBetweenDates(Context context, int categoryId, String dateFrom, String dateTo) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByCategoryAndBetweenDates(categoryId, dateFrom, dateTo);
    }

    /**
     * find data between dates
     * @param context
     * @param medicineId
     * @param dateFrom
     * @param dateTo
     * @return cursor
     */
    public static Cursor fetchByMedicineAndBetweenDates(Context context, int medicineId, String dateFrom, String dateTo) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndBetweenDates(medicineId, dateFrom, dateTo);
    }

    /**
     * find data between dates
     * @param context
     * @param medicineId
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public static Cursor fetchByMedicineAndBetweenDatesUnconsumed(Context context, int medicineId, String dateFrom, String dateTo) {

        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndBetweenDatesUnconsumed(medicineId, dateFrom, dateTo);
    }
}
