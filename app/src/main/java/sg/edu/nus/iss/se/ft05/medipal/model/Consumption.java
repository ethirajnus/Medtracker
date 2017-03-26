package sg.edu.nus.iss.se.ft05.medipal.model;

import android.content.Context;
import android.database.Cursor;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.dao.ConsumptionDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.domain.Medicine;
import sg.edu.nus.iss.se.ft05.medipal.managers.MedicineManager;

/**
 * Created by ethi on 10/03/17.
 */

/**
 * Class for consumption
 */
public class Consumption {

    private static ConsumptionDAOImpl consumptionAll;
    private int id, medicineId, quantity;
    private String date, time;

    private ConsumptionDAOImpl consumptionDAO;

    public Consumption(int medicineId, int quantity, String date, String time) {
        this.medicineId = medicineId;
        this.date = date;
        this.time = time;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Consumption() {

    }

    /**
     * Method for finding all consumption
     * @param context
     * @return
     */
    public static Cursor findAll(Context context) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.findAll();
    }

    /**
     * Method to filter consumption by date
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
     * @param context
     * @param id
     * @return
     */
    public static Consumption findById(Context context, int id) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.findById(id);
    }

    /**
     * SAve consumption
     * @param context
     * @return
     */
    public long save(Context context) {
        consumptionDAO = new ConsumptionDAOImpl(context);
        return consumptionDAO.insert(this);
    }

    /**
     * Update consumption
     * @param context
     * @return
     */
    public int update(Context context) {
        consumptionDAO = new ConsumptionDAOImpl(context);
        return consumptionDAO.update(this);
    }

    /**
     * Delete consumption
     * @param context
     * @return
     */
    public int delete(Context context) {
        consumptionDAO = new ConsumptionDAOImpl(context);
        return consumptionDAO.delete(this.getId());
    }

    /**
     * Method to get medicine
     * @param context
     * @return
     */
    public Medicine getMedicine(Context context) {
        MedicineManager medicineManager = new MedicineManager();
        return medicineManager.findById(context, getMedicineId());
    }

    /**
     * Lsit all consumption
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

    public static List<Consumption> filterByDate(List<Consumption> consumptions, String date) {
        List<Consumption> filteredConsumptions = new ArrayList<>();
        for (Consumption consumption : consumptions) {
            if (consumption.getDate().contentEquals(date)) {
                filteredConsumptions.add(consumption);
            }
        }
        return filteredConsumptions;
    }

    /**
     * Fetch consumption based on category
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
     * @param context
     * @param medicineId
     * @return
     */
    public static int deleteAllForMedicine(Context context,int medicineId) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.deleteAllForMedicine(medicineId);

    }

    /**
     *
     * @param context
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public static Cursor betweenDate(Context context,String dateFrom, String dateTo) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.betweenDate(dateFrom,dateTo);
    }
}
