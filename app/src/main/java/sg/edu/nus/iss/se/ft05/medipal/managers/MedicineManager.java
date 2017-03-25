package sg.edu.nus.iss.se.ft05.medipal.managers;

import android.content.Context;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import sg.edu.nus.iss.se.ft05.medipal.dao.MedicineDAO;
import sg.edu.nus.iss.se.ft05.medipal.dao.MedicineDAOImpl;
import sg.edu.nus.iss.se.ft05.medipal.domain.Category;
import sg.edu.nus.iss.se.ft05.medipal.domain.Medicine;
import sg.edu.nus.iss.se.ft05.medipal.domain.Reminder;
import sg.edu.nus.iss.se.ft05.medipal.model.Consumption;

import static sg.edu.nus.iss.se.ft05.medipal.constants.Constants.TIME_FORMAT;
import static sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper.MEDICINE_KEY_ID;
import static sg.edu.nus.iss.se.ft05.medipal.dao.DBHelper.MEDICINE_KEY_REMINDERID;


/**
 * Created by ethi on 12/03/17.
 */

public class MedicineManager {

    private static MedicineDAO medicineAll;
    private MedicineDAO medicineDAO;


    private Medicine medicine;

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }


    public MedicineManager(String name, String description, int categoryId, int reminderId, Boolean remind, int quantity, int dosage, int consumeQuantity, int threshold, String dateIssued, int expireFactor) {

        medicine = new Medicine(name, description, categoryId, reminderId, remind, quantity, dosage, consumeQuantity, threshold, dateIssued, expireFactor);
    }

    public MedicineManager() {

    }


    public static Cursor findAll(Context context) {
        medicineAll = new MedicineDAOImpl(context);
        Cursor cursor = medicineAll.findAll();
        return cursor;
    }

    public Medicine findById(Context context, int id) {
        medicineDAO = new MedicineDAOImpl(context);
        medicine = medicineDAO.findById(id);
        return medicine;
    }

    public int delete(Context context) {
        medicineDAO = new MedicineDAOImpl(context);
        return medicineDAO.delete(this.medicine.getId());
    }

    public long save(Context context) {
        medicineDAO = new MedicineDAOImpl(context);
        return medicineDAO.insert(medicine);
    }

    public int update(Context context) {
        medicineDAO = new MedicineDAOImpl(context);
        return medicineDAO.update(medicine);
    }

    public static Map<Integer, Integer> listAllMedicine(Context context) {
        Cursor cursor = MedicineManager.findAll(context);
        Map<Integer, Integer> medicineHashMap = new HashMap();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            medicineHashMap.put(cursor.getInt(cursor.getColumnIndex(MEDICINE_KEY_ID)), cursor.getInt(cursor.getColumnIndex(MEDICINE_KEY_REMINDERID)));
        }
        return medicineHashMap;
    }


    public int updateReminder(Context context, boolean isChecked) {
        medicineDAO = new MedicineDAOImpl(context);
        medicine.setRemind(isChecked);
        return medicineDAO.update(medicine);

    }

    public Category getCategory(Context context) {

        return new CategoryManager().findById(context, medicine.getCategoryId());
    }

    public static Cursor fetchAllMedicinesWithId(Context context) {
        medicineAll = new MedicineDAOImpl(context);
        return medicineAll.fetchAllMedicinesWithId();
    }

    public Reminder getReminder(Context context) {

        return new ReminderManager().findById(context, medicine.getReminderId());
    }

    public List<Consumption> consumptions(Context context) {

        return Consumption.findByMedicineID(context, medicine.getId());

    }

    public List<String> findConsumptionTime(Context context, int consumptionMedicine) {

        List<String> timelist = new ArrayList<>();
        medicine = findById(context, consumptionMedicine);
        Reminder reminder = getReminder(context);
        String startTime = reminder.getStartTime();
        int frequency = reminder.getFrequency();
        int interval = reminder.getInterval();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(startTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < frequency; i++) {
            cal.add(Calendar.MINUTE, interval * i);
            timelist.add(sdf.format(cal.getTime()));
        }
        return timelist;

    }
}
