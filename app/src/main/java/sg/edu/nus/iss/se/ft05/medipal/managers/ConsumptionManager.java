package sg.edu.nus.iss.se.ft05.medipal.managers;

import android.content.Context;
import android.database.Cursor;


import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.se.ft05.medipal.dao.ConsumptionDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.domain.Consumption;
import sg.edu.nus.iss.se.ft05.medipal.domain.Medicine;

/**
 * Created by ethi on 10/03/17.
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

    public static Cursor findAll(Context context) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.findAll();
    }

    public static Cursor filterDate(Context context, String date) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.filterDate(date);
    }

    public Consumption findById(Context context, int id) {
        consumptionDAO = new ConsumptionDAOImpl(context);
        consumption = consumptionDAO.findById(id);
        return consumption;
    }

    public long save(Context context) {
        consumptionDAO = new ConsumptionDAOImpl(context);
        return consumptionDAO.insert(consumption);
    }

    public int update(Context context) {
        consumptionDAO = new ConsumptionDAOImpl(context);
        return consumptionDAO.update(consumption);
    }

    public int delete(Context context) {
        consumptionDAO = new ConsumptionDAOImpl(context);
        return consumptionDAO.delete(this.consumption.getId());
    }

    public Medicine getMedicine(Context context) {
        MedicineManager medicineManager = new MedicineManager();
        return medicineManager.findById(context, consumption.getMedicineId());
    }

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

    public static Cursor fetchByCategory(Context context, int medicineCategoryId) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByCategory(medicineCategoryId);
    }

    public static Cursor fetchByMedicine(Context context, int medicineId) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicine(medicineId);
    }

    public static Cursor fetchByMedicineAndDate(Context context, Integer medicineId, String date) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndDate(medicineId, date);
    }

    public static Cursor fetchByMedicineAndYear(Context context, Integer medicineId, String year) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndYear(medicineId, year);
    }

    public static Cursor fetchByMedicineAndMonth(Context context, Integer medicineId, String year, String month) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndMonth(medicineId, year, month);
    }

    public static Cursor fetchByCategoryAndDate(Context context, Integer medicineCategoryId, String date) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByCategoryAndDate(medicineCategoryId, date);
    }

    public static Cursor fetchByCategoryAndYear(Context context, Integer medicineCategoryId, String year) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByCategoryAndYear(medicineCategoryId, year);
    }

    public static Cursor fetchByCategoryAndMonth(Context context, Integer medicineCategoryId, String year, String month) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByCategoryAndMonth(medicineCategoryId, year, month);
    }

    public static boolean exists(Context context, int medicineId, String date, String time) {
        consumptionAll = new ConsumptionDAOImpl(context);
        Cursor cursor = consumptionAll.fetchByMedicineDateAndTime(medicineId, date, time);
        return cursor.getCount() > 0;
    }

    public static Cursor fetchByMedicineAndDateUnconsumed(Context context, Integer medicineId, String date) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndDateUnconsumed(medicineId, date);
    }

    public static Cursor fetchByMedicineAndYearUnconsumed(Context context, Integer medicineId, String year) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndYearUnconsumed(medicineId, year);
    }

    public static Cursor fetchByMedicineAndMonthUnconsumed(Context context, Integer medicineId, String year, String month) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndMonthUnconsumed(medicineId, year, month);
    }

    public static int deleteAllForMedicine(Context context, int medicineId) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.deleteAllForMedicine(medicineId);

    }

    public static Cursor betweenDate(Context context, String dateFrom, String dateTo) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.betweenDate(dateFrom, dateTo);
    }

    public static Cursor fetchByCategoryAndBetweenDates(Context context, int categoryId, String dateFrom, String dateTo) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByCategoryAndBetweenDates(categoryId, dateFrom, dateTo);
    }

    public static Cursor fetchByMedicineAndBetweenDates(Context context, int medicineId, String dateFrom, String dateTo) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndBetweenDates(medicineId, dateFrom, dateTo);
    }

    public static Cursor fetchByMedicineAndBetweenDatesUnconsumed(Context context, int medicineId, String dateFrom, String dateTo) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndBetweenDatesUnconsumed(medicineId, dateFrom, dateTo);
    }


}
