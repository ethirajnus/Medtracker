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

    public static Cursor findAll(Context context) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.findAll();
    }

    public static Cursor filterDate(Context context, String date) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.filterDate(date);
    }

    public static Consumption findById(Context context, int id) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.findById(id);
    }

    public long save(Context context) {
        consumptionDAO = new ConsumptionDAOImpl(context);
        return consumptionDAO.insert(this);
    }

    public int update(Context context) {
        consumptionDAO = new ConsumptionDAOImpl(context);
        return consumptionDAO.update(this);
    }

    public int delete(Context context) {
        consumptionDAO = new ConsumptionDAOImpl(context);
        return consumptionDAO.delete(this.getId());
    }

    public Medicine getMedicine(Context context) {
        MedicineManager medicineManager = new MedicineManager();
        return medicineManager.findById(context, getMedicineId());
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

    public static List<Consumption> filterByDate(List<Consumption> consumptions, String date) {
        List<Consumption> filteredConsumptions = new ArrayList<>();
        for (Consumption consumption : consumptions) {
            if (consumption.getDate().contentEquals(date)) {
                filteredConsumptions.add(consumption);
            }
        }
        return filteredConsumptions;
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

    public static int deleteAllForMedicine(Context context,int medicineId) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.deleteAllForMedicine(medicineId);

    }

    public static Cursor betweenDate(Context context,String dateFrom, String dateTo) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.betweenDate(dateFrom,dateTo);
    }

    public static Cursor fetchByCategoryAndBetweenDates(Context context,int categoryId,String dateFrom, String dateTo) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByCategoryAndBetweenDates(categoryId, dateFrom,dateTo);
    }

    public static Cursor fetchByMedicineAndBetweenDates(Context context,int medicineId,String dateFrom, String dateTo) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndBetweenDates(medicineId, dateFrom,dateTo);
    }

    public static Cursor fetchByMedicineAndBetweenDatesUnconsumed(Context context,int medicineId,String dateFrom, String dateTo) {
        consumptionAll = new ConsumptionDAOImpl(context);
        return consumptionAll.fetchByMedicineAndBetweenDatesUnconsumed(medicineId, dateFrom,dateTo);
    }


}
